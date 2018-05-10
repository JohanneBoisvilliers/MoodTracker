package com.boisvilliers.johanne.moodtracker.controller;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Johanne Boisvilliers on 03/05/2018.
 */
public class GestureDetectorListener extends GestureDetector.SimpleOnGestureListener {

    private static final float GESTURE_SIZE = 200;//the minimum lenght to activate the sliding event
    private static char gestureDirection;//return U or D for Up or Down slide. onTouchEvent in MainActivity takes this char for swapping views

    @Override
    public boolean onDown(MotionEvent e) {//The Override of onDown() is to ovoid multiple sliding after onFling :
        gestureDirection = ' ';           //reset the gestureDirection to always start with an empty gesture listener
        return super.onDown(e);           //when user start to touch the screen.
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

        float y1 = event1.getY();           //Get the start and end y coordinates of motion to calculate the lenght
        float y2 = event2.getY();           //between them and at the same time knowing if the gesture is up or
        float compareGestureSize = y1 - y2; //down. If the difference is negative, the movement is necessarily down
                                            //otherwise it's an up motion (we're ignoring x coordinates so left and right movements)

        if(event1.ACTION_DOWN == 0&& compareGestureSize>=GESTURE_SIZE) { gestureDirection = 'U'; }

        if (compareGestureSize<0 && (compareGestureSize*-1)>=GESTURE_SIZE ) { gestureDirection = 'D'; }
        return true;
    }
    //Getter for gestureDirection which onTouchEvent (in MainActivity) need to sliding views
    public static char getGestureDirection() {
        return gestureDirection;
    }
}
