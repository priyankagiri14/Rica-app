package com.tms.ontrack.mobile.Web_Services.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class Pref {
    public static final String USER_TOKEN = "user_token";
    public static final String ID = "ID";
    public static final String BATCH_ID = "id";
    public static final String BATCH_NO = "batchNo";
    public static final String BATCH_ARRAY = "batcharray";
    public static final String ALLOCATION_ID = "allocation_id";
    public static final String CITY = "city";
    public static final String FNAME = "firstName";
    public static final String USER_ID = "USER_ID";
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

    public static void setBatchNo(Context context,String batch)
    {
        getPref(context).edit().putString(BATCH_NO,batch).commit();
    }
    public static String getBatchNo(Context context) {
        return getPref(context).getString(BATCH_NO, null);
    }
    public static void setBatchID(Context context,String id)
    {
        getPref(context).edit().putString(BATCH_ID,id).commit();
    }
    public static String getBatchID(Context context) {
        return getPref(context).getString(BATCH_ID, null);
    }

    public static void setCity(Context context,String city)
    {
        getPref(context).edit().putString(CITY,city).commit();
    }
    public static String getCity(Context context) {
        return getPref(context).getString(CITY, null);
    }

    public static void putBatchArray(Context context,String batcharray)
    {
        getPref(context).edit().putString(BATCH_ARRAY,batcharray).commit();
    }
    public static String getBatchArray(Context context) {
        return getPref(context).getString(BATCH_ARRAY, null);
    }

    public static void putFirstName(Context context,String firstname)
    {
        getPref(context).edit().putString(FNAME,firstname).commit();
    }
    public static String getFirstName(Context context) {
        return getPref(context).getString(FNAME, null);
    }

    public static void putUserId(Context context,String userId)
    {
        getPref(context).edit().putString(USER_ID,userId).commit();
    }
    public static String getUserId(Context context) {
        return getPref(context).getString(USER_ID, null);
    }
}