package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.ViewGroup;

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
    private Fade mFade;
    private GestureDetectorCompat mDetector;
    private int currentView = 3;

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

        constructList(mCurrentLinearLayout);
    }
    //Method who create the view's hierarchy as list and set initial view
    public void constructList(ViewGroup viewGroup){

        ArrayList<LayoutConstructor> listLayout=new ArrayList<LayoutConstructor>();

        listLayout.add(new LayoutSmileySad(getBaseContext()));
        listLayout.add(new LayoutSmileyDissapointed(getBaseContext()));
        listLayout.add(new LayoutSmileyNormal(getBaseContext()));
        listLayout.add(new LayoutSmileyHappy(getBaseContext()));
        listLayout.add(new LayoutSmileySuperHappy(getBaseContext()));

        viewGroup.addView(listLayout.get(currentView).getFrameLayout());
    }
}
