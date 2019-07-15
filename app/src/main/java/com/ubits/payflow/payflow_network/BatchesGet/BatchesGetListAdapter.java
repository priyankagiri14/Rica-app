package com.ubits.payflow.payflow_network.BatchesGet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.Driver.Stock_allocate.ListViewItemDTO;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.ListViewItemViewHolder;
import com.ubits.payflow.payflow_network.R;

import java.util.List;

public class BatchesGetListAdapter extends BaseAdapter {

    private List<BatchesGetResponse> batchesGetLists;
    private List<Body> bodyList;
    private Context context;


    public BatchesGetListAdapter(Context context, List<Body> bodyList) {
        this.context = context;
        this.bodyList = bodyList;

    }

    class MyViewHolder {
        public TextView batchesgettext;
        public CheckBox batchesgetcheckbox;

        MyViewHolder(View view) {
            batchesgettext = (TextView) view.findViewById(R.id.batches_get_list_text);
            batchesgetcheckbox = (CheckBox) view.findViewById(R.id.batches_get_list_checkbox);
        }
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
            row = inflater.inflate(R.layout.batches_get_list_item, parent, false);
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
//             batchesgettext = view.findViewById(R.id.batches_get_list_text);
//            CheckBox batchesgetcheckbox = view.findViewById(R.id.batches_get_list_checkbox);
//
//
//
//            viewHolder = new ListViewItemViewHolder(view);
//
//            viewHolder.setItemCheckbox(batchesgetcheckbox);
//
//            viewHolder.setItemTextView(batchesgettext);
//
//            view.setTag(viewHolder);
//
////            batchesgetcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////                @Override
////                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                    if()
////                }
////            });
//        }
//
//
//        final BatchesGetResponse thisBatchesGetResponse = batchesGetLists.get(position);
//
//        bodyList = thisBatchesGetResponse.getBody();
//        //batchesgetcheckbox.setChecked(fa);
//        for (int i=0;i<bodyList.size();i++)
//            batchesgettext.setText(bodyList.get(i).getBatchNo());
//        return view;
//    }
//}
