package com.example.projectver3.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.R;
import com.example.projectver3.model.DanhMuc;
import com.example.projectver3.model.User;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //imageview
    ImageView ivHien, ivAn;

    //Button
    Button btnBack, btnQMK, btnLogin;
    //Edittext
    EditText edtEmailDN, edtPassDN;
    //ProgressBar
    ProgressBar progressBar;
    //Firebase
    FirebaseAuth authLogin;
    private CallbackManager mCallbackManager;
    public static String currenEmailUser;
    //list giao dich
    public static ArrayList<DanhMuc> danhMucList = new ArrayList<>();

    public static ArrayList<User> arrUser = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authLogin = FirebaseAuth.getInstance();
        setControl();
        //getDataUser();
        setEvent();
    }

    private void setEvent() {

        //Button Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Button Quên Mật Khẩu
        btnQMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });

        //Button Đăng Nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gọi hàm kiểm tra đăng nhập
                checkLogin(edtEmailDN.getText().toString(), edtPassDN.getText().toString());
            }
        });

        //Ẩn hiện password
        ivAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAn.setVisibility(View.INVISIBLE);
                ivHien.setVisibility(View.VISIBLE);
                edtPassDN.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        });

        ivHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAn.setVisibility(View.VISIBLE);
                ivHien.setVisibility(View.INVISIBLE);
                edtPassDN.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }


    private boolean checkEmail(String email){
        if (email.matches("[a-zA-Z0-9._-]+@gmail.com+") || email.matches("[a-zA-Z0-9._-]+@mail.tdc.edu.vn+"))
            return true;
        else {
            edtEmailDN.setError("Email không đúng định dạng !!!");
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

    private void checkLogin(String email, String pass){

        if (!checkEmail(email) || !checkPass(edtPassDN)){
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        authLogin.signInWithEmailAndPassword(email, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("ĐĂNG NHẬP KHÔNG THÀNH CÔNG");
                    builder.setMessage("\nSai tên tài khoản hoặc mật khẩu!!!\n");
                    builder.show();
                } else {
                    FirebaseUser firebaseUser = authLogin.getCurrentUser();
                    currenEmailUser = firebaseUser.getEmail();
                    Intent intent = new Intent(LoginActivity.this, otpActivity.class);
                    startActivity(intent);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setControl() {

        progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);
        btnQMK = findViewById(R.id.btnQMK);
        edtEmailDN = findViewById(R.id.edtEmailDN);
        edtPassDN = findViewById(R.id.edtPassDN);
        btnLogin = findViewById(R.id.btnLoginLogin);
        ivAn = findViewById(R.id.ivAn);
        ivHien = findViewById(R.id.ivHien);

    }
}