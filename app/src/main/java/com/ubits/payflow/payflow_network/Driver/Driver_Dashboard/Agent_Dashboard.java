package com.ubits.payflow.payflow_network.Driver.Driver_Dashboard;

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

import com.ubits.payflow.payflow_network.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Agent_Dashboard extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.agent_dashboard, container, false);
        ((Driver_Dashboard) getActivity()).getSupportActionBar().setTitle("Agent Dashboard");
        ButterKnife.bind(this,view);
        //stocks_received.setOnClickListener(this);
        Log.d("Agent Dashboard","Agent Dashboard opens");
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*CardView agent_attendance=(CardView)view.findViewById(R.id.agent_attendance);
        agent_attendance.setOnClickListener(this);
*/
    }

    @Override
    public void onClick(View v) {
        /*if(v.getId()==R.id.stocks_received){
            Toast.makeText(getContext(),"Stocks received",Toast.LENGTH_SHORT).show();
        }*/
    }
}
