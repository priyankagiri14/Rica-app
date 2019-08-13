package com.tms.ontrack.mobile.OpenCloseBatches;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenCloseActivity extends AppCompatActivity {

    public static String message;
    ListView listView;
    Button btnstatus1;
    String id,value;
    String batchid;
    OpenCloseListAdapter adapter;
    TextView batchnumber;
    ProgressDialog progressBar;

    private void populateListView(List<String> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
//        Log.d("PNK", list1.toString());
        //bodyArrayList1 = batchesReceivedResponseList;
        adapter = new OpenCloseListAdapter(this,batchesReceivedResponseList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
//        listviewclick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        id = getIntent().getExtras().getString("batchid");
        value = getIntent().getExtras().getString("value");

        btnstatus1 =(Button)findViewById(R.id.agentserials);
        listView = (ListView)findViewById(R.id.agent_serials_received_listview);
        batchnumber = (TextView) findViewById(R.id.batch_number);
        batchnumber.setText(value);
        apiImplement();
        btnstatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(OpenCloseActivity.this).create();
                alertDialog.setMessage("Are you sure to Start with this Batch");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressBar.show();

                        String status = "start";
                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                        JSONObject paramObject = new JSONObject();
                        try {
//                            paramObject.put("action",status);
                            paramObject.put("batchNo", value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());

                        Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status,body);
                        openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
                            @Override
                            public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    message = response.body().getMessage();
                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Pref.setBatchID(MyApp.getContext(), id);
                                    Pref.setBatchNo(MyApp.getContext(),value);
                                    progressBar.cancel();
                                    Intent intent = new Intent(OpenCloseActivity.this, Agent_Mainactivity.class);
                                    intent.putExtra("batch",id);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
                                Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                      // Toast.makeText(OpenCloseActivity.this, "Batch is now opened for Working", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();

//                        String status = "stop";
//                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                        try {
//                            JSONObject paramObject = new JSONObject();
//                            paramObject.put("batchNo", value);
//
//
//                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                            Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status,body);
//                            openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
//                                @Override
//                                public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
//                                    String message = response.body().getMessage();
//                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    alertDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
//                                    Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        catch (JSONException e)
//                        {
//                            Toast.makeText(OpenCloseActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
                       // Toast.makeText(OpenCloseActivity.this, "You are done with this Batch", Toast.LENGTH_SHORT).show();
                    }
                });
//                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Complete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String status = "complete";
//                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                        try {
//                            JSONObject paramObject = new JSONObject();
//                            paramObject.put("batchNo", value);
//                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                            Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status,body);
//                            openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
//                                @Override
//                                public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
//                                    String message = response.body().getMessage();
//                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    alertDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
//                                    Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        catch (JSONException e)
//                        {
//                            Toast.makeText(OpenCloseActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                        //Toast.makeText(OpenCloseActivity.this, "You are completed with this batch", Toast.LENGTH_SHORT).show();
//                    }
//                });
                alertDialog.show();
            }
        });
    }

    private void apiImplement() {

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<SerialsGetResponse> serialsGetResponseCall = web_interface.requestSerialsGet(id);
        serialsGetResponseCall.enqueue(new Callback<SerialsGetResponse>() {
            @Override
            public void onResponse(Call<SerialsGetResponse> call, Response<SerialsGetResponse> response) {
//
                //List<SerialsGetResponse> batchesReceivedResponseList1 = (List<SerialsGetResponse>) new SerialsGetResponse();
                String status = response.body().getSuccess().toString();
                Log.d("Serials Get: ",status);

                String body = response.body().getBody();
                List<String> bodystring = Arrays.asList(body.split(","));
                List<String> strings = new ArrayList<>();

                for(int i = 0;i<bodystring.size();i++)
                {
                    Log.d("ListView ",bodystring.get(i));
                    strings.add(bodystring.get(i));
                }
                //Log.d("onResponse: ",);
                    populateListView(strings);
            }

            @Override
            public void onFailure(Call<SerialsGetResponse> call, Throwable t) {
                Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
