package com.ubits.payflow.payflow_network.Kits;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.ubits.payflow.payflow_network.Adapter;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.IntentIntegrator;
import com.ubits.payflow.payflow_network.IntentResult;
import com.ubits.payflow.payflow_network.R;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class IndividualSim extends AppCompatActivity implements OnClickListener {

    private Button scanBtn, RicaBtn;
    Spinner sp;
    private TextView formatTxt;
    private EditText c, txtbatchto;
    String username, Status, RicasTATUS, Network;
    EditText contentTxt;


    String[] networks = {"MTN", "Vodacom", "CellC", "Telkom"};
    int[] images = {R.drawable.mtn, R.drawable.vodacoms, R.drawable.cellcs, R.drawable.telkom};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_sim);

        Network = "MTN";


        sp = (Spinner) findViewById(R.id.spinnetwork);

        Adapter adapter = new Adapter(this, networks, images);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                Network = networks[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        //  setContentView(R.layout.activity_main);


        scanBtn = (Button) findViewById(R.id.scan_button);

        RicaBtn = (Button) findViewById(R.id.btnricaindiidual);


        contentTxt = (EditText) findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);


        RicaBtn.setOnClickListener(this);
    }


    public void onClick(View v) {


        if (v.getId() == R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }


        if (v.getId() == R.id.btnricaindiidual) {
            //RICA Kit


            if (contentTxt.getText().toString() == "") {
                Toast.makeText(IndividualSim.this, "Please add a Kit Number...", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(IndividualSim.this, "Hold on, we are processing...", Toast.LENGTH_LONG).show();

                String urls = Config.BASE_URL + "app/kit//tester/lastname/" + username + "/" + Network + "/" + contentTxt.getText() + "/test/";


                new IndividualSim.JSONTask().execute(urls);
            }


        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            //String scanFormat = scanningResult.getFormatName();

            contentTxt.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                Status = parentObject.getString("Status");
                RicasTATUS = parentObject.getString("messageDetails");
                // Discount = parentObject.getString("Discount");


            } catch (MalformedURLException e) {

                e.printStackTrace();

                Toast.makeText(IndividualSim.this, "Please Retry", Toast.LENGTH_LONG).show();

            } catch (IOException e) {

                e.printStackTrace();

                Toast.makeText(IndividualSim.this, "Please Retry", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(IndividualSim.this, "Please Retry", Toast.LENGTH_LONG).show();
            } finally {

                if (connection != null) {
                    connection.disconnect();

                }
                try {
                    if (reader != null) {

                        reader.close();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            // Toast.makeText(MySims.this, "Please Retry", Toast.LENGTH_LONG).show();
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (Status.equals("Complete")) {

                Toast.makeText(IndividualSim.this, "RICA Complete " + RicasTATUS.toString(), Toast.LENGTH_LONG).show();


            }

            if (Status.equals("Issue")) {

                Toast.makeText(IndividualSim.this, "RICA Issue " + RicasTATUS.toString(), Toast.LENGTH_LONG).show();


            }


        }

    }


}
