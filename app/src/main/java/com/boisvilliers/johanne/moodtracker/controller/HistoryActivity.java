package com.boisvilliers.johanne.moodtracker.controller;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.model.HistoryElements;
import com.boisvilliers.johanne.moodtracker.view.HistoryConstructor;

import java.util.ArrayList;
import java.util.Collections;

import static com.boisvilliers.johanne.moodtracker.controller.MainActivity.BUNDLE_MOOD_TO_SAVE;

public class HistoryActivity extends AppCompatActivity {

    private ViewGroup mHistoryCurrentView;
    private FrameLayout mHierBg, mAvantHier, mTroisJours, mQuatreJours, mCinqJours, mSixJours, mSeptJours;
    private ImageButton mButtonhHier,mButtonAvHier,mButtonTroisJours,mButtonQuatreJours,mButtonCinqJours,mButtonSixJours,mButtonSeptJours;
    private HistoryConstructor mHistoryConstructor;
    private ArrayList<HistoryElements> mMoodToPutInHistory;
    private FrameLayout[] mMoodList;
    private ImageButton[] mButtonList;
    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryConstructor = new HistoryConstructor(this); // Inflate the activity_history.xml
        mHistoryCurrentView = findViewById(R.id.history_main_view); // Connect the Main Layout
        mHistoryCurrentView = mHistoryConstructor.getHistoryHierarchy(); // Setting the Main layout with activity_history.xml inflated
        mMoodToPutInHistory = new ArrayList<>();
        mMoodToPutInHistory = (ArrayList<HistoryElements>) getIntent().getSerializableExtra(BUNDLE_MOOD_TO_SAVE); // get the list of moods of last week thanks to intent
        ArrayList<HistoryElements> invertedList = new ArrayList<>(mMoodToPutInHistory);
        Collections.reverse(invertedList);//we reverse the list to have the yesterday's mood in bottom of list and so in bottom of history
        mMoodToPutInHistory=invertedList;

        fillMoodList();
        fillButtonsList();
        settingMoodList();
    }

    //method to connect xml views and put them into an array
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
    //method to connect xml buttons and put them into an array
    public void fillButtonsList(){
        mButtonhHier = mHistoryCurrentView.findViewById(R.id.btn_hier);
        mButtonAvHier = mHistoryCurrentView.findViewById(R.id.btn_avant_hier);
        mButtonTroisJours = mHistoryCurrentView.findViewById(R.id.btn_trois_jour);
        mButtonQuatreJours = mHistoryCurrentView.findViewById(R.id.btn_quatre_jour);
        mButtonCinqJours = mHistoryCurrentView.findViewById(R.id.btn_cinq_jour);
        mButtonSixJours = mHistoryCurrentView.findViewById(R.id.btn_six_jour);
        mButtonSeptJours = mHistoryCurrentView.findViewById(R.id.btn_une_semaine);

        mButtonList = new ImageButton[]{mButtonhHier, mButtonAvHier, mButtonTroisJours, mButtonQuatreJours, mButtonCinqJours, mButtonSixJours, mButtonSeptJours};
    }
    //create the mood history thanks to mMoodToPutInHistory which contains all the mood transferred from MainActivity
    public void settingMoodList() {
        if (mMoodToPutInHistory.size() < 1) { //if there is no history, a message appear to invite the user to comeback next days
            ViewGroup clearView = findViewById(R.id.history_main_view);
            clearView.removeAllViews();
            TextView alertMessageEmpty = new TextView(this);
            alertMessageEmpty.setText("History is empty, please come back to see tomorrow");
            clearView.addView(alertMessageEmpty);
            setContentView(clearView);
        } else { //else for every elements into the list, we take color and index and setting the mood history
            for (int i = 0; i < mMoodToPutInHistory.size(); i++) {
                Display display = getWindowManager().getDefaultDisplay();//Catch the screen to set moods width in history
                Point size = new Point();
                display.getSize(size);
                int width = (size.x)/5;
                mMoodList[i].setLayoutParams(new LinearLayout.LayoutParams((mMoodToPutInHistory.get(i).getIndex()+1)*width, FrameLayout.LayoutParams.MATCH_PARENT));
                mMoodList[i].setBackgroundColor(mMoodToPutInHistory.get(i).getColor());
                final int current = i;
                if(mMoodToPutInHistory.get(i).getComment()!= null){ //if user had left a comment this day, we set the comment icon visible
                    mButtonList[i].setVisibility(View.VISIBLE);
                    mButtonList[i].setOnClickListener(new View.OnClickListener() { //if user press the icon, a toast message appear and show the message left this day
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext,mMoodToPutInHistory.get(current).getComment(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                setContentView(mHistoryCurrentView);
            }
        }
    }
}