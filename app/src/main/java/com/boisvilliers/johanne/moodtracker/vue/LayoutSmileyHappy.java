package com.boisvilliers.johanne.moodtracker.vue;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 01/05/2018.
 */
public class LayoutSmileyHappy extends ViewGroup {

    private LinearLayout mLinearLayoutHappy;
    private LinearLayout.LayoutParams mParamsSmileyHappy;
    private ImageView smileyHappy;

    public LayoutSmileyHappy(Context context) {
        super(context);

        mLinearLayoutHappy = new LinearLayout(context);

        mParamsSmileyHappy = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mParamsSmileyHappy.height=0;
        mParamsSmileyHappy.weight=1;

        mLinearLayoutHappy.setLayoutParams(mParamsSmileyHappy);
        mLinearLayoutHappy.setGravity(Gravity.CENTER);
        mLinearLayoutHappy.setBackgroundColor(getResources().getColor(R.color.light_sage));

        smileyHappy = new ImageView(context);
        smileyHappy.setImageResource(R.drawable.smiley_happy);
        smileyHappy.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        smileyHappy.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
        smileyHappy.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);

        mLinearLayoutHappy.addView(smileyHappy);
    }

    public LinearLayout getLinearLayoutHappy() {
        return mLinearLayoutHappy;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.layout(l, t, r, b);
        }
    }
}

