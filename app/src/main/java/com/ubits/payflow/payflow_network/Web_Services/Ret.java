package com.ubits.payflow.payflow_network.Web_Services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ubits.payflow.payflow_network.Web_Services.Utils.Pref;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.ubits.payflow.payflow_network.Web_Services.IBaseURL.ATTENDANCE_BASE_URL;
import static com.ubits.payflow.payflow_network.Web_Services.IBaseURL.BASE_URL;
import static com.ubits.payflow.payflow_network.Web_Services.IBaseURL.BASE_URL_GET;
import static com.ubits.payflow.payflow_network.Web_Services.MyApp.getContext;

public class Ret {
    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {   if(retrofit== null)
    {

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout

        Log.d("token", Pref.getUserToken(getContext()));

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .addHeader("x-access-token", Pref.getUserToken(getContext()))
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        })


                //here we adding Interceptor for full level logging
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        OkHttpClient client = httpClient.build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_GET)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }
        return retrofit;

    }
}
