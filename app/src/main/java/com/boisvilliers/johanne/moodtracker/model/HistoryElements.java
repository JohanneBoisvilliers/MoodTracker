package com.boisvilliers.johanne.moodtracker.model;

import java.io.Serializable;

/**
 * Created by Johanne Boisvilliers on 15/05/2018.
 */
public class HistoryElements implements Serializable {
    private int mColor;
    private int mIndexForWeight;

    public HistoryElements(int color, int indexForWeight) {
        this.mColor = color;
        this.mIndexForWeight = indexForWeight;
    }

    public int getColor() {
        return mColor;
    }

    public int getIndexForWeight() {
        return mIndexForWeight;
    }

}
