package com.ismaeldeka.TrivialTrivia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.ArrayList;

/**
 * Created by Ismael on 2/20/2018.
 */

public class QuestionLoaderCallback implements LoaderManager.LoaderCallbacks<ArrayList<Question>> {

    private Context mContext;
    private OnQuestionLoaderCompleteListener mCallback;
    private int mTimeLimit;

    public QuestionLoaderCallback(Context context){
        mContext = context;
        mCallback = (OnQuestionLoaderCompleteListener)context;
    }

    public interface OnQuestionLoaderCompleteListener {
        void startGame(ArrayList<Question> questions,int timeLimit);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public Loader<ArrayList<Question>> onCreateLoader(int id, Bundle args) {

        mTimeLimit = args.getInt(mContext.getString(R.string.time_limit));
        int numQuestion = args.getInt(mContext.getString(R.string.num_questions));
        String category = args.getString(mContext.getString(R.string.category));
        String difficulty= args.getString(mContext.getString(R.string.difficulty));

        ApiCall apiCall = new ApiCall(numQuestion);
        apiCall.setCategory(category);
        apiCall.setDifficulty(difficulty);
        if(isNetworkAvailable()) {
            return new QuestionLoader(mContext, apiCall);
        }else{
            return new QuestionLoader(mContext, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Question>> loader, ArrayList<Question> data) {
        mCallback.startGame(data, mTimeLimit);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Question>> loader) {

    }
}
