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
public class LayoutConstructor extends ViewGroup{

    protected LinearLayout mLinearLayout;
    protected LinearLayout.LayoutParams mParamsSmiley;
    protected ImageView smiley;

    public LayoutConstructor(Context context) {
        super(context);

    }

    protected void LayoutCreator(int color, int draw) {


        mLinearLayout = new LinearLayout(getContext());

        mParamsSmiley = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        mParamsSmiley.height=0;
        mParamsSmiley.weight=1;

        mLinearLayout.setLayoutParams(mParamsSmiley);
        mLinearLayout.setGravity(Gravity.CENTER);
        mLinearLayout.setBackgroundColor(color);

        smiley = new ImageView(getContext());
        smiley.setImageResource(draw);
        smiley.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        smiley.getLayoutParams().height=(int) getResources().getDimension(R.dimen.size_image_heigt);
        smiley.getLayoutParams().width=(int) getResources().getDimension(R.dimen.size_image_width);

        mLinearLayout.addView(smiley);
    }

    public LinearLayout getLinearLayout() {
        return this.mLinearLayout;
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
