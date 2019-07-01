package com.ubits.payflow.payflow_network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utils {

    public Utils() {

    }

    /*
     * Show Toast Message
     * */
    public static void showToasMessage(Context mcontext, String message) {
        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
    }

    /*
     * Check Network Connection
     * */
    public static boolean isOnline(Context mcontex) {
        ConnectivityManager cm = (ConnectivityManager) mcontex.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
