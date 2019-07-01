package com.ubits.payflow.payflow_network.General;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class ForgotPassword extends AppCompatActivity {

    String username, Status;
    Button btnReset;
    private TextView tvTitle;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        tvTitle = (TextView) findViewById(R.id.tv_titlebar);
        tvTitle.setText("Forgot Password");

        backButton = (ImageView) findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Forgot Password
        btnReset = (Button) findViewById(R.id.btnResetPassword);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline()) {
                    EditText txtUsername = (EditText) findViewById(R.id.txtCellNumberPassword);
                    username = txtUsername.getText().toString();

                    Toast.makeText(ForgotPassword.this, "Hold on while we check if you an Agent with us...", Toast.LENGTH_LONG).show();

                    if (username.equals("")) {

                        Toast.makeText(ForgotPassword.this, "Please enter your cell number", Toast.LENGTH_LONG).show();
                    } else {
                        String urls = Config.BASE_URL + "app/resetpassword//tester/lastname/" + username + "/ubits.co.za/pass/test/";
                        new ForgotPassword.JSONTask().execute(urls);
                    }

                } else {
                    Toast.makeText(ForgotPassword.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(ForgotPassword.this, "Please contact On Track Mobile, we cant find you on our side..", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ForgotPassword.this, "Your password has been sent to you via SMS", Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Login", "No");
                    editor.putString("UserName", username);
                    editor.commit();
                    startActivity(new Intent(ForgotPassword.this, Login.class));
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
