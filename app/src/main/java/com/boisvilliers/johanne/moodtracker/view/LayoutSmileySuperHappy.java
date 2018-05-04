package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public class LayoutSmileySuperHappy extends LayoutConstructor {
    //call parent class LayoutConstructor and customize it with color background and image src
    public LayoutSmileySuperHappy(Context context) {
        super(context);
        this.LayoutCreator(getResources().getColor(R.color.banana_yellow),R.drawable.smiley_super_happy);
    }
}
