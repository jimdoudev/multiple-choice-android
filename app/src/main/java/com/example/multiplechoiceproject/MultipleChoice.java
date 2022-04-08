package com.example.multiplechoiceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MultipleChoice extends AppCompatActivity implements View.OnClickListener {

    int Minutes = 3;
    int Seconds = 0;
    String DateSnap;
    TextView TvTimer;
    TextView TvQuestNo;
    TextView TvQuestion;
    TextView [] TvAnswers;
    Button BtSubmit;
    ImageButton BtPrev;
    ImageButton BtNext;
    ProgressBar PbProgress;
    Timer MyTimer = null;
    TimerTask MyTimerTask = null;
    FinalQuestions AllQuests;
    Question CurrentQuestion;
    int CurQNum;
    int SelectedAnswer;
    Drawable Backdraw;
    SQLiteDatabase DB;
    Bundle bundle;
    String Name;
    String AM;
    String Score;
    int ScoreID;

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
        TvQuestNo = findViewById(R.id.TvQuestNo);
        TvQuestion = findViewById(R.id.TvQuestion);
        TvAnswers = new TextView[4];
        TvAnswers[0] = findViewById(R.id.TvAnswer1);
        TvAnswers[1] = findViewById(R.id.TvAnswer2);
        TvAnswers[2] = findViewById(R.id.TvAnswer3);
        TvAnswers[3] = findViewById(R.id.TvAnswer4);
        for(int i = 0; i < 4; i++) {
            TvAnswers[i].setOnClickListener(this);
        }
        BtSubmit = findViewById(R.id.BtSubmit);
        BtSubmit.setOnClickListener(this);
        BtNext = findViewById(R.id.BtNext);
        BtNext.setOnClickListener(this);
        BtPrev = findViewById(R.id.BtPrev);
        BtPrev.setOnClickListener(this);
        PbProgress = findViewById(R.id.PbProgress);
        bundle = getIntent().getExtras();
        Name = bundle.getString("Name");
        AM = bundle.getString("AM");
        System.out.println(Name + AM);
        DB = SQLiteDatabase.openDatabase(getApplicationContext().getFilesDir() + "/MultipleChoiceProject.db", null, 0);
        AllQuests = FinalQuestions.GetInstance(this, DB);
        TimerStart();
        Next();
    }

    @Override
    public void onClick(View v) {
        if(v == BtPrev) {
            AllQuests.GoPreviousUnAnswered();
            AllQuests.setCurrentQuestion(AllQuests.getCurrentQuestion() - 1);
            Next();
        }
        if(v == BtNext) {
            Next();
        }
        if(v == BtSubmit) {
            if(SelectedAnswer == -1) {
                return;
            }
            CurrentQuestion.setUserAnswer(SelectedAnswer);
            Next();
        }
        for (int i = 0; i < 4; i++) {
            if(v == TvAnswers[i]) {
                SelectedAnswer = i;
            }
        }
        ChangeAnswer();
    }

    void Next() {
        CurQNum = AllQuests.GoNextUnAnswered();
        if(CurQNum == -1) {
            End();
        } else {
            CurrentQuestion = AllQuests.GetQuestion();
            for (int i = 0; i < 4; i++) {
                TvAnswers[i].setEnabled(true);
            }
            TvQuestNo.setText("ΕΡΩΤΗΣΗ " + (CurQNum + 1));
            TvQuestion.setText(CurrentQuestion.getQuestionText());
            for(int i = 0; i < CurrentQuestion.getNoAnswers(); i++) {
                TvAnswers[i].setText(CurrentQuestion.getAnswer(i));
            }
            for(int i = CurrentQuestion.getNoAnswers(); i < 4; i++) {
                TvAnswers[i].setEnabled(false);
                TvAnswers[i].setText("");
            }
        }
        SelectedAnswer = -1;
    }

    void ChangeAnswer() {
        int i;
        for(i = 0; i < 4; i++) {
            TvAnswers[i].setBackground(Backdraw);
            if(i == SelectedAnswer)
                TvAnswers[i].setBackgroundColor(Color.GREEN);
        }
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
                    End();
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

    public void End() {
        StopTimer();
        GetScore();
        GetDate();
        InsertData();
        StartNextActivity();
    }

    public void InsertData() {
        SQLiteStatement stmt = DB.compileStatement("INSERT INTO Scores (Name, AM, Score, Date) VALUES (?, ?, ?, ?)");
        stmt.bindString(1, Name);
        stmt.bindString(2, AM);
        stmt.bindString(3, Score);
        stmt.bindString(4, DateSnap);
        ScoreID = (int) stmt.executeInsert();
        System.out.println(ScoreID);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Not Allowed");
    }

    public void GetScore() {
        Double Counter = 0.0;
        DecimalFormat df = new DecimalFormat("00.00");
        int NoQ = AllQuests.GetNoQuestions();
        for(int i = 0; i < NoQ; i++) {
            if(AllQuests.GetQuestion(i).isCorrect()) {
                Counter++;
            }
        }
        Score = df.format((Counter / NoQ) * 100);
        System.out.println(Score);
    }

    public void GetDate() {
        Date Date = Calendar.getInstance().getTime();
        DateFormat DateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        DateSnap = DateFormat.format(Date);
        System.out.println(DateSnap);
    }

    public void StartNextActivity() {
        bundle.putInt("ScoreID", ScoreID);
        Intent intent = new Intent(getApplicationContext(), Evaluation.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}