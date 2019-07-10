//package com.ubits.payflow.payflow_network.Driver.Stock_allocate;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.ubits.payflow.payflow_network.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Agent_list_adapter extends BaseAdapter{
//    Context context;
//    private String[] questionsList;
//    private LayoutInflater inflater;
//    private static ArrayList<String> selectedAnswers;
//    private int selectedPosition = -1;
//    private RadioButton lastCheckedRB = null;
//
//
//    public Agent_list_adapter(Context applicationContext, String[] questionsList) {
//        this.context = context;
//        this.questionsList = questionsList;
//// initialize arraylist and add static string for all the questions
//        selectedAnswers = new ArrayList<>();
//        for (int i = 0; i < questionsList.length; i++) {
//            selectedAnswers.add("Not Attempted");
//        }
//        inflater = (LayoutInflater.from(applicationContext));
//
//
//    }
//
//    @Override
//    public int getCount() {
//        return questionsList.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//
//        view = inflater.inflate(R.layout.agent_list_view, null);
//// get the reference of TextView and Button's
//        //TextView agentname = (TextView) view.findViewById(R.id.agent_listview_text);
//        //RadioGroup radioGroup=view.findViewById(R.id.agent_radiogroup);
//        //RadioButton agentnm = (RadioButton) view.findViewById(R.id.agent_radiobtn);
//        //agentnm.setChecked(i == selectedPosition);
//        //agentnm.setTag(i);
//
//        //agentnm.setOnClickListener(new View.OnClickListener() {
//          //  @Override
//            //public void onClick(View view) {
//                selectedPosition = (Integer) view.getTag();
//                notifyDataSetChanged();
//            }
//        //});
//// set the value in TextView
//        //agentname.setText(questionsList[i]);
//        //return view;
//    }
//}

