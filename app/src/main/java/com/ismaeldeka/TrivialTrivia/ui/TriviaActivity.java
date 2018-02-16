package com.ismaeldeka.TrivialTrivia.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ismaeldeka.TrivialTrivia.ApiCall;
import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.QuestionLoader;
import com.ismaeldeka.TrivialTrivia.R;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Question>>{

    TriviaQuestionFragment mQuestionFragment;

    public interface OnQuestionLoaderFinished{
       void displayQuestion(Question question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        mQuestionFragment = new TriviaQuestionFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.question_fragment, mQuestionFragment)
                .commit();

        getSupportLoaderManager().initLoader(1,null,this).forceLoad();
    }

    @Override
    public Loader<ArrayList<Question>> onCreateLoader(int id, Bundle args) {
        Log.e("trivia activity","");
        return new QuestionLoader(this,new ApiCall(1));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Question>> loader, ArrayList<Question> data) {
        Log.e("trivia activity",data.get(0).getQuestion());
            mQuestionFragment.displayQuestion(data.get(0));
        getSupportLoaderManager().destroyLoader(1);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Question>> loader) {

    }
}
