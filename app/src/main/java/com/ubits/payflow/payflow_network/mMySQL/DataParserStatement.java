package com.ubits.payflow.payflow_network.mMySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;
import com.ubits.payflow.payflow_network.mDataObject.statementdata;
import com.ubits.payflow.payflow_network.mListView.StatementAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by sauda on 2017/08/29.
 */


public class DataParserStatement extends AsyncTask<Void,Void,Integer>{

    Context c;
    ListView lv;
    String jsonData;

    ProgressDialog pd;
    ArrayList<statementdata> statementdatas=new ArrayList<>();

    public DataParserStatement(Context c, ListView lv, String jsonData) {
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
            StatementAdapter adapter = new StatementAdapter(c,statementdatas);
            lv.setAdapter(adapter);
        }
    }

    private int parseData()
    {
        try {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;
            statementdatas.clear();
            statementdata s=null;

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                int id=i;
                String name=jo.getString("Sold_Warehouse");
                String propellant=jo.getString("Date_import");
                String description=jo.getString("total");

                s=new statementdata();
                s.setId(id);
                s.setReference(name);
                s.setStatDate(propellant);
                s.setStatID(description);

                statementdatas.add(s);

            }

            return 1;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
