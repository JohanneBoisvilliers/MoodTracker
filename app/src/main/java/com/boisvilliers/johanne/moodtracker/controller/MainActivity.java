package com.boisvilliers.johanne.moodtracker.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.boisvilliers.johanne.moodtracker.model.HistoryElements;
import com.boisvilliers.johanne.moodtracker.view.EditTextAddComments;
import com.boisvilliers.johanne.moodtracker.view.LayoutConstructor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private View mCurrentFrameLayout;
    private ImageView mSmiley;
    private ImageButton mAddComments, mHistory;
    private GestureDetectorCompat mDetector;
    protected int mCurrentView = 3;
    private Context mContext = this;
    private LayoutConstructor mMainHierarchy;
    private int[] mListColor, mListSmiley;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Calendar mDDay, mRefDay;
    private ArrayList<Integer> mThingsToSave, mThingsToLoad;
    private ArrayList<HistoryElements> mThingsToTransfer;
    private HistoryElements mTempMood;
    private boolean mCompareDate = true;
    private Gson gson;
    private int mDaysBetween;
    public static final String KEY_VIEW = "KEY_VIEW";
    public static final String KEY_REF_DATE = "KEY_REF_DATE";
    public static final String KEY_TRANSFER = "KEY_TRANSFER";
    public static final String BUNDLE_MOODTOSAVE = "BUNDLE_MOODTOSAVE";
    public static final String KEY_TEMP_MOOD = "KEY_TEMP_MOOD";


    /*OnTouchEvent get the gestureDirection in GestureDetectorListener and then add or remove 1 to mCurrentView
     which contain the index of the current smiley and background color.
    After that, refresh the view with refreshView method.*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        char gestureDirection = GestureDetectorListener.getGestureDirection();
        if (gestureDirection == 'U') {
            if (mCurrentView < 4) {
                int viewToReduce = mCurrentView;
                mCurrentView += 1;
                refreshView(mCurrentView);
            }
        }
        if (gestureDirection == 'D') {
            if (mCurrentView > 0) {
                mCurrentView -= 1;
                refreshView(mCurrentView);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        mDetector = new GestureDetectorCompat(this, new GestureDetectorListener());
        mSmiley = new ImageView(this);
        mMainHierarchy = new LayoutConstructor(this);
        //initialize differents views
        mCurrentFrameLayout = findViewById(R.id.mainActivity_global);
        mCurrentFrameLayout = mMainHierarchy.getViewsHierarchy();
        mSmiley = mCurrentFrameLayout.findViewById(R.id.image_smiley);
        mAddComments = mCurrentFrameLayout.findViewById(R.id.Addcomments_button);
        mHistory = mCurrentFrameLayout.findViewById(R.id.History_button);
        //get color's list and smiley's list
        mListColor = new ConstructList().getColorArray();
        mListSmiley = new ConstructList().getSmileyArray();
        //check the actual date ton compare it to the previous app's opening date
        checkDate();
        //try to get view saved in SharedPreferences
        loadSharedPreferences();
        //add listener on buttons on bottom of view. One for open a dialog window to let user add a comment about his day
        //and another to open the history of last week mood
        addListenerOnAddCommentsButton();
        addListenerOnHistoryButton();
    }

    //method who refresh the view thanks to mCurrentView ( mCurrentView is transforming to index of each list : mListColor and mListSmiley)
    public void refreshView(int index) {
        mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[index]));
        mSmiley.setImageResource(mListSmiley[index]);
        setContentView(mCurrentFrameLayout);
    }

    //add a listener on buttons to know when user clicked on them and to know what to do
    public void addListenerOnHistoryButton() {
        //listener and actions for History button
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                historyActivity.putExtra(BUNDLE_MOODTOSAVE, mThingsToTransfer);
                startActivity(historyActivity);
            }
        });
    }

    public void addListenerOnAddCommentsButton() {
        //Listener and actions for Addcomments button
        mAddComments.setOnClickListener(new View.OnClickListener() {
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

    public void checkDate() {
        mDDay = Calendar.getInstance();
        int mDDayWeek = mDDay.get(Calendar.WEEK_OF_YEAR);
        int mDDayDayWeek = mDDay.get(Calendar.DAY_OF_WEEK);

        if (preferences.contains(KEY_REF_DATE)) {
            mRefDay = Calendar.getInstance();
            mRefDay.setTimeInMillis(preferences.getLong(KEY_REF_DATE, 0L));
            int mRefWeek = mRefDay.get(Calendar.WEEK_OF_YEAR);
            int mRefDayWeek = mRefDay.get(Calendar.DAY_OF_WEEK);
            mDaysBetween = daysBetween(mRefDay.getTime(),mDDay.getTime());
            if (mRefDayWeek == mDDayDayWeek && mRefWeek == mDDayWeek)
                mCompareDate = true;
            else
                mCompareDate = false;
            mRefDay = mDDay;
        } else {
            mRefDay = mDDay;
        }
    }
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    //save the color and the smiley which are corresponding to the day's mood
    public void saveSharedPreferences() {
        gson = new Gson();
        mThingsToSave = new ArrayList<>();
        mThingsToSave.add(getResources().getColor(mListColor[mCurrentView]));
        mThingsToSave.add(mListSmiley[mCurrentView]);
        mThingsToSave.add(mCurrentView);
        mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]),mCurrentView);
        String jsonSave = gson.toJson(mThingsToSave);
        String jsonTransfer = gson.toJson(mThingsToTransfer);
        String jsonTemp = gson.toJson(mTempMood);
        editor = preferences.edit();
        editor.putString(KEY_VIEW, jsonSave).apply();
        editor.putString(KEY_TRANSFER, jsonTransfer).apply();
        editor.putString(KEY_TEMP_MOOD, jsonTemp).apply();
        editor.putLong(KEY_REF_DATE, mRefDay.getTimeInMillis()).apply();
    }

    //load the last color and smiley in SharedPreferences
    public void loadSharedPreferences() {
        gson = new Gson();
        Type typeSave = new TypeToken<ArrayList<Integer>>() {}.getType();
        Type typeTransfer = new TypeToken<ArrayList<HistoryElements>>() {}.getType();
        Type typeTemp = new TypeToken<HistoryElements>() {}.getType();
        String jsonSave = preferences.getString(KEY_VIEW, "");
        String jsonTransfer = preferences.getString(KEY_TRANSFER, "");
        String jsonTemp = preferences.getString(KEY_TEMP_MOOD, "");
        if (preferences.contains(KEY_TRANSFER))
            mThingsToTransfer = gson.fromJson(jsonTransfer, typeTransfer);
        else
            mThingsToTransfer = new ArrayList<>();
        if (preferences.contains(KEY_TEMP_MOOD))
            mTempMood = gson.fromJson(jsonTemp,typeTemp);
        else
            mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]),mCurrentView);
        mThingsToLoad = new ArrayList<>();
        if (jsonSave.isEmpty())
            //Add the background color and the smiley by default if there is nothing in the shared preferences
            refreshView(mCurrentView);
        else if (!mCompareDate) {
            if(mThingsToTransfer.size()<7)
                mThingsToTransfer.add(mTempMood);
            else{
                mThingsToTransfer.remove(0);
                mThingsToTransfer.add(mTempMood);
            }
            refreshView(mCurrentView);
        } else {
            mThingsToLoad = gson.fromJson(jsonSave, typeSave);
            mCurrentView = mThingsToLoad.get(2);
            mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[mCurrentView]));
            mSmiley.setImageResource(mListSmiley[mCurrentView]);
            setContentView(mCurrentFrameLayout);
        }
    }

    @Override
    protected void onResume() {
        checkDate();
        loadSharedPreferences();
        System.out.println("MainActivity :: onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        checkDate();
        saveSharedPreferences();
        System.out.println("MainActivity :: onPause()");
        super.onPause();
    }
}