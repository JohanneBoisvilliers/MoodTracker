package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public abstract class LayoutConstructor extends ViewGroup{

    private FrameLayout mFrameLayout;
    private FrameLayout.LayoutParams mParamsFrameLayout;
    private ImageView mSmiley;
    private FrameLayout.LayoutParams mParamSmiley;
    private FrameLayout mFrameLayoutButtons;
    private FrameLayout.LayoutParams mParamsButtons;
    private Button mAddComments;
    private FrameLayout.LayoutParams mParamsAddComments;
    private Button mHistoryButton;
    private FrameLayout.LayoutParams mParamsHistoryButton;

    public LayoutConstructor(Context context) {
        super(context);
    }
    //Method who create a FrameLayout containing an ImageView with all their parameters, just 2 parameters are customizable
    //by children : color background and image
    protected void LayoutCreator(int color, int draw) {
        //create an empty FrameLayout
        mFrameLayout = new FrameLayout(getContext());
        //get some parameters for mFrameLayout in a Frame.LayoutParams object
        mParamsFrameLayout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mParamsFrameLayout.gravity= Gravity.CENTER;
        //set previous Frame.LayoutParams in mFrameLayout and set some other parameters
        mFrameLayout.setLayoutParams(mParamsFrameLayout);
        //mFrameLayout.setGravity(Gravity.CENTER);
        mFrameLayout.setBackgroundColor(color);
        //--Create an ImageView with his parameters
            mSmiley = new ImageView(getContext());
            mSmiley.setImageResource(draw);
            mParamSmiley =new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            mParamSmiley.gravity=Gravity.CENTER;
            mSmiley.setLayoutParams(mParamSmiley);
            mSmiley.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
            mSmiley.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);
            //and push it into his parent : mFrameLayout
            mFrameLayout.addView(mSmiley);
        //------Create a new FrameLayout who contains 2 buttons : new comments and history
                mFrameLayoutButtons = new FrameLayout(getContext());
                //Get basics parameters for it
                mParamsButtons = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                mParamsButtons.gravity = Gravity.BOTTOM;
                mParamsButtons.setMargins(20,0,20,20);
                //Set previous Frame.LayoutParams in mFrameLayoutButtons and set some other parameters
                mFrameLayoutButtons.setLayoutParams(mParamsButtons);
        //----------Create a first button for a new comment with his parameters
                    mAddComments = new Button(getContext());
                    mParamsAddComments = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    mAddComments.setBackground(getResources().getDrawable(R.drawable.ic_note_add_black));
                    mAddComments.setWidth((int)getResources().getDimension(R.dimen.size_icone_width_add_com));
                    mAddComments.setHeight((int)getResources().getDimension(R.dimen.size_icone_heigt_add_com));
                    mParamsAddComments.gravity=Gravity.START;
                    mAddComments.setLayoutParams(mParamsAddComments);
                    //and push it into his parent : mFrameLayoutButtons
                    mFrameLayoutButtons.addView(mAddComments);
        //----------Create a second Button for accessing mood's history
                    mHistoryButton = new Button(getContext());
                    mParamsHistoryButton = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    mHistoryButton.setBackground(getResources().getDrawable(R.drawable.ic_history_black));
                    mHistoryButton.setWidth((int)getResources().getDimension(R.dimen.size_icone_width_history));
                    mHistoryButton.setHeight((int)getResources().getDimension(R.dimen.size_icone_heigt_history));
                    mParamsHistoryButton.gravity=Gravity.END;
                    mHistoryButton.setLayoutParams(mParamsHistoryButton);
                    //and push it into his parent : mFrameLayoutButtons
                    mFrameLayoutButtons.addView(mHistoryButton);
        //add This new FrameLinear into his parent : mFrameLayout
        mFrameLayout.addView(mFrameLayoutButtons);
    }
    //Getter for mFrameLayout
    public FrameLayout getFrameLayout() {
        return this.mFrameLayout;
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
