package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 12/05/2018.
 */
public class HistoryConstructor extends ViewGroup {

    private ViewGroup mHistoryHierarchy;

    public HistoryConstructor(Context context) {
        super(context);
        //Create all the History's hierarchy.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHistoryHierarchy = (ViewGroup) inflater.inflate(R.layout.activity_history,null);
    }

    public ViewGroup getHistoryHierarchy() { return mHistoryHierarchy; }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
