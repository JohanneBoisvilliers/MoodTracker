package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 12/05/2018.
 */
public class HistoryConstructor extends ViewGroup {

    private View mHistoryHierarchy;

    public HistoryConstructor(Context context) {
        super(context);
        //Create all the History's hierarchy.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHistoryHierarchy = inflater.inflate(R.layout.activity_history,null);
    }

    public View getHistoryHierarchy() { return mHistoryHierarchy; }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
