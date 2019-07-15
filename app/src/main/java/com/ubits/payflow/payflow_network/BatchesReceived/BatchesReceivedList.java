package com.ubits.payflow.payflow_network.BatchesReceived;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.ubits.payflow.payflow_network.AgentsList.AgentsList;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchesReceivedList extends AppCompatActivity implements View.OnClickListener{

    private BatchesReceivedListAdapter adapter;
    List<BatchesReceivedResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    public ListView listView;
    Button btnstts;

    private void populateListView(List<Body> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());
        bodyArrayList1 = batchesReceivedResponseList;
        adapter = new BatchesReceivedListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_received_list);

        listView = (ListView) findViewById(R.id.batches_received_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnstts = (Button)findViewById(R.id.btnreceive);
        btnstts.setVisibility(View.INVISIBLE);
        batchesGet();
        btnstts.setOnClickListener(this);
    }
    private void batchesGet() {

        Log.d("PNK", "BATCHES Received");

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
                    if(status.equals("RECEIVED"))
                    {
                        bodyArrayList1.add(list.get(i));
                        populateListView(bodyArrayList1);
                        btnstts.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(BatchesReceivedList.this, "No Data are received by You!", Toast.LENGTH_SHORT).show();
                    }
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
                Toast.makeText(BatchesReceivedList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());

        AlertDialog alertDialog = new AlertDialog.Builder(BatchesReceivedList.this).create();
        alertDialog.setMessage("Are you want to send this stock to the agent");

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                int size = listView.getCount();
                bodybatchesstring = new String[size];

                for (int j = 0; j <bodyArrayList1.size(); j++) {

                    if (bodyArrayList1.get(j).isIschecked()) {
                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                String[] batches = new String[bodyArrayListbatches.size()];

                for(int j=0; j<bodyArrayListbatches.size();j++)
                {
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

                Intent intent=new Intent(BatchesReceivedList.this, AgentsList.class);
                intent.putExtra("allocation_stock",batches);
                startActivity(intent);
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.cancel();

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
}

