package com.boisvilliers.johanne.moodtracker.model;

import java.io.Serializable;

/**
 * Class which create an object to save the color and an index (which is the position of the mood into the list and later this index will be the weight of the mood's view into the history)
 */
public class HistoryElements implements Serializable {
    private int mColor;
    private int mIndex;
    private String mComment;

    public HistoryElements(int color, int pIndex, String comment) {
        this.mColor = color;
        this.mIndex = pIndex;//mood list index in HistoryActivity(whose this index come from) begin at 0 and finish at 4 while future weight is between 1 to 5 so we add 1 to the index when we're calling it
        this.mComment = comment;
    }

    public int getColor() {
        return mColor;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getComment() { return mComment; }
}
