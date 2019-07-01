package com.ubits.payflow.payflow_network.Airtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ubits.payflow.payflow_network.Adapter;
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



import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;
public class SellAirtimePrinted extends AppCompatActivity {

    String Status,Balance,Discount,username,password,Network,cellnumber,amount,user,Description;
    TextView txtNetwork;
    EditText txtamount,txtcellnumber;
    ProgressDialog pd;
    Spinner sp;
    Button btnPurchase ,btn1, btn2, btn3, btn4;



    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String value = "";

    String[] networks={"MTN","Vodacom","CellC","TelkomMobile"};
    int[] images = {R.drawable.mtn,R.drawable.vodacoms,R.drawable.cellcs,R.drawable.telkom};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_airtime_printed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        txtNetwork = (TextView)findViewById(R.id.txtNetworkP);
        txtamount =  (EditText) findViewById(R.id.txtAmountP);

        btnPurchase =  (Button) findViewById(R.id.btnPurchaseP);
        btn1=  (Button) findViewById(R.id.btn1P);
        btn2=  (Button) findViewById(R.id.btn2P);
        btn3=  (Button) findViewById(R.id.btn3P);
        btn4=  (Button) findViewById(R.id.btn4P);



        password = "1234";
        SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("UserName", null);


        String urls = "http://ezaga.co.za/app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";


        new SellAirtimePrinted.JSONTaskb().execute(urls);


        txtNetwork.setText("MTN");

        sp=(Spinner)findViewById(R.id.spinnerbuyairtimeP);

        Adapter adapter=new Adapter(this,networks,images);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                Network = networks[i];
                txtNetwork.setText(Network);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("5");



            }


        });


        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("10");

            }


        });

        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("15");

            }


        });

        //Select BUTTON  &&&&&&&&&&&&&&&&&&&&&&&&&&&((((((((((((((((((((((((((((***************************************())))))))))))))))))))))))))))))))))))))
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtamount.setText("25");

            }


        });




        //Purchase Airtime
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtNetwork.setText("Processing Trasnaction...");

                pd=new ProgressDialog(SellAirtimePrinted.this);
                pd.setTitle("Processing Transaction");
                pd.setMessage("Processing...Please wait");
                pd.show();
                //Validate text amount

                amount = txtamount.getText().toString();

                if (amount.toString() == "") {

                    Toast.makeText(SellAirtimePrinted.this, "Please enter an amount", Toast.LENGTH_LONG).show();

                }else{
                    //Validate text cell number

                    cellnumber = "0786123457";

                    if (cellnumber == "") {



                    }else{

                        if (cellnumber.length() == 10) {

                            Description = "EZAGA";

                         // String urls = "http://ezaga.co.za/app/airtime//"+ username +"/"+ Description +"/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/Ezaga/";

                            //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000002/CSPC/" + cellnumber + "/" + amount + "/" + Network + "/" + user + "/CSPC/";


                            IntentPrint("\nEzaga Airtime\nYou Purchased R"+amount+" "+Network+"\nVoucher:121592 125121 13923\n\n\n\n");

                            //new SellAirtimePrinted.JSONTask().execute(urls);

                        }else{

                            Toast.makeText(SellAirtimePrinted.this, "Please enter a correct cell number", Toast.LENGTH_LONG).show();

                            txtNetwork.setText("Error with Cell Number");
                        }

                        //String urls = "http://41.222.34.204/~erzsystemco/webservice/airtime//100000000/pepfizz/" + cellnumber + "/" + amount + "/" + Network + "/" + username + "/"+username+"/";

                        //  http://cspcapp.co.za/app/airtime//0786826455/hello/0786826455/3/MTN/0786826455/CSPC/



                    }



                }

            }


        });


    }



    public void IntentPrint(String txtvalue)
    {
        byte[] buffer = txtvalue.getBytes();
        byte[] PrintHeader = { (byte) 0xAA, 0x55,2,0 };
        PrintHeader[3]=(byte) buffer.length;
        InitPrinter();
        if(PrintHeader.length>128)
        {
            value+="\nValue is more than 128 size\n";
            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {

                outputStream.write(txtvalue.getBytes());
                outputStream.close();
                socket.close();
            }
            catch(Exception ex)
            {
                value+=ex.toString()+ "\n" +"Excep IntentPrint \n";
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
            }
        }
    }
    public void InitPrinter()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0)
            {
                for(BluetoothDevice device : pairedDevices)
                {
                    if(device.getName().equals("BlueTooth PrinterUtil")) //Note, you will need to change this to match the name of your device
                    {
                        bluetoothDevice = device;
                        break;
                    }
                }

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
                bluetoothAdapter.cancelDiscovery();
                socket.connect();
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                beginListenForData();
            }
            else
            {
                value+="No Devices found";
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
                return;
            }
        }
        catch(Exception ex)
        {
            value+=ex.toString()+ "\n" +" InitPrinter \n";
            Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        }
    }
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = inputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                inputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Log.d("e", data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public class JSONTaskb extends AsyncTask<String, String, String> {


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
                Balance= parentObject.getString("Balance");
                Discount = parentObject.getString("discount");


                return Balance;


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

            if (Status.equals("ERROR") ) {

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalanceP);
                txtBalance.setText("R 0.00");


            }  if (Status.equals("SUCCESS")) {


                TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalanceP);
                txtBalance.setText("Balance: R "+ Balance.toString());

                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("Balance", Balance);
                // editor.putString("Discount", Discount);
                editor.commit();

            } else {


                SharedPreferences sharedPreferences = getSharedPreferences("EZAGA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Balance", "0.00");
                editor.commit();
                Balance = "0.00";
                TextView txtBalance = (TextView) findViewById(R.id.txtsellairtimebalanceP);
                txtBalance.setText("Balance: R"+ Balance.toString());





            }


        }

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
                pd.dismiss();

            } catch (IOException e) {

                e.printStackTrace();
                pd.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
                pd.dismiss();
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



            if (Status.substring(0,1).equals("S")) {
                pd.dismiss();
                String urls = "http://ezaga.co.za/app/account//tester/lastname/" + username + "/ubits.co.za/" + password + "/test/";


                new SellAirtimePrinted.JSONTaskb().execute(urls);

                IntentPrint("\nEzaga Airtime\n Your Purchased R"+amount+" "+Network+"\n Your Voucher: 121592 125121 13923\n");

                txtNetwork.setText("Airtime Purchase Complete..");
                txtamount.setText("");
                txtcellnumber.setText("");
                Toast.makeText(SellAirtimePrinted.this, "Transaction Complete", Toast.LENGTH_LONG).show();



            }else{
                pd.dismiss();
                txtNetwork.setText(result.toString());
                //Display Error
                Toast.makeText(SellAirtimePrinted.this, "Transaction Error", Toast.LENGTH_LONG).show();

            }


        }
    }

}