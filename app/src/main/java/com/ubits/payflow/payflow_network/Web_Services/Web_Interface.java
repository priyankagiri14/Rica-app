package com.ubits.payflow.payflow_network.Web_Services;

import com.ubits.payflow.payflow_network.Agent_Login.AgentLoginResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Web_Interface {

    @POST("?method=login")
    Call<AgentLoginResponse> requestAgentLogin(@Body RequestBody agentLoginBody);
}
