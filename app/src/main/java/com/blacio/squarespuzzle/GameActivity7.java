package com.blacio.squarespuzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class GameActivity7 extends Activity implements RewardedVideoAdListener{

    Functions obj;

    TableLayout tbl;
    TableRow row;
    Button b;
    Button reset, next;

    TextView score,time;
    private InterstitialAd InterAd;
    private RewardedVideoAd BonusAd;

    int sw,sw1;

    private boolean mStarted;
    private Handler mHandler;

    private long s,auxx;
    private long games,auxgames;

    private long timeStart,timeStop;

    private boolean touched;
    MediaPlayer m;
    AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game7);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        } else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        BonusAd = MobileAds.getRewardedVideoAdInstance(this);
        BonusAd.setRewardedVideoAdListener(this);
        loadAd();

        newAd();

        mStarted=false;
        mHandler = new Handler();
        time =(TextView)findViewById(R.id.chronometer);
        score = (TextView)findViewById(R.id.score);

        timeStart=0;timeStop=0;sw1=0;

        SharedPreferences sharedPrefr = getSharedPreferences("TAG", Context.MODE_PRIVATE);
        s = sharedPrefr.getLong("aux7",0);
        games = sharedPrefr.getLong("games7",0);

        if(sharedPrefr.getLong("aux7",0)!=s)
        {s=sharedPrefr.getLong("aux7",0);games++;}

        auxx=s;auxgames=games;

        sw = 0;
        touched=false;

        m = MediaPlayer.create(this,R.raw.clock_up);
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        tbl = (TableLayout) findViewById(R.id.tabel7);

        obj = new Functions(tbl,7,this);

        score.setText("Score:\n"+s);
        obj.set_startBackground(true);



        for (int i = 0; i < 7; i++) {
            row = (TableRow) tbl.getChildAt(i);
            for (int j = 0; j < 7; j++) {
                b = (Button) row.getChildAt(j);
                b.setTag(sw++);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        obj.doAction((Integer) v.getTag());

                        if (!touched) {
                            touched = true;
                            mRunnable.run();
                            mStarted = true;

                            if((games-1)%9==0)
                                newAd();
                        }
                    }
                });
            }
        }

        reset = (Button) findViewById(R.id.mainbutton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s-=7;
                mStarted=false;

                score.setText("Score:\n"+s);
                obj.set_startBackground(false);
                mStarted=true;
                mRunnable.run();
            }
        });


        next = (Button) findViewById(R.id.main2button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stop_game(true);
                score.setText("Score:\n"+s);
                obj.set_startBackground(true);

                if(games%9==0 && games!=0)
                    if(InterAd.isLoaded())
                        InterAd.show();
                    else {mStarted=true;
                        mRunnable.run();
                    }
                else {mStarted=true;
                    mRunnable.run();
                }
            }
        });


        InterAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                timeStart =  System.currentTimeMillis();
                obj.setT(timeStart-timeStop);

                mStarted=true;
                mRunnable.run();

                sw1=0;

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                mStarted = false;
                mHandler.removeCallbacks(mRunnable);
                timeStop =  System.currentTimeMillis();

                sw1=1;
            }
        });
    }


    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run(){
            if (mStarted) {
                long seconds = (System.currentTimeMillis() - obj.getT()) / 1000;
                time.setText("Time:\n" + String.format("%02d:%02d", seconds / 60, seconds % 60));
                if (seconds % 60 == 0 && seconds != 0 && audio.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                    m.start();
                mHandler.postDelayed(mRunnable, 1000L);
            }
        }

    };




    private void stop_game(boolean x){
        mStarted=false;

        if(touched) {

            long lostS = obj.calculate_score(s)-s;

            s = obj.calculate_score(s);
            games++;

            SharedPreferences sharedPref = getSharedPreferences("TAG", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putLong("scor7", s);
            editor.putLong("games7", games);
            editor.putLong("aux7", s);
            editor.apply();

            if(lostS<0 && x && BonusAd.isLoaded()) {

                mStarted = false;
                mHandler.removeCallbacks(mRunnable);
                timeStop =  System.currentTimeMillis();

                createDialog2();
            }

            touched = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStarted = true;
        mHandler.postDelayed(mRunnable, 1000L);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mStarted = false;
        mHandler.removeCallbacks(mRunnable);
        timeStop =  System.currentTimeMillis();

        if(touched) {

            SharedPreferences sharedPref = getSharedPreferences("TAG", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putLong("aux7", obj.calculate_score(s));
            editor.apply();
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        timeStart =  System.currentTimeMillis();
        obj.setT(timeStart-timeStop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop_game(false);
    }


    public void createDialog(long sc, long gm) {

        String str;

        if(sc<0) str = "Sorry...\nYou have lost "+sc+" points !\nGames played: "+gm;
        else if(sc>0) str = "CONGRATULATIONS\nYou have won "+sc+" points !\nGames played: "+gm;
        else str = "No points won or lost !\nGames played: "+gm;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Session Summary")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(str)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startIntent(0);
                    }
                })
                .setCancelable(false);

        builder.create().show();
    }

    public void createDialog2() {

        String str;

        str = getString(R.string.save100);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save your points")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(str)
                .setPositiveButton("Watch Video", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        showAd();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        score.setText("Score:\n"+s);
                        obj.set_startBackground(true);

                        mStarted=true;
                        mRunnable.run();

                    }
                })
                .setCancelable(false);

        builder.create().show();
    }

    private void startIntent(int x) {

        if(x==0) {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }
        else{

            Intent i = new Intent(this, RewardActivity.class);

            if (x==2) {

                SharedPreferences sharedPref = getSharedPreferences("TAG", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putLong("scor7", s + 100);
                editor.putLong("aux7", s + 100);
                editor.apply();

                i.putExtra("SAVED",2);

            }
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {

        if(sw1==1){
            timeStart =  System.currentTimeMillis();
            obj.setT(timeStart-timeStop);

            mStarted=true;
            mRunnable.run();

            sw1=0;
        }
        else{
            stop_game(false);
            createDialog(s - auxx, games - auxgames);
        }
    }


    private void newAd() {
        InterAd = new InterstitialAd(this);
        InterAd.setAdUnitId("***");
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("***")
                .build();

        InterAd.loadAd(request);
    }

    public void loadAd(){
        if(!BonusAd.isLoaded())
            BonusAd.loadAd("***",new AdRequest.Builder().build());
    }


    public void showAd(){
        if(BonusAd.isLoaded())
            BonusAd.show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        startIntent(1);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        startIntent(2);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }



}