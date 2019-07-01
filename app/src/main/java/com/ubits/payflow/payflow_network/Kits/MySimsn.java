package com.ubits.payflow.payflow_network.Kits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.General.Dashboard;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.R;

public class MySimsn extends AppCompatActivity {
    String username, password, Balance, Status;
    private TextView tvTitle;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_simsn);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_my_sim_listn);

        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);

        ImageButton btnProcess = (ImageButton) findViewById(R.id.btnprocessSims);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, ProcessBatchn.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);
            }
        });

        ImageButton btnRicaIndv = (ImageButton) findViewById(R.id.btnProcessIndv);
        btnRicaIndv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, IndividualSimn.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);

            }
        });
        ImageButton btnRica = (ImageButton) findViewById(R.id.btnMainRICA);
        btnRica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, Rican.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                intent.putExtra("identity", "Rica");
                startActivity(intent);
            }
        });

        ImageButton btnMainMenu = (ImageButton) findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);

            }

        });

        ImageButton btnAllocation = (ImageButton) findViewById(R.id.btnAllocate);
        btnAllocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, AoolcateBatchn.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);
            }
        });

        ImageButton btnMysims = (ImageButton) findViewById(R.id.btnDashboardSims);
        btnMysims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySimsn.this, Dashboard.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);
            }

        });

        ImageButton btnbatchlist = (ImageButton) findViewById(R.id.btnMyBatches);
        btnbatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySimsn.this, BatchListn.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);

            }

        });

        ImageButton btnsearch = (ImageButton) findViewById(R.id.btnSellASim_btn);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySimsn.this, SellaSim.class);
                intent.putExtra("username", username);
                intent.putExtra("acccount", username);
                startActivity(intent);
            }

        });
    }
}
