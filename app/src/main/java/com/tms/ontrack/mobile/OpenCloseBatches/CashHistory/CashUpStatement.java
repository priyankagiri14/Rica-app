package com.tms.ontrack.mobile.OpenCloseBatches.CashHistory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.levitnudi.legacytableview.LegacyTableView;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.levitnudi.legacytableview.LegacyTableView.DESKTOP;
import static com.levitnudi.legacytableview.LegacyTableView.ECOKENYA;
import static com.levitnudi.legacytableview.LegacyTableView.GOLDALINE;
import static com.levitnudi.legacytableview.LegacyTableView.LAVICI;
import static com.levitnudi.legacytableview.LegacyTableView.LEVICI;
import static com.levitnudi.legacytableview.LegacyTableView.MAASAI;
import static com.levitnudi.legacytableview.LegacyTableView.MESH;
import static com.levitnudi.legacytableview.LegacyTableView.OCEAN;
import static com.levitnudi.legacytableview.LegacyTableView.ORIO;
import static com.levitnudi.legacytableview.LegacyTableView.SKELETON;

public class CashUpStatement extends AppCompatActivity {

    List<String> bodyArrayList1 = new ArrayList<>();
    List<String> bodyArrayList2 = new ArrayList<>();
    List<String> bodyArrayList3 = new ArrayList<>();
    List<String> bodyArrayList4 = new ArrayList<>();

    TextView norecords;
    ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_up_statement);

        norecords = (TextView)findViewById(R.id.norecords);
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();

        LegacyTableView legacyTableView = (LegacyTableView)findViewById(R.id.legacytableview);

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<CashHistoryResponse> cashHistoryResponseCall = web_interface.requestCashHistory(0,0);
        cashHistoryResponseCall.enqueue(new Callback<CashHistoryResponse>() {
            @Override
            public void onResponse(Call<CashHistoryResponse> call, Response<CashHistoryResponse> response) {

                assert response.body() != null;
                
                if (response.body().getBody().size() != 0) {
                    List<Body> bodyList = new ArrayList<>();
//                    Log.d("onBody: ", response.body().getBody().get(0).getFrom().getName());
                    bodyList = response.body().getBody();
                    LegacyTableView.insertLegacyTitle("Date", "Type", "Db/Cr", "User");

                    for (int i = 0; i < bodyList.size(); i++) {
                       bodyArrayList1.add(bodyList.get(i).getCreatedAt());
//                       String datetime = date.substring(0,10);
                        bodyArrayList2.add(bodyList.get(i).getType());
                        bodyArrayList3.add(String.valueOf(bodyList.get(i).getAmount()));
                        //bodyArrayList4.add(bodyList.get(i).getUser().getName());
                        bodyArrayList4.add(bodyList.get(i).getFrom().getName());

                        LegacyTableView.insertLegacyContent((bodyArrayList1.get(i)).substring(0,10), bodyArrayList2.get(i), bodyArrayList3.get(i), bodyArrayList4.get(i));
                    }
                    legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
                    legacyTableView.setContent(LegacyTableView.readLegacyContent());

                    legacyTableView.setTablePadding(20);
                    legacyTableView.setContentTextSize(40);
                    legacyTableView.setTitleTextSize(40);
                    legacyTableView.setTheme(ECOKENYA);

                    legacyTableView.build();
                    progressBar.cancel();
                }
                else
                    {
                        //String finaldate = null;
//                        String date = "2019-07-24 11:07:24.331+0000";
//                        String datetime =  date.substring(0,10);
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'+'SSSS");
//                        Date date1 = null;
//                        try {
//                            date1 = simpleDateFormat.parse(date);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        try{
//                            date1 = simpleDateFormat.parse(date);
//                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-mm-yyyy");
//                            finaldate = simpleDateFormat1.format(date1);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        legacyTableView.setVisibility(View.GONE);
                        norecords.setVisibility(View.VISIBLE);
                        progressBar.cancel();
//                        norecords.setText(datetime);
                        //Toast.makeText(CashUpStatement.this, "No Records Found!", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<CashHistoryResponse> call, Throwable t) {

            }
        });
    }
}
