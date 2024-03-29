package com.tms.ontrack.mobile.BatchesReceived;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tms.ontrack.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class BatchesReceivedListAdapter extends BaseAdapter implements Filterable {

    private List<BatchesReceivedResponse> batchesGetLists;
    private List<Body> bodyList;
    public List<Body> orig;
    private Context context;
    Double aDouble=123.12;



    public BatchesReceivedListAdapter(Context context,List<Body> bodyList) {
        this.context = context;
        this.bodyList = bodyList;


    }

    class MyViewHolder {
        public TextView batchesreceivedtext;
        public TextView batchesvaluereceivedtext;
        public CheckBox batchesreceivedcheckbox;

        MyViewHolder(View view) {
            batchesreceivedtext = (TextView) view.findViewById(R.id.batches_received_list_text);
            batchesreceivedcheckbox = (CheckBox) view.findViewById(R.id.batches_received_list_checkbox);
            batchesvaluereceivedtext = (TextView)view.findViewById(R.id.batches_value_received_list_text);
        }
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final List<Body> results = new ArrayList<Body>();
                if (orig == null)
                    orig = bodyList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Body g : orig) {
                            if (g.getBatchNo().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                bodyList = (ArrayList<Body>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return bodyList.size();
    }

    @Override
    public Object getItem(int position) {
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
            row = inflater.inflate(R.layout.batches_received_list_item, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) row.getTag();
        }
        //batchesgetcheckbox.setChecked(fa);

        holder.batchesreceivedtext.setText(bodyList.get(position).getBatchNo());
        holder.batchesreceivedcheckbox.setChecked(bodyList.get(position).isIschecked());
        holder.batchesvaluereceivedtext.setText("R "+bodyList.get(position).getTotalValue().toString());

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
