package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Context;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.ismaeldeka.TrivialTrivia.Question;

import com.ismaeldeka.TrivialTrivia.TriviaUtils;
import com.example.trivialtrivia.R;

import java.util.ArrayList;


public class MasterListFragment extends Fragment {


    private ListView mMasterList;
    private OnItemClickListener mCallback;

    public interface OnItemClickListener {
        void onItemClicked(int position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnItemClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement OnItemClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_master_list, container, false);

        mMasterList = (ListView) rootView.findViewById(R.id.master_list);

        setGameList();

        return rootView;
    }

    public void setGameList(){
        ArrayList<String> games = TriviaUtils.getGameTypeList();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.list_item,games);

        mMasterList.setAdapter(arrayAdapter);

        mMasterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onItemClicked(i);
            }
        });
    }

    public void setQuestionList(ArrayList<Question> questions){

        ArrayList<String> questionList = new ArrayList<>();
        int questionNumber;
        for(Question q : questions){
            questionNumber = questions.indexOf(q)+1;
            questionList.add(Html.fromHtml(questionNumber +".) "+q.getQuestion()).toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.list_item,questionList);
        mMasterList.setAdapter(adapter);

        mMasterList.setOnItemClickListener(null);



    }



}
