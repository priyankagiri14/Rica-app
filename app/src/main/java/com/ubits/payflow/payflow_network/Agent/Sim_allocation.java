package com.ubits.payflow.payflow_network.Agent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.location.aravind.getlocation.GeoLocator;
import com.ubits.payflow.payflow_network.Agent.model.Simallocatemodel;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.driverattendancephoto.GetDriverAttendanceResponse;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.Body;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.FetchAgent;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.Ret;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sim_allocation extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public EditText fname, lname, address, pincode, subhurb, simserial, idnum,city;
    public RadioGroup networkrg;
    RadioButton vodacom, telkom, cellc, mtn;
    String network;
    Button simallocate;

    public void onCreate(Bundle savedInstancestate) {

        super.onCreate(savedInstancestate);
        setContentView(R.layout.simactivation);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.postal_code);
        subhurb = findViewById(R.id.subhurb);
        simserial = findViewById(R.id.serialnum);
        idnum = findViewById(R.id.idnum);
        city=findViewById(R.id.city);
        networkrg = findViewById(R.id.netwrokrg);
        networkrg.setOnCheckedChangeListener(this);
        vodacom = findViewById(R.id.vodacom);
        telkom = findViewById(R.id.telkom);
        mtn = findViewById(R.id.mtn);
        simallocate=findViewById(R.id.activate_sim);
        simallocate.setOnClickListener(this);
        cellc = findViewById(R.id.cellc);


    }

    private void getLocation() {
        GeoLocator geoLocator = new GeoLocator(getApplicationContext(), this);
        Log.d("startbranding", "getLocation: " + geoLocator.getLattitude() + "\n" + geoLocator.getLongitude());
        address.setText(geoLocator.getAddress());
        Log.d("locationda", address.toString());
    }

    @Override
    public void onClick(View v) {

        if(fname.getText().toString().length()==0 || lname.getText().length() ==0 || address.length()==0 ||
        pincode.getText().toString().length()==0 ||subhurb.getText().toString().length()==0 ||network.isEmpty()
        || simserial.getText().length()==0 || idnum.getText().length()==0){
            Toast.makeText(this,"Enter required fields",Toast.LENGTH_SHORT).show();
        }
        else{
            simallocation(simserial.getText().toString(),network,idnum.getText().toString(),fname.getText().toString(),lname.getText().toString(),
                    address.getText().toString(),pincode.getText().toString(),subhurb.getText().toString(),city.getText().toString());
        }
    }

    private void simallocation(String serial, String network, String idnum, String fname, String lname, String address, String postalcode, String subhurb, String city) {

        Web_Interface webInterface = Ret.getClient().create(Web_Interface.class);
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("serial",serial );
            paramObject.put("network", network);
            paramObject.put("idNo", idnum);
            paramObject.put("name", fname);
            paramObject.put("surname",lname);
            paramObject.put("address",address );
            paramObject.put("postalCode", postalcode);
            paramObject.put("suburb", subhurb);
            paramObject.put("city", city);
            Log.d("simalloacte data",serial+"\n"+network +"\n" +idnum+"\n"+fname+"\n"+lname +"\n" +address +"\n" +postalcode +"\n" +subhurb +"\n" +city);
            RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<Simallocatemodel> call= webInterface.simallocate(body,RetrofitToken.token);
            //exeuting the service
            call.enqueue(new Callback<Simallocatemodel>() {
                @Override
                public void onResponse(Call<Simallocatemodel> call, Response<Simallocatemodel> response) {
                    if(response.isSuccessful()||response.code()==200){
                        String message=response.body().getMessage();
                        Toasty.success(getApplicationContext(),message).show();

                    }
                    else{
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().toString());
                            Log.d("AgentLoginActivity", jObjError.getString("message"));
                            Toasty.info(getApplicationContext(), jObjError.getString("message")).show();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show();
                            //stopping progress
                        }
                    }
                }

                @Override
                public void onFailure(Call<Simallocatemodel> call, Throwable t) {
                    Log.d("simallocate",t.getLocalizedMessage());
                    Toasty.info(getApplicationContext(),t.getLocalizedMessage()).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.vodacom:
                // do operations specific to this selection
                network = vodacom.getText().toString();
                Log.d("age", network);
                break;

            case R.id.telkom:
                network=telkom.getText().toString();
                break;
            case R.id.mtn:
                // do operations specific to this selection
                network = mtn.getText().toString();
                break;

            case R.id.cellc:
                network=cellc.getText().toString();
                break;



        }
    }
}
