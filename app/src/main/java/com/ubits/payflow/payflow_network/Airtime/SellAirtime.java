package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SellAirtime extends AppCompatActivity implements View.OnClickListener {

    String Status, Balance, Discount, username, password, Network, cellnumber, amount, user, Description, chk;
    TextView txtNetwork;
    EditText txtamount, txtcellnumber;
    ProgressDialog pd;
    Button btnPurchase, btn1, btn2, btn3, btn4;
    private ImageView backBtn;
    private TextView tvTitlebar;

    private ImageView ivMtnAirtime, imgVodacomAirtime, ivTelkomAirtime, ivCellCAirtime, imgMtnSecond,
            imgVodacomSecond, imgTelkomSecond, imgCellCSecond;
    private ImageView imgCbOne, imgCbTwo, imgCbThree, imgCbFour;


    private Bitmap bmp;
    int id = 0;
    private int displayWidth;
    private int displayHeight;
    private float scaleWidth = 1;
    private float scaleHeight = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_airtime);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        tvTitlebar = (TextView) findViewById(R.id.tv_titlebar);
        tvTitlebar.setText("Sell Airtime");

        txtNetwork = (TextView) findViewById(R.id.txtNetwork);
        txtamount = (EditText) findViewById(R.id.txtAmount);
        txtcellnumber = (EditText) findViewById(R.id.txtCellNumber);
        txtcellnumber.setVisibility(View.GONE);
        btnPurchase = (Button) findViewById(R.id.btnPurchase);

        ivMtnAirtime = (ImageView) findViewById(R.id.iv_mtn);
        imgVodacomAirtime = (ImageView) findViewById(R.id.iv_vodacom);
        ivCellCAirtime = (ImageView) findViewById(R.id.iv_CellC);
        ivTelkomAirtime = (ImageView) findViewById(R.id.iv_Telkom);


        imgMtnSecond = (ImageView) findViewById(R.id.iv_mtn_second);
        imgVodacomSecond = (ImageView) findViewById(R.id.iv_vodacom_second);
        imgCellCSecond = (ImageView) findViewById(R.id.iv_CellC_second);
        imgTelkomSecond = (ImageView) findViewById(R.id.iv_Telkom_second);


        imgCbOne = (ImageView) findViewById(R.id.img1);
        imgCbTwo = (ImageView) findViewById(R.id.img2);
        imgCbThree = (ImageView) findViewById(R.id.img3);
        imgCbFour = (ImageView) findViewById(R.id.img4);


        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        ivMtnAirtime.setVisibility(View.VISIBLE);
        imgVodacomAirtime.setVisibility(View.VISIBLE);
        ivCellCAirtime.setVisibility(View.VISIBLE);
        ivTelkomAirtime.setVisibility(View.VISIBLE);


        ivMtnAirtime.setOnClickListener(this);
        imgVodacomAirtime.setOnClickListener(this);
        ivCellCAirtime.setOnClickListener(this);
        ivTelkomAirtime.setOnClickListener(this);


        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.mtn);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayWidth = dm.widthPixels;
        displayHeight = dm.heightPixels - 100;


        btn1.setBackgroundColor(btn1.getContext().getResources().getColor(R.color.colorPrimary));
        btn2.setBackgroundColor(btn2.getContext().getResources().getColor(R.color.colorPrimary));
        btn3.setBackgroundColor(btn3.getContext().getResources().getColor(R.color.colorPrimary));
        btn4.setBackgroundColor(btn4.getContext().getResources().getColor(R.color.colorPrimary));

        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";

        new SellAirtime.JSONTaskb().execute(urls);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("5");
                txtcellnumber.setVisibility(View.VISIBLE);
                btn1.setBackgroundColor(btn1.getContext().getResources().getColor(R.color.color_drak_blue));
                btn2.setBackgroundColor(btn2.getContext().getResources().getColor(R.color.colorPrimary));
                btn3.setBackgroundColor(btn3.getContext().getResources().getColor(R.color.colorPrimary));
                btn4.setBackgroundColor(btn4.getContext().getResources().getColor(R.color.colorPrimary));

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("10");
                txtcellnumber.setVisibility(View.VISIBLE);
                btn1.setBackgroundColor(btn1.getContext().getResources().getColor(R.color.colorPrimary));
                btn2.setBackgroundColor(btn2.getContext().getResources().getColor(R.color.color_drak_blue));
                btn3.setBackgroundColor(btn3.getContext().getResources().getColor(R.color.colorPrimary));
                btn4.setBackgroundColor(btn4.getContext().getResources().getColor(R.color.colorPrimary));

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("15");
                txtcellnumber.setVisibility(View.VISIBLE);
                btn1.setBackgroundColor(btn1.getContext().getResources().getColor(R.color.colorPrimary));
                btn2.setBackgroundColor(btn2.getContext().getResources().getColor(R.color.colorPrimary));
                btn3.setBackgroundColor(btn3.getContext().getResources().getColor(R.color.color_drak_blue));
                btn4.setBackgroundColor(btn4.getContext().getResources().getColor(R.color.colorPrimary));

            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("25");
                txtcellnumber.setVisibility(View.VISIBLE);

                btn1.setBackgroundColor(btn1.getContext().getResources().getColor(R.color.colorPrimary));
                btn2.setBackgroundColor(btn2.getContext().getResources().getColor(R.color.colorPrimary));
                btn3.setBackgroundColor(btn3.getContext().getResources().getColor(R.color.colorPrimary));
                btn4.setBackgroundColor(btn4.getContext().getResources().getColor(R.color.color_drak_blue));

            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline()) {
                    txtNetwork.setText("Processing Trasnaction...");
                    amount = txtamount.getText().toString();

                    if (amount.equals("")) {
                        Toast.makeText(SellAirtime.this, "Please enter an amount", Toast.LENGTH_LONG).show();

                    } else {
                        //Validate text cell number
                        cellnumber = txtcellnumber.getText().toString();

                        if (cellnumber == "") {

                        } else {

                            if (cellnumber.length() == 10) {
                                Description = "ON";
                                String urls = Config.BASE_URL + "app/airtime//" + username + "/" + Description + "/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/ON/";
                                new SellAirtime.JSONTask().execute(urls);
                            } else {
                                Toast.makeText(SellAirtime.this, "Please enter a correct cell number", Toast.LENGTH_LONG).show();
                                txtNetwork.setText("Error with Cell Number");
                            }

                        }
                    }
                } else {
                    Toast.makeText(SellAirtime.this, "Check network connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    public void onClick(View v) {
        if (v.getId() == backBtn.getId()) {
            finish();
        } else if (v.getId() == R.id.iv_mtn) {
            Network = "MTN";
            txtNetwork.setText("MTN");

            imgMtnSecond.setVisibility(View.VISIBLE);
            ivMtnAirtime.setVisibility(View.GONE);
            imgVodacomSecond.setVisibility(View.GONE);
            imgVodacomAirtime.setVisibility(View.VISIBLE);
            imgCellCSecond.setVisibility(View.GONE);
            ivCellCAirtime.setVisibility(View.VISIBLE);
            imgTelkomSecond.setVisibility(View.GONE);
            ivTelkomAirtime.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.VISIBLE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_vodacom) {
            txtcellnumber.setVisibility(View.GONE);
            txtNetwork.setText("Vodacom");

            imgVodacomSecond.setVisibility(View.VISIBLE);
            imgVodacomAirtime.setVisibility(View.GONE);

            imgMtnSecond.setVisibility(View.GONE);
            ivMtnAirtime.setVisibility(View.VISIBLE);

            imgCellCSecond.setVisibility(View.GONE);
            ivCellCAirtime.setVisibility(View.VISIBLE);


            imgTelkomSecond.setVisibility(View.GONE);
            ivTelkomAirtime.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.VISIBLE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);

        } else if (v.getId() == R.id.iv_CellC) {
            Network = "CellC";
            txtNetwork.setText("CellC");

            imgCellCSecond.setVisibility(View.VISIBLE);
            ivCellCAirtime.setVisibility(View.GONE);

            imgVodacomSecond.setVisibility(View.GONE);
            imgVodacomAirtime.setVisibility(View.VISIBLE);

            imgMtnSecond.setVisibility(View.GONE);
            ivMtnAirtime.setVisibility(View.VISIBLE);

            imgTelkomSecond.setVisibility(View.GONE);
            ivTelkomAirtime.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.VISIBLE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_Telkom) {

            Network = "Telkom";
            txtNetwork.setText("Telkom");

            imgTelkomSecond.setVisibility(View.VISIBLE);
            ivTelkomAirtime.setVisibility(View.GONE);


            imgCellCSecond.setVisibility(View.GONE);
            ivCellCAirtime.setVisibility(View.VISIBLE);

            imgVodacomSecond.setVisibility(View.GONE);
            imgVodacomAirtime.setVisibility(View.VISIBLE);

            imgMtnSecond.setVisibility(View.GONE);
            ivMtnAirtime.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.VISIBLE);

        }
    }
    /*
     *
     * */

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
                    TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalance);
                    txtBalance.setText("R 0.00");

                }
                if (Status.equals("SUCCESS")) {
                    TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalance);
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
                    TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalance);
                    txtBalance.setText("Balance: R" + Balance.toString());

                }

            }

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

            if (Status != null && result != null) {
                if (Status.substring(0, 1).equals("S")) {
                    pd.dismiss();
                    String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";
                    new SellAirtime.JSONTaskb().execute(urls);
                    txtNetwork.setText("Airtime Purchase Complete..");
                    txtamount.setText("");
                    txtcellnumber.setText("");
                    Toast.makeText(SellAirtime.this, "Transaction Complete", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SellAirtime.this, "Transaction Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(SellAirtime.this);
            pd.setMessage("Processing Please wait...");
            pd.setCancelable(false);
            pd.show();

        }
    }

    /*Check Network Connection
     * */

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
