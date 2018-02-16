package com.ismaeldeka.TrivialTrivia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ismaeldeka.TrivialTrivia.R;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnGameClickListener {

    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MasterListFragment listFragment = new MasterListFragment();

        if(findViewById(R.id.trivia_fragment_tablet) != null){
            mTwoPane = true;


            ListView masterList = (ListView) findViewById(R.id.master_list);
            masterList.setAdapter(null); // create new adapter for Opening menu

        }
        else {
            mTwoPane = false;
        }


    }

    @Override
    public void onGameClicked(int position) {
        Intent i = new Intent(this,TriviaActivity.class);

        startActivity(i);


    }


}
