package com.moody.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPass;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        passwordEmail = findViewById(R.id.etPassEmail);
        resetPass = findViewById(R.id.btnResetPass);
        firebaseAuth = FirebaseAuth.getInstance();
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = passwordEmail.getText().toString().trim();
                if(userEmail.equals("")) {
                    Toast.makeText(ForgotPassActivity.this, "Please enter your email associated with your account", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ForgotPassActivity.this, "Sent password reset successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(ForgotPassActivity.this, "Password Reset Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}