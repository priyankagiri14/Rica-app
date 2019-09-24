package com.tms.ontrack.mobile.AirtimeSales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tms.ontrack.mobile.Agent_Login.AgentLoginResponse;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.RetrofitClient;
import com.tms.ontrack.mobile.Web_Services.RetrofitSmartCallToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirtimeRechargeActivity extends AppCompatActivity implements View.OnClickListener, Callback<ResponseBody> {
    private static final String TAG ="AirtimeRechargeActivity" ;
    Toolbar toolbar;
    private String productId;
    private Double retailValue;
    private String rechargeDesc;
    private EditText editTextRechargeNumber,editTextRechargeAmount,editTextMeterNumber;
    private Button buttonRecharge;
    private TextView tvRetail,tvDesc,tvRechargeLabel;
    private String smartLoadId,rechargeNumber,rechargeAmount,meterNumber;
    private String message;
    ProgressDialog progressDialog;
    private Integer errorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtime_recharge);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Recharge");
        buttonRecharge=findViewById(R.id.buttonRecharge);
        tvRetail=findViewById(R.id.tvRetail);
        tvDesc=findViewById(R.id.tvDesc);
        tvRechargeLabel=findViewById(R.id.tvRechargeLabel);
        editTextRechargeNumber=findViewById(R.id.editTextRechargeNumber);
        editTextRechargeAmount=findViewById(R.id.editTextRechargeAmount);
        editTextMeterNumber=findViewById(R.id.editTextMeterNumber);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        productId=getIntent().getStringExtra("product_id");
        retailValue=getIntent().getDoubleExtra("retail_value",0);
        Double rv=99999.99;
        rechargeDesc=getIntent().getStringExtra("recharge_description");
        if(productId!=null && retailValue.equals(rv) && rechargeDesc!=null)
        {
            //set text to textview if values not null
            tvRetail.setText("R2-R1000");
            tvDesc.setText(rechargeDesc);
        }
        else if(productId!=null && !retailValue.equals(rv) && rechargeDesc!=null)
        {
            tvRetail.setText(retailValue+"");
            tvDesc.setText(rechargeDesc);
            editTextRechargeAmount.setText(retailValue+"");
        }

        assert rechargeDesc != null;
        if(rechargeDesc.contains("Electricity"))
        {
            tvRechargeLabel.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate: showing meter number");
            editTextMeterNumber.setVisibility(View.VISIBLE);
            editTextRechargeNumber.setHint("Enter Number");
        }

        else if(rechargeDesc.contains("R10-125MB"))
        {
            tvRechargeLabel.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate: showing meter number");
            editTextMeterNumber.setVisibility(View.VISIBLE);
            editTextRechargeNumber.setHint("Enter Number");
        }

        else {
            Log.d(TAG, "onCreate: hide meter number");
            tvRechargeLabel.setVisibility(View.INVISIBLE);
            editTextMeterNumber.setVisibility(View.GONE);
            editTextRechargeNumber.setHint("Enter Recharge Number");
        }

        buttonRecharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
/*
        smartLoadId="27827419888";
*/
        smartLoadId="0739962418";
        rechargeNumber=editTextRechargeNumber.getText().toString();
        rechargeAmount=editTextRechargeAmount.getText().toString().trim();
        meterNumber=editTextMeterNumber.getText().toString();

         if (rechargeNumber.isEmpty())
        {
            Toast.makeText(this, "Enter number to recharge", Toast.LENGTH_SHORT).show();

        }
         else if(rechargeAmount.isEmpty())
         {
             Toast.makeText(this, "Enter recharge amount", Toast.LENGTH_SHORT).show();


         }
        else if(editTextMeterNumber.getVisibility()==View.VISIBLE)
        {
             if (rechargeNumber.isEmpty())
            {
                Toast.makeText(this, "Enter number to recharge", Toast.LENGTH_SHORT).show();

            }
            else if (rechargeAmount.isEmpty())
            {
                Toast.makeText(this, "Enter recharge amount", Toast.LENGTH_SHORT).show();

            }
            else if (meterNumber.isEmpty())
            {
                Toast.makeText(this, "Enter Meter Number", Toast.LENGTH_SHORT).show();

            }
            else {
                String client_ref=GenerateRandomString.randomString(6);
                String send_sms="true";
                Log.d(TAG, "onClick: clientref:"+client_ref);
                sendRechargeDetailsToServer(smartLoadId,rechargeNumber,rechargeAmount,client_ref,send_sms,productId,meterNumber);

            }
        }
        else {
            meterNumber="";
            String client_ref=GenerateRandomString.randomString(6);
            String send_sms="true";
            Log.d(TAG, "onClick: clientref:"+client_ref);
            sendRechargeDetailsToServer(smartLoadId,rechargeNumber,rechargeAmount,client_ref,send_sms,productId,meterNumber);
        }

    }

    private void sendRechargeDetailsToServer(String smartLoadId, String rechargeNumber, String rechargeAmount,
                                             String client_ref, String send_sms,String productId,String meterNumber) {
        Log.d(TAG, "sendRechargeDetailsToServer: retrofit");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Web_Interface webInterface= RetrofitSmartCallToken.getClient().create(Web_Interface.class);
        //creating request body to parse form data
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("smartloadId", smartLoadId);
            paramObject.put("clientReference", client_ref);
            paramObject.put("smsRecipientMsisdn", rechargeNumber);
            paramObject.put("deviceId",meterNumber);
            paramObject.put("productId", productId);
            paramObject.put("amount", rechargeAmount);
            paramObject.put("sendSms", send_sms);
            paramObject.put("smsProviderIdentifier","SmartCall");
            RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<ResponseBody> call= webInterface.requestSmartCallRecharge(body);
            //exeuting the service
            call.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful() && response.code() == 200) {
            try {
                JSONObject jObjSuccess = new JSONObject(response.body().string());
                Log.d(TAG,jObjSuccess.getString("statusMessage"));
                message=jObjSuccess.getString("statusMessage");
                Toasty.success(this, message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();

                //stopping progress
            } catch (Exception e) {
                Log.d(TAG, "onResponse: exception:"+e.getLocalizedMessage());
                //stopping progress
                progressDialog.dismiss();


            }

        }
        else {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Log.d(TAG,jObjError.getJSONObject("error").getString("message"));
                message=jObjError.getJSONObject("error").getString("message");
                errorCode=jObjError.getJSONObject("error").getInt("code");
                if(errorCode.equals(16))
                {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                if(errorCode.equals(9))
                {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                else if(errorCode.equals(1001))
                {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                }
                else if(errorCode.equals(1002))
                {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                else if(errorCode.equals(1003))
                {
                    message=jObjError.getJSONObject("error").getString("statusMessage");

                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                }
                else if(errorCode.equals(1004))
                {
                    message=jObjError.getJSONObject("error").getString("statusMessage");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                }
                else if(errorCode.equals(1005))
                {
                    message=jObjError.getJSONObject("error").getString("statusMessage");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();

                }
                else if(errorCode.equals(1006))
                {
                    message=jObjError.getJSONObject("error").getString("statusMessage");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                else if(errorCode.equals(1007))
                {
                    message=jObjError.getJSONObject("error").getString("statusMessage");
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }

                Log.d(TAG, "onResponse: else");
                progressDialog.dismiss();
                //stopping progress
            } catch (Exception e) {
                Log.d(TAG, "onResponse: exception:"+e.getLocalizedMessage());
                //stopping progress
                progressDialog.dismiss();

            }
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }
}
