package com.example.mobileprogrammingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignIn,btnSignup, LoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        LoginButton = findViewById(R.id.button4);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignIn = findViewById(R.id.btnSignIn);



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),getString(R.string.successsignin), Toast.LENGTH_LONG).show();
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(v.getContext(), SignInActivity.class);
                startActivity(intent);
            }
        });


    }



}
