package com.tms.ontrack.mobile.Web_Services;
import android.content.SharedPreferences;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderIntercepter implements Interceptor {
    private String getSavedToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        SharedPreferences sharedPreferences=MyApp.getContext().getSharedPreferences("smartCallLogin", 0);
        getSavedToken= sharedPreferences.getString("smartCallToken",null);
        Request tokenRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer " + getSavedToken)
                .addHeader("Content-Type", "application/json")
                .method(request.method(), request.body())
                .build();

        return chain
                .proceed(tokenRequest);
    }
}