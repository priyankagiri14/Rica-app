package com.tms.ontrack.mobile.Driver.DriverAttendance.model.get_Agent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tms.ontrack.mobile.Driver.Stock_allocate.ListViewItemViewHolder;
import com.tms.ontrack.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class  Agentattendance_Adapter extends BaseAdapter {
    Context context;
    private String[] questionsList;
    private LayoutInflater inflater;
    private static ArrayList<String> selectedAnswers;
    private int selectedPosition = -1;
    private RadioButton lastCheckedRB = null;
    private List<Body> bodyList;
    private List<FetchAgent> agentGetLists;
    TextView agentname;
    CheckBox agentcheckbox;


    public Agentattendance_Adapter(Context context, List<FetchAgent> agentGetLists ) {
        this.context = context;
        this.agentGetLists = agentGetLists;

        //initialize arraylist and add static string for all the questions

        /*selectedAnswers = new ArrayList<>();
        for (int i = 0; i < agentGetLists.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }*/
//        inflater = (LayoutInflater.from(applicationContext));


    }

    @Override
    public int getCount() {
        return agentGetLists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder=null;
        TextView agentname=null;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.driver_attendance, viewGroup,false);
            //get the reference of TextView and Button's
            agentname = view.findViewById(R.id.driver_text);
            CheckBox agentcheckbox = view.findViewById(R.id.driver_checkbox);
            /*RadioButton agentnm = (RadioButton) view.findViewById(R.id.driver_checkbox);*/
            agentcheckbox.setChecked(i == selectedPosition);
            agentcheckbox.setTag(i);


            agentcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        bodyList.get(i).setIschecked(true);
                    }
                    else
                    {
                        bodyList.get(i).setIschecked(false);
                    }
                }
            });
//            agentcheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    selectedPosition = (Integer) view.getTag();
//                    notifyDataSetChanged();
//                    Log.d("Selected Position", String.valueOf(selectedPosition));
//                }
//            });
// set the value in TextView
            //agentname.setText(questionsList[i]);
            viewHolder = new ListViewItemViewHolder(view);
            viewHolder.setItemCheckbox(agentcheckbox);
            viewHolder.setItemTextView(agentname);
            view.setTag(viewHolder);
        }
        // bodyList=thisFetchAgent.getBody();

        //   agentname.setText(bodyList.get(j).getName());
        final FetchAgent thisBatchesGetResponse = agentGetLists.get(i);

        bodyList = thisBatchesGetResponse.getBody();
        //batchesgetcheckbox.setChecked(fa);
        for (int j=0;j<bodyList.size();j++) {

            if (agentname != null) {
                agentname.setText(bodyList.get(i).getName());
            }

        }
        return view;
    }
}



