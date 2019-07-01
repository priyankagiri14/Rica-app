package com.ubits.payflow.payflow_network.General;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

public class Login extends AppCompatActivity implements HttpRequest.OnErrorListener, HttpRequest.OnReadyStateChangeListener {
    private Button btnLogin, btnLinkToRegisterScreen, btnReset;
    private String username, Status, password, CustomerType, address = null;
    private Double Lat, Longs;
    private String devicedId, deviceManufacturer, deviceModel, status, deviceType, customerId, messagedetails;
    private ProgressDialog dialog;
    private String urlLogin = Config.BASE_URL+"app//login/index.php";
    private EditText txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        Status = sharedPreferences.getString("Login", "No");
        CustomerType = sharedPreferences.getString("CustomerType", "");

        if (Status.equals("Yes")) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } else {

            dialog = new ProgressDialog(this);
            txtUsername = findViewById(R.id.cellnumber);
            txtPassword = findViewById(R.id.password);
            /*
             * get Current Location of user
             * */
            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

            btnLogin = findViewById(R.id.btnLogin);
            btnReset = findViewById(R.id.btnForgotPassword);
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Login.this, ForgotPassword.class));
                }
            });
            btnLinkToRegisterScreen = findViewById(R.id.btnLinkToRegisterScreen);
            btnLinkToRegisterScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Login.this, "Please contact On Track Mobile to Register as an Agent", Toast.LENGTH_LONG).show();
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkConnection();
                }
            });

            getAddress();
        }
    }

    public void checkConnection() {
        status = "login";
        deviceType = "android";
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();

        if (Utils.isOnline(Login.this)) {
            if (username.equals("") || password.equals("")) {
                Utils.showToasMessage(Login.this, "Please enter your login details");
            } else {
                sendLoginData(username = txtUsername.getText().toString(), password = txtPassword.getText().toString(),
                        Lat, Longs, devicedId, deviceModel, deviceManufacturer, status, deviceType, address);
            }
        } else {
            Utils.showToasMessage(Login.this, "Check network connection");
        }
    }

    private void sendLoginData(String userName, String userPasword, Double lat, Double
            longs, String devicedId,
                               String deviceModel, String deviceManufacturer, String status, String
                                       deviceType, String address) {

        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        getAddress();

        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "cellNumber", userName);
        data.append(FormData.TYPE_CONTENT_TEXT, "password", userPasword);
        data.append(FormData.TYPE_CONTENT_TEXT, "latitude", String.valueOf(lat));
        data.append(FormData.TYPE_CONTENT_TEXT, "longitude", String.valueOf(longs));
        data.append(FormData.TYPE_CONTENT_TEXT, "deviceId", devicedId);
        data.append(FormData.TYPE_CONTENT_TEXT, "device_model", deviceModel);
        data.append(FormData.TYPE_CONTENT_TEXT, "manufacturer", deviceManufacturer);
        data.append(FormData.TYPE_CONTENT_TEXT, "status", status);
        data.append(FormData.TYPE_CONTENT_TEXT, "device_type", deviceType);
        data.append(FormData.TYPE_CONTENT_TEXT, "address", address != null ? address : "");

        HttpRequest request = new HttpRequest(getApplicationContext());
        request.setOnReadyStateChangeListener(this);
        request.setOnErrorListener(this);
        request.open("POST", urlLogin);
        request.send(data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
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
                            messagedetails = jsonObject.optString(("Description"));
                            customerId = jsonObject.optString(("Customer_ID"));

                            if (code.equals("ERROR")) {
                                Toast.makeText(Login.this, messagedetails.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                // show success
                                txtUsername.setText("");
                                txtPassword.setText("");

                                SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Login", "Yes");
                                editor.putString("UserName", customerId);
                                editor.commit();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Utils.showToasMessage(Login.this, "Error with your Query Request");
                        }
                }
        }
    }

    @Override
    public void onError(HttpRequest request, int readyState, short error, Exception exception) {
        Utils.showToasMessage(Login.this, "" + messagedetails);
        dialog.dismiss();
    }

    private void getAddress() {
        if (address != null) return;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                GPStracker gt = new GPStracker(getApplicationContext());
                Location l = gt.getLocation();
                if (l == null) {
                    Lat = 0.00;
                    Longs = 0.00;
                } else {
                    Lat = l.getLatitude();
                    Longs = l.getLongitude();
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    // Get Device Id
                    devicedId = Settings.Secure.getString(Login.this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    String manufacturer = Build.MANUFACTURER;
                    deviceModel = Build.MODEL;
                    if (deviceModel.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                        deviceModel = capitalize(deviceModel);
                    } else {
                        deviceModel = capitalize(manufacturer) + " " + deviceModel;
                    }
                    deviceManufacturer = Build.MANUFACTURER;
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(Login.this, Locale.getDefault());

                    addresses = geocoder.getFromLocation(Lat, Longs, 1);
                    if (addresses != null && addresses.size() > 0) {
                        address = addresses.get(0).getAddressLine(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
