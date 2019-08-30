package com.tms.ontrack.mobile.Agent_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.Agent.Sim_allocation;
import com.tms.ontrack.mobile.CredentialsCheck.CredentailsCheckResponse;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.RicaTab;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RetrofitClient;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper;
import com.veyo.autorefreshnetworkconnection.listener.StopReceiveDisconnectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Agent_Login_Activity extends AppCompatActivity implements Callback<AgentLoginResponse> {

    EditText cellid,password;
    String accessToken = "null";
    Button login;
    ProgressDialog progressBar;
    private ProgressDialog progressDialog;
    SharedPreferences mSharedPreferences,aSharedPreferences;
    private String DRIVER="Driver";
    private String AGNET="Agent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent__login_);


        cellid = (EditText)findViewById(R.id.cellnumber);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btnLogin);
        mSharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
        aSharedPreferences = getSharedPreferences("Agent",Context.MODE_PRIVATE);

        if(mSharedPreferences.contains(DRIVER)){

            Intent intent = new Intent(Agent_Login_Activity.this, Stocks_dashboard.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Checking the Login Credentials...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();
                Log.d("CellID : ",cellid.getText().toString());
                Log.d( "Password : ",password.getText().toString());
                if(cellid.length() == 0 || password.length() == 0)
                {
                    progressBar.cancel();
                    Toast.makeText(Agent_Login_Activity.this, "Enter the Required Fields", Toast.LENGTH_SHORT).show();

                }
                else {
                    agentlogin(cellid.getText().toString(), password.getText().toString());

                    }
                }
        });
    }

    private void networkCheck() {

        CheckNetworkConnectionHelper
                .getInstance()
                .registerNetworkChangeListener(new StopReceiveDisconnectedListener() {
                    @Override
                    public void onDisconnected() {
                        Log.d("onDisconnected: ","Network Disconnected");
                        Intent intent = new Intent(Agent_Login_Activity.this, RicaTab.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onNetworkConnected() {
                        //Do your task on Network Connected!
                        Log.e("onConnected","Network Connected");

                    }

                    @Override
                    public Context getContext() {
                        return Agent_Login_Activity.this;
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
            }
            else
                {
                    progressBar.cancel();
                    Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
//            Log.d("Access 2: ",accessToken);
//            if(accessToken.length() == 0)
//            {
//                String status = response.body().getSuccess();
//                String message = response.body().getMessage();
//                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            }
//            else if(response.body().getAccessToken().length()>0){
//                String expirytime = response.body().getExpiresIn().toString();
//                String token = "Bearer " + accessToken;
//                Pref.putToken(MyApp.getContext(), token);
//                Log.d("onResponse: ", token);
//                Log.d("onResponse: ", expirytime);
//                agentLoginCheck();
////            Intent intent = new Intent(Agent_Login_Activity.this, ProcessingActivity.class);
////            startActivity(intent);
//            }

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
                            Toast.makeText(Agent_Login_Activity.this, "You are logging in with ADMIN Credentials", Toast.LENGTH_SHORT).show();

                        }
                        else if(authority.equals("DRIVER"))
                        {
                            progressBar.cancel();


                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                            mEditor.putString(DRIVER,"Driver");
                            mEditor.apply();

                            Log.d("Driver Login",response.body().getBody().getAuthority().getAuthority());
                            Log.d("Driver Login",response.body().getBody().getId().toString());
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Toast.makeText(Agent_Login_Activity.this, "Login Successful for Driver", Toast.LENGTH_SHORT).show();
                            Intent intent = (new Intent(Agent_Login_Activity.this, Stocks_dashboard.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();


                        }
                        else{
                            progressBar.cancel();

                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                            mEditor.putString(DRIVER,"Driver");
                            mEditor.apply();

                            Toast.makeText(Agent_Login_Activity.this, "Login Successful for Agent", Toast.LENGTH_SHORT).show();
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Intent intent = new Intent(Agent_Login_Activity.this, Agent_Mainactivity.class);
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
}
