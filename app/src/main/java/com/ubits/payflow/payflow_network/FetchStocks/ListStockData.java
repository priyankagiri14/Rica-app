package com.ubits.payflow.payflow_network.FetchStocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitClient;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStockData extends AppCompatActivity implements Callback<FetchStocksResponse> {

    ListView listView;
    List<String> stringList = new ArrayList<>();
    List<Inhand> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stock_data);
        listView = (ListView)findViewById(R.id.listview);
        fetchdata();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.listview,stringList);
        listView.setAdapter(arrayAdapter);

    }

    private void fetchdata() {
        Web_Interface webInterface = RetrofitToken.getClient().create(Web_Interface.class);
      //  Call<FetchStocksResponse> stocksResponse = webInterface.requestFetchStocks();
       // stocksResponse.enqueue(this);
    }

    @Override
    public void onResponse(Call<FetchStocksResponse> call, Response<FetchStocksResponse> response) {

        Log.d("onResponse: ",response.toString());
        if (response.isSuccessful() && response.code() == 200) {

            list=response.body().getData().getStocks().getInhand();
            for(int i = 0; i<list.size();i++)
            {
                String jsonResponse = list.get(i).getStockData().getStockId();
                stringList.add(jsonResponse);
            }
        }
    }

    @Override
    public void onFailure(Call<FetchStocksResponse> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
