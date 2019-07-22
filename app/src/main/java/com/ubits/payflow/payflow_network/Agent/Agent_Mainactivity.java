package com.ubits.payflow.payflow_network.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.ubits.payflow.payflow_network.AgentBatchesGet.AgentBatchesGet;
import com.ubits.payflow.payflow_network.AgentBatchesReceived.AgentBatchesReceived;
import com.ubits.payflow.payflow_network.Agent_Login.Agent_Login_Activity;
import com.ubits.payflow.payflow_network.DriverBatchesGet.BatchesGetList;
import com.ubits.payflow.payflow_network.Navigation_main.Navigation_Main;
import com.ubits.payflow.payflow_network.R;

public class Agent_Mainactivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6,cardView7,cardView8,cardView9,cardView10;
    Toolbar toolbar;
    public void onCreate(Bundle savedInstancestate) {

        super.onCreate(savedInstancestate);
        setContentView(R.layout.agent_mainactivity);
        cardView1 = findViewById(R.id.checkStock);
        cardView2 = findViewById(R.id.Callagent);
        cardView3=findViewById(R.id.sim_activation);
        cardView4=findViewById(R.id.airtimeSales);
        cardView5=findViewById(R.id.dataBundle);
        cardView6=findViewById(R.id.payTv);
        cardView7=findViewById(R.id.payUtility);
        cardView8=findViewById(R.id.playLotto);
        cardView9=findViewById(R.id.microLoan);
        cardView10=findViewById(R.id.microInsurance);
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
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Agent Dashboard");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logout) {

                    Intent i = new Intent(Agent_Mainactivity.this, Navigation_Main.class);
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
                Intent i=new Intent(this, Navigation_Main.class);
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
            Intent intent = new Intent(this, AgentBatchesReceived.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.airtimeSales)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.dataBundle)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payTv)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.payUtility)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.playLotto)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microLoan)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.microInsurance)
        {
            Toast.makeText(Agent_Mainactivity.this, "Coming Soon....", Toast.LENGTH_SHORT).show();
        }
    }
}
