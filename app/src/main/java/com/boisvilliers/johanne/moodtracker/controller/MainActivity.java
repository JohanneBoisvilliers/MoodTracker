package com.boisvilliers.johanne.moodtracker.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    private View mCurrentFrameLayout;
    private ImageView mSmiley;
    private ImageButton mAddComments,mHistory;
    private GestureDetectorCompat mDetector;
    protected int mCurrentView = 3;
    private Context mContext = this;
    private LayoutConstructor mMainHierarchy;
    private int[] mListColor,mListSmiley;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private Calendar mDDay,mRefDay;
    private ArrayList<Integer> mThingsToSave, mThingsToLoad;
    private ArrayList<HistoryElements>mThingsToTransfer;
    public static final String KEY_VIEW = "KEY_VIEW";
    public static final String KEY_REF_DATE = "KEY_REF_DATE";
    public static final String KEY_TRANSFER = "KEY_TRANSFER";
    private boolean mCompareDate = false;
    private Gson gson;

    /*OnTouchEvent get the gestureDirection in GestureDetectorListener and then add or remove 1 to mCurrentView
     which contain the index of the current smiley and background color.
    After that, refresh the view with refreshView method.*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        char gestureDirection = GestureDetectorListener.getGestureDirection();
        if (gestureDirection =='U'){
            if(mCurrentView <4){
                int viewToReduce = mCurrentView;
                mCurrentView +=1;
                refreshView(mCurrentView);
            }
        }
        if (gestureDirection =='D'){
            if(mCurrentView >0){
                mCurrentView -=1;
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
        mDetector = new GestureDetectorCompat(this,new GestureDetectorListener());
        mSmiley=new ImageView(this);
        mMainHierarchy=new LayoutConstructor(this);
        mThingsToTransfer = new ArrayList<>();
        //initialize differents views
        mCurrentFrameLayout =findViewById(R.id.mainActivity_global);
        mCurrentFrameLayout =mMainHierarchy.getViewsHierarchy();
        mSmiley = mCurrentFrameLayout.findViewById(R.id.image_smiley);
        mAddComments = mCurrentFrameLayout.findViewById(R.id.Addcomments_button);
        mHistory = mCurrentFrameLayout.findViewById(R.id.History_button);
        //get color's list and smiley's list
        mListColor=new ConstructList().getColorArray();
        mListSmiley=new ConstructList().getSmileyArray();
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
    public void refreshView(int index){
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
                historyActivity.putExtra("MOOD_TO_SAVE",mThingsToTransfer);
                startActivity(historyActivity);
            }
        });
    }
    public void addListenerOnAddCommentsButton() {
        //Listener and actions for Addcomments button
        mAddComments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Set an alert dialog which contain the EditText to let a commentary
                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
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

    public void checkDate(){
        mDDay = Calendar.getInstance();
        int mDDayWeek =mDDay.get(Calendar.WEEK_OF_YEAR);
        int mDDayDayWeek=mDDay.get(Calendar.DAY_OF_WEEK);

        if(preferences.contains(KEY_REF_DATE)){
            mRefDay=Calendar.getInstance();
            mRefDay.setTimeInMillis(preferences.getLong(KEY_REF_DATE,0L));
            int mRefWeek =mRefDay.get(Calendar.WEEK_OF_YEAR);
            int mRefDayWeek=mRefDay.get(Calendar.DAY_OF_WEEK);
            if (mRefDayWeek==mDDayDayWeek && mRefWeek==mDDayWeek )
                mCompareDate=true;
            else
                mCompareDate=false;
            mRefDay=mDDay;
        }

        else{ mRefDay=mDDay; }
    }

    //save the color and the smiley which are corresponding to the day's mood
    public void saveSharedPreferences(){
        gson = new Gson();
        mThingsToSave = new ArrayList<>();
        mThingsToSave.add(getResources().getColor(mListColor[mCurrentView]));
        mThingsToSave.add(mListSmiley[mCurrentView]);
        mThingsToSave.add(mCurrentView);
        mThingsToTransfer.add(new HistoryElements(getResources().getColor(mListColor[mCurrentView]),mCurrentView));
        String json = gson.toJson(mThingsToSave);
        String jsonBis = gson.toJson(mThingsToTransfer);
        editor = preferences.edit();
        editor.putString(KEY_VIEW,json).apply();
        editor.putString(KEY_TRANSFER,jsonBis).apply();
        editor.putLong(KEY_REF_DATE,mRefDay.getTimeInMillis()).apply();
    }
    //load the last color and smiley in SharedPreferences
    public void loadSharedPreferences(){
        gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        Type typeBis = new TypeToken<ArrayList<HistoryElements>>(){}.getType();
        String json = preferences.getString(KEY_VIEW,"");
        String jsonBis = preferences.getString(KEY_TRANSFER,"");
        mThingsToLoad = new ArrayList<>();
        mThingsToTransfer = gson.fromJson(jsonBis,typeBis);
        if(mThingsToTransfer!=null)
        Log.d("SIZE_LIST",mThingsToTransfer.toString());
        if(json.isEmpty())
            //Add the background color and the smiley by default if there is nothing in the shared preferences
            refreshView(mCurrentView);
        else if(mCompareDate==false){
            refreshView(mCurrentView);
        }
        else {
            mThingsToLoad = gson.fromJson(json, type);
            mCurrentView= mThingsToLoad.get(2);
            mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[mCurrentView]));
            mSmiley.setImageResource(mListSmiley[mCurrentView]);
            setContentView(mCurrentFrameLayout);
        }
    }

    @Override
    protected void onRestart() {
        loadSharedPreferences();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        saveSharedPreferences();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferences();
        super.onDestroy();
    }
}
