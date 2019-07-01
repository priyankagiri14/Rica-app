package com.ubits.payflow.payflow_network.mListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.mDataObject.payflowdata;

import java.util.ArrayList;

public class PendingAdapter extends BaseAdapter{



    Context c;
    ArrayList<payflowdata> payflowdatas;
    LayoutInflater inflater;

    public PendingAdapter(Context c, ArrayList<payflowdata> payflowdatas) {
        this.c = c;
        this.payflowdatas = payflowdatas;

        //INITIALIE
        inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return payflowdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return payflowdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return payflowdatas.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.pending_custom_list,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        TextView propellantTxt= (TextView) convertView.findViewById(R.id.propellantTxts);
        TextView descTxt= (TextView) convertView.findViewById(R.id.descTxt);

        nameTxt.setText(payflowdatas.get(position).getBatch());
        propellantTxt.setText(payflowdatas.get(position).getDate());
        descTxt.setText(payflowdatas.get(position).getDescription());

        //ITEM CLICKS
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(c,payflowdatas.get(position).getBatch(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}

