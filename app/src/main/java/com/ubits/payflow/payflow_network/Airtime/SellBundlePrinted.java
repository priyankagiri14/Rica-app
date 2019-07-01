package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Adapter;
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

public class SellBundlePrinted extends AppCompatActivity {


    ProgressDialog pd;
    Spinner sp;
    EditText txtBuyCellNumber;
    Button btn1, btn2, btn3, btn4,btn5, btn6, btn7, btn8,btnPurchase ;
    String[] networks={"Please Select a Network","MTN","Vodacom","CellC","Telkom"};
    String Network,cellnumber,amount,user,Status,username,Description,productcode,Balance,password;
    int[] images = {R.drawable.empty,R.drawable.mtn,R.drawable.vodacoms,R.drawable.cellcs,R.drawable.telkom};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_bundle_printed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        password = "123";

        String urls = "http://ezaga.co.za/app/account//tester/lastname/"+ username +"/ubits.co.za/" + password +"/test/";



        new SellBundlePrinted.JSONTaskb().execute(urls);

        btn1=  (Button) findViewById(R.id.btn1);
        btn2=  (Button) findViewById(R.id.btn2);
        btn3=  (Button) findViewById(R.id.btn3);
        btn4=  (Button) findViewById(R.id.btn4);
        btn5=  (Button) findViewById(R.id.btn5);
        btn6=  (Button) findViewById(R.id.btn6);
        btn7=  (Button) findViewById(R.id.btn7);
        btn8=  (Button) findViewById(R.id.btn8);
        btnPurchase=  (Button) findViewById(R.id.btnbundlePurchaseP);

        txtBuyCellNumber =  (EditText) findViewById(R.id.txtBuyCellNumberP);

        btn1.setVisibility(View.GONE);

        btn2.setVisibility(View.GONE);

        btn3.setVisibility(View.GONE);

        btn4.setVisibility(View.GONE);

        btn5.setVisibility(View.GONE);

        btn6.setVisibility(View.GONE);

        btn7.setVisibility(View.GONE);

        btn8.setVisibility(View.GONE);

        sp=(Spinner)findViewById(R.id.spinnerbuyairtime);

        Adapter adapter=new Adapter(this,networks,images);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                Network = networks[i];





                if (Network=="MTN") {





                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn4.setVisibility(View.VISIBLE);

                    btn5.setVisibility(View.VISIBLE);
                    btn6.setVisibility(View.VISIBLE);
                    btn7.setVisibility(View.VISIBLE);
                    btn8.setVisibility(View.VISIBLE);


                    btn1.setText("50 MB 1 Day R7");
                    btn2.setText("20 MB R12");
                    btn3.setText("100 MB R35");
                    btn4.setText("1 GIG R160");

                    btn5.setText("30 SMS R10");
                    btn6.setText("50 SMS R17");
                    btn7.setText("Whatsapp R12");
                    btn8.setText("3 GB R330");





                }if (Network=="Vodacom"){

                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn4.setVisibility(View.VISIBLE);

                    btn5.setVisibility(View.VISIBLE);
                    btn6.setVisibility(View.VISIBLE);
                    btn7.setVisibility(View.VISIBLE);
                    btn8.setVisibility(View.VISIBLE);


                    btn1.setText("10min Voice Bundle R2");
                    btn2.setText("30MB 1hr Data Bundle R4");
                    btn3.setText("60min Voice Bundle R5");
                    btn4.setText("250MB R59");

                    btn5.setText("500MB R99");
                    btn6.setText("1GB R149");
                    btn7.setText("200 SMS R45.00");
                    btn8.setText("MyGig 20 R999");


                }if (Network=="Telkom"){
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn4.setVisibility(View.VISIBLE);

                    btn5.setVisibility(View.VISIBLE);
                    btn6.setVisibility(View.VISIBLE);
                    btn7.setVisibility(View.VISIBLE);
                    btn8.setVisibility(View.VISIBLE);


                    btn1.setText("100MB R29");
                    btn2.setText("250MB R39");
                    btn3.setText("500MB R69");
                    btn4.setText("1GB R99");

                    btn5.setText("3GB R199");
                    btn6.setText("5GB R299");
                    btn7.setText("10GB R499");
                    btn8.setText("20GB R899");


                }if (Network=="CellC"){
                    btn1.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.VISIBLE);
                    btn3.setVisibility(View.VISIBLE);
                    btn4.setVisibility(View.VISIBLE);

                    btn5.setVisibility(View.VISIBLE);
                    btn6.setVisibility(View.VISIBLE);
                    btn7.setVisibility(View.VISIBLE);
                    btn8.setVisibility(View.VISIBLE);


                    btn1.setText("25MB R10");
                    btn2.setText("50MB R16");
                    btn3.setText("100MB R29");
                    btn4.setText("300MB R65");

                    btn5.setText("500MB R99");
                    btn6.setText("1GB R149");
                    btn7.setText("2GB R245");
                    btn8.setText("100 SMS R39.00");

                }



            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (Network=="MTN"){
                    productcode="MTN163";
                    btn1.setText("50 MB 1 Day R7");

                }if (Network=="Vodacom"){
                    productcode="Vod232";
                    btn1.setText("10min Voice Bundle R2");



                }if (Network=="CellC"){
                    productcode="CC131";


                    btn1.setText("25MB R10");

                }if (Network=="Telkom"){
                    productcode="Tel183";


                    btn1.setText("100MB R29");


                }

                btn2.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


        //Select BUTTON 2  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN153";
                    btn2.setText("20 MB R12");

                }if (Network=="Vodacom"){
                    productcode="Vod242";

                    btn2.setText("30MB 1hr Data Bundle R4");




                }if (Network=="CellC"){
                    productcode="CC130";


                    btn2.setText("50MB R16");

                }if (Network=="Telkom"){
                    productcode="Tel185";


                    btn2.setText("250MB R39");


                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$


        //Select BUTTON 3  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN151";

                    btn3.setText("100 MB R35");

                }if (Network=="Vodacom"){
                    productcode="Vod243";

                    btn3.setText("60min Voice Bundle R5");



                }if (Network=="CellC"){
                    productcode="CC129";


                    btn3.setText("100MB R29");


                }if (Network=="Telkom"){
                    productcode="Tel186";

                    btn3.setText("500MB R69");


                }

                btn1.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//Select BUTTON 4  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN146";

                    btn4.setText("1 GIG R160");


                }if (Network=="Vodacom"){
                    productcode="Vod206";

                    btn4.setText("250MB R59");





                }if (Network=="CellC"){
                    productcode="CC128";


                    btn4.setText("300MB R65");



                }if (Network=="Telkom"){
                    productcode="Tel187";

                    btn4.setText("1GB R99");


                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        // tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//Select BUTTON 5  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN139";


                    btn5.setText("30 SMS R10");

                }if (Network=="Vodacom"){
                    productcode="Vod207";

                    btn5.setText("500MB R99");



                }if (Network=="CellC"){
                    productcode="CC127";



                    btn5.setText("500MB R99");


                }if (Network=="Telkom"){
                    productcode="Tel192";

                    btn5.setText("3GB R199");

                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//Select BUTTON 6  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN140";


                    btn6.setText("50 SMS R17");

                }if (Network=="Vodacom"){
                    productcode="Vod208";


                    btn6.setText("1GB R149");




                }if (Network=="CellC"){
                    productcode="CC115";


                    btn6.setText("1GB R149");



                }if (Network=="Telkom"){
                    productcode="Tel188";


                    btn6.setText("5GB R299");


                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        //Select BUTTON 7  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN138";


                    btn7.setText("Whatsapp R12");

                }if (Network=="Vodacom"){
                    productcode="Vod211";


                    btn7.setText("200 SMS R45.00");




                }if (Network=="CellC"){
                    productcode="CC116";


                    btn7.setText("2GB R245");


                }if (Network=="Telkom"){
                    productcode="Tel191";

                    btn7.setText("10GB R499");


                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);

                btn8.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        //Select BUTTON 8  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network=="MTN"){
                    productcode="MTN154";


                    btn8.setText("3 GB R330");
                }if (Network=="Vodacom"){
                    productcode="Vod222";



                    btn8.setText("MyGig 20 R999");



                }if (Network=="CellC"){
                    productcode="CC114";


                    btn8.setText("100 SMS R39.00");

                }if (Network=="Telkom"){
                    productcode="Tel193";

                    btn8.setText("20GB R899");

                }

                btn1.setVisibility(View.GONE);

                btn3.setVisibility(View.GONE);

                btn4.setVisibility(View.GONE);

                btn5.setVisibility(View.GONE);

                btn6.setVisibility(View.GONE);

                btn7.setVisibility(View.GONE);

                btn2.setVisibility(View.GONE);


            }


        });


// tIL hERE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$



        //Purchase Airtime
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd=new ProgressDialog(SellBundlePrinted.this);
                pd.setTitle("Processing Transaction");
                pd.setMessage("Processing...Please wait");
                pd.show();

                cellnumber = txtBuyCellNumber.getText().toString();

                if (productcode.toString() == "") {

                    Toast.makeText(SellBundlePrinted.this, "Please enter an amount", Toast.LENGTH_LONG).show();

                }else{
                    //Validate text cell number

                    amount = productcode;

                    if (cellnumber == "") {



                    }else{

                        if (cellnumber.length() == 10) {

                            Description = "EZAGA";

                            String urls = "http://ezaga.co.za/app/bundle//"+ username +"/"+ Description +"/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/ezaga/";

                            //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000002/CSPC/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/CSPC/";

                            new SellBundlePrinted.JSONTask().execute(urls);

                        }else{

                            Toast.makeText(SellBundlePrinted.this, "Please enter a correct cell number", Toast.LENGTH_LONG).show();
                        }

                        //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000000/pepfizz/" + cellnumber + "/" + amount + "/" + Network + "/" + username + "/"+username+"/";

                        //  http://cspcapp.co.za/app/airtime//0786826455/hello/0786826455/3/MTN/0786826455/CSPC/



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

                return Description;




            } catch (MalformedURLException e) {

                e.printStackTrace();
                pd.dismiss();

            } catch (IOException e) {

                e.printStackTrace();
                pd.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                pd.dismiss();
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



            if (Status.substring(0,1).equals("S")) {
                // txtNetwork.setText("Airtime Purchase Complete..");
                // txtamount.setText("");
                pd.dismiss();
                txtBuyCellNumber.setText("");

                String urls = "http://ezaga.co.za/app/account//tester/lastname/"+ username +"/ubits.co.za/" + password +"/test/";



                new SellBundlePrinted.JSONTaskb().execute(urls);


                Toast.makeText(SellBundlePrinted.this, "Transaction Complete", Toast.LENGTH_LONG).show();




            }else{
                pd.dismiss();
                // txtNetwork.setText(result.toString());
                //Display Error
                Toast.makeText(SellBundlePrinted.this, "Transaction Error", Toast.LENGTH_LONG).show();

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

            if (Status.equals("ERROR")) {

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                txtBalance.setText("R 0.00");


            }  if (Status.equals("SUCCESS")) {
                TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                txtBalance.setText("Balance: R"+ Balance.toString());

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("Balance", Balance);

                editor.commit();

            } else {


                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                txtBalance.setText("Balance: R"+ Balance.toString());





            }


        }

    }




}
