package com.ubits.payflow.payflow_network.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.card.MaterialCardView;
import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetList;
import com.ubits.payflow.payflow_network.R;

public class Agent_Mainactivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView1,cardView2;
    public void onCreate(Bundle savedInstancestate) {

        super.onCreate(savedInstancestate);
        setContentView(R.layout.agent_mainactivity);
        cardView1 = findViewById(R.id.checkStock);
        cardView2=findViewById(R.id.sim_activation);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkStock){
            Intent i=new Intent(this, BatchesGetList.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.sim_activation){
            Intent intent=new Intent(this,Sim_allocation.class);
            startActivity(intent);

        }
    }
}
