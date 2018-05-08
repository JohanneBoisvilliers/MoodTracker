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
import android.widget.EditText;
import android.widget.ImageButton;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.GestureDetectorListener;
import com.boisvilliers.johanne.moodtracker.view.LayoutConstructor;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileyDissapointed;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileyHappy;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileyNormal;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileySad;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileySuperHappy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mCurrentLinearLayout;
    private ImageButton mAddComments;
    private ImageButton mHistory;
    private Fade mFade;
    private GestureDetectorCompat mDetector;
    private int currentView = 3;
    private Context mContext = this;

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
                constructList(mCurrentLinearLayout);
            }
            setContentView(mCurrentLinearLayout);
        }
        if (gestureDirection =='D'){
            if(currentView>0){
                currentView-=1;
                mCurrentLinearLayout.removeAllViews();
                constructList(mCurrentLinearLayout);
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

        constructList(mCurrentLinearLayout);

    }
    //Method who create the view's hierarchy as list, set initial view,and set a listener on buttons
    public void constructList(ViewGroup viewGroup){
        ArrayList<LayoutConstructor> listLayout=new ArrayList<LayoutConstructor>();
        listLayout.add(new LayoutSmileySad(getBaseContext()));
        listLayout.add(new LayoutSmileyDissapointed(getBaseContext()));
        listLayout.add(new LayoutSmileyNormal(getBaseContext()));
        listLayout.add(new LayoutSmileyHappy(getBaseContext()));
        listLayout.add(new LayoutSmileySuperHappy(getBaseContext()));
        viewGroup.addView(listLayout.get(currentView).getFrameLayout());
        addListenerOnButton();
    }
    //add a listener on buttons
    public void addListenerOnButton() {
        //listener and actions for History button
        mHistory = (ImageButton) findViewById(R.id.History_button);
        mHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
        //Listener and actions for Addcomments button
        mAddComments = (ImageButton) findViewById(R.id.Addcomments_button);
        mAddComments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Set an alert dialog which contain the EditText to let a commentary
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final EditText commentaries = new EditText(mContext);
                builder.setView(commentaries);

                builder.setMessage("Commentaries :")
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
