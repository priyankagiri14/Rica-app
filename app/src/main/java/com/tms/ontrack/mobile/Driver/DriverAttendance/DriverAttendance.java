package com.tms.ontrack.mobile.Driver.DriverAttendance;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Places;
import com.google.gson.JsonArray;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto.GetDriverAttendanceResponse;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto.UploadedFile;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.get_Agent.Agentattendance_Adapter;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.get_Agent.Body;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.get_Agent.FetchAgent;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.Attachment;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.Attendance;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.TeamAttendanceResponse_MyPojo;
import com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance.Team_Attendance_Response;
import com.tms.ontrack.mobile.Driver.Driver_Dashboard.Stocks_dashboard;

import com.tms.ontrack.mobile.R;
import com.tms.ontrack.mobile.Web_Services.Ret;
import com.tms.ontrack.mobile.Web_Services.RetrofitToken;
import com.tms.ontrack.mobile.Web_Services.Web_Interface;
import com.tms.ontrack.mobile.utils.Utils;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import es.dmoral.toasty.Toasty;
import fr.arnaudguyon.perm.Perm;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import info.androidhive.fontawesome.FontTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverAttendance  extends AppCompatActivity  implements View.OnClickListener, LocationListener {
private static final String TAG="HomeFragment";
private int current_store_pos;
private String filePath;
Button buttonStartDay;
private TextView location1,locationtext;
FontTextView buttonCapture;


Spinner spinner_stores;
private String message,message1,status="PRESENT",userid;
private String id;
private String store_name;
private List<String> list;
private List<Integer> agentid;
private List<String> storeId;
private String timeNow;
private File destination = null;
private InputStream inputStreamImg;
private String imgPath = null;
private final int PICK_IMAGE_CAMERA = 1;
private String current_date_time;
private Double Lat, Longs;
//private Double latitude=0.00, longitude=0.00;
private String agentId;
List<String> agentlist;
ListView agentlistview;
private int imageid;
List<FetchAgent> list1;
private List<Attendance> attendance = new ArrayList<>();
Attendance attendance1 = new Attendance();
Attachment attachment1 = new Attachment();
private List<Attachment> attachment = new ArrayList<>();
List<Body> agent =new ArrayList<>();
ArrayList<String> bodyArrayListId = new ArrayList<String>();
JSONObject jsonObject;
    Location location;
    TextView datetext,timetext,noagents;
    double longitude;
    double savelatitude,savelongitude;
    ProgressDialog progressBar,progressBar1;

    double latitude;
    protected LocationManager locationManager;
    private String provider_info;
    Perm perm;
    AlertDialog alert;
    int count=0;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSIONS[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.CAMERA};
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.driverattendance_listview);

    progressBar = new ProgressDialog(this);
    progressBar.setCancelable(false);
    progressBar.setMessage("Please Wait... We are Fetching Your Location");
    progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressBar.show();
    agentlistview = findViewById(R.id.Agent_listview);
    buttonStartDay = findViewById(R.id.driver_button);
    buttonStartDay.setOnClickListener(this);
    buttonCapture = findViewById(R.id.camera);
    buttonCapture.setOnClickListener(this);
    locationtext = findViewById(R.id.locationtext);
    locationtext.setVisibility(View.VISIBLE);
    location1 = findViewById(R.id.location);
    location1.setVisibility(View.INVISIBLE);
    datetext = findViewById(R.id.Date);
    timetext = findViewById(R.id.Time);
    noagents = findViewById(R.id.noagents);
    buttonCapture.setVisibility(View.INVISIBLE);
    buttonStartDay.setVisibility(View.INVISIBLE);
    getLocation();
    initDateTime();
    initLocation();

}

    @Override
    protected void onResume() {
        super.onResume();
        //getLocation();
        progressBar.dismiss();
    }
    //    @Override
//    protected void onRestart() {
//        super.onRestart();
//        progressBar.show();
//    }

    private void initDateTime() {
        Calendar calendar = Calendar.getInstance();
//date format is:  "Date-Month-Year Hour:Minutes am/pm"
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy"); //Date and time
        String currentDate = sdf.format(calendar.getTime());
        //getting current time
        SimpleDateFormat sds = new SimpleDateFormat(" hh:mm a"); //time
        String currentTime = sds.format(calendar.getTime());


//Day of Name in full form like,"Saturday", or if you need the first three characters you have to put "EEE" in the date format and your result will be "Sat".
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        datetext.setText(currentDate);
        timetext.setText(currentTime);

    }

    private void initLocation () {
        progressBar.show();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000);

        perm = new Perm(this, PERMISSIONS);
        if (perm.areGranted()) {
            //   Toast.makeText(this, "All Permissions granted", Toast.LENGTH_LONG).show();
        } else {
            perm.askPermissions(PERMISSIONS_REQUEST);
            progressBar.dismiss();
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            progressBar.dismiss();
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
       // getLocation();
        fetchagent();
    }



    void getLocation() {
        try {
            progressBar.show();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
            progressBar.dismiss();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        location1.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude()  );
        locationtext.setVisibility(View.GONE);
        location1.setVisibility(View.VISIBLE);
        savelongitude= location.getLongitude();
        savelatitude= location.getLatitude();
        //Toast.makeText(this,"Location"+savelatitude +" "+savelongitude,Toast.LENGTH_SHORT).show();


        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            location1.setText(location1.getText()+ "\n"+addresses.get(0).getAddressLine(0));
            }catch(Exception e)
        {
            progressBar.dismiss();
            Toast.makeText(this, "" +e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if(!((Activity) DriverAttendance.this).isFinishing())
        {
            //show dialog
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Open GPS Settings?");
            alertDialog.setCancelable(false);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if(Settings.ACTION_LOCATION_SOURCE_SETTINGS.equals(true))
                    {
                        alertDialog.dismiss();
                    }
                    count++;
                }
            });
            if(count>0)
            {
                alertDialog.dismiss();
                count = 0;
            }
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(DriverAttendance.this, Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });
        alertDialog.show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Enable Location")
//                    .setMessage("Open GPS Settings")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
//                    .setNegativeButton("Cancel",
//                            (dialog, id) -> startActivity(new Intent(DriverAttendance.this,Stocks_dashboard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
//            alert = builder.create();
//            alert.show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }




    private void fetchagent() {
                progressBar.show();
                Web_Interface webInterface = RetrofitToken.getClient().create(Web_Interface.class);
                Call<FetchAgent> call = webInterface.requestfetchagent(RetrofitToken.token);
                call.enqueue(new Callback<FetchAgent>() {
                        @Override
                        public void onResponse(Call<FetchAgent> call, Response<FetchAgent> response) {
                                if (response.isSuccessful() && response.code() == 200) {



                                        assert response.body() != null;
                                        agent=response.body().getBody();
                                        list1=new ArrayList<>();
                                        list = new ArrayList<>();
                                        agentid=new ArrayList<>();
                                        for (int i = 0; i < agent.size(); i++) {
                                              list1.add(response.body());
                                              agentid.add(agent.get(i).getId());
                                              list.add(agent.get(i).getName());
                                              progressBar.dismiss();
                                        }

                                        Log.d("agentname",list.toString() +"\n" +"agentid" +agentid.toString());
                                    populateListView(list1);
                                    if(agentlistview.getCount() == 0)
                                    {
                                        noagents.setVisibility(View.VISIBLE);
//                                        buttonCapture.setVisibility(View.INVISIBLE);
//                                        buttonStartDay.setVisibility(View.INVISIBLE);
//                                        progressBar.dismiss();
                                    }
                                    else if(agentlistview.getCount()>0)
                                    {
                                        buttonCapture.setVisibility(View.VISIBLE);
                                        buttonStartDay.setVisibility(View.VISIBLE);
                                    }
                                }
                                else {

                                        try {
                                                JSONObject jObjError = new JSONObject(response.errorBody().toString());
                                                Log.d("AgentLoginActivity", jObjError.getString("message"));
                                                Toasty.info(getApplicationContext(), message +"cameraa try").show();

                                        } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage() +"camerahere catch", Toast.LENGTH_LONG).show();
                                                //stopping progress
                                        }
                                }
                        }
                        @Override
                        public void onFailure(Call<FetchAgent> call, Throwable t) {
                                Log.d(TAG,t.getLocalizedMessage());
                                Toasty.info(getApplicationContext(),t.getLocalizedMessage() +"camerahere").show();

                        }
                });

        }



        private void populateListView(List<FetchAgent> list) {

                Agentattendance_Adapter agentattendance_adapter=new Agentattendance_Adapter(this,list);
                agentlistview.setAdapter(agentattendance_adapter);
                agentattendance_adapter.notifyDataSetChanged();
        }








        /*--------- getting stores list from api -----------*/



@Override
public void onClick(View v) {

    if (perm.areGranted()) {
        //   Toast.makeText(this, "All Permissions granted", Toast.LENGTH_LONG).show();
        if (v.getId() == R.id.driver_button) {
            if (location1.getText().toString().length() == 0) {
                getLocation();
            } else {
                if (filePath == null) {

                    Toasty.warning(getApplicationContext(), "please capture the image").show();
                } else {

                    int count = 0;
                    TeamAttendanceResponse_MyPojo myPojo = new TeamAttendanceResponse_MyPojo();
                    Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);

                    for (int j = 0; j < agent.size(); j++) {

                        if (agent.get(j).isIschecked()) {
                            String status1 = "PRESENT";
                            int userId = agent.get(j).getId();
                            attendance1.setStatus(status1);
                            attendance1.setUserId(userId);
                            attendance.add(attendance1);
                            myPojo.setAttendance(attendance);
//                          Log.d("onClick1: ",myPojo.getAttendance().get(i).getUserId().toString());
//                          Log.d("onClick2: ",myPojo.getAttendance().get(i).getStatus());
                        }
                        //Log.d("onClick: ",bodyArrayListId.toString());
                        //batches[j] = bodyArrayList1.get(j).getBatchNo();
                    }
                    attachment1.setId(imageid);
                    attachment.add(attachment1);
                    myPojo.setAttachments(attachment);

                    myPojo.setLat(savelatitude);
                    myPojo.setLng(savelongitude);

                    Call<Team_Attendance_Response> team_attendance_responseCall = web_interface.requestTeamAttendance(myPojo);
                    team_attendance_responseCall.enqueue(new Callback<Team_Attendance_Response>() {
                        @Override
                        public void onResponse(Call<Team_Attendance_Response> call, Response<Team_Attendance_Response> response) {

                            Boolean success = response.body().getSuccess();
                            Log.d("TeamResponse: ", success.toString());
                            String message = response.body().getMessage();
                            if (success.equals(false)) {
                                Toast.makeText(DriverAttendance.this, message, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(DriverAttendance.this, message, Toast.LENGTH_LONG).show();
                                filePath=null;
                                Intent intent = new Intent(DriverAttendance.this, Stocks_dashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Team_Attendance_Response> call, Throwable t) {
                            Toast.makeText(DriverAttendance.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Toasty.success(getApplicationContext(),"Attendance marked successfully").show();

                }
            }

        } else if (v.getId() == R.id.camera) {


            getImageFromCamera();

        }

    } else {
        perm.askPermissions(PERMISSIONS_REQUEST);
        progressBar.dismiss();
    }

    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        progressBar.dismiss();
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

    }
}

        private void markattendance(String userid, Double latitude, Double longitude,String status, JSONObject imageid) {
                Web_Interface webInterface = Ret.getClient().create(Web_Interface.class);
                try {
                    JsonArray jsonArray=new JsonArray();
                        JSONObject paramObject = new JSONObject();
                        paramObject.put("userId", userid);
                        paramObject.put("lat", latitude);
                        paramObject.put("lon", longitude);
                        paramObject.put("status", status);
                        paramObject.put("attachments",imageid);
                        Log.d("uploadeimage data",userid+"\n"+latitude +"\n" +longitude+"\n"+status+"\n"+jsonObject);
                        RequestBody body = RequestBody.create(MediaType.parse("application/json"),(paramObject).toString());
                        Call<GetDriverAttendanceResponse> call= webInterface.markAgentattendance(body,RetrofitToken.token);
                        //exeuting the service
                        call.enqueue(new Callback<GetDriverAttendanceResponse>() {
                                @Override
                                public void onResponse(Call<GetDriverAttendanceResponse> call, Response<GetDriverAttendanceResponse> response) {
                                        if(response.isSuccessful()||response.code()==200){
                                                String message=response.body().getMessage();
                                                Toasty.success(getApplicationContext(),message).show();
                                                Log.d("uploadeimage data",userid+"\n"+latitude +"\n" +longitude+"\n"+status+"\n"+jsonObject);
                                        }
                                        else{
                                                try {
                                                        JSONObject jObjError = new JSONObject(response.errorBody().toString());
                                                        Log.d("AgentLoginActivity", jObjError.getString("message"));
                                                        Toasty.info(getApplicationContext(), message).show();

                                                } catch (Exception e) {
                                                        Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show();
                                                        //stopping progress
                                                }
                                        }
                                }

                                @Override
                                public void onFailure(Call<GetDriverAttendanceResponse> call, Throwable t) {
                                        Log.d(TAG,t.getLocalizedMessage());
                                        Toasty.info(getApplicationContext(),t.getLocalizedMessage()).show();
                                }
                        });

                } catch (JSONException e) {
                        e.printStackTrace();
                }

}

/*--------- uploading image to server-----------*/


private void sendImageDataToServer() {
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

        message1=response.body().getMessage();
                imageid=response.body().getBody().getId();

        //now we will send this image id and store name from spinner using retrofit
        Utils.stopProgress();
        Toasty.success(getApplicationContext(),message1 ).show();

        progressBar.cancel();



        }
        else {

        try {
        JSONObject jObjError = new JSONObject(response.errorBody().string());
        Log.d("AgentLoginActivity",jObjError.getString("message"));
        message1=response.body().getMessage();
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
        message = "Socket Time out. Please try again.";
        Log.d(TAG,message);
        }

        }
        });


        }

        }

/*--------- getting image from camera -----------*/


private void getImageFromCamera() {
       /* Log.d(TAG,"getting image from camera");
        Intent intent = new Intent(getApplicationContext(), ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, false);//default is true
        startActivityForResult(intent, 1213);*/
    new ImagePicker.Builder(DriverAttendance.this)
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
                sendImageDataToServer();
            }
        }
     /*   if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Log.d(TAG,"file path: "+filePath);
            try {
                Uri selectedImage = data.getData();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                if (bitmap != null) {
                    Log.d("AmanPic: ",bitmap.toString());
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,bytes);
                }

                Log.e(TAG, "Pick from Camera::>>> ");

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                boolean isDirectoryCreated= destination.mkdirs();

                try {
                    if (!isDirectoryCreated) {
                        isDirectoryCreated= destination.mkdirs();
                    }
                    if(isDirectoryCreated) {
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();}
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                filePath=imgPath;
                Log.d(TAG,"file Path is: "+filePath);
                Toast.makeText(this, "Image captured Successfully", Toast.LENGTH_SHORT).show();
                //                Toasty.success(getApplicationContext(),"Image captured Successfully").show();

            }
            catch (Exception e) {
                e.printStackTrace();
            }*/
           // Toasty.success(getApplicationContext(),"Image captured Successfully").show();

        }
      //  inputStreamImg = null;
//        if (requestCode == PICK_IMAGE_CAMERA) {
////            try {
////                Uri selectedImage = data.getData();
////                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
////                if (bitmap != null) {
////                    Log.d("AmanPic: ",bitmap.toString());
////                    bitmap.compress(Bitmap.CompressFormat.JPEG,50,bytes);
////                }
////
////                Log.e(TAG, "Pick from Camera::>>> ");
////
////                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
////                destination = new File(Environment.getExternalStorageDirectory() + "/" +
////                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
////                FileOutputStream fo;
////                boolean isDirectoryCreated= destination.mkdirs();
////
////                try {
////                    if (!isDirectoryCreated) {
////                        isDirectoryCreated= destination.mkdirs();
////                    }
////                    if(isDirectoryCreated) {
////                        fo = new FileOutputStream(destination);
////                        fo.write(bytes.toByteArray());
////                        fo.close();}
////                }
////                catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                }
////                catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////                imgPath = destination.getAbsolutePath();
////                filePath=imgPath;
////                Log.d(TAG,"file Path is: "+filePath);
////                Toast.makeText(this, "Image captured Successfully", Toast.LENGTH_SHORT).show();
////                //                Toasty.success(getApplicationContext(),"Image captured Successfully").show();
////
////            }
////            catch (Exception e) {
////                e.printStackTrace();
////            }
        }



/*--------- start day response -----------*/








