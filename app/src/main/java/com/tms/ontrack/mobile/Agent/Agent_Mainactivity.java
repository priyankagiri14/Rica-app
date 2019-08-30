package com.tms.ontrack.mobile.Agent;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.tms.ontrack.mobile.Agent.AttendanceGetResponse.AttendanceConfirmResponse;
import com.tms.ontrack.mobile.Agent.AttendanceGetResponse.AttendanceGetResponse;
import com.tms.ontrack.mobile.Agent.AttendanceGetResponse.Body;
import com.tms.ontrack.mobile.AgentAssignBatchesTab;
import com.tms.ontrack.mobile.AgentBatchesGet.AgentBatchesGet;
import com.tms.ontrack.mobile.AgentBatchesGet.AppDatabase;
import com.tms.ontrack.mobile.AgentBatchesGet.Batches;
import com.tms.ontrack.mobile.AgentBatchesGet.BatchesAdapter;
import com.tms.ontrack.mobile.AgentBatchesReceived.AgentBatchesReceived;
import com.tms.ontrack.mobile.AgentBatchesReceived.AgentNormalBatchesReceivedList;
import com.tms.ontrack.mobile.AgentReceiveBatchesTab;
import com.tms.ontrack.mobile.AirtimeSales.AirtimeSalesActivity;
import com.tms.ontrack.mobile.DataBundle.DataBundleActivity;
import com.tms.ontrack.mobile.ElectricityBundle.ElectricityBundleActivity;
import com.tms.ontrack.mobile.Navigation_main.Navigation_Main;
import com.tms.ontrack.mobile.OpenBatchesResponse.OpenedBatchesActivity;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.RicaTab;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.tms.ontrack.mobile.WifiBundle.WifiBundle;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import fr.arnaudguyon.perm.Perm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Agent_Mainactivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardviewAttendance, airtimeSales, payWater;

    Toolbar toolbar;

    int id;
    String batchid,nameString;
    private SharedPreferences sharedPreferences;
    private AppDatabase db;
    Perm perm;
    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSIONS[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.CAMERA};

    TextView textView,nameText;

    public static BatchesAdapter batchesAdapter;
    public static List<Batches> batcheslist = new ArrayList<>();
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstancestate) {

        super.onCreate(savedInstancestate);
        setContentView(R.layout.agent_mainactivity);


        /*CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e("onDisconnected","Network");
                        Intent intent = new Intent(Agent_Mainactivity.this,NetworkError.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onNetworkConnected() {
                        //Do your task on Network Connected!
                        Log.d("onNetworkConnected: ","Network");
                       *//* Intent intent = new Intent(Agent_Mainactivity.this,Agent_Mainactivity.class);
                        startActivity(intent);*//*
                    }

                    @Override
                    public Context getContext() {
                        return Agent_Mainactivity.this;
                    }
                });*/
        cardView1 = findViewById(R.id.checkStock);
        cardView2 = findViewById(R.id.Callagent);
        cardView3 = findViewById(R.id.sim_activation);
        cardView4 = findViewById(R.id.airtimeSales);
        cardView5 = findViewById(R.id.dataBundle);
        cardView6 = findViewById(R.id.payTv);
        cardView7 = findViewById(R.id.payUtility);
        cardView8 = findViewById(R.id.playLotto);
        cardView9 = findViewById(R.id.microLoan);
        cardView10 = findViewById(R.id.microInsurance1);
        cardviewAttendance = findViewById(R.id.attendanceacpt);
        airtimeSales = findViewById(R.id.activebatches);
        payWater = findViewById(R.id.payWater);
        textView = findViewById(R.id.uhlPrompt);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
        cardView6.setOnClickListener(this);
        cardView7.setOnClickListener(this);
        cardView8.setOnClickListener(this);
        cardView9.setOnClickListener(this);
        cardView10.setOnClickListener(this);
        payWater.setOnClickListener(this);
        cardviewAttendance.setOnClickListener(this);
        airtimeSales.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        nameText = findViewById(R.id.nametext);

        if (Pref.getBatchID(this) == null) {
            batchid = "";
        } else {
            batchid = Pref.getBatchID(this);
        }
        nameString = Pref.getFirstName(this);
        nameText.setText(nameString);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {

                    sharedPreferences = getSharedPreferences("Agent", Context.MODE_PRIVATE);
                    if (sharedPreferences.contains("Agent")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent i = new Intent(Agent_Mainactivity.this, Navigation_Main.class);
                        startActivity(i);
                        finish();
                    }
                }
                if (menuItem.getItemId() == R.id.bulkrica) {
                    Intent intent = new Intent(Agent_Mainactivity.this, OfflineRica.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        perm = new Perm(this, PERMISSIONS);
        if (perm.areGranted()) {
            //   Toast.makeText(this, "All Permissions granted", Toast.LENGTH_LONG).show();
        } else {
            perm.askPermissions(PERMISSIONS_REQUEST);
        }

}
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                Intent i=new Intent(this, Navigation_Main.class);
                startActivity(i);
                finish();
                break;
            case R.id.bulkrica:
                Intent intent = new Intent(this,OfflineRica.class);
                startActivity(intent);
                finish();
                break;
                default:
                    break;
        }
        return true;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkStock){
            Intent i=new Intent(this, AgentAssignBatchesTab.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.sim_activation){
            Intent intent=new Intent(this, RicaTab.class);
            startActivity(intent);

        }
        else if(v.getId() == R.id.Callagent)
        {
            if(batchid.length()==0)
            {
                Intent intent = new Intent(this, AgentReceiveBatchesTab.class);
                startActivity(intent);
            }
            else
            {
                anotherBatchActive();
            }
        }
        else if(v.getId() == R.id.airtimeSales)
        {
           /* Intent intent = new Intent(this, AirtimeSalesActivity.class);
            startActivity(intent);*/
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();

        }
        else if(v.getId() == R.id.dataBundle)
        {
/*            Intent intent = new Intent(this, DataBundleActivity.class);
            startActivity(intent);*/
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payTv)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payUtility)
        {
          /*  Intent intent = new Intent(this, ElectricityBundleActivity.class);
            startActivity(intent);*/
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.playLotto)
        {
 /*           Intent intent = new Intent(this, WifiBundle.class);
            startActivity(intent);*/
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microLoan)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microInsurance1)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.attendanceacpt)
        {
            attendanceGet();
        }
        else if(v.getId() == R.id.activebatches)
        {
                if (batchid.length()==0) {
                    noActiveBatches();
                } else {
                    Intent intent = new Intent(Agent_Mainactivity.this, OpenedBatchesActivity.class);
                    startActivity(intent);
                }
        }
        else if(v.getId() == R.id.payWater)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
    }

    private void anotherBatchActive() {

        AlertDialog alertDialog = new AlertDialog.Builder(Agent_Mainactivity.this).create();
        alertDialog.setMessage("Another Batch is already in Active State");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Got It!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void noActiveBatches() {
        AlertDialog alertDialog = new AlertDialog.Builder(Agent_Mainactivity.this).create();
        alertDialog.setMessage("No Active Batches");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Got It!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void attendanceGet() {


        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AttendanceGetResponse> attendanceGetResponseCall = web_interface.requestAttendanceGet(0,0);
        attendanceGetResponseCall.enqueue(new Callback<AttendanceGetResponse>() {
            @Override
            public void onResponse(Call<AttendanceGetResponse> call, Response<AttendanceGetResponse> response) {
                String confirmation = "null";
                List<Body> bodyList = new ArrayList<>();

                bodyList = response.body().getBody();
                Log.d("onResponse: ", confirmation);
                if (bodyList.size() == 0) {
                    noAcknowledgmentDialog();
                } else {
                    confirmation = bodyList.get(0).getConfirmation().toString();
                    Log.d("onResponse: ", confirmation);
                   if (confirmation == "false") {

                        id = bodyList.get(0).getId();
                        Log.d("onResponse: ", confirmation);
                        alertDialogopen();
                    }
                }
            }

            private void noAcknowledgmentDialog() {
                AlertDialog alertDialog = new AlertDialog.Builder(Agent_Mainactivity.this).create();
                alertDialog.setMessage("No Attendance Acknowledgement Pending..!");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Got It!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<AttendanceGetResponse> call, Throwable t) {
                Toast.makeText(Agent_Mainactivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertDialogopen() {

        AlertDialog alertDialog = new AlertDialog.Builder(Agent_Mainactivity.this).create();
        alertDialog.setTitle("Please Acknowledge Your Attendence");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String confirmation = "true";
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);


                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("confirmation",confirmation);

                    RequestBody body = RequestBody.create(MediaType.parse("application/json"),(jsonObject).toString());
                    Call<AttendanceConfirmResponse> attendanceConfirmResponseCall = web_interface.requestAttendanceConfirm(id,body);
                    attendanceConfirmResponseCall.enqueue(new Callback<AttendanceConfirmResponse>() {
                        @Override
                        public void onResponse(Call<AttendanceConfirmResponse> call, Response<AttendanceConfirmResponse> response) {
                            if(response.isSuccessful() && response.code() == 200)
                            {
                                String message = response.body().getMessage();
                                Toast.makeText(Agent_Mainactivity.this, message, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<AttendanceConfirmResponse> call, Throwable t) {
                            Toast.makeText(Agent_Mainactivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (JSONException e)
                {
                    Toast.makeText(Agent_Mainactivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
