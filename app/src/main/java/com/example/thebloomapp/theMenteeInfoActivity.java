package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
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
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

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
        ExpandableListView expandableListView = findViewById(R.id.expandListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();
        Button saveInfo = findViewById(R.id.saveMenteeInfoBT);
        Button editInfo = findViewById(R.id.pencilBT);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establishment.getVisibility() == EditText.INVISIBLE) {
                    establishmentTV.setVisibility(TextView.INVISIBLE);
                    establishment.setVisibility(EditText.VISIBLE);
                } else {
                    establishmentTV.setVisibility(TextView.VISIBLE);
                    establishment.setVisibility(EditText.INVISIBLE);
                }
            }
        });

        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference(uid);
                Map<String, Object> update = new HashMap<>();
                update.put("establishment", establishment.getText().toString().trim());
                update.put("service", services.getSelectedItem());
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


    private void initListData() {
        listGroup.add(getString(R.string.goal1));
        listGroup.add(getString(R.string.goal2));
        listGroup.add(getString(R.string.goal3));
        listGroup.add(getString(R.string.goal4));
        listGroup.add(getString(R.string.goal5));

        String[] array;
        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.goal1);
        for(String item : array) {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.goal2);
        for(String item : array) {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.goal3);
        for(String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.goal4);
        for(String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.goal5);
        for(String item : array) {
            list5.add(item);
        }

        listItem.put(listGroup.get(0), list1);
        listItem.put(listGroup.get(1), list2);
        listItem.put(listGroup.get(2), list3);
        listItem.put(listGroup.get(3), list4);
        listItem.put(listGroup.get(4), list5);
        adapter.notifyDataSetChanged();
    }
}