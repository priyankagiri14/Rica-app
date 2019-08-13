package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentBatchesGet extends AppCompatActivity implements View.OnClickListener{

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_batches_get);

        db = Room.databaseBuilder(MyApp.getContext(),AppDatabase.class,"batch")
                .allowMainThreadQueries()
                .build();
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
        batchesGet();
        agentbtnstatus.setOnClickListener(this);
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
                    if(status.equals("PENDING"))
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
                    int i = db.batchesInterface().count();
                    Log.d("onDb: ",String.valueOf(i));
                    /*Gson gson = new Gson();
                    String jsonbatcharray = gson.toJson(bodyArrayListbatches);
                    Log.d("BatchArray: ",jsonbatcharray);
                    Pref.putBatchArray(getApplicationContext(),jsonbatcharray);*/
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                    myPojo1.setStatus(stts);
                    myPojo1.setBatches(batches);
                    Call<AgentAllocationStatusResponse> callagent = web_interface.requestAgentAllocationStatus(myPojo1);
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
                    Call<AgentAllocationStatusResponse> callagent = web_interface.requestAgentAllocationStatus(myPojo1);
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
}
