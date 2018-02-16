package com.ismaeldeka.TrivialTrivia.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ismaeldeka.TrivialTrivia.Question;
import com.ismaeldeka.TrivialTrivia.R;
import com.ismaeldeka.TrivialTrivia.TriviaUtils;

import java.util.Random;


public class TriviaQuestionFragment extends Fragment implements TriviaActivity.OnQuestionLoaderFinished {

    private TextView mQuestionTextView;

    LinearLayout mQuestionView;

    ProgressBar mProgressBar;

    private RadioGroup mMultipleChoiceGroup;
    private RadioGroup mTrueFalseGroup;

    private RadioButton mAnswerOneButton;
    private RadioButton mAnswerTwoButton;
    private RadioButton mAnswerThreeButton;
    private RadioButton mAnswerFourButton;

    private RadioButton mTrueButton;
    private RadioButton mFalseButton;

    private Button mNextButton;

    private Question mCurrentQuestion;

    private int mCorrectAnswerPosition;

    public TriviaQuestionFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_trivia_question, container, false);

        mQuestionTextView = rootView.findViewById(R.id.question);

        mQuestionView = rootView.findViewById(R.id.question_view);
        mProgressBar = rootView.findViewById(R.id.progress_bar);

        mMultipleChoiceGroup = rootView.findViewById(R.id.multiple_choice);
        mTrueFalseGroup = rootView.findViewById(R.id.true_false);

        mAnswerOneButton=rootView.findViewById(R.id.answer_button_one);
        mAnswerTwoButton = rootView.findViewById(R.id.answer_button_two);
        mAnswerThreeButton = rootView.findViewById(R.id.answer_button_three);
        mAnswerFourButton = rootView.findViewById(R.id.answer_button_four);

        mTrueButton = rootView.findViewById(R.id.answer_button_true);
        mFalseButton = rootView.findViewById(R.id.answer_button_false);

        mNextButton = rootView.findViewById(R.id.button_next_question);

        mQuestionView.setVisibility(View.GONE);


        return rootView;
    }


    @Override
    public void displayQuestion(Question question) {

        mCurrentQuestion = question;

        mQuestionView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);

        mQuestionTextView.setText(Html.fromHtml(question.getQuestion()));

        if(TriviaUtils.isQuestionMultipleChoice(question)){
            displayMultipleChoiceAnswer(question);
        }else {
            displayTrueFalseAnswer();
        }

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAnswerCorrect()){
                    Toast.makeText(TriviaQuestionFragment.this.getContext(),"Correct.",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(TriviaQuestionFragment.this.getContext(),"Incorrect.",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void displayMultipleChoiceAnswer(Question question){
        mTrueFalseGroup.setVisibility(View.GONE);
        mMultipleChoiceGroup.setVisibility(View.VISIBLE);
        Random rng = new Random();
        mCorrectAnswerPosition = rng.nextInt(4) + 1;
        switch (mCorrectAnswerPosition){
            case 1:
                mAnswerOneButton.setText(question.getCorrectAnswer());
                mAnswerTwoButton.setText(question.getIncorrectAnswers().get(0));
                mAnswerThreeButton.setText(question.getIncorrectAnswers().get(1));
                mAnswerFourButton.setText(question.getIncorrectAnswers().get(2));
                break;
            case 2:
                mAnswerOneButton.setText(question.getIncorrectAnswers().get(0));
                mAnswerTwoButton.setText(question.getCorrectAnswer());
                mAnswerThreeButton.setText(question.getIncorrectAnswers().get(1));
                mAnswerFourButton.setText(question.getIncorrectAnswers().get(2));
                break;
            case 3:
                mAnswerOneButton.setText(question.getIncorrectAnswers().get(0));
                mAnswerTwoButton.setText(question.getIncorrectAnswers().get(1));
                mAnswerThreeButton.setText(question.getCorrectAnswer());
                mAnswerFourButton.setText(question.getIncorrectAnswers().get(2));
                break;
            case 4:
                mAnswerOneButton.setText(question.getIncorrectAnswers().get(0));
                mAnswerTwoButton.setText(question.getIncorrectAnswers().get(1));
                mAnswerThreeButton.setText(question.getIncorrectAnswers().get(2));
                mAnswerFourButton.setText(question.getCorrectAnswer());
                break;
        }

    }

    private void displayTrueFalseAnswer(){
        mMultipleChoiceGroup.setVisibility(View.GONE);
        mTrueFalseGroup.setVisibility(View.VISIBLE);

    }

    private boolean isAnswerCorrect(){
        if(TriviaUtils.isQuestionMultipleChoice(mCurrentQuestion)){
            int checkedId = mMultipleChoiceGroup.getCheckedRadioButtonId();
            if(checkedId == mAnswerOneButton.getId()&&mCorrectAnswerPosition == 1){
                return true;
            } else if(checkedId == mAnswerTwoButton.getId()&&mCorrectAnswerPosition == 2){
                return true;
            }else if(checkedId == mAnswerThreeButton.getId()&&mCorrectAnswerPosition == 3){
                return true;
            }else if(checkedId == mAnswerFourButton.getId()&&mCorrectAnswerPosition == 4){
                return true;
            }else {
                return false;
            }

        }else {
            int checkedId = mTrueFalseGroup.getCheckedRadioButtonId();
            String correctAnswer = mCurrentQuestion.getCorrectAnswer();
            if(checkedId == mTrueButton.getId()&&correctAnswer.equals("True")){
                return true;
            }else if(checkedId == mFalseButton.getId()&&correctAnswer.equals("False")){
                return true;
            }else {
                return false;
            }

        }

    }

}
