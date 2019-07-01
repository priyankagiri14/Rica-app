package com.ubits.payflow.payflow_network.General;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Utils;

public class TopUpInfon extends AppCompatActivity {

    TextView txtRef, tvTitle;
    String username;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_infon);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_top_up_infon);

        txtRef = (TextView) findViewById(R.id.txtRefs);
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
        txtRef.setText("REFERENCE: " + username);

        if (Utils.isOnline(TopUpInfon.this)) {

            //  Utils.showToasMessage(TopUpInfon.this,"network connection availble");

        } else {
            Utils.showToasMessage(TopUpInfon.this, "Check network connection");
        }
    }

}
