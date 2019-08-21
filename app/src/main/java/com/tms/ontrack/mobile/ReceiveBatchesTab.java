package com.tms.ontrack.mobile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.tms.ontrack.mobile.BatchesReceived.BatchesReceivedList;
import com.tms.ontrack.mobile.BatchesReceived.NormalBatchesReceivedList;

public class ReceiveBatchesTab extends TabActivity {

    private TextView tabtext;
    String driver="DRIVER";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_login_activty);
        tabtext=findViewById(R.id.tab_tv_title);

        TabHost tabHost=findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;

        spec=tabHost.newTabSpec("Boxno");
        spec.setIndicator("Value Sims");
        intent=new Intent(this, BatchesReceivedList.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Pending stock");
        spec.setIndicator("Normal Sims");
        intent=new Intent(this, NormalBatchesReceivedList.class);
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
