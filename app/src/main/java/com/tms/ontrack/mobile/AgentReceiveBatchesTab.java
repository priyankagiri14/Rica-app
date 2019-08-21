package com.tms.ontrack.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.tms.ontrack.mobile.AgentBatchesReceived.AgentBatchesReceived;
import com.tms.ontrack.mobile.AgentBatchesReceived.AgentNormalBatchesReceivedList;

public class AgentReceiveBatchesTab extends TabActivity {

    private TextView tabtext;
    String driver="DRIVER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_receive_batches_tab);

        tabtext=findViewById(R.id.tab_tv_title);

        TabHost tabHost=findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;

        spec=tabHost.newTabSpec("Boxno");
        spec.setIndicator("Value Sims");
        intent=new Intent(this, AgentBatchesReceived.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Pending stock");
        spec.setIndicator("Normal Sims");
        intent=new Intent(this, AgentNormalBatchesReceivedList.class);
        spec.setContent(intent);
        tabHost.addTab(spec);



        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
                // Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();


            }
        });
    }
}

