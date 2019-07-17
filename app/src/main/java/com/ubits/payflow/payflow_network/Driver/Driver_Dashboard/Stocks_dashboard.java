package com.ubits.payflow.payflow_network.Driver.Driver_Dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetList;

import com.ubits.payflow.payflow_network.Driver.DriverAttendance.DriverAttendance;

import com.ubits.payflow.payflow_network.BatchesReceived.BatchesReceivedList;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Stock_allocate;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Tab_Stock_Activity;

import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.Ret;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import info.androidhive.fontawesome.FontTextView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stocks_dashboard extends Fragment implements View.OnClickListener{

    FontTextView stock_allocation;

    private List<String> performance;
    private List<String> datearray;
    public static String storeid="1",date1="2019-06-26",date2="2019-07-04";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.stocks_dashboard, container, false);
        ((Driver_Dashboard) getActivity()).getSupportActionBar().setTitle("Driver Dashboard");
        ButterKnife.bind(this,view);
        //stocks_received.setOnClickListener(this);
        Log.d("Driver Dashboard","Driver Dashboard opens");
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);/*
        FontTextView assign_agent=(FontTextView) view.findViewById(R.id.stck_aloc);
        assign_agent.setOnClickListener(this);*/
        CardView stocks_received=(CardView)view.findViewById(R.id.sim_aloc);

        CardView attendance=(CardView)view.findViewById(R.id.Attendance);
        CardView call=(CardView)view.findViewById(R.id.Call);
        call.setOnClickListener(this);
        stocks_received.setOnClickListener(this);
        attendance.setOnClickListener(this);


        CardView stocks_allcoated=(CardView)view.findViewById(R.id.stockReceived);
        stocks_received.setOnClickListener(this);
        stocks_allcoated.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.sim_aloc){
            Intent i=new Intent(getContext(), BatchesReceivedList.class);
            startActivity(i);
        }


        if(v.getId()==R.id.Attendance){

            Intent i=new Intent(getContext(),DriverAttendance.class);
            startActivity(i);

        }


        if(v.getId()==R.id.stockReceived){
            Intent i=new Intent(getContext(), BatchesGetList.class);
            startActivity(i);
        }

    }


}
