package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 04/05/2018.
 */
public class LayoutSmileyDissapointed extends LayoutConstructor {

    public LayoutSmileyDissapointed(Context context) {
        super(context);
        this.LayoutCreator(getResources().getColor(R.color.warm_grey),R.drawable.smiley_disappointed);
    }
}
