package com.tms.ontrack.mobile.AgentBatchesGet;

import android.content.Context;
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
        private int searchview_layout;

        public BatchesAdapter(Context context, int resource,
                                    List<Batches> batchesList) {
            super(context, resource, batchesList);
            batchesDataList = batchesList;
            mContext = context;
            searchview_layout = resource;
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
                        .inflate(searchview_layout, parent, false);
            }

            Batches batches = getItem(position);

            TextView dealsTv = (TextView) view.findViewById(R.id.textbatches);
            assert batches != null;
            dealsTv.setText(batches.getBatches());

            return view;
        }
    }

