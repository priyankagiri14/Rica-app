package com.tms.ontrack.mobile.AirtimeSales;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.mindorks.placeholderview.annotations.expand.Toggle;
import com.tms.ontrack.mobile.R;

@Parent
@SingleTop
@Layout(R.layout.feed_heading)
public class HeadingView {

    @View(R.id.headingTxt)
    public TextView headingTxt;

    @View(R.id.toggleIcon)
    public ImageView toggleIcon;

    @Toggle(R.id.toggleView)
    public LinearLayout toggleView;

    @ParentPosition
    public int mParentPosition;

    public Context mContext;
    public String mHeading;

    public HeadingView(Context context, String heading) {
        mContext = context;
        mHeading = heading;
    }

    @Resolve
    public void onResolved() {
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.arrow_up_float));
        headingTxt.setText(mHeading);
    }

    @Expand
    public void onExpand(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.arrow_down_float));
    }

    @Collapse
    public void onCollapse(){
        toggleIcon.setImageDrawable(mContext.getResources().getDrawable(android.R.drawable.arrow_up_float));
    }
}