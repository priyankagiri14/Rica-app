package com.ubits.payflow.payflow_network.OpenCloseBatches;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Adapter;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class OpenCloseActivity extends AppCompatActivity {

    ListView listView;
    Button btnstatus1;
    String id;
    OpenCloseListAdapter adapter;
    TextView batchnumber;

    private void populateListView(List<String> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
//        Log.d("PNK", list1.toString());
        //bodyArrayList1 = batchesReceivedResponseList;
        adapter = new OpenCloseListAdapter(this,batchesReceivedResponseList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
//        listviewclick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

        id = getIntent().getExtras().getString("batchid");
        String value = getIntent().getExtras().getString("value");

        btnstatus1 =(Button)findViewById(R.id.agentserials);
        listView = (ListView)findViewById(R.id.agent_serials_received_listview);
        batchnumber = (TextView) findViewById(R.id.batch_number);
        batchnumber.setText(value);
        apiImplement();
        btnstatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(OpenCloseActivity.this).create();
                alertDialog.setMessage("Please Confirm Your Status");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OpenCloseActivity.this, "Batch is now opened for Working", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(OpenCloseActivity.this, "You are done with this Batch", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void apiImplement() {

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<SerialsGetResponse> serialsGetResponseCall = web_interface.requestSerialsGet(id);
        serialsGetResponseCall.enqueue(new Callback<SerialsGetResponse>() {
            @Override
            public void onResponse(Call<SerialsGetResponse> call, Response<SerialsGetResponse> response) {
//
                //List<SerialsGetResponse> batchesReceivedResponseList1 = (List<SerialsGetResponse>) new SerialsGetResponse();
                String status = response.body().getSuccess().toString();
                Log.d("Serials Get: ",status);

                String body = response.body().getBody();
                List<String> bodystring = Arrays.asList(body.split(","));
                List<String> strings = new ArrayList<>();

                for(int i = 0;i<bodystring.size();i++)
                {
                    Log.d("ListView ",bodystring.get(i));
                    strings.add(bodystring.get(i));
                }
                //Log.d("onResponse: ",);
                    populateListView(strings);
            }

            @Override
            public void onFailure(Call<SerialsGetResponse> call, Throwable t) {
                Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
