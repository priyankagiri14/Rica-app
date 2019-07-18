package com.ubits.payflow.payflow_network.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.ubits.payflow.payflow_network.AgentBatchesGet.AgentBatchesGet;
import com.ubits.payflow.payflow_network.Agent_Login.Agent_Login_Activity;
import com.ubits.payflow.payflow_network.DriverBatchesGet.BatchesGetList;
import com.ubits.payflow.payflow_network.R;

public class Agent_Mainactivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView1,cardView2,cardView3;
    Toolbar toolbar;
    public void onCreate(Bundle savedInstancestate) {

        super.onCreate(savedInstancestate);
        setContentView(R.layout.agent_mainactivity);
        cardView1 = findViewById(R.id.checkStock);
        cardView2 = findViewById(R.id.Callagent);
        cardView3=findViewById(R.id.sim_activation);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Agent Dashboard");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {

                    Intent i = new Intent(Agent_Mainactivity.this, Agent_Login_Activity.class);
                    startActivity(i);
                finish();

                }
                return false;
    }
});
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
        if(v.getId()==R.id.checkStock){
            Intent i=new Intent(this, AgentBatchesGet.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.sim_activation){
            Intent intent=new Intent(this,Sim_allocation.class);
            startActivity(intent);

        }
        else if(v.getId() == R.id.Callagent)
        {
            Intent intent = new Intent(this, Sim_allocation.class);
            startActivity(intent);
        }
    }
}
