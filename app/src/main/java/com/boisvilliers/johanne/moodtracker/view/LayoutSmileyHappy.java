package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 01/05/2018.
 */
public class LayoutSmileyHappy extends LayoutConstructor {

    public LayoutSmileyHappy(Context context) {
        super(context);
        this.LayoutCreator(getResources().getColor(R.color.light_sage),R.drawable.smiley_happy);
    }

}

