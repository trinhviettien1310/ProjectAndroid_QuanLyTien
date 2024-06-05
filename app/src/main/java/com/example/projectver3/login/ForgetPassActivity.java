package com.example.projectver3.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import com.example.projectver3.R;
import com.example.projectver3.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {
    Button btnBackQMK, btnNext;
    EditText edtEmail;
    ProgressBar progressBar;
    FirebaseAuth authForgetPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        authForgetPass = FirebaseAuth.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {

        //Button quay lại
        btnBackQMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //button Next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword(edtEmail.getText().toString());
            }
        });
    }



    private void setControl() {
        btnBackQMK = findViewById(R.id.btnBackQMK);
        btnNext = findViewById(R.id.btnNextQMK);
        edtEmail = findViewById(R.id.edtEmailQMK);
        progressBar = findViewById(R.id.progressBar);
    }
    
    //Hàm check email có tồn tại không 
    private boolean checkEmailForgetPass(String sEmailProgetPass){
        for (User user : LoginActivity.arrUser) {
            if (sEmailProgetPass.equals(user.geteMail())){
                return true;
            }
        }
        return false;
    }

    private boolean checkEmail(String email){
        if (email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
            return true;
        else {
            edtEmail.setError("Email sai định dạng!");
        }
        return false;
    }

    private void resetPassword(String stringEmail) {
        if (stringEmail.isEmpty()) {
            edtEmail.setError("Hãy nhập email của bạn !!!");
        } else if (!checkEmail(stringEmail)) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        authForgetPass.sendPasswordResetEmail(stringEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ForgetPassActivity.this);
                            builder.setTitle("Cập nhật mật khẩu mới");
                            builder.setMessage("\nMật khẩu mới của bạn đã được gửi về email!!!\n");
                            builder.show();
                        } else {

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}