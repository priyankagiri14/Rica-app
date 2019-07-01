package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BuyBundle extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog pd;
    EditText txtBuyCellNumber;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btnPurchase;
    String cellnumber, amount, user, Status, username, Description, productcode, Balance, password;

    private TextView tvTitle;
    private String Network = "";
    private LinearLayout liHideLayout;

    private ImageView ivMtn, ivVodacom, ivCellc, ivTellkom, ivMtnLarze, ivVodacomLarze,
            ivCellcLarze, ivTellkomLarze, imgCbOne, imgCbTwo, imgCbThree, imgCbFour, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_bundle);

        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", null);
        password = "123";

        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText("Sell Bundle");

        liHideLayout = (LinearLayout) findViewById(R.id.li_hide_layout);

        backButton = (ImageView) findViewById(R.id.back_btn);
        ivMtn = (ImageView) findViewById(R.id.iv_mtn);
        ivVodacom = (ImageView) findViewById(R.id.iv_vodacom);
        ivCellc = (ImageView) findViewById(R.id.iv_CellC);
        ivTellkom = (ImageView) findViewById(R.id.iv_Telkom);

        ivMtnLarze = (ImageView) findViewById(R.id.iv_mtn_second);
        ivVodacomLarze = (ImageView) findViewById(R.id.iv_vodacom_second);
        ivCellcLarze = (ImageView) findViewById(R.id.iv_CellC_second);
        ivTellkomLarze = (ImageView) findViewById(R.id.iv_Telkom_second);

        imgCbOne = (ImageView) findViewById(R.id.img1);
        imgCbTwo = (ImageView) findViewById(R.id.img2);
        imgCbThree = (ImageView) findViewById(R.id.img3);
        imgCbFour = (ImageView) findViewById(R.id.img4);

        String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";
        new BuyBundle.JSONTaskb().execute(urls);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);

        btnPurchase = (Button) findViewById(R.id.btnbundlePurchase);
        txtBuyCellNumber = (EditText) findViewById(R.id.txtBuyCellNumber);

        ivMtn.setVisibility(View.VISIBLE);
        ivVodacom.setVisibility(View.VISIBLE);
        ivCellc.setVisibility(View.VISIBLE);
        ivTellkom.setVisibility(View.VISIBLE);

        ivMtn.setOnClickListener(this);
        ivVodacom.setOnClickListener(this);
        ivCellc.setOnClickListener(this);
        ivTellkom.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
        backButton.setOnClickListener(this);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);


        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        btn4.setVisibility(View.GONE);
        btn5.setVisibility(View.GONE);
        btn6.setVisibility(View.GONE);
        btn7.setVisibility(View.GONE);
        btn8.setVisibility(View.GONE);
        btn8.setVisibility(View.GONE);
    }

    /*
     * On Click Event on Button
     * */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_btn) {
            finish();
        } else if (v.getId() == R.id.btn1) {
            liHideLayout.setVisibility(View.GONE);
            txtBuyCellNumber.setVisibility(View.VISIBLE);

            if (Network == "MTN") {
                productcode = "MTN157";
                btn1.setText("5MB R4");
            }

            if (Network == "Vodacom") {
                productcode = "Vod214";
                btn1.setText("MyMeg 20 R10");
            }

            if (Network == "CellC") {
                productcode = "CC130";
                btn1.setText("50MB R16");
            }

            if (Network == "Telkom") {
                productcode = "Tel189";
                btn1.setText("25MB R7.25");
            }

            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }
//...........Button 2
        else if (v.getId() == R.id.btn2) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN153";
                btn2.setText("20MB R12");

            }
            if (Network == "Vodacom") {
                productcode = "Vod215";
                btn2.setText("Vodacom MyMeg 50");
            }
            if (Network == "CellC") {
                productcode = "CC131";
                btn2.setText("25MB R10");
            }
            if (Network == "Telkom") {
                productcode = "Tel190";
                btn2.setText("50MB R14.50");
            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }
//................................................Button 3.................
        else if (v.getId() == R.id.btn3) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN155";
                btn3.setText("50MB R25");

            }
            if (Network == "Vodacom") {
                productcode = "Vod205";
                btn3.setText("100MB R29");
            }
            if (Network == "CellC") {
                productcode = "CC128";
                btn3.setText("300MB R65");
            }
            if (Network == "Telkom") {
                productcode = "Tel183";
                btn3.setText("100MB R29");

            }
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }
//.........................................Button 4........................
        else if (v.getId() == R.id.btn4) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN151";
                btn4.setText("100MB R35");

            }
            if (Network == "Vodacom") {
                productcode = "Vod216";
                btn4.setText("MyMeg 150 R39");

            }
            if (Network == "CellC") {
                productcode = "CC127";
                btn4.setText("500MB R99");
            }
            if (Network == "Telkom") {
                productcode = "Tel185";

                btn4.setText("250MB R39.");
            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }
//...........................................Button 5....................
        else if (v.getId() == R.id.btn5) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN144";
                btn5.setText("300MB R85");
            }
            if (Network == "Vodacom") {
                productcode = "Vod206";
                btn5.setText("250MB R59");
            }
            if (Network == "CellC") {
                productcode = "CC133";

                btn5.setText("100MB X12 R99");
            }
            if (Network == "Telkom") {
                productcode = "Tel186";
                btn5.setText("500MB R69");

            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }
//..........................................Button 6..............................
        else if (v.getId() == R.id.btn6) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN145";
                btn6.setText("500MB R105");
            }
            if (Network == "Vodacom") {
                productcode = "Vod207";

                btn6.setText("500MB R99");
            }
            if (Network == "CellC") {
                productcode = "CC115";
                btn6.setText("1GB R149");
            }
            if (Network == "Telkom") {
                productcode = "Tel187";
                btn6.setText("1GB R99");
            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);
        }

//...............................................Button 7
        else if (v.getId() == R.id.btn7) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN146";
                btn7.setText("1GB R160");
            }
            if (Network == "Vodacom") {
                productcode = "Vod208";


                btn7.setText("1GB R149");
            }
            if (Network == "CellC") {
                productcode = "CC132";
                btn7.setText("200MB X12 R159");
            }
            if (Network == "Telkom") {
                productcode = "Tel188";
                btn7.setText("5GB R299");
            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn8.setVisibility(View.GONE);

        } else if (v.getId() == R.id.btn8) {
            txtBuyCellNumber.setVisibility(View.VISIBLE);
            liHideLayout.setVisibility(View.GONE);

            if (Network == "MTN") {
                productcode = "MTN147";
                btn8.setText("2GB R260");
            }
            if (Network == "Vodacom") {
                productcode = "Vod209";
                btn8.setText("GB R249");
            }
            if (Network == "CellC") {
                productcode = "CC116";
                btn8.setText("2GB R245");

            }
            if (Network == "Telkom") {
                productcode = "Tel191";
                btn8.setText("10GB R499");
            }

            btn1.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
        }

        /*
         * Buy Bundle
         *
         * */
        else if (v.getId() == R.id.btnbundlePurchase) {

            if (Utils.isOnline(BuyBundle.this)) {
                cellnumber = txtBuyCellNumber.getText().toString();
                if (Network.toString() == "") {

                    Utils.showToasMessage(BuyBundle.this, "Please select network");

                } else if (productcode == null || productcode.toString() == "") {

                    Utils.showToasMessage(BuyBundle.this, "Please enter an amount");

                } else {
                    //Validate text cell number
                    amount = productcode != null ? productcode : "";

                    if (cellnumber == "") {
                    } else {

                        if (cellnumber.length() == 10) {
                            Description = "ON Track";
                            String urls = Config.BASE_URL + "app/bundle//" + username + "/" + Description + "/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/Ontrack/";
                            pd = new ProgressDialog(BuyBundle.this);
                            pd.setTitle("Processing Transaction");
                            pd.setMessage("Processing...Please wait");
                            pd.show();
                            new BuyBundle.JSONTask().execute(urls);

                        } else {
                            Utils.showToasMessage(BuyBundle.this, "Please enter a correct cell number");
                        }
                    }
                }

            } else {
                Utils.showToasMessage(BuyBundle.this, "Check network connection");
            }


        } else if (v.getId() == R.id.iv_mtn) {
            Network = "MTN";
            ivMtnLarze.setVisibility(View.VISIBLE);
            ivMtn.setVisibility(View.GONE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.VISIBLE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);

        } else if (v.getId() == R.id.iv_vodacom) {
            Network = "Vodacom";
            ivVodacomLarze.setVisibility(View.VISIBLE);
            ivVodacom.setVisibility(View.GONE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);
            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.VISIBLE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_CellC) {
            Network = "CellC";
            ivCellcLarze.setVisibility(View.VISIBLE);
            ivCellc.setVisibility(View.GONE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);
            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.VISIBLE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_Telkom) {
            Network = "Telkom";
            ivTellkomLarze.setVisibility(View.VISIBLE);
            ivTellkom.setVisibility(View.GONE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);
            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.VISIBLE);
        }

        if (Network == "MTN") {

            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);

            btn5.setVisibility(View.VISIBLE);
            btn6.setVisibility(View.VISIBLE);
            btn7.setVisibility(View.VISIBLE);
            btn8.setVisibility(View.VISIBLE);

            btn1.setText("5MB R4.00");
            btn2.setText("20MB R12.00");
            btn3.setText("50MB R25.00");
            btn4.setText("100MB R35.00");

            btn5.setText("300MB R85.00");
            btn6.setText("5500MB R105.00");
            btn7.setText("1GB R160.00");
            btn8.setText("2GB R260.00");

        }
        if (Network == "Vodacom") {

            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);

            btn5.setVisibility(View.VISIBLE);
            btn6.setVisibility(View.VISIBLE);
            btn7.setVisibility(View.VISIBLE);
            btn8.setVisibility(View.VISIBLE);

            btn1.setText("MyMeg 20 R10");
            btn2.setText("MyMeg 50 R15");
            btn3.setText("100MB R29");
            btn4.setText("MyMeg 150 R39");

            btn5.setText("50MB R59");
            btn6.setText("500MB R99");
            btn7.setText("1GB R149");
            btn8.setText("2GB R249");

        }
        if (Network == "CellC") {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            btn5.setVisibility(View.VISIBLE);
            btn6.setVisibility(View.VISIBLE);
            btn7.setVisibility(View.VISIBLE);
            btn8.setVisibility(View.VISIBLE);


            btn1.setText("25MB R7.25");
            btn2.setText("50MB R14.50");
            btn3.setText("100MB R29");
            btn4.setText("250MB R39");
            btn5.setText("500MB R69");
            btn6.setText("1GB R99");
            btn7.setText("5GB R299");
            btn8.setText("10GB R499");

        }
        if (Network == "Telkom") {
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.VISIBLE);
            btn5.setVisibility(View.VISIBLE);
            btn6.setVisibility(View.VISIBLE);
            btn7.setVisibility(View.VISIBLE);
            btn8.setVisibility(View.VISIBLE);


            btn1.setText("50MB R16");
            btn2.setText("25MB R10");
            btn3.setText("300MB R65");
            btn4.setText("500MB R99");
            btn5.setText("100MB X12 R99");
            btn6.setText("1GB R149");
            btn7.setText("200MB X12 R159");
            btn8.setText("2GB R245");
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
                System.out.println("parent object===>" + parentObject);
                System.out.println("status===>" + parentObject.getString("Status"));
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

            if (result != null) {
                if (Status.substring(0, 1).equals("S")) {
                    pd.dismiss();
                    txtBuyCellNumber.setText("");

                    String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ontrack.co.za/" + password + "/test/";
                    new BuyBundle.JSONTaskb().execute(urls);
                    Utils.showToasMessage(BuyBundle.this, "Transaction Complete");

                } else {
                    pd.dismiss();
                    Utils.showToasMessage(BuyBundle.this, "Transaction Error");

                }
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
                Balance = parentObject.getString("Balance");
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
                    TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                    txtBalance.setText("R 0.00");
                }
                if (Status.equals("SUCCESS")) {
                    TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                    txtBalance.setText("Balance: R" + Balance.toString());
                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("Balance", Balance);
                    editor.commit();

                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Balance", "0.00");
                    editor.commit();
                    Balance = "0.00";
                    TextView txtBalance = (TextView) findViewById(R.id.txtBundleBalance);
                    txtBalance.setText("Balance: R" + Balance.toString());
                }
            }
        }
    }
}
