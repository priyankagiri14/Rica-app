package com.tms.ontrack.mobile.OpenCloseBatches;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.OpenBatchesResponse.Body;
import com.tms.ontrack.mobile.OpenBatchesResponse.SerialsGet;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.AppDatabaseSerials;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.Serials;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenCloseActivity extends AppCompatActivity implements LocationListener {

    public static String message;
    ListView listView;
    Button btnstatus1;
    String id,value;
    String batchid;
    OpenCloseListAdapter adapter;
    TextView batchnumber;
    ProgressDialog progressBar;
    LocationManager locationManager;
    double longitude,latitude;
    int count = 0;
    List<Body> bodyArrayList = new ArrayList<>();
    private AppDatabaseSerials db;
    Serials serialsdata = new Serials();

    private void populateListView(List<String> batchesReceivedResponseList)
    {
        Log.d("PNK", "POPULATELIST");
//        Log.d("PNK", list1.toString());
        //bodyArrayList1 = batchesReceivedResponseList;
        adapter = new OpenCloseListAdapter(this,batchesReceivedResponseList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        progressBar.cancel();
//        listviewclick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

        db = Room.databaseBuilder(MyApp.getContext(),AppDatabaseSerials.class,"serials")
                .allowMainThreadQueries()
                .build();
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        id = getIntent().getExtras().getString("batchid");
        value = getIntent().getExtras().getString("value");

        btnstatus1 =(Button)findViewById(R.id.agentserials);
        listView = (ListView)findViewById(R.id.agent_serials_received_listview);
        batchnumber = (TextView) findViewById(R.id.batch_number);
        batchnumber.setText(value);
        getLocation();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//        }
//        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//        onLocationChanged(location);
        apiImplement();
        btnstatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(OpenCloseActivity.this).create();
                alertDialog.setMessage("Are you sure for Start with this Batch");

                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressBar.show();

                        String status = "start";
                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                        JSONObject paramObject = new JSONObject();
                        try {
//                            paramObject.put("action",status);
                            paramObject.put("batchNo", value);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());

                        Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(latitude,longitude,status,body);
                        openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
                            @Override
                            public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
                                if (response.isSuccessful() && response.code() == 200) {
                                    message = response.body().getMessage();
                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
                                    Pref.setBatchID(MyApp.getContext(), id);
                                    Pref.setBatchNo(MyApp.getContext(),value);
                                    progressBar.cancel();

                                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                                    Call<SerialsGetResponse> serialsGet = web_interface.requestSerialsGet(id);
                                    serialsGet.enqueue(new Callback<SerialsGetResponse>() {
                                        @Override
                                        public void onResponse(Call<SerialsGetResponse> call, Response<SerialsGetResponse> response) {

                                            String status = response.body().getSuccess().toString();
                                            Log.d("Serials Get: ",status);

                                            String body = response.body().getBody();
                                            List<String> bodystring = Arrays.asList(body.split(","));
                                            List<String> strings = new ArrayList<>();

                                            for(int i = 0;i<bodystring.size();i++)
                                            {
                                                strings.add(bodystring.get(i));
                                                serialsdata.setSerials(strings.get(i));
                                                db.serialsInterface().insertSerials(serialsdata);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<SerialsGetResponse> call, Throwable t) {

                                        }
                                    });
                                    Intent intent = new Intent(OpenCloseActivity.this, Agent_Mainactivity.class);
                                    intent.putExtra("batch",id);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
                                Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                      // Toast.makeText(OpenCloseActivity.this, "Batch is now opened for Working", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        alertDialog.dismiss();

//                        String status = "stop";
//                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                        try {
//                            JSONObject paramObject = new JSONObject();
//                            paramObject.put("batchNo", value);
//
//
//                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                            Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status,body);
//                            openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
//                                @Override
//                                public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
//                                    String message = response.body().getMessage();
//                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    alertDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
//                                    Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        catch (JSONException e)
//                        {
//                            Toast.makeText(OpenCloseActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
                       // Toast.makeText(OpenCloseActivity.this, "You are done with this Batch", Toast.LENGTH_SHORT).show();
                    }
                });
//                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Complete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String status = "complete";
//                        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//                        try {
//                            JSONObject paramObject = new JSONObject();
//                            paramObject.put("batchNo", value);
//                            RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                            Call<OpenCloseResponse> openCloseActivityCall = web_interface.requestOpenClose(status,body);
//                            openCloseActivityCall.enqueue(new Callback<OpenCloseResponse>() {
//                                @Override
//                                public void onResponse(Call<OpenCloseResponse> call, Response<OpenCloseResponse> response) {
//                                    String message = response.body().getMessage();
//                                    Toast.makeText(OpenCloseActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    alertDialog.dismiss();
//                                }
//
//                                @Override
//                                public void onFailure(Call<OpenCloseResponse> call, Throwable t) {
//                                    Toast.makeText(OpenCloseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                        catch (JSONException e)
//                        {
//                            Toast.makeText(OpenCloseActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                        //Toast.makeText(OpenCloseActivity.this, "You are completed with this batch", Toast.LENGTH_SHORT).show();
//                    }
//                });
                alertDialog.show();
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

    @Override
    public void onLocationChanged(Location location) {
        longitude= location.getLongitude();
        latitude= location.getLatitude();
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
        if(!((Activity) OpenCloseActivity.this).isFinishing())
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
                    startActivity(new Intent(OpenCloseActivity.this, Agent_Mainactivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
