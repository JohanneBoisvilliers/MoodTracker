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
import com.boisvilliers.johanne.moodtracker.view.EditTextAddComments;
import com.boisvilliers.johanne.moodtracker.view.LayoutConstructor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private View mCurrentFrameLayout;
    private ImageView mSmiley;
    private ImageButton mAddComments,mHistory;
    private GestureDetectorCompat mDetector;
    protected int mCurrentView = 3;
    private Context mContext = this;
    private LayoutConstructor mMainHierarchy;
    protected int[] mListColor,mListSmiley;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    protected ArrayList<Integer> mThingsToSave;
    private ArrayList<Integer> mThingsToLoad;
    public static final String KEY_VIEW = "KEY_VIEW";
    Gson gson;
    String json;

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
        mThingsToSave = new ArrayList<Integer>();
        mThingsToLoad = new ArrayList<Integer>();
        //initialize differents views
        mCurrentFrameLayout =findViewById(R.id.mainActivity_global);
        mCurrentFrameLayout =mMainHierarchy.getViewsHierarchy();
        mSmiley = mCurrentFrameLayout.findViewById(R.id.image_smiley);
        mAddComments = mCurrentFrameLayout.findViewById(R.id.Addcomments_button);
        mHistory = mCurrentFrameLayout.findViewById(R.id.History_button);
        //get color's list and smiley's list
        mListColor=new ConstructList().getColorArray();
        mListSmiley=new ConstructList().getSmileyArray();
        //try to get view saved in SharedPreferences
        loadSharedPreferences();
        Log.d("TAG","truc a save = " + json);
        addListenerOnAddCommentsButton();
        addListenerOnHistoryButton();
    }
    //method who refresh the view thanks to mCurrentView ( mCurrentView is transforming to index of each list : mListColor and mListSmiley)
    public void refreshView(int index){
        mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[index]));
        mSmiley.setImageResource(mListSmiley[index]);
        setContentView(mCurrentFrameLayout);
        mThingsToSave.clear();
        mThingsToSave.add(getResources().getColor(mListColor[mCurrentView]));
        mThingsToSave.add(mListSmiley[mCurrentView]);
        mThingsToSave.add(mCurrentView);
    }
    //add a listener on buttons to know when user clicked on them and to know what to do
    public void addListenerOnHistoryButton() {
        //listener and actions for History button
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
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
    //save the color and the smiley which are corresponding to the day's mood
    public void saveSharedPreferences(){
        gson = new Gson();
        json = gson.toJson(mThingsToSave);
        editor = preferences.edit();
        editor.putString(KEY_VIEW,json).apply();
    }
    //load the last color and smiley in SharedPreferences
    public void loadSharedPreferences(){
        gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        json=preferences.getString(KEY_VIEW,"");
        if(json.isEmpty())
            //Add the background color and the smiley by default if there is nothing in the shared preferences
            refreshView(mCurrentView);
        else {
            mThingsToLoad = gson.fromJson(json, type);
            mCurrentView= mThingsToLoad.get(2);
            mCurrentFrameLayout.setBackgroundColor(getResources().getColor(mListColor[mCurrentView]));
            mSmiley.setImageResource(mListSmiley[mCurrentView]);
            setContentView(mCurrentFrameLayout);
        }
    }

    @Override
    protected void onDestroy() {
        saveSharedPreferences();
        Log.d("TAG","truc a save = " + json);
        super.onDestroy();
    }
}
