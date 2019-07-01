package com.ubits.payflow.payflow_network.Agent_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Driver.Driver_Dashboard.Driver_Dashboard;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitClient;
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
    String type = "0";
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
                if(cellid.length() == 0 || password.length() == 0)
                {
                    Toast.makeText(Agent_Login_Activity.this, "Enter the Required Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    agentlogin(cellid.getText().toString(), password.getText().toString(), type);
                    }
                }
        });
    }

    private void agentlogin(String cellid, String password, String type) {
        Web_Interface webInterface= RetrofitClient.getClient().create(Web_Interface.class);
//        Log.d("LoginActivity","retrofit initialised");

        //creating request body to parse form data

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("id", cellid);
            paramObject.put("password", password);
            paramObject.put("type",type);
            
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"),(paramObject).toString());

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

            String userid = response.body().getData().getUser().getId();
            String name = response.body().getData().getUser().getName();
            String token = response.body().getData().getUser().getToken();
            String type = response.body().getData().getUser().getType();

            Intent intent = new Intent(Agent_Login_Activity.this, Driver_Dashboard.class);
            startActivity(intent);
        }

    }

    @Override
    public void onFailure(Call<AgentLoginResponse> call, Throwable t) {

        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }
}
