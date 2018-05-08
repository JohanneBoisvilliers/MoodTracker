package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public class LayoutConstructor extends ViewGroup{

    private FrameLayout mFrameLayout;
    private FrameLayout.LayoutParams mParamsFrameLayout;
    private ImageView mSmiley;
    private FrameLayout.LayoutParams mParamSmiley;
    private FrameLayout mFrameLayoutButtons;
    private FrameLayout.LayoutParams mParamsButtons;
    private ImageButton mAddComments;
    private FrameLayout.LayoutParams mParamsAddComments;
    private ImageButton mHistoryButton;
    private FrameLayout.LayoutParams mParamsHistoryButton;
    private int mColor;
    private int mDraw;

    //Constructor who create a FrameLayout containing an ImageView with all their parameters, just 2 parameters are customizable
    //by children : color background and image
    public LayoutConstructor(Context context,int color, int draw) {
        super(context);
        this.mColor=color;
        this.mDraw=draw;
        setFrameLayout();
    }
    //Create a FrameLayout who contain a color background and an ImageView for Smileys
    public void setFrameLayout(){
        //create an empty FrameLayout
        mFrameLayout = new FrameLayout(getContext());
        //get some parameters for mFrameLayout in a Frame.LayoutParams object
        mParamsFrameLayout = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        mParamsFrameLayout.gravity= Gravity.CENTER;
        //set previous Frame.LayoutParams in mFrameLayout and set some other parameters
        mFrameLayout.setLayoutParams(mParamsFrameLayout);
        //mFrameLayout.setGravity(Gravity.CENTER);
        mFrameLayout.setBackgroundColor(this.mColor);
        //--Create an ImageView with his parameters
        mSmiley = new ImageView(getContext());
        mSmiley.setImageResource(this.mDraw);
        mParamSmiley =new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mParamSmiley.gravity=Gravity.CENTER;
        mSmiley.setLayoutParams(mParamSmiley);
        mSmiley.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
        mSmiley.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);
        //and push it into his parent : mFrameLayout
        mFrameLayout.addView(mSmiley);
        setFrameLayoutButtons();
    }
    //Create a new FrameLayout who contains 2 buttons : new comments and history
    public void setFrameLayoutButtons(){
        mFrameLayoutButtons = new FrameLayout(getContext());
        //Get basics parameters for it
        mParamsButtons = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mParamsButtons.gravity = Gravity.BOTTOM;
        mParamsButtons.bottomMargin=0;
        //Set previous Frame.LayoutParams in mFrameLayoutButtons and set some other parameters
        mFrameLayoutButtons.setLayoutParams(mParamsButtons);
        setAddCommentsButtonParams();
        setHistoryButtonParams();
        //add This new FrameLayout into his parent : mFrameLayout
        mFrameLayout.addView(mFrameLayoutButtons);
    }
    //Create a first button to leave a new comment
    public void setAddCommentsButtonParams(){
        mAddComments = new ImageButton(getContext());
        mAddComments.setId(R.id.Addcomments_button);
        mParamsAddComments = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mAddComments.setBackground(null);
        mAddComments.setImageDrawable(getResources().getDrawable(R.drawable.ic_note_add_black));
        mAddComments.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mParamsAddComments.width=((int)getResources().getDimension(R.dimen.size_icone_width_add_com));
        mParamsAddComments.height=((int)getResources().getDimension(R.dimen.size_icone_heigt_add_com));
        mParamsAddComments.gravity=Gravity.START;
        mAddComments.setLayoutParams(mParamsAddComments);
        //push it into his parent : mFrameLayoutButtons
        mFrameLayoutButtons.addView(mAddComments);
    }
    //Create a second Button for accessing mood's history
    public void setHistoryButtonParams() {
        mHistoryButton = new ImageButton(getContext());
        mParamsHistoryButton = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mHistoryButton.setBackground(null);
        mHistoryButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_history_black));
        mHistoryButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mParamsHistoryButton.width=((int)getResources().getDimension(R.dimen.size_icone_width_history));
        mParamsHistoryButton.height=((int)getResources().getDimension(R.dimen.size_icone_heigt_history));
        mParamsHistoryButton.gravity=Gravity.END;
        mHistoryButton.setLayoutParams(mParamsHistoryButton);
        mHistoryButton.setId(R.id.History_button);
        //push it into his parent : mFrameLayoutButtons
        mFrameLayoutButtons.addView(mHistoryButton);
    }
    //Getter for mFrameLayout
    public FrameLayout getFrameLayout() {
        return this.mFrameLayout;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
