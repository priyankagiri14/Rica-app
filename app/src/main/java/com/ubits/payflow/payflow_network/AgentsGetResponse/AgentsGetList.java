package com.ubits.payflow.payflow_network.AgentsGetResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsGetList extends AppCompatActivity implements Callback<AgentsGetResponse> {

    List<Body> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_get_list);

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentsGetResponse> agentsGetResponseCall = web_interface.requestAgentsGet(0,0,"AGENT");
        agentsGetResponseCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<AgentsGetResponse> call, Response<AgentsGetResponse> response) {
        if(response.isSuccessful() && response.code() == 200)
        {
            if(response.body()!= null)
            {
                list = response.body().getBody();
                for(int i=0; i<list.size();i++)
                {
                    String agents = list.get(i).getName();
                    Log.d("Agents: ",agents);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<AgentsGetResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();

    }
}
