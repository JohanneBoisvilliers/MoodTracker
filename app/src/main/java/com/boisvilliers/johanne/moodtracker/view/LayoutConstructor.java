package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public class LayoutConstructor extends ViewGroup{

    private View mViewsHierarchy;
    //Constructor which create view's hierarchy thank to inflate on activity_main.xml.
    public LayoutConstructor(Context context) {
        super(context);
        //Create all the view's hierarchy.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewsHierarchy = inflater.inflate(R.layout.activity_main,null);
    }

    public View getViewsHierarchy() {
        return mViewsHierarchy;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

}
