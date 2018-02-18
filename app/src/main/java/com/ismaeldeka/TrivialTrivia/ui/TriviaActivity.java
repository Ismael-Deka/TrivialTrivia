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

public class TriviaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Question>>{

    TriviaQuestionFragment mQuestionFragment;

    public interface OnQuestionLoaderFinished{
       void startGame(ArrayList<Question> questions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        mQuestionFragment = new TriviaQuestionFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.question_fragment, mQuestionFragment)
                .commit();

        if(isNetworkAvailable()){
            getSupportLoaderManager().initLoader(1,null,this).forceLoad();
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

        return new QuestionLoader(this,new ApiCall(10));
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
