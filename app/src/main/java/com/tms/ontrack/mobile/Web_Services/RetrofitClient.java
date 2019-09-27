package com.tms.ontrack.mobile.Web_Services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.tms.ontrack.mobile.Web_Services.IBaseURL.BASE_URL;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {   if(retrofit== null)
    {

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        httpClient .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }
        return retrofit; //return RetroFit
    }
}
