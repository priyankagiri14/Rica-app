package com.tms.ontrack.mobile.Web_Services;

import com.tms.ontrack.mobile.Agent.AttendanceGetResponse.AttendanceConfirmResponse;
import com.tms.ontrack.mobile.Agent.AttendanceGetResponse.AttendanceGetResponse;
import com.tms.ontrack.mobile.Agent.ScanBatchesResponse;
import com.tms.ontrack.mobile.Agent.ScanPojo;
import com.tms.ontrack.mobile.Agent.model.Simallocatemodel;
import com.tms.ontrack.mobile.AgentBatchesGet.AgentBatchesGetResponse;
import com.tms.ontrack.mobile.AgentBatchesGet.MyPojo;
import com.tms.ontrack.mobile.AgentBatchesReceived.AgentBatchesReceivedResponse;
import com.tms.ontrack.mobile.Agent_Login.AgentLoginResponse;
import com.tms.ontrack.mobile.AgentsGetResponse.AgentsGetResponse;
import com.tms.ontrack.mobile.AgentsList.AgentsListResponse;
import com.tms.ontrack.mobile.AirtimeSales.model.SmartCallAgentLogin;
import com.tms.ontrack.mobile.AirtimeSales.model.get_all_networks.GetAllNetworksResponse;
import com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans.GetAllDataPlansResponse;
import com.tms.ontrack.mobile.AllocationCreateResponse.AllocationCreate;

import com.tms.ontrack.mobile.AllocationGet.AllocationGetResponse;
import com.tms.ontrack.mobile.AllocationStatus.AgentAllocationStatusResponse;
import com.tms.ontrack.mobile.AllocationStatus.AllocationStatusResponse;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.TeamAttendanceResponse_MyPojo;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.Team_Attendance_Response;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.ValueWalletResponse;
import com.tms.ontrack.mobile.DriverBatchesGet.BatchesGetResponse;
import com.tms.ontrack.mobile.DriverBatchesGet.Pojo;
import com.tms.ontrack.mobile.BatchesReceived.BatchesReceivedResponse;
import com.tms.ontrack.mobile.CredentialsCheck.CredentailsCheckResponse;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto.GetDriverAttendanceResponse;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto.UploadedFile;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.get_Agent.FetchAgent;
import com.tms.ontrack.mobile.OpenBatchesResponse.OpenedBatchesResponse;
import com.tms.ontrack.mobile.OpenBatchesResponse.SerialsGet;
import com.tms.ontrack.mobile.OpenCloseBatches.CashHistory.CashHistoryResponse;
import com.tms.ontrack.mobile.OpenCloseBatches.OpenCloseResponse;
import com.tms.ontrack.mobile.OpenCloseBatches.SerialsGetResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
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
    Call<AllocationCreate> requestAllocationCreate(@Path("userId") String id, @Query("lat")double lat, @Query("long") double longitude,@Body String[] batches);

    @Headers("Accept: application/json")
    @GET("allocations?size=size&page=page/")
    Call<AllocationGetResponse> requestAllocationGet(@Query("size") int size, @Query("page") int page);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
   // @Headers("Accept: application/json");
    @PUT("allocations")
    Call<AllocationStatusResponse> requestAllocationStatus(@Query("lat") double lat,@Query("long") double longitude,@Body Pojo pojo);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<BatchesReceivedResponse> requestBatchesReceived(@Query("size") int size, @Query("page") int page);

    @Headers("Accept: application/json")
    @POST("sims")
    Call<Simallocatemodel> simallocate(@Body RequestBody simallocate);

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
    Call<AgentAllocationStatusResponse> requestAgentAllocationStatus(@Query("lat")double lat, @Query("long") double longitude,@Body MyPojo pojo);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<AgentBatchesReceivedResponse> requestAgentBatchesReceived(@Query("size") int size, @Query("page") int page);

    @Headers("Accept: application/json")
    @GET("batches/{id}/serials")
    Call<SerialsGetResponse> requestSerialsGet(@Path("id") String id);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("attendance/team")
    Call<Team_Attendance_Response> requestTeamAttendance(@Body TeamAttendanceResponse_MyPojo pojo);

    @Headers("Accept: application/json")
    @GET("attendance?page=page&size=size")
    Call<AttendanceGetResponse> requestAttendanceGet(@Query("page") int page, @Query("size") int size);

    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @PUT("attendance/{id}")
    Call<AttendanceConfirmResponse> requestAttendanceConfirm(@Path("id")int id, @Body RequestBody body);


    @Headers({"Accept: application/json", "Content-Type: application/json"})
    @POST("activities")
    Call<OpenCloseResponse> requestOpenClose(@Query("lat")double lat,@Query("long") double longitude,@Query("action") String action, @Body RequestBody body);

    @Headers("Accept: application/json")
    @GET("batches?size=size&page=page/")
    Call<OpenedBatchesResponse> requestOpenedBatches(@Query("size") int size, @Query("page") int page);

    @Headers("Accept: application/json")
    @GET("batches/{id}/serials")
    Call<SerialsGet> requestSerials(@Path("id") int id, @Query("status") String status);

    @Headers("Content-Type: application/json")
    @GET("users/me/balance")
    Call<ValueWalletResponse> requestValueWallet();

    @Headers("Accept: application/json")
    @GET("cashflow?page=page&size=size")
    Call<CashHistoryResponse> requestCashHistory(@Query("page") int page, @Query("size") int size);

    @Headers("Content-Type: application/json")
    @POST("sims/batches")
    Call<ScanBatchesResponse> requestScanBatches(@Body ScanPojo scanPojo);

    @GET("attachments/{id}/download")
    Call<ResponseBody> requestImagefromserver(@Path("id") Integer id);

    //smartcall login auth
    @POST("auth")
    Call<SmartCallAgentLogin> requestSmartCallLogin(@Header("Authorization") String auth);
    //smartcall delete auth
    @DELETE("auth")
    Call<SmartCallAgentLogin> requestSmartCallLoginInvalidateToken(@Header("Authorization") String auth);
    //smartcall get all networks
    @GET("smartload/networks")
    Call<GetAllNetworksResponse> requestSmartCallGetAllNetworks();

    //smartcall get recharge plans
    @Headers({ "Content-Type: application/json"})
    @GET("smartload/networks/{networkId}")
    Call<GetAllDataPlansResponse> requestGetAllDataPlans(@Path("networkId") int network_id);

    //smartcall recharge request
    @POST("smartload/recharges")
    Call<ResponseBody> requestSmartCallRecharge(@Body RequestBody requestRecharge);
}
