package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoaderCallback;
import com.ismaeldeka.TrivialTrivia.R;
import com.ismaeldeka.TrivialTrivia.TriviaUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnItemClickListener,
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
    public void onItemClicked(int position) {
        if(!mTwoPane){
            Intent i = new Intent(this, TriviaActivity.class);
            i.putExtra(getString(R.string.game_type),position);
            startActivity(i);
        }else if(position == TriviaUtils.CUSTOM_GAME){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.trivia_fragment_tablet, mGameSettingsFragment)
                    .commit();
        }else {
            Bundle gameArgs = TriviaUtils.getGameArgs(position,this);
            gameSelected(gameArgs);
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
    public void startGame(ArrayList<Question> questions,int timeLimit) {
        getSupportLoaderManager().destroyLoader(1);
        mMasterListFragment.setQuestionList(questions);
        mQuestionFragment.startGame(questions,mTwoPane,timeLimit);

    }


    @Override
    public void onGameFinished() {
        getSupportFragmentManager().beginTransaction()
                .detach(mQuestionFragment)
                .commit();
        mMasterListFragment.setGameList();

    }
}
