package com.blacio.squarespuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreActivityWorld extends AppCompatActivity {

    TextView tv,tv5,tv7;
    TextView nm,nm5,nm7;
    TextView cy,cy5,cy7;

    Long s,s5,s7;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        tv = (TextView)findViewById(R.id.hardWorld);
        tv7 = (TextView)findViewById(R.id.mediumWorld);
        tv5 = (TextView)findViewById(R.id.easyWorld);

        nm = (TextView)findViewById(R.id.nume3world);
        nm7 = (TextView)findViewById(R.id.nume2world);
        nm5 = (TextView)findViewById(R.id.nume1world);

        cy = (TextView)findViewById(R.id.country3world);
        cy7 = (TextView)findViewById(R.id.country2world);
        cy5 = (TextView)findViewById(R.id.country1world);


        final SharedPreferences sharedPrefr = getSharedPreferences("FIRST_LAUNCH", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefr2 = getSharedPreferences("TAG", Context.MODE_PRIVATE);

        s = sharedPrefr2.getLong("aux", 0);
        s5 = sharedPrefr2.getLong("aux5", 0);
        s7 = sharedPrefr2.getLong("aux7", 0);


        database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        myRef.child("hard").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Long value = dataSnapshot.child("score").getValue(Long.class);
                String value2 = dataSnapshot.child("country").getValue(String.class);
                String value3 = dataSnapshot.child("name").getValue(String.class);

                tv.setText("Score: "+value);
                nm.setText(value3);
                cy.setText("from "+value2);


                if(s>value) {
                    myRef.child("hard").child("score").setValue(s);
                    myRef.child("hard").child("name").setValue(String.valueOf(sharedPrefr.getString("name_world", "nimeni")));
                    myRef.child("hard").child("country").setValue(String.valueOf(sharedPrefr.getString("country_world", "nicaieri")));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        myRef.child("medium").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Long value = dataSnapshot.child("score").getValue(Long.class);
                String value2 = dataSnapshot.child("country").getValue(String.class);
                String value3 = dataSnapshot.child("name").getValue(String.class);


                tv7.setText("Score: "+value);
                nm7.setText(value3);
                cy7.setText("from "+value2);


                if(s7>value) {
                    myRef.child("medium").child("score").setValue(s7);
                    myRef.child("medium").child("name").setValue(String.valueOf(sharedPrefr.getString("name_world", "nimeni")));
                    myRef.child("medium").child("country").setValue(String.valueOf(sharedPrefr.getString("country_world", "nicaieri")));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });




        myRef.child("easy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Long value = dataSnapshot.child("score").getValue(Long.class);
                String value2 = dataSnapshot.child("country").getValue(String.class);
                String value3 = dataSnapshot.child("name").getValue(String.class);

                tv5.setText("Score: "+value);
                nm5.setText(value3);
                cy5.setText("from "+value2);


                if(s5>value) {
                    myRef.child("easy").child("score").setValue(s5);
                    myRef.child("easy").child("name").setValue(String.valueOf(sharedPrefr.getString("name_world", "nimeni")));
                    myRef.child("easy").child("country").setValue(String.valueOf(sharedPrefr.getString("country_world", "nicaieri")));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

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