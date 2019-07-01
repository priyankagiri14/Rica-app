package com.ubits.payflow.payflow_network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import HPRTAndroidSDK.HPRTPrinterHelper;

/**
 * Created by Vikas on 7/30/2018.
 */

public class PrinterUtil {
    private Context context;

    public PrinterUtil(Context context) {
        this.context = context;
    }

    public void print(String barcode) {
        try {
            if (isBluetoothEnabled()) {
                if (connect()) {
                    PublicAction pub = new PublicAction(context);
                    pub.BeforePrintAction();
                    HPRTPrinterHelper.PrintBarCode(73, barcode, 2, 70, 2, 1);
                    pub.AfterPrintAction();
                }
            } else {
                Toast.makeText(context, "Please turn on the bluetooth first", Toast.LENGTH_SHORT).show();
                openBluetoothSetting();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean connect() {
        if (!HPRTPrinterHelper.IsOpened()) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            List<String> s = new ArrayList<>();
            List<String> address = new ArrayList<>();
            for (BluetoothDevice bt : pairedDevices) {
                s.add(bt.getName());
                address.add(bt.getAddress());
            }
            showBlueToothDevices(s, address);
            return false;
        } else {
            return true;
        }
    }

    private void showBlueToothDevices(List<String> data, final List<String> address) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, data);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    HPRTPrinterHelper printer = new HPRTPrinterHelper(context, "MPT-II");
                    int res = HPRTPrinterHelper.PortOpen("Bluetooth," + address.get(i));
                    if (res == -1) {
                        Toast.makeText(context, "Connection Failure", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Connection Successful", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void openBluetoothSetting() {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.android.settings",
                "com.android.settings.bluetooth.BluetoothSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
