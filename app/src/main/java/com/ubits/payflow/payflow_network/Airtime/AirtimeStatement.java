package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.mMySQL.DownloaderStatement;

import org.json.JSONArray;

public class AirtimeStatement extends AppCompatActivity {
    String username,cusTomerId;
    private JSONArray result;
    private ProgressDialog dialog;
    ListView lv;
    private TextView tvTile;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_statement);

        lv = (ListView) findViewById(R.id.listviewstatement);

        tvTile = (TextView) findViewById(R.id.tv_titlebar);
        backButton = (ImageView) findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTile.setText(R.string.title_activity_view_statement_details);

        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
      //  String url = Config.BASE_URL + "app/statement//tester/hello/" + username + "/email/12345/test/";

        String url = Config.BASE_URL+"app/statement?Customer_ID="+username;
        DownloaderStatement d = new DownloaderStatement(AirtimeStatement.this, url, lv);
        d.execute();
    }
}
