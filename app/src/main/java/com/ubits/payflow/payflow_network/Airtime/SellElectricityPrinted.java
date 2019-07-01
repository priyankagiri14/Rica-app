package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SellElectricityPrinted extends AppCompatActivity {

    String Status,Balance,Discount,username,password,Network,meternumber,amount,user,Description,cellnumber, Token;
    Button btnPurchase ,btn1, btn2, btn3, btn4;
    EditText txtamount,txtmeternumber,txtcellnumbere;
    TextView txtbatchescount;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_electricity_printed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnPurchase =  (Button) findViewById(R.id.btnAllocate);
        txtamount =  (EditText) findViewById(R.id.txtelectricityamount);
        txtmeternumber = (EditText) findViewById(R.id.txtmeternumber);
        txtbatchescount = (TextView) findViewById(R.id.txtbatchescount);
        txtcellnumbere =  (EditText) findViewById(R.id.txtcellnumbere);
        btn1=  (Button) findViewById(R.id.btn1e);
        btn2=  (Button) findViewById(R.id.btn2e);
        btn3=  (Button) findViewById(R.id.btn3e);
        btn4=  (Button) findViewById(R.id.btn4e);


        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        String urls = "http://ezaga.co.za/app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";

        new SellElectricityPrinted.JSONTaskb().execute(urls);


        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("50");

            }


        });


        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("100");

            }


        });

        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("200");

            }


        });

        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("250");

            }


        });



        //Purchase Airtime
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtbatchescount.setText("Processing Transaction...");


                //Validate text amount

                amount = txtamount.getText().toString();

                if (amount.toString() == "") {

                    Toast.makeText(SellElectricityPrinted.this, "Please enter an amount", Toast.LENGTH_LONG).show();

                }else{
                    //Validate text cell number

                    meternumber = txtmeternumber.getText().toString();
                    cellnumber = txtcellnumbere.getText().toString();

                    if (meternumber == "") {

                        Toast.makeText(SellElectricityPrinted.this, "Please enter a Meter Number", Toast.LENGTH_LONG).show();



                    }else{

                        if (cellnumber == "") {

                            Toast.makeText(SellElectricityPrinted.this, "Please enter a Valid Cell Number to send Airtime To", Toast.LENGTH_LONG).show();


                        }else{


                            pd=new ProgressDialog(SellElectricityPrinted.this);
                            pd.setTitle("Processing Transaction");
                            pd.setMessage("Processing...Please wait");
                            pd.show();

                            Description = "EZAGALIVE";

                            String urls = "http://ezaga.co.za/app/electricity//"+ username +"/"+ Description +"/" + meternumber + "/" + amount + "/" + username + "/" + cellnumber + "/EZAGA/";

                            //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000002/CSPC/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/CSPC/";

                            new SellElectricityPrinted.JSONTask().execute(urls);

                        }



                    }



                }

            }


        });





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
                String Description = parentObject.getString("Description");
                Token = parentObject.getString("Token");

                return Description;




            } catch (MalformedURLException e) {
                pd.dismiss();
                e.printStackTrace();

            } catch (IOException e) {
                pd.dismiss();
                e.printStackTrace();

            } catch (JSONException e) {
                pd.dismiss();
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



            if (Status.equals("success") ||Status.equals("SUCCESS") ) {

                String urls = "http://ezaga.co.za/app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";


                new SellElectricityPrinted.JSONTaskb().execute(urls);

                txtbatchescount.setText("Token: " +  Token);
                txtamount.setText("");
                txtmeternumber.setText("");
                txtcellnumbere.setText("");

                pd.dismiss();
                Toast.makeText(SellElectricityPrinted.this, "Transaction Complete", Toast.LENGTH_LONG).show();



            }else{
                pd.dismiss();

                txtbatchescount.setText(result.toString());
                //Display Error
                Toast.makeText(SellElectricityPrinted.this, "Transaction Error", Toast.LENGTH_LONG).show();

            }


        }
    }



    public class JSONTaskb extends AsyncTask<String, String, String> {


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
                Balance= parentObject.getString("Balance");
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

            if (Status.equals("ERROR") ) {

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtTopUpByElectricity);
                txtBalance.setText("R 0.00");


            }  if (Status.equals("SUCCESS")) {
                TextView txtBalance = (TextView) findViewById(R.id.txtTopUpByElectricity);
                txtBalance.setText("Balance: R "+ Balance.toString());

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("Balance", Balance);
                // editor.putString("Discount", Discount);
                editor.commit();

            } else {


                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtTopUpByElectricity);
                txtBalance.setText("Balance: R"+ Balance.toString());





            }


        }

    }


}

