package com.ismaeldeka.TrivialTrivia.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismaeldeka.TrivialTrivia.R;


public class TriviaListFragment extends Fragment {


    public TriviaListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia_list, container, false);
    }

}
