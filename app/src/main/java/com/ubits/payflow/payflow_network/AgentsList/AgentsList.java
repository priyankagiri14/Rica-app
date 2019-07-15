package com.ubits.payflow.payflow_network.AgentsList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetListAdapter;
import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetResponse;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

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
    public ListView listView;
    Button btnstatus;

    private void populateListView(List<Body> batchesGetResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());

        bodyArrayList1 = batchesGetResponseList;
        adapter = new AgentsListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_list);

        listView = (ListView) findViewById(R.id.batches_get_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnstatus = (Button) findViewById(R.id.btnstatus);
        agentsGet();
        btnstatus.setOnClickListener(this);
    }

    private void agentsGet(){
        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentsListResponse> agentsListResponseCall = web_interface.requestAgentsList();

        agentsListResponseCall.enqueue(new Callback<AgentsListResponse>() {
            @Override
            public void onResponse(Call<AgentsListResponse> call, Response<AgentsListResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<Body> list = new ArrayList<>();
                    assert response.body() != null;
                    list = response.body().getBody();

                    for(int i=0;i<list.size();i++)
                    {
                            list1.add(response.body());
                            populateListView(list1.get(0).getBody());
                        }
                    }
//                for (int i =0; i <list.size(); i++) {
//                    list1.add(response.body());
//                }

                    Log.d("PNK", "LIST1");
                    Log.d("PNK",  list1.toString());

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

        String[] batches = new String[bodyArrayList1.size()];
        batches = getIntent().getStringArrayExtra("allocation_stock");
        Toast.makeText(this, "Nothing is here....", Toast.LENGTH_SHORT).show();
    }
}
