package com.ubits.payflow.payflow_network.General;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class Dashboard extends AppCompatActivity {

    ProgressBar myprogressBar, myprogressBar1, myprogressBar3, myprogressBar4;
    TextView progressingTextView, progressingTextView1, progressingTextView3, progressingTextView4, tvTitle;
    Handler progressHandler = new Handler();
    int i = 0;
    private ImageView backBtn;
    String airtimesold, AirtimeT, Status, KitsT, Balance, Discount, username, password, kitssold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_dashboard);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myprogressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressingTextView = (TextView) findViewById(R.id.txtDayAirtime);

        myprogressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressingTextView1 = (TextView) findViewById(R.id.txtDayKits);

        myprogressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressingTextView3 = (TextView) findViewById(R.id.txtDayProfit);

        myprogressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressingTextView4 = (TextView) findViewById(R.id.txtDayTargets);

        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        String urls = Config.BASE_URL + "app/dashboard//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";

        new Dashboard.JSONTask().execute(urls);

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
                Status = parentObject.optString("Status");
                Balance = parentObject.optString("Balance").length() > 0 ? parentObject.getString("Balance") : "0.0";
                Discount = parentObject.optString("current");
                airtimesold = parentObject.optString("airtimesold");
                kitssold = parentObject.optString("kitssold");
                AirtimeT = parentObject.optString("AirtimeT");
                KitsT = parentObject.optString("KitsT");

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
                    TextView txtBalance = (TextView) findViewById(R.id.txtPaymentsBalanceDash);
                    txtBalance.setText("R 0.00");

                }
                if (Status.equals("SUCCESS")) {
                    TextView txtBalance = (TextView) findViewById(R.id.txtPaymentsBalanceDash);
                    txtBalance.setText("Available Balance: R " + Balance.toString());

                    TextView txtCurrent = (TextView) findViewById(R.id.txtcurrent);
                    txtCurrent.setText("Current Balance: R " + Discount.toString());


                    // My pROFIT
                    new Thread(new Runnable() {
                        public void run() {
                            while (i < 100) {
                                i += 10;
                                progressHandler.post(new Runnable() {
                                    public void run() {
                                        myprogressBar.setProgress(i);
                                        progressingTextView.setText("R" + airtimesold + " ");
                                    }
                                });
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();


                    new Thread(new Runnable() {
                        public void run() {
                            while (i < 100) {
                                i += 10;
                                progressHandler.post(new Runnable() {
                                    public void run() {
                                        myprogressBar1.setProgress(i);
                                        progressingTextView1.setText("" + kitssold + " ");
                                    }
                                });
                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();


                    new Thread(new Runnable() {
                        public void run() {
                            while (i < 100) {
                                i += 10;
                                progressHandler.post(new Runnable() {
                                    public void run() {
                                        myprogressBar3.setProgress(i);
                                        progressingTextView3.setText("" + AirtimeT + " %");
                                    }
                                });
                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();


                    new Thread(new Runnable() {
                        public void run() {
                            while (i < 100) {
                                i += 10;
                                progressHandler.post(new Runnable() {
                                    public void run() {
                                        myprogressBar4.setProgress(i);
                                        progressingTextView4.setText("" + KitsT + " %");
                                    }
                                });
                                try {
                                    Thread.sleep(400);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();


                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("Balance", Balance);
                    // editor.putString("Discount", Discount);
                    editor.commit();

                }
            } else {

                SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtPaymentsBalanceDash);
                txtBalance.setText("Balance: R" + Balance.toString());

            }

        }

    }

}
