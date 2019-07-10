package com.ubits.payflow.payflow_network.Web_Services.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Pref {
    public static final String USER_TOKEN = "user_token";
    public static final String ID = "ID";
    public static final String ALLOCATION_ID = "allocation_id";

    public static SharedPreferences getPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //put username no in shared pref
    public static void putToken(Context context,String token)
    {
        getPref(context).edit().putString(USER_TOKEN,token).commit();
    }
    public static String getUserToken(Context context) {
        return getPref(context).getString(USER_TOKEN, null);
    }

    public static void putId(Context context,String Id)
    {
        getPref(context).edit().putString(ID,Id).commit();
    }
    public static String getId(Context context) {
        return getPref(context).getString(ID, null);
    }

    public static void putAllocationId(Context context,String Id)
    {
        getPref(context).edit().putString(ALLOCATION_ID,Id).commit();
    }
    public static String getAllocationId(Context context) {
        return getPref(context).getString(ALLOCATION_ID, null);
    }
}