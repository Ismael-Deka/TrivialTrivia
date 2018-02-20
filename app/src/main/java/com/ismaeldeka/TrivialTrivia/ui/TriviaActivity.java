package com.ismaeldeka.TrivialTrivia.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoaderCallback;
import com.ismaeldeka.TrivialTrivia.R;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements QuestionLoaderCallback.OnQuestionLoaderCompleteListener,CustomGameSettingsFragment.CustomGameCallback{

    TriviaQuestionFragment mQuestionFragment;
    CustomGameSettingsFragment mGameSettingsFragment;
    QuestionLoaderCallback mLoaderCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        mLoaderCallback = new QuestionLoaderCallback(this);
        mQuestionFragment = new TriviaQuestionFragment();
        mGameSettingsFragment = new CustomGameSettingsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.question_fragment, mGameSettingsFragment)
                .commit();



    }

    @Override
    public void gameSelected(Bundle gameParams) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.question_fragment, mQuestionFragment)
                .commit();

        getSupportLoaderManager().initLoader(1,gameParams,mLoaderCallback).forceLoad();
    }




    @Override
    public void startGame(ArrayList<Question> questions) {
        mQuestionFragment.startGame(questions,false);
        getSupportLoaderManager().destroyLoader(1);

    }
}
