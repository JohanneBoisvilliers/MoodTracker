package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.HistoryElements;
import com.boisvilliers.johanne.moodtracker.view.HistoryConstructor;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private View mHistoryCurrentView;
    private FrameLayout mHierBg, mAvantHier, mTroisJours, mQuatreJours, mCinqJours, mSixJours, mSeptJours;
    private HistoryConstructor mHistoryConstructor;
    private ArrayList<HistoryElements> mMoodToPutInHistory;
    private View[] mMoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryConstructor = new HistoryConstructor(this);
        mHistoryCurrentView = findViewById(R.id.history_main_view);
        mHistoryCurrentView = mHistoryConstructor.getHistoryHierarchy();
        mMoodToPutInHistory = new ArrayList<>();
        mMoodToPutInHistory =(ArrayList<HistoryElements>)getIntent().getSerializableExtra("MOOD_TO_SAVE");

        fillMoodList();
        mMoodList[0].setBackgroundColor(mMoodToPutInHistory.get(0).getColor());
        mMoodList[1].setBackgroundColor(mMoodToPutInHistory.get(1).getColor());

        setContentView(mHistoryCurrentView);

    }

    public void fillMoodList(){
        mHierBg = mHistoryCurrentView.findViewById(R.id.hier_bg);
        mAvantHier = mHistoryCurrentView.findViewById(R.id.avant_hier_bg);
        mTroisJours = mHistoryCurrentView.findViewById(R.id.trois_jours);
        mQuatreJours = mHistoryCurrentView.findViewById(R.id.quatre_jours);
        mCinqJours = mHistoryCurrentView.findViewById(R.id.cinq_jours);
        mSixJours = mHistoryCurrentView.findViewById(R.id.six_jours);
        mSeptJours = mHistoryCurrentView.findViewById(R.id.sept_jours);

        mMoodList = new View[]{mHierBg,mAvantHier,mTroisJours,mQuatreJours,mCinqJours,mSixJours,mSeptJours};
    }
}
