package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Evaluation extends AppCompatActivity {

    TextView TvResult;
    TextView TvScore;
    Button BtStartMenu;
    Bundle bundle;
    String Score;
    int ScoreCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_evaluation);
        TvResult = findViewById(R.id.TvResult);
        TvScore = findViewById(R.id.TvScore);
        BtStartMenu = findViewById(R.id.BtStartMenu);
        BtStartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), Menu.class);
                //startActivity(intent);
                finish();
            }
        });
        bundle = getIntent().getExtras();
        Score = bundle.getString("Score");
        ScoreCounter = bundle.getInt("ScoreCounter");
        GenerateResults();
    }

    public void GenerateResults() {
        TvResult.setText("Απαντήσατε σωστά στις " + ScoreCounter + " από τις 6 ερωτήσεις!");
        TvScore.setText(Score + " %");
    }
}