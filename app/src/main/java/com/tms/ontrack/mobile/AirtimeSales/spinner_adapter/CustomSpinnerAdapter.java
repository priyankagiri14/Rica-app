package com.tms.ontrack.mobile.AirtimeSales.spinner_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tms.ontrack.mobile.R;

import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    int images[];
    List<String> networks;
    LayoutInflater inflter;
 
    public CustomSpinnerAdapter(Context applicationContext, int[] images, List<String> networks) {
        this.context = applicationContext;
        this.images = images;
        this.networks = networks;
        inflter = (LayoutInflater.from(applicationContext));
    }
 
    @Override
    public int getCount() {
        return images.length;
    }
 
    @Override
    public Object getItem(int i) {
        return null;
    }
 
    @Override
    public long getItemId(int i) {
        return 0;
    }
 
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_custom_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(images[i]);
        names.setText(networks.get(i));
        return view;
    }
}