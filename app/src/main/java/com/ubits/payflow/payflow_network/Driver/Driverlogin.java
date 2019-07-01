package com.ubits.payflow.payflow_network.Driver;

import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ubits.payflow.payflow_network.Driver.Driver_Dashboard.Driver_Dashboard;
import com.ubits.payflow.payflow_network.R;


public class Driverlogin extends AppCompatActivity implements View.OnClickListener {
    private EditText cellno,password;
    private Button login;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_login);
        cellno=findViewById(R.id.cellnumber);
        password=findViewById(R.id.password);
        login=findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       /* if(cellno.length()==0 ||password.length()==0){
            Toast.makeText(this,"Please enter your login details",Toast.LENGTH_SHORT).show();
        }
        else if(cellno.getText().toString().equals("1234567") && password.getText().toString().equals("opt123")){
            Toast.makeText(this,"Login Successfull",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(this, Driver_Dashboard.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this,"Invalid login details",Toast.LENGTH_SHORT).show();
        }*/
        Intent i=new Intent(this, Driver_Dashboard.class);
        startActivity(i);
        finish();
    }
}
