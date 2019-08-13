package com.tms.ontrack.mobile.Agent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.AgentBatchesGet.AppDatabase;
import com.tms.ontrack.mobile.AgentBatchesGet.Batches;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitClient;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import info.androidhive.fontawesome.FontTextView;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanBatch extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener  {


    private static final String TAG = "ScanBatch";
    Button activatebacthes,scanbtn;
    private ListView listViewScannedBatches,listView2;
    private String mResult=null;
    String[] ListElements = new String[]{
    };
    ArrayList< String > ListElementsArrayList;
    ArrayAdapter< String > adapter;
    String[] batches;
    Spinner regionspinner1;
    private String region;
    ArrayAdapter arrayAdapter;
    String arraylist;
    private SearchView searchView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_batch);

        regionspinner1 = findViewById(R.id.regionspinner1);
        activatebacthes=(findViewById(R.id.activatebacthes));
        scanbtn = findViewById(R.id.scanbtn);
        arraylist = Pref.getBatchArray(this);
        listViewScannedBatches= findViewById(R.id.listViewScannedBatches);
        listView2 = findViewById(R.id.search_results_list);
        searchView = findViewById(R.id.searchview);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        activatebacthes.setOnClickListener(this);
        scanbtn.setOnClickListener(this);
        /*regionspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Toast.makeText(ScanBatch.this,"Please Select the Region",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    region = regionspinner1.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }});*/
        ListElementsArrayList = new ArrayList< String >();


        adapter = new ArrayAdapter < String >
                (ScanBatch.this, R.layout.scan_batches_layout,
                        R.id.tv_batches,
                        ListElementsArrayList);

        listViewScannedBatches.setAdapter(adapter);
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            List arrayList = new ArrayList();
            arrayList = db.batchesInterface().batchestabledata(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            return false;
        }

    };
    private void initiateScan() {
        ZxingOrient integrator = new ZxingOrient(this);
        integrator.setVibration(true);
        integrator.setBeep(true);
        integrator.initiateScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: is called");
        if(batches!=null)
        {
            Log.d(TAG,"batches:"+batches.length);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scanbtn)
        {
            initiateScan();
        }
        if(v.getId() == R.id.activatebacthes)
        {
            if(regionspinner1.getSelectedItemPosition()!=0 && batches!=null)
            {
                region = regionspinner1.getSelectedItem().toString();
                BatchesAPICall(batches,region);
            }
            else if(regionspinner1.getSelectedItemPosition() == 0 && batches != null)
                {
                    Toast.makeText(ScanBatch.this,"Please Select the Region",Toast.LENGTH_SHORT).show();
                }
            else{
                Toast.makeText(ScanBatch.this,"Please Scan Batches from Barcode Scanner icon",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void BatchesAPICall(String[] batches,String region) {


            ScanPojo scanPojo = new ScanPojo();
            scanPojo.setBatches(batches);
            Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
            Call<ScanBatchesResponse> scanBatchesResponseCall = web_interface.requestScanBatches(region,batches);
            scanBatchesResponseCall.enqueue(new Callback<ScanBatchesResponse>() {
                @Override
                public void onResponse(Call<ScanBatchesResponse> call, Response<ScanBatchesResponse> response) {
                    String status = response.body().getSuccess().toString();
                    Log.d(TAG, "onResponse: "+status);
                    if(status.equals("true"))
                    {
                        String message = response.body().getMessage();
                        Toast.makeText(ScanBatch.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanBatch.this, Agent_Mainactivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        String message = response.body().getMessage();
                        Toast.makeText(ScanBatch.this, message, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ScanBatchesResponse> call, Throwable t) {

                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ZxingOrientResult result = ZxingOrient.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.i("SMW", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.i("SMW", "Scanned");
                mResult = result.getContents();
                if(ListElementsArrayList.contains(mResult))
                {
                    //find duplicate values
                    Toasty.info(this, "Thie value is already added", Toast.LENGTH_SHORT).show();
                }
                else {
                    //adding only unique values
                    ListElementsArrayList.add(mResult);
                    batches = new String[ListElementsArrayList.size()];
                    for (int j = 0; j < ListElementsArrayList.size(); j++) {
                        batches[j] = ListElementsArrayList.get(j);
                    }
                    Log.d(TAG, "onActivityResult: batches" + "\n" + batches);

                    //Log.d(TAG, "onActivityResult: result"+ListElementsArrayList);
                    adapter.notifyDataSetChanged();
                }

            }

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}

