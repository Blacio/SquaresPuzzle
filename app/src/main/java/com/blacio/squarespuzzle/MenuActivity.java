package com.blacio.squarespuzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.FirebaseDatabase;

public class MenuActivity extends Activity {

    Button b;
    private String[] index,index2;
    boolean bol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch(Exception e){}


        ConnectivityManager cm =(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        SharedPreferences sharedPrefr = getSharedPreferences("FIRST_LAUNCH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefr.edit();

        bol = sharedPrefr.getBoolean("bool",true);

        if(bol) {
            create_name_dialog(getString(R.string.ask_country), "country_world");
            create_name_dialog(getString(R.string.ask_name), "name_world");

            editor.putBoolean("bool", false);
            editor.apply();
        }

        if(!isConnected){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.dialog_menu);
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }


        b = (Button) findViewById(R.id.butNew);

        index = new String[3];
        index2 = new String[2];

        index[0]="EASY";
        index[1]="MEDIUM";
        index[2]="HARD";

        index2[0]="Personal Scores";
        index2[1]="World Leaders";

    }

    public void newGame(View view) {
        createDialog(index).show();
    }

    public void newInstr(View view){
        Intent i = new Intent(this, InstructActivity.class);
        startActivity(i);
    }

    public void newScore(View view){
        createDialog(index2).show();
    }

    Dialog createDialog(final String [] index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setTitle("Choose Difficulty")
                .setIcon(R.mipmap.ic_launcher)
                .setItems(index, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(index.length==2)
                            startIntent(which+3);
                        else startIntent(which);
                    }
                });
        return builder.create();
    }

    private void create_name_dialog(String text, final String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

        final EditText input = new EditText(MenuActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setLayoutParams(lp);

        input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(9)});

        builder.setTitle("Type your name")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(text)
                .setView(input)
                .setCancelable(false)
                .setNeutralButton("OK",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){

                            SharedPreferences sharedPrefr = getSharedPreferences("FIRST_LAUNCH", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefr.edit();

                            editor.putString(s, input.getText().toString());
                            editor.apply();

            }
        });

        builder.create().show();
    }

    private void startIntent(int i){
        switch (i){
            case 0: {Intent intent = new Intent(this, GameActivity5.class);startActivity(intent);break;}
            case 1: {Intent intent = new Intent(this, GameActivity7.class);startActivity(intent);break;}
            case 2: {Intent intent = new Intent(this, GameActivity.class);startActivity(intent);break;}
            case 3: {Intent intent = new Intent(this, ScoreActivity.class);startActivity(intent);break;}
            case 4: {Intent intent = new Intent(this, ScoreActivityWorld.class);startActivity(intent);break;}
                default: break;
        }
    }

    @Override
    public void onBackPressed() {
    }
}
