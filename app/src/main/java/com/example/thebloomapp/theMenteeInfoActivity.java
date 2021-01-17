package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class theMenteeInfoActivity extends AppCompatActivity {
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_mentee_info);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        final ImageView profile = findViewById(R.id.menteeProfile);
        final TextView name = findViewById(R.id.menteeInfoName);
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserProfile tempProfile = snapshot.getValue(UserProfile.class);
                    Picasso.get().load(tempProfile.getLink()).fit().centerCrop().transform(new CircleTransform()).into(profile);
                    name.setText(tempProfile.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(theMenteeInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(theMenteeInfoActivity.this, uid, Toast.LENGTH_SHORT).show();
//                final DatabaseReference myRef = firebaseDatabase.getReference(uid);
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                        System.out.println(snapshot.getValue(UserProfile.class).getName()); }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(theMenteeInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Map<String, Object> update = new HashMap<>();
//                update.put("name", "Billy Tow");
//                myRef.updateChildren(update);
//                finish();
//                startActivity(new Intent(theMenteeInfoActivity.this, MainActivity.class));
//            }
//        });
    }
}