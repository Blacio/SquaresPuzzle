package com.blacio.squarespuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ScoreActivity extends Activity {

    TextView s5,s7,s10;
    TextView g5,g7,g10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        s5 = (TextView)findViewById(R.id.scoreFIVE);
        g5 = (TextView)findViewById(R.id.gamesFIVE);
        s7 = (TextView)findViewById(R.id.scoreSEVEN);
        g7 = (TextView)findViewById(R.id.gamesSEVEN);
        s10 = (TextView)findViewById(R.id.scoreTEN);
        g10 = (TextView)findViewById(R.id.gamesTEN);

        SharedPreferences sharedPrefr = getSharedPreferences("TAG", Context.MODE_PRIVATE);


        s10.setText("Score: " + sharedPrefr.getLong("aux",0));
        g10.setText("Games Played: " + sharedPrefr.getLong("games10",0));

        s7.setText("Score: " + sharedPrefr.getLong("aux7",0));
        g7.setText("Games Played: " + sharedPrefr.getLong("games7",0));

        s5.setText("Score: " + sharedPrefr.getLong("aux5",0));
        g5.setText("Games Played: " + sharedPrefr.getLong("games5",0));

    }


    public void FIVEGame(View view){
        Intent i  =new Intent(this,GameActivity5.class);
        startActivity(i);
    }

    public void SEVENGame(View view){
        Intent i  =new Intent(this,GameActivity7.class);
        startActivity(i);
    }

    public void TENGame(View view){
        Intent i  =new Intent(this,GameActivity.class);
        startActivity(i);
    }
}
