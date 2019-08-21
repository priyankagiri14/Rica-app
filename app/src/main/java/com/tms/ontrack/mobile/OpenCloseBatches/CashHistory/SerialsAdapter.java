package com.tms.ontrack.mobile.OpenCloseBatches.CashHistory;

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

public class SerialsAdapter extends ArrayAdapter {

    private List<Serials> serialsDataList;
    private Context mContext;
    public static ArrayAdapter<List> arrayAdapter;
    private int resource;


    public SerialsAdapter(Context context, int resource,
                          List<Serials> batchesList) {
        super(context, resource, batchesList);
        serialsDataList = batchesList;
        mContext = context;

    }

    @Override
    public int getCount() {
        return serialsDataList.size();
    }

    @Override
    public Serials getItem(int position) {
        return serialsDataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.searchview_layout, parent, false);
        }

        Serials serials = getItem(position);

        TextView textbatches = (TextView) view.findViewById(R.id.textbatches);
        assert serials != null;
        textbatches.setText(serials.getSerials());
        Log.d("batchesdatalist",serialsDataList.toString());
        //arrayAdapter.add(batchesDataList);

        return view;
    }
}
