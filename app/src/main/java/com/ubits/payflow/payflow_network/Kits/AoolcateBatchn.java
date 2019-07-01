package com.ubits.payflow.payflow_network.Kits;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AoolcateBatchn extends AppCompatActivity implements View.OnClickListener, TextWatcher, Handler.Callback, AdapterView.OnItemClickListener {

    private static final int HANDLER_SEARCH_SEARIAL = 121;
    private static final int HANDLER_GET_SERIALS_BY_SUPPLIER = 12;
    private static final int HANDLER_SEARCH_SUPPLIER = 15;
    private static final int HANDLER_SERIAL_COUNT_CHANGE = 33;
    private String username;
    private EditText txtbatchto;
    private AutoCompleteTextView txtscan_content;
    private ProgressDialog dialog;
    private ImageView backBtn;
    private TextView tvTitle;
    private EditText etSerials;
    private AutoCompleteTextView etCustomBatch;
    private TextView tvCount;
    private Handler mHandler = new Handler(this);
    private List<String> mTempSerials = new ArrayList<>();
    private CustomListAdapter mArrayAdapter;
    private int lastClickedItem = 0;
    private Spinner spNetwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aoolcate_batchn);

        tvTitle = findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_aoolcate_batchn);
        backBtn = findViewById(R.id.back_btn);
        etSerials = findViewById(R.id.et_serials);
        dialog = new ProgressDialog(this);

        etCustomBatch = findViewById(R.id.et_custom_supplier_batch);
        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);

        txtbatchto = findViewById(R.id.txtbatchto);
        tvCount = findViewById(R.id.txtbatchescount);
        spNetwork = findViewById(R.id.sp_network);

        findViewById(R.id.scan_batch).setOnClickListener(this);
        findViewById(R.id.btnAllocate).setOnClickListener(this);
        findViewById(R.id.scan_custom_batch).setOnClickListener(this);
        findViewById(R.id.btnCancelallocate).setOnClickListener(this);

        txtscan_content = findViewById(R.id.txtscan_content);
        txtscan_content.addTextChangedListener(this);
        etSerials.addTextChangedListener(serialTextWatcher);

        txtscan_content.setOnItemClickListener(this);
        backBtn.setOnClickListener(this);


        etCustomBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mHandler.removeMessages(HANDLER_SEARCH_SUPPLIER);
                mHandler.sendEmptyMessageDelayed(HANDLER_SEARCH_SUPPLIER, 600);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCustomBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mHandler.removeMessages(HANDLER_SEARCH_SUPPLIER);
                mHandler.sendEmptyMessage(HANDLER_GET_SERIALS_BY_SUPPLIER);
            }
        });

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnCancelallocate) {
            tvCount.setText("Count: 0");
            etCustomBatch.setText("");
            etSerials.setText("");
        } else if (v.getId() == R.id.back_btn) {
            finish();
        } else if (v.getId() == R.id.scan_custom_batch) {
            lastClickedItem = R.id.scan_custom_batch;
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        } else if (v.getId() == R.id.scan_batch) {
            lastClickedItem = R.id.scan_batch;
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        } else if (v.getId() == R.id.btnAllocate) {
            String network = (String) spNetwork.getSelectedItem();
            if (isOnline()) {
                String agentId = txtbatchto.getText().toString();
                if (agentId.trim().length() == 0) {
                    Toast.makeText(AoolcateBatchn.this, "Please fill Agent number", Toast.LENGTH_LONG).show();
                } else if (etCustomBatch.getText().toString().length() == 0) {
                    Toast.makeText(AoolcateBatchn.this, "Please Scan custom barcode", Toast.LENGTH_LONG).show();
                } else if (etSerials.getText().toString().length() == 0) {
                    Toast.makeText(AoolcateBatchn.this, "No serials found for this custom barcode", Toast.LENGTH_LONG).show();
                } else {
                    dialog = ProgressDialog.show(this, "", "Loading...");
                    String serial = etSerials.getText().toString().trim();
                    List<String> serials = Arrays.asList(serial.split("\n"));
                    String content = TextUtils.join(",", serials);
                    String params = "agent_id=" + agentId
                            + "&serials=" + content
                            + "&custom_batch=" + etCustomBatch.getText().toString()
                            + "&allocate_from=" + username
                            + "&network=" + network;

                    Http.post(Config.API_ALLOCATE_BATCH, params, new Http.IOnResultLisnter() {
                        @Override
                        public void onResult(String result) {
                            dialog.dismiss();
                            try {
                                JSONObject parentObject = new JSONObject(result);
                                String status = parentObject.optString("Status");
                                String msg = parentObject.optString("Description");
                                if (status.equals("SUCCESS")) {
                                    txtscan_content.setText("");
                                    txtbatchto.setText("");
                                    etSerials.setText("");
                                    etCustomBatch.setText("");

                                    AlertDialog.Builder builder = new AlertDialog.Builder(AoolcateBatchn.this);
                                    builder.setTitle("Success");
                                    builder.setMessage(msg);
                                    builder.setPositiveButton("Ok", null);

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                } else {
                                    Toast.makeText(AoolcateBatchn.this, "Batch Issue ", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(AoolcateBatchn.this, "Please Retry", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "Check network connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            try {
                String scanContent = scanningResult.getContents();
                switch (lastClickedItem) {
                    case R.id.scan_custom_batch:
                        etCustomBatch.setText(scanContent);
                        mHandler.sendEmptyMessage(HANDLER_GET_SERIALS_BY_SUPPLIER);
                        break;
                    case R.id.scan_batch:
                        txtscan_content.removeTextChangedListener(AoolcateBatchn.this);
                        txtscan_content.setText(scanContent);
                        txtscan_content.addTextChangedListener(AoolcateBatchn.this);

                        String items[] = scanContent.split(",");
                        for (String code : items) {
                            code = code.replaceAll("\\r|\\n", "").trim();
                            etSerials.append(code + "\n");
                        }
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to scan barcode", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mHandler.removeMessages(HANDLER_SEARCH_SEARIAL);
        mHandler.sendEmptyMessageDelayed(HANDLER_SEARCH_SEARIAL, 800);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_SEARCH_SEARIAL:
                String serial = txtscan_content.getText().toString();
                if (serial.trim().length() == 0) break;
                dialog = ProgressDialog.show(AoolcateBatchn.this, "", "Loading...");
                String httpUrl = Config.BASE_URL + "app/search/kitSearch.php?search=" + serial + "&user_id=" + username;
                Http.get(httpUrl, new Http.IOnResultLisnter() {
                    @Override
                    public void onResult(String result) {
                        mTempSerials.clear();
                        dialog.dismiss();
                        try {
                            JSONObject parentObject = new JSONObject(result);
                            JSONArray searchArray = parentObject.optJSONArray("data");
                            for (int i = 0; i < searchArray.length(); i++) {
                                JSONObject dataObject = searchArray.optJSONObject(i);
                                String kitNumber = dataObject.optString("kit_number");
                                mTempSerials.add(kitNumber);
                            }
                            mArrayAdapter = new CustomListAdapter(AoolcateBatchn.this, R.layout.autocompleteitem, mTempSerials);
                            txtscan_content.setAdapter(mArrayAdapter);
                            txtscan_content.showDropDown();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case HANDLER_GET_SERIALS_BY_SUPPLIER:
                String batchNumber = etCustomBatch.getText().toString();
                if (batchNumber.trim().length() == 0) break;
                dialog = ProgressDialog.show(AoolcateBatchn.this, "", "Loading...");
                String url = Config.BASE_URL + "app//search/customSearch.php";
                String params = "Supplier_Batch_Number=" + batchNumber;
                params += "&user_id=" + username;
                Http.post(url, params, new Http.IOnResultLisnter() {
                    @Override
                    public void onResult(String result) {
                        String txt = "";
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String value = jsonArray.optString(i);
                                txt += value + "\n";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        etSerials.append(txt);
                    }
                });
                break;
            case HANDLER_SERIAL_COUNT_CHANGE:
                String s1 = etSerials.getText().toString().trim();
                if (s1.length() > 0) {
                    List<String> serials = Arrays.asList(s1.split("\n"));
                    tvCount.setText("Count: " + serials.size());
                } else {
                    tvCount.setText("Count: 0");
                }
                break;

            case HANDLER_SEARCH_SUPPLIER:
                if (etCustomBatch.getText().toString().length() >= 6) {
                    String hUrl = Config.BASE_URL + "app//search/allocateSearch.php?search=" + etCustomBatch.getText().toString() + "&user_id=" + username;
                    hitSearchApi(hUrl);
                }
                break;
        }
        return true;
    }

    /*
     * Hit Custom Search API
     * */
    private void hitSearchApi(String url) {
        dialog = ProgressDialog.show(AoolcateBatchn.this, "", "Loading...");
        dialog.setCancelable(true);
        Http.get(url, new Http.IOnResultLisnter() {
            @Override
            public void onResult(String result) {
                try {
                    dialog.dismiss();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray searchArray = parentObject.optJSONArray("data");

                    List<String> items = new ArrayList<>();
                    for (int i = 0; i < searchArray.length(); i++) {
                        JSONObject dataObject = searchArray.optJSONObject(i);
                        String kitNumber = dataObject.optString("kit_number");
                        items.add(kitNumber);
                    }
                    CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.autocompleteitem, items);
                    etCustomBatch.setAdapter(adapter);
                    etCustomBatch.showDropDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mHandler.removeMessages(HANDLER_SEARCH_SEARIAL);
        String value = mArrayAdapter.getItem(position);
        etSerials.append(value + "\n");
    }

    TextWatcher serialTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHandler.removeMessages(HANDLER_SERIAL_COUNT_CHANGE);
            mHandler.sendEmptyMessageAtTime(HANDLER_SERIAL_COUNT_CHANGE, 700);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
