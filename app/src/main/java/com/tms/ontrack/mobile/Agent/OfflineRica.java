package com.tms.ontrack.mobile.Agent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.location.aravind.getlocation.GeoLocator;
import com.tms.ontrack.mobile.Agent.model.Simallocatemodel;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.AppDatabaseSerials;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.Serials;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.SerialsAdapter;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.SerialsInterface;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineRica extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener{

    public EditText fname, lname, address, pincode, subhurb, simserial, idnum, city;
    public RadioGroup networkrg;
    ListView listViewsearchserials;
    private String mResult = null;
    ArrayList<String> ListElementsArrayList;
    ArrayAdapter<String> adapter;
    String[] batches;
    Toolbar toolbar;
    RadioButton vodacom, telkom, cellc, mtn;
    String network, citystring;
    String simcard = "";
    Button simallocate, agentscanbtn;
    Spinner regionspinner;
    String region;
    String type = "OFFLINE";
    private SearchView searchView;
    AppDatabaseSerials db;
    SerialsInterface serialsInterface;
    SerialsAdapter serialsAdapter;
    List<Serials> serialsList = new ArrayList<>();
    TextView textserials;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_rica);

        regionspinner = (Spinner) findViewById(R.id.regionspinner);

        db = Room.databaseBuilder(MyApp.getContext(), AppDatabaseSerials.class, "serials")
                .allowMainThreadQueries()
                .build();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (SearchView) findViewById(R.id.agentsearchview);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.postal_code);
        subhurb = findViewById(R.id.subhurb);
        idnum = findViewById(R.id.idnum);
        city = findViewById(R.id.city);
        networkrg = findViewById(R.id.netwrokrg);
        networkrg.setOnCheckedChangeListener(this);
        vodacom = findViewById(R.id.vodacom);
        telkom = findViewById(R.id.telkom);
        mtn = findViewById(R.id.mtn);
        simallocate = findViewById(R.id.activate_sim);
        agentscanbtn = findViewById(R.id.agentscanbtn);
        searchView.setQueryHint("Search");
        //searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        agentscanbtn.setOnClickListener(this);
        simallocate.setOnClickListener(this);
        listViewsearchserials = findViewById(R.id.agent_search_results_list);
        listViewsearchserials.setTextFilterEnabled(true);
        listViewsearchserials.setVisibility(View.GONE);
        listViewsearchserials.setOnItemClickListener(this);
        cellc = findViewById(R.id.cellc);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            searchView.setQuery("", false);
        } else {
            simcard = bundle.getString("simcard");
            searchView.setQuery(simcard, false);
        }
        if (Pref.getCity(this) == null) {
            city.setText("");
        } else {
            city.setText(Pref.getCity(this));
        }

        regionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(OfflineRica.this, "Please Enter data for all the Fields", Toast.LENGTH_SHORT).show();
                } else {
                    region = regionspinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initiateScan() {
        ZxingOrient integrator = new ZxingOrient(this);
        integrator.setVibration(true);
        integrator.setBeep(true);
        integrator.initiateScan();
    }

    private void getLocation() {
        GeoLocator geoLocator = new GeoLocator(getApplicationContext(), this);
        Log.d("startbranding", "getLocation: " + geoLocator.getLattitude() + "\n" + geoLocator.getLongitude());
        address.setText(geoLocator.getAddress());
        Log.d("locationda", address.toString());
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.agentscanbtn) {
            initiateScan();
        }
        if (v.getId() == R.id.activate_sim) {
            if (fname.getText().toString().length() == 0 || lname.getText().length() == 0 || address.length() == 0 ||
                    pincode.getText().toString().length() == 0 || subhurb.getText().toString().length() == 0 || network.isEmpty()
                    || searchView.getQuery().length() == 0 || idnum.getText().length() == 0) {
                Toast.makeText(this, "Enter required fields", Toast.LENGTH_SHORT).show();
            } else {
                simallocation(searchView.getQuery().toString(), network, idnum.getText().toString(), fname.getText().toString(), lname.getText().toString(),
                        address.getText().toString(), pincode.getText().toString(), subhurb.getText().toString(), city.getText().toString());
            }
        }

    }

    private void addBatchValue() {

        String textvalue = searchView.getQuery().toString();

        //adding only unique values
        ListElementsArrayList.add(textvalue);
        batches = new String[ListElementsArrayList.size()];
        for (int j = 0; j < ListElementsArrayList.size(); j++) {
            batches[j] = ListElementsArrayList.get(j);
        }

        //Log.d(TAG, "onActivityResult: result"+ListElementsArrayList);
        adapter.notifyDataSetChanged();
        listViewsearchserials.setVisibility(View.GONE);
        searchView.setQuery("",false);

    }

    private void simallocation(String serial, String network, String idnum, String fname, String lname, String address, String postalcode, String subhurb, String city) {

        Web_Interface webInterface = RetrofitToken.getClient().create(Web_Interface.class);
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("serial",serial );
            paramObject.put("network", network);
            paramObject.put("idNo", idnum);
            paramObject.put("name", fname);
            paramObject.put("surname",lname);
            paramObject.put("address",address );
            paramObject.put("postalCode", postalcode);
            paramObject.put("suburb", subhurb);
            paramObject.put("city", city);
            paramObject.put("region",region);
            paramObject.put("type",type);
            Log.d("simalloacte data",serial+"\n"+network +"\n" +idnum+"\n"+fname+"\n"+lname +"\n" +address +"\n" +postalcode +"\n" +subhurb +"\n" +city);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<Simallocatemodel> call= webInterface.simallocate(body);
            //exeuting the service
            call.enqueue(new Callback<Simallocatemodel>() {
                @Override
                public void onResponse(Call<Simallocatemodel> call, Response<Simallocatemodel> response) {
                    if(response.isSuccessful()||response.code()==200){
                        String message=response.body().getMessage();
                        String success = response.body().getSuccess().toString();
                        if(success.equals("true"))
                        {
                            db.serialsInterface().deleteSerials(serial) ;
                            Toasty.success(getApplicationContext(),message).show();
                            Pref.setCity(MyApp.getContext(),city);
                            Intent intent = new Intent(OfflineRica.this, Agent_Mainactivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toasty.warning(getApplicationContext(),message).show();
                        }
                    }
                    else{
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().toString());
                            Log.d("AgentLoginActivity", jObjError.getString("message"));
                            Toasty.info(getApplicationContext(), jObjError.getString("message")).show();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show();
                            //stopping progress
                        }
                    }
                }

                @Override
                public void onFailure(Call<Simallocatemodel> call, Throwable t) {
                    Log.d("simallocate",t.getLocalizedMessage());
                    Toasty.info(getApplicationContext(),t.getLocalizedMessage()).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.vodacom:
                // do operations specific to this selection
                network = vodacom.getText().toString();
                Log.d("age", network);
                break;

            case R.id.telkom:
                network=telkom.getText().toString();
                break;
            case R.id.mtn:
                // do operations specific to this selection
                network = mtn.getText().toString();
                break;

            case R.id.cellc:
                network=cellc.getText().toString();
                break;



        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(searchView.getQuery().length() == 0)
        {
            listViewsearchserials.setVisibility(View.GONE);
            agentscanbtn.setVisibility(View.VISIBLE);
        }
        //arrayAdapterstring.getFilter().filter(newText);
        else
        {
            listViewsearchserials.setVisibility(View.VISIBLE);
            agentscanbtn.setVisibility(View.GONE);
            String text = "%" + newText + "%";
            serialsList = db.serialsInterface().serialscount(text);
            serialsAdapter = new SerialsAdapter(this, R.layout.searchview_layout, serialsList);
            serialsAdapter.notifyDataSetChanged();
            listViewsearchserials.setAdapter(serialsAdapter);
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String batchvalue = serialsList.get(position).getSerials();
        //String batchvalue = parent.getItemAtPosition(position).toString();
        Log.d("onItemClick: ",batchvalue);
        searchView.setQuery(batchvalue,false);
        listViewsearchserials.setVisibility(View.GONE);
        agentscanbtn.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ZxingOrientResult result = ZxingOrient.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.i("SMW", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.i("SMW", "Scanned");
                mResult = result.getContents();
                searchView.setQuery(mResult,false);
                //Log.d(TAG, "onActivityResult: result"+ListElementsArrayList);
            }

        }

    }

    public void searchviewClickable(View view) {
        searchView.setIconified(false);
    }
}
