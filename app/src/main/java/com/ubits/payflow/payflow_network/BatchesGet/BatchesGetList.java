package com.ubits.payflow.payflow_network.BatchesGet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.AllocationGet.AllocationGet;
import com.ubits.payflow.payflow_network.AllocationGet.AllocationGetResponse;
import com.ubits.payflow.payflow_network.AllocationStatus.AllocationStatusResponse;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.MyApp;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Utils.Pref;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchesGetList extends AppCompatActivity {


    private BatchesGetListAdapter adapter;
    private ListView listView;


    private void populateListView(List<BatchesGetResponse> batchesGetResponseList)
    {

        adapter = new BatchesGetListAdapter(this,batchesGetResponseList);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_get_list);
        listView = (ListView) findViewById(R.id.batches_get_listview);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BatchesGetList.this);
        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String id = Pref.getAllocationId(BatchesGetList.this);
                String status = "ACCEPTED";
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);

                try {
                    JSONObject paramObject = new JSONObject();
                    paramObject.put("id", id);
                    paramObject.put("status", status);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());

                    Call<AllocationStatusResponse> allocationStatusResponseCall = web_interface.requestAllocationStatus(body, id);
                    allocationStatusResponseCall.enqueue(new Callback<AllocationStatusResponse>() {
                        @Override
                        public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {

                            String message = response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            batchesGet();
//                            Intent batchesGetIntent = new Intent(BatchesGetList.this, BatchesGetList.class);
//                            startActivity(batchesGetIntent);
                        }

                        @Override
                        public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You Declined the Batches", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AllocationGetResponse> allocationGetResponseCall = web_interface.requestAllocationGet(0, 0);
        allocationGetResponseCall.enqueue(new Callback<AllocationGetResponse>() {

            @Override
            public void onResponse(Call<AllocationGetResponse> call, Response<AllocationGetResponse> response) {

                List<com.ubits.payflow.payflow_network.AllocationGet.Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    Log.d("AllocationGet", "onResponse: " + list.get(i).getMessage());
                    String allocation_id = list.get(i).getId().toString();
                    Pref.putAllocationId(MyApp.getContext(), allocation_id);
                    String message = list.get(i).getMessage();
                    list1.add(list.get(i).getId().toString());
                    TextView myMsg = new TextView(BatchesGetList.this);
                    myMsg.setText(message);
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(20);
                    alertDialog.setCustomTitle(myMsg);
                    alertDialog.show().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                }

                Log.d("Batches", "onResponse: " + list1);
            }

            @Override
            public void onFailure(Call<AllocationGetResponse> call, Throwable t) {

            }
        });


//        listView = (ListView)findViewById(R.id.batches_get_listview);
//        adapter = new BatchesGetListAdapter(this);
//        listView.setAdapter(adapter);

        //final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();
    }
    private void batchesGet(){
        Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
        Call<BatchesGetResponse> batchesGetResponseCall = web_interface1.requestBatchesGet(0, 0);
        batchesGetResponseCall.enqueue(new Callback<BatchesGetResponse>() {
            @Override
            public void onResponse(Call<BatchesGetResponse> call, Response<BatchesGetResponse> response) {
                //populateListView(response.body().getBody().get(0).getBatchNo());
                List<Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();
                List<BatchesGetResponse> list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    list1.add(response.body());
                }
                populateListView(list1);

                Log.d("Batches", "onResponse: " + list1);
            }

            @Override
            public void onFailure(Call<BatchesGetResponse> call, Throwable t) {
                Toast.makeText(BatchesGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private List<ListViewItemDTO> getInitViewItemDtoList() {
//        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();
//
//        //int length = itemTextArr.length;
//
//        //for(int i=0;i<length;i++)
//        {
//            //String itemText = itemTextArr[i];
//
//            ListViewItemDTO dto = new ListViewItemDTO();
//            dto.setChecked(false);
//            //dto.setItemText(itemText);
//
//            ret.add(dto);
//        }
//
//        return ret;
//    }
//
//    @Override
//    public void onResponse(Call<BatchesGetResponse> call, Response<BatchesGetResponse> response) {
//        if(response.isSuccessful() && response.code() == 200)
//        {
//            if(response.body()!= null)
//            {
//                list = response.body().getBody();
//                for(int i=0; i<list.size();i++)
//                {
//                    String stock = list.get(i).getBatchNo();
//                    Log.d("Stock: ",stock);
//
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onFailure(Call<BatchesGetResponse> call, Throwable t) {
//        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
//    }
}
