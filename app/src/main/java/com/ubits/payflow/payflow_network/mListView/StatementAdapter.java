package com.ubits.payflow.payflow_network.mListView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Kits.ViewStatementDetails;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.mDataObject.statementdata;

import java.util.ArrayList;

/**
 * Created by sauda on 2017/08/29.
 */

public class StatementAdapter extends BaseAdapter {

    Context c;
    ArrayList<statementdata> statementdatas;
    LayoutInflater inflater;

    public StatementAdapter(Context c, ArrayList<statementdata> statementdatas) {
        this.c = c;
        this.statementdatas= statementdatas;

        //INITIALIE
        inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return statementdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return statementdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return statementdatas.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.statement_airtime_custom_list,parent,false);
        }

        TextView nameTxt= (TextView) convertView.findViewById(R.id.txtstatref);
        TextView propellantTxt= (TextView) convertView.findViewById(R.id.txtstatdate);
        TextView descTxt= (TextView) convertView.findViewById(R.id.txtstatid);

        nameTxt.setText(statementdatas.get(position).getReference());
        propellantTxt.setText(statementdatas.get(position).getStatDate());
        descTxt.setText("R " + statementdatas.get(position).getStatID());

        //ITEM CLICKS
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(c, ViewStatementDetails.class);
                intent.putExtra("batch", statementdatas.get(position).getReference());
                c.startActivity(intent);

              //  Toast.makeText(c,statementdatas.get(position).getReference(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
