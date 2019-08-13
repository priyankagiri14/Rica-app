package com.tms.ontrack.mobile.AgentsList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tms.ontrack.mobile.AllocationCreateResponse.AllocationCreate;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsList extends AppCompatActivity implements View.OnClickListener {

    private AgentsListAdapter adapter;
    List<AgentsListResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    TextView assignagents,noagentslist;
    public ListView listView;
    Button btnstatus;
    ProgressDialog progressBar;
    int count =0;

    private void populateListView(List<Body> batchesGetResponseList) {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());

        bodyArrayList1 = batchesGetResponseList;
        adapter = new AgentsListAdapter(this, bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_list);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        listView = (ListView) findViewById(R.id.batches_get_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnstatus = (Button) findViewById(R.id.btnstatus);
        assignagents = (TextView)findViewById(R.id.assign_agents);
        noagentslist = (TextView)findViewById(R.id.noagentslist);
        btnstatus.setVisibility(View.INVISIBLE);
        assignagents.setVisibility(View.INVISIBLE);
        agentsGet();
        btnstatus.setOnClickListener(this);
    }

    private void agentsGet() {
        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentsListResponse> agentsListResponseCall = web_interface.requestAgentsList();

        agentsListResponseCall.enqueue(new Callback<AgentsListResponse>() {
            @Override
            public void onResponse(Call<AgentsListResponse> call, Response<AgentsListResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<Body> list = new ArrayList<>();
                    assert response.body() != null;
                    list = response.body().getBody();

                    String[] id = new String[bodyArrayList1.size()];

                    for (int i = 0; i < list.size(); i++) {
                        bodyArrayList1.add(list.get(i));
                        populateListView(bodyArrayList1);
                        assignagents.setVisibility(View.VISIBLE);
                        btnstatus.setVisibility(View.VISIBLE);
//                        list1.add(response.body());
//                        populateListView(list1.get(0).getBody());
                    }
                    if(listView.getCount() == 0)
                    {
                        noagentslist.setVisibility(View.VISIBLE);
                        progressBar.cancel();
                        //Toast.makeText(AgentsList.this, "No Data is Received by You..!", Toast.LENGTH_SHORT).show();
                    }
                }
//                for (int i =0; i <list.size(); i++) {
//                    list1.add(response.body());
//                }

                Log.d("PNK", "LIST1");
                Log.d("PNK", list1.toString());

//                populateListView(list1.get(0).getBody());

                Log.d("Batches", "onResponse: " + list1);
//                    if (response.body() != null) {
//                        list = response.body().getBody();
//                        for (int i = 0; i < list.size(); i++) {
//                            String agents = list.get(i).getName();
//                            Log.d("Agents: ", agents);
//
//                        }
//                    }
            }

            @Override
            public void onFailure(Call<AgentsListResponse> call, Throwable t) {
                Toast.makeText(AgentsList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        progressBar.show();

        int count = 0;
        Pojo pojo = new Pojo();
        String id = "";
        String[] batches = new String[bodyArrayListbatches.size()];
        batches = getIntent().getStringArrayExtra("allocation_stock");

        for (int j = 0; j < bodyArrayList1.size(); j++) {

            if (bodyArrayList1.get(j).isIschecked()) {
                //bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                id = bodyArrayList1.get(j).getId().toString();
                count++;
            }
        }
        if(count == 0)
        {
            Toast.makeText(AgentsList.this,"Select any Agent to Allocate the Batches",Toast.LENGTH_SHORT).show();
            progressBar.cancel();

        }
        else if (count > 1) {
            Toast.makeText(this, "Sorry! can't assign Stock to more than one Agent", Toast.LENGTH_SHORT).show();
            progressBar.cancel();
        }
        else {
            Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
            pojo.setBatches(batches);
            Call<AllocationCreate> allocationCreateCall = web_interface.requestAllocationCreate(id, batches);
            allocationCreateCall.enqueue(new Callback<AllocationCreate>() {
                @Override
                public void onResponse(Call<AllocationCreate> call, Response<AllocationCreate> response) {
                    String message = response.body().getMessage();
                    Toast.makeText(AgentsList.this, message, Toast.LENGTH_SHORT).show();
                    progressBar.cancel();
                    Intent intent = new Intent(AgentsList.this, Stocks_dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<AllocationCreate> call, Throwable t) {
                    Toast.makeText(AgentsList.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
//        Toast.makeText(this, "Nothing is here....", Toast.LENGTH_SHORT).show();
        }
    }
}