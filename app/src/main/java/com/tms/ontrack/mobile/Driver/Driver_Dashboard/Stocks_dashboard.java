package com.tms.ontrack.mobile.Driver.Driver_Dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.tms.ontrack.mobile.AboutActivity;
import com.tms.ontrack.mobile.Agent.NetworkError;
import com.tms.ontrack.mobile.Agent.OfflineRica;
import com.tms.ontrack.mobile.Agent_Login.Agent_Login_Activity;
import com.tms.ontrack.mobile.AssignBatchTab;
import com.tms.ontrack.mobile.Driver.SignUpAgent.SignUpAgent;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.CashUpStatement;
import com.tms.ontrack.mobile.Driver.DriverAttendance.DriverAttendance;
import com.tms.ontrack.mobile.Navigation_main.Navigation_Main;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.ReceiveBatchesTab;
import com.tms.ontrack.mobile.RicaTab;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;

import java.util.List;

import es.dmoral.toasty.Toasty;
import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stocks_dashboard extends AppCompatActivity implements View.OnClickListener{

    FontTextView stock_allocation;
    TextView activeliabilitiesvalue,nameText;
    private List<String> performance;
    Toolbar toolbar;
    String nameString;
    TextView textView;
    private List<String> datearray;
    public static String storeid="1",date1="2019-06-26",date2="2019-07-04";
    private SharedPreferences sharedPreferences;
    CardView airtimeSales,databundle,payTv,payUtility,playLotto,Microloan, MicroInsurance,payWater,sim_activation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks_dashboard);
        /**
         comment auto refresh connection code
         */
        /*CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e("onDisconnected","Network");
                        Intent intent = new Intent(Stocks_dashboard.this, NetworkError.class);
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
                        return Stocks_dashboard.this;
                    }
                });*/
        activeliabilitiesvalue = (TextView)findViewById(R.id.activeliabilitiesvalue);

        CardView stocks_received=(CardView)findViewById(R.id.sim_aloc);

        airtimeSales=findViewById(R.id.airtimeSales);
        databundle=findViewById(R.id.dataBundle);
        payTv=findViewById(R.id.payTv);
        payUtility=findViewById(R.id.payUtility);
        playLotto=findViewById(R.id.playLotto);
        Microloan=findViewById(R.id.microLoan);
        MicroInsurance=findViewById(R.id.microInsurance1);
        payWater = findViewById(R.id.payWater);
        sim_activation = findViewById(R.id.sim_activation);
        textView = findViewById(R.id.uhlPrompt);
        nameText = findViewById(R.id.nametext);

        sim_activation.setOnClickListener(this);
        airtimeSales.setOnClickListener(this);
        databundle.setOnClickListener(this);
        payTv.setOnClickListener(this);
        payUtility.setOnClickListener(this);
        playLotto.setOnClickListener(this);
        Microloan.setOnClickListener(this);
        MicroInsurance.setOnClickListener(this);
        payWater.setOnClickListener(this);

        CardView attendance=(CardView)findViewById(R.id.Attendance);
        CardView cashHistory = (CardView)findViewById(R.id.cashupHistory);
        cashHistory.setOnClickListener(this);
        stocks_received.setOnClickListener(this);
        attendance.setOnClickListener(this);
        toolbar=findViewById(R.id.toolbar);
/*

        new UpdateHandler.Builder(this)
                .setContent("New Version Found")
                .setTitle("Update Found")
                .setUpdateText("Update!")
                .setCancelable(false)
                .showDefaultAlert(true)
                .showWhatsNew(true)
                .setCheckerCount(2)
                .setOnUpdateFoundLister(new UpdateHandler.Builder.UpdateListener() {
                    @Override
                    public void onUpdateFound(boolean newVersion, String whatsNew) {
                        textView.setText(textView.getText() + "\n\nUpdate Found : " + newVersion + "\n\nWhat's New\n" + whatsNew);
                    }
                })
                .setOnUpdateClickLister(new UpdateHandler.Builder.UpdateClickListener() {
                    @Override
                    public void onUpdateClick(boolean newVersion, String whatsNew) {
*/
/*                        Log.v("onUpdateClick", String.valueOf(newVersion));
                        Log.v("onUpdateClick", whatsNew);*//*

                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tms.ontrack.mobile"));
                        startActivity(intent);
                    }
                })
                .build();
*/

        CardView stocks_allcoated=(CardView)findViewById(R.id.stockReceived);
        nameString = Pref.getFirstName(this);
        stocks_received.setOnClickListener(this);
        stocks_allcoated.setOnClickListener(this);
        valueWallet();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        nameText.setText(nameString);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {

                    sharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
                    if (sharedPreferences.contains("Driver")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent i = new Intent(Stocks_dashboard.this, Navigation_Main.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                }
                    if(menuItem.getItemId() == R.id.bulkrica)
                    {
                        Log.d("StocksDash", "onOptionsItemSelected:bulk ");
                        Intent intent = new Intent(Stocks_dashboard.this, OfflineRica.class);
                        startActivity(intent);
                    }

                if(menuItem.getItemId() == R.id.about)
                {
                    Log.d("StocksDash", "onOptionsItemSelected:bulk ");
                    Intent intent = new Intent(Stocks_dashboard.this, AboutActivity.class);
                    startActivity(intent);
                }
                if(menuItem.getItemId() == R.id.addagent)
                {
                    Log.d("StocksDash", "onOptionsItemSelected:bulk ");
                    Intent intent = new Intent(Stocks_dashboard.this, SignUpAgent.class);
                    startActivity(intent);
                }

                return false;
            }
        });


    }

    private void valueWallet() {

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<ValueWalletResponse> valueWalletResponseCall = web_interface.requestValueWallet();
        valueWalletResponseCall.enqueue(new Callback<ValueWalletResponse>() {
            @Override
            public void onResponse(Call<ValueWalletResponse> call, Response<ValueWalletResponse> response) {


                if(response.isSuccessful() && response.code() == 200) {
                    
                    if (response.body() != null && response.body().getBody() != null) {
                        int body = response.body().getBody();
                        activeliabilitiesvalue.setText(String.valueOf(body));
                    }
                }
                else if(response.code() == 401)
                {
                    Toast.makeText(Stocks_dashboard.this, "Your Session has Expired.. Please Login again..!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(Stocks_dashboard.this, Navigation_Main.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<ValueWalletResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver_logout_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                Intent i=new Intent(this, Agent_Login_Activity.class);
                startActivity(i);
                finish();
                break;
            case R.id.bulkrica:
                Intent intent = new Intent(this, OfflineRica.class);
                startActivity(intent);
                finish();
                break;
            case R.id.addagent:
                Intent intent1 = new Intent(this, SignUpAgent.class);
                startActivity(intent1);
                break;
            case R.id.about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.sim_aloc){
            Intent i=new Intent(Stocks_dashboard.this, ReceiveBatchesTab.class);
            startActivity(i);
        }


        if(v.getId()==R.id.Attendance){

            Intent i=new Intent(Stocks_dashboard.this,DriverAttendance.class);
            startActivity(i);

        }


        if(v.getId()==R.id.stockReceived){
            Intent i=new Intent(Stocks_dashboard.this, AssignBatchTab.class);
            startActivity(i);
        }
        if(v.getId()==R.id.cashupHistory)
        {
            Intent intent = new Intent(Stocks_dashboard.this, CashUpStatement.class);
            startActivity(intent);
        }

        else if(v.getId() == R.id.airtimeSales)
        {
            /*Intent intent = new Intent(this, AirtimeSalesActivity.class);
            startActivity(intent);*/
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.dataBundle)
        {
/*            Intent intent = new Intent(this, DataBundleActivity.class);
            startActivity(intent);*/
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payTv)
        {
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payUtility)
        {
/*            Intent intent = new Intent(this, ElectricityBundleActivity.class);
            startActivity(intent);*/
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.playLotto)
        {
/*            Intent intent = new Intent(this, WifiBundle.class);
            startActivity(intent);*/
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microLoan)
        {
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microInsurance1)
        {
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payWater)
        {
            Toast.makeText(Stocks_dashboard.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.sim_activation)
        {
            Intent intent = new Intent(Stocks_dashboard.this, RicaTab.class);
            startActivity(intent);
        }

    }


}
