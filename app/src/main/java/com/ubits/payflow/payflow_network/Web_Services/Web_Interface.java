package com.ubits.payflow.payflow_network.Web_Services;

import com.ubits.payflow.payflow_network.Agent_Login.AgentLoginResponse;
import com.ubits.payflow.payflow_network.AgentsGetResponse.AgentsGetResponse;
import com.ubits.payflow.payflow_network.AgentsList.AgentsListResponse;
import com.ubits.payflow.payflow_network.AllocationCreateResponse.AllocationCreate;
import com.ubits.payflow.payflow_network.AllocationGet.AllocationGetResponse;
import com.ubits.payflow.payflow_network.AllocationStatus.AllocationStatusResponse;
import com.ubits.payflow.payflow_network.BatchesGet.BatchesGetResponse;
import com.ubits.payflow.payflow_network.BatchesGet.Pojo;
import com.ubits.payflow.payflow_network.CredentialsCheck.CredentailsCheckResponse;
import com.ubits.payflow.payflow_network.FetchStocks.FetchStocksResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Web_Interface {

    @POST("login")
    Call<AgentLoginResponse> requestAgentLogin(@Body RequestBody agentLoginBody);

//    @Headers({"x-access-token:"})
//    @GET("")
//    Call<FetchStocksResponse> requestFetchStocks();

    @Headers("Content-Type: application/json")
    @GET("users/me")
    Call<CredentailsCheckResponse> requestCredentialsCheck();

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<BatchesGetResponse> requestBatchesGet(@Query("size") int size, @Query("page") int page);

    @Headers({"Accept: application/json"})
    @GET("users")
    Call<AgentsListResponse> requestAgentsList();

    @Headers({"Accept: application/json"})
    @GET("users?size=size&page=page&authorities=authorities/")
    Call<AgentsGetResponse> requestAgentsGet(@Query("size") int size, @Query("page") int page, @Query("authorities") String authorities);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("allocations/{userId}")
    Call<AllocationCreate> requestAllocationCreate(@Path("userId") String id);

    @Headers("Accept: application/json")
    @GET("allocations?size=size&page=page/")
    Call<AllocationGetResponse> requestAllocationGet(@Query("size") int size, @Query("page") int page);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
   // @Headers("Accept: application/json");
    @PUT("allocations")
    Call<AllocationStatusResponse> requestAllocationStatus(@Body Pojo pojo);
}
