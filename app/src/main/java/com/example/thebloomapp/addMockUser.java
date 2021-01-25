package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addMockUser extends AppCompatActivity {
    private EditText nameET;
    private EditText emailET;
    private Button addMenteeBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mock_user);
        nameET = findViewById(R.id.etNAME);
        emailET = findViewById(R.id.etEMAIL);
        addMenteeBT = findViewById(R.id.saveNewMenteeBT);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        addMenteeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()) {
                    Map<String, Object> update = new HashMap<>();
                    Map<String, Object> inside = new HashMap<>();
                    List<String> scores = new ArrayList<>(Collections.nCopies(5, ""));
                    inside.put("name",nameET.getText().toString().trim());
                    inside.put("email",emailET.getText().toString().trim());
                    inside.put("scores", scores);
                    inside.put("uid", nameET.getText().toString().trim() + emailET.getText().toString().length());
                    update.put(nameET.getText().toString().trim() + emailET.getText().toString().length(), null);
                    databaseReference.updateChildren(update);
                    DatabaseReference insideRef;
                    insideRef = firebaseDatabase.getReference(nameET.getText().toString().trim() + emailET.getText().toString().length());
                    insideRef.updateChildren(inside).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(addMockUser.this, "Successfully added Mentee", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(addMockUser.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean validate() {
        return !nameET.getText().toString().isEmpty() && !emailET.getText().toString().isEmpty();
    }
}