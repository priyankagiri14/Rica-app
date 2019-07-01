package com.ubits.payflow.payflow_network.General;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;


public class RicaAgentRegisterActivty extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView backButton, btnDatePicker, ivPassportDoc, ivIdDoc, ivProofDoc;
    private TextView tvPassport, tvResidence, tvDocuments, tvIdDocPath, tvRecidencePath;
    private CheckBox cbVodakom, ccbMtn, cbCellc, cbtelkom;
    private Spinner spIdType, spCountry, spSunAgent, spSelectPostalCode;
    private EditText etCountryCode, etAreaCode, etDialingNo, etIdNo, etPassportExpiredate, etFirstName,
            etSurname, etPhoneNumber, etAddress,
            etSuburb, etpostalcode, etCity;
    private Button btnMainAgent;
    private String IdentificationType, Spinnercountry, subAgent, proofOfAddress;

    private String filePath = "";
    private String picturePathPassport = "", picturePathId = "", picturePathResidence = "";

    private int mYear, mMonth, mDay;
    private String imgString = "", passportImageName = "";
    private ImagePicker imagePicker = new ImagePicker();
    private Bitmap selectedImage;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rica_agent_register_activty);

        initView();
    }

    /**
     * initialize variable and listeners
     */
    private void initView() {

        dialog = new ProgressDialog(this);
        backButton = (ImageView) findViewById(R.id.back_btn);


        btnMainAgent = (Button) findViewById(R.id.btnMainAgent);
        btnDatePicker = (ImageView) findViewById(R.id.img_datePicker);
        tvPassport = (TextView) findViewById(R.id.tv_passport);
        tvDocuments = (TextView) findViewById(R.id.tv_documents);
        tvResidence = (TextView) findViewById(R.id.tv_residence);

        tvRecidencePath = (TextView) findViewById(R.id.tv_path_residence);
        tvIdDocPath = (TextView) findViewById(R.id.tv_id_doc);

        cbVodakom = (CheckBox) findViewById(R.id.cb_vodacom);
        ccbMtn = (CheckBox) findViewById(R.id.cb_mtn);
        cbCellc = (CheckBox) findViewById(R.id.cb_cellc);
        cbtelkom = (CheckBox) findViewById(R.id.cb_telkom);

        spIdType = (Spinner) findViewById(R.id.sp_id_type);
        spCountry = (Spinner) findViewById(R.id.sp_country);
        spSunAgent = (Spinner) findViewById(R.id.sp_sub_agent);
        spSelectPostalCode = (Spinner) findViewById(R.id.sp_select_postalCode);

        etCountryCode = (EditText) findViewById(R.id.et_country_code);
        etAreaCode = (EditText) findViewById(R.id.et_areaCode);
        etDialingNo = (EditText) findViewById(R.id.et_dialing_no);
        etFirstName = (EditText) findViewById(R.id.et_first_name);
        etSurname = (EditText) findViewById(R.id.et_surname);
        etPhoneNumber = (EditText) findViewById(R.id.et_PhoneNumber);

        etIdNo = (EditText) findViewById(R.id.et_id_no);
        etCity = (EditText) findViewById(R.id.et_city);
        etSuburb = (EditText) findViewById(R.id.et_suburb);
        etpostalcode = (EditText) findViewById(R.id.et_postalcode);
        etPassportExpiredate = (EditText) findViewById(R.id.et_passport_ex);

        etAddress = (EditText) findViewById(R.id.etAddress);
        ivPassportDoc = (ImageView) findViewById(R.id.img_passport_doc);
        ivIdDoc = (ImageView) findViewById(R.id.img_id_doc);
        ivProofDoc = (ImageView) findViewById(R.id.img_proof_residence);

        backButton.setOnClickListener(this);
        tvPassport.setOnClickListener(this);
        tvDocuments.setOnClickListener(this);
        tvResidence.setOnClickListener(this);
        btnMainAgent.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        spIdType.setOnItemSelectedListener(this);
        spCountry.setOnItemSelectedListener(this);
        spSunAgent.setOnItemSelectedListener(this);
        spSelectPostalCode.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.sp_id_type) {
            String idType = spIdType.getSelectedItem().toString();
            if (idType.equals(getResources().getString(R.string.business)))
                IdentificationType = "B";
            else if (idType.equals(getResources().getString(R.string.id_Number)))
                IdentificationType = "N";
            else if (idType.equals(getResources().getString(R.string.passport)))
                IdentificationType = "P";
        } else if (spinner.getId() == R.id.sp_country) {
            Spinnercountry = spCountry.getSelectedItem().toString();
            //do this
        } else if (spinner.getId() == R.id.sp_sub_agent)

        {
            subAgent = spSunAgent.getSelectedItem().toString();

        } else if (spinner.getId() == R.id.sp_select_postalCode)

        {
            proofOfAddress = spSelectPostalCode.getSelectedItem().toString();
        }
    }

    /*
     * Click Listener of VIew
     * */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.back_btn) {
            finish();
        } else if (v.getId() == R.id.img_datePicker) {
            final Calendar c = Calendar.getInstance();

            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            showDate();
        } else if (v.getId() == R.id.tv_passport) {
            showFileChoosser();
            filePath = "1";

        } else if (v.getId() == R.id.tv_documents) {
            showFileChoosser();
            filePath = "2";
        } else if (v.getId() == R.id.tv_residence) {
            showFileChoosser();
            filePath = "3";
        } else if (v.getId() == R.id.btnMainAgent) {
            if (Utils.isOnline(RicaAgentRegisterActivty.this)) {
                if (etCountryCode.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill country code");
                    return;

                } else if (etAreaCode.getText().toString().length() == 0) {

                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill area code");
                    return;

                } else if (etDialingNo.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Dialing number");
                    return;
                } else if (etIdNo.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Id number");
                    return;
                } else if (etFirstName.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill First name");
                    return;
                } else if (etSurname.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Surname name");
                    return;
                } else if (etPhoneNumber.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Phone number");
                    return;
                } else if (etSuburb.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Suburb");
                    return;
                } else if (etCity.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill City");
                    return;
                } else if (etpostalcode.getText().toString().length() == 0) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "fill Postal code");
                    return;
                } else if (picturePathResidence == null) {
                    Utils.showToasMessage(RicaAgentRegisterActivty.this, "Attach Residence document");
                    return;
                } else {

                    sendData(IdentificationType, Spinnercountry, subAgent.equals("Yes") ? "true" : "false",
                            etCountryCode.getText().toString(), etAreaCode.getText().toString(),
                            etDialingNo.getText().toString(),
                            etIdNo.getText().toString(), etPassportExpiredate.getText().toString(),
                            etFirstName.getText().toString(), etSurname.getText().toString(),
                            etPhoneNumber.getText().toString(), etAddress.getText().toString(), etSuburb.getText().toString(),
                            etpostalcode.getText().toString(), proofOfAddress.equals("Yes") ? "true" : "false", passportImageName,
                            imgString, imgString, imgString);
                }

            } else {
                Utils.showToasMessage(RicaAgentRegisterActivty.this, "Please Check network connection");
            }

        }
    }

    /**
     * file choose from gallery,camera etc..
     */
    private void showFileChoosser() {

        imagePicker.withActivity(this)
                .chooseFromGallery(true)
                .chooseFromCamera(true)
                .withCompression(true)
                .start();

    }

    // Show Date when user Select
    private void showDate() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        etPassportExpiredate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /**
     * send data to server
     */
    private void sendData(String identificationType, String spinnercountry, String subAgent,
                          String str_countryCode, String str_areaCode, String str_dailingcode, String edIdNo, String SetPassportExpiredate,
                          String SfirstName, String SetSurname,
                          String StxtPhoneNumber, String SetAddress,
                          String SetSuburb, String Stxtpostalcode, String proof_OfAddress, String passportImageName,
                          String valuePassport, String valueId, String valueResidance) {

        dialog = ProgressDialog.show(RicaAgentRegisterActivty.this, "", "Please wait...");
        HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        final HttpClient client = new DefaultHttpClient(params);
        File file = new File(picturePathResidence);
        File file1 = new File(picturePathId);
        File file2 = new File(picturePathPassport);

        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
                    .create()
                    .addTextBody("network", "Vodacom")
                    .addTextBody("groupName", "60WPQ")
                    .addTextBody("operatorID", "FLHEL")
                    .addTextBody("mayRegisterChildAgents", "Vodacom")
                    .addTextBody("networksToDoRegistrations1", "Vodacom")
                    .addTextBody("MSISDN", StxtPhoneNumber)
                    .addTextBody("firstName", SfirstName)
                    .addTextBody("lastName", SetSurname)
                    .addTextBody("idinfoCountryCode", "ZA")
                    .addTextBody("idNumber", edIdNo)
                    .addTextBody("idType", identificationType)
                    .addTextBody("contactCountryCode", str_countryCode)
                    .addTextBody("areaCode", str_areaCode)
                    .addTextBody("dialingNo", str_dailingcode)
                    .addTextBody("individualAddress1", SetAddress)
                    .addTextBody("individualCountryCode", "ZA")
                    .addTextBody("individualPostalCode", Stxtpostalcode)
                    .addTextBody("individualRegion", SetSuburb)
                    .addTextBody("individualSuburb", SetSuburb)
                    .addTextBody("individualCity", etCity.getText().toString())
                    .addTextBody("proofOfAddress", proof_OfAddress);

            if (file.exists()) {
                multipartEntityBuilder.addBinaryBody("ProofOfResidenceFileContent", file, ContentType.DEFAULT_BINARY, file.getName());
            }
            if (file1.exists()) {
                multipartEntityBuilder.addBinaryBody("IdFileContent", file1, ContentType.DEFAULT_BINARY, file1.getName());
            }
            if (file2.exists()) {
                multipartEntityBuilder.addBinaryBody("PassportDocumentFileContent", file2, ContentType.DEFAULT_BINARY, file2.getName());
            }

            HttpEntity entity = multipartEntityBuilder.build();
            final HttpPost httpPost = new HttpPost(Config.AGENT_REGISTER_URL);
            httpPost.setEntity(entity);

            new AsyncTask<Void, Void, Void>() {
                private String body = null;

                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        HttpResponse response = client.execute(httpPost);
                        HttpEntity r_entity = response.getEntity();
                        body = EntityUtils.toString(r_entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String code = jsonObject.optString(("status"));
                        String messagedetails = jsonObject.optString(("messageDetails"));

                        if (code.equals("Failure") || code.equals("RetryLater")) {
                            String message = jsonObject.optString("message");
                            AlertDialog.Builder builder = new AlertDialog.Builder(RicaAgentRegisterActivty.this);
                            builder.setMessage(message);
                            builder.setPositiveButton("OK", null);
                            builder.setTitle("Error!");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            etFirstName.setText("");
                            etSurname.setText("");
                            etPhoneNumber.setText("");
                            etIdNo.setText("");
                            etCountryCode.setText("");
                            etAreaCode.setText("");
                            etDialingNo.setText("");
                            etAddress.setText("");
                            etSuburb.setText("");
                            etCity.setText("");
                            etpostalcode.setText("");
                            ivPassportDoc.setImageBitmap(null);
                            ivPassportDoc.setVisibility(View.GONE);
                            ivIdDoc.setImageBitmap(null);
                            ivIdDoc.setVisibility(View.GONE);
                            ivProofDoc.setImageBitmap(null);
                            ivProofDoc.setVisibility(View.GONE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(RicaAgentRegisterActivty.this);
                            builder.setMessage(messagedetails);
                            builder.setPositiveButton("OK", null);
                            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.setTitle("Success");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(RicaAgentRegisterActivty.this, "Error with your registration, please try again later ", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            }.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true
            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {
                    //convert to bitmap easily
                    dialog.setMessage("Please wait....");
                    dialog.show();
                }

                @Override
                public void onCompressed(String path) {
                    dialog.dismiss();
                    selectedImage = BitmapFactory.decodeFile(path);
                    if (filePath.equals("1")) {
                        dialog.dismiss();
                        ivPassportDoc.setVisibility(View.VISIBLE);
                        ivPassportDoc.setImageBitmap(selectedImage);
                        System.out.println("passport path==>" + path);
                        picturePathPassport = path;
                    } else if (filePath.equals("2")) {
                        dialog.dismiss();
                        ivIdDoc.setVisibility(View.VISIBLE);
                        ivIdDoc.setImageBitmap(selectedImage);
                        System.out.println("passport path==>" + path);
                        picturePathId = path;
                    } else if (filePath.equals("3")) {
                        dialog.dismiss();
                        ivProofDoc.setVisibility(View.VISIBLE);
                        ivProofDoc.setImageBitmap(selectedImage);
                        System.out.println("passport path==>" + path);
                        picturePathResidence = path;
                    }

                }
            });
            imagePicker.getImageFilePath(data);
        }
    }
}
