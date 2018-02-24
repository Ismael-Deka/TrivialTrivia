package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.SparseArray;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class TriviaQuestionFragment extends Fragment implements MasterListFragment.OnItemClickListener{

    private TextView mQuestionTextView;
    private  TextView mNoInternetView;

    LinearLayout mQuestionView;

    ProgressBar mProgressBar;

    TextView mTimerView;

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

    private ArrayList<Question> mQuestionList;

    private SparseArray<String> mSelectedAnswerList;

    private int mCorrectAnswerPosition;

    private boolean mTwoPane;

    private OnGameFinishedListener mCallback;

    private TextView mQuestionNumber;



    public TriviaQuestionFragment()  {

    }

    @Override
    public void onItemClicked(int position) {
        mCurrentQuestion = mQuestionList.get(position);
        displayQuestion(mCurrentQuestion);
    }

    public interface OnGameFinishedListener{
        void onGameFinished();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnGameFinishedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement OnGameFinishedListener");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_trivia_question, container, false);

        mQuestionTextView = rootView.findViewById(R.id.question);
        mNoInternetView = rootView.findViewById(R.id.no_internet);

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

        mTimerView = rootView.findViewById(R.id.timer);

        mQuestionNumber = rootView.findViewById(R.id.question_number);

        mQuestionView.setVisibility(View.GONE);





        return rootView;
    }



    private void displayQuestion(Question question) {
        mProgressBar.setVisibility(View.GONE);

        if(question != null) {
            mCurrentQuestion = question;

            mQuestionView.setVisibility(View.VISIBLE);


            mQuestionTextView.setText(Html.fromHtml(question.getQuestion()));

            if (TriviaUtils.isQuestionMultipleChoice(question)) {
                displayMultipleChoiceAnswer(question);
            } else {
                displayTrueFalseAnswer();
            }

            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isAnswerCorrect()) {
                        Toast.makeText(TriviaQuestionFragment.this.getContext(), "Correct.", Toast.LENGTH_SHORT).show();// TODO: Implement system to grade quiz after it's complete.

                    } else {
                        Toast.makeText(TriviaQuestionFragment.this.getContext(), "Incorrect.", Toast.LENGTH_SHORT).show();
                    }
                    setSelectedAnswer();
                    displayNextQuestion();
                }
            });
            if(isQuestionNew(question)) {
                mTrueFalseGroup.clearCheck();
                mMultipleChoiceGroup.clearCheck();
            }else {
                restorePreviousSelection(question);
            }
        }else{
            mNoInternetView.setVisibility(View.VISIBLE);
        }


    }

    private void displayNextQuestion(){
        int nextQuestionIndex = mQuestionList.indexOf(mCurrentQuestion) + 1;
        if(nextQuestionIndex >= mQuestionList.size()-1){
            endGame();
        }else{
            mQuestionNumber.setText((nextQuestionIndex+1)+".)");
            displayQuestion(mQuestionList.get(nextQuestionIndex));
        }
    }

    private void endGame(){
        int numCorrect = gradeQuiz();
        int numQuestions = mQuestionList.size();
        Toast.makeText(getContext(),"You got " + numCorrect + " out of "+ numQuestions +" questions correct.",Toast.LENGTH_LONG).show();
        if(!mTwoPane) {
            getActivity().finish();
        }else {
            mCallback.onGameFinished();
        }

    }

    private int gradeQuiz(){
        int numCorrect = 0;
        String selectedAnswer;
        String correctAnswer;
        for(int i = 0 ; i < mSelectedAnswerList.size(); i++){
            selectedAnswer = mSelectedAnswerList.get(i);
            correctAnswer = mQuestionList.get(i).getCorrectAnswer();
            if(selectedAnswer.equals(correctAnswer)){
                numCorrect++;
            }
        }
        return numCorrect;
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
    private void startTimer(int timeLimit){

        CountDownTimer timer = new CountDownTimer((timeLimit * 1000), 1000) {
            @Override
            public void onTick(long l) {
                Date date = new Date(l);
                DateFormat formatter = new SimpleDateFormat("mm:ss");
                String countDownTime = formatter.format(date);
                mTimerView.setText(countDownTime);
            }

            @Override
            public void onFinish() {
                finishIncompleteQuiz(mQuestionList.indexOf(mCurrentQuestion)+1);
                endGame();
            }
        };

        timer.start();

    }

    public boolean isQuestionNew(Question question) {
        int questionIndex = mQuestionList.indexOf(question);
        if(mSelectedAnswerList.indexOfKey(questionIndex) < 0){
            return true;
        }else{
            return false;
        }
    }

    private void restorePreviousSelection(Question question){
        int questionIndex = mQuestionList.indexOf(question);
        if(TriviaUtils.isQuestionMultipleChoice(question)){
            restoreMultipleChoiceSelection(questionIndex);
        }else {
            restoreTrueFalseSelection(questionIndex);
        }
    }

    private void setSelectedAnswer(){
        int selectedIndex = mQuestionList.indexOf(mCurrentQuestion);
        String selectedAnswer;
        if(TriviaUtils.isQuestionMultipleChoice(mCurrentQuestion)){
            int checkedId = mMultipleChoiceGroup.getCheckedRadioButtonId();
            if(checkedId == mAnswerOneButton.getId()){
                selectedAnswer =mAnswerOneButton.getText().toString();
                mSelectedAnswerList.put(selectedIndex, selectedAnswer);
            } else if(checkedId == mAnswerTwoButton.getId()&&mCorrectAnswerPosition == 2){
                selectedAnswer =mAnswerTwoButton.getText().toString();
                mSelectedAnswerList.put(selectedIndex, selectedAnswer);
            }else if(checkedId == mAnswerThreeButton.getId()&&mCorrectAnswerPosition == 3){
                selectedAnswer =mAnswerThreeButton.getText().toString();
                mSelectedAnswerList.put(selectedIndex, selectedAnswer);
            }else if(checkedId == mAnswerFourButton.getId()&&mCorrectAnswerPosition == 4){
                selectedAnswer =mAnswerFourButton.getText().toString();
                mSelectedAnswerList.put(selectedIndex, selectedAnswer);
            }

        }else {
            int checkedId = mTrueFalseGroup.getCheckedRadioButtonId();
            if(checkedId == mTrueButton.getId()){
                mSelectedAnswerList.put(selectedIndex, "True");
            }else if(checkedId == mFalseButton.getId()){
                mSelectedAnswerList.put(selectedIndex, "False");
            }
        }
    }

    private void restoreMultipleChoiceSelection(int questionIndex){
        String selectedAnswer = mSelectedAnswerList.valueAt(questionIndex);

        String answerOne = mAnswerOneButton.getText().toString();
        String answerTwo = mAnswerTwoButton.getText().toString();
        String answerThree = mAnswerThreeButton.getText().toString();
        String answerFour = mAnswerFourButton.getText().toString();

        if(selectedAnswer.equals(answerOne)){
            mAnswerOneButton.setChecked(true);
        }else if(selectedAnswer.equals(answerTwo)){
            mAnswerTwoButton.setChecked(true);
        }else if(selectedAnswer.equals(answerThree)){
            mAnswerThreeButton.setChecked(true);
        }else if(selectedAnswer.equals(answerFour)) {
            mAnswerFourButton.setChecked(true);
        }
    }

    private void restoreTrueFalseSelection(int questionIndex){
        String selectedAnswer = mSelectedAnswerList.valueAt(questionIndex);

        if(selectedAnswer.equals("true")){
            mTrueButton.setChecked(true);
        }else {
            mFalseButton.setChecked(true);
        }
    }


    private void finishIncompleteQuiz(int startingIndex){
        for (int i = startingIndex; i < mSelectedAnswerList.size(); i++){
            mSelectedAnswerList.put(i,"");
        }
    }


    public void startGame(ArrayList<Question> questions, boolean isTwoPane,int timeLimit) {
        mTwoPane = isTwoPane;
        if(questions != null) {
            mQuestionList = questions;
            mCurrentQuestion = questions.get(0);
            mSelectedAnswerList = new SparseArray<>(mQuestionList.size());
            if(timeLimit == -1) {
                mTimerView.setVisibility(View.GONE);
            }else {
                startTimer(timeLimit);
            }
            displayQuestion(mCurrentQuestion);
        }else {
            displayQuestion(null);
        }
    }
}
