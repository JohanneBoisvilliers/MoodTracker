package com.boisvilliers.johanne.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.HistoryElements;
import com.boisvilliers.johanne.moodtracker.view.HistoryConstructor;

import java.util.ArrayList;
import java.util.Collections;

import static com.boisvilliers.johanne.moodtracker.controller.MainActivity.BUNDLE_MOODTOSAVE;

public class HistoryActivity extends AppCompatActivity {

    private ViewGroup mHistoryCurrentView;
    private FrameLayout mHierBg, mAvantHier, mTroisJours, mQuatreJours, mCinqJours, mSixJours, mSeptJours;
    private HistoryConstructor mHistoryConstructor;
    private ArrayList<HistoryElements> mMoodToPutInHistory;
    private FrameLayout[] mMoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryConstructor = new HistoryConstructor(this);
        mHistoryCurrentView = findViewById(R.id.history_main_view);
        mHistoryCurrentView = mHistoryConstructor.getHistoryHierarchy();
        mMoodToPutInHistory = new ArrayList<>();
        mMoodToPutInHistory = (ArrayList<HistoryElements>) getIntent().getSerializableExtra(BUNDLE_MOODTOSAVE);
        ArrayList<HistoryElements> invertedList = new ArrayList<>(mMoodToPutInHistory);
        Collections.reverse(invertedList);
        mMoodToPutInHistory=invertedList;
        Log.d("NBRE_ELEMENT_DANS_LIST", mMoodToPutInHistory.toString());

        fillMoodList();
        settingMoodList();

    }

    public void fillMoodList() {
        mHierBg = mHistoryCurrentView.findViewById(R.id.hier_bg);
        mAvantHier = mHistoryCurrentView.findViewById(R.id.avant_hier_bg);
        mTroisJours = mHistoryCurrentView.findViewById(R.id.trois_jours);
        mQuatreJours = mHistoryCurrentView.findViewById(R.id.quatre_jours);
        mCinqJours = mHistoryCurrentView.findViewById(R.id.cinq_jours);
        mSixJours = mHistoryCurrentView.findViewById(R.id.six_jours);
        mSeptJours = mHistoryCurrentView.findViewById(R.id.sept_jours);

        mMoodList = new FrameLayout[]{mHierBg, mAvantHier, mTroisJours, mQuatreJours, mCinqJours, mSixJours, mSeptJours};
    }

    public void settingMoodList() {
        if (mMoodToPutInHistory.size() < 1) {
            ViewGroup clearView = findViewById(R.id.history_main_view);
            clearView.removeAllViews();
            TextView alertMessageEmpty = new TextView(this);
            alertMessageEmpty.setText("History is empty, please come back to see tomorrow");
            clearView.addView(alertMessageEmpty);
            setContentView(clearView);
        } else {
            for (int i = 0; i < mMoodToPutInHistory.size(); i++) {
                mMoodList[i].setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT, mMoodToPutInHistory.get(i).getIndexForWeight()));
                mMoodList[i].setBackgroundColor(mMoodToPutInHistory.get(i).getColor());
            }
            setContentView(mHistoryCurrentView);
        }
    }
}