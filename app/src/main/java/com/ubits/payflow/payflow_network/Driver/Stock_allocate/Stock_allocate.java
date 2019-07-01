package com.ubits.payflow.payflow_network.Driver.Stock_allocate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ubits.payflow.payflow_network.R;

import java.util.ArrayList;
import java.util.List;

public class Stock_allocate extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_with_checkbox);
        setTitle("dev2qa.com - Android ListView With CheckBox");

        // Get listview checkbox.
        final ListView listViewWithCheckbox = (ListView)findViewById(R.id.listview_with_checkbox);

        // Initiate listview data.
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        // Create a custom list view adapter with checkbox control.
        final ListViewItemCheckboxBaseAdapter listViewDataAdapter = new ListViewItemCheckboxBaseAdapter(getApplicationContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listViewWithCheckbox.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                }else
                {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });




    Button selectRemoveButton = (Button)findViewById(R.id.send);
        selectRemoveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog alertDialog = new AlertDialog.Builder(Stock_allocate.this).create();
            alertDialog.setMessage("Are you want to send this stock to the agent");

            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    int size = initItemList.size();
                    for(int i=0;i<size;i++)
                    {
                        ListViewItemDTO dto = initItemList.get(i);

                        if(dto.isChecked())
                        {
                           /* initItemList.remove(i);
                            i--;
                            size = initItemList.size();*/
                            Intent intent=new Intent(Stock_allocate.this,Assign_agent.class);
                            startActivity(intent);
                        }
                        else if(!dto.isChecked()){
                            Toast.makeText(getApplicationContext(),"Select any lot",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    listViewDataAdapter.notifyDataSetChanged();
                    Intent intent=new Intent(Stock_allocate.this,Assign_agent.class);
                    startActivity(intent);
                }
            });

            alertDialog.show();
        }
    });

}

    // Return an initialize list of ListViewItemDTO.
    private List<ListViewItemDTO> getInitViewItemDtoList()
    {
        String itemTextArr[] = {"270620191547001", "270620191547002", "270620191547003", "260620191447001", "260620191447002", "260620191447003", "250620191447001", "250620191447002", "240620191447001", "240620191447002"};

        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = itemTextArr.length;

        for(int i=0;i<length;i++)
        {
            String itemText = itemTextArr[i];

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);

            ret.add(dto);
        }

        return ret;
    }
}
