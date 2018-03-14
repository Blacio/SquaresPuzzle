package com.blacio.squarespuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class RewardActivity extends AppCompatActivity {

    TextView tv;

    int l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv = (TextView)findViewById(R.id.ptsaved);

        l = getIntent().getIntExtra("SAVED",0);

        switch(l) {
            case 1: {tv.setText(R.string.well150);break;}
            case 2: {tv.setText(R.string.well100);break;}
            case 3: {tv.setText(R.string.well75);break;}
            default: {tv.setText(R.string.nopoints);break;}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            switch(l) {
                case 1: {Intent int1 = new Intent(this,GameActivity.class);
                    startActivity(int1);break;}
                case 2: {Intent int2 = new Intent(this,GameActivity7.class);
                    startActivity(int2);break;}
                case 3: {Intent int3 = new Intent(this,GameActivity5.class);
                    startActivity(int3);break;}
                default:{Intent int4 = new Intent(this,MenuActivity.class);
                    startActivity(int4);break;}
            }
        }
        return super.onTouchEvent(event);
    }


}
