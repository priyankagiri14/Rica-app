package com.tms.ontrack.mobile.Web_Services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.tms.ontrack.mobile.SharedPreference.SharedPref;
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

import static com.tms.ontrack.mobile.Web_Services.IBaseURL.BASE_URL_GET;
import static com.tms.ontrack.mobile.Web_Services.IBaseURL.SMARTCALL_BASE_URL;

public class RetrofitSmartCallToken {
    public static String token;

    private static Retrofit retrofit = null;
    private static String getSavedToken;

    public static Retrofit getClient()
    {   if(retrofit== null)
    {

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        SharedPreferences sharedPreferences=MyApp.getContext().getSharedPreferences("smartCallLogin", 0);

        getSavedToken= sharedPreferences.getString("smartCallToken",null);
        if(getSavedToken!=null) {
            Log.d("RetrofitSmartCallToken", "getClienttoken: "+getSavedToken);

            httpClient.addInterceptor(new HeaderIntercepter())

                    //here we adding Interceptor for full level logging
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(SMARTCALL_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build();
        }

    }
        return retrofit;


    }
    }

