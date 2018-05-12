package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.view.HistoryConstructor;

public class HistoryActivity extends AppCompatActivity {

    private View mHistoryCurrentView;
    private HistoryConstructor mHistoryConstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryConstructor= new HistoryConstructor(this);

        mHistoryCurrentView=findViewById(R.id.history_main_view);
        mHistoryCurrentView= mHistoryConstructor.getHistoryHierarchy();
    }
}
