package com.tms.ontrack.mobile.AgentsList;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tms.ontrack.mobile.AllocationCreateResponse.AllocationCreate;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class AgentsList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, LocationListener {

    private AgentsListAdapter adapter;
    List<AgentsListResponse> list1 = new ArrayList<>();
    ArrayList<Body> bodyArrayList = new ArrayList<>();
    List<Body> bodyArrayList1 = new ArrayList<>();
    ArrayList<String> bodyArrayListbatches = new ArrayList<String>();
    String bodybatchesstring[];
    TextView assignagents, noagentslist;
    public ListView listView;
    Button btnstatus;
    LocationManager locationManager;
    ProgressDialog progressBar;
    int count = 0;
    Dialog agentDetailsDialog;
    double latitude,longitude;

    private void populateListView(List<Body> batchesGetResponseList) {
        Log.d("PNK", "POPULATELIST");
        Log.d("PNK", list1.toString());

        bodyArrayList1 = batchesGetResponseList;
        adapter = new AgentsListAdapter(this, bodyArrayList1);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
        assignagents = (TextView) findViewById(R.id.assign_agents);
        noagentslist = (TextView) findViewById(R.id.noagentslist);
        btnstatus.setVisibility(View.INVISIBLE);
        assignagents.setVisibility(View.INVISIBLE);
        agentsGet();
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
        btnstatus.setOnClickListener(this);
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
                    if (listView.getCount() == 0) {
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
            Call<AllocationCreate> allocationCreateCall = web_interface.requestAllocationCreate(id, latitude,longitude,batches);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        agentDetailsDialog = new Dialog(this);
        agentDetailsDialog.setContentView(R.layout.agentdetailsdialog);
        agentDetailsDialog.setTitle("Agent Details");
        final TextView name = (TextView)agentDetailsDialog.findViewById(R.id.name);
        final TextView mobile = (TextView)agentDetailsDialog.findViewById(R.id.mobile);
        final TextView email = (TextView)agentDetailsDialog.findViewById(R.id.email);
        final TextView idno = (TextView)agentDetailsDialog.findViewById(R.id.id);
        final ImageView profileImage = (ImageView)agentDetailsDialog.findViewById(R.id.profileimage);
        String namestring = bodyArrayList1.get(position).getName();
        String mobilestring = bodyArrayList1.get(position).getProfile().getMobileNo();
        String emailstring = bodyArrayList1.get(position).getProfile().getEmail();
        String idnostring = bodyArrayList1.get(position).getProfile().getIdNo();
        Integer fileid = bodyArrayList1.get(position).getAttachments().get(position).getId();


        name.setText(namestring);
        mobile.setText(mobilestring);
        email.setText(emailstring);
        idno.setText(idnostring);

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<ResponseBody> responseBodyCall = web_interface.requestImagefromserver(fileid);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String imageView = response.toString();

                if (response.body() != null) {
                    Bitmap bitmap= BitmapFactory.decodeStream(response.body().byteStream());
                    Log.d("bitmap", "onResponse: "+bitmap);
                    profileImage.setImageBitmap(bitmap);
                }
                Log.d("onImage: ",imageView);
               // URI uri = new URI();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        final TextView okbtn = (TextView) agentDetailsDialog.findViewById(R.id.ok);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agentDetailsDialog.cancel();
            }
        });

        agentDetailsDialog.show();
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
        if(!((Activity) AgentsList.this).isFinishing()) {
            //show dialog
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Open GPS Settings?");
            alertDialog.setCancelable(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if (Settings.ACTION_LOCATION_SOURCE_SETTINGS.equals(true)) {
                        alertDialog.dismiss();
                    }
                    count++;
                }
            });
            if (count > 0) {
                alertDialog.dismiss();
                count = 0;
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AgentsList.this, Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
            alertDialog.show();
        }
    }
}