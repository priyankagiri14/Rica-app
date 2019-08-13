package com.tms.ontrack.mobile.Driver.Stock_allocate;//package com.ubits.payflow.payflow_network.Driver.Stock_allocate;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Menu;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.SearchView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.ubits.payflow.payflow_network.R;
//import com.ubits.payflow.payflow_network.adapter.ProcessBatchnAdapter;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Assign_agent extends AppCompatActivity implements SearchView.OnQueryTextListener {
//    ListView agentList;
//    String[] questions;
//    Button assign;
//    SearchView searchView;
//    ArrayAdapter<String> adapter;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.assign_agent);
//        searchView=findViewById(R.id.searchview);
//        searchView.setOnQueryTextListener(this);
//        String agentsname[] = {"Aman(123)", "Mohit(243)", "Ojas(543)", "Yagya(573)", "Pankaj(989)", "Ajay(888)", "Dhruv(190)", "Vikas(575)", "Navpreet(26)", "Khanu(232)"};
//// get the string array from string.xml file
//        questions = agentsname;
//// get the reference of ListView and Button
//        agentList = (ListView) findViewById(R.id.agent_list);
//        assign = (Button) findViewById(R.id.send);
//// set the adapter to fill the data in the ListView
//        Agent_list_adapter customAdapter = new Agent_list_adapter(getApplicationContext(), questions);
//        agentList.setAdapter(customAdapter);
//
//        assign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//
//        adapter=new ArrayAdapter<>(this,R.layout.agent_list_view,R.id.agent_listview_text,agentsname);
//        agentList.setAdapter(adapter);
//
//    }
//
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        Assign_agent.this.adapter.getFilter().filter(newText);
//        return false;
//    }
//}
