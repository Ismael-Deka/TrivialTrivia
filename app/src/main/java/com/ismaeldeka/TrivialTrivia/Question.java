package com.ismaeldeka.TrivialTrivia;

import java.util.ArrayList;

/**
 * Created by Ismael on 2/15/2018.
 */

public class Question {

    private String mCategory;
    private String mQuestionType;
    private String mDifficulty;
    private String mQuestion;
    private String mCorrectAnswer;
    private ArrayList<String> mIncorrectAnswers;

    public Question(){

    }

    public Question(String category, String questionType, String difficulty, String question, String correctAnswer, ArrayList<String> incorrectAnswers){
        mCategory = category;
        mQuestionType = TriviaUtils.getQuestionTypeName(questionType);
        mDifficulty = difficulty;
        mQuestion = question;
        mCorrectAnswer = correctAnswer;
        mIncorrectAnswers = incorrectAnswers;
    }

    public void setQuestionType(String questionType) {
        this.mQuestionType = questionType;
    }

    public void setDifficulty(String difficulty) {
        this.mDifficulty = difficulty;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.mCorrectAnswer = correctAnswer;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.mIncorrectAnswers = incorrectAnswers;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }



    public ArrayList<String> getIncorrectAnswers() {
        return mIncorrectAnswers;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public String getDifficulty() {
        return mDifficulty;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getQuestionType() {
        return mQuestionType;
    }

}
