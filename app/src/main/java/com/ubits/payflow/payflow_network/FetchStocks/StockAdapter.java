//package com.ubits.payflow.payflow_network.FetchStocks;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.ubits.payflow.payflow_network.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class StockAdapter extends BaseAdapter {
//
//    private ArrayList<FetchStocksResponse> fetchStocksResponse;
//    private static LayoutInflater inflater=null;
//    private Context context;
//
//    public StockAdapter(Context context,ArrayList<FetchStocksResponse> fetchStocksResponse)
//    {
//        this.context = context;
//        this.fetchStocksResponse = fetchStocksResponse;
//    }
//
//    @Override
//    public int getCount() {
//        return fetchStocksResponse.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return fetchStocksResponse.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.lv_player, null, true);
//        return null;
//    }
//
////    public StockAdapter(Context context,List<FetchStocksResponse> fetchStocksResponse)
////    {
////        this.context = context;
////        this.fetchStocksResponse = fetchStocksResponse;
////        inflater = (LayoutInflater)Ced
////    }
////
////    @Override
////    public void add(@Nullable Object object) {
////        super.add(object);
////    }
////
////    @Override
////    public int getCount() {
////        return super.getCount();
////    }
////
////    @Nullable
////    @Override
////    public Object getItem(int position) {
////        return super.getItem(position);
////    }
////
////    @NonNull
////    @Override
////    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
////        return super.getView(position, convertView, parent);
////    }
//}
