package com.ubits.payflow.payflow_network.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.mDataObject.payflowdata;
import com.ubits.payflow.payflow_network.mListView.PendingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataParserPending extends AsyncTask<Void,Void,Integer> {

    Context c;
    ListView lv;
    String jsonData;
    ProgressDialog pd;
    ArrayList<payflowdata> payflowdatas=new ArrayList<>();

    public DataParserPending(Context c, ListView lv, String jsonData) {
        this.c = c;
        this.lv = lv;
        this.jsonData = jsonData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();
        if(result==0)
        {
            Toast.makeText(c,"Unable to parse",Toast.LENGTH_SHORT).show();
        }else {
            //CALL ADAPTER TO BIND DATA
            PendingAdapter adapter=new PendingAdapter(c,payflowdatas);
            lv.setAdapter(adapter);
        }
    }

    private int parseData()
    {
        try {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;
            payflowdatas.clear();
            payflowdata s=null;

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                int id=i;
                String name=jo.getString("Merchant_Code");
                String propellant=jo.getString("Customer_Name") + " " + jo.getString("Customer_Last_Name");
                String description=jo.getString("Account_Type");

                s=new payflowdata();
                s.setId(id);
                s.setBatch(name);
                s.setDate(propellant);
                s.setDescription(description);

                payflowdatas.add(s);

            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}

