package com.example.projectver3.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.projectver3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button btnNext, btnBack;
    EditText edtEmail, edtPass, edtRePass;

    String sEmail, sPass, sRePass;

    FirebaseAuth authRegister;
    ProgressBar progressBar;

//    public static final String KEY_PASSWORD = "password";
//    public static final String KEY_EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authRegister = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {

        //btn back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //btn Đăng ký
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail = edtEmail.getText().toString();
                sPass = edtPass.getText().toString();
                sRePass = edtRePass.getText().toString();

                register(sEmail, sPass, edtPass, edtRePass);
            }
        });
    }

    private void setControl() {
        btnNext = findViewById(R.id.btnNextRegister);
        btnBack = findViewById(R.id.btnBackRegister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtRePass = findViewById(R.id.edtRePass);
        progressBar =findViewById(R.id.progressBar);
    }

    private boolean checkEmail(String email){
        if (email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        else {
            edtEmail.setError("Email sai định dạng!");
        }
        return false;
    }

    private boolean checkPass(EditText editText){
        if (editText.getText().toString().trim().length() > 6)

            return true;
        else {

            editText.setError("Mật khẩu phải dài hơn 6 ký tự!");
        }

        return false;
    }

    private boolean checkRePass(EditText editText, String pass){
        if (editText.getText().toString().equals(pass))

            return true;
        else {

            editText.setError("Mật khẩu nhập lại không đúng!");
        }

        return false;
    }

    private void register(final String email, final String password, final EditText editTextPass, final EditText editTextRePass){
        //Kiểm tra định dạng email, mật khẩu nhập, mật khẩu nhập lại
        if (checkEmail(email) && checkPass(editTextPass) && checkPass(editTextRePass) && checkRePass(editTextRePass, password)){
            if (authRegister != null){
                progressBar.setVisibility(View.VISIBLE);
                //Thực hiện đăng ký với firebase
                authRegister.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setTitle("Đăng ký");
                            builder.setMessage("Đăng ký không thành công\nEmail đăng kýđã tồn tại !!!");
                            builder.show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}