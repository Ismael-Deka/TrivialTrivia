package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoaderCallback;
import com.ismaeldeka.TrivialTrivia.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnGameClickListener,
                                                        CustomGameSettingsFragment.CustomGameCallback,
                                                        QuestionLoaderCallback.OnQuestionLoaderCompleteListener,
        TriviaQuestionFragment.OnGameFinishedListener{

    private boolean mTwoPane;
    private CustomGameSettingsFragment mGameSettingsFragment;
    private QuestionLoaderCallback mLoaderCallback;
    private TriviaQuestionFragment mQuestionFragment;

    private MasterListFragment mMasterListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMasterListFragment = new MasterListFragment();
        mGameSettingsFragment = new CustomGameSettingsFragment();
        mLoaderCallback = new QuestionLoaderCallback(this);
        mQuestionFragment = new TriviaQuestionFragment();



        if(findViewById(R.id.trivia_fragment_tablet) != null){
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_fragment, mMasterListFragment)
                    .commit();

        }
        else {
            mTwoPane = false;
        }


    }

    @Override
    public void onGameClicked(int position) {
        if(mTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.trivia_fragment_tablet, mGameSettingsFragment)
                    .commit();

        }else {
            Intent i = new Intent(this, TriviaActivity.class);

            startActivity(i);

        }
    }


    @Override
    public void gameSelected(Bundle gameParams) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.trivia_fragment_tablet, mQuestionFragment)
                .commit();

        getSupportLoaderManager().initLoader(1,gameParams,mLoaderCallback).forceLoad();
    }

    @Override
    public void startGame(ArrayList<Question> questions) {
        getSupportLoaderManager().destroyLoader(1);
        mMasterListFragment.setQuestionList(questions);
        mQuestionFragment.startGame(questions,mTwoPane);

    }


    @Override
    public void onGameFinished() {
        getSupportFragmentManager().beginTransaction()
                .detach(mQuestionFragment)
                .commit();
        mMasterListFragment.setGameList();

    }
}
