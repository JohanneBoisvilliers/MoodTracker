package com.boisvilliers.johanne.moodtracker.model;

import android.content.Context;

import com.boisvilliers.johanne.moodtracker.R;
import com.boisvilliers.johanne.moodtracker.view.LayoutConstructor;

import java.util.ArrayList;

/**
 * Created by Johanne Boisvilliers on 08/05/2018.
 */
public class ConstructList  {

    private Context mContext;
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
        }
    }


