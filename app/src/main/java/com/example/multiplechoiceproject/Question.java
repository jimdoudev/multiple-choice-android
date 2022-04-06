package com.example.multiplechoiceproject;

import java.util.ArrayList;

public class Question {
    private String QuestionText;
    private ArrayList<String> Answers;
    private int CorrectAnswer;
    private int UserAnswer;

    public Question() {
        Answers = new ArrayList();
        CorrectAnswer = -1;
        UserAnswer = -1;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }

    public int getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public int getUserAnswer() {
        return UserAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        UserAnswer = userAnswer;
    }

    public void AddAnswer(String Answer) {
        Answers.add(Answer);
    }

    public boolean isAnswered() {
        if(UserAnswer == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isCorrect() {
        return UserAnswer == CorrectAnswer;
    }

    public int GetNoAnswers () {
        return Answers.size();
    }
}
