package com.boisvilliers.johanne.moodtracker.model;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 08/05/2018.
 */
public class ConstructList  {

    private int[] mSmileyArray;
    private int[] mColorArray;

    public ConstructList() {
        mSmileyArray =new int[]{R.drawable.smiley_sad,R.drawable.smiley_disappointed,R.drawable.smiley_normal,R.drawable.smiley_happy,R.drawable.smiley_super_happy};
        mColorArray =new int[]{R.color.faded_red,R.color.warm_grey,R.color.cornflower_blue_65,R.color.light_sage,R.color.banana_yellow};
    }

    public int[] getSmileyArray() {
        return mSmileyArray;
    }

    public int[] getColorArray() {
        return mColorArray;
    }

    /*private Context mContext;
    private ArrayList<LayoutConstructor> mListLayout=new ArrayList<>();

        //Constructor who create the view's hierarchy as list
        public ConstructList(Context current){
            this.mContext=current;
            mListLayout.add(new LayoutConstructor(mContext,mContext.getResources().getColor(R.color.faded_red),R.drawable.smiley_sad));
            mListLayout.add(new LayoutConstructor(mContext,mContext.getResources().getColor(R.color.warm_grey),R.drawable.smiley_disappointed));
            mListLayout.add(new LayoutConstructor(mContext,mContext.getResources().getColor(R.color.cornflower_blue_65),R.drawable.smiley_normal));
            mListLayout.add(new LayoutConstructor(mContext,mContext.getResources().getColor(R.color.light_sage),R.drawable.smiley_happy));
            mListLayout.add(new LayoutConstructor(mContext,mContext.getResources().getColor(R.color.banana_yellow),R.drawable.smiley_super_happy));
        }

        public ArrayList<LayoutConstructor> getListLayout() {
            return mListLayout;
        }*/
    }


