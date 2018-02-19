package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ismaeldeka.TrivialTrivia.R;

import java.util.ArrayList;


public class MasterListFragment extends Fragment {


    private OnGameClickListener mCallback;

    public interface OnGameClickListener {
        void onGameClicked(int position);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnGameClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement OnGameClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_master_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.master_list);

        ArrayList<String> game = new ArrayList<>();

        game.add("Custom Game");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),R.layout.list_item,game);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCallback.onGameClicked(i);
            }
        });

        return rootView;
    }


}
