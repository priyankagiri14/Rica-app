package com.tms.ontrack.mobile.BatchesReceived;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tms.ontrack.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class DriverBatchesReceivedAdapter extends ArrayAdapter {

    private List<DriverBatchesReceived> driverBatchesReceivedDataList;
    private Context mContext;
    SparseBooleanArray mSparseBooleanArray;


    boolean[] itemChecked;
    ArrayList<String> strings = new ArrayList<>();


    public DriverBatchesReceivedAdapter(Context context, int resource,
                                        List<DriverBatchesReceived> driverBatchesReceivedList) {
        super(context, resource, driverBatchesReceivedList);

        driverBatchesReceivedDataList = driverBatchesReceivedList;
        mContext = context;
        itemChecked = new boolean[driverBatchesReceivedList.size()];
        mSparseBooleanArray = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return driverBatchesReceivedDataList.size();
    }

    @Override
    public DriverBatchesReceived getItem(int position) {
        return driverBatchesReceivedDataList.get(position);
    }

    class MyViewHolder {
        public TextView batchesreceivedtext;
        public TextView batchesvaluereceivedtext;
        public CheckBox batchesreceivedcheckbox;

        MyViewHolder(View view) {
            batchesreceivedtext = (TextView) view.findViewById(R.id.batches_received_list_text);
            batchesreceivedcheckbox = (CheckBox) view.findViewById(R.id.batches_received_list_checkbox);
            batchesvaluereceivedtext = (TextView) view.findViewById(R.id.batches_value_received_list_text);
        }
    }

 /*   @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.batches_received_list_item, parent, false);
        }

        DriverBatchesReceived driverBatchesReceived = getItem(position);

        TextView textbatches = (TextView) view.findViewById(R.id.batches_received_list_text);
        CheckBox batchesreceivedcheckbox = (CheckBox) view.findViewById(R.id.batches_received_list_checkbox);
        TextView batchesvaluereceivedtext = (TextView)view.findViewById(R.id.batches_value_received_list_text);
        batchesreceivedcheckbox.setChecked(false);
        assert driverBatchesReceived != null;
        textbatches.setText(driverBatchesReceived.getDriverBathcesreceived());

        if (itemChecked[position])
            batchesreceivedcheckbox.setChecked(true);
        else
            batchesreceivedcheckbox.setChecked(false);

        batchesreceivedcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (batchesreceivedcheckbox.isChecked()) {
                    itemChecked[position] = true;
                }
                else
                {
                    itemChecked[position] = false;
                }
            }
        });
        Log.d("batchesdatalist",driverBatchesReceivedDataList.toString());
        return view;
    }
}*/

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        MyViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.batches_received_list_item, parent, false);
            holder = new MyViewHolder(row);
            holder = new MyViewHolder(row);
            row.setTag(holder);
            holder.batchesreceivedcheckbox.setTag(position);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        holder.batchesreceivedcheckbox.setTag(position);
        holder.batchesreceivedcheckbox.setChecked(mSparseBooleanArray.get(position));
        //batchesgetcheckbox.setChecked(fa);

        holder.batchesreceivedtext.setText(driverBatchesReceivedDataList.get(position).getDriverBathcesreceived());
//        holder.batchesvaluereceivedtext.setText("R "+bodyList.get(position).getTotalValue().toString());
//        holder.batchesreceivedcheckbox.setTag(position);
        holder.batchesreceivedcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/*
                int position = (int) buttonView.getTag();
*/
                mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                if (isChecked) {
                    driverBatchesReceivedDataList.get(position).setIschecked(true);
                } else {
                    driverBatchesReceivedDataList.get(position).setIschecked(false);
                }
            }
        });

       /* for(int i=0;i<driverBatchesReceivedDataList.size();i++)
        {
            if(holder.batchesreceivedcheckbox.)
        }*/
        return row;
    }
}
