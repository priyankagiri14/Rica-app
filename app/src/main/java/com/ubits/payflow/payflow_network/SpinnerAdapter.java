package com.ubits.payflow.payflow_network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by sauda on 2017/08/03.
 */
public class SpinnerAdapter extends ArrayAdapter {

    Context c;
    String[] networks;

    public SpinnerAdapter(Context ctx, String[] networks) {

        super(ctx, R.layout.spinner_item, networks);
        this.c = ctx;
        this.networks = networks;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }

        TextView txtNetworks = (TextView) convertView.findViewById(R.id.txtNetworks);

        txtNetworks.setText(networks[position]);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }
        TextView txtNetworks = (TextView) convertView.findViewById(R.id.txtNetworks);
        txtNetworks.setText(networks[position]);
        return convertView;
    }
}
