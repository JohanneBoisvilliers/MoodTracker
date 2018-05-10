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
    //Constructor who create a FrameLayout containing an ImageView with all their parameters, just 2 parameters are customizable
    //by children : color background and image
    public LayoutConstructor(Context context) {
        super(context);
        //Create a FrameLayout who contain a color background and an ImageView for Smileys
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
