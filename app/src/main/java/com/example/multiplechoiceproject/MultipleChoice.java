package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoice extends AppCompatActivity {

    int Minutes = 3;
    int Seconds = 0;
    TextView TvTimer;
    ProgressBar PbProgress;
    Timer MyTimer = null;
    TimerTask MyTimerTask = null;
    FinalQuestions AllQuests;
    SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //hide the title bar
        getSupportActionBar().hide();
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_multiple_choice);
        TvTimer = findViewById(R.id.TvTimer);
        PbProgress = findViewById(R.id.PbProgress);
        TimerStart();
        DB = SQLiteDatabase.openDatabase(getApplicationContext().getFilesDir() + "/MultipleChoiceProject.db", null, 0);
        AllQuests = FinalQuestions.GetInstance(this, DB);
    }

    public void TimerStart ()
    {
        MyTimerTask = new TimerTask ()
        {
            public void run ()
            {
                if(Seconds == 00) {
                    Minutes--;
                    Seconds = 59;
                } else {
                    Seconds--;
                }
                String Time;
                if(Seconds < 10) {
                    Time = "0" + String.valueOf(Minutes) + ":0" + String.valueOf(Seconds);
                } else {
                    Time = "0" + String.valueOf(Minutes) + ":" + String.valueOf(Seconds);
                }
                int ProgressBar = Seconds + Minutes*60;
                ShowMessage (Time, ProgressBar);
                if(Minutes == 0 && Seconds ==0) {
                    StopTimer();
                }
            }
        };
        if (MyTimer == null){
            MyTimer = new Timer ();
            MyTimer.schedule (MyTimerTask, 1000, 1000);
        }
    }

    public void StopTimer ()
    {
            MyTimer.cancel ();
            MyTimer.purge ();
            MyTimer = null;
    }

    public void ShowMessage (final String Time, final int Progress)
    {
        runOnUiThread (new Runnable ()
        {
            @Override
            public void run ()
            {
                TvTimer.setText (Time);
                PbProgress.setProgress(Progress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.out.println("Not Allowed");
    }
}