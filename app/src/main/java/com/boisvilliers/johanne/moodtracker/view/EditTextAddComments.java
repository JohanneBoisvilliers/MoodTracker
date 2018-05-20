package com.boisvilliers.johanne.moodtracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boisvilliers.johanne.moodtracker.R;

/**
 * Create the alertDialog to let user add a comment about his day
 */
public class EditTextAddComments  extends ViewGroup{
    private View mDialogComments;

    public EditTextAddComments(Context context){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogComments =  inflater.inflate(R.layout.dialog_addcomments,null);
    }

    public View getDialogComments() {
        return mDialogComments;
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
