package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class searchMenteeActivity extends AppCompatActivity {

    private EditText SearchInput;
    private Button searchButton;
    private RecyclerView rvMenteeList;
    private DatabaseReference UserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mentee);
        UserDatabase = FirebaseDatabase.getInstance().getReference("Users");
        SearchInput = (EditText) findViewById(R.id.etSearchMentee);
        searchButton = (Button) findViewById(R.id.btnSearchMentee);
        rvMenteeList = (RecyclerView) findViewById(R.id.rvMenteeList);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

