package com.ubits.payflow.payflow_network.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.model.ProcessBatch;
import java.util.ArrayList;

public class ProcessBatchnAdapter extends RecyclerView.Adapter<ProcessBatchnAdapter.ViewHolder> {

    Context context;
    ArrayList<ProcessBatch> arrayList = new ArrayList<>();

    public ProcessBatchnAdapter(Context context, ArrayList<ProcessBatch> arrayList) {

        this.context = context;
        this.arrayList = arrayList;
        Log.d("scan",arrayList.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_process_batchn, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvBarcode.setText(arrayList.get(position).getBarCode());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBarcode;

        public ViewHolder(View itemView) {
            super(itemView);

            tvBarcode = (TextView) itemView.findViewById(R.id.tv_barcode);
        }
    }
}
