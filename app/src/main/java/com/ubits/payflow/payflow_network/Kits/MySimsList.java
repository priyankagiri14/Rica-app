package com.ubits.payflow.payflow_network.Kits;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
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

public class MySimsList extends AppCompatActivity {

    String username, Status, Vodacom, MTN, CellC, Telkom, Vodacoms, MTNs, CellCs, Telkoms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sims_list);


        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);


        String urls = Config.BASE_URL + "app/mysimlist//tester/hello/" + username + "/email/12345/test/";


        new MySimsList.JSONTask().execute(urls);


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
                MTN = parentObject.getString("MTN");
                Vodacom = parentObject.getString("Vodacom");
                CellC = parentObject.getString("CellC");
                Telkom = parentObject.getString("Telkom");
                MTNs = parentObject.getString("MTNs");
                Vodacoms = parentObject.getString("Vodacoms");
                CellCs = parentObject.getString("CellCs");
                Telkoms = parentObject.getString("Telkoms");


            } catch (MalformedURLException e) {

                e.printStackTrace();

                Toast.makeText(MySimsList.this, "Please Retry", Toast.LENGTH_LONG).show();

            } catch (IOException e) {

                e.printStackTrace();

                Toast.makeText(MySimsList.this, "Please Retry", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(MySimsList.this, "Please Retry", Toast.LENGTH_LONG).show();
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

            if (Status.equals("Success")) {

                Button btnmtncur = (Button) findViewById(R.id.btnmtncur);
                btnmtncur.setText("MTN: " + MTN.toString());

                Button btnvodacomcur = (Button) findViewById(R.id.btnvodacomcur);
                btnvodacomcur.setText("Vodacom: " + Vodacom.toString());

                Button btnpcellccur = (Button) findViewById(R.id.btnpcellccur);
                btnpcellccur.setText("CellC:" + CellC.toString());

                Button btntelkomcurrr = (Button) findViewById(R.id.btntelkomcurrr);
                btntelkomcurrr.setText("Telkom: " + Telkom.toString());

                Button btnmtnsold = (Button) findViewById(R.id.btnmtnsold);
                btnmtnsold.setText("MTN: " + MTNs.toString());

                Button btnvodacomsold = (Button) findViewById(R.id.btnvodacomsold);
                btnvodacomsold.setText("Vodacom: " + Vodacoms.toString());

                Button btnpcellsold = (Button) findViewById(R.id.btnpcellsold);
                btnpcellsold.setText("CellC:" + CellCs.toString());

                Button btntelkomsold = (Button) findViewById(R.id.btntelkomsold);
                btntelkomsold.setText("Telkom: " + Telkoms.toString());


            }

            if (Status.equals("ERROR")) {

                Toast.makeText(MySimsList.this, "You have no Kits, please check with admin", Toast.LENGTH_LONG).show();


            }


        }

    }

}
