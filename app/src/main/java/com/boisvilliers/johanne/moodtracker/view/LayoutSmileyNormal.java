package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 04/05/2018.
 */
public class LayoutSmileyNormal extends LayoutConstructor {
    //call parent class LayoutConstructor and customize it with color background and image src
    public LayoutSmileyNormal(Context context) {
        super(context);
        this.LayoutCreator(getResources().getColor(R.color.cornflower_blue_65),R.drawable.smiley_normal);
    }
}
