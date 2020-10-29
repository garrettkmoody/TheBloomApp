package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText age;
    private Button saveProfile;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.etProfileName);
        email = (EditText) findViewById(R.id.etProfileEmail);
        age = (EditText) findViewById(R.id.etProfileAge);
        saveProfile = (Button) findViewById(R.id.btnSaveProfile);
        firebaseAuth = FirebaseAuth.getInstance();
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || age.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendUserData();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                }
            }
        });
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        myRef.setValue(new UserProfile(name.getText().toString(), email.getText().toString(), age.getText().toString()));
        Toast.makeText(ProfileActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
    }
}