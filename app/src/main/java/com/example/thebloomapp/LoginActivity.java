package com.example.thebloomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText Password;
    private Button Login;
    private TextView NewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = (EditText) findViewById(R.id.loginUserName);
        Password = (EditText) findViewById(R.id.loginPassword);
        Login = (Button) findViewById(R.id.btlogin);
        NewUser = (TextView) findViewById(R.id.tvNewUser);


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
    }


    public void validate(String name, String pass) {
        if (name.equals("Big") && pass.equals("1234")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}