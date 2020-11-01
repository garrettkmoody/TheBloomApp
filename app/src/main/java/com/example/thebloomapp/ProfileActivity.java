package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Age;
    private Button saveProfile;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = (EditText) findViewById(R.id.etProfileName);
        Email = (EditText) findViewById(R.id.etProfileEmail);
        Age = (EditText) findViewById(R.id.etProfileAge);
        saveProfile = (Button) findViewById(R.id.btnSaveProfile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || Age.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendUserData();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                }
            }

        });


            DatabaseReference myReference = firebaseDatabase.getReference(firebaseAuth.getUid());
            myReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        UserProfile userProfile = snapshot.getValue(UserProfile.class);
                        Name.setText(userProfile.getName());
                        Email.setText(userProfile.getEmail());
                        Age.setText(userProfile.getAge());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


    }
    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        myRef.setValue(new UserProfile(Name.getText().toString(), Email.getText().toString(), Age.getText().toString()));
        Toast.makeText(ProfileActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
    }
}