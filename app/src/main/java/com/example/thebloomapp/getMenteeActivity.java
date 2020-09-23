package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;


public class getMenteeActivity extends AppCompatActivity {


    private static final String TAG = "getMenteeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_mentee);
        ListView mListView = (ListView) findViewById(R.id.listView);


        MenteeListAdapter adapter = new MenteeListAdapter(this, R.layout.adapter_view_layout, Singleton.getInstance().getArray() );
        mListView.setAdapter(adapter);
    }
}