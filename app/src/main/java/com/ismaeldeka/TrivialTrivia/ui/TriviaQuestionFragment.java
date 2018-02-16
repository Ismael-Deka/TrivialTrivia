package com.ismaeldeka.TrivialTrivia.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.R;


public class TriviaQuestionFragment extends Fragment implements TriviaActivity.OnQuestionLoaderFinished {

    private TextView mQuestionView;

    public TriviaQuestionFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_trivia_question, container, false);

        mQuestionView = rootView.findViewById(R.id.question);
        mQuestionView.setText("");

        return rootView;
    }


    @Override
    public void displayQuestion(Question question) {
        Log.e("trivia activty",Html.fromHtml(question.getQuestion()).toString());
        mQuestionView.setText(Html.fromHtml(question.getQuestion()));
    }
}
