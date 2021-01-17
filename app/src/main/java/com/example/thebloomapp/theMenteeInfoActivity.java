package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class theMenteeInfoActivity extends AppCompatActivity {
String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_mentee_info);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        Button button = findViewById(R.id.menteeInfoButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(theMenteeInfoActivity.this, uid, Toast.LENGTH_SHORT).show();
            }
        });
    }
}