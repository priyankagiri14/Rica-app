package com.ubits.payflow.payflow_network.AgentBatchesReceived;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.R;

import java.util.List;

public class AgentBatchesReceivedListAdapter extends BaseAdapter {

    private List<AgentBatchesReceivedResponse> batchesGetLists;
    private List<Body> bodyList;
    private Context context;


    public AgentBatchesReceivedListAdapter(Context context, List<Body> bodyList) {
        this.context = context;
        this.bodyList = bodyList;

    }

    class MyViewHolder {
        public TextView batchesreceivedtext;
        public CheckBox batchesreceivedcheckbox;

        MyViewHolder(View view) {
            batchesreceivedtext = (TextView) view.findViewById(R.id.agents_batches_received_list_text);
            batchesreceivedcheckbox = (CheckBox) view.findViewById(R.id.agent_batches_received_list_checkbox);
        }
    }


    @Override
    public int getCount() {
        return bodyList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return bodyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        MyViewHolder holder = null;

        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.agent_batches_received_list_item, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) row.getTag();
        }
        //batchesgetcheckbox.setChecked(fa);

        holder.batchesreceivedtext.setText(bodyList.get(position).getBatchNo());
        holder.batchesreceivedcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    bodyList.get(position).setIschecked(true);
                }
                else
                {
                    bodyList.get(position).setIschecked(false);
                }
            }
        });
        return row;
    }
}
