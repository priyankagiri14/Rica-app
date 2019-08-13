package com.tms.ontrack.mobile.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {

    public static final String DRIVER="Driver";
    public static final String AGENT="Agent";

    //initialising shared preferences
    public static SharedPreferences getPref(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);

    }

    //put user phone number
    public static void putAgent(Context context,String id)
    {
        getPref(context).edit().putString(AGENT,id).apply();

    }


    public static void putDriver(Context context,String id)
    {
        getPref(context).edit().putString(DRIVER,id).apply();

    }

    //get user phone number

    public static String getDriver(Context context)
    {

        return getPref(context).getString(DRIVER,null);
    }

    //clear shared preferences

    public static void clearPref(Context context) {
        getPref(context).edit().clear().apply();
    }
}
