package com.tms.ontrack.mobile.AgentsGetResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsGetList extends AppCompatActivity {

    List<Body> list = new ArrayList<>();
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_get_list);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<AgentsGetResponse> agentsGetResponseCall = web_interface.requestAgentsGet(0,0,"AGENT");

        agentsGetResponseCall.enqueue(new Callback<AgentsGetResponse>() {
            @Override
            public void onResponse(Call<AgentsGetResponse> call, Response<AgentsGetResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        list = response.body().getBody();
                        for (int i = 0; i < list.size(); i++) {
                            String agents = list.get(i).getName();
                            Log.d("Agents: ", agents);

                        }
                        progressBar.cancel();
                    }
                }
            }

            @Override
            public void onFailure(Call<AgentsGetResponse> call, Throwable t) {
                Toast.makeText(AgentsGetList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
