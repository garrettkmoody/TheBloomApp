package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CoachDialog.CoachDialogListener{


    private static final String TAG = "MainActivity";
    private ImageButton button;
    private ImageButton button1;
    private TextView notMenteeTV;
    private Button logoutbtn;
    private Button editProfile;
    private Boolean ISCOACH = false;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCoachData();
        firebaseAuth = FirebaseAuth.getInstance();
        button1 = (ImageButton) findViewById(R.id.addMentee);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMenteeActivity();
            }
        });
        button = (ImageButton) findViewById(R.id.getMentees);
        notMenteeTV = (TextView) findViewById(R.id.becomeCoachTV);
        editProfile = (Button) findViewById(R.id.btnEditProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGetMenteeActivity();
            }
        });
        logoutbtn = (Button) findViewById(R.id.btnlogout);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        notMenteeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCoachConsole();
            }
        });

    }

    public void openCoachConsole() {
        CoachDialog coachDialog = new CoachDialog();
        coachDialog.show(getSupportFragmentManager(), "Coach Dialog");
    }

    public void getCoachData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                if(userProfile.getIsacoach()) {
                    ISCOACH = true;
                    notMenteeTV.setVisibility(TextView.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openGetMenteeActivity() {
        Animation shake = AnimationUtils.loadAnimation(button.getContext(), R.anim.fade);
        button.startAnimation(shake);
        if(ISCOACH) {
            Intent intent = new Intent(MainActivity.this, addMockUser.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, theMenteeInfoActivity.class);
            startActivity(intent);
        }
    }

    public void openAddMenteeActivity() {
        Animation shake = AnimationUtils.loadAnimation(button1.getContext(), R.anim.fade);
        button1.startAnimation(shake);
        if(ISCOACH) {
            Intent intent = new Intent(this,searchMenteeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Must be a Coach to access Mentee Search", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void checkKey(String key) {
        if(key.equals("tjf44gbap77grt")) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
            Map<String, Object> update = new HashMap<>();
            update.put("isacoach", true);
            databaseReference.updateChildren(update);
            Toast.makeText(MainActivity.this, "Coach Profile Successful", Toast.LENGTH_SHORT).show();
            notMenteeTV.setVisibility(TextView.GONE);
        } else {
            Toast.makeText(MainActivity.this, "Incorrect Key", Toast.LENGTH_SHORT).show();
        }
    }
}


