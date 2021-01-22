package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class theMenteeInfoActivity extends AppCompatActivity {
    String uid;
    public static List<String> listGroup;
    HashMap<String, List<String>> listItem;
    public static MainAdapter adapter;


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
        final TextView establishmentTV = findViewById(R.id.hiddenTV);
        Button saveGoal = findViewById(R.id.addGoalbt);
        final EditText goalET = findViewById(R.id.etAddGoal);
        ExpandableListView expandableListView = findViewById(R.id.expandListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        Button saveInfo = findViewById(R.id.saveMenteeInfoBT);
        final Button editInfo = findViewById(R.id.pencilBT);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.toggleDeletes();
                adapter.notifyDataSetChanged();
                Animation shake = AnimationUtils.loadAnimation(editInfo.getContext(), R.anim.fade);
                editInfo.startAnimation(shake);
                if(establishment.getVisibility() == EditText.INVISIBLE) {
                    establishmentTV.setVisibility(TextView.INVISIBLE);
                    establishment.setVisibility(EditText.VISIBLE);
                } else {
                    establishmentTV.setVisibility(TextView.VISIBLE);
                    establishment.setVisibility(EditText.INVISIBLE);
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
                update.put("establishment", establishment.getText().toString().trim());
                update.put("service", services.getSelectedItem());
                update.put("goals", goals);
                myRef.updateChildren(update);
            }
        });

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
                    if(tempProfile.getGoals() != null) {
                        List<String> hold = (List<String>) tempProfile.getGoals();
                        listGroup.clear();
                        for (String temp : hold) {
                            listGroup.add(temp);
                        }
                    }
                    adapter.notifyDataSetChanged();
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