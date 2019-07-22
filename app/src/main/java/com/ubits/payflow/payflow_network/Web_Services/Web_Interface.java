package com.ubits.payflow.payflow_network.Web_Services;

import com.ubits.payflow.payflow_network.Agent.model.Simallocatemodel;
import com.ubits.payflow.payflow_network.AgentBatchesGet.AgentBatchesGetResponse;
import com.ubits.payflow.payflow_network.AgentBatchesGet.MyPojo;
import com.ubits.payflow.payflow_network.AgentBatchesReceived.AgentBatchesReceivedResponse;
import com.ubits.payflow.payflow_network.Agent_Login.AgentLoginResponse;
import com.ubits.payflow.payflow_network.AgentsGetResponse.AgentsGetResponse;
import com.ubits.payflow.payflow_network.AgentsList.AgentsListResponse;
import com.ubits.payflow.payflow_network.AllocationCreateResponse.AllocationCreate;
import com.ubits.payflow.payflow_network.AllocationGet.AllocationGetResponse;
import com.ubits.payflow.payflow_network.AllocationStatus.AgentAllocationStatusResponse;
import com.ubits.payflow.payflow_network.AllocationStatus.AllocationStatusResponse;
import com.ubits.payflow.payflow_network.DriverBatchesGet.BatchesGetResponse;
import com.ubits.payflow.payflow_network.DriverBatchesGet.Pojo;
import com.ubits.payflow.payflow_network.BatchesReceived.BatchesReceivedResponse;
import com.ubits.payflow.payflow_network.CredentialsCheck.CredentailsCheckResponse;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.driverattendancephoto.GetDriverAttendanceResponse;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.driverattendancephoto.UploadedFile;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.FetchAgent;
import com.ubits.payflow.payflow_network.OpenCloseBatches.SerialsGetResponse;
import com.ubits.payflow.payflow_network.TeamAttendance.TeamAttendanceResponse_MyPojo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<AllocationCreate> requestAllocationCreate(@Path("userId") String id, @Body String[] batches);

    @Headers("Accept: application/json")
    @GET("allocations?size=size&page=page/")
    Call<AllocationGetResponse> requestAllocationGet(@Query("size") int size, @Query("page") int page);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
   // @Headers("Accept: application/json");
    @PUT("allocations")
    Call<AllocationStatusResponse> requestAllocationStatus(@Body Pojo pojo);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<BatchesReceivedResponse> requestBatchesReceived(@Query("size") int size, @Query("page") int page);

    @Headers("Accept: application/json")
    @POST("sims")
    Call<Simallocatemodel> simallocate(@Body RequestBody simallocate, @Header("Authorization") String auth);

    //Atendance section
    //for fetching stores list
    @Headers("Accept: application/json")
    @GET("users")
    Call<FetchAgent> requestfetchagent(@Header("Authorization") String auth);

    //for agent start day
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("attendance")
    Call<GetDriverAttendanceResponse> markAgentattendance(@Body RequestBody agentStartDayBody, @Header("Authorization") String auth);

    //for image upload
    @Multipart
    @Headers("Accept: application/json")
    @POST("attachments")
    Call<UploadedFile> requestUpdateProfilePic(@Part MultipartBody.Part[] files, @Header("Authorization") String auth);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<AgentBatchesGetResponse> requestAgentBatchesGet(@Query("size") int size, @Query("page") int page);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    // @Headers("Accept: application/json");
    @PUT("allocations")
    Call<AgentAllocationStatusResponse> requestAgentAllocationStatus(@Body MyPojo pojo);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<AgentBatchesReceivedResponse> requestAgentBatchesReceived(@Query("size") int size, @Query("page") int page);

    @Headers("Accept: application/json")
    @GET("batches/{id}/serials")
    Call<SerialsGetResponse> requestSerialsGet(@Path("id") String id);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("attendance/team")
    Call<TeamAttendanceResponse_MyPojo> requestTeamAttendance(@Body Pojo pojo);

}
