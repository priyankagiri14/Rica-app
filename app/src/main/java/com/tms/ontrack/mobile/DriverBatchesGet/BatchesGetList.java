package com.tms.ontrack.mobile.DriverBatchesGet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.AllocationStatus.AllocationStatusResponse;

import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchesGetList extends AppCompatActivity implements View.OnClickListener ,SearchView.OnQueryTextListener, LocationListener {


    SearchView searchView;
    private BatchesGetListAdapter adapter;
    List<BatchesGetResponse> list2 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    TextView assignedbatches,nobatchesget;
    public ListView listView;
    ProgressDialog progressBar;
    int count = 0;
    LocationManager locationManager;
    double latitude, longitude;

    Button btnstatus;


        private void populateListView(List<Body> batchesGetResponseList)
        {
            Log.d("PNK", "POPULATELIST");
            Log.d("PNK", list2.toString());

            bodyArrayList1 = batchesGetResponseList;
            adapter = new BatchesGetListAdapter(this,bodyArrayList1);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            progressBar.cancel();
            /*searchView=findViewById(R.id.searchview);
            searchView.setOnQueryTextListener(this);
            setupSearchView();*/
        }

    private void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            Log.d("PNK", "ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_get_list);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        listView = (ListView) findViewById(R.id.batches_get_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        assignedbatches = (TextView)findViewById(R.id.driver_assigned_batches);
        nobatchesget = (TextView)findViewById(R.id.nobatchesget);
        btnstatus = (Button)findViewById(R.id.btnstatus);
        btnstatus.setVisibility(View.INVISIBLE);
        assignedbatches.setVisibility(View.INVISIBLE);
        getLocation();
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
                }
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);*/
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
        Call<BatchesGetResponse> batchesGetResponseCall = web_interface1.requestBatchesGet(0, 0);
        batchesGetResponseCall.enqueue(new Callback<BatchesGetResponse>() {
            @Override
            public void onResponse(Call<BatchesGetResponse> call, Response<BatchesGetResponse> response) {
                List<Body> list1 = new ArrayList<>();
                assert response.body() != null;
                list1 = response.body().getBody();

                for(int i=0;i<list1.size();i++)
                {
                    String status = list1.get(i).getStatus();
                    String valuesim = String.valueOf(list1.get(i).isValueSim());
                    if(status.equals("PENDING") && valuesim.equals("true"))
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
            public void onFailure(Call<BatchesGetResponse> call, Throwable t) {
                Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());

        AlertDialog alertDialog = new AlertDialog.Builder(BatchesGetList.this).create();
        alertDialog.setMessage("Please Confirm..?");

        Pojo pojo = new Pojo();

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
                    Toast.makeText(BatchesGetList.this, "Please Select any Batch for Receiving", Toast.LENGTH_SHORT).show();
                } else {
                    String[] batches = new String[bodyArrayListbatches.size()];
                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                    }
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    pojo.setStatus(stts);
                    pojo.setBatches(batches);
                    Call<AllocationStatusResponse> call = web_interface.requestAllocationStatus(latitude,longitude,pojo);
                    //exeuting the service
                    call.enqueue(new Callback<AllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(BatchesGetList.this, Stocks_dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
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
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                String[] batches = new String[bodyArrayList1.size()];
                for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                    batches[j] = bodyArrayListbatches.get(j);
                    count++;
                }
                if (count == 0) {
                    Toast.makeText(BatchesGetList.this, "Please Select any Batch for Decline", Toast.LENGTH_SHORT).show();
                } else {
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    pojo.setStatus(status);
                    pojo.setBatches(batches);
                    pojo.setReason(reason);
                    Call<AllocationStatusResponse> call = web_interface.requestAllocationStatus(latitude,longitude,pojo);
                    //exeuting the service
                    Log.d("agentlogin: ", call.toString());
                    call.enqueue(new Callback<AllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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

        if(!((Activity) BatchesGetList.this).isFinishing())
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
                    startActivity(new Intent(BatchesGetList.this, Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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