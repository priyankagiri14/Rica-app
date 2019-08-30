package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.AllocationStatus.AgentAllocationStatusResponse;
import com.tms.ontrack.mobile.AllocationStatus.AllocationStatusResponse;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentNormalBatchesList extends AppCompatActivity implements View.OnClickListener , LocationListener {

    private AgentBatchesGetListAdapter adapter;
    List<AgentBatchesGetResponse> list2 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    TextView assignedbatches,nobatchesget;
    public ListView listView;
    ProgressDialog progressBar;
    int count = 0;
    LocationManager locationManager;
    double latitude,longitude;
    Button btnstatus;
    private AppDatabase db;
    Batches batchesdata = new Batches();

    private void populateListView(List<Body> batchesGetResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list2.toString());

        bodyArrayList1 = batchesGetResponseList;
        adapter = new AgentBatchesGetListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
            /*searchView=findViewById(R.id.searchview);
            searchView.setOnQueryTextListener(this);
            setupSearchView();*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_normal_batches_list);

        db = Room.databaseBuilder(MyApp.getContext(),AppDatabase.class,"batch")
                .allowMainThreadQueries()
                .build();

        Log.d("PNK", "ONCREATE");
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        listView = (ListView) findViewById(R.id.agent_batches_get_listview);
        assignedbatches = (TextView)findViewById(R.id.agent_assigned_batches);
        nobatchesget = (TextView)findViewById(R.id.noagentbatchesget);
        btnstatus = (Button)findViewById(R.id.agentbtnstatus);
        btnstatus.setVisibility(View.INVISIBLE);
        assignedbatches.setVisibility(View.INVISIBLE);
        getLocation();
        batchesGet();
        btnstatus.setOnClickListener(this);
    }

    private void getLocation() {

        try{
            progressBar.show();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
            progressBar.dismiss();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private void batchesGet(){

        Log.d("PNK", "BATCHESGET");

        Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentBatchesGetResponse> batchesGetResponseCall = web_interface1.requestAgentBatchesGet(0, 0);
        batchesGetResponseCall.enqueue(new Callback<AgentBatchesGetResponse>() {
            @Override
            public void onResponse(Call<AgentBatchesGetResponse> call, Response<AgentBatchesGetResponse> response) {
                List<Body> list1 = new ArrayList<>();
                assert response.body() != null;
                list1 = response.body().getBody();

                for(int i=0;i<list1.size();i++)
                {
                    String status = list1.get(i).getStatus();
                    String valuesim = String.valueOf(list1.get(i).isValueSim());
                    if(status.equals("PENDING") && valuesim.equals("false"))
                    {
                        bodyArrayList1.add(list1.get(i));
                        populateListView(bodyArrayList1);
                        btnstatus.setVisibility(View.VISIBLE);
                        assignedbatches.setVisibility(View.VISIBLE);
                    }
//                    else if(status.equals("RECEIVED"))
//                        {
//                            Toast.makeText(BatchesGetList.this, "No Data is Assigned to You!", Toast.LENGTH_SHORT).show();
//                        }
                }
                if(listView.getCount() == 0)
                {
                    nobatchesget.setVisibility(View.VISIBLE);
                    progressBar.cancel();
                    //Toast.makeText(BatchesGetList.this, "No Data is Assigned to You..!", Toast.LENGTH_SHORT).show();
                }
//                for (int i =0; i <list.size(); i++) {
//                    list1.add(response.body());
//                }

                Log.d("PNK", "LIST1");
                Log.d("PNK",  list2.toString());

//                populateListView(list1.get(0).getBody());

                Log.d("Batches", "onResponse: " + list2);
            }

            @Override
            public void onFailure(Call<AgentBatchesGetResponse> call, Throwable t) {
                Toast.makeText(AgentNormalBatchesList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        for (int j = 0; j < bodyArrayList1.size(); j++) {

            if (bodyArrayList1.get(j).isIschecked()) {
                bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                count++;
                //batches[j] = bodyArrayList1.get(j).getBatchNo();
            }
        }
        if (count == 0) {
            Toast.makeText(AgentNormalBatchesList.this, "Please Select any Batch", Toast.LENGTH_SHORT).show();
        } else {

            Log.d("PNK", "ONCLICK");
            Log.d("PNK", "" + v.getId());

            AlertDialog alertDialog = new AlertDialog.Builder(AgentNormalBatchesList.this).create();
            alertDialog.setMessage("Please Confirm..?");

            MyPojo pojo = new MyPojo();

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "RECEIVED", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {

                    String stts = "RECEIVED";
                    Log.d("PNK", "Here I am");


                    String[] batches = new String[bodyArrayListbatches.size()];
                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                        batchesdata.setBatches(bodyArrayListbatches.get(j));
                    }

                    db.batchesInterface().insertBatches(batchesdata);
                    Log.d("onTableData: ", batchesdata.getBatches());

                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    pojo.setStatus(stts);
                    pojo.setBatches(batches);
                    Call<AgentAllocationStatusResponse> call = web_interface.requestAgentAllocationStatus(latitude, longitude, pojo);
                    //exeuting the service
                    call.enqueue(new Callback<AgentAllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AgentAllocationStatusResponse> call, Response<AgentAllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(AgentNormalBatchesList.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AgentAllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(AgentNormalBatchesList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(AgentNormalBatchesList.this, Agent_Mainactivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "DECLINE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String reason = "These Batches are assigned mistakenly";
                    String status = "DECLINED";
                    Log.d("PNK", "Here I am");
                    int size = listView.getCount();

                    for (int j = 0; j < bodyArrayList1.size(); j++) {

                        if (bodyArrayList1.get(j).isIschecked()) {
                            bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                            //batches[j] = bodyArrayList1.get(j).getBatchNo();
                        }
                    }
                    String[] batches = new String[bodyArrayList1.size()];
                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                        count++;
                    }
                    if (count == 0) {
                        Toast.makeText(AgentNormalBatchesList.this, "Please Select any Batch for Decline", Toast.LENGTH_SHORT).show();
                    } else {
                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                        pojo.setStatus(status);
                        pojo.setBatches(batches);
                        pojo.setReason(reason);
                        Call<AgentAllocationStatusResponse> call = web_interface.requestAgentAllocationStatus(latitude, longitude, pojo);
                        //exeuting the service
                        Log.d("agentlogin: ", call.toString());
                        call.enqueue(new Callback<AgentAllocationStatusResponse>() {
                            @Override
                            public void onResponse(Call<AgentAllocationStatusResponse> call, Response<AgentAllocationStatusResponse> response) {

                                String message = response.body().getMessage();
                                Toast.makeText(AgentNormalBatchesList.this, message, Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<AgentAllocationStatusResponse> call, Throwable t) {
                                Toast.makeText(AgentNormalBatchesList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

//                Intent intent=new Intent(BatchesGetList.this, MainActivity.class);
//                intent.putExtra("batcheslist",bodyArrayList);
//                startActivity(intent);
//
                    }
                }
            });

            alertDialog.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude= location.getLongitude();
        latitude= location.getLatitude();
        Log.d("onLocationChanged: ", String.valueOf(latitude));
        Log.d("onLocationChanged: ", String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if(!((Activity) AgentNormalBatchesList.this).isFinishing())
        {
            //show dialog
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Open GPS Settings?");
            alertDialog.setCancelable(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if(Settings.ACTION_LOCATION_SOURCE_SETTINGS.equals(true))
                    {
                        alertDialog.dismiss();
                    }
                    count++;
                }
            });
            if(count>0)
            {
                alertDialog.dismiss();
                count = 0;
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AgentNormalBatchesList.this, Agent_Mainactivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            alertDialog.show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Enable Location")
//                    .setMessage("Open GPS Settings")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
//                    .setNegativeButton("Cancel",
//                            (dialog, id) -> startActivity(new Intent(DriverAttendance.this,Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
//            alert = builder.create();
//            alert.show();
        }
    }
}

