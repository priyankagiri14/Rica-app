package com.ubits.payflow.payflow_network.General;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Register extends AppCompatActivity {

EditText txtFirstName, txtLastName,txtemail,txtpassword, txtConfirmpassword,txtcellnumber;
String Province,Status;
Button btnLinkToLoginScreen, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtFirstName = (EditText)findViewById(R.id.name);
        txtLastName =  (EditText) findViewById(R.id.lastname);
        txtemail =  (EditText) findViewById(R.id.email);
        txtpassword =  (EditText) findViewById(R.id.password);
        txtConfirmpassword =  (EditText) findViewById(R.id.confirmpassword);
        txtcellnumber =  (EditText) findViewById(R.id.cellnumber);


       btnRegister = (Button) findViewById(R.id.btnRegister);



        btnLinkToLoginScreen = (Button) findViewById(R.id.btnLinkToLoginScreen);



        btnLinkToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                startActivity(new Intent(Register.this, Login.class));


            }
        });




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Validate text amount

                if (txtemail.getText().toString() == "") {

                    Toast.makeText(Register.this, "Please enter a valid email", Toast.LENGTH_LONG).show();

                }else{
                    //Validate text cell number



                    if (txtcellnumber.getText().toString() == "") {

                        Toast.makeText(Register.this, "Please enter a cell number", Toast.LENGTH_LONG).show();

                    }else{

                        String urls = "http://41.222.34.204/~erzsystemco/app/register//" + txtFirstName.getText().toString() + "/" +txtLastName.getText().toString() + "/" + txtcellnumber.getText().toString() +  "/" + txtemail.getText().toString() + "/"  + txtpassword.getText().toString() + "/" + Province + "/";

                        //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000002/CSPC/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/CSPC/";

                        new Register.JSONTask().execute(urls);

                    }


                }

            }

        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;





            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();


                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();


                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJSON);
                Status = parentObject.getString("Status");
                String Description = parentObject.getString("Description");

                return Description;




            } catch (MalformedURLException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();

                }
                try {
                    if (reader != null) {

                        reader.close();

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

if (Status.equals("ERROR")){

        Toast.makeText(Register.this, "Error with Registration" + result.toString(), Toast.LENGTH_LONG).show();


            }else{

    Toast.makeText(Register.this, "Registration Successfull", Toast.LENGTH_LONG).show();

    SharedPreferences sharedPreferences = getSharedPreferences("Payflow", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("Login","Yes");
    editor.putString("UserName",txtcellnumber.getText().toString());
    editor.putString("Name",txtFirstName.getText().toString());
    editor.commit();

    startActivity(new Intent(Register.this, MainActivity.class));


}




        }
    }



}
