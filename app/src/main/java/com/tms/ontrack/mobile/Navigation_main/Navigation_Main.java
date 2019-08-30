package com.tms.ontrack.mobile.Navigation_main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.Agent.NetworkError;
import com.tms.ontrack.mobile.Agent_Login.AgentLoginResponse;
import com.tms.ontrack.mobile.CredentialsCheck.CredentailsCheckResponse;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.SharedPreference.SharedPref;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitClient;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Navigation_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<AgentLoginResponse> {

    EditText cellid,password;
    Button login;
    String accessToken = "null";
    ProgressDialog progressBar;
    private ProgressDialog progressDialog;
    public static SharedPreferences mSharedPreferences,aSharedPreferences;
    public static String DRIVER="Driver";
    public static String AGENT = "Agent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation__main);

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        //Do your task on Network Disconnected!
                        Log.e("onDisconnected","Network");
                        Intent intent = new Intent(Navigation_Main.this, NetworkError.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onNetworkConnected() {
                        //Do your task on Network Connected!
                        Log.d("onNetworkConnected: ","Network");
                       /* Intent intent = new Intent(Agent_Mainactivity.this,Agent_Mainactivity.class);
                        startActivity(intent);*/
                    }

                    @Override
                    public Context getContext() {
                        return Navigation_Main.this;
                    }
                });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
        aSharedPreferences = getSharedPreferences("Agent",Context.MODE_PRIVATE);

        // mSharedPreferences = getSharedPreferences("Agent", Context.MODE_PRIVATE);
        if(mSharedPreferences.contains(DRIVER)){
            Intent intent = new Intent(Navigation_Main.this,Stocks_dashboard.class);
            startActivity(intent);
            finish();
        }
        else if(aSharedPreferences.contains(AGENT)){
            Intent intent1 = new Intent(Navigation_Main.this,Agent_Mainactivity.class);
            startActivity(intent1);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        cellid = (EditText) findViewById(R.id.cellnumber);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Checking the Login Credentials...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();

                if(cellid.length() == 0 || password.length() == 0)
                {
                    progressBar.cancel();
                    Toast.makeText(Navigation_Main.this, "Enter the Required Fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    agentlogin(cellid.getText().toString(), password.getText().toString());

                }
            }
        });
    }
    private void agentlogin(String cellid, String password) {
        Web_Interface webInterface= RetrofitClient.getClient().create(Web_Interface.class);
        //creating request body to parse form data
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("username", cellid);
            paramObject.put("password", password);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());

            Call<AgentLoginResponse> call= webInterface.requestAgentLogin(body);
            //exeuting the service
            Log.d("agentlogin: ",call.toString());
            call.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResponse(Call<AgentLoginResponse> call, Response<AgentLoginResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {
            accessToken = response.body().getAccessToken();
            if (response.body().getAccessToken() != null) {
                String expirytime = response.body().getExpiresIn().toString();
                String token = "Bearer " + accessToken;
                Pref.putToken(MyApp.getContext(), token);
                Log.d("onResponse: ", token);
                Log.d("onResponse: ", expirytime);
                agentLoginCheck();
            } else {
                progressBar.cancel();
                Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onFailure(Call<AgentLoginResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
    private void agentLoginCheck() {

        Web_Interface webInterface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<CredentailsCheckResponse> credentailsCheckResponseCall = webInterface.requestCredentialsCheck();
        credentailsCheckResponseCall.enqueue(new Callback<CredentailsCheckResponse>() {
            @Override
            public void onResponse(Call<CredentailsCheckResponse> call, Response<CredentailsCheckResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        String authority = response.body().getBody().getAuthority().getAuthority();
                        String firstName = response.body().getBody().getFirstName();
                        if(!firstName.equals("null"))
                        {
                            Pref.putFirstName(MyApp.getContext(),firstName);
                        }
                        if(authority.equals("ADMIN"))
                        {
                            progressBar.cancel();
                            Toast.makeText(Navigation_Main.this, "You are logging in with ADMIN Credentials", Toast.LENGTH_SHORT).show();
                        }
                        else if(authority.equals("DRIVER"))
                        {
                            progressBar.cancel();
                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                            mEditor.putString(DRIVER,"Driver");
                            mEditor.apply();
                            SharedPref.putDriver(Navigation_Main.this,DRIVER);
                            Log.d("Driver Login",response.body().getBody().getAuthority().getAuthority());
                            Log.d("Driver Login",response.body().getBody().getId().toString());
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Toast.makeText(Navigation_Main.this, "Login Successful for Driver", Toast.LENGTH_SHORT).show();
                            Intent intent = (new Intent(Navigation_Main.this, Stocks_dashboard.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);


                        }
                        else{
                            progressBar.cancel();
                            SharedPreferences.Editor mEditor = aSharedPreferences.edit();
                            mEditor.putString(AGENT,"Agent");
                            mEditor.apply();
                            SharedPref.putAgent(Navigation_Main.this,AGENT);
                            Toast.makeText(Navigation_Main.this, "Login Successful for Agent", Toast.LENGTH_SHORT).show();
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Intent intent = new Intent(Navigation_Main.this, Agent_Mainactivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CredentailsCheckResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            // Handle the camera action

        } else if (id == R.id.nav_contacts) {
            Intent i1=new Intent(this, Contact_Ontrack.class);
            startActivity(i1);

        } else if (id == R.id.nav_address) {
            Intent i2=new Intent(this, Address_Ontrack.class);
            startActivity(i2);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
