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
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private View mCurrentFrameLayout;
    private ImageView mSmiley;
    private ImageButton mAddComments, mHistory, mPieChart;
    private GestureDetectorCompat mDetector;
    private int mCurrentView = 3;
    private Context mContext = this;
    private LayoutConstructor mMainHierarchy;
    private int[] mListColor, mListSmiley;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Calendar mDDay, mRefDay;
    private ArrayList<Integer> mThingsToSave, mThingsToLoad;
    private ArrayList<HistoryElements> mThingsToTransfer;
    private HistoryElements mTempMood;
    private boolean mCompareDate = true;
    private Gson gson;
    private int mDaysBetween;
    private String mFinalComment = null;
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
        mSharedPreferences = getPreferences(MODE_PRIVATE);
        mDetector = new GestureDetectorCompat(this, new GestureDetectorListener());
        mSmiley = new ImageView(this);
        mMainHierarchy = new LayoutConstructor(this);
        //initialize differents views
        mCurrentFrameLayout = findViewById(R.id.mainActivity_global);
        mCurrentFrameLayout = mMainHierarchy.getViewsHierarchy();
        mSmiley = mCurrentFrameLayout.findViewById(R.id.image_smiley);
        mAddComments = mCurrentFrameLayout.findViewById(R.id.Addcomments_button);
        mHistory = mCurrentFrameLayout.findViewById(R.id.History_button);
        mPieChart = mCurrentFrameLayout.findViewById(R.id.piechart_button);
        //get color's list and smiley's list
        mListColor = new ConstructList().getColorArray();
        mListSmiley = new ConstructList().getSmileyArray();
        checkDate();
        loadSharedPreferences();
        activeAndListenerOnPieChart();
        addListenerOnAddCommentsButton();
        addListenerOnHistoryButton();
    }

    //method who refresh the view thanks to mCurrentView ( mCurrentView is transforming to index of each list : mListColor and mListSmiley)
    public void refreshView(int index) {
        mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[index]));
        mSmiley.setImageResource(mListSmiley[index]);
        setContentView(mCurrentFrameLayout);
    }

    //add a listener on button to open the history of last week mood
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

    //add a listener on button to open a dialog window to let user add a comment about his day
    public void addListenerOnAddCommentsButton() {
        //Listener and actions for Addcomments button
        mAddComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextAddComments commentaries = new EditTextAddComments(mContext);
                final EditText commentToAdd = commentaries.getDialogComments().findViewById(R.id.edit_text_comment);
                //Set an alert dialog which contain the EditText to let a commentary
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(commentaries.getDialogComments());
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFinalComment = commentToAdd.getText().toString();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    public void activeAndListenerOnPieChart(){
        if(mSharedPreferences.contains(KEY_TRANSFER)){
            mPieChart.setVisibility(View.VISIBLE);
            mPieChart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pieChartIntent = new Intent(MainActivity.this, PieChartActivity.class);
                    pieChartIntent.putExtra(BUNDLE_MOODTOSAVE, mThingsToTransfer);
                    startActivity(pieChartIntent);
                }
            });
        }
    }

    //method to check the actual date and compare it to the last date when the app was opened
    public void checkDate() {
        mDDay = Calendar.getInstance();
        int mDDayWeek = mDDay.get(Calendar.WEEK_OF_YEAR);
        int mDDayDayWeek = mDDay.get(Calendar.DAY_OF_WEEK);

        if (mSharedPreferences.contains(KEY_REF_DATE)) {
            mRefDay = Calendar.getInstance();
            mRefDay.setTimeInMillis(mSharedPreferences.getLong(KEY_REF_DATE, 0L));
            int mRefWeek = mRefDay.get(Calendar.WEEK_OF_YEAR);
            int mRefDayWeek = mRefDay.get(Calendar.DAY_OF_WEEK);
            mDaysBetween = daysBetween(mRefDay, mDDay);
            if (mRefDayWeek == mDDayDayWeek && mRefWeek == mDDayWeek)
                mCompareDate = true;
            else
                mCompareDate = false;
            mRefDay = mDDay;
        } else {
            mRefDay = mDDay;
        }
    }

    //method to calculate the number of days between the actual day and the last date when the app was opened
    public int daysBetween(Calendar d1, Calendar d2) {
        return (int) ((d2.getTimeInMillis() - d1.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    /* save :  - the color and the smiley which are corresponding to the day's mood
               - the list of mood of the week (mThingsToTransfer) to transfer it to HistoryActivity
               - the mood of the day (mTempMood) to put it into mood history the next day
               - mRefDay which is the last date when app was open */
    public void saveSharedPreferences() {
        gson = new Gson();
        mThingsToSave = new ArrayList<>();
        mThingsToSave.add(getResources().getColor(mListColor[mCurrentView]));
        mThingsToSave.add(mListSmiley[mCurrentView]);
        mThingsToSave.add(mCurrentView);
        mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]), mCurrentView,mFinalComment);
        String jsonSave = gson.toJson(mThingsToSave);
        String jsonTransfer = gson.toJson(mThingsToTransfer);
        String jsonTemp = gson.toJson(mTempMood);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(KEY_VIEW, jsonSave).apply();
        mEditor.putString(KEY_TRANSFER, jsonTransfer).apply();
        mEditor.putString(KEY_TEMP_MOOD, jsonTemp).apply();
        mEditor.putLong(KEY_REF_DATE, mRefDay.getTimeInMillis()).apply();
    }

    /* load :  - the last color and smiley in SharedPreferences
               - the list of mood of the week (mThingsToTransfer) to transfer it to HistoryActivity
               - the mood of the day (mTempMood) and put it into mood history if we are another day than mRefDay
               - mRefDay which is the last date when app was open */
    public void loadSharedPreferences() {
        gson = new Gson();
        Type typeSave = new TypeToken<ArrayList<Integer>>() {}.getType();
        Type typeTransfer = new TypeToken<ArrayList<HistoryElements>>() {}.getType();
        Type typeTemp = new TypeToken<HistoryElements>() {}.getType();
        String jsonSave = mSharedPreferences.getString(KEY_VIEW, "");
        String jsonTransfer = mSharedPreferences.getString(KEY_TRANSFER, "");
        String jsonTemp = mSharedPreferences.getString(KEY_TEMP_MOOD, "");
        if (mSharedPreferences.contains(KEY_TRANSFER))//if there is a list of mood saved we load it, else we initialize the mood list
            mThingsToTransfer = gson.fromJson(jsonTransfer, typeTransfer);
        else
            mThingsToTransfer = new ArrayList<>();
        if (mSharedPreferences.contains(KEY_TEMP_MOOD))//if there is a mood saved we load it to push it into history, else we create one by default
            mTempMood = gson.fromJson(jsonTemp, typeTemp);
        else
            mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]), mCurrentView, mFinalComment);
        mThingsToLoad = new ArrayList<>();
        if (jsonSave.isEmpty())//Add the background color and the smiley by default if there is nothing in the shared mSharedPreferences(at the first opening)
            refreshView(mCurrentView);
        else if (!mCompareDate) {//if we are in a new day, app add the last days mood into history and show the default view
            addMoodIntoHistory();
            refreshView(mCurrentView);
        } else {//if we are in the same day, app is loading the last mood we saved and show it
            mThingsToLoad = gson.fromJson(jsonSave, typeSave);
            mCurrentView = mThingsToLoad.get(2);
            mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[mCurrentView]));
            mSmiley.setImageResource(mListSmiley[mCurrentView]);
            setContentView(mCurrentFrameLayout);
        }
    }

    /* method which add the mood of yesterday(mTempMood) into mood's history, if user hadn't open the app during few days, the method add mood by default for the days
   whose user didn't precise the mood to save */
    public void addMoodIntoHistory() {
        if (mThingsToTransfer.size() < 7 && mDaysBetween == 1) {
            mThingsToTransfer.add(mTempMood);
        } else {
            for (int i = 1; i <= mDaysBetween; i++) {
                if (mThingsToTransfer.size() < 7) {
                    mThingsToTransfer.add(mTempMood);
                    mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]), mCurrentView, mFinalComment);
                } else {
                    mThingsToTransfer.remove(0);
                    mThingsToTransfer.add(mTempMood);
                    mTempMood = new HistoryElements(getResources().getColor(mListColor[mCurrentView]), mCurrentView, mFinalComment);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        checkDate();
        loadSharedPreferences();
        super.onResume();
    }

    @Override
    protected void onPause() {
        checkDate();
        saveSharedPreferences();
        super.onPause();
    }
}