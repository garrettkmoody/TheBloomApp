package com.moody.thebloomapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}

public class ProfileActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Age;
    private ImageView profilePic;
    private Button saveProfile;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private static final int PICK_IMAGE = 123;
    Uri imagePath;
    String profileLinker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {       //Delete this try and catch and see what happens when you have time
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                //profilePic.setImageBitmap(bitmap);
                Picasso.get().load(imagePath).fit().centerCrop().transform(new CircleTransform()).into(profilePic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Name = findViewById(R.id.etProfileName);
        Email = findViewById(R.id.etProfileEmail);
        Age = findViewById(R.id.etProfileAge);
        profilePic = findViewById(R.id.profilePiciv);
        saveProfile = findViewById(R.id.btnSaveProfile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().transform(new CircleTransform()).into(profilePic);
                profileLinker = String.valueOf(uri);
            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || Age.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    sendUserData();
                    finish();
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
        final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
        Map<String, Object> update1 = new HashMap<>();
        update1.put("name", Name.getText().toString());
        update1.put("email", Email.getText().toString());
        update1.put("age", Age.getText().toString());
        update1.put("link", profileLinker);
        myRef.updateChildren(update1);
        if(imagePath != null) {

            UploadTask uploadTask = imageRef.putFile(imagePath);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profileLinker = String.valueOf(uri);
                            Map<String, Object> update = new HashMap<>();
                            update.put("name", Name.getText().toString());
                            update.put("email", Email.getText().toString());
                            update.put("age", Age.getText().toString());
                            update.put("link", profileLinker);
                            myRef.updateChildren(update);
                        }
                    });
                }
            });
        }


        Toast.makeText(ProfileActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
    }
}