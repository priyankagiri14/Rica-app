package com.tms.ontrack.mobile.OpenCloseBatches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tms.ontrack.mobile.R;
import java.util.List;

public class OpenCloseListAdapter extends BaseAdapter {

    private List<String> serialsGetResponses;

    private Context context;

    public OpenCloseListAdapter(Context context, List<String> serialsGetResponses) {
        this.context = context;
        this.serialsGetResponses = serialsGetResponses;

    }

    class MyViewHolder {
        public TextView serialreceivedtext;


        MyViewHolder(View view) {
            serialreceivedtext = (TextView) view.findViewById(R.id.agents_serials_received_list_text);
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
            row = inflater.inflate(R.layout.open_close_list_item, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) row.getTag();
        }
        //batchesgetcheckbox.setChecked(fa);

        holder.serialreceivedtext.setText(serialsGetResponses.get(position));
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
