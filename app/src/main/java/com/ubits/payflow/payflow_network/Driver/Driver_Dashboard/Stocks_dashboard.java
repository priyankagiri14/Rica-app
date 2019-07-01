package com.ubits.payflow.payflow_network.Driver.Driver_Dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Stock_allocate;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Tab_Stock_Activity;
import com.ubits.payflow.payflow_network.R;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.androidhive.fontawesome.FontTextView;

public class Stocks_dashboard extends Fragment implements View.OnClickListener {

    FontTextView stock_allocation;

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
        stocks_received.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.sim_aloc){
            Intent i=new Intent(getContext(), Tab_Stock_Activity.class);
            startActivity(i);
        }

    }
}
