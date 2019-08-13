package com.tms.ontrack.mobile.Driver.Stock_allocate;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.tms.ontrack.mobile.R;

public class Tab_Stock_Activity extends TabActivity {

    private TextView tabtext;
    String driver="DRIVER";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_box_activity);
        tabtext=findViewById(R.id.tab_tv_title);

        TabHost tabHost=findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;

        spec=tabHost.newTabSpec("Boxno");
        spec.setIndicator("BOX NUMBER");
        intent=new Intent(this, Stock_allocate.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("Pending stock");
        spec.setIndicator("PENDING STOCK");
        intent=new Intent(this, Pending_Box_List.class);
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
