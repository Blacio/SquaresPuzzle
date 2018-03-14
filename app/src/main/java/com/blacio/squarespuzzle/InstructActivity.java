package com.blacio.squarespuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class InstructActivity extends Activity  implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private ViewFlipper flip;
    private GestureDetector detector;

    private static int x;

    private Animation leftIn;
    private Animation leftOut;
    private Animation rightIn;
    private Animation rightOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruct);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        flip = (ViewFlipper) findViewById(R.id.instr_flipper);

        detector = new GestureDetector(this,this);

        x=1;

        leftIn = AnimationUtils.loadAnimation(this,R.anim.left_in);
        leftOut = AnimationUtils.loadAnimation(this,R.anim.left_out);
        rightIn = AnimationUtils.loadAnimation(this,R.anim.right_in);
        rightOut = AnimationUtils.loadAnimation(this,R.anim.right_out);

    }

    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if(Math.abs(e1.getY()-e2.getY())>250) return false;

        if(e1.getX()-e2.getX()>120 && Math.abs(velocityX)>200){
            if(x!=4) {
                flip.setInAnimation(leftIn);
                flip.setOutAnimation(leftOut);
                flip.showNext();
                x++;
            }
        }

        else if(e2.getX()-e1.getX()>120 && Math.abs(velocityX)>200){
            if(x!=1) {
                flip.setInAnimation(rightIn);
                flip.setOutAnimation(rightOut);
                flip.showPrevious();
                x--;
            }
        }

        return false;
    }
}