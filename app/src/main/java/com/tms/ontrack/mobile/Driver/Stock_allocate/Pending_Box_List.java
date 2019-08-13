package com.tms.ontrack.mobile.Driver.Stock_allocate;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tms.ontrack.mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Pending_Box_List extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_box_list);


        setTitle("dev2qa.com - Android ListView With CheckBox");

        // Get listview checkbox.
        // Initiate listview data.
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        // Create a custom list view adapter with checkbox control.
        final Stockallocate_listadapter listViewDataAdapter = new Stockallocate_listadapter(getApplicationContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();


    }

    // Return an initialize list of ListViewItemDTO.
    private List<ListViewItemDTO> getInitViewItemDtoList()
    {
        String itemTextArr[] = {"Aman (123)", "Mohit (234)", "Ajay (454)", "Sachin (555)", "Mayank (456)", "Ojas (567)", "Arjun (888)"};

        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = itemTextArr.length;

        /*for(int i=0;i<length;i++)
        {
            String itemText = itemTextArr[i];

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);

            ret.add(dto);
        }*/

        return ret;
    }
}
