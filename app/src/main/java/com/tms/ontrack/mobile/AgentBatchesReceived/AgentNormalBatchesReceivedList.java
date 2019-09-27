package com.tms.ontrack.mobile.AgentBatchesReceived;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.OpenCloseBatches.OpenCloseActivity;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentNormalBatchesReceivedList extends AppCompatActivity implements View.OnClickListener,LocationListener, SearchView.OnQueryTextListener {

    private AgentBatchesReceivedListAdapter adapter;
    List<AgentBatchesReceivedResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String batchid[];
    public ListView listView;
    TextView agentbatchesreceived,noagentbatchesreceived;
    ProgressDialog progressBar;
    LocationManager locationManager;
    double latitude, longitude;
    int count = 0;
    SearchView searchView;

    private void populateListView(List<Body> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());
        bodyArrayList1 = batchesReceivedResponseList;
        adapter = new AgentBatchesReceivedListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        searchView.setVisibility(View.VISIBLE);
        progressBar.cancel();
        listviewclick();
    }

    private void listviewclick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String batchclickid = batchid[position];
                Log.d("onItemClick: ",batchclickid);

                String value = bodyArrayList1.get(position).getBatchNo();
                Log.d("Value is: ",value);

                Intent intent = new Intent(AgentNormalBatchesReceivedList.this, OpenCloseActivity.class);
                intent.putExtra("batchid",batchclickid);
                intent.putExtra("value",value);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_normal_batches_received_list);

        searchView = (SearchView) findViewById(R.id.agentnormalbatchsearch);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Batches");
        searchView.requestFocusFromTouch();
        searchView.setVisibility(View.INVISIBLE);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        listView = (ListView) findViewById(R.id.agent_batches_received_listview);
        listView.setTextFilterEnabled(true);
        agentbatchesreceived = (TextView) findViewById(R.id.agent_received_batches);
        noagentbatchesreceived = (TextView) findViewById(R.id.noagentbatchesreceived);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        agentbatchesreceived.setVisibility(View.INVISIBLE);
        getLocation();
        batchesReceived();

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

    private void batchesReceived(){

        Log.d("PNK", "BATCHES Received");

        Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentBatchesReceivedResponse> batchesGetResponseCall = web_interface1.requestAgentBatchesReceived(0, 0);
        batchesGetResponseCall.enqueue(new Callback<AgentBatchesReceivedResponse>() {
            @Override
            public void onResponse(Call<AgentBatchesReceivedResponse> call, Response<AgentBatchesReceivedResponse> response) {
                List<Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();

                for(int i=0;i<list.size();i++)
                {
                    String status = response.body().getBody().get(i).getStatus();
                    String valueSim = String.valueOf(response.body().getBody().get(i).isValueSim());
                    if(status.equals("RECEIVED") && valueSim.equals("false"))
                    {
//
                        bodyArrayList1.add(list.get(i));
                        populateListView(bodyArrayList1);
                        agentbatchesreceived.setVisibility(View.VISIBLE);
                    }
                }

                String[] batches = new String[bodyArrayList1.size()];
                if(bodyArrayList1.size()>0) {
                    for (int j = 0; j < bodyArrayList1.size(); j++) {
                        batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                batchid = new String[bodyArrayList1.size()];
                for(int i = 0; i<bodyArrayList1.size();i++)
                {
                    batchid[i] = response.body().getBody().get(i).getId().toString();
                    Log.d("ID: ",batchid[i]);
                }

                if(listView.getCount() == 0)
                {
                    noagentbatchesreceived.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.INVISIBLE);
                    progressBar.cancel();
                    // Toast.makeText(BatchesReceivedList.this, "No Data is Received by You..!", Toast.LENGTH_SHORT).show();
                }
//                for (int i =0; i <list.size(); i++) {
//                    list1.add(response.body());
//                }

                Log.d("PNK", "LIST1");
                Log.d("PNK",  list1.toString());

//                populateListView(list1.get(0).getBody());

                Log.d("Batches", "onResponse: " + list1);
            }

            @Override
            public void onFailure(Call<AgentBatchesReceivedResponse> call, Throwable t) {
                Toast.makeText(AgentNormalBatchesReceivedList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());


        Toast.makeText(AgentNormalBatchesReceivedList.this, "No Functionality here for as of now", Toast.LENGTH_SHORT).show();

//        AlertDialog alertDialog = new AlertDialog.Builder(AgentBatchesReceived.this).create();
//        alertDialog.setMessage("Do you want to allocate the stock to Agent");
//
//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//
//                int size = listView.getCount();
//                bodybatchesstring = new String[size];
//
//                for (int j = 0; j <bodyArrayList1.size(); j++) {
//
//                    if (bodyArrayList1.get(j).isIschecked()) {
//                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
//                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
//                    }
//                }
//                String[] batches = new String[bodyArrayListbatches.size()];
//
//                for(int j=0; j<bodyArrayListbatches.size();j++)
//                {
//                    batches[j] = bodyArrayListbatches.get(j);
//                }
////                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
////                pojo.setStatus(status);
////                pojo.setBatches(batches);
////                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
////                //exeuting the service
////                call.enqueue(new Callback<AllocationStatusResponse>() {
////                    @Override
////                    public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {
////
////                        String message = response.body().getMessage();
////                        Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
////                        Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                });
//
//                Intent intent=new Intent(BatchesReceivedList.this, AgentsList.class);
//                intent.putExtra("allocation_stock",batches);
//                startActivity(intent);
//            }
//        });
//        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                alertDialog.cancel();
//
////                String reason = "These Batches are assigned mistakenly";
////                String status = "DECLINED";
////                Log.d("PNK", "Here I am");
////                int size = listView.getCount();
////
////                for (int j = 0; j <bodyArrayList1.size(); j++) {
////
////                    if (bodyArrayList1.get(j).isIschecked()) {
////                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
////                        batches[j] = bodyArrayList1.get(j).getBatchNo();
////                    }
////                }
////                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
////                pojo.setStatus(status);
////                pojo.setBatches(batches);
////                pojo.setReason(reason);
////                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
////                //exeuting the service
////                Log.d("agentlogin: ",call.toString());
////                call.enqueue(new Callback<AllocationStatusResponse>() {
////                    @Override
////                    public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {
////
////                        String message = response.body().getMessage();
////                        Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
////                    }
////
////                    @Override
////                    public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
////                        Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
////                    }
////                });
//
////                Intent intent=new Intent(BatchesGetList.this, MainActivity.class);
////                intent.putExtra("batcheslist",bodyArrayList);
////                startActivity(intent);
////
//            }
//        });
//
//        alertDialog.show();
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

        if(!((Activity) AgentNormalBatchesReceivedList.this).isFinishing()) {
            //show dialog
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Open GPS Settings?");
            alertDialog.setCancelable(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if (Settings.ACTION_LOCATION_SOURCE_SETTINGS.equals(true)) {
                        alertDialog.dismiss();
                    }
                    count++;
                }
            });
            if (count > 0) {
                alertDialog.dismiss();
                count = 0;
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AgentNormalBatchesReceivedList.this, Agent_Mainactivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }
}

