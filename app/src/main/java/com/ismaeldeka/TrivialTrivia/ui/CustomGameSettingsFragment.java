package com.ismaeldeka.TrivialTrivia.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ismaeldeka.TrivialTrivia.R;
import com.ismaeldeka.TrivialTrivia.TriviaUtils;


public class CustomGameSettingsFragment extends Fragment {

    private String mSelectedCategory;
    private String mSelectedDifficulty;
    private EditText mNumQuestionsView;
    CustomGameCallback mCallback;


    public interface CustomGameCallback{
        void gameSelected(Bundle gameParams);
    }

    public CustomGameSettingsFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (CustomGameCallback) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_custom_game_settings, container, false);
        Spinner categorySpinner = rootView.findViewById(R.id.spinner_category);
        Spinner difficultySpinner = rootView.findViewById(R.id.spinner_difficulty);
        mNumQuestionsView = rootView.findViewById(R.id.edit_num_questions);
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


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                int numQuestionsSelected = Integer.valueOf(mNumQuestionsView.getText().toString())+1;
                bundle.putString(getString(R.string.category),mSelectedCategory);
                bundle.putString(getString(R.string.difficulty),mSelectedDifficulty);
                bundle.putInt(getString(R.string.num_questions),numQuestionsSelected);
                mCallback.gameSelected(bundle);
            }
        });


        return rootView;
    }


}
