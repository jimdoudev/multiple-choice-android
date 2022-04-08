package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HighScores extends AppCompatActivity {

    TextView TvAA;
    TextView TvFullName;
    TextView TvAM;
    TextView TvHighScore;
    TextView TvDate;
    SQLiteDatabase DB;
    Button BtMen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_high_scores);
        TvAA = findViewById(R.id.TvAA);
        TvFullName = findViewById(R.id.TvFullName);
        TvAM = findViewById(R.id.TvAM);
        TvHighScore = findViewById(R.id.TvHighScore);
        TvDate = findViewById(R.id.TvDate);
        DB = SQLiteDatabase.openDatabase(getApplicationContext().getFilesDir() + "/MultipleChoiceProject.db", null, 0);
        BtMen = findViewById(R.id.BtMen);
        BtMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ShowData(DB);
    }

    public void ShowData(SQLiteDatabase DB) {
        Cursor Cur = DB.rawQuery("SELECT ScoreID, Name, AM, Score, Date FROM Scores", null);
        if(Cur.moveToFirst()) {
            do {
                int ScoreID = Cur.getInt(0);
                TvAA.append(Integer.toString(ScoreID) + "\n");
                String Name = Cur.getString(1);
                TvFullName.append(Name + "\n");
                String AM = Cur.getString(2);
                TvAM.append(AM + "\n");
                String Score = Cur.getString(3);
                TvHighScore.append(Score + " %\n");
                String Date = Cur.getString(4);
                TvDate.append(Date + "\n");

            } while(Cur.moveToNext());
            Cur.close();

        } else {
            TvFullName.append("No Entries.....");
        }
    }
}