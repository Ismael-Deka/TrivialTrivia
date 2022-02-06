package com.ismaeldeka.TrivialTrivia.ui;


import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trivialtrivia.R;
import com.ismaeldeka.TrivialTrivia.TriviaUtils;


public class CustomGameSettingsFragment extends Fragment {

    private String mSelectedCategory;
    private String mSelectedDifficulty;
    private EditText mNumQuestionsView;
    private EditText mTimeLimitView;
    private boolean isTimeLimited = false;
    private CustomGameCallback mCallback;
    private Context mContext;


    public interface CustomGameCallback{
        void gameSelected(Bundle gameParams);
    }

    public CustomGameSettingsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (CustomGameCallback) context;
            mContext = context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement CustomCallbackListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_game_settings, container, false);
        Spinner categorySpinner = rootView.findViewById(R.id.spinner_category);
        Spinner difficultySpinner = rootView.findViewById(R.id.spinner_difficulty);
        mNumQuestionsView = rootView.findViewById(R.id.edit_num_questions);
        mTimeLimitView = rootView.findViewById(R.id.timer_length);
        CheckBox timeLimitCheckBox = rootView.findViewById(R.id.timer_checkbox);
        Button button = rootView.findViewById(R.id.game_settings_button);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(rootView.getContext(),R.layout.list_item, TriviaUtils.getCategoryList());
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(rootView.getContext(),R.layout.list_item, TriviaUtils.getDifficultyList());

        categorySpinner.setAdapter(categoryAdapter);
        difficultySpinner.setAdapter(difficultyAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedCategory = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mSelectedCategory = null;
            }
        });

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedDifficulty = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mSelectedDifficulty = null;
            }
        });

        timeLimitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean isChecked) {
                if(isChecked){
                    mTimeLimitView.setVisibility(View.VISIBLE);
                    isTimeLimited = true;
                }else{
                    mTimeLimitView.setVisibility(View.GONE);
                    isTimeLimited = false;
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String rawTimeLimit = mTimeLimitView.getText().toString();
                int numQuestionsSelected = Integer.valueOf(mNumQuestionsView.getText().toString());
                int timeLimit = -1;
                if(isTimeLimited && !rawTimeLimit.equals("")){
                    timeLimit =  Integer.valueOf(rawTimeLimit);
                }

                if(numQuestionsSelected <= 50 && numQuestionsSelected >= 1) {
                    bundle.putString(getString(R.string.category), mSelectedCategory);
                    bundle.putString(getString(R.string.difficulty), mSelectedDifficulty);
                    bundle.putInt(getString(R.string.num_questions), numQuestionsSelected);
                    bundle.putInt(getString(R.string.time_limit),timeLimit);
                    mCallback.gameSelected(bundle);
                }else{
                    Toast.makeText(mContext,"Number of questions must be between 1-50.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return rootView;
    }


}
