package com.ubits.payflow.payflow_network.BatchesGet;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.ubits.payflow.payflow_network.AllocationStatus.AllocationStatusResponse;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchesGetList extends AppCompatActivity implements View.OnClickListener{


    private BatchesGetListAdapter adapter;
    List<BatchesGetResponse> list1 = new ArrayList<>();
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
            adapter = new BatchesGetListAdapter(this,bodyArrayList1);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            Log.d("PNK", "ONCREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_get_list);
        listView = (ListView) findViewById(R.id.batches_get_listview);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        btnstatus = (Button)findViewById(R.id.btnstatus);
        batchesGet();
        btnstatus.setOnClickListener(this);
    }
    private void batchesGet(){

        Log.d("PNK", "BATCHESGET");

        Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
        Call<BatchesGetResponse> batchesGetResponseCall = web_interface1.requestBatchesGet(0, 0);
        batchesGetResponseCall.enqueue(new Callback<BatchesGetResponse>() {
            @Override
            public void onResponse(Call<BatchesGetResponse> call, Response<BatchesGetResponse> response) {
                List<Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();

                for(int i=0;i<list.size();i++)
                {
                    String status = response.body().getBody().get(i).getStatus();
                    if(status.equals("ASSIGNED"))
                    {
                        list1.add(response.body());
                        populateListView(list1.get(0).getBody());
                    }
                    else
                        {

                            btnstatus.setVisibility(View.INVISIBLE);
                            Toast.makeText(BatchesGetList.this, "No Data is Assigned to You!", Toast.LENGTH_SHORT).show();
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
        alertDialog.setMessage("Are you want to send this stock to the agent");

        Pojo pojo = new Pojo();
        String[] batches = new String[bodyArrayList1.size()];
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "RECEIVED", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                String status = "RECEIVED";
                Log.d("PNK", "Here I am");
                int size = listView.getCount();
                bodybatchesstring = new String[size];

                    for (int j = 0; j <bodyArrayList1.size(); j++) {

                        if (bodyArrayList1.get(j).isIschecked()) {
                            bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                            batches[j] = bodyArrayList1.get(j).getBatchNo();
                        }
                    }
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                pojo.setStatus(status);
                pojo.setBatches(batches);
                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
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

                Intent intent=new Intent(BatchesGetList.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "DECLINE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String reason = "These Batches are assigned mistakenly";
                String status = "DECLINED";
                Log.d("PNK", "Here I am");
                int size = listView.getCount();

                for (int j = 0; j <bodyArrayList1.size(); j++) {

                    if (bodyArrayList1.get(j).isIschecked()) {
                        bodyArrayListbatches.add(bodyArrayList1.get(j).getBatchNo());
                        batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                }
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                pojo.setStatus(status);
                pojo.setBatches(batches);
                pojo.setReason(reason);
                Call<AllocationStatusResponse> call= web_interface.requestAllocationStatus(pojo);
                //exeuting the service
                Log.d("agentlogin: ",call.toString());
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

//                Intent intent=new Intent(BatchesGetList.this, MainActivity.class);
//                intent.putExtra("batcheslist",bodyArrayList);
//                startActivity(intent);
//
            }
        });

        alertDialog.show();
    }
}