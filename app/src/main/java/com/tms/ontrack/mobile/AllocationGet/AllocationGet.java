package com.tms.ontrack.mobile.AllocationGet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tms.ontrack.mobile.R;

public class AllocationGet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allocation_get);

//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllocationGet.this);
//
//        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                String id = Pref.getAllocationId(AllocationGet.this);
//                String status = "ACCEPTED";
//                Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//
//                try {
//                    JSONObject paramObject = new JSONObject();
//                    paramObject.put("id", id);
//                    paramObject.put("status", status);
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), (paramObject).toString());
//
//                    Call<AllocationStatusResponse> allocationStatusResponseCall = web_interface.requestAllocationStatus(bo);
//                    allocationStatusResponseCall.enqueue(new Callback<AllocationStatusResponse>() {
//                        @Override
//                        public void onResponse(Call<AllocationStatusResponse> call, Response<AllocationStatusResponse> response) {
//
//                            String message = response.body().getMessage();
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                            Intent batchesGetIntent = new Intent(AllocationGet.this, BatchesGetList.class);
//                            startActivity(batchesGetIntent);
//                        }
//
//                        @Override
//                        public void onFailure(Call<AllocationStatusResponse> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // Write your code here to invoke NO event
//                Toast.makeText(getApplicationContext(), "You Declined the Batches", Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });
//
//
//        Web_Interface web_interface = RetrofitToken.getClient().create(Web_Interface.class);
//        Call<AllocationGetResponse> allocationGetResponseCall = web_interface.requestAllocationGet(0, 0);
//        allocationGetResponseCall.enqueue(new Callback<AllocationGetResponse>() {
//
//            @Override
//            public void onResponse(Call<AllocationGetResponse> call, Response<AllocationGetResponse> response) {
//
//                List<Body> list=new ArrayList<>();
//                assert response.body() != null;
//                list=response.body().getBody();
//                List<String>list1=new ArrayList<>();
//                for(int i=0;i<list.size();i++)
//                {
//                    Log.d("AllocationGet", "onResponse: "+list.get(i).getMessage());
//                    String allocation_id = list.get(i).getId().toString();
//                    Pref.putAllocationId(MyApp.getContext(), allocation_id);
//                    String message = list.get(i).getMessage();
//                    list1.add(list.get(i).getId().toString());
//                    TextView myMsg = new TextView(AllocationGet.this);
//                    myMsg.setText(message);
//                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
//                    myMsg.setTextSize(20);
//                    alertDialog.setCustomTitle(myMsg);
//                    alertDialog.show().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//                }
//
//                Log.d("Batches", "onResponse: "+list1);
//            }
//
//            @Override
//            public void onFailure(Call<AllocationGetResponse> call, Throwable t) {
//
//            }
//        });
//    }
}
}
