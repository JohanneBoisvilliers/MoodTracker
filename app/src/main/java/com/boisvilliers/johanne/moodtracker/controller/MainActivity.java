package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.vue.LayoutSmileyHappy;
import com.boisvilliers.johanne.moodtracker.vue.LayoutSmileySuperHappy;

public class MainActivity extends AppCompatActivity {

    LinearLayout mCurrentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentLinearLayout = findViewById(R.id.mainActivity_global);

        LayoutSmileyHappy layoutSmileyHappy = new LayoutSmileyHappy(this);
        LayoutSmileySuperHappy layoutSmileySuperHappy = new LayoutSmileySuperHappy(this);
        mCurrentLinearLayout.addView(layoutSmileyHappy.getLinearLayoutHappy());
        layoutSmileySuperHappy.getParamsSmileySuperHappy().weight=4;
        mCurrentLinearLayout.addView(layoutSmileySuperHappy.getLinearLayoutSuperHappy());




    }
}
