package com.tms.ontrack.mobile.Web_Services;

import com.tms.ontrack.mobile.Web_Services.Utils.Pref;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.tms.ontrack.mobile.Web_Services.IBaseURL.BASE_URL;
import static com.tms.ontrack.mobile.Web_Services.IBaseURL.BASE_URL_GET;

public class RefreshAccessToken {

    public static String token;
    private static Retrofit retrofit = null;
    public static Retrofit getClient()
    {   if(retrofit== null)
    {

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", Pref.getUserToken(MyApp.getContext()))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        })
                //here we adding Interceptor for full level logging
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();

        token=Pref.getUserToken(MyApp.getContext());


    }
        return retrofit;


    }
}
