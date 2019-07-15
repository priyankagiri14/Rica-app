package com.ubits.payflow.payflow_network.Driver;

import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ubits.payflow.payflow_network.Agent_Login.AgentLoginResponse;
import com.ubits.payflow.payflow_network.Agent_Login.Agent_Login_Activity;
import com.ubits.payflow.payflow_network.Driver.Driver_Dashboard.Driver_Dashboard;
import com.ubits.payflow.payflow_network.FetchStocks.ListStockData;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.MyApp;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitClient;
import com.ubits.payflow.payflow_network.Web_Services.Utils.Pref;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Driverlogin extends AppCompatActivity implements View.OnClickListener, Callback<AgentLoginResponse> {
    EditText drivercellid,pswrd;
    Button login;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_login);
        drivercellid=findViewById(R.id.cellnumber);
        pswrd=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if(drivercellid.length() == 0 || pswrd.length() == 0)
        {
            Toast.makeText(Driverlogin.this, "Enter the Required Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            driverLogin(drivercellid.getText().toString(), pswrd.getText().toString());
        }
    }

    private void driverLogin(String cellid, String password) {
        Web_Interface webInterface= RetrofitClient.getClient().create(Web_Interface.class);
        //Log.d("LoginActivity","retrofit initialised");
        //creating request body to parse form data

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("username", cellid);
            paramObject.put("password", password);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<AgentLoginResponse> call= webInterface.requestAgentLogin(body);
           //exeuting the service
            call.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<AgentLoginResponse> call, Response<AgentLoginResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {

            String r=response.body().toString();
        }

                String accessToken = response.body().getAccessToken();
                String expiry_time = response.body().getExpiresIn().toString();
                Pref.putToken(MyApp.getContext(), accessToken);
                Log.d("onResponse: ",accessToken);
                Log.d("onResponse: ", expiry_time);


            Intent intent = new Intent(Driverlogin.this, MainActivity.class);
            startActivity(intent);
        }

    @Override
    public void onFailure(Call<AgentLoginResponse> call, Throwable t) {

        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
