package com.ubits.payflow.payflow_network.BatchesGet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.Driver.Stock_allocate.ListViewItemDTO;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.ListViewItemViewHolder;
import com.ubits.payflow.payflow_network.R;

import java.util.List;

public class BatchesGetListAdapter extends BaseAdapter {

    private List<BatchesGetResponse> batchesGetLists;
    private List<Body> bodyList;
    private Context context;

    public BatchesGetListAdapter(Context context, List<BatchesGetResponse> batchesGetLists) {
        this.context = context;
        this.batchesGetLists = batchesGetLists;
    }


    @Override
    public int getCount() {
        return batchesGetLists.size();
    }

    @Override
    public Object getItem(int position) {
        return batchesGetLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.batches_get_list_item,parent,false);
        }

        TextView batchesgettext = convertView.findViewById(R.id.batches_get_list_text);
        CheckBox batchesgetcheckbox = convertView.findViewById(R.id.batches_get_list_checkbox);

        final BatchesGetResponse thisBatchesGetResponse = batchesGetLists.get(position);

        bodyList = thisBatchesGetResponse.getBody();
        for (int i=0;i<bodyList.size();i++) {
            batchesgettext.setText(bodyList.get(i).getBatchNo());
        }
        return convertView;
    }
}
