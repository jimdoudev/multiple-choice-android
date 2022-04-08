package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    Button BtNew;
    Button BtGrades;
    Button BtReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        BtNew = findViewById(R.id.BtNew);
        BtGrades = findViewById(R.id.BtGrades);
        BtReturn = findViewById(R.id.BtReturn);
        BtNew.setOnClickListener(this);
        BtGrades.setOnClickListener(this);
        BtReturn.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        if(v == BtNew) {
            Intent intent = new Intent(getApplicationContext(), DataEntry.class);
            startActivity(intent);
        }
        if(v == BtGrades) {
            Intent intent = new Intent(getApplicationContext(), HighScores.class);
            startActivity(intent);
        }
        if(v == BtReturn) {
            finish();
        }
    }
}