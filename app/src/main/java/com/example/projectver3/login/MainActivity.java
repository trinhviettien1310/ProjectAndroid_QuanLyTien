package com.example.projectver3.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //layout
    Button btnLogin, btnRegister, btnLoginGG, btnLoginFB;
    ProgressBar progressBar;
    //firebase



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get Firebase instance

        //ánh xạ
        setControl();
        //sự kiện
        setEvent();

    }
    private void setEvent() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
            }
        });

        btnLoginGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //signWithGG();
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setControl() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        btnLoginGG = findViewById(R.id.btnLoginGG);
    }

}