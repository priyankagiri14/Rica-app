package com.ubits.payflow.payflow_network.Kits;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.Http;
import com.ubits.payflow.payflow_network.IntentIntegrator;
import com.ubits.payflow.payflow_network.IntentResult;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.adapter.CustomListAdapter;
import com.ubits.payflow.payflow_network.adapter.PlaceArrayAdapter;
import com.ubits.payflow.payflow_network.model.PlaceAutocomplete;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Rican extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, Handler.Callback {
    private static final int HANDLER_SEARCH_SERIAL = 121;

    String referencenumber, idNumber, username, CountryCode, Suburb, Region, FirstName, LastName, areaCode, dialingno,
            address1, PostalCode, scanDocument = null;
    String scanBarcode = "", identity;
    int scanCode;
    String IDType = "N";
    TextView txtfill;
    EditText txtName;
    String Network = "";
    EditText txtPassportCountry, txtLastName, txtPhoneNumber, txtPostalCode, txtSuburbs, etCityName,
            txtFirstName, etScanIdPassport;

    private String item;
    private AutoCompleteTextView txtSimNumber;
    private Button scanBtn, scanid;
    private Spinner spnDocumets;

    private String kitNumber;
    private List<String> dataList = new ArrayList<String>();

    private ProgressDialog dialog;
    private ImageView ivMtn, ivVodacom, ivCellc, ivTellkom, ivMtnLarze, ivVodacomLarze, ivCellcLarze, ivTellkomLarze;
    ArrayAdapter<String> arrayAdapter;
    private AutoCompleteTextView etAddress;
    private ImageView backBtn;
    private TextView tvTitlebar;
    private Button btnMainRICA;
    private ImageView imgCbOne, imgCbTwo, imgCbThree, imgCbFour;


    private PlaceArrayAdapter mPlaceArrayAdapter;
    private Handler mHandler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rican);


        backBtn = (ImageView) findViewById(R.id.back_btn);


        imgCbOne = (ImageView) findViewById(R.id.img1);
        imgCbTwo = (ImageView) findViewById(R.id.img2);
        imgCbThree = (ImageView) findViewById(R.id.img3);
        imgCbFour = (ImageView) findViewById(R.id.img4);

        btnMainRICA = (Button) findViewById(R.id.btnMainRICA);
        backBtn.setOnClickListener(this);

        Intent intent = getIntent();
        identity = intent.getStringExtra("identity");

        tvTitlebar = (TextView) findViewById(R.id.tv_titlebar);

        if (identity.equals(identity)) {
            tvTitlebar.setText(identity);
        } else {
        }


        txtfill = (TextView) findViewById(R.id.txtFill);
        txtLastName = findViewById(R.id.tv_surname);
        etScanIdPassport = (EditText) findViewById(R.id.et_scan_id_passport);
        dialog = new ProgressDialog(this);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, dataList);
        txtPassportCountry = (EditText) findViewById(R.id.txtPassportCountry);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtName = (EditText) findViewById(R.id.txtFirstName);
        txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);
        txtSuburbs = (EditText) findViewById(R.id.txtSuburb);
        etCityName = (EditText) findViewById(R.id.et_suburb);
        txtPostalCode = (EditText) findViewById(R.id.txtpostalcode);

        ivMtn = (ImageView) findViewById(R.id.iv_mtn);
        ivVodacom = (ImageView) findViewById(R.id.iv_vodacom);
        ivCellc = (ImageView) findViewById(R.id.iv_CellC);
        ivTellkom = (ImageView) findViewById(R.id.iv_Telkom);

        ivMtnLarze = (ImageView) findViewById(R.id.iv_mtn_second);
        ivVodacomLarze = (ImageView) findViewById(R.id.iv_vodacom_second);
        ivCellcLarze = (ImageView) findViewById(R.id.iv_CellC_second);
        ivTellkomLarze = (ImageView) findViewById(R.id.iv_Telkom_second);

        txtSimNumber = (AutoCompleteTextView) findViewById(R.id.txtSimNumber);


        ivMtn.setVisibility(View.VISIBLE);
        ivVodacom.setVisibility(View.VISIBLE);
        ivCellc.setVisibility(View.VISIBLE);
        ivTellkom.setVisibility(View.VISIBLE);

        ivMtn.setOnClickListener(this);
        ivVodacom.setOnClickListener(this);
        ivCellc.setOnClickListener(this);
        ivTellkom.setOnClickListener(this);
        btnMainRICA.setOnClickListener(this);


        txtSimNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHandler.removeMessages(HANDLER_SEARCH_SERIAL);
                mHandler.sendEmptyMessageDelayed(HANDLER_SEARCH_SERIAL, 700);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        txtPassportCountry.setVisibility(View.GONE);
        spnDocumets = (Spinner) findViewById(R.id.spinnerID);
        spnDocumets.setOnItemSelectedListener(this);
        scanBtn = (Button) findViewById(R.id.scan_rica_button);
        scanid = (Button) findViewById(R.id.scan_document);

        scanid.setOnClickListener(this);
        scanBtn.setOnClickListener(this);
        etAddress = findViewById(R.id.etAddress);

        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1);

        etAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtPostalCode.setText("");
                txtSuburbs.setText("");
                etCityName.setText("");
                PlaceAutocomplete place = mPlaceArrayAdapter.getItem(position);
                Http.findAddress(Rican.this, place, txtSuburbs, etCityName, txtPostalCode);
            }
        });
        txtSimNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHandler.removeMessages(HANDLER_SEARCH_SERIAL);
            }
        });
        etAddress.setAdapter(mPlaceArrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();

        if (item.equals("ID")) {
            IDType = "N";
            etScanIdPassport.setVisibility(View.VISIBLE);
            etScanIdPassport.setHint("ID");
            txtPassportCountry.setVisibility(View.GONE);
        } else if (item.equals("Passport No")) {
            txtPassportCountry.setVisibility(View.VISIBLE);
            etScanIdPassport.setVisibility(View.VISIBLE);
            etScanIdPassport.setHint("Passport Number");
            IDType = "P";
        } else if (item.equals("Business Registration")) {
            txtPassportCountry.setVisibility(View.GONE);
            etScanIdPassport.setVisibility(View.VISIBLE);
            etScanIdPassport.setHint("Business Registration");
            IDType = "B";
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClick(View v) {
        if (v.getId() == R.id.scan_rica_button) {
            scanCode = 1;
            SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Rica", "Yes");
            editor.commit();
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        } else if (v.getId() == R.id.scan_document) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(Rican.this);
            scanIntegrator.initiateScan();
            scanCode = 2;
            txtPassportCountry.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.back_btn) {
            finish();
        } else if (v.getId() == R.id.iv_mtn) {
            Network = "MTN";
            ivMtnLarze.setVisibility(View.VISIBLE);
            ivMtn.setVisibility(View.GONE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.VISIBLE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_vodacom) {
            Network = "Vodacom";
            ivVodacomLarze.setVisibility(View.VISIBLE);
            ivVodacom.setVisibility(View.GONE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.VISIBLE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_CellC) {
            Network = "CellC";
            ivCellcLarze.setVisibility(View.VISIBLE);
            ivCellc.setVisibility(View.GONE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);
            ivTellkomLarze.setVisibility(View.GONE);
            ivTellkom.setVisibility(View.VISIBLE);


            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.VISIBLE);
            imgCbFour.setVisibility(View.GONE);


        } else if (v.getId() == R.id.iv_Telkom) {
            Network = "Telkom";
            ivTellkomLarze.setVisibility(View.VISIBLE);
            ivTellkom.setVisibility(View.GONE);
            ivCellcLarze.setVisibility(View.GONE);
            ivCellc.setVisibility(View.VISIBLE);
            ivVodacomLarze.setVisibility(View.GONE);
            ivVodacom.setVisibility(View.VISIBLE);
            ivMtnLarze.setVisibility(View.GONE);
            ivMtn.setVisibility(View.VISIBLE);

            imgCbOne.setVisibility(View.GONE);
            imgCbTwo.setVisibility(View.GONE);
            imgCbThree.setVisibility(View.GONE);
            imgCbFour.setVisibility(View.VISIBLE);


        } else if (v.getId() == R.id.btnMainRICA) {


            if (isOnline()) {
                txtfill.setText("Processing Trasnaction...");
                scanDocument = etScanIdPassport.getText().toString();
                referencenumber = txtSimNumber.getText().toString();
                if (referencenumber.length() == 0) {
                    Toast.makeText(Rican.this, "Please enter Barcode number", Toast.LENGTH_LONG).show();
                } else if (Network.toString() == "") {
                    Toast.makeText(Rican.this, "Please Select Network Type", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(scanDocument) && item.equals("Passport No")) {
                    Toast.makeText(Rican.this, "Please enter Passport number", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(scanDocument) && item.equals("Driving License")) {
                    Toast.makeText(Rican.this, "Please enter Driving License ", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(scanDocument) && item.equals("Asylum Document")) {
                    Toast.makeText(Rican.this, "Please enter PassPort ", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    FirstName = txtName.getText().toString();
                    if (FirstName == "") {
                        Toast.makeText(Rican.this, "Please enter a valid Name", Toast.LENGTH_LONG).show();
                    } else {
                        CountryCode = "ZAF";
                        LastName = txtLastName.getText().toString();
                        areaCode = "+27";
                        dialingno = txtPhoneNumber.getText().toString();
                        if (dialingno != null && dialingno.length() > 0)
                            areaCode = dialingno.substring(2, 3);
                        address1 = etAddress.getText().toString();
                        PostalCode = txtPostalCode.getText().toString();
                        Suburb = txtSuburbs.getText().toString();
                        Region = txtSuburbs.getText().toString();
                        if (identity.equals("Rica"))
// not worked parameter
                            sendRicaData(Network, referencenumber, scanDocument, IDType, CountryCode, FirstName, LastName, areaCode, dialingno, address1, PostalCode, Suburb, Region, username);
                        else
                            //  worked parameter
                            sendRicaSimData(Network, referencenumber, IDType, scanDocument, FirstName, LastName, dialingno, address1, Suburb, areaCode);
                    }
                }
            } else {
                Toast.makeText(Rican.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();

            }

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();

            if (scanCode == 1) {
                txtSimNumber.setText(scanContent);
            } else if (scanCode == 2) {
                etScanIdPassport.setText(scanContent);
            }

        } else {
            etScanIdPassport.setText("Data not received!");
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
        mHandler.removeMessages(HANDLER_SEARCH_SERIAL);
    }

    private void sendRicaData(String Network, String referencenumber, String idNumber, String IDType, String CountryCode, String FirstName, String LastName, String areaCode, String dialingno, String address1, String PostalCode, String Suburb, String Region, String Username) {
        List<String> data = new ArrayList<>();

        data.add("username=" + Username);
        data.add("cellnumber=" + dialingno);

        data.add("groupName=" + "60WPQ");
        data.add("password=" + "Ep67MbGi");
        data.add("agent=" + "FLHEL");
        data.add("network=" + Network);
        data.add("operatorID=" + "FLHEL");
        data.add("MSISDNnetwork=" + Network);
        data.add("existing=" + "false");

        data.add("referenceType=" + "StarterPackRef");
        data.add("referenceNumber=" + referencenumber);
        data.add("last4SIM=" + "");
        data.add("idinfoCountryCode=" + "ZA");
        data.add("idNumber=" + idNumber);
        data.add("idType=" + IDType + "");
        data.add("firstName=" + FirstName);
        data.add("lastName=" + LastName);

        data.add("contactCountryCode=" + "ZA");

        data.add("areaCode=" + areaCode);
        data.add("dialingNo=" + dialingno);

        data.add("individualAddress1=" + address1);
        data.add("individualAddress2=" + "");
        data.add("individualAddress3=" + "");
        data.add("individualCountryCode=" + "ZA");
        data.add("individualPostalCode=" + PostalCode);
        data.add("individualRegion=" + Region);
        data.add("individualSuburb=" + Suburb);

        String city = etCityName.getText().toString();
        data.add("individualCity=" + city);

        data.add("networkOptIn=" + "");
        data.add("retailerOptIn=" + "");
        data.add("portDate=" + "N");
        data.add("portInCheck=" + "false");
        data.add("portMsisdn=" + "");
        data.add("proofOfAddress=" + "");


        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        String params = TextUtils.join("&", data);
        Log.d(Rican.class.getName(), params);

        hitRicaApi(Config.RICA_URL, params);
    }

    /**
     * set data for rica sim
     */

    private void sendRicaSimData(String Network, String barcode, String documnetType, String documentNumber, String firstName, String lastName, String dialingno, String address1, String Suburb, String areaCode) {
        List<String> data = new ArrayList<>();

        data.add("network_type=" + Network);
        data.add("barcode=" + barcode);
        data.add("document_type=" + documnetType + "");
        data.add("document_number=" + documentNumber);
        data.add("firstname=" + firstName);
        data.add("lastName=" + lastName);
        data.add("dialing_no=" + dialingno);
        data.add("agent_id=" + "QKCAB");
        data.add("individualAddress=" + address1);
        data.add("individualSuburb=" + Suburb);
        data.add("area_code=" + areaCode);

        String params = TextUtils.join("&", data);
        Log.d(Rican.class.getName(), params);

        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        hitRicaApi(Config.RICA_OFFLINE_URL, params);
    }

    private void hitRicaApi(String url, String params) {
        Http.post(url, params, new Http.IOnResultLisnter() {
            @Override
            public void onResult(String result) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.optString(("status"));
                    String messagedetails = jsonObject.optString(("messageDetails"));
                    txtfill.setText(messagedetails);
                    if (code.equals("Failure") || code.equals("RetryLater")) {
                        String message = jsonObject.optString("message");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Rican.this);
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", null);
                        builder.setTitle("Error!");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        txtName.setText("");
                        txtLastName.setText("");
                        etScanIdPassport.setText("");
                        txtSimNumber.setText("");
                        txtPhoneNumber.setText("");
                        etAddress.setText("");
                        txtPostalCode.setText("");
                        txtSuburbs.setText("");
                        etCityName.setText("");

                        AlertDialog.Builder builder = new AlertDialog.Builder(Rican.this);
                        builder.setMessage(messagedetails);
                        builder.setPositiveButton("OK", null);
                        builder.setTitle("Success");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Rican.this, "Error with your registration, please try again later " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_SEARCH_SERIAL:
                String serial = txtSimNumber.getText().toString().trim();
                if (serial.length() >= 4) {
                    String url = Config.BASE_URL + "app/search/kitSearch.php?search=" + serial + "&network=" + Network + "&user_id=" + username;
                    hitSearchApi(url);
                }
                break;
        }
        return true;
    }

    /**
     * @param url
     */
    private void hitSearchApi(String url) {
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        Http.get(url, new Http.IOnResultLisnter() {
            @Override
            public void onResult(String result) {
                dialog.dismiss();
                try {
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray searchArray = parentObject.optJSONArray("data");
                    dataList.clear();
                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject dataObject = searchArray.optJSONObject(i);
                        kitNumber = dataObject.optString("kit_number");
                        dataList.add(kitNumber);
                    }
                    CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.autocompleteitem, dataList);
                    txtSimNumber.setAdapter(adapter);
                    txtSimNumber.showDropDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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