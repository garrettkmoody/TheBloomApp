package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchMenteeActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private RecyclerView rvMenteeList;
    private DatabaseReference ref;
    private FirebaseAuth firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mentee);
        firebaseUser = FirebaseAuth.getInstance();

        searchInput = findViewById(R.id.etSearchMentee);
        searchButton = findViewById(R.id.btnSearchMentee);
        rvMenteeList = findViewById(R.id.rvMenteeList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        rvMenteeList.setHasFixedSize(true);
        rvMenteeList.setLayoutManager(new LinearLayoutManager(this));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchInput.getText().toString();
                firebaseUserSearch(searchText);
            }
        });

    }

    public void firebaseUserSearch(String search) {
        ref = firebaseDatabase.getReference();
        Query firebaseSearchQuery = ref.orderByChild("name").startAt(search).endAt(search + "\uf8ff");

        FirebaseRecyclerOptions<UserProfile> options = new FirebaseRecyclerOptions.Builder<UserProfile>().setQuery(firebaseSearchQuery, UserProfile.class).build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserProfile, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull UserProfile model) {

                holder.setDetails(model.getName(), model.getAge(), model.getEmail());

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout, parent, false);
                return new UsersViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        rvMenteeList.setAdapter(firebaseRecyclerAdapter);

    }

    //View Holder Class
    public class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(String userName, String bio, String img) {

            TextView user_name = mView.findViewById(R.id.tvListName);
            TextView user_bio = mView.findViewById(R.id.tvBio);
            ImageView profilePic = mView.findViewById(R.id.ivProfilePic);

            user_name.setText(userName);
            user_bio.setText(bio);
            //Will add image link here
        }
    }
}



