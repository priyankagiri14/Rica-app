package com.tms.ontrack.mobile.AgentBatchesGet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tms.ontrack.mobile.R;

import java.util.List;

public class BatchesAdapter extends ArrayAdapter {

        private List<Batches> batchesDataList;
        private Context mContext;
    public static ArrayAdapter<List> arrayAdapter;
    private int resource;


    public BatchesAdapter(Context context, int resource,
                                    List<Batches> batchesList) {
            super(context, resource, batchesList);
            batchesDataList = batchesList;
            mContext = context;

        }

        @Override
        public int getCount() {
            return batchesDataList.size();
        }

        @Override
        public Batches getItem(int position) {
            return batchesDataList.get(position);
        }

        @Override
        public View getView(int position, View view, @NonNull ViewGroup parent) {

            if (view == null) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.searchview_layout, parent, false);
            }

            Batches batches = getItem(position);

            TextView textbatches = (TextView) view.findViewById(R.id.textbatches);
            assert batches != null;
            textbatches.setText(batches.getBatches());
            Log.d("batchesdatalist",batchesDataList.toString());
            //arrayAdapter.add(batchesDataList);

            return view;
        }
    }

