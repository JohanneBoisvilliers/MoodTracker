package com.boisvilliers.johanne.moodtracker.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.ConstructList;
import com.boisvilliers.johanne.moodtracker.model.GestureDetectorListener;
import com.boisvilliers.johanne.moodtracker.view.EditTextAddComments;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mCurrentLinearLayout;
    private ImageButton mAddComments;
    private ImageButton mHistory;
    private Fade mFade;
    private GestureDetectorCompat mDetector;
    protected int currentView = 3;
    private Context mContext = this;
    ConstructList mMoodList;

    /*OnTouchEvent get the gestureDirection in GestureDetectorListener and then add or remove 1 to currentView
     which contain the index of the current view in Hierarchy View's list.
    After that, refreshing the view with setContentView.*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        char gestureDirection = GestureDetectorListener.getGestureDirection();
        if (gestureDirection =='U'){
            if(currentView<4){
                currentView+=1;
                mCurrentLinearLayout.removeAllViews();
                refreshList(mCurrentLinearLayout);
            }
            setContentView(mCurrentLinearLayout);
        }
        if (gestureDirection =='D'){
            if(currentView>0){
                currentView-=1;
                mCurrentLinearLayout.removeAllViews();
                refreshList(mCurrentLinearLayout);
            }
            setContentView(mCurrentLinearLayout);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this,new GestureDetectorListener());
        mCurrentLinearLayout =(ViewGroup) findViewById(R.id.mainActivity_global);
        mAddComments = (ImageButton) findViewById(R.id.Addcomments_button);

        mMoodList=new ConstructList(this);
        refreshList(mCurrentLinearLayout);
    }
    //Method who refresh the view to show and set a listener on buttons
    public void refreshList(ViewGroup viewGroup){
        viewGroup.addView(mMoodList.getListLayout().get(currentView).getFrameLayout());
        addListenerOnHistoryButton();
        addListenerOnAddCommentsButton();
    }
    //add a listener on buttons
    public void addListenerOnHistoryButton() {
        //listener and actions for History button
        mHistory = (ImageButton) findViewById(R.id.History_button);
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }
    public void addListenerOnAddCommentsButton() {
        //Listener and actions for Addcomments button
        mAddComments = (ImageButton) findViewById(R.id.Addcomments_button);
        mAddComments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Set an alert dialog which contain the EditText to let a commentary
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final EditTextAddComments commentaries = new EditTextAddComments(mContext);
                builder.setView(commentaries.getDialogComments());
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .create()
                       .show();
            }
        });
    }
}
