package com.ubits.payflow.payflow_network.AgentBatchesReceived;

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
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.AgentBatchesGet.AgentBatchesGet;
import com.ubits.payflow.payflow_network.BatchesReceived.BatchesReceivedResponse;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentBatchesReceived extends AppCompatActivity implements View.OnClickListener{

    private AgentBatchesReceivedListAdapter adapter;
    List<AgentBatchesReceivedResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    public ListView listView;
    TextView agentbatchesreceived,noagentbatchesreceived;
    Button agentbtnreceive;


    private void populateListView(List<Body> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());
        bodyArrayList1 = batchesReceivedResponseList;
        adapter = new AgentBatchesReceivedListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_batches_received);

        listView = (ListView) findViewById(R.id.agent_batches_received_listview);
        agentbatchesreceived = (TextView)findViewById(R.id.agent_received_batches);
        noagentbatchesreceived = (TextView)findViewById(R.id.noagentbatchesreceived);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        agentbtnreceive = (Button)findViewById(R.id.agentbtnreceived);
        agentbtnreceive.setVisibility(View.INVISIBLE);
        agentbatchesreceived.setVisibility(View.INVISIBLE);
        batchesReceived();
        agentbtnreceive.setOnClickListener(this);
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
                    if(status.equals("RECEIVED"))
                    {
                        bodyArrayList1.add(list.get(i));
                        populateListView(bodyArrayList1);
                        agentbtnreceive.setVisibility(View.VISIBLE);
                        agentbatchesreceived.setVisibility(View.VISIBLE);
                    }
                }

                if(listView.getCount() == 0)
                {
                    noagentbatchesreceived.setVisibility(View.VISIBLE);
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
                Toast.makeText(AgentBatchesReceived.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d("PNK", "ONCLICK");
        Log.d("PNK", ""+v.getId());


        Toast.makeText(AgentBatchesReceived.this, "No Functionality here for as of now", Toast.LENGTH_SHORT).show();

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
}
