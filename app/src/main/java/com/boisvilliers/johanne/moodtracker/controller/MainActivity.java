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
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileyHappy;
import com.boisvilliers.johanne.moodtracker.view.LayoutSmileySuperHappy;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mCurrentLinearLayout;
    private Fade mFade;
    private GestureDetectorCompat mDetector;
    LayoutConstructor layoutSmileyHappy;
    LayoutConstructor layoutSmileySuperHappy;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        char gestureDirection = GestureDetectorListener.getGestureDirection();
        if (gestureDirection =='U'){
            for(int i=1;i==3;i+=0.1) {
                layoutSmileySuperHappy.getParamsSmiley().weight = i;
                setContentView(mCurrentLinearLayout);
            }
        }
        if (gestureDirection =='D'){
            layoutSmileySuperHappy.getParamsSmiley().weight = 0.5f;
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

        layoutSmileyHappy = new LayoutSmileyHappy(this);
        layoutSmileySuperHappy = new LayoutSmileySuperHappy(this);
        mCurrentLinearLayout.addView(layoutSmileyHappy.getLinearLayout());
        mCurrentLinearLayout.addView(layoutSmileySuperHappy.getLinearLayout());
    }
}
