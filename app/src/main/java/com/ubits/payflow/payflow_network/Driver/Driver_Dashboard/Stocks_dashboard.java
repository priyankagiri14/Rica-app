package com.ubits.payflow.payflow_network.Driver.Driver_Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ubits.payflow.payflow_network.DriverBatchesGet.BatchesGetList;

import com.ubits.payflow.payflow_network.Driver.DriverAttendance.DriverAttendance;

import com.ubits.payflow.payflow_network.BatchesReceived.BatchesReceivedList;

import com.ubits.payflow.payflow_network.R;

import java.util.List;

import info.androidhive.fontawesome.FontTextView;


public class Stocks_dashboard extends AppCompatActivity implements View.OnClickListener{

    FontTextView stock_allocation;

    private List<String> performance;
    private List<String> datearray;
    public static String storeid="1",date1="2019-06-26",date2="2019-07-04";

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

//    FontTextView assign_agent=(FontTextView)findViewById(R.id.stck);
//        assign_agent.setOnClickListener(this);
        CardView stocks_received=(CardView)findViewById(R.id.sim_aloc);

        CardView attendance=(CardView)findViewById(R.id.Attendance);
        CardView call=(CardView)findViewById(R.id.Call);
        call.setOnClickListener(this);
        stocks_received.setOnClickListener(this);
        attendance.setOnClickListener(this);


        CardView stocks_allcoated=(CardView)findViewById(R.id.stockReceived);
        stocks_received.setOnClickListener(this);
        stocks_allcoated.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.sim_aloc){
            Intent i=new Intent(Stocks_dashboard.this, BatchesReceivedList.class);
            startActivity(i);
        }


        if(v.getId()==R.id.Attendance){

            Intent i=new Intent(Stocks_dashboard.this,DriverAttendance.class);
            startActivity(i);

        }


        if(v.getId()==R.id.stockReceived){
            Intent i=new Intent(Stocks_dashboard.this, BatchesGetList.class);
            startActivity(i);
        }

    }


}
