package com.tms.ontrack.mobile.ElectricityBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.AirtimeSales.HeadingView;
import com.tms.ontrack.mobile.AirtimeSales.InfoView;
import com.tms.ontrack.mobile.AirtimeSales.model.SmartCallAgentLogin;
import com.tms.ontrack.mobile.AirtimeSales.model.get_all_networks.GetAllNetworksResponse;
import com.tms.ontrack.mobile.AirtimeSales.model.get_all_networks.NeTworks;
import com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans.GetAllDataPlansResponse;
import com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans.Product;
import com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans.ProductType;
import com.tms.ontrack.mobile.AirtimeSales.spinner_adapter.CustomSpinnerAdapter;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitSmartCallToken;
import com.tms.ontrack.mobile.Web_Services.RetrofitSmartCallClient;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricityBundleActivity extends AppCompatActivity {

    private static final String TAG ="ElectricityBundleAct" ;
    Toolbar toolbar;
    private Dialog smartCallLoginDialog;
    private String username;
    @BindView(R.id.spinnerNetworks)
    Spinner spinnerNetworks;
    private String password;
    private String smartCallToken;
    Button loadingButton;
    private String message;
    ProgressDialog progressBar;
    private SharedPreferences sharedPreferences;
    private String getSavedToken;
    private List<String> networkName;
    List<String> networkId;
    private String network_name;
    private int current_network_pos;
    private LinearLayout linearGetProductInfo;
    List<String> expandableListTitle;
    public List<Product> prod;
    public static List<Product> prod1=new ArrayList<>();
    ArrayList<String>desc;
    ExpandablePlaceHolderView expandableView;
    private ProgressBar progress;
    private TextView tvNoPlanFound;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_bundle);
        ButterKnife.bind(this);
        toolbar=findViewById(R.id.toolbar);
        expandableView=findViewById(R.id.expandableView);
        spinnerNetworks=findViewById(R.id.spinnerNetworks);
        progress=findViewById(R.id.progressBar);
        linearGetProductInfo=findViewById(R.id.linearGetProductInfo);
        toolbar.setTitle("Electricity Bundle");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        sharedPreferences= getSharedPreferences("smartCallLogin", 0);
        getSavedToken=sharedPreferences.getString("smartCallToken",null);
        if(getSavedToken!=null)
        {
            Log.d(TAG, "onCreate: saved token:"+getSavedToken);
            getAllNetworks();

        }
        else {
            Log.d(TAG, "onCreate: token:"+getSavedToken);
            showDialog();
        }
        NestedScrollView view = (NestedScrollView) findViewById(R.id.scrollView);
        view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.smartcall_logout)
                {
                    invalidateToken();

                }
                return false;
            }
        });
    }

    private void getAllNetworks() {
        progress.setVisibility(View.VISIBLE);
        Log.d(TAG, "getAllNetworks: getting netwrks");
        Web_Interface webInterface= RetrofitSmartCallToken.getClient().create(Web_Interface.class);
        Call<GetAllNetworksResponse> call=webInterface.requestSmartCallGetAllNetworks();
        call.enqueue(new Callback<GetAllNetworksResponse>() {
            @Override
            public void onResponse(Call<GetAllNetworksResponse> call, Response<GetAllNetworksResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    List<NeTworks> neTworksList=new ArrayList<>();
                    neTworksList=response.body().getNetworks();
                    networkName=new ArrayList<>();
                    networkId=new ArrayList<>();
                    for(int i=0;i<neTworksList.size();i++)
                    {
                        if(neTworksList.get(i).getDescription().contains("Electricity")) {
                            networkName.add(neTworksList.get(i).getDescription());
                            networkId.add(neTworksList.get(i).getId() + "");
                        }
                    }
                    Log.d(TAG, "onResponse: networksname:"+networkName+"\n"+"networkid:"+networkId);
                    setSpinnerView(networkName);
                    progress.setVisibility(View.GONE);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG,jObjError.getString("responseDescription"));
                        message=jObjError.getString("responseDescription");
                        Toast.makeText(ElectricityBundleActivity.this, message, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: else");
                        progress.setVisibility(View.GONE);
                        //stopping progress
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception:"+e.getLocalizedMessage());
                        //stopping progress
                        progress.setVisibility(View.GONE);


                    }
                    Log.d(TAG, "onResponse: else fail");
                }
            }

            @Override
            public void onFailure(Call<GetAllNetworksResponse> call, Throwable t) {
                Toast.makeText(ElectricityBundleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                progress.setVisibility(View.GONE);


            }
        });

    }

    private void setSpinnerView(List<String> networkName) {
        Log.d(TAG, "setSpinnerView: called");
        int[] images = {R.drawable.eletricity_mun,
                R.drawable.eskom, R.drawable.eletricity_free};

        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getApplicationContext(),images,networkName);
        spinnerNetworks.setAdapter(customSpinnerAdapter);
        spinnerNetworks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                network_name = networkName.get(position);
                String opt_code = networkId.get(position);
                current_network_pos = Integer.parseInt(opt_code);
                Log.d(TAG, "Position is: " + current_network_pos + " item name: " + network_name);
                if(current_network_pos!=-1)
                {
                    getSpecificNetworkInfo(current_network_pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSpecificNetworkInfo(int current_network_pos) {
        progress.setVisibility(View.VISIBLE);
        linearGetProductInfo.setVisibility(View.GONE);
        Log.d(TAG, "getSpecificNetworkInfo: called current pos:"+current_network_pos);
        Log.d(TAG, "getSpecificNetworkInfo: retrofit initalised");
        Web_Interface web_interface=RetrofitSmartCallToken.getClient().create(Web_Interface.class);
        Call<GetAllDataPlansResponse> call=web_interface.requestGetAllDataPlans(current_network_pos);
        call.enqueue(new Callback<GetAllDataPlansResponse>() {
            @Override
            public void onResponse(Call<GetAllDataPlansResponse> call, Response<GetAllDataPlansResponse> response) {
                if(response.isSuccessful() && response.code()==200)
                {
                    expandableView.removeAllViews();
                    prod=new ArrayList<>();
                    HashMap<String, List<String>> expandableListDetail=new HashMap<>();
                    desc=new ArrayList<>();
                    List<String> code=new ArrayList<>();
                    List<String> Typecode=new ArrayList<>();
                    desc.clear();
                    assert response.body() != null;
                    List<ProductType>productType=response.body().getProductTypes();
                    ArrayList<String>products;
                    ArrayList<String>retailvalue;
                    for(int i=0;i<productType.size();i++)
                    {   retailvalue=new ArrayList<>();
                        products=new ArrayList<>();
                        prod= productType.get(i).getProducts();
                        prod1= productType.get(i).getProducts();
                        if(productType.get(i).getCode().contains("E ")) {
                            expandableView.addView(new HeadingView(MyApp.getContext(), productType.get(i).getDescription()));
                            Log.d(TAG, "onResponse: contains+"+productType.get(i).getDescription());
                        }

                        code.add(productType.get(i).getCode());// product type codes
                        desc.add(productType.get(i).getDescription());// product type names
                        for (Product product : productType.get(i).getProducts()) {
                            if(productType.get(i).getCode().contains("E ")) {
                                Log.d(TAG, "onResponse: contains+"+product.getDescription());
                                expandableView.addView(new InfoView(MyApp.getContext(), product));
                            }
                            products.add(product + "");
                        }
                       /* for (int j=0;j<prod.size();j++)
                        {
                            retailvalue.add(prod.get(j).getRetailValue().toString());
                            products.add(prod.get(j).getDescription()); //product names
                            Typecode.add(prod.get(j).getTypeCode()); //type code of products
                            expandableListDetail.put(desc.get(i),products);
                            expandableListDetail.put(desc.get(i)+"1",retailvalue);
                            expandableView.addView(new InfoView(MyApp.getContext(),);

                            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        }*/
                        Log.d(TAG, "onResponse: product type:"+productType.get(i).getDescription());
                        Log.d(TAG, "onResponse: packs:"+products.size());

                    }
                    progress.setVisibility(View.GONE);
                    linearGetProductInfo.setVisibility(View.VISIBLE);



                }
                else {
                    Log.d(TAG, "onResponse: else case");
                    progress.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<GetAllDataPlansResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                progress.setVisibility(View.GONE);


            }
        });
    }



    private void invalidateToken() {
        Log.d(TAG, "invalidateToken: called");
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Disconnecting from smart call");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        Web_Interface webInterface= RetrofitSmartCallClient.getClient().create(Web_Interface.class);
        Call<SmartCallAgentLogin> call=webInterface.requestSmartCallLoginInvalidateToken("Bearer "+getSavedToken);
        call.enqueue(new Callback<SmartCallAgentLogin>() {
            @Override
            public void onResponse(Call<SmartCallAgentLogin> call, Response<SmartCallAgentLogin> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    smartCallToken=response.body().getResponseDescription();
                    message=response.body().getResponseDescription();
                    Toasty.success(ElectricityBundleActivity.this,message).show();
                    progressBar.dismiss();
                    sharedPreferences= getSharedPreferences("smartCallLogin", 0);
                    if (sharedPreferences.contains("smartCallToken")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("smartCallToken","");
                        editor.remove("smartCallToken");
                        editor.apply();
                        startActivity(new Intent(ElectricityBundleActivity.this, Agent_Mainactivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }


                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG,jObjError.getString("responseDescription"));
                        message=jObjError.getString("responseDescription");
                        Toast.makeText(ElectricityBundleActivity.this, message, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: else");
                        progressBar.dismiss();
                        //stopping progress
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception:"+e.getLocalizedMessage());
                        //stopping progress

                    }
                }
            }


            @Override
            public void onFailure(Call<SmartCallAgentLogin> call, Throwable t) {
                Toast.makeText(ElectricityBundleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.dismiss();

            }
        });

    }

    private void showDialog() {

        smartCallLoginDialog = new Dialog(this);
        smartCallLoginDialog.setContentView(R.layout.custom_smartcall_login_dialog);
        smartCallLoginDialog.setTitle("Ontrack Mobile");
        final EditText editTextUsername = (EditText) smartCallLoginDialog.findViewById(R.id.editTextUsername);
        final EditText editTextPassword = (EditText) smartCallLoginDialog.findViewById(R.id.editTextpassword);
        loadingButton = smartCallLoginDialog.findViewById(R.id.btnLogin);
        loadingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                username=editTextUsername.getText().toString();
                password=editTextPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty())
                {
                    Toasty.info(MyApp.getContext(),"Please fill the credentials").show();
                }
                else {
                    String up=username+":"+password;
                    //noinspection deprecation
                    byte[] encoded_username_password = Base64.encodeBase64(up.getBytes());
                    String finalEncoded=new String(encoded_username_password);

                    Log.d(TAG, "showDialog: username:"+finalEncoded);
                    sendLoginDataToServer(finalEncoded);
                }
            }
        });
        smartCallLoginDialog.setCanceledOnTouchOutside(false);
        smartCallLoginDialog.show();
    }

    private void sendLoginDataToServer(String finalEncoded) {

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Checking the Login Credentials...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        Web_Interface webInterface= RetrofitSmartCallClient.getClient().create(Web_Interface.class);
        Call<SmartCallAgentLogin> call=webInterface.requestSmartCallLogin("Basic "+finalEncoded);
        call.enqueue(new Callback<SmartCallAgentLogin>() {
            @Override
            public void onResponse(Call<SmartCallAgentLogin> call, Response<SmartCallAgentLogin> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    assert response.body() != null;
                    smartCallToken=response.body().getAccessToken();
                    message=response.body().getResponseDescription();
                    Log.d(TAG, "onResponse: token:"+smartCallToken);
                    sharedPreferences= getSharedPreferences("smartCallLogin", 0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("smartCallToken",smartCallToken);
                    editor.apply();
                    getAllNetworks();
                    //getting token
                    sharedPreferences= getSharedPreferences("smartCallLogin", 0);
                    String getSavedToken=sharedPreferences.getString("smartCallToken",null);
                    Log.d(TAG, "onResponse: saved token is:"+getSavedToken);
                    Toasty.success(ElectricityBundleActivity.this,message).show();
                    if(smartCallLoginDialog.isShowing())
                    {
                        smartCallLoginDialog.dismiss();
                    }
                    progressBar.dismiss();


                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG,jObjError.getString("responseDescription"));
                        message=jObjError.getString("responseDescription");
                        Toast.makeText(ElectricityBundleActivity.this, message, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: else");
                        progressBar.dismiss();
                        //stopping progress
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: exception:"+e.getLocalizedMessage());
                        //stopping progress

                    }
                }
            }

            @Override
            public void onFailure(Call<SmartCallAgentLogin> call, Throwable t) {
                Toast.makeText(ElectricityBundleActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.dismiss();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signout_smartcall, menu);
        return true;
    }

}
