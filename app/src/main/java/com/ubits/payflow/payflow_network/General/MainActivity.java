package com.ubits.payflow.payflow_network.General;

import android.annotation.SuppressLint;
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
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.ubits.payflow.payflow_network.Airtime.AirtimeStatement;
import com.ubits.payflow.payflow_network.Airtime.BuyBundle;
import com.ubits.payflow.payflow_network.Airtime.BuyElectricityn;
import com.ubits.payflow.payflow_network.Airtime.SellAirtime;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.Kits.MySimsn;
import com.ubits.payflow.payflow_network.Kits.Rican;
import com.ubits.payflow.payflow_network.Kits.SellaSim;
import com.ubits.payflow.payflow_network.Login.Login_Activity;
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
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HttpRequest.OnErrorListener, HttpRequest.OnReadyStateChangeListener {

    private String Status, Balance, Discount, username, password, printedproducts, address = "";

    private String cellNumber, statusApi, customerType, devicedId, model, deviceModel, deviceManufacturer, messagedetails, deviceType;

    private Double Lat, Longs;
    private ProgressDialog dialog;

    private String urlLogout = Config.BASE_URL + "app/login/logout.php";
    private ImageButton liAgentRegister;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListeners();


    }

    /**
     * initialise listeners
     */
    public void initListeners() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(this);


        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
        printedproducts = sharedPreferences.getString("Printed_Products", "No");
        String urls = Config.BASE_URL + "app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";

        new MainActivity.JSONTask().execute(urls);

        ImageButton btnBuyAirtime = (ImageButton) findViewById(R.id.btnBuyAirtime);
        btnBuyAirtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SellAirtime.class);
                intent.putExtra("network", "MTN");
                startActivity(intent);

            }

        });

        ImageButton btnAirtime = (ImageButton) findViewById(R.id.btnRicasm);

        btnAirtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Rican.class);
                intent.putExtra("network", "MTN");
                intent.putExtra("identity", "Rica");
                startActivity(intent);

            }

        });

        ImageButton btnSendMoney = (ImageButton) findViewById(R.id.btnTransfers);

        btnSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, BuyElectricityn.class);

                startActivity(intent);

            }

        });

        ImageButton btnBundles = (ImageButton) findViewById(R.id.btnBundles);

        btnBundles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BuyBundle.class);
                intent.putExtra("network", "MTN");
                startActivity(intent);
            }
        });
        ImageButton btnMyTransfers = (ImageButton) findViewById(R.id.btnSellaSim);
        btnMyTransfers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SellaSim.class);
                intent.putExtra("network", "MTN");
                startActivity(intent);

            }

        });

        ImageButton btnMyStatements = (ImageButton) findViewById(R.id.btnMyStatements);

        btnMyStatements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AirtimeStatement.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }

        });

        liAgentRegister = (ImageButton) findViewById(R.id.agent_register);
        liAgentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RicaAgentRegisterActivty.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                    TextView txtBalance = (TextView) findViewById(R.id.txtBalancesMain);
                    txtBalance.setText("R 0.00");

                }
                if (Status.equals("SUCCESS")) {
                    TextView txtBalance = (TextView) findViewById(R.id.txtBalancesMain);
                    txtBalance.setText("Balance: R " + Balance.toString());
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
                    TextView txtBalance = (TextView) findViewById(R.id.txtBalancesMain);
                    txtBalance.setText("Balance: R" + Balance.toString());

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            GPStracker gt = new GPStracker(getApplicationContext());

            Location l = gt.getLocation();

            if (l == null) {
                Lat = 0.00;
                Longs = 0.00;
            } else {
                Lat = l.getLatitude();
                Longs = l.getLongitude();
            }

            // get Address from lat and long
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Lat, Longs, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses != null && addresses.size() > 0) {
                    address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Get Device Id
            devicedId = Settings.Secure.getString(MainActivity.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            // device model
            getdeviceModel();
            deviceModel = model;
            deviceManufacturer = android.os.Build.MANUFACTURER;
            checkConnection();

        }

        return super.onOptionsItemSelected(item);
    }
 /* *
    Get Device
    model
     **/

    public String getdeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * capitalize for string
     */
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


    /**
     * check internet connection
     */
    public void checkConnection() {
        if (Utils.isOnline(MainActivity.this)) {

            statusApi = "logout";
            deviceType = "android";

            sendLogoutData(username, Lat, Longs, devicedId, deviceModel, deviceManufacturer, statusApi, deviceType, address);
        } else {
            Utils.showToasMessage(MainActivity.this, "Check network connection");
        }
    }

    /**
     * logout request for api
     */
    private void sendLogoutData(String userName, Double lat, Double longs, String devicedId,
                                String deviceModel, String deviceManufacturer, String status, String deviceType, String address) {

        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "cellNumber", userName);
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
        request.open("POST", urlLogout);
        request.send(data);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // rica
            Intent intent = new Intent(MainActivity.this, Rican.class);
            intent.putExtra("identity", "Rica");
            startActivity(intent);

        } else if (id == R.id.rica_sim) {
            // rica
            String titleName = "Rica a Sim";
            Intent intent = new Intent(MainActivity.this, Rican.class);
            intent.putExtra("identity", titleName);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            //Attendance Register

            startActivity(new Intent(MainActivity.this, Register_Agent.class));

        } else if (id == R.id.nav_slideshow) {

            //My Dashboard

            startActivity(new Intent(MainActivity.this, Dashboard.class));

        } else if (id == R.id.nav_manage) {

            startActivity(new Intent(MainActivity.this, MySimsn.class));

        } else if (id == R.id.nav_topup) {

            startActivity(new Intent(MainActivity.this, TopUpInfon.class));

        } else if (id == R.id.nav_query) {

            startActivity(new Intent(MainActivity.this, Query.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onError(HttpRequest request, int readyState, short error, Exception exception) {
        Utils.showToasMessage(MainActivity.this, "" + messagedetails);
        dialog.dismiss();
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
                            String customerId = jsonObject.optString(("Customer_ID"));

                            if (code.equals("ERROR")) {
                                Toast.makeText(MainActivity.this, messagedetails.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                // show success
                                SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                Toast.makeText(MainActivity.this, messagedetails.toString(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Utils.showToasMessage(MainActivity.this, "Error with your Query Request");
                        }
                }
        }
    }
}
