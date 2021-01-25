package com.example.thebloomapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText Password;
    private Button Login;
    private TextView NewUser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = findViewById(R.id.loginUserName);
        Password = findViewById(R.id.loginPassword);
        Login = findViewById(R.id.btlogin);
        NewUser = findViewById(R.id.tvNewUser);
        forgotPass = findViewById(R.id.tvForgotPass);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        progressDialog = new ProgressDialog(this);

        if(user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(UserName.getText().toString(), Password.getText().toString());
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });
    }


    public void validate(String name, String pass) {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        if(name.isEmpty() || pass.isEmpty()) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerify();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmailVerify() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            Toast.makeText(this, "Please Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}