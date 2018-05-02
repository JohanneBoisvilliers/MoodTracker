package com.boisvilliers.johanne.moodtracker.vue;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Created by Johanne Boisvilliers on 02/05/2018.
 */
public class LayoutSmileySuperHappy extends ViewGroup {

    private LinearLayout mLinearLayoutSuperHappy;
    private LinearLayout.LayoutParams mParamsSmileySuperHappy;
    private ImageView smileySuperHappy;

    public LayoutSmileySuperHappy(Context context) {
        super(context);

        mLinearLayoutSuperHappy = new LinearLayout(context);

        mParamsSmileySuperHappy = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mParamsSmileySuperHappy.height=0;
        mParamsSmileySuperHappy.weight=1;

        mLinearLayoutSuperHappy.setLayoutParams(mParamsSmileySuperHappy);
        mLinearLayoutSuperHappy.setGravity(Gravity.CENTER);
        mLinearLayoutSuperHappy.setBackgroundColor(getResources().getColor(R.color.banana_yellow));

        smileySuperHappy = new ImageView(context);
        smileySuperHappy.setImageResource(R.drawable.smiley_super_happy);
        smileySuperHappy.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        smileySuperHappy.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
        smileySuperHappy.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);

        mLinearLayoutSuperHappy.addView(smileySuperHappy);

    }

    public LinearLayout getLinearLayoutSuperHappy() {
        return mLinearLayoutSuperHappy;
    }

    public LinearLayout.LayoutParams getParamsSmileySuperHappy() {
        return mParamsSmileySuperHappy;
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
