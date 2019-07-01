package com.ubits.payflow.payflow_network.Kits;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewStatementDetails extends AppCompatActivity {


    String batch, password, Status, MTN, Dates, Vodacom, CellC, Telkom,cusTomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_statement_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtBatch = (TextView) findViewById(R.id.txtBatch);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        batch = bundle.getString("batch");

        txtBatch.setText("Batch: " + batch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        password = "12345";

       // String urls = Config.BASE_URL + "app/viewbatchinfo//tester/lastname/" + batch + "/ubits.co.za/" + password + "/test/";
        String urls = Config.BASE_URL+"app/statement?Customer_ID="+cusTomerId;

        if (isOnline()) {
            new ViewStatementDetails.JSONTask().execute(urls);
        } else {
            Toast.makeText(ViewStatementDetails.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
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
                Dates = parentObject.getString("Date");
                MTN = parentObject.getString("MTN");
                Vodacom = parentObject.getString("Vodacom");
                CellC = parentObject.getString("CellC");
                Telkom = parentObject.getString("Telkom");

                return MTN;


            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
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

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (Status != null) {

                if (Status.equals("ERROR")) {

                }
                else if (Status.equals("SUCCESS")) {
                    TextView txtBatchDate = (TextView) findViewById(R.id.txtBatchDate);
                    txtBatchDate.setText(" ");

                    TextView txtBatchMTN = (TextView) findViewById(R.id.txtBatchMTN);
                    txtBatchMTN.setText("MTN:  " + MTN.toString());

                    TextView txtBatchVodacom = (TextView) findViewById(R.id.txtBatchVodacom);
                    txtBatchVodacom.setText("Vodacom:  " + Vodacom.toString());

                    TextView txtCellC = (TextView) findViewById(R.id.txtCellC);
                    txtCellC.setText("CellC:  " + CellC.toString());

                    TextView txtBatchTelkom = (TextView) findViewById(R.id.txtBatchTelkom);
                    txtBatchTelkom.setText("Telkom:  " + Telkom.toString());


                } else {

                }
            }


        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
