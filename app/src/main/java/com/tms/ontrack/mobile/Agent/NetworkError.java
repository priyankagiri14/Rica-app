package com.tms.ontrack.mobile.Agent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent_Login.Agent_Login_Activity;
import com.tms.ontrack.mobile.Navigation_main.Navigation_Main;
import com.tms.ontrack.mobile.R;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;

public class NetworkError extends AppCompatActivity {

    TextView refreshtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_error);

        refreshtext = findViewById(R.id.refreshtext);
        refreshtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckNetworkConnectionHelper
                        .getInstance()
                        .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                            @Override
                            public void onDisconnected() {
                                //Do your task on Network Disconnected!
                                Toast.makeText(NetworkError.this, "Check your Internet Connection", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNetworkConnected() {
                                //Do your task on Network Connected!
                                Log.d("onNetworkConnected: ","Network");
                        Intent intent = new Intent(NetworkError.this, Navigation_Main.class);
                        startActivity(intent);
                            }

                            @Override
                            public Context getContext() {
                                return NetworkError.this;
                            }
                        });
            }
        });
    }
}
