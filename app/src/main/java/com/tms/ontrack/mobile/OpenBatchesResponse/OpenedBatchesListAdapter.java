package com.tms.ontrack.mobile.OpenBatchesResponse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tms.ontrack.mobile.R;

import java.util.List;

public class OpenedBatchesListAdapter extends BaseAdapter {

    private List<Body> serialsGetResponses;

    private Context context;

    public OpenedBatchesListAdapter(Context context, List<Body> serialsGetResponses) {
        this.context = context;
        this.serialsGetResponses = serialsGetResponses;

    }

    class MyViewHolder {
        public TextView serialopenedtext;


        MyViewHolder(View view) {
            serialopenedtext = (TextView) view.findViewById(R.id.agents_batches_opened_list_text);
        }
    }


    @Override
    public int getCount() {
        return serialsGetResponses.size();
    }

    @Override
    public Object getItem(int position)
    {
        return serialsGetResponses.get(position);
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
            row = inflater.inflate(R.layout.opened_batches_list_item, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) row.getTag();
        }
        //batchesgetcheckbox.setChecked(fa);

        holder.serialopenedtext.setText(serialsGetResponses.get(position).getNumber());
//        holder.batchesreceivedcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked)
//                {
//                    bodyList.get(position).setIschecked(true);
//                }
//                else
//                {
//                    bodyList.get(position).setIschecked(false);
//                }
//            }
//        });
        return row;
    }

}
