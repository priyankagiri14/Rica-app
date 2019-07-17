package com.ubits.payflow.payflow_network.Driver.DriverAttendance;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.driverattendancephoto.GetDriverAttendanceResponse;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.driverattendancephoto.UploadedFile;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.Agentattendance_Adapter;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.Body;
import com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_Agent.FetchAgent;
import com.ubits.payflow.payflow_network.Driver.Stock_allocate.Tab_Stock_Activity;
import com.ubits.payflow.payflow_network.General.GPStracker;
import com.ubits.payflow.payflow_network.R;
import com.ubits.payflow.payflow_network.Web_Services.Ret;
import com.ubits.payflow.payflow_network.Web_Services.RetrofitToken;
import com.ubits.payflow.payflow_network.Web_Services.Web_Interface;
import com.ubits.payflow.payflow_network.utils.Utils;

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
import java.util.TimeZone;


import es.dmoral.toasty.Toasty;
import fr.arnaudguyon.perm.Perm;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import info.androidhive.fontawesome.FontTextView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverAttendance  extends AppCompatActivity  implements View.OnClickListener, Callback<ResponseBody>, LocationListener {
private static final String TAG="HomeFragment";
private int current_store_pos;
private String filePath;
Button buttonStartDay;
private EditText location1;
FontTextView buttonCapture;

TextView textViewDate,textViewTime;
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
JSONObject jsonObject;
    Location location;
    TextView datetext,timetext;
    double longitude;
    double latitude;
    protected LocationManager locationManager;
    private String provider_info;
    Perm perm;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSIONS[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.CAMERA};
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.driverattendance_listview);
        agentlistview=findViewById(R.id.Agent_listview);
        buttonStartDay=findViewById(R.id.driver_button);
        buttonStartDay.setOnClickListener(this);
        buttonCapture=findViewById(R.id.camera);
        buttonCapture.setOnClickListener(this);
        location1=findViewById(R.id.location);
        location1.setOnClickListener(this);
        datetext=findViewById(R.id.Date);
        timetext=findViewById(R.id.Time);
        initDateTime();
        //getLocation();
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
    }

    if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
    }
    fetchagent();
        }

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
        datetext.setText("" + dayName + " " + currentDate + "");
        timetext.setText(currentTime);

    }




    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        location1.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude()  );

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            location1.setText(location1.getText()+ "\n"+addresses.get(0).getAddressLine(0));
        }catch(Exception e)
        {
            Toast.makeText(this, "" +e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(DriverAttendance.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }




    private void fetchagent() {
                Web_Interface webInterface = Ret.getClient().create(Web_Interface.class);
                Call<FetchAgent> call = webInterface.requestfetchagent(RetrofitToken.token);
                call.enqueue(new Callback<FetchAgent>() {
                        @Override
                        public void onResponse(Call<FetchAgent> call, Response<FetchAgent> response) {
                                if (response.isSuccessful() && response.code() == 200) {


                                        List<Body> agent =new ArrayList<>();
                                        assert response.body() != null;
                                        agent=response.body().getBody();
                                        list1=new ArrayList<>();
                                        list = new ArrayList<>();
                                        agentid=new ArrayList<>();
                                        for (int i = 0; i < agent.size(); i++) {
                                              list1.add(response.body());
                                              agentid.add(agent.get(i).getId());
                                              list.add(agent.get(i).getName());
                                        }
                                        Log.d("agentname",list.toString() +"\n" +"agentid" +agentid.toString());
                                    populateListView(list1);
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
        }








        /*--------- getting stores list from api -----------*/



@Override
public void onClick(View v) {
    if(v.getId()==R.id.location){
        getLocation();
    }
        if(v.getId()==R.id.driver_button)
        {
            if(location1.getText().toString().length()==0){
                Toast.makeText(this,"Enable GPS First",Toast.LENGTH_SHORT).show();
            }
            else {
                if (filePath == null) {

                    Toasty.warning(getApplicationContext(), "please capture the image").show();
                } else {
                    userid = agentid.toString();

                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", imageid);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    markattendance(userid, latitude, longitude, status, jsonObject);

                }
            }

        }


        else if(v.getId()==R.id.camera)
        {


                    getImageFromCamera();

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

        /*------------------- getting current latlong ------------------*/
private void getGps() {

        GPStracker gt = new GPStracker(getApplicationContext());
        Location l = gt.getLocation();
        if (l == null) {
        Lat = 0.00;
        Longs = 0.00;
        } else {
        Lat = l.getLatitude();
        Longs = l.getLongitude();
        latitude=Lat;
        longitude=Longs;

        }
        Log.d(TAG, "getGps: "+latitude+" "+longitude);
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
        Log.d(TAG,"getting image from camera");
        Intent intent = new Intent(getApplicationContext(), ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, false);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, false);//default is true
        startActivityForResult(intent, 1213);

        }


@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
         filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
        Log.d(TAG,"file path: "+filePath);
        Toasty.success(getApplicationContext(),"Image captured Successfully").show();
        if(filePath!=null)
        {
         sendImageDataToServer();
        }
        }
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {
         try {
          Uri selectedImage = data.getData();
          Bitmap bitmap = (Bitmap) data.getExtras().get("data");
          ByteArrayOutputStream bytes = new ByteArrayOutputStream();
          if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
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
        Toasty.success(getApplicationContext(),"Image captured Successfully").show();

        }
        catch (Exception e) {
          e.printStackTrace();
        }
        }
}

        public String getRealPathFromURI(Uri contentUri) {
         String[] proj = {MediaStore.Audio.Media.DATA};
         Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
         int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
         cursor.moveToFirst();
         return cursor.getString(column_index);
        }

/*--------- start day response -----------*/


  @Override
  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful() && response.code()==200) {
         try {
           JSONObject jObjSuccess = new JSONObject(response.body().string());
           Log.d(TAG,jObjSuccess.getString("message"));
           message=jObjSuccess.getString("message");
           Toasty.success(getApplicationContext(),message).show();
           Log.d(TAG, "saving day started value to true");
           Intent i =new Intent(this,Tab_Stock_Activity.class);
           startActivity(i);
         }
         catch (Exception e) {
          Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
         }
        }
        else {
          try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
             Log.d(TAG,jObjError.getString("message"));
             message=jObjError.getString("message");
             Toasty.info(getApplicationContext(),message +"here").show();
          }
          catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
          }
        }

  }

  @Override
  public void onFailure(Call<ResponseBody> call, Throwable t) {
    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
    Log.d(TAG,t.getLocalizedMessage());
  }
 }




