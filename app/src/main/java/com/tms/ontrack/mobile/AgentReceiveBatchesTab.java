package com.tms.ontrack.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
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
        spec.setIndicator("Normal Sims");
        intent=new Intent(this, AgentNormalBatchesReceivedList.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Pending stock");
        spec.setIndicator("Value Sims");
        intent=new Intent(this, AgentBatchesReceived.class);
        spec.setContent(intent);
        tabHost.addTab(spec);



        tabHost.setCurrentTab(0);
        if(tabHost.getCurrentTab() == 0)
        {
            tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#300B50")); // unselected
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title); //Unselected Tabs
            //tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
            tv.setTextColor(Color.parseColor("#FFFD6E"));
        }

        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title); //Unselected Tabs
        //tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); // unselected
        tv.setTextColor(Color.parseColor("#FFFD6E"));
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#461C70")); // unselected
                    tv.setTextColor(Color.parseColor("#FFFD6E"));
                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#300B50")); // selected
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#FFFD6E"));

            }
        });
    }
}

