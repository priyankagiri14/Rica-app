package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.AllocationStatus.AgentAllocationStatusResponse;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import fr.arnaudguyon.perm.Perm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentBatchesGet extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private AgentBatchesGetListAdapter adapter;
    List<AgentBatchesGetResponse> list2 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    public ListView listView;
    TextView agentbatchesassigned,noagentbatchesget;
    Button agentbtnstatus;
    ProgressDialog progressBar;
    int count = 0;
    private AppDatabase db;
    LocationManager locationManager;
    double latitude,longitude;
    Batches batchesdata = new Batches();
    private static final int REQUEST_LOCATION = 1;


    private void populateListView(List<Body> batchesGetResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list2.toString());

        bodyArrayList1 = batchesGetResponseList;
        adapter = new AgentBatchesGetListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_batches_get);

        db = Room.databaseBuilder(MyApp.getContext(),AppDatabase.class,"batch")
                .allowMainThreadQueries()
                .build();

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        agentbatchesassigned = (TextView) findViewById(R.id.agent_assigned_batches);
        listView = (ListView) findViewById(R.id.agent_batches_get_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        agentbtnstatus = (Button)findViewById(R.id.agentbtnstatus);
        noagentbatchesget = (TextView)findViewById(R.id.noagentbatchesget);
        agentbtnstatus.setVisibility(View.INVISIBLE);
        agentbatchesassigned.setVisibility(View.INVISIBLE);
        getLocation();
        /*perm = new Perm(this, PERMISSIONS);
        if (perm.areGranted()) {
            //   Toast.makeText(this, "All Permissions granted", Toast.LENGTH_LONG).show();
        } else {
            perm.askPermissions(PERMISSIONS_REQUEST);
            progressBar.dismiss();
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            progressBar.dismiss();
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }*/

//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            onGPS();
//        }
//        else
//        {
//            getLocation();
//
//        }
        batchesGet();
        agentbtnstatus.setOnClickListener(this);
        db.batchesInterface().batchestabledata();





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
        /*if(ActivityCompat.checkSelfPermission(AgentBatchesGet.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
         && ActivityCompat.checkSelfPermission(AgentBatchesGet.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        else
        {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,(LocationListener)this);
            Location location = LocationManager getSystemService(Context.LOCATION_SERVICE);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location !=null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("onLocationChanged: ", String.valueOf(latitude));
                Log.d("onLocationChanged: ", String.valueOf(longitude));
            }

        }*/
    }

    private void onGPS() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS..?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog =builder.create();
        alertDialog.show();
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
                    if(status.equals("PENDING")&& valuesim.equals("true"))
                    {
                        bodyArrayList1.add(list1.get(i));
                        populateListView(bodyArrayList1);
                        agentbtnstatus.setVisibility(View.VISIBLE);
                        agentbatchesassigned.setVisibility(View.VISIBLE);

                    }
//                    else if(status.equals("RECEIVED"))
//                        {
//                            Toast.makeText(BatchesGetList.this, "No Data is Assigned to You!", Toast.LENGTH_SHORT).show();
//                        }
                }
                if(listView.getCount() == 0)
                {
                    noagentbatchesget.setVisibility(View.VISIBLE);
                    progressBar.cancel();
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
                Toast.makeText(AgentBatchesGet.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {

        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());

        AlertDialog alertDialog = new AlertDialog.Builder(AgentBatchesGet.this).create();
        alertDialog.setMessage("Please Confirm..?");

        MyPojo myPojo1 = new MyPojo();

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "RECEIVED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                String stts = "RECEIVED";
                Log.d("PNK", "Here I am");

                for (int j = 0; j < bodyArrayList1.size(); j++) {

                    if (bodyArrayList1.get(j).isIschecked()) {
                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                        count++;
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                if (count == 0) {
                    Toast.makeText(AgentBatchesGet.this, "Please Select any Batch for Receiving", Toast.LENGTH_SHORT).show();
                } else {
                    String[] batches = new String[bodyArrayListbatches.size()];
                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                        batchesdata.setBatches(bodyArrayListbatches.get(j));
                    }
                    db.batchesInterface().insertBatches(batchesdata);
                    Log.d("onTableData: ",batchesdata.getBatches());


                    /*Gson gson = new Gson();
                    String jsonbatcharray = gson.toJson(bodyArrayListbatches);
                    Log.d("BatchArray: ",jsonbatcharray);
                    Pref.putBatchArray(getApplicationContext(),jsonbatcharray);*/
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    myPojo1.setStatus(stts);
                    myPojo1.setBatches(batches);
                    Call<AgentAllocationStatusResponse> callagent = web_interface.requestAgentAllocationStatus(latitude,longitude,myPojo1);
                    //exeuting the service
                    callagent.enqueue(new Callback<AgentAllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AgentAllocationStatusResponse> call, Response<AgentAllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(AgentBatchesGet.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AgentBatchesGet.this, Agent_Mainactivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<AgentAllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(AgentBatchesGet.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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
                        count++;
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                if (count == 0) {
                    Toast.makeText(AgentBatchesGet.this, "Please Select any Batch for Decline", Toast.LENGTH_SHORT).show();
                } else {
                    String[] batches = new String[bodyArrayList1.size()];
                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                    }
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    myPojo1.setStatus(status);
                    myPojo1.setBatches(batches);
                    myPojo1.setReason(reason);
                    Call<AgentAllocationStatusResponse> callagent = web_interface.requestAgentAllocationStatus(latitude,longitude,myPojo1);
                    //exeuting the service
                    Log.d("agentlogin: ", callagent.toString());
                    callagent.enqueue(new Callback<AgentAllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AgentAllocationStatusResponse> call, Response<AgentAllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(AgentBatchesGet.this, message, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AgentBatchesGet.this, Agent_Mainactivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<AgentAllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(AgentBatchesGet.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
//
                }
            }
        });

        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
      /*  latitude = 30.85625;
        longitude = 72.52424;*/
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
        if(!((Activity) AgentBatchesGet.this).isFinishing())
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
                    startActivity(new Intent(AgentBatchesGet.this, Agent_Mainactivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
