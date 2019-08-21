package com.tms.ontrack.mobile.Driver.Driver_Dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.tms.ontrack.mobile.Agent_Login.Agent_Login_Activity;
import com.tms.ontrack.mobile.AssignBatchTab;
import com.tms.ontrack.mobile.Driver.Stock_allocate.Tab_Stock_Activity;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.CashUpStatement;
import com.tms.ontrack.mobile.DriverBatchesGet.BatchesGetList;

import com.tms.ontrack.mobile.Driver.DriverAttendance.DriverAttendance;

import com.tms.ontrack.mobile.BatchesReceived.BatchesReceivedList;

import com.tms.ontrack.mobile.Navigation_main.Navigation_Main;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.ReceiveBatchesTab;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stocks_dashboard extends AppCompatActivity implements View.OnClickListener{

    FontTextView stock_allocation;
    TextView activeliabilitiesvalue;
    private List<String> performance;
    Toolbar toolbar;
    private List<String> datearray;
    public static String storeid="1",date1="2019-06-26",date2="2019-07-04";
    private SharedPreferences sharedPreferences;

//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view=inflater.inflate(R.layout.stocks_dashboard, container, false);
//        ((Driver_Dashboard) getActivity()).getSupportActionBar().setTitle("Driver Dashboard");
//        ButterKnife.bind(this,view);
//        //stocks_received.setOnClickListener(this);
//        Log.d("Driver Dashboard","Driver Dashboard opens");
//        return view;
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks_dashboard);

        activeliabilitiesvalue = (TextView)findViewById(R.id.activeliabilitiesvalue);

//    FontTextView assign_agent=(FontTextView)findViewById(R.id.stck);
//        assign_agent.setOnClickListener(this);
        CardView stocks_received=(CardView)findViewById(R.id.sim_aloc);

        CardView attendance=(CardView)findViewById(R.id.Attendance);
        CardView cashHistory = (CardView)findViewById(R.id.cashupHistory);
        cashHistory.setOnClickListener(this);
        stocks_received.setOnClickListener(this);
        attendance.setOnClickListener(this);
        toolbar=findViewById(R.id.toolbar);


        CardView stocks_allcoated=(CardView)findViewById(R.id.stockReceived);
        stocks_received.setOnClickListener(this);
        stocks_allcoated.setOnClickListener(this);
        valueWallet();
        toolbar.setTitle("Driver Dashboard");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {

                        sharedPreferences=getSharedPreferences("Driver", Context.MODE_PRIVATE);
                      if(sharedPreferences.contains("Driver")) {
                          SharedPreferences.Editor editor = sharedPreferences.edit();
                          editor.clear();
                          editor.apply();
                          Intent i = new Intent(Stocks_dashboard.this, Navigation_Main.class);
                          i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(i);
                          finish();

                      }



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

                int body = response.body().getBody();
                activeliabilitiesvalue.setText(String.valueOf(body));
            }

            @Override
            public void onFailure(Call<ValueWalletResponse> call, Throwable t) {
                Toast.makeText(Stocks_dashboard.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    }


}
