package com.ubits.payflow.payflow_network.Agent_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetList;
import com.ubits.payflow.payflow_network.CredentialsCheck.CredentailsCheckResponse;
import com.ubits.payflow.payflow_network.Driver.Driver_Dashboard.Driver_Dashboard;
import com.ubits.payflow.payflow_network.Driver.Driver_Dashboard.Stocks_dashboard;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Stock_allocate;
import com.ubits.payflow.payflow_network.FetchStocks.ListStockData;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.Login.Login_Activity;
import com.ubits.payflow.payflow_network.ProcessingActivity;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.MyApp;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitClient;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Utils.Pref;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Agent_Login_Activity extends AppCompatActivity implements Callback<AgentLoginResponse> {

    EditText cellid,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent__login_);

        cellid = (EditText)findViewById(R.id.cellnumber);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("CellID : ",cellid.getText().toString());
                Log.d( "Password : ",password.getText().toString());
                if(cellid.length() == 0 || password.length() == 0)
                {
                    Toast.makeText(Agent_Login_Activity.this, "Enter the Required Fields", Toast.LENGTH_SHORT).show();

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

            String accessToken = response.body().getAccessToken();
            String expirytime = response.body().getExpiresIn().toString();
            String token = "Bearer "+accessToken;
            Pref.putToken(MyApp.getContext(), token);
            Log.d("onResponse: ",token);
            Log.d("onResponse: ",expirytime);
            agentLoginCheck();
//            Intent intent = new Intent(Agent_Login_Activity.this, ProcessingActivity.class);
//            startActivity(intent);

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
                        if(authority.equals("ADMIN"))
                        {
                            Toast.makeText(Agent_Login_Activity.this, "You are logging in with ADMIN Credentials", Toast.LENGTH_SHORT).show();
                        }
                        else if(authority.equals("DRIVER"))
                        {
                            Log.d("Driver Login",response.body().getBody().getAuthority().getAuthority());
                            Log.d("Driver Login",response.body().getBody().getId().toString());
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Toast.makeText(Agent_Login_Activity.this, "Login Successful for Driver", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Agent_Login_Activity.this, Driver_Dashboard.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(Agent_Login_Activity.this, "Login Successful for Agent", Toast.LENGTH_SHORT).show();
                            String Id = response.body().getBody().getId().toString();
                            Pref.putId(MyApp.getContext(),Id);
                            Intent intent = new Intent(Agent_Login_Activity.this, BatchesGetList.class);
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
