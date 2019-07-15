package com.ubits.payflow.payflow_network.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public Utils() {

    }
    private static ProgressDialog progressDialog;
    public static void showProgress(Context mcontext, String message)
    {
        progressDialog=new ProgressDialog(mcontext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();

    }

    public static void stopProgress()
    {
        progressDialog.dismiss();

    }
}
