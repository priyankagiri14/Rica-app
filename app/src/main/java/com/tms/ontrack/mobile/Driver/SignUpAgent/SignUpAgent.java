package com.tms.ontrack.mobile.Driver.SignUpAgent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.kyanogen.signatureview.SignatureView;
import com.tms.ontrack.mobile.Driver.Contract.ContractResponse;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto.UploadedFile;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;
import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.Ret;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Utils.Pref;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.tms.ontrack.mobile.utils.Utils;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import info.androidhive.fontawesome.FontTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpAgent extends AppCompatActivity {
    View rowView;

    Spinner titlespinner,authorityspinner,statusspinner,warehousespinner,idspinner,doctypespinner;
    EditText firstname,lastname,username,password,idnum,passport,expirydate,
            streetno,streetname,suburb,city,province,postal_code,
            ricauser,ricapassword,ricagroup,
            email,mobno,altmobno;
    TextView nametext;
    FontTextView calender;
    public TextInputLayout idnumtext,passporttext,expirydatetext;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    String authorityname,authority;
    String imagename,imagefilename,imagepath,imagecreatedat,imageupdatedat;
    int authorityId,warehouseId,imageid;
    String title,status,idstring,doctype;
    String namestring,datestring;
    Button signupbtn,uploadbtn,addviewbtn,removeviewbtn;
    Boolean enableid,enablepass;
    private String filePath;
    FontTextView cancelbtn;

    EditText network1,dailyrate1,simcost1,activationcom1,ogr1,cib1,sims1;
    EditText network2,dailyrate2,simcost2,activationcom2,ogr2,cib2,sims2;
    EditText network3,dailyrate3,simcost3,activationcom3,ogr3,cib3,sims3;
    EditText network4,dailyrate4,simcost4,activationcom4,ogr4,cib4,sims4;

    EditText signedat;

    JSONArray attachmentArray = new JSONArray();
    JSONArray attachmentArray2 = new JSONArray();


    private static final String TAG = "SignatureActivity";
    Button buttonSign3,buttonSign6,btnClear,btnSave;
    private LinearLayout canvasView;
    private String path;
    // Creating Separate Directory for saving Generated Images
    private static final String IMAGE_DIRECTORY = "/signdemo";

    View view;
    SignatureView signatureView;
    AssetManager assetManager;
    File root;
    Bitmap bitmap;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout linearLayout;
    public int count = 0,count1 = 0;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_agent);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Please Wait... while signing up the user...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        titlespinner = findViewById(R.id.titlespinner);
        authorityspinner = findViewById(R.id.authorityspinner);
        statusspinner = findViewById(R.id.statusspinner);
        warehousespinner = findViewById(R.id.warehousespinner);
        idspinner = findViewById(R.id.idspinner);



        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        idnum = findViewById(R.id.idnum);
        passport = findViewById(R.id.passport);
        expirydate = findViewById(R.id.expirydate);
        expirydate.setClickable(false);
        expirydate.setFocusable(false);


        streetno = findViewById(R.id.streetno);
        streetname = findViewById(R.id.streetname);
        suburb = findViewById(R.id.suburb);
        city = findViewById(R.id.city);
        province = findViewById(R.id.province);
        postal_code = findViewById(R.id.postal_code);

        ricauser = findViewById(R.id.ricauser);
        ricapassword = findViewById(R.id.ricapassword);
        ricagroup = findViewById(R.id.ricagroup);

        email = findViewById(R.id.email);
        mobno = findViewById(R.id.mobno);
        altmobno = findViewById(R.id.altmobno);

        network1 = findViewById(R.id.network1);
        dailyrate1 = findViewById(R.id.dailyrate1);
        simcost1 = findViewById(R.id.simcost1);
        activationcom1 = findViewById(R.id.activatecom1);
        ogr1 = findViewById(R.id.ogr1);
        cib1 = findViewById(R.id.cib1);
        sims1 = findViewById(R.id.sims1);

        network2 = findViewById(R.id.network2);
        dailyrate2 = findViewById(R.id.dailyrate2);
        simcost2 = findViewById(R.id.simcost2);
        activationcom2 = findViewById(R.id.activatecom2);
        ogr2 = findViewById(R.id.ogr2);
        cib2 = findViewById(R.id.cib2);
        sims2 = findViewById(R.id.sims2);

        network3 = findViewById(R.id.network3);
        dailyrate3 = findViewById(R.id.dailyrate3);
        simcost3 = findViewById(R.id.simcost3);
        activationcom3 = findViewById(R.id.activatecom3);
        ogr3 = findViewById(R.id.ogr3);
        cib3 = findViewById(R.id.cib3);
        sims3 = findViewById(R.id.sims3);

        network4 = findViewById(R.id.network4);
        dailyrate4 = findViewById(R.id.dailyrate4);
        simcost4 = findViewById(R.id.simcost4);
        activationcom4 = findViewById(R.id.activatecom4);
        ogr4 = findViewById(R.id.ogr4);
        cib4 = findViewById(R.id.cib4);
        sims4 = findViewById(R.id.sims4);

        nametext = findViewById(R.id.nametext);

        calender = findViewById(R.id.calender);

        signedat = findViewById(R.id.signedat);

        doctypespinner = findViewById(R.id.doctypespinner);
        uploadbtn = findViewById(R.id.uploadbtn);
        addviewbtn = findViewById(R.id.addviewbtn);

        cancelbtn = findViewById(R.id.cancel);

        signupbtn = findViewById(R.id.signupbtn);


        linearLayout = (LinearLayout)findViewById(R.id.doctypelinear);

        addviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                  rowView = inflater.inflate(R.layout.doctypelinearlayout, null);
                  rowView.setId(View.generateViewId());
                // Add the new row before the add field button.
                linearLayout.addView(rowView, linearLayout.getChildCount() - 1);

                final Spinner doctypespinner = (Spinner)rowView.findViewById(R.id.doctypespinner);
                doctypespinner.setId(View.generateViewId());
                doctypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position == 0)
                        {

                        }
                        else
                        {
                            doctype = doctypespinner.getSelectedItem().toString();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                Button uploadbtn = (Button)rowView.findViewById(R.id.uploadbtn);
                uploadbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(doctypespinner.getSelectedItemPosition() == 0)
                        {
                            Toast.makeText(SignUpAgent.this, "Select Document Type", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            getImageFromCamera();
                        }
                    }
                });
            }
        });


        authorityAPICall();
        warehouseAPICall();

        idnumtext = findViewById(R.id.idnumtext);
        passporttext = findViewById(R.id.passporttext);
        expirydatetext = findViewById(R.id.expirydatetext);
        namestring = Pref.getFirstName(this);
        nametext.setText(namestring);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpAgent.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                if(month < 10){

                    String monthstring = "0" + month;
                    datestring = year+"-"+monthstring+"-"+dayOfMonth+"T00:00:00.000Z";
                }
                else {
                    datestring = year+"-"+month+"-"+dayOfMonth+"T00:00:00.000Z";
                }
                expirydate.setText(date);
            }
        };

        titlespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {

                }
                else
                {
                    title = titlespinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        authorityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //authority = authorityspinner.getSelectedItem().toString();
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                Call<ResponseAuthority> responseAuthorityCall = web_interface.requestResponseAuthority();
                responseAuthorityCall.enqueue(new Callback<ResponseAuthority>() {
                    @Override
                    public void onResponse(Call<ResponseAuthority> call, Response<ResponseAuthority> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            List<Body> bodyList = new ArrayList<>();
                            if (response.body() != null) {
                                bodyList = response.body().getBody();
                                authorityId = bodyList.get(position).getId();
                                authorityname = bodyList.get(position).getName();
                                authority = bodyList.get(position).getAuthority();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAuthority> call, Throwable t) {
                        Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        statusspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    status = statusspinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        warehousespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    warehouse = warehousespinner.getSelectedItem().toString();
                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
                Call<WarehouseResponse> warehouseResponseCall = web_interface.requestWarehouseResponse();
                warehouseResponseCall.enqueue(new Callback<WarehouseResponse>() {
                    @Override
                    public void onResponse(Call<WarehouseResponse> call, Response<WarehouseResponse> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            List<WarehouseBody> warehouseBodyList = new ArrayList<>();

                            if(response.body() != null)
                            {
                                warehouseBodyList = response.body().getBody();
                                warehouseId = warehouseBodyList.get(position).getId();

                            }
                        }
                        else{
                            Log.d("onResponse: ","ERROR");
                            Toast.makeText(SignUpAgent.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WarehouseResponse> call, Throwable t) {
                        Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        idspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    idnumtext.setVisibility(View.GONE);
                    passporttext.setVisibility(View.GONE);
                    expirydatetext.setVisibility(View.GONE);
                    calender.setVisibility(View.GONE);
                }
                else if(position == 1)
                {
                    idstring = idspinner.getSelectedItem().toString();
                    idnumtext.setVisibility(View.VISIBLE);
                    passporttext.setVisibility(View.GONE);
                    expirydatetext.setVisibility(View.GONE);
                    calender.setVisibility(View.GONE);
                }
                else if(position == 2)
                {
                    idstring = idspinner.getSelectedItem().toString();
                    idnumtext.setVisibility(View.GONE);
                    passporttext.setVisibility(View.VISIBLE);
                    expirydatetext.setVisibility(View.VISIBLE);
                    calender.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        doctypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {

                }
                else
                {
                    doctype = doctypespinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doctypespinner.getSelectedItemPosition() == 0)
                {
                    Toast.makeText(SignUpAgent.this, "Select Document Type", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getImageFromCamera();
                }
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(SignUpAgent.this, "No Functionalitily here for as of now....", Toast.LENGTH_SHORT).show();
                if(idspinner.getSelectedItem().toString().equals("ID")||idspinner.getSelectedItem().toString().equals("ID Type"))
                {
                    if (firstname.getText().toString().length() == 0)
                    {
                        firstname.requestFocus();
                    }
                    else if(lastname.getText().toString().length() == 0)
                    {
                        lastname.requestFocus();
                    }
                    else if(username.getText().toString().length() == 0)
                    {
                        username.requestFocus();
                    }
                    else if(password.getText().toString().length() == 0)
                    {
                        password.requestFocus();
                    }
                    else if(idnum.getText().toString().length() ==0)
                    {
                        idnum.requestFocus();
                    } else if (streetno.getText().toString().length() == 0)
                    {
                        streetno.requestFocus();
                    }
                    else if(streetname.getText().toString().length() == 0)
                    {
                        streetname.requestFocus();
                    }
                    else if(suburb.getText().toString().length() == 0)
                    {
                        suburb.requestFocus();
                    }
                    else if(city.getText().toString().length() == 0)
                    {
                        city.requestFocus();
                    }
                    else if(province.getText().toString().length() == 0)
                    {
                        province.requestFocus();
                    }
                    else if(postal_code.getText().toString().length() == 0)
                    {
                        postal_code.requestFocus();
                    }
                    else if(ricauser.getText().toString().length() == 0)
                    {
                        ricauser.requestFocus();
                    }
                    else if (ricapassword.getText().toString().length() == 0)
                    {
                        ricapassword.requestFocus();
                    }
                    else if (ricagroup.getText().toString().length() == 0)
                    {
                        ricagroup.requestFocus();
                    }
                    else if(email.getText().toString().length() == 0)
                    {
                        email.requestFocus();
                    }
                    else if(mobno.getText().toString().length() == 0)
                    {
                        mobno.requestFocus();
                    }
                    else if (altmobno.getText().toString().length() == 0)
                    {
                        altmobno.requestFocus();
                    }
                    else if(signedat.getText().toString().length() == 0)
                    {
                        signedat.requestFocus();
                    }
                    else if(titlespinner.getSelectedItem().toString().equals("Title"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Title", Toast.LENGTH_SHORT).show();
                    }
                    else if(authorityspinner.getSelectedItem().toString().equals("Authority"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Role Type", Toast.LENGTH_SHORT).show();
                    }
                    else if (warehousespinner.getSelectedItem().toString().equals("Warehouse"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Wareouse", Toast.LENGTH_SHORT).show();
                    }
                    else if (idspinner.getSelectedItem().toString().equals("Id Type"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose ID Type", Toast.LENGTH_SHORT).show();
                    }
                    else if(doctypespinner.getSelectedItem().toString().equals("Choose DOC_TYPE"))
                    {
                        Toast.makeText(SignUpAgent.this, "Select Document Type", Toast.LENGTH_SHORT).show();
                    }
                    else {

                           if(dailyrate1.getText().length() == 0 && simcost1.getText().length() == 0
                                &&activationcom1.getText().length() == 0 &&ogr1.getText().length() == 0
                                &&cib1.getText().length() == 0 && sims1.getText().length() == 0)
                        {
                            dailyrate1.setText("0");
                            simcost1.setText("0");
                            activationcom1.setText("0");
                            ogr1.setText("0");
                            cib1.setText("0");
                            sims1.setText("0");
                        }
                        if(dailyrate2.getText().length() == 0 && simcost2.getText().length() == 0
                                &&activationcom2.getText().length() == 0 &&ogr2.getText().length() == 0
                                &&cib2.getText().length() == 0 && sims2.getText().length() == 0)
                        {
                            dailyrate2.setText("0");
                            simcost2.setText("0");
                            activationcom2.setText("0");
                            ogr2.setText("0");
                            cib2.setText("0");
                            sims2.setText("0");
                        }
                        if(dailyrate3.getText().length() == 0 && simcost3.getText().length() == 0
                                   &&activationcom3.getText().length() == 0 &&ogr3.getText().length() == 0
                                   &&cib3.getText().length() == 0 && sims3.getText().length() == 0)
                           {
                               dailyrate3.setText("0");
                               simcost3.setText("0");
                               activationcom3.setText("0");
                               ogr3.setText("0");
                               cib3.setText("0");
                               sims3.setText("0");
                           }
                        if(dailyrate4.getText().length() == 0 && simcost4.getText().length() == 0
                                   &&activationcom4.getText().length() == 0 &&ogr4.getText().length() == 0
                                   &&cib4.getText().length() == 0 && sims4.getText().length() == 0)
                           {
                               dailyrate4.setText("0");
                               simcost4.setText("0");
                               activationcom4.setText("0");
                               ogr4.setText("0");
                               cib4.setText("0");
                               sims4.setText("0");
                           }
                           if(dailyrate1.getText().length() == 0 || simcost1.getText().length() == 0
                                   ||activationcom1.getText().length() == 0 ||ogr1.getText().length() == 0
                                   ||cib1.getText().length() == 0 || sims1.getText().length() == 0
                                   ||dailyrate2.getText().length() == 0 || simcost2.getText().length() == 0
                                ||activationcom2.getText().length() == 0 ||ogr2.getText().length() == 0
                                ||cib2.getText().length() == 0 || sims2.getText().length() == 0
                                ||dailyrate3.getText().length() == 0 || simcost3.getText().length() == 0
                                ||activationcom3.getText().length() == 0 ||ogr3.getText().length() == 0
                                ||cib3.getText().length() == 0 || sims3.getText().length() == 0
                                ||dailyrate4.getText().length() == 0 || simcost4.getText().length() == 0
                                ||activationcom4.getText().length() == 0 ||ogr4.getText().length() == 0
                                ||cib4.getText().length() == 0 || sims4.getText().length() == 0) {
                               if (dailyrate1.getText().length() == 0) {
                                   dailyrate1.requestFocus();
                               } else if (simcost1.getText().length() == 0) {
                                   simcost1.requestFocus();
                               } else if (activationcom1.getText().length() == 0) {
                                   activationcom1.requestFocus();
                               } else if (ogr1.getText().length() == 0) {
                                   ogr1.requestFocus();
                               } else if (cib1.getText().length() == 0) {
                                   cib1.requestFocus();
                               } else if (sims1.getText().length() == 0) {
                                   sims1.requestFocus();
                               } else if (dailyrate2.getText().length() == 0) {
                                   dailyrate2.requestFocus();
                               } else if (simcost2.getText().length() == 0) {
                                   simcost2.requestFocus();
                               } else if (activationcom2.getText().length() == 0) {
                                   activationcom2.requestFocus();
                               } else if (ogr2.getText().length() == 0) {
                                   ogr2.requestFocus();
                               } else if (cib2.getText().length() == 0) {
                                   cib2.requestFocus();
                               } else if (sims2.getText().length() == 0) {
                                   sims2.requestFocus();
                               } else if (dailyrate3.getText().length() == 0) {
                                   dailyrate3.requestFocus();
                               } else if (simcost3.getText().length() == 0) {
                                   simcost3.requestFocus();
                               } else if (activationcom3.getText().length() == 0) {
                                   activationcom3.requestFocus();
                               } else if (ogr3.getText().length() == 0) {
                                   ogr3.requestFocus();
                               } else if (cib3.getText().length() == 0) {
                                   cib3.requestFocus();
                               } else if (sims3.getText().length() == 0) {
                                   sims3.requestFocus();
                               } else if (dailyrate4.getText().length() == 0) {
                                   dailyrate4.requestFocus();
                               } else if (simcost4.getText().length() == 0) {
                                   simcost4.requestFocus();
                               } else if (activationcom4.getText().length() == 0) {
                                   activationcom4.requestFocus();
                               } else if (ogr4.getText().length() == 0) {
                                   ogr4.requestFocus();
                               } else if (cib4.getText().length() == 0) {
                                   cib4.requestFocus();
                               } else if (sims4.getText().length() == 0) {
                                   sims4.requestFocus();
                               }
                           }
                           else
                           {
                               contractagent(titlespinner.getSelectedItem().toString(),firstname.getText().toString(),
                                       lastname.getText().toString(),username.getText().toString(),
                                       password.getText().toString(),authorityspinner.getSelectedItem().toString(),
                                       statusspinner.getSelectedItem().toString(),warehousespinner.getSelectedItem().toString(),
                                       idspinner.getSelectedItem().toString(),idnum.getText().toString(),
                                       streetno.getText().toString(),streetname.getText().toString(),
                                       suburb.getText().toString(),city.getText().toString(),province.getText().toString(),
                                       postal_code.getText().toString(),ricauser.getText().toString(),
                                       ricapassword.getText().toString(),ricagroup.getText().toString(),
                                       email.getText().toString(),mobno.getText().toString(),altmobno.getText().toString(),
                                       network1.getText().toString(),dailyrate1.getText().toString(),
                                       simcost1.getText().toString(),activationcom1.getText().toString(),
                                       ogr1.getText().toString(),cib1.getText().toString(),sims1.getText().toString(),network2.getText().toString(),
                                       dailyrate2.getText().toString(),simcost2.getText().toString(),activationcom2.getText().toString(),
                                       ogr2.getText().toString(),cib2.getText().toString(),sims2.getText().toString(),network3.getText().toString(),
                                       dailyrate3.getText().toString(),simcost3.getText().toString(),activationcom3.getText().toString(),
                                       ogr3.getText().toString(),cib3.getText().toString(),sims3.getText().toString(),network4.getText().toString(),
                                       dailyrate4.getText().toString(),simcost4.getText().toString(),activationcom4.getText().toString(),
                                       ogr4.getText().toString(),cib1.getText().toString(),sims4.getText().toString(),signedat.getText().toString());
                           }
                    }
            }

                else if(idspinner.getSelectedItem().toString().equals("PASSPORT"))
                {
                    if (firstname.getText().toString().length() == 0)
                    {
                        firstname.requestFocus();
                    }
                    else if(lastname.getText().toString().length() == 0)
                    {
                        lastname.requestFocus();
                    }
                    else if(username.getText().toString().length() == 0)
                    {
                        username.requestFocus();
                    }
                    else if(password.getText().toString().length() == 0)
                    {
                        password.requestFocus();
                    }
                    else if(passport.getText().toString().length() ==0)
                    {
                        passport.requestFocus();
                    }
                    else if(expirydate.getText().toString().length() == 0)
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Expiry Date from Calender", Toast.LENGTH_SHORT).show();
                    }else if (streetno.getText().toString().length() == 0)
                    {
                        streetno.requestFocus();
                    }
                    else if(streetname.getText().toString().length() == 0)
                    {
                        streetname.requestFocus();
                    }
                    else if(suburb.getText().toString().length() == 0)
                    {
                        suburb.requestFocus();
                    }
                    else if(city.getText().toString().length() == 0)
                    {
                        city.requestFocus();
                    }
                    else if(province.getText().toString().length() == 0)
                    {
                        province.requestFocus();
                    }
                    else if(postal_code.getText().toString().length() == 0)
                    {
                        postal_code.requestFocus();
                    }
                    else if(ricauser.getText().toString().length() == 0)
                    {
                        ricauser.requestFocus();
                    }
                    else if (ricapassword.getText().toString().length() == 0)
                    {
                        ricapassword.requestFocus();
                    }
                    else if (ricagroup.getText().toString().length() == 0)
                    {
                        ricagroup.requestFocus();
                    }
                    else if(email.getText().toString().length() == 0)
                    {
                        email.requestFocus();
                    }
                    else if(mobno.getText().toString().length() == 0)
                    {
                        mobno.requestFocus();
                    }
                    else if (altmobno.getText().toString().length() == 0)
                    {
                        altmobno.requestFocus();
                    }
                    else if(signedat.getText().toString().length() == 0)
                    {
                        signedat.requestFocus();
                    }
                    else if(titlespinner.getSelectedItem().toString().equals("Title"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Title", Toast.LENGTH_SHORT).show();
                    }
                    else if(authorityspinner.getSelectedItem().toString().equals("Authority"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Role Type", Toast.LENGTH_SHORT).show();
                    }
                    else if (warehousespinner.getSelectedItem().toString().equals("Warehouse"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose Wareouse", Toast.LENGTH_SHORT).show();
                    }
                    else if (idspinner.getSelectedItem().toString().equals("Id Type"))
                    {
                        Toast.makeText(SignUpAgent.this, "Choose ID Type", Toast.LENGTH_SHORT).show();
                    }
                    else if(doctypespinner.getSelectedItem().toString().equals("Choose DOC_TYPE"))
                    {
                        Toast.makeText(SignUpAgent.this, "Select Document Type", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            if(dailyrate1.getText().length() == 0 && simcost1.getText().length() == 0
                                    &&activationcom1.getText().length() == 0 &&ogr1.getText().length() == 0
                                    &&cib1.getText().length() == 0 && sims1.getText().length() == 0)
                            {
                                dailyrate1.setText("0");
                                simcost1.setText("0");
                                activationcom1.setText("0");
                                ogr1.setText("0");
                                cib1.setText("0");
                                sims1.setText("0");
                            }
                            if(dailyrate2.getText().length() == 0 && simcost2.getText().length() == 0
                                    &&activationcom2.getText().length() == 0 &&ogr2.getText().length() == 0
                                    &&cib2.getText().length() == 0 && sims2.getText().length() == 0)
                            {
                                dailyrate2.setText("0");
                                simcost2.setText("0");
                                activationcom2.setText("0");
                                ogr2.setText("0");
                                cib2.setText("0");
                                sims2.setText("0");
                            }
                            if(dailyrate3.getText().length() == 0 && simcost3.getText().length() == 0
                                    &&activationcom3.getText().length() == 0 &&ogr3.getText().length() == 0
                                    &&cib3.getText().length() == 0 && sims3.getText().length() == 0)
                            {
                                dailyrate3.setText("0");
                                simcost3.setText("0");
                                activationcom3.setText("0");
                                ogr3.setText("0");
                                cib3.setText("0");
                                sims3.setText("0");
                            }
                            if(dailyrate4.getText().length() == 0 && simcost4.getText().length() == 0
                                    &&activationcom4.getText().length() == 0 &&ogr4.getText().length() == 0
                                    &&cib4.getText().length() == 0 && sims4.getText().length() == 0)
                            {
                                dailyrate4.setText("0");
                                simcost4.setText("0");
                                activationcom4.setText("0");
                                ogr4.setText("0");
                                cib4.setText("0");
                                sims4.setText("0");
                            }
                            if(dailyrate1.getText().length() == 0 || simcost1.getText().length() == 0
                                    ||activationcom1.getText().length() == 0 ||ogr1.getText().length() == 0
                                    ||cib1.getText().length() == 0 || sims1.getText().length() == 0
                                    ||dailyrate2.getText().length() == 0 || simcost2.getText().length() == 0
                                    ||activationcom2.getText().length() == 0 ||ogr2.getText().length() == 0
                                    ||cib2.getText().length() == 0 || sims2.getText().length() == 0
                                    ||dailyrate3.getText().length() == 0 || simcost3.getText().length() == 0
                                    ||activationcom3.getText().length() == 0 ||ogr3.getText().length() == 0
                                    ||cib3.getText().length() == 0 || sims3.getText().length() == 0
                                    ||dailyrate4.getText().length() == 0 || simcost4.getText().length() == 0
                                    ||activationcom4.getText().length() == 0 ||ogr4.getText().length() == 0
                                    ||cib4.getText().length() == 0 || sims4.getText().length() == 0) {
                                if (dailyrate1.getText().length() == 0) {
                                    dailyrate1.requestFocus();
                                } else if (simcost1.getText().length() == 0) {
                                    simcost1.requestFocus();
                                } else if (activationcom1.getText().length() == 0) {
                                    activationcom1.requestFocus();
                                } else if (ogr1.getText().length() == 0) {
                                    ogr1.requestFocus();
                                } else if (cib1.getText().length() == 0) {
                                    cib1.requestFocus();
                                } else if (sims1.getText().length() == 0) {
                                    sims1.requestFocus();
                                } else if (dailyrate2.getText().length() == 0) {
                                    dailyrate2.requestFocus();
                                } else if (simcost2.getText().length() == 0) {
                                    simcost2.requestFocus();
                                } else if (activationcom2.getText().length() == 0) {
                                    activationcom2.requestFocus();
                                } else if (ogr2.getText().length() == 0) {
                                    ogr2.requestFocus();
                                } else if (cib2.getText().length() == 0) {
                                    cib2.requestFocus();
                                } else if (sims2.getText().length() == 0) {
                                    sims2.requestFocus();
                                } else if (dailyrate3.getText().length() == 0) {
                                    dailyrate3.requestFocus();
                                } else if (simcost3.getText().length() == 0) {
                                    simcost3.requestFocus();
                                } else if (activationcom3.getText().length() == 0) {
                                    activationcom3.requestFocus();
                                } else if (ogr3.getText().length() == 0) {
                                    ogr3.requestFocus();
                                } else if (cib3.getText().length() == 0) {
                                    cib3.requestFocus();
                                } else if (sims3.getText().length() == 0) {
                                    sims3.requestFocus();
                                } else if (dailyrate4.getText().length() == 0) {
                                    dailyrate4.requestFocus();
                                } else if (simcost4.getText().length() == 0) {
                                    simcost4.requestFocus();
                                } else if (activationcom4.getText().length() == 0) {
                                    activationcom4.requestFocus();
                                } else if (ogr4.getText().length() == 0) {
                                    ogr4.requestFocus();
                                } else if (cib4.getText().length() == 0) {
                                    cib4.requestFocus();
                                } else if (sims4.getText().length() == 0) {
                                    sims4.requestFocus();
                                }
                            }

                            else {
                                contractagentPassport(titlespinner.getSelectedItem().toString(),firstname.getText().toString(),
                                        lastname.getText().toString(),username.getText().toString(),
                                        password.getText().toString(),authorityspinner.getSelectedItem().toString(),
                                        statusspinner.getSelectedItem().toString(),warehousespinner.getSelectedItem().toString(),
                                        idspinner.getSelectedItem().toString(),passport.getText().toString(),expirydate.getText().toString(),
                                        streetno.getText().toString(),streetname.getText().toString(),
                                        suburb.getText().toString(),city.getText().toString(),province.getText().toString(),
                                        postal_code.getText().toString(),ricauser.getText().toString(),
                                        ricapassword.getText().toString(),ricagroup.getText().toString(),
                                        email.getText().toString(),mobno.getText().toString(),altmobno.getText().toString(),
                                        network1.getText().toString(),dailyrate1.getText().toString(),
                                        simcost1.getText().toString(),activationcom1.getText().toString(),
                                        ogr1.getText().toString(),cib1.getText().toString(),sims1.getText().toString(),network2.getText().toString(),
                                        dailyrate2.getText().toString(),simcost2.getText().toString(),activationcom2.getText().toString(),
                                        ogr2.getText().toString(),cib2.getText().toString(),sims2.getText().toString(),network3.getText().toString(),
                                        dailyrate3.getText().toString(),simcost3.getText().toString(),activationcom3.getText().toString(),
                                        ogr3.getText().toString(),cib3.getText().toString(),sims3.getText().toString(),network4.getText().toString(),
                                        dailyrate4.getText().toString(),simcost4.getText().toString(),activationcom4.getText().toString(),
                                        ogr4.getText().toString(),cib1.getText().toString(),sims4.getText().toString(),signedat.getText().toString());
                            }
                        }
                }
        };
    });

        //buttonSign1=findViewById(R.id.signature1);
      //  buttonSign2=findViewById(R.id.signature2);
        buttonSign3=findViewById(R.id.distributorbtn);
     //   buttonSign4=findViewById(R.id.signature3);
     //   buttonSign5=findViewById(R.id.signature4);
        buttonSign6=findViewById(R.id.signaturebtn);
        root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        assetManager = getAssets();

        buttonSign3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: canvas for signature opens");
                count++;
                openDialog();
            }
        });
        buttonSign6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: canvas for signature opens");
                count1++;
                openDialog();
            }
        });
    }

    public void onDelete(View view)
    {
        linearLayout.removeView((View)view.getParent());
    }

    private void openDialog() {

        final AlertDialog.Builder alert =new AlertDialog.Builder(this);
        LayoutInflater layoutInflater=getLayoutInflater();
        View v=layoutInflater.inflate(R.layout.customs_sign_dialog,null);
        alert.setTitle("Signature");
        alert.setView(v);
        final AlertDialog dialog=alert.show();
        btnClear=v.findViewById(R.id.btnClear);
        signatureView = v.findViewById(R.id.signature_view);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();

            }
        });
        btnSave=v.findViewById(R.id.btnSave);
        dialog.setCanceledOnTouchOutside(true);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked save");
                Toast.makeText(SignUpAgent.this, "Signature Captured!", Toast.LENGTH_SHORT).show();
                if(!signatureView.isBitmapEmpty()) {
                    //getting bitmap from signature canvas
                    bitmap = signatureView.getSignatureBitmap();
                    Log.d(TAG, "onClick: bitmap:" + bitmap);
                    /**
                     method to save image and get saved path
                     */
                    path = saveImage(bitmap);

                    sendImageDataToServer();
                    dialog.dismiss();
                }
                    //dialog.dismiss();
            }
        });
    }

    private String saveImage(Bitmap bitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            Toast.makeText(this, "Signature Captured Succesfully", Toast.LENGTH_SHORT).show();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void contractagentPassport(String titlevalue, String firstname, String lastname, String username, String password,
                                       String authorityvalue, String statusvalue, String warehousevalue, String idvalue,
                                       String passport, String expirydate, String streetno, String streetname, String suburb,
                                       String city, String province, String postalcode, String ricauser, String ricapassword,
                                       String ricagroup, String email, String mobno, String altmobno,String network1, String dailyRate1,
                                       String simcost1,String activationcom1, String ogr1,String cib1, String sims1, String network2, String dailyRate2,
                                       String simcost2,String activationcom2, String ogr2,String cib2, String sims2, String network3, String dailyRate3,
                                       String simcost3,String activationcom3, String ogr3,String cib3, String sims3, String network4, String dailyRate4,
                                       String simcost4,String activationcom4, String ogr4,String cib4, String sims4, String signedat) {

        progressBar.show();
        int parentId = Integer.parseInt(Pref.getUserId(this));

        if(status.equals("Enabled"))
        {
            enableid = Boolean.valueOf("true");

        }
        else
        {

            enableid = Boolean.valueOf("false");

        }


        JSONObject profileobject = new JSONObject();
        try {
            profileobject.put("idType",idvalue);
            profileobject.put("passportNo",passport);
            profileobject.put("passportExpiryDate",datestring);
            profileobject.put("ricaUser",ricauser);
            profileobject.put("ricaPassword",ricapassword);
            profileobject.put("ricaGroup",ricagroup);
            profileobject.put("email",email);
            profileobject.put("mobileNo",mobno);
            profileobject.put("altMobileNo",altmobno);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject addressObject = new JSONObject();
        try {
            addressObject.put("streetNo",streetno);
            addressObject.put("streetName",streetname);
            addressObject.put("suburb",suburb);
            addressObject.put("city",city);
            addressObject.put("province",province);
            addressObject.put("postalCode",postalcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject commisionsobject1 = new JSONObject();
        try {
            commisionsobject1.put("network",network1);
            commisionsobject1.put("dailyRate",dailyRate1);
            commisionsobject1.put("simCost",simcost1);
            commisionsobject1.put("activationCom",activationcom1);
            commisionsobject1.put("ogr",ogr1);
            commisionsobject1.put("cib",cib1);
            commisionsobject1.put("sims",sims1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray commisionsArray = new JSONArray();
        commisionsArray.put(commisionsobject1);

        JSONObject commisionsobject2 = new JSONObject();
        try {
            commisionsobject2.put("network",network2);
            commisionsobject2.put("dailyRate",dailyRate2);
            commisionsobject2.put("simCost",simcost2);
            commisionsobject2.put("activationCom",activationcom2);
            commisionsobject2.put("ogr",ogr2);
            commisionsobject2.put("cib",cib2);
            commisionsobject2.put("sims",sims2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject2);

        JSONObject commisionsobject3 = new JSONObject();
        try {
            commisionsobject3.put("network",network3);
            commisionsobject3.put("dailyRate",dailyRate3);
            commisionsobject3.put("simCost",simcost3);
            commisionsobject3.put("activationCom",activationcom3);
            commisionsobject3.put("ogr",ogr3);
            commisionsobject3.put("cib",cib3);
            commisionsobject3.put("sims",sims3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject3);


        JSONObject commisionsobject4 = new JSONObject();
        try {
            commisionsobject4.put("network",network4);
            commisionsobject4.put("dailyRate",dailyRate4);
            commisionsobject4.put("simCost",simcost4);
            commisionsobject4.put("activationCom",activationcom4);
            commisionsobject4.put("ogr",ogr4);
            commisionsobject4.put("cib",cib4);
            commisionsobject4.put("sims",sims4);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject4);


        JSONObject authorityObject = new JSONObject();
        try {
            authorityObject.put("id",authorityId);
            authorityObject.put("name",authorityname);
            authorityObject.put("authority",authority);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("title",titlevalue);
            paramObject.put("firstName",firstname);
            paramObject.put("lastName",lastname);
            paramObject.put("username",username);
            paramObject.put("password",password);
            paramObject.put("enabled",enableid);
            paramObject.put("authority",authorityObject);
            paramObject.put("attachments",attachmentArray);
            paramObject.put("profile",profileobject);
            paramObject.put("address",addressObject);
            paramObject.put("commissions",commisionsArray);
            paramObject.put("parentId",parentId);
            paramObject.put("warehouseId",warehouseId);
            paramObject.put("signedAt",signedat);

            RequestBody contractbody = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<ContractResponse> signUpResponseCall = web_interface.requestContractResponse(contractbody);
            signUpResponseCall.enqueue(new Callback<ContractResponse>() {
                @Override
                public void onResponse(Call<ContractResponse> call, Response<ContractResponse> response) {
                    if(response.isSuccessful() && response.code() == 200) {
                        if (response.body() != null) {
                            String success = response.body().getSuccess().toString();
                            if (success.equals("true")) {
                                progressBar.dismiss();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("id",response.body().getBody().getId());
                                    jsonObject.put("name",response.body().getBody().getName());
                                    jsonObject.put("fileName",response.body().getBody().getFileName());
                                    jsonObject.put("type",response.body().getBody().getType());
                                    attachmentArray2.put(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();

                                progressBar.show();
                                String sttsvalue = "";

                                if(status.equals("Enabled"))
                                {
                                    sttsvalue="true";

                                }
                                else
                                {
                                    sttsvalue="false";
                                }
                                JSONObject profileobject = new JSONObject();
                                try {
                                    profileobject.put("idType",idvalue);
                                    profileobject.put("passportNo",passport);
                                    profileobject.put("passportExpiryDate",datestring);
                                    profileobject.put("ricaUser",ricauser);
                                    profileobject.put("ricaPassword",ricapassword);
                                    profileobject.put("ricaGroup",ricagroup);
                                    profileobject.put("email",email);
                                    profileobject.put("mobileNo",mobno);
                                    profileobject.put("altMobileNo",altmobno);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject addressObject = new JSONObject();
                                try {
                                    addressObject.put("streetNo",streetno);
                                    addressObject.put("streetName",streetname);
                                    addressObject.put("suburb",suburb);
                                    addressObject.put("city",city);
                                    addressObject.put("province",province);
                                    addressObject.put("postalCode",postalcode);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject commisionsobject1 = new JSONObject();
                                try {
                                    commisionsobject1.put("network",network1);
                                    commisionsobject1.put("dailyRate",dailyRate1);
                                    commisionsobject1.put("simCost",simcost1);
                                    commisionsobject1.put("activationCom",activationcom1);
                                    commisionsobject1.put("ogr",ogr1);
                                    commisionsobject1.put("cib",cib1);
                                    commisionsobject1.put("sims",sims1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONArray commisionsArray = new JSONArray();
                                commisionsArray.put(commisionsobject1);

                                JSONObject commisionsobject2 = new JSONObject();
                                try {
                                    commisionsobject2.put("network",network2);
                                    commisionsobject2.put("dailyRate",dailyRate2);
                                    commisionsobject2.put("simCost",simcost2);
                                    commisionsobject2.put("activationCom",activationcom2);
                                    commisionsobject2.put("ogr",ogr2);
                                    commisionsobject2.put("cib",cib2);
                                    commisionsobject2.put("sims",sims2);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject2);

                                JSONObject commisionsobject3 = new JSONObject();
                                try {
                                    commisionsobject3.put("network",network3);
                                    commisionsobject3.put("dailyRate",dailyRate3);
                                    commisionsobject3.put("simCost",simcost3);
                                    commisionsobject3.put("activationCom",activationcom3);
                                    commisionsobject3.put("ogr",ogr3);
                                    commisionsobject3.put("cib",cib3);
                                    commisionsobject3.put("sims",sims3);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject3);


                                JSONObject commisionsobject4 = new JSONObject();
                                try {
                                    commisionsobject4.put("network",network4);
                                    commisionsobject4.put("dailyRate",dailyRate4);
                                    commisionsobject4.put("simCost",simcost4);
                                    commisionsobject4.put("activationCom",activationcom4);
                                    commisionsobject4.put("ogr",ogr4);
                                    commisionsobject4.put("cib",cib4);
                                    commisionsobject4.put("sims",sims4);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject4);


                                JSONObject authorityObject = new JSONObject();
                                try {
                                    authorityObject.put("id",authorityId);
                                    authorityObject.put("name",authorityname);
                                    authorityObject.put("authority",authority);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

/*        JSONObject attachmentObject = new JSONObject();
        try {
            attachmentObject.put("id",imageid);
            attachmentObject.put("name",imagename);
            attachmentObject.put("fileName",imagefilename);
            attachmentObject.put("path",imagepath);
            attachmentObject.put("createdAt",imagecreatedat);
            attachmentObject.put("updatedAt",imageupdatedat);
            attachmentObject.put("type",doctype);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray attachmentArray = new JSONArray();
        attachmentArray.put(attachmentObject);*/

                                JSONArray paymentsArray = new JSONArray();
                                Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
                                JSONObject paramObject = new JSONObject();
                                try {
                                    paramObject.put("enabled",enableid);
                                    paramObject.put("profile",profileobject);
                                    paramObject.put("address",addressObject);
                                    paramObject.put("parentId",parentId);
                                    paramObject.put("title",titlevalue);
                                    paramObject.put("firstName",firstname);
                                    paramObject.put("lastName",lastname);
                                    paramObject.put("username",username);
                                    paramObject.put("password",password);
                                    paramObject.put("authority",authorityObject);
                                    paramObject.put("warehouseId",warehouseId);
                                    paramObject.put("attachments",attachmentArray2);
                                    paramObject.put("paymentAccounts",paymentsArray);
                                    paramObject.put("commissions",commisionsArray);
                                    paramObject.put("signedAt",signedat);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestBody signupBody = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
                                Call<SignUpResponse> signUpResponseCall2 = web_interface1.requestSignUpResponse(signupBody);
                                signUpResponseCall2.enqueue(new Callback<SignUpResponse>() {
                                    @Override
                                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                                        if(response.isSuccessful() && response.code() == 200)
                                        {
                                            String success = response.body().getSuccess().toString();
                                            String message = response.body().getMessage();
                                            if(success.equals("true"))
                                            {
                                                progressBar.dismiss();
                                                        Toasty.success(SignUpAgent.this, message, Toast.LENGTH_SHORT, true).show();
                                                        Intent intent = new Intent(SignUpAgent.this,Stocks_dashboard.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                            }
                                            else
                                            {
                                                progressBar.dismiss();
                                                Toast.makeText(SignUpAgent.this, message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            progressBar.dismiss();
                                            Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                                        progressBar.dismiss();
                                        Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                            else
                            {
                                progressBar.dismiss();
                                Toast.makeText(SignUpAgent.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            progressBar.dismiss();
                            Toast.makeText(SignUpAgent.this, response.code()+" Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        progressBar.dismiss();
                        Toast.makeText(SignUpAgent.this, response.code()+" Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ContractResponse> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getImageFromCamera() {

        new ImagePicker.Builder(SignUpAgent.this)
                .mode(ImagePicker.Mode.CAMERA)
                .compressLevel(ImagePicker.ComperesLevel.HARD)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            filePath=mPaths.get(0);
            if(filePath!=null)
            {
                sendImageDataToServer2();
            }
        }
    }

    private void sendImageDataToServer2() {

        if(filePath!=null)
        {

            Utils.showProgress(this,"Image is uploading, please wait");
            Web_Interface webInterface = Ret.getClient().create(Web_Interface.class);
            List<MultipartBody.Part> parts = new ArrayList<>();
            List<String> files=new ArrayList<>(); //These are the uris for the files to be uploaded
            files.add(filePath);
            MediaType mediaType = MediaType.parse("multipart/form-data");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
            MultipartBody.Part[] fileParts = new MultipartBody.Part[files.size()];
            for (int i = 0; i < files.size(); i++) {
                File file = new File(files.get(i));
                RequestBody fileBody = RequestBody.create(mediaType, file);
                //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
                fileParts[i] =  MultipartBody.Part.createFormData("file",file.getName(),fileBody);
                Log.d("filedata ",file.getName()+" " +fileBody);
            }
            Log.d("file2upload", Arrays.toString(fileParts) +"" +RetrofitToken.token);

            Call<UploadedFile> call=webInterface.requestUpdateProfilePic(fileParts,RetrofitToken.token);
            call.enqueue(new Callback<UploadedFile>() {
                @Override
                public void onResponse(Call<UploadedFile> call, Response<UploadedFile> response) {
                    if(response.isSuccessful() && response.code()==200) {
                        //if code is 200 and response is successfull means the agent is login successfully
                        //now setting flag to true to day started

                        String message1=response.body().getMessage();
                        imageid=response.body().getBody().getId();
                        imagename = response.body().getBody().getName();
                        imagefilename = response.body().getBody().getFilename();
                        imagepath = response.body().getBody().getPath();
                        imagecreatedat = response.body().getBody().getCreatedAt();
                        imageupdatedat = response.body().getBody().getUpdatedAt();

                        JSONObject attachmentObject = new JSONObject();
                        try {
                            attachmentObject.put("id",imageid);
                            attachmentObject.put("name",imagename);
                            attachmentObject.put("fileName",imagefilename);
                            attachmentObject.put("type",doctype);


                            attachmentArray2.put(attachmentObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //now we will send this image id and store name from spinner using retrofit
                        Utils.stopProgress();
                        Toasty.success(getApplicationContext(),message1 ).show();



                    }
                    else {

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.d("AgentLoginActivity",jObjError.getString("message"));
                            String message1=response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message1 +"here", Toast.LENGTH_LONG).show();

                            //stopping progress
                            Utils.stopProgress();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() +"error", Toast.LENGTH_LONG).show();
                            //stopping progress
                            Utils.stopProgress();

                        }
                    }}

                @Override
                public void onFailure(Call<UploadedFile> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if(t instanceof SocketTimeoutException){
                        String message = "Socket Time out. Please try again.";
                        Log.d("Response is: ",message);
                    }

                }
            });


        }
    }

    private void sendImageDataToServer() {

        if(path!=null)
        {

            Utils.showProgress(this,"Image is uploading, please wait");
            Web_Interface webInterface = Ret.getClient().create(Web_Interface.class);
            List<MultipartBody.Part> parts = new ArrayList<>();
            List<String> files=new ArrayList<>(); //These are the uris for the files to be uploaded
            files.add(path);
            MediaType mediaType = MediaType.parse("multipart/form-data");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
            MultipartBody.Part[] fileParts = new MultipartBody.Part[files.size()];
            for (int i = 0; i < files.size(); i++) {
                File file = new File(files.get(i));
                RequestBody fileBody = RequestBody.create(mediaType, file);
                //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
                fileParts[i] =  MultipartBody.Part.createFormData("file",file.getName(),fileBody);
                Log.d("filedata ",file.getName()+" " +fileBody);
            }
            Log.d("file2upload", Arrays.toString(fileParts) +"" +RetrofitToken.token);

            Call<UploadedFile> call=webInterface.requestUpdateProfilePic(fileParts,RetrofitToken.token);
            call.enqueue(new Callback<UploadedFile>() {
                @Override
                public void onResponse(Call<UploadedFile> call, Response<UploadedFile> response) {
                    if(response.isSuccessful() && response.code()==200) {
                        //if code is 200 and response is successfull means the agent is login successfully
                        //now setting flag to true to day started

                        String message1=response.body().getMessage();
                         imageid=response.body().getBody().getId();
                         imagename = response.body().getBody().getName();
                         imagefilename = response.body().getBody().getFilename();
                         imagepath = response.body().getBody().getPath();
                         imagecreatedat = response.body().getBody().getCreatedAt();
                         imageupdatedat = response.body().getBody().getUpdatedAt();

                        JSONObject attachmentObject = new JSONObject();
                        try {
                            attachmentObject.put("id",imageid);
                            attachmentObject.put("name",imagename);
                            attachmentObject.put("fileName",imagefilename);
                            if(count>0)
                            {
                                attachmentObject.put("type","DISTRIBUTOR_SIGNATURE");
                                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                                ImageView myImage = (ImageView) findViewById(R.id.dist_sign_image);
                                myImage.setVisibility(View.VISIBLE);
                                myImage.setImageBitmap(myBitmap);

                                count = 0;
                            }
                            else if(count1 > 0)
                            {
                                attachmentObject.put("type","USER_SIGNATURE");
                                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                                ImageView myImage = (ImageView) findViewById(R.id.user_sign_image);
                                myImage.setVisibility(View.VISIBLE);
                                myImage.setImageBitmap(myBitmap);
                                count1 = 0;
                            }
                            attachmentArray.put(attachmentObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //now we will send this image id and store name from spinner using retrofit
                        Utils.stopProgress();
                        Toasty.success(getApplicationContext(),message1 ).show();



                    }
                    else {

                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.d("AgentLoginActivity",jObjError.getString("message"));
                            String message1=response.body().getMessage();
                            Toast.makeText(getApplicationContext(), message1 +"here", Toast.LENGTH_LONG).show();

                            //stopping progress
                            Utils.stopProgress();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() +"error", Toast.LENGTH_LONG).show();
                            //stopping progress
                            Utils.stopProgress();

                        }
                    }}

                @Override
                public void onFailure(Call<UploadedFile> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    if(t instanceof SocketTimeoutException){
                        String message = "Socket Time out. Please try again.";
                        Log.d("Response is: ",message);
                    }

                }
            });


        }

    }


    private void contractagent(String titlevalue, String firstname, String lastname, String username, String password, String authorityvalue, String statusvalue, String warehousevalue,
                             String idvalue, String idnum, String streetno, String streetname, String suburb, String city, String province, String postalcode,
                             String ricauser, String ricapassword, String ricagroup, String email, String mobno, String altmobno, String network1, String dailyRate1,
                             String simcost1,String activationcom1, String ogr1,String cib1, String sims1, String network2, String dailyRate2,
                             String simcost2,String activationcom2, String ogr2,String cib2, String sims2, String network3, String dailyRate3,
                             String simcost3,String activationcom3, String ogr3,String cib3, String sims3, String network4, String dailyRate4,
                             String simcost4,String activationcom4, String ogr4,String cib4, String sims4, String signedat) {

        progressBar.show();
        int parentId = Integer.parseInt(Pref.getUserId(this));

        if(status.equals("Enabled"))
            {
                statusvalue="true";

            }
        else
            {
                statusvalue="false";
            }
        JSONObject profileobject = new JSONObject();
        try {
            profileobject.put("idType",idvalue);
            profileobject.put("idNo",idnum);
            profileobject.put("ricaUser",ricauser);
            profileobject.put("ricaPassword",ricapassword);
            profileobject.put("ricaGroup",ricagroup);
            profileobject.put("email",email);
            profileobject.put("mobileNo",mobno);
            profileobject.put("altMobileNo",altmobno);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject addressObject = new JSONObject();
        try {
            addressObject.put("streetNo",streetno);
            addressObject.put("streetName",streetname);
            addressObject.put("suburb",suburb);
            addressObject.put("city",city);
            addressObject.put("province",province);
            addressObject.put("postalCode",postalcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject commisionsobject1 = new JSONObject();
        try {
                commisionsobject1.put("network",network1);
                commisionsobject1.put("dailyRate",dailyRate1);
                commisionsobject1.put("simCost",simcost1);
                commisionsobject1.put("activationCom",activationcom1);
                commisionsobject1.put("ogr",ogr1);
                commisionsobject1.put("cib",cib1);
                commisionsobject1.put("sims",sims1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray commisionsArray = new JSONArray();
        commisionsArray.put(commisionsobject1);

        JSONObject commisionsobject2 = new JSONObject();
        try {
            commisionsobject2.put("network",network2);
            commisionsobject2.put("dailyRate",dailyRate2);
            commisionsobject2.put("simCost",simcost2);
            commisionsobject2.put("activationCom",activationcom2);
            commisionsobject2.put("ogr",ogr2);
            commisionsobject2.put("cib",cib2);
            commisionsobject2.put("sims",sims2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject2);

        JSONObject commisionsobject3 = new JSONObject();
        try {
            commisionsobject3.put("network",network3);
            commisionsobject3.put("dailyRate",dailyRate3);
            commisionsobject3.put("simCost",simcost3);
            commisionsobject3.put("activationCom",activationcom3);
            commisionsobject3.put("ogr",ogr3);
            commisionsobject3.put("cib",cib3);
            commisionsobject3.put("sims",sims3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject3);


        JSONObject commisionsobject4 = new JSONObject();
        try {
            commisionsobject4.put("network",network4);
            commisionsobject4.put("dailyRate",dailyRate4);
            commisionsobject4.put("simCost",simcost4);
            commisionsobject4.put("activationCom",activationcom4);
            commisionsobject4.put("ogr",ogr4);
            commisionsobject4.put("cib",cib4);
            commisionsobject4.put("sims",sims4);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        commisionsArray.put(commisionsobject4);


        JSONObject authorityObject = new JSONObject();
        try {
            authorityObject.put("id",authorityId);
            authorityObject.put("name",authorityname);
            authorityObject.put("authority",authority);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        JSONObject paramObject = new JSONObject();
        try {
            paramObject.put("title",titlevalue);
            paramObject.put("firstName",firstname);
            paramObject.put("lastName",lastname);
            paramObject.put("username",username);
            paramObject.put("password",password);
            paramObject.put("enabled",statusvalue);
            paramObject.put("authority",authorityObject);
            paramObject.put("attachments",attachmentArray);
            paramObject.put("profile",profileobject);
            paramObject.put("address",addressObject);
            paramObject.put("commissions",commisionsArray);
            paramObject.put("parentId",parentId);
            paramObject.put("warehouseId",warehouseId);
            paramObject.put("signedAt",signedat);

            //paramObject.put("paymentAccounts",paymentsArray);

            RequestBody signupBody = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
            Call<ContractResponse> signUpResponseCall = web_interface.requestContractResponse(signupBody);
            signUpResponseCall.enqueue(new Callback<ContractResponse>() {
                @Override
                public void onResponse(Call<ContractResponse> call, Response<ContractResponse> response) {
                    if(response.isSuccessful() && response.code() == 200) {
                        if (response.body() != null) {
                            String success = response.body().getSuccess().toString();

                            if (success.equals("true")) {
                                progressBar.dismiss();
                                //String message = response.body().getMessage();
                                //Toast.makeText(SignUpAgent.this, message, Toast.LENGTH_SHORT).show();

                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("id",response.body().getBody().getId());
                                    jsonObject.put("name",response.body().getBody().getName());
                                    jsonObject.put("fileName",response.body().getBody().getFileName());
                                    jsonObject.put("type",response.body().getBody().getType());
                                    attachmentArray2.put(jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();

                                progressBar.show();
                                String sttsvalue = "";

                                if(status.equals("Enabled"))
                                {
                                    sttsvalue="true";

                                }
                                else
                                {
                                    sttsvalue="false";
                                }
                                JSONObject profileobject = new JSONObject();
                                try {
                                    profileobject.put("idType",idvalue);
                                    profileobject.put("idNo",idnum);
                                    profileobject.put("ricaUser",ricauser);
                                    profileobject.put("ricaPassword",ricapassword);
                                    profileobject.put("ricaGroup",ricagroup);
                                    profileobject.put("email",email);
                                    profileobject.put("mobileNo",mobno);
                                    profileobject.put("altMobileNo",altmobno);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject addressObject = new JSONObject();
                                try {
                                    addressObject.put("streetNo",streetno);
                                    addressObject.put("streetName",streetname);
                                    addressObject.put("suburb",suburb);
                                    addressObject.put("city",city);
                                    addressObject.put("province",province);
                                    addressObject.put("postalCode",postalcode);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject commisionsobject1 = new JSONObject();
                                try {
                                    commisionsobject1.put("network",network1);
                                    commisionsobject1.put("dailyRate",dailyRate1);
                                    commisionsobject1.put("simCost",simcost1);
                                    commisionsobject1.put("activationCom",activationcom1);
                                    commisionsobject1.put("ogr",ogr1);
                                    commisionsobject1.put("cib",cib1);
                                    commisionsobject1.put("sims",sims1);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONArray commisionsArray = new JSONArray();
                                commisionsArray.put(commisionsobject1);

                                JSONObject commisionsobject2 = new JSONObject();
                                try {
                                    commisionsobject2.put("network",network2);
                                    commisionsobject2.put("dailyRate",dailyRate2);
                                    commisionsobject2.put("simCost",simcost2);
                                    commisionsobject2.put("activationCom",activationcom2);
                                    commisionsobject2.put("ogr",ogr2);
                                    commisionsobject2.put("cib",cib2);
                                    commisionsobject2.put("sims",sims2);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject2);

                                JSONObject commisionsobject3 = new JSONObject();
                                try {
                                    commisionsobject3.put("network",network3);
                                    commisionsobject3.put("dailyRate",dailyRate3);
                                    commisionsobject3.put("simCost",simcost3);
                                    commisionsobject3.put("activationCom",activationcom3);
                                    commisionsobject3.put("ogr",ogr3);
                                    commisionsobject3.put("cib",cib3);
                                    commisionsobject3.put("sims",sims3);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject3);


                                JSONObject commisionsobject4 = new JSONObject();
                                try {
                                    commisionsobject4.put("network",network4);
                                    commisionsobject4.put("dailyRate",dailyRate4);
                                    commisionsobject4.put("simCost",simcost4);
                                    commisionsobject4.put("activationCom",activationcom4);
                                    commisionsobject4.put("ogr",ogr4);
                                    commisionsobject4.put("cib",cib4);
                                    commisionsobject4.put("sims",sims4);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                commisionsArray.put(commisionsobject4);


                                JSONObject authorityObject = new JSONObject();
                                try {
                                    authorityObject.put("id",authorityId);
                                    authorityObject.put("name",authorityname);
                                    authorityObject.put("authority",authority);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

/*        JSONObject attachmentObject = new JSONObject();
        try {
            attachmentObject.put("id",imageid);
            attachmentObject.put("name",imagename);
            attachmentObject.put("fileName",imagefilename);
            attachmentObject.put("path",imagepath);
            attachmentObject.put("createdAt",imagecreatedat);
            attachmentObject.put("updatedAt",imageupdatedat);
            attachmentObject.put("type",doctype);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray attachmentArray = new JSONArray();
        attachmentArray.put(attachmentObject);*/

                                JSONArray paymentsArray = new JSONArray();
                                Web_Interface web_interface1 = RetrofitToken.getClient().create(Web_Interface.class);
                                JSONObject paramObject = new JSONObject();
                                try {
                                    paramObject.put("enabled",enableid);
                                    paramObject.put("profile",profileobject);
                                    paramObject.put("address",addressObject);
                                    paramObject.put("parentId",parentId);
                                    paramObject.put("title",titlevalue);
                                    paramObject.put("firstName",firstname);
                                    paramObject.put("lastName",lastname);
                                    paramObject.put("username",username);
                                    paramObject.put("password",password);
                                    paramObject.put("authority",authorityObject);
                                    paramObject.put("warehouseId",warehouseId);
                                    paramObject.put("attachments",attachmentArray2);
                                    paramObject.put("paymentAccounts",paymentsArray);
                                    paramObject.put("commissions",commisionsArray);
                                    paramObject.put("signedAt",signedat);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestBody signupBody = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
                                Call<SignUpResponse> signUpResponseCall2 = web_interface1.requestSignUpResponse(signupBody);
                                signUpResponseCall2.enqueue(new Callback<SignUpResponse>() {
                                    @Override
                                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                                        if(response.isSuccessful() && response.code() == 200)
                                        {
                                            String success = response.body().getSuccess().toString();
                                            if(success.equals("true"))
                                            {
                                                progressBar.dismiss();
                                                String message = response.body().getMessage();
                                                Toasty.success(SignUpAgent.this, message, Toast.LENGTH_SHORT, true).show();
                                                Intent intent = new Intent(SignUpAgent.this,Stocks_dashboard.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                progressBar.dismiss();
                                                Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else
                                        {
                                            progressBar.dismiss();
                                            Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                                        progressBar.dismiss();
                                        Toast.makeText(SignUpAgent.this, response.code(), Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                            else
                            {
                                progressBar.dismiss();
                                Toast.makeText(SignUpAgent.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            progressBar.dismiss();
                            Toast.makeText(SignUpAgent.this, response.code()+" Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        progressBar.dismiss();
                        Toast.makeText(SignUpAgent.this, response.code()+" Error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ContractResponse> call, Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void warehouseAPICall() {

        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<WarehouseResponse> warehouseResponseCall = web_interface.requestWarehouseResponse();
        warehouseResponseCall.enqueue(new Callback<WarehouseResponse>() {
            @Override
            public void onResponse(Call<WarehouseResponse> call, Response<WarehouseResponse> response) {
                if(response.isSuccessful() && response.code() == 200)
                {
                    List<WarehouseBody> warehouseBodyList = new ArrayList<>();
                    List<String> strings = new ArrayList<>();
                    if(response.body() != null)
                    {
                        warehouseBodyList = response.body().getBody();
                        for(int i=0; i<warehouseBodyList.size();i++)
                        {
                            strings.add(warehouseBodyList.get(i).getName());
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(SignUpAgent.this,android.R.layout.simple_spinner_dropdown_item,
                            strings);
                    warehousespinner.setAdapter(adapter);
                }
                else{
                    Log.d("onResponse: ","ERROR");
                    Toast.makeText(SignUpAgent.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WarehouseResponse> call, Throwable t) {
                Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void authorityAPICall() {
        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
        Call<ResponseAuthority> responseAuthorityCall = web_interface.requestResponseAuthority();
        responseAuthorityCall.enqueue(new Callback<ResponseAuthority>() {
            @Override
            public void onResponse(Call<ResponseAuthority> call, Response<ResponseAuthority> response) {
                if(response.isSuccessful() && response.code() == 200)
                {
                    List<Body> bodyList = new ArrayList<>();
                    List<String> strings = new ArrayList<>();
                    if (response.body() != null) {
                        bodyList = response.body().getBody();
                        for(int i = 0;i<bodyList.size();i++)
                        {
                            strings.add(bodyList.get(i).getAuthority());
                        }

                    }

                    ArrayAdapter adapter = new ArrayAdapter(SignUpAgent.this,android.R.layout.simple_spinner_dropdown_item,
                            strings);
                    authorityspinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseAuthority> call, Throwable t) {
                Toast.makeText(SignUpAgent.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
