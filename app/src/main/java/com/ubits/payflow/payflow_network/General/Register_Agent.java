package com.ubits.payflow.payflow_network.General;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Config;
import com.ubits.payflow.payflow_network.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Register_Agent extends AppCompatActivity {
    private static String ACTIVE = "active";
    private static String IN_ACTIVE = "inactive";

    private Button btpic, btnup, btnCheckIn, btnCheckOut;
    private String ba1, clientid, message, st, address;
    private int status;
    public static String URL = Config.BASE_URL + "app/ficapics/upload_image_pop.php";
    private String mCurrentPhotoPath;
    private ImageView mImageView, backButton;
    private TextView txtinfosid, tvTitle;
    private Double Lat, Longs;
    private boolean statusValue = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__agent);

        backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvTitle = findViewById(R.id.tv_titlebar);
        tvTitle.setText(R.string.title_activity_register__agent);


        SharedPreferences sharedPreferences = getSharedPreferences("ON", Context.MODE_PRIVATE);
        clientid = sharedPreferences.getString("UserName", null);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Balance", "0.00");
        editor.commit();

        txtinfosid = findViewById(R.id.txtagentcode);
        txtinfosid.setText(clientid.toString());


        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        btpic = findViewById(R.id.buttonChoose);

        mImageView = findViewById(R.id.Imageprev);
        btpic.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        captureImage();
                    } else {
                        String[] permissionRequested = {Manifest.permission.CAMERA};
                        requestPermissions(permissionRequested, CONTEXT_INCLUDE_CODE);
                    }
                } else {
                    captureImage();
                }
            }
        });
        btnCheckIn = findViewById(R.id.btn_check_in);
        btnCheckOut = findViewById(R.id.btn_check_out);

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    if (mImageView.getDrawable() == null)
                        Toast.makeText(Register_Agent.this, "please click image", Toast.LENGTH_SHORT).show();
                    else
                        upload();
                } else {
                    Toast.makeText(Register_Agent.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    if (mImageView.getDrawable() == null)
                        Toast.makeText(Register_Agent.this, "please click image", Toast.LENGTH_SHORT).show();
                    else {
                        statusValue = false;
                        upload();
                    }
                } else {
                    Toast.makeText(Register_Agent.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTEXT_INCLUDE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                Toast.makeText(this, "PERMISSION DENIED: cannot take photo", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void upload() {
        new Register_Agent.uploadToServer().execute();
    }


    private void captureImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        photoFile = createImageFile();
                    } else {
                        String[] permissionRequested = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissionRequested, CONTEXT_INCLUDE_CODE);
                    }
                } else {
                    photoFile = createImageFile();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setPic();
        }
    }


    public class uploadToServer extends AsyncTask<String, Void, String> {

        private ProgressDialog pd = new ProgressDialog(Register_Agent.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();

            GPStracker gt = new GPStracker(getApplicationContext());
            Location l = gt.getLocation();
            if (l == null) {
                Lat = 0.00;
                Longs = 0.00;
            } else {
                Lat = l.getLatitude();
                Longs = l.getLongitude();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
                byte[] ba = bao.toByteArray();
                ba1 = Base64.encodeToString(ba, Base64.NO_WRAP);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(Register_Agent.this, Locale.getDefault());

                addresses = geocoder.getFromLocation(Lat, Longs, 1);
                if (addresses != null && addresses.size() > 0) {
                    address = addresses.get(0).getAddressLine(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1 != null ? ba1 : ""));
            nameValuePairs.add(new BasicNameValuePair("lat", Lat + ""));
            nameValuePairs.add(new BasicNameValuePair("Long", Longs + ""));
            nameValuePairs.add(new BasicNameValuePair("address", address != null ? address : ""));
            nameValuePairs.add(new BasicNameValuePair("Status", statusValue == true ? ACTIVE : IN_ACTIVE));
            nameValuePairs.add(new BasicNameValuePair("Customer_ID", txtinfosid.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + "_" + clientid + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                st = EntityUtils.toString(response.getEntity());

                JSONObject myObject = new JSONObject(st);
                status = myObject.optInt("status");
                message = myObject.optString("msg");
            } catch (Exception e) {
            }
            return st;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.dismiss();
            if (result != null) {
                if (status == 1) {
                    Toast.makeText(Register_Agent.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Register_Agent.this, MainActivity.class));
                }
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", "Cool" + mCurrentPhotoPath);
        return image;
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
