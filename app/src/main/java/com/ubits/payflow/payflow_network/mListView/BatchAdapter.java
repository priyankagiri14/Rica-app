package com.ubits.payflow.payflow_network.mListView;

/**
 * Created by sauda on 2017/08/13.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.Http;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.mDataObject.payflowdata;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class BatchAdapter extends BaseAdapter {

    Context c;
    ArrayList<payflowdata> payflowdatas;
    LayoutInflater inflater;
    private String username;
    private ProgressDialog dialog;

    public BatchAdapter(Context c, ArrayList<payflowdata> payflowdatas) {
        this.c = c;
        this.payflowdatas = payflowdatas;
        SharedPreferences sharedPreferences = c.getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return payflowdatas.size();
    }

    @Override
    public Object getItem(int position) {
        return payflowdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return payflowdatas.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.batch_custom_list, parent, false);
        }

        TextView nameTxt = convertView.findViewById(R.id.nameTxt);
        TextView descTxt = convertView.findViewById(R.id.descTxt);
        TextView btnViewDetails = convertView.findViewById(R.id.tvViewMasjid);

        String batchNumber = payflowdatas.get(position).getBatch();
        nameTxt.setText(batchNumber);
        descTxt.setText(payflowdatas.get(position).getDate());
        btnViewDetails.setTag(batchNumber);

        //ITEM CLICKS
        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String batchNumber = v.getTag().toString();
                showSerials(batchNumber);
            }
        });
        return convertView;
    }

    private void showSerials(String customBarcode) {
        dialog = ProgressDialog.show(c, "", "Loading...");
        String url = Config.BASE_URL+"app//search/customSearch.php";
        String params = "Supplier_Batch_Number=" + customBarcode;
        params += "&user_id=" + username;
        Http.post(url, params, new Http.IOnResultLisnter() {
            @Override
            public void onResult(String result) {
                dialog.dismiss();
                List<String> serials = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String value = jsonArray.optString(i);
                        serials.add(value);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_list_item_1, serials);
                    builder.setAdapter(adapter, null);
                    builder.setPositiveButton("Ok", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
