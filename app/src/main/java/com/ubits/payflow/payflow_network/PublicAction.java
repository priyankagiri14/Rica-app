package com.ubits.payflow.payflow_network;

import android.content.Context;
import android.util.Log;

import HPRTAndroidSDK.HPRTPrinterHelper;

public class PublicAction {
    private Context context = null;

    public PublicAction(Context con) {
        context = con;
    }

    public void BeforePrintAction() {
        try {
            HPRTPrinterHelper.CutPaper(HPRTPrinterHelper.HPRT_PARTIAL_CUT, PrinterProperty.CutSpacing);
            HPRTPrinterHelper.OpenCashdrawer(0);
        } catch (Exception e) {
            Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> BeforePrintAction ")).append(e.getMessage()).toString());
        }
    }

    public void AfterPrintAction() {
        try {
            HPRTPrinterHelper.OpenCashdrawer(0);
            int iFeed = 10;
            HPRTPrinterHelper.PrintAndFeed(iFeed * 8);
            HPRTPrinterHelper.CutPaper(HPRTPrinterHelper.HPRT_PARTIAL_CUT, PrinterProperty.CutSpacing);
        } catch (Exception e) {
            Log.e("HPRTSDKSample", (new StringBuilder("PublicAction --> AfterPrintAction ")).append(e.getMessage()).toString());
        }
    }
}