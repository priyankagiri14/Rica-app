package com.ubits.payflow.payflow_network.General;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.SpinnerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class Query extends AppCompatActivity implements AdapterView.OnItemSelectedListener, HttpRequest.OnErrorListener, HttpRequest.OnReadyStateChangeListener {
    String username, QT, Status, Query;
    Button btnRegister;
    EditText txtQuery;
    String RegisterURL = Config.BASE_URL + "app/query/register.php";
    String urls = Config.BASE_URL+"app/query/ ";
    private TextView tvtitle;
    private ImageView backBtn;
    private ProgressDialog dialog;


    Spinner sp;

    String[] CustomerType = {"Kits", "Airtime/Bundle", "Electricity", "Payments", "Cash Out", "Cash IN", "Client Complaint", "Rica"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query2);

        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvtitle = (TextView) findViewById(R.id.tv_titlebar);
        tvtitle.setText(R.string.title_activity_query);
        dialog = new ProgressDialog(this);


        sp = (Spinner) findViewById(R.id.spinnerQuery);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, CustomerType);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                Query = CustomerType[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        txtQuery = (EditText) findViewById(R.id.txtQuery);

        btnRegister = (Button) findViewById(R.id.btnProcessQuery);

        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (isOnline()) {
                    if (txtQuery.getText().toString().equals("")) {

                        Toast.makeText(Query.this, "Please enter your QUERY", Toast.LENGTH_LONG).show();
                    } else {

                        QT = txtQuery.getText().toString();
                       // String urls = Config.BASE_URL + "app/query//tester/lastname/" + username + "/" + Query + "/" + QT + "/test/";

                        sendRicaData(username,Query,txtQuery.getText().toString());

                    }
                } else {
                    Toast.makeText(Query.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRicaData(String userId, String Query, String QuerysText) {

        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "Customer_ID", userId);
        data.append(FormData.TYPE_CONTENT_TEXT, "Query_Type", Query);
        data.append(FormData.TYPE_CONTENT_TEXT, "Query_text", QuerysText);

        HttpRequest request = new HttpRequest(getApplicationContext());
        request.setOnReadyStateChangeListener(this);
        request.setOnErrorListener(this);
        request.open("POST", urls);
        request.send(data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        if (item.equals("Kits")) {
            Query = "Kits";

        }


        if (item.equals("Airtime")) {
            Query = "Airtime";

        }

        if (item.equals("Payments")) {
            Query = "Payments";

        }

        if (item.equals("Cash Out")) {
            Query = "Cash Out";

        }

        if (item.equals("Cash In")) {
            Query = "Cash In";

        }

        if (item.equals("Client's Complaint")) {
            Query = "Client's Complaint";

        }

        if (item.equals("App Complaint")) {
            Query = "App Complaint";

        }

        if (item.equals("Rica")) {
            Query = "Rica";

        }

        if (item.equals("Client Account Not Created")) {
            Query = "Client Account Not Created";

        }

        if (item.equals("Electricity")) {
            Query = "Electricity";

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onError(HttpRequest request, int readyState, short error, Exception exception) {

    }

    @Override
    public void onReadyStateChange(HttpRequest request, int readyState) {
        switch (readyState) {
            case HttpRequest.STATE_DONE:
                switch (request.getStatus()) {
                    case HttpURLConnection.HTTP_OK:
                        dialog.dismiss();
                        Log.i("TAG", request.getResponseText());
                        try {


                            JSONObject jsonObject = new JSONObject(request.getResponseText());

                            String code = jsonObject.getString(("Status"));

                            String messagedetails = jsonObject.getString(("Description"));

                            if (code.equals("ERROR")) {
                                Toast.makeText(Query.this, messagedetails.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                // show success
                                Toast.makeText(Query.this, "Your Query has been sent to head office, we will call you soon!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Query.this, "Error with your Query Request", Toast.LENGTH_LONG).show();


                        }
                }
        }
    }



    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


}
