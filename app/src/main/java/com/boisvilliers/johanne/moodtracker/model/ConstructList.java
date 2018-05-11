package com.boisvilliers.johanne.moodtracker.model;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 08/05/2018.
 */
public class ConstructList  {

    private int[] mSmileyArray;
    private int[] mColorArray;
    /*Constructor which create 2 arrays, one for keeping all the smileys and the other all the colors. Both array are sorted for each element
     is corresponding to the other element's array at the same index(mSmileyArray[1] is matching mColorArray[1]*/
    public ConstructList() {
        mSmileyArray =new int[]{R.drawable.smiley_sad,R.drawable.smiley_disappointed,R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};
        mColorArray =new int[]{R.color.faded_red,R.color.warm_grey,R.color.cornflower_blue_65,R.color.light_sage,R.color.banana_yellow};
    }
    //getter for return smiley array
    public int[] getSmileyArray() {
        return mSmileyArray;
    }
    //getter for return color array
    public int[] getColorArray() {
        return mColorArray;
    }
}


