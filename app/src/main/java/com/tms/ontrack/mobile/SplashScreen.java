package com.tms.ontrack.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent.Agent_Mainactivity;
import com.tms.ontrack.mobile.Agent_Login.RefreshToken;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.Navigation_main.Navigation_Main;
import com.tms.ontrack.mobile.Web_Services.MyApp;
import com.tms.ontrack.mobile.Web_Services.RefreshAccessToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tms.ontrack.mobile.SharedPreference.SharedPref.AGENT;
import static com.tms.ontrack.mobile.SharedPreference.SharedPref.DRIVER;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=3000;
    public static SharedPreferences mSharedPreferences,aSharedPreferences;
    String accessToken,expirytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
                aSharedPreferences = getSharedPreferences("Agent",Context.MODE_PRIVATE);

                // mSharedPreferences = getSharedPreferences("Agent", Context.MODE_PRIVATE);
                if(mSharedPreferences.contains(DRIVER)){
                    Web_Interface web_interface = RefreshAccessToken.getClient().create(Web_Interface.class);
                    Call<RefreshToken> refreshTokenCall = web_interface.requestRefreshToken();
                    refreshTokenCall.enqueue(new Callback<RefreshToken>() {
                        @Override
                        public void onResponse(Call<RefreshToken> call, Response<RefreshToken> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                if (response.body() != null) {
                                    accessToken = response.body().getAccessToken();
                                    expirytime = response.body().getExpiresIn().toString();
                                    String token = "Bearer " + accessToken;
                                    Pref.putToken(MyApp.getContext(), token);
                                    Log.d("onResponse: ", token);
                                    Log.d("onResponse: ", expirytime);
                                    Intent intent = new Intent(SplashScreen.this, Stocks_dashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(SplashScreen.this, "Your Session has Expired.. Please Login again..!", Toast.LENGTH_SHORT).show();
                                mSharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
                                if (mSharedPreferences.contains("Driver")) {
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    Intent i = new Intent(SplashScreen.this, Navigation_Main.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();

                                }
                                else
                                {
                                    Intent i = new Intent(SplashScreen.this, Navigation_Main.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RefreshToken> call, Throwable t) {
                            Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else if(aSharedPreferences.contains(AGENT)){

                    Web_Interface web_interface = RefreshAccessToken.getClient().create(Web_Interface.class);
                    Call<RefreshToken> refreshTokenCall = web_interface.requestRefreshToken();
                    refreshTokenCall.enqueue(new Callback<RefreshToken>() {
                        @Override
                        public void onResponse(Call<RefreshToken> call, Response<RefreshToken> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                if (response.body() != null) {
                                    accessToken = response.body().getAccessToken();
                                    expirytime = response.body().getExpiresIn().toString();
                                    String token = "Bearer " + accessToken;
                                    Pref.putToken(MyApp.getContext(), token);
                                    Log.d("onResponse: ", token);
                                    Log.d("onResponse: ", expirytime);
                                    Intent intent = new Intent(SplashScreen.this, Agent_Mainactivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(SplashScreen.this, "Your Session has Expired.. Please Login again..!", Toast.LENGTH_SHORT).show();
                                mSharedPreferences = getSharedPreferences("Driver", Context.MODE_PRIVATE);
                                if (mSharedPreferences.contains("Driver")) {
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    Intent i = new Intent(SplashScreen.this, Navigation_Main.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();

                                }
                                else
                                {
                                    Intent i = new Intent(SplashScreen.this, Navigation_Main.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RefreshToken> call, Throwable t) {
                            Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Intent intent = new Intent(SplashScreen.this,Navigation_Main.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}
