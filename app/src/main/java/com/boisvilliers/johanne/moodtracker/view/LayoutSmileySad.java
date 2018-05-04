package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 04/05/2018.
 */
public class LayoutSmileySad extends LayoutConstructor {
    //call parent class LayoutConstructor and customize it with color background and image src
    public LayoutSmileySad(Context context) {
        super(context);
        this.LayoutCreator(getResources().getColor(R.color.faded_red),R.drawable.smiley_sad);
    }
}
