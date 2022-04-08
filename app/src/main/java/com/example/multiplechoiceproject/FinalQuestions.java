package com.example.multiplechoiceproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FinalQuestions {

    private ArrayList<Question> Questions;
    private int CurrentQuestion;
    private int Duration;
    private static FinalQuestions Instance = null;
    private static Context Cont;
    private SQLiteDatabase DB;

    public static FinalQuestions GetInstance (Context Co, SQLiteDatabase DB) {
        Cont = Co;
        if(Instance == null) {
            Instance = new FinalQuestions(DB);
        }
        return Instance;
    }

    public int getCurrentQuestion() {
        return CurrentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        CurrentQuestion = currentQuestion;
    }

    private FinalQuestions(SQLiteDatabase DB) {
        Questions = new ArrayList();
        CurrentQuestion = -1;
        Duration = 0;
        LoadData(DB);
    }


    private void LoadData(SQLiteDatabase DB) {

        Cursor Cur = DB.rawQuery("SELECT QuestID, Question FROM Questions", null);
        Cur.moveToFirst();
        do {
            int NoQ = Cur.getInt(0);
            Question Q = new Question();
            Q.setQuestionText(Cur.getString(1));
            Cursor CurAns = DB.rawQuery("SELECT RelativeID, Answer, Correct FROM Answers WHERE QuestID=?", new String[] {Integer.toString(NoQ)});
            CurAns.moveToFirst();
            do {
                Q.AddAnswer(CurAns.getString(1));
                if(CurAns.getInt(2) == 1) {
                    Q.setCorrectAnswer(CurAns.getInt(0));
                }
            } while (CurAns.moveToNext());
            CurAns.close();
            Questions.add(Q);
        } while(Cur.moveToNext());
        Cur.close();


    }

    public int GetNoQuestions() {
        return Questions.size();
    }

    public Question GetQuestion(int QNum) {
        return Questions.get(QNum);
    }

    public Question GetQuestion() {
        return Questions.get(CurrentQuestion);
    }

    public int GetNoUnansweredQuestions() {
        int Counter = 0;
        int i;
        for (i = 0; i < GetNoQuestions(); i++) {
            if(GetQuestion(i).getUserAnswer() == -1) {
                Counter++;
            }
        }
        return Counter;
    }

    public int GoNextUnAnswered() {
        if(GetNoUnansweredQuestions() == 0) {
            return -1;
        }
        do {
            CurrentQuestion++;
            if (CurrentQuestion == GetNoQuestions()) {
                CurrentQuestion = 0;
            }
        } while (GetQuestion(CurrentQuestion).isAnswered());
        return CurrentQuestion;
    }

    public int GoPreviousUnAnswered() {
        if(GetNoUnansweredQuestions() == 0) {
            return -1;
        }
        do {
            CurrentQuestion--;
            if (CurrentQuestion == -1) {
                CurrentQuestion = GetNoQuestions() - 1;
            }
        } while (GetQuestion(CurrentQuestion).isAnswered());
        return CurrentQuestion;
    }

    public void ResetFinalQuestions() {
        for(int i = 0; i < Questions.size(); i++) {
            Questions.get(i).setUserAnswer(-1);
        }
    }
}
