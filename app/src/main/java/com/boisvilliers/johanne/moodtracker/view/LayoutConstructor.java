package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public abstract class LayoutConstructor extends ViewGroup{

    private LinearLayout mLinearLayout;
    private LinearLayout.LayoutParams mParamsSmiley;
    private ImageView smiley;

    public LayoutConstructor(Context context) {
        super(context);
    }
    //Method who create a LinearLayout containing an ImageView with all their parameters, just 2 parameters are customizable
    //by children : color background and image
    protected void LayoutCreator(int color, int draw) {
        //create an empty LinearLayout
        mLinearLayout = new LinearLayout(getContext());
        //get some parameters for mlinearLayout in a Linear.LayoutParams object
        mParamsSmiley = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mParamsSmiley.height=0;
        mParamsSmiley.weight=1f;
        //set previous Linear.LayoutParams in mLinearLayout and set some other parameters
        mLinearLayout.setLayoutParams(mParamsSmiley);
        mLinearLayout.setGravity(Gravity.CENTER);
        mLinearLayout.setBackgroundColor(color);
        //Create an ImageView with his parameters
        smiley = new ImageView(getContext());
        smiley.setImageResource(draw);
        smiley.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        smiley.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
        smiley.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);
        //and push it into his parent : mLinearLayout
        mLinearLayout.addView(smiley);
    }
    //Getter for mLinearLayout
    public LinearLayout getLinearLayout() {
        return this.mLinearLayout;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.layout(l, t, r, b);
        }
    }
}
