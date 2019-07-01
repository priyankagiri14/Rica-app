package com.ubits.payflow.payflow_network.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.ubits.payflow.payflow_network.Http;
import com.ubits.payflow.payflow_network.model.PlaceAutocomplete;

import java.util.List;

public class PlaceArrayAdapter extends ArrayAdapter<PlaceAutocomplete> implements Filterable {
    private List<PlaceAutocomplete> mResultList;
    private Context mContext;

    public PlaceArrayAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    mResultList = Http.getPrdications(mContext, constraint.toString());
                    if (mResultList != null) {
                        // Results
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}