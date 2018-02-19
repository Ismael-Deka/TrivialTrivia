package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.ismaeldeka.TrivialTrivia.ApiCall;
import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoader;
import com.ismaeldeka.TrivialTrivia.R;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Question>>,CustomGameSettingsFragment.CustomGameCallback{

    TriviaQuestionFragment mQuestionFragment;
    CustomGameSettingsFragment mGameSettingsFragment;


    public interface OnQuestionLoaderFinished{
       void startGame(ArrayList<Question> questions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

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

        if(isNetworkAvailable()){
            getSupportLoaderManager().initLoader(1,gameParams,this).forceLoad();
        }else{
            mQuestionFragment.startGame(null);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public Loader<ArrayList<Question>> onCreateLoader(int id, Bundle args) {

        int numQuestion = args.getInt(getString(R.string.num_questions));
        String category = args.getString(getString(R.string.category));
        String difficulty= args.getString(getString(R.string.difficulty));

        ApiCall apiCall = new ApiCall(numQuestion);
        apiCall.setCategory(category);
        apiCall.setDifficulty(difficulty);

        return new QuestionLoader(this,apiCall);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Question>> loader, ArrayList<Question> data) {
        mQuestionFragment.startGame(data);
        getSupportLoaderManager().destroyLoader(1);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Question>> loader) {

    }
}
