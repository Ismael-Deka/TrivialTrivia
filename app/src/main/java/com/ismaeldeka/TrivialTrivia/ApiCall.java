package com.ismaeldeka.TrivialTrivia;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ismael on 2/13/2018.
 */

public class ApiCall {
    private final String LOG_TAG = "ApiCall";
    private final String URL_BASE = "https://opentdb.com/api.php?";
    private int mNumQuestions = -1;
    private int mCategory = -1;
    private String mDifficulty;
    private String mQuestionType;


    public ApiCall(int numQuestions){
        mNumQuestions = numQuestions;

    }

    public void setNumQuestions(int numQuestions) {
        this.mNumQuestions = numQuestions;
    }

    public void setCategory(String category) {
        this.mCategory = TriviaUtils.getCategoryId(category);
    }

    public void setDifficulty(String difficulty) {
        this.mDifficulty = difficulty;
    }

    public void setQuestionType(String questionType) {
        this.mQuestionType = TriviaUtils.getQuestionTypeParam(questionType);
    }

    public URL buildApiCall(){
        String url="";

        url= URL_BASE;

        if(mNumQuestions > 0){
            url += "amount="+ mNumQuestions;
        }
        if(mCategory >= 9 && mCategory <= 32){
            url += "&category="+mCategory;
        }
        if(mDifficulty != null && !mDifficulty.equals("")){
            url += "&difficulty=" + mDifficulty.toLowerCase();
        }
        if(mQuestionType != null && !mQuestionType.equals("")) {
            url += "&type=" + mQuestionType;
        }
        Log.e("trivia activity",url);
        return createUrl(url);

    }

    private URL createUrl(String requestUrl) {
        URL url = null;

        try {
            url = new URL(requestUrl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }





}
