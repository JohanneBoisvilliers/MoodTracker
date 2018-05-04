package com.boisvilliers.johanne.moodtracker.model;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Johanne Boisvilliers on 03/05/2018.
 */
public class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
    private static final String DEBUG_TAG = "Gestures";
    private static final float GESTURE_SIZE = 200;
    private static char gestureDirection;

    @Override
    public boolean onDown(MotionEvent e) {
        gestureDirection = ' ';
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

        float y1 = event1.getY();
        float y2 = event2.getY();
        float compareGestureSize = y1 - y2;
        Boolean returnValue = false;

        if(event1.ACTION_DOWN == 0&& compareGestureSize>=GESTURE_SIZE) {
            gestureDirection = 'U';
            Log.d(DEBUG_TAG, "onFling:mouvement vers le haut" + y1 + "///" + y2 +" "+ gestureDirection+" ;");
            returnValue = true;
        }

        if (compareGestureSize<0 && (compareGestureSize*-1)>=GESTURE_SIZE ) {
            gestureDirection = 'D';
            Log.d(DEBUG_TAG, "onFling:mouvement vers le bas"+y1+y2 +" "+ gestureDirection+" ;");
            returnValue = true;
        }
        return returnValue;
    }

    public static char getGestureDirection() {
        return gestureDirection;
    }
}
