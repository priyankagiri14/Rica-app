package com.ubits.payflow.payflow_network.Kits;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.requests.FormData;
import com.byteshaft.requests.HttpRequest;
import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.IntentIntegrator;
import com.ubits.payflow.payflow_network.IntentResult;
import com.ubits.payflow.payflow_network.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class IndividualSimn extends AppCompatActivity implements View.OnClickListener, HttpRequest.OnReadyStateChangeListener, HttpRequest.OnErrorListener {
    private Button scanBtn, RicaBtn;
    Spinner sp;
    private TextView formatTxt;
    private EditText c, etBarcode;
    String username, Status, RicasTATUS;
    private String Network = null;
    private TextView tvTile;
    private ImageView backButton;
    private ImageView imgMtn, imgVodaCom, imgCell, imgTelcom, imgMtnSecond, imgVodaComSecond, imgCellSecond, imgTelcomSecond;

    private ImageView imgCbOne, imgCbTwo, imgCbThree, imgCbFour;


    String url = Config.BASE_URL + "app/kit/";

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_simn);

        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);
        dialog = new ProgressDialog(this);

        tvTile = (TextView) findViewById(R.id.tv_titlebar);
        tvTile.setText(R.string.title_activity_individual_simn);

        backButton = (ImageView) findViewById(R.id.back_btn);
        imgMtn = (ImageView) findViewById(R.id.iv_mtn);
        imgVodaCom = (ImageView) findViewById(R.id.iv_vodacom);
        imgCell = (ImageView) findViewById(R.id.iv_CellC);
        imgTelcom = (ImageView) findViewById(R.id.iv_Telkom);

        imgMtnSecond = (ImageView) findViewById(R.id.iv_mtn_second);
        imgVodaComSecond = (ImageView) findViewById(R.id.iv_vodacom_second);
        imgCellSecond = (ImageView) findViewById(R.id.iv_CellC_second);
        imgTelcomSecond = (ImageView) findViewById(R.id.iv_Telkom_second);

        imgCbOne = (ImageView) findViewById(R.id.img1);
        imgCbTwo = (ImageView) findViewById(R.id.img2);
        imgCbThree = (ImageView) findViewById(R.id.img3);
        imgCbFour = (ImageView) findViewById(R.id.img4);


        scanBtn = (Button) findViewById(R.id.scan_button);
        RicaBtn = (Button) findViewById(R.id.btnricaindiidual);
        etBarcode = (EditText) findViewById(R.id.scan_content);

        imgMtn.setVisibility(View.VISIBLE);
        imgVodaCom.setVisibility(View.VISIBLE);
        imgCell.setVisibility(View.VISIBLE);
        imgTelcom.setVisibility(View.VISIBLE);

        scanBtn.setOnClickListener(this);
        RicaBtn.setOnClickListener(this);
        imgMtn.setOnClickListener(this);
        imgVodaCom.setOnClickListener(this);
        imgCell.setOnClickListener(this);
        imgTelcom.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.back_btn) {
            finish();
        } else if (v.getId() == R.id.scan_button) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        } else if (v.getId() == R.id.iv_mtn) {
            Network = "MTN";

            imgMtnSecond.setVisibility(View.VISIBLE);
            imgMtn.setVisibility(View.GONE);
            imgVodaComSecond.setVisibility(View.GONE);
            imgVodaCom.setVisibility(View.VISIBLE);
            imgCellSecond.setVisibility(View.GONE);
            imgCell.setVisibility(View.VISIBLE);
            imgTelcomSecond.setVisibility(View.GONE);
            imgTelcom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.VISIBLE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_vodacom) {

            Network = "VODACOM";

            imgVodaComSecond.setVisibility(View.VISIBLE);
            imgVodaCom.setVisibility(View.GONE);
            imgMtnSecond.setVisibility(View.GONE);
            imgMtn.setVisibility(View.VISIBLE);
            imgCellSecond.setVisibility(View.GONE);
            imgCell.setVisibility(View.VISIBLE);
            imgTelcomSecond.setVisibility(View.GONE);
            imgTelcom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.VISIBLE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_CellC) {

            Network = "CELL";

            imgCellSecond.setVisibility(View.VISIBLE);
            imgCell.setVisibility(View.GONE);
            imgVodaComSecond.setVisibility(View.GONE);
            imgVodaCom.setVisibility(View.VISIBLE);
            imgMtnSecond.setVisibility(View.GONE);
            imgMtn.setVisibility(View.VISIBLE);
            imgTelcomSecond.setVisibility(View.GONE);
            imgTelcom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.VISIBLE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_Telkom) {

            Network = "TELCOME";

            imgTelcomSecond.setVisibility(View.VISIBLE);
            imgTelcom.setVisibility(View.GONE);
            imgCellSecond.setVisibility(View.GONE);
            imgCell.setVisibility(View.VISIBLE);
            imgVodaComSecond.setVisibility(View.GONE);
            imgVodaCom.setVisibility(View.VISIBLE);
            imgMtnSecond.setVisibility(View.GONE);
            imgMtn.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.VISIBLE);


        } else if (v.getId() == R.id.btnricaindiidual) {

            if (isOnline()) {
                if (TextUtils.isEmpty(Network)) {
                    Toast.makeText(IndividualSimn.this, "Please Select Network", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(etBarcode.getText().toString())) {
                    Toast.makeText(IndividualSimn.this, "Please Scan Barcode", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    /*
                     * Send Params Request on Server
                     * */

                    sendData(Network, username, etBarcode.getText().toString());
                }

            } else {
                Toast.makeText(IndividualSimn.this, "Check Network Connection ", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //........................................................................................................
    private void sendData(String network, String username, String KitNumber) {
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        FormData data = new FormData();
        data.append(FormData.TYPE_CONTENT_TEXT, "Network", network);
        data.append(FormData.TYPE_CONTENT_TEXT, "username", username);
        data.append(FormData.TYPE_CONTENT_TEXT, "kitnumber ", KitNumber);

        HttpRequest request = new HttpRequest(getApplicationContext());
        request.setOnReadyStateChangeListener(this);
        request.setOnErrorListener(this);
        request.open("POST", url);
        request.send(data);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            etBarcode.setText(scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
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
                                Toast.makeText(IndividualSimn.this, messagedetails.toString(), Toast.LENGTH_LONG).show();

                            } else {
                                // show success
                                Toast.makeText(IndividualSimn.this, "Your Query has been sent to head office, we will call you soon!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IndividualSimn.this, "Error with your Query Request", Toast.LENGTH_LONG).show();

                        }
                }
        }
    }

    @Override
    public void onError(HttpRequest request, int readyState, short error, Exception exception) {

    }

    /*
     * Check Network Connection
     * */
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
///

}
