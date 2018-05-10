package com.boisvilliers.johanne.moodtracker.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.ConstructList;
import com.boisvilliers.johanne.moodtracker.view.EditTextAddComments;
import com.boisvilliers.johanne.moodtracker.view.LayoutConstructor;

public class MainActivity extends AppCompatActivity {

    private View mCurrentFrameLayout;
    private ImageView mSmiley;
    private ImageButton mAddComments,mHistory;
    private GestureDetectorCompat mDetector;
    protected int currentView = 3;
    private Context mContext = this;
    private LayoutConstructor mMainHierarchy;
    private int[] mListColor,mListSmiley;

    /*OnTouchEvent get the gestureDirection in GestureDetectorListener and then add or remove 1 to currentView
     which contain the index of the current view in Hierarchy View's list.
    After that, refreshing the view with setContentView.*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        char gestureDirection = GestureDetectorListener.getGestureDirection();
        if (gestureDirection =='U'){
            if(currentView<4){
                int viewToReduce = currentView;
                currentView+=1;
                refreshView(currentView);
            }
        }
        if (gestureDirection =='D'){
            if(currentView>0){
                currentView-=1;
                refreshView(currentView);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this,new GestureDetectorListener());
        mSmiley=new ImageView(this);
        mMainHierarchy=new LayoutConstructor(this);

        mCurrentFrameLayout =findViewById(R.id.mainActivity_global);
        mCurrentFrameLayout =mMainHierarchy.getViewsHierarchy();
        mSmiley = mCurrentFrameLayout.findViewById(R.id.image_smiley);
        mAddComments = mCurrentFrameLayout.findViewById(R.id.Addcomments_button);
        mHistory = mCurrentFrameLayout.findViewById(R.id.History_button);

        mListColor=new ConstructList().getColorArray();
        mListSmiley=new ConstructList().getSmileyArray();

        this.refreshView(currentView);
        addListenerOnAddCommentsButton();
        addListenerOnHistoryButton();
    }

    public void refreshView(int index){
        mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[index]));
        mSmiley.setImageResource(mListSmiley[index]);
        setContentView(mCurrentFrameLayout);

    }
    //add a listener on buttons
    public void addListenerOnHistoryButton() {
        //listener and actions for History button
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
