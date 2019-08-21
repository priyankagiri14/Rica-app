package com.tms.ontrack.mobile.OpenBatchesResponse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.Agent.Sim_allocation;
import com.tms.ontrack.mobile.OpenCloseBatches.OpenCloseResponse;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenedBatchesActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    public static String message;
    Button btnstatus1;
    String id,value;
    int count = 0;
    int status_success=0,status_available=0,status_failed=0,listsize = 0,listsize1 = 0;
    String batchid,batchvalue;
    TextView agentopenedbatch,noserialsget;

    private OpenedBatchesListAdapter adapter;
    List<OpenedBatchesResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    public ListView listView;
    TextView moreinfo;
    Button btnstopcomplete;
    ProgressDialog progressBar;
    LocationManager locationManager;
    double latitude,longitude;

    private void populateListView(List<Body> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());
        bodyArrayList1 = batchesReceivedResponseList;
        adapter = new OpenedBatchesListAdapter(this,bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
        listviewclick();
    }

    private void listviewclick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = bodyArrayList1.get(position).getNumber();
                Log.d("Value is: ",value);

                Intent intent = new Intent(OpenedBatchesActivity.this, Sim_allocation.class);
                intent.putExtra("simcard",value);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_batches);

        moreinfo = (TextView)findViewById(R.id.moreInfo);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        btnstopcomplete = (Button)findViewById(R.id.agentopenedbatches);
        batchid = Pref.getBatchID(this);
        batchvalue = Pref.getBatchNo(this);
        listView = (ListView)findViewById(R.id.agent_batches_opened_listview);
        agentopenedbatch = (TextView)findViewById(R.id.agent_opened_batch);
        noserialsget= (TextView)findViewById(R.id.noserialsget);
        agentopenedbatch.setText(batchvalue);
        getLocation();
        /*locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);*/
        btnstopcomplete.setOnClickListener(this);
        moreinfo.setOnClickListener(this);


        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<SerialsGet> serialsGet = web_interface.requestSerials(Integer.parseInt(batchid),"all");
        serialsGet.enqueue(new Callback<SerialsGet>() {
            @Override
            public void onResponse(Call<SerialsGet> call, Response<SerialsGet> response) {
//

                List<Body> list = new ArrayList<>();
                assert response.body() != null;
                list = response.body().getBody();
                listsize1 = list.size();
                Log.d("onActive: ",response.body().getBody().get(0).getNumber());

                for(int i=0;i<list.size();i++)
                {
                    String status = response.body().getBody().get(i).getStatus();
                    if(status.equals("AVAILABLE")) {
                        bodyArrayList1.add(list.get(i));
                    }
                }
                listsize = bodyArrayList1.size();
                populateListView(bodyArrayList1);

                if(listView.getCount() == 0)
                {
                    noserialsget.setVisibility(View.VISIBLE);
                    progressBar.cancel();
                }
                for(int i=0;i<list.size();i++)
                {
                    String status = list.get(i).getStatus();
                    switch (status)
                    {
                        case "AVAILABLE":
                        {
                            status_available = status_available+1;
                            break;
                        }
                        case "FAILED":
                        {
                            status_failed = status_failed+1;
                            break;
                        }
                        case "SUCCESS":
                        {
                            status_success = status_success+1;
                            break;
                        }
                        default:
                        {

                        }
                    }
                }
                //List<SerialsGetResponse> batchesReceivedResponseList1 = (List<SerialsGetResponse>) new SerialsGetResponse();
//                String status = response.body().getSuccess().toString();
//                Log.d("Serials Get: ",status);
//
//                List<Body> body = response.body().getBody();
//
//                List<String> strings = new ArrayList<>();
//
//                for(int i = 0;i<bodystring.size();i++)
//                {
//                    Log.d("ListView ",bodystring.get(i));
//                    strings.add(bodystring.get(i));
//                }
//                //Log.d("onResponse: ",);
//                populateListView(strings);
            }

            @Override
            public void onFailure(Call<SerialsGet> call, Throwable t) {
                Toast.makeText(OpenedBatchesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLocation() {

        try{
            progressBar.show();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
            progressBar.dismiss();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.agentopenedbatches) {

//            AlertDialog alertDialog = new AlertDialog.Builder(OpenedBatchesActivity.this).create();
//            alertDialog.setMessage("Please Confirm the Batch Status");
//            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Stop", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
                    AlertDialog alertDialog1 = new AlertDialog.Builder(OpenedBatchesActivity.this).create();
                    alertDialog1.setMessage("Are You Sure You want to Stop This Batch");
                    alertDialog1.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (status_success == listsize) {
                                String status = "stop";
                                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                                try {
                                    JSONObject paramObject = new JSONObject();
                                    paramObject.put("batchNo", batchvalue);


                                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());

                                    Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(latitude,longitude,status, body);
                                    openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
                                        @Override
                                        public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
                                            String message = response.body().getMessage();
                                            Toast.makeText(OpenedBatchesActivity.this, message, Toast.LENGTH_SHORT).show();
                                            batchid = "";
                                            Pref.setBatchID(MyApp.getContext(), batchid);
                                            progressBar.cancel();
                                            Intent intent = new Intent(OpenedBatchesActivity.this, Agent_Mainactivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
                                            progressBar.cancel();
                                            Toast.makeText(OpenedBatchesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                progressBar.cancel();
                                AlertDialog alertDialog = new AlertDialog.Builder(OpenedBatchesActivity.this).create();
                                alertDialog.setMessage("Please Sell all the Sims before Stop this Batch..");
                                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Got It!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.cancel();
                                    }
                                });
                                alertDialog.show();                            }
                        }
                    });
                    alertDialog1.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressBar.cancel();
                            alertDialog1.cancel();
                        }
                    });
                    alertDialog1.show();
                    //Toast.makeText(OpenedBatchesActivity.this, "You are done with this Batch", Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Complete", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
                   // progressBar.show();

//                    AlertDialog alertDialog2 = new AlertDialog.Builder(OpenedBatchesActivity.this).create();
//                    alertDialog2.setMessage("Are You Sure You want to Complete this Batch");
//                    alertDialog2.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if (status_success == listsize) {
//                                String status = "complete";
//                                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                                try {
//                                    JSONObject paramObject = new JSONObject();
//                                    paramObject.put("batchNo", batchvalue);
//                                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                                    Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status, body);
//                                    openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
//                                        @Override
//                                        public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
//                                            String message = response.body().getMessage();
//                                            Toast.makeText(OpenedBatchesActivity.this, message, Toast.LENGTH_SHORT).show();
//                                            batchid = "";
//                                            Pref.setBatchID(MyApp.getContext(), batchid);
//                                            progressBar.cancel();
//                                            Intent intent = new Intent(OpenedBatchesActivity.this, Agent_Mainactivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
//                                            progressBar.cancel();
//                                            Toast.makeText(OpenedBatchesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                } catch (JSONException e) {
//                                    progressBar.cancel();
//                                    Toast.makeText(OpenedBatchesActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                progressBar.cancel();
//                                Toast.makeText(OpenedBatchesActivity.this, "Please Sell all the Sims before complete this batch", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                    alertDialog2.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            progressBar.cancel();
//                            alertDialog2.cancel();
//                        }
//                    });
//                    alertDialog2.show();
                    //Toast.makeText(OpenCloseActivity.this, "You are completed with this batch", Toast.LENGTH_SHORT).show();
//                }
//            });
//            alertDialog.show();
        }

        if(v.getId() == R.id.moreInfo)
        {
            AlertDialog alertDialog2 = new AlertDialog.Builder(OpenedBatchesActivity.this).create();
            alertDialog2.setMessage("If this Batch is Lost or Misplaced by You, then you have to Contact your Driver to stop this Batch");
            alertDialog2.setButton(DialogInterface.BUTTON_POSITIVE, "GOT IT!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog2.cancel();
                }
            });
            alertDialog2.show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("onLocationChanged: ", String.valueOf(latitude));
        Log.d("onLocationChanged: ", String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        if(!((Activity) OpenedBatchesActivity.this).isFinishing())
        {
            //show dialog
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Open GPS Settings?");
            alertDialog.setCancelable(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if(Settings.ACTION_LOCATION_SOURCE_SETTINGS.equals(true))
                    {
                        alertDialog.dismiss();
                    }
                    count++;
                }
            });
            if(count>0)
            {
                alertDialog.dismiss();
                count = 0;
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(OpenedBatchesActivity.this, Agent_Mainactivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            alertDialog.show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Enable Location")
//                    .setMessage("Open GPS Settings")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
//                    .setNegativeButton("Cancel",
//                            (dialog, id) -> startActivity(new Intent(DriverAttendance.this,Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
//            alert = builder.create();
//            alert.show();
        }
    }
}
