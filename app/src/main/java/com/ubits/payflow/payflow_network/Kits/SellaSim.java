package com.ubits.payflow.payflow_network.Kits;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.IntentIntegrator;
import com.ubits.payflow.payflow_network.IntentResult;
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

public class SellaSim extends AppCompatActivity implements View.OnClickListener, HttpRequest.OnReadyStateChangeListener, HttpRequest.OnErrorListener {


    String Status, Balance, Discount, username, password, cellnumber, RicasTATUS, Network, amount;
    EditText contentTxt;
    private Button scanBtn, btnsell;
    private TextView tvTitle;
    private ImageView backBtn;
    private ProgressDialog dialog;
    String urls = Config.BASE_URL + "app/sellkit/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sella_sim);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_sella_sim);
        dialog = new ProgressDialog(this);


        Network = getIntent().getExtras().getString("network");
        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
        String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";
        new SellaSim.JSONTaska().execute(urls);


        scanBtn = (Button) findViewById(R.id.scan_btn_sell);

        btnsell = (Button) findViewById(R.id.btnsellsim);

        contentTxt = (EditText) findViewById(R.id.scan_content_sell);

        scanBtn.setOnClickListener(this);


        btnsell.setOnClickListener(this);
    }

    public void onClick(View v) {


        if (v.getId() == R.id.scan_btn_sell) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        if (v.getId() == R.id.btnsellsim) {
            //RICA Kit

            checkConnection();

          /*  if (contentTxt.getText().toString() == "") {
                Toast.makeText(SellaSim.this, "Please add a Kit Number...", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(SellaSim.this, "Hold on, we are processing...", Toast.LENGTH_LONG).show();


                EditText txtBalance = (EditText) findViewById(R.id.txtamontsimsell);

                amount = txtBalance.getText().toString();

                String urls = Config.BASE_URL + "app/sellkit//tester/lastname/" + username + "/" + Network + "/" + contentTxt.getText() + "/" + amount + "/";


                new SellaSim.JSONTask().execute(urls);
            }*/


        }
    }

    private void checkConnection() {

        if (isOnline()) {
            if (contentTxt.getText().toString() == "") {
                Toast.makeText(SellaSim.this, "Please add a Kit Number...", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SellaSim.this, "Hold on, we are processing...", Toast.LENGTH_LONG).show();


                EditText txtBalance = (EditText) findViewById(R.id.txtamontsimsell);

                amount = txtBalance.getText().toString();

               // String urls = Config.BASE_URL + "app/sellkit//tester/lastname/" + username + "/" + Network + "/" + contentTxt.getText() + "/" + amount + "/";

                sendSellKit(amount,username,contentTxt.getText().toString());
            }
        } else {
            Toast.makeText(SellaSim.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

        }
    }

    private void sendSellKit(String amount, String userId,String kitNumber) {
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "amount ", amount);
        data.append(FormData.TYPE_CONTENT_TEXT, "username  ", userId);
        data.append(FormData.TYPE_CONTENT_TEXT, "kitnumber ", kitNumber);

        HttpRequest request = new HttpRequest(getApplicationContext());
        request.setOnReadyStateChangeListener(this);
        request.setOnErrorListener(this);
        request.open("POST", urls);
        request.send(data);
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

    @Override
    public void onReadyStateChange(HttpRequest request, int readyState) {
        switch (readyState) {
            case HttpRequest.STATE_DONE:
                switch (request.getStatus()) {
                    case HttpURLConnection.HTTP_OK:
                        dialog.dismiss();
                        Log.i("TAG", request.getResponseText());
                        try {


                            JSONObject jsonObject = new JSONObject(request.getResponseText());

                            String code = jsonObject.getString(("Status"));

                            String messagedetails = jsonObject.getString(("Description"));

                            if (code.equals("ERROR")) {
                                Toast.makeText(SellaSim.this, messagedetails.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                // show success
                                Toast.makeText(SellaSim.this, "Your Query has been sent to head office, we will call you soon!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SellaSim.this, "Error with your Query Request", Toast.LENGTH_LONG).show();


                        }
                }
        }

    }

    @Override
    public void onError(HttpRequest request, int readyState, short error, Exception exception) {

    }


    public class JSONTaska extends AsyncTask<String, String, String> {


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
                Balance = parentObject.getString("Balance");
                Discount = parentObject.getString("discount");


                return Balance;


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

                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Balance", "0.00");
                    editor.commit();
                    Balance = "0.00";
                    TextView txtBalance = (TextView) findViewById(R.id.txtSellSIMTopUpBalance);
                    txtBalance.setText("R 0.00");


                }
                if (Status.equals("SUCCESS")) {
                    TextView txtBalance = (TextView) findViewById(R.id.txtSellSIMTopUpBalance);
                    txtBalance.setText("Balance: R " + Balance.toString());

                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("Balance", Balance);
                    // editor.putString("Discount", Discount);
                    editor.commit();

                } else {


                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Balance", "0.00");
                    editor.commit();
                    Balance = "0.00";
                    TextView txtBalance = (TextView) findViewById(R.id.txtSellSIMTopUpBalance);
                    txtBalance.setText("Balance: R" + Balance.toString());


                }
            }


        }

    }


/*
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
            // Toast.makeText(MySims.this, "Please Retry", Toast.LENGTH_LONG).show();
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (Status != null) {
                if (Status.equals("Complete")) {
                    Toast.makeText(SellaSim.this, "Kit is sold " + RicasTATUS.toString(), Toast.LENGTH_LONG).show();
                    String urls = Config.BASE_URL + "app/sellkit//tester/lastname/" + username + "/" + Network + "/" + contentTxt.getText() + "/test/";
                    new SellaSim.JSONTask().execute(urls);
                }

                if (Status.equals("Issue")) {
                    Toast.makeText(SellaSim.this, "Kit is sold " + RicasTATUS.toString(), Toast.LENGTH_LONG).show();
                }
            }

        }


    }
*/

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

