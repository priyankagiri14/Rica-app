package com.tms.ontrack.mobile.AirtimeSales;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans.Product;
import com.tms.ontrack.mobile.R;
@Layout(R.layout.feed_item)
public class InfoView {
    @ParentPosition
    public int mParentPosition;
    @ChildPosition
    public int mChildPosition;

    @View(R.id.titlePack)
    public TextView titlePack;

    @View(R.id.titleRetail)
    public TextView titleRetail;

   @View(R.id.buttonProceed)
   public Button buttonProceed;


    public Product mInfo;
    public Context mContext;

    public InfoView(Context context, Product info) {
        mContext = context;
        mInfo = info;
    }

    @Resolve
    public void onResolved() {
        Log.d("AirtimeSales", "onResolved: called ");
        titlePack.setText(mInfo.getDescription());
        titleRetail.setText("R"+" "+mInfo.getRetailValue()+"");

    }

    @Click(R.id.buttonProceed)
    public void onClick()
    {
        Log.d("AirtimeSales", "onClick: "+mInfo.getId());
        String productId=mInfo.getId()+"";
        Intent i=new Intent(mContext,AirtimeRechargeActivity.class);
        i.putExtra("product_id",productId);
        i.putExtra("retail_value",mInfo.getRetailValue());
        i.putExtra("recharge_description",mInfo.getDescription());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);

    }



}