package com.ismaeldeka.TrivialTrivia.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoaderCallback;
import com.ismaeldeka.TrivialTrivia.R;
import com.ismaeldeka.TrivialTrivia.TriviaUtils;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements TriviaQuestionFragment.OnGameFinishedListener,QuestionLoaderCallback.OnQuestionLoaderCompleteListener,CustomGameSettingsFragment.CustomGameCallback{

    private TriviaQuestionFragment mQuestionFragment;
    private CustomGameSettingsFragment mGameSettingsFragment;
    private QuestionLoaderCallback mLoaderCallback;
    private int mGameType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        mLoaderCallback = new QuestionLoaderCallback(this);
        mQuestionFragment = new TriviaQuestionFragment();
        mGameSettingsFragment = new CustomGameSettingsFragment();
        mGameType = getIntent().getIntExtra(getString(R.string.game_type), TriviaUtils.CUSTOM_GAME);

        if(mGameType == TriviaUtils.CUSTOM_GAME){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.question_fragment, mGameSettingsFragment)
                    .commit();
        }else{
            Bundle gameArg = TriviaUtils.getGameArgs(mGameType,this);
            gameSelected(gameArg);
        }




    }

    @Override
    public void gameSelected(Bundle gameParams) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.question_fragment, mQuestionFragment)
                .commit();

        getSupportLoaderManager().initLoader(1,gameParams,mLoaderCallback).forceLoad();
    }




    @Override
    public void startGame(ArrayList<Question> questions, int timeLimit) {
        mQuestionFragment.startGame(questions,false, timeLimit);
        getSupportLoaderManager().destroyLoader(1);

    }

    @Override
    public void onGameFinished() {

    }
}
