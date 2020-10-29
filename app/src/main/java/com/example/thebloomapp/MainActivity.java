package com.example.thebloomapp;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private ImageButton button;
    private ImageButton button1;
    private Button logoutbtn;
    private Button editProfile;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        firebaseAuth = FirebaseAuth.getInstance();
        button1 = (ImageButton) findViewById(R.id.addMentee);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMenteeActivity();
            }
        });
        button = (ImageButton) findViewById(R.id.getMentees);
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


    }

    public void openGetMenteeActivity() {
        Intent intent = new Intent(this, getMenteeActivity.class);
        startActivity(intent);
    }

    public void openAddMenteeActivity() {
        Intent intent = new Intent(this,addMenteeActivity.class);
        startActivity(intent);
    }

    public void taptofade(View view) {
        ImageButton imageButton=(ImageButton) findViewById(R.id.addMentee);
        Animation animation= AnimationUtils.loadAnimation( this, R.anim.fade);
        imageButton.startAnimation(animation);

    }

    public void taptofade1(View view) {
        ImageButton imageButton=(ImageButton) findViewById(R.id.getMentees);
        Animation animation= AnimationUtils.loadAnimation( this, R.anim.fade);
        imageButton.startAnimation(animation);

    }
    private void loadData() {
        ArrayList<Mentee> tempArray;
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Mentee>>() {}.getType();
        tempArray = gson.fromJson(json, type);
        if(tempArray == null) {
            return;
        }
        for (int i = 0; i <tempArray.size(); i++) {
            Singleton.getInstance().addToArray(tempArray.get(i));
        }
    }

}


