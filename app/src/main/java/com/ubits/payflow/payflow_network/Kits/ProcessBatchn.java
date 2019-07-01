package com.ubits.payflow.payflow_network.Kits;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.Http;
import com.ubits.payflow.payflow_network.IntentIntegrator;
import com.ubits.payflow.payflow_network.IntentResult;
import com.ubits.payflow.payflow_network.PrinterUtil;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.adapter.ProcessBatchnAdapter;
import com.ubits.payflow.payflow_network.model.ProcessBatch;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProcessBatchn extends AppCompatActivity implements View.OnClickListener, Handler.Callback {

    private static final int HANDLER_SEARCH_BY_SUPPLIER = 121;
    private RecyclerView recyclerView;
    private ProcessBatchnAdapter adapter;
    private TextView tvScanResult;
    private ArrayList<ProcessBatch> arrayList = new ArrayList<>();
    private ProgressDialog dialog;
    String url = Config.BASE_URL+"app/barcode/";

    private TextView tvTile;
    private TextView tvCount;
    private EditText etCustomBatch;
    private int lastClickedItem;
    private Handler mHandler = new Handler(this);
    private PrinterUtil mPrinterUtil = new PrinterUtil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_batchn);

        initView();
        initListeners();
    }

    private void initView() {
        etCustomBatch = findViewById(R.id.et_custom_supplier_batch);
        tvTile = findViewById(R.id.tv_titlebar);
        tvTile.setText("Process Sim");
        tvCount = findViewById(R.id.tv_count);
        tvScanResult = findViewById(R.id.tv_scan_result);
        recyclerView = findViewById(R.id.rv_add_sim);
        adapter = new ProcessBatchnAdapter(getApplicationContext(), arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        generateBarCode();

    }

    private void initListeners() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.btn_scan_sim).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_print).setOnClickListener(this);
        findViewById(R.id.scan_custom_batch).setOnClickListener(this);
    }

    private void generateBarCode() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        Http.get(url, new Http.IOnResultLisnter() {
            @Override
            public void onResult(String result) {

                dialog.dismiss();
                String barcode = null;
                try {
                    if (result != null) {
                        JSONObject parentObject = new JSONObject(result);
                        String status = parentObject.optString("status");
                        barcode = parentObject.optString("data");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tvScanResult.setText(barcode);
            }
        });
        //...............................................
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;

            case R.id.btn_scan_sim:
            case R.id.scan_custom_batch:
                lastClickedItem = v.getId();
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;

            case R.id.btn_save:
                clearData();
                break;

            case R.id.btn_print:
                String barcode = tvScanResult.getText().toString();
                mPrinterUtil.print(barcode);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            switch (lastClickedItem) {
                case R.id.btn_scan_sim:
                    SetRecycle(scanContent);
                    break;
                case R.id.scan_custom_batch:
                    etCustomBatch.setText(scanContent);
                    mHandler.sendEmptyMessage(HANDLER_SEARCH_BY_SUPPLIER);
                    break;
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*
     *  Set RecycleView AdapterView
     * */
    public void SetRecycle(String code) {
        if (code != null) {
            String items[] = code.split(",");
            for (String item : items) {
                item = item.replaceAll("\\r|\\n", "").trim();
                ProcessBatch scanData = new ProcessBatch();
                scanData.setBarCode(item);
                arrayList.add(scanData);
            }
            adapter.notifyDataSetChanged();
            tvCount.setText("Count: " + arrayList.size());
        }

    }

    /*
     * Clear List
     * */
    public void clearData() {
        if (arrayList.size() > 0) {
            List<String> codes = new ArrayList<>();
            for (ProcessBatch item : arrayList) {
                if (item.getBarCode().trim().length() > 0)
                    codes.add(item.getBarCode().trim());
            }
            String serials = TextUtils.join(",", codes);

            dialog.setMessage("Please wait");
            dialog.show();

            String customBarcode = tvScanResult.getText().toString();
            String urls = Config.BASE_URL + "app/custombatch?custombarcode=" + customBarcode + "&supplier_batch_number=" + serials;
            Http.get(urls, new Http.IOnResultLisnter() {
                @Override
                public void onResult(String result) {
                    dialog.dismiss();
                    try {
                        JSONObject parentObject = new JSONObject(result);
                        String status = parentObject.getString("Status");
                        String BatchTATUS = parentObject.getString("Description");
                        if (status.equals("SUCCESS")) {
                            etCustomBatch.setText("");
                            arrayList.clear(); //clear list
                            adapter.notifyDataSetChanged();
                            tvCount.setText("Count: " + arrayList.size());
                            Toast.makeText(ProcessBatchn.this, BatchTATUS.toString(), Toast.LENGTH_LONG).show();
                            generateBarCode();
                        } else if (status.equals("ERROR")) {
                            Toast.makeText(ProcessBatchn.this, "Batch Issue", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ProcessBatchn.this, "An error occur, Try again", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(ProcessBatchn.this, "Please add supplier list", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLER_SEARCH_BY_SUPPLIER:
                String batchNumber = etCustomBatch.getText().toString();
                if (batchNumber.trim().length() == 0) break;
                dialog = ProgressDialog.show(ProcessBatchn.this, "", "Loading...");
                String url = Config.BASE_URL+"app//search/customSearch.php";
                String params = "Supplier_Batch_Number=" + batchNumber;
                Http.post(url, params, new Http.IOnResultLisnter() {
                    @Override
                    public void onResult(String result) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String value = jsonArray.optString(i);
                                SetRecycle(value);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        return true;
    }
}
