package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class addMenteeActivity extends AppCompatActivity {

    String name;
    String year;
    String birthday;

    Button submitButton;
    EditText nameInput;
    EditText yearInput;
    EditText birthdayInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentee);
        nameInput = (EditText) findViewById(R.id.editTextTextPersonName);
        yearInput = (EditText) findViewById(R.id.editTextTextPersonName2);
        birthdayInput = (EditText) findViewById(R.id.editTextTextPersonName3);

        submitButton = (Button) findViewById(R.id.addMenteeButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                //When button clicked, saves data to shared preferences and adds to Singleton array
                name = nameInput.getText().toString();
                year = yearInput.getText().toString();
                birthday = birthdayInput.getText().toString();
                Singleton.getInstance().addToArray(new Mentee(name, year, birthday));
                saveData();
            }
        });
    }

    private void saveData() {                                 //Saves Mentee info to shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Singleton.getInstance().getArray());
        editor.putString("task list", json);
        editor.apply();
    }


}
