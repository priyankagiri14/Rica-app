package com.tms.ontrack.mobile.BatchesReceived;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tms.ontrack.mobile.AgentsList.AgentsList;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NormalBatchesReceivedList extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener{

    private BatchesReceivedListAdapter adapter;
    List<BatchesReceivedResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    public ListView listView;
    TextView batchesreceivedtext,nobatchesreceived;
    Button btnstts;
    ProgressDialog progressBar;
    int count =0;
    SearchView searchView;

    private void populateListView(List<Body> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());
        bodyArrayList1 = batchesReceivedResponseList;
        adapter = new BatchesReceivedListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        searchView.setVisibility(View.VISIBLE);
        progressBar.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_received_list);

        searchView = (SearchView) findViewById(R.id.driverbatchsearch);
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

        listView = (ListView) findViewById(R.id.batches_received_listview);
        listView.setTextFilterEnabled(true);
        batchesreceivedtext = (TextView)findViewById(R.id.driver_batches_received);
        nobatchesreceived = (TextView)findViewById(R.id.nobatchesrc);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnstts = (Button)findViewById(R.id.btnreceive);
        btnstts.setVisibility(View.INVISIBLE);
        batchesreceivedtext.setVisibility(View.INVISIBLE);
        batchesGet();
        btnstts.setOnClickListener(this);
    }
    private void batchesGet() {

        Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
        Call<BatchesReceivedResponse> batchesGetResponseCall = web_interface1.requestBatchesReceived(0, 0);
        batchesGetResponseCall.enqueue(new Callback<BatchesReceivedResponse>() {
            @Override
            public void onResponse(Call<BatchesReceivedResponse> call, Response<BatchesReceivedResponse> response) {
                List<Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();

                for(int i=0;i<list.size();i++)
                {
                    String status = response.body().getBody().get(i).getStatus();
                    String valueSim = String.valueOf(response.body().getBody().get(i).isValueSim());
                    if(status.equals("RECEIVED") && valueSim.equals("false"))
                    {
                        bodyArrayList1.add(list.get(i));
                        populateListView(bodyArrayList1);
                        btnstts.setVisibility(View.VISIBLE);
                        batchesreceivedtext.setVisibility(View.VISIBLE);
                    }
                }

                if(listView.getCount() == 0)
                {
                    nobatchesreceived.setVisibility(View.VISIBLE);
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
            public void onFailure(Call<BatchesReceivedResponse> call, Throwable t) {
                Toast.makeText( NormalBatchesReceivedList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());

        AlertDialog alertDialog = new AlertDialog.Builder(NormalBatchesReceivedList.this).create();
        alertDialog.setMessage("Do you want to allocate the stock to Agent");

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                progressBar.show();

                int size = listView.getCount();
                bodybatchesstring = new String[size];

                for (int j = 0; j < bodyArrayList1.size(); j++) {

                    if (bodyArrayList1.get(j).isIschecked()) {
                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                        count++;
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                if (count == 0) {
                    progressBar.cancel();
                    Toast.makeText(NormalBatchesReceivedList.this, "Select any Batch for Allocation", Toast.LENGTH_SHORT).show();
                } else {
                    String[] batches = new String[bodyArrayListbatches.size()];

                    for (int j = 0; j < bodyArrayListbatches.size(); j++) {
                        batches[j] = bodyArrayListbatches.get(j);
                    }
//                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                pojo.setStatus(status);
//                pojo.setBatches(batches);
//                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
//                //exeuting the service
//                call.enqueue(new Callback<AllocationStatusResponse>() {
//                    @Override
//                    public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {
//
//                        String message = response.body().getMessage();
//                        Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
//                        Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

                    progressBar.cancel();
                    Intent intent = new Intent(NormalBatchesReceivedList.this, AgentsList.class);
                    intent.putExtra("allocation_stock", batches);
                    startActivity(intent);
                }
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();
                progressBar.cancel();

//                String reason = "These Batches are assigned mistakenly";
//                String status = "DECLINED";
//                Log.d("PNK", "Here I am");
//                int size = listView.getCount();
//
//                for (int j = 0; j <bodyArrayList1.size(); j++) {
//
//                    if (bodyArrayList1.get(j).isIschecked()) {
//                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
//                        batches[j] = bodyArrayList1.get(j).getBatchNo();
//                    }
//                }
//                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                pojo.setStatus(status);
//                pojo.setBatches(batches);
//                pojo.setReason(reason);
//                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
//                //exeuting the service
//                Log.d("agentlogin: ",call.toString());
//                call.enqueue(new Callback<AllocationStatusResponse>() {
//                    @Override
//                    public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {
//
//                        String message = response.body().getMessage();
//                        Toast.makeText(BatchesGetList.this, message, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
//                        Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//                Intent intent=new Intent(BatchesGetList.this, MainActivity.class);
//                intent.putExtra("batcheslist",bodyArrayList);
//                startActivity(intent);
//
            }
        });

        alertDialog.show();
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
