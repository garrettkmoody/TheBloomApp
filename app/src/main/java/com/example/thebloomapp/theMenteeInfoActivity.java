package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class theMenteeInfoActivity extends AppCompatActivity {
    String uid;
    public static List<String> listGroup;
    HashMap<String, List<String>> listItem;
    public static MainAdapter adapter;
    Boolean ISCOACH = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_mentee_info);


        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        final ImageView profile = findViewById(R.id.menteeProfile);
        final TextView name = findViewById(R.id.menteeInfoName);
        final EditText establishment = findViewById(R.id.etEstablish);
        final Spinner services = findViewById(R.id.serviceSpin);
        final Spinner tests = findViewById(R.id.testSPIN);
        final EditText scoreET = findViewById(R.id.addScoreET);
        final TextView establishmentTV = findViewById(R.id.hiddenTV);
        final Button saveGoal = findViewById(R.id.addGoalbt);
        final TextView notesTV = findViewById(R.id.notesTV);
        final TextView serviceTV = findViewById(R.id.serviceTV);
        final EditText noteTextArea = findViewById(R.id.noteTA);
        final EditText coachET = findViewById(R.id.coachEt);
        final EditText goalET = findViewById(R.id.etAddGoal);
        ExpandableListView expandableListView = findViewById(R.id.expandListView);
        listGroup = new ArrayList<>();
        final List<String> testScores = new ArrayList<>();

        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        Button saveInfo = findViewById(R.id.saveMenteeInfoBT);

        noteTextArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        tests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scoreET.setText(testScores.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        scoreET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (tests.getSelectedItem().toString()) {
                    case "SAT":
                        testScores.set(0,scoreET.getText().toString());
                        break;
                    case "ACT":
                        testScores.set(1,scoreET.getText().toString());
                        break;
                    case "GPA":
                        testScores.set(2,scoreET.getText().toString());
                        break;
                    case "PSAT":
                        testScores.set(3,scoreET.getText().toString());
                        break;
                    case "ITBS":
                        testScores.set(4,scoreET.getText().toString());
                        break;
                }
            }
        });


        final Button editInfo = findViewById(R.id.pencilBT);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.toggleDeletes();
                adapter.notifyDataSetChanged();
                Animation shake = AnimationUtils.loadAnimation(editInfo.getContext(), R.anim.fade);
                editInfo.startAnimation(shake);
                if(establishment.getVisibility() == EditText.INVISIBLE) {
                    if(ISCOACH) {
                        noteTextArea.setEnabled(true);
                        scoreET.setEnabled(true);
                        goalET.setEnabled(true);
                        coachET.setEnabled(true);
                    }
                    establishmentTV.setVisibility(TextView.INVISIBLE);
                    establishment.setVisibility(EditText.VISIBLE);
                } else {
                    establishmentTV.setVisibility(TextView.VISIBLE);
                    establishment.setVisibility(EditText.INVISIBLE);
                    noteTextArea.setEnabled(false);
                    scoreET.setEnabled(false);
                    goalET.setEnabled(false);
                    coachET.setEnabled(false);
                }
            }
        });

        saveGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goalET.getText().toString().trim().equals("")) {
                    Toast.makeText(theMenteeInfoActivity.this, "Please enter a Goal", Toast.LENGTH_SHORT).show();
                } else {
                    listGroup.add(goalET.getText().toString());
                    goalET.setText(null);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference(uid);
                Map<String, Object> update = new HashMap<>();
                List<String> goals = listGroup;
                List<String> scores = testScores;
                if(ISCOACH) {
                    update.put("service", services.getSelectedItem());
                    update.put("goals", goals);
                    update.put("scores", scores);
                    update.put("coach", coachET.getText().toString().trim());
                    update.put("notes", noteTextArea.getText().toString());
                }
                update.put("establishment", establishment.getText().toString().trim());
                myRef.updateChildren(update);
            }
        });

        final ArrayAdapter<CharSequence> testAdapter = ArrayAdapter.createFromResource(this, R.array.tests, R.layout.spinner_item);
        testAdapter.setDropDownViewResource(R.layout.inspinner);
        tests.setAdapter(testAdapter);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.services, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.inspinner);
        services.setAdapter(adapter);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserProfile tempProfile = snapshot.getValue(UserProfile.class);
                    Picasso.get().load(tempProfile.getLink()).fit().centerCrop().transform(new CircleTransform()).into(profile);
                    name.setText(tempProfile.getName());
                    establishment.setText(tempProfile.getEstablishment());
                    establishmentTV.setText(tempProfile.getEstablishment());
                    noteTextArea.setText(tempProfile.getNotes());
                    coachET.setText(tempProfile.getCoach());
                    ISCOACH = tempProfile.getIsacoach();
                    if(ISCOACH) {
                        services.setVisibility(Spinner.VISIBLE);
                        serviceTV.setVisibility(TextView.VISIBLE);
                        notesTV.setVisibility(TextView.VISIBLE);
                        noteTextArea.setVisibility(EditText.VISIBLE);
                        goalET.setVisibility(EditText.VISIBLE);
                        saveGoal.setVisibility(Button.VISIBLE);
                    }
                    if(tempProfile.getScores() != null) {
                        List<String> hold = tempProfile.getScores();
                        testScores.clear();
                        for(String temp : hold) {
                            testScores.add(temp);
                        }
                    }

                    if(tempProfile.getGoals() != null) {
                        List<String> hold = (List<String>) tempProfile.getGoals();
                        listGroup.clear();
                        for (String temp : hold) {
                            listGroup.add(temp);
                        }
                    }

                    String[] serviceArr = getResources().getStringArray(R.array.services);
                    for(int i = 0;  i < serviceArr.length; i++) {
                        if(serviceArr[i].equals(tempProfile.getService())) {
                            services.setSelection(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(theMenteeInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}