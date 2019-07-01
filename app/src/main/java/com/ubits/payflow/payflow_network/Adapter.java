package com.ubits.payflow.payflow_network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sauda on 2017/08/03.
 */

public class Adapter extends ArrayAdapter {


    Context c;
    String [] networks;
    int[] images;



    public Adapter(Context ctx, String[] networks, int[] images) {

        super(ctx, R.layout.model,networks);
        this.c=ctx;
        this.networks=networks;
        this.images=images;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.model,null);

        }

        TextView txtNetworks = (TextView) convertView.findViewById(R.id.txtNetworks);
        ImageView imgnetworks = (ImageView) convertView.findViewById(R.id.imgnetworks);


        // SET DATA

        txtNetworks.setText(networks[position]);


        imgnetworks.setImageResource(images[position]);


        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.model, null);

        }

        TextView txtNetworks = (TextView) convertView.findViewById(R.id.txtNetworks);
        ImageView imgnetworks = (ImageView) convertView.findViewById(R.id.imgnetworks);


        // SET DATA

        txtNetworks.setText(networks[position]);


        imgnetworks.setImageResource(images[position]);


        return convertView;
    }
}
