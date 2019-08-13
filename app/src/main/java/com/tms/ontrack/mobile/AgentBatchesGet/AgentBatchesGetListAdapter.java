package com.tms.ontrack.mobile.AgentBatchesGet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tms.ontrack.mobile.R;

import java.util.List;

public class AgentBatchesGetListAdapter extends BaseAdapter {

    private List<AgentBatchesGetResponse> batchesGetLists;
    private List<Body> bodyList;
    private Context context;


    public AgentBatchesGetListAdapter(Context context, List<Body> bodyList) {
        this.context = context;
        this.bodyList = bodyList;

    }

    class MyViewHolder {
        public TextView batchesgettext;
        public CheckBox batchesgetcheckbox;

        MyViewHolder(View view) {
            batchesgettext = (TextView) view.findViewById(R.id.agents_list_text);
            batchesgetcheckbox = (CheckBox) view.findViewById(R.id.agent_list_checkbox);
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
            row = inflater.inflate(R.layout.agent_batches_get_list_item, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) row.getTag();
        }
        //batchesgetcheckbox.setChecked(fa);


        holder.batchesgettext.setText(bodyList.get(position).getBatchNo());
        holder.batchesgetcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
