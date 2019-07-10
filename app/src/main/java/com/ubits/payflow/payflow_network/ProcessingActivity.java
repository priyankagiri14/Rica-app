package com.ubits.payflow.payflow_network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Agent_Login.Agent_Login_Activity;
import com.ubits.payflow.payflow_network.CredentialsCheck.Authority;
import com.ubits.payflow.payflow_network.CredentialsCheck.Body;
import com.ubits.payflow.payflow_network.CredentialsCheck.CredentailsCheckResponse;
import com.ubits.payflow.payflow_network.General.MainActivity;
import com.ubits.payflow.payflow_network.Login.Login_Activity;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessingActivity extends AppCompatActivity implements Callback<CredentailsCheckResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        Web_Interface webInterface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<CredentailsCheckResponse> credentailsCheckResponseCall = webInterface.requestCredentialsCheck();
        credentailsCheckResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<CredentailsCheckResponse> call, Response<CredentailsCheckResponse> response) {
        if (response.isSuccessful() && response.code() == 200) {
            if (response.body() != null) {
                Log.d("Process: ",response.body().getBody().getAuthority().getAuthority());
                String authority = response.body().getBody().getAuthority().getAuthority();
                if(authority.equals("ADMIN") || authority.equals("DRIVER"))
                {
                    Toast.makeText(this, "You are logging in with ADMIN or DRIVER Credentials", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProcessingActivity.this, Login_Activity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "Login Successful for Agent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProcessingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<CredentailsCheckResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }
}
