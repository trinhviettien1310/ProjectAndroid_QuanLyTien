package com.example.projectver3.login;

import static com.example.projectver3.fragment.CateFragment.DocDLDM;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.HomeActivity;
import com.example.projectver3.R;
import com.example.projectver3.giaodich.ExpenseActivity;
import com.example.projectver3.model.Mail;


public class otpActivity extends AppCompatActivity {
    Button btnGui, btnGuiLai;
    ProgressBar progressBar;
    EditText edtOtp;
    String sReceiverEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setControl();
        setEvent();
    }

    private void setEvent() {
        Intent intent = getIntent();

        sReceiverEmail = LoginActivity.currenEmailUser;
        //Gửi mã otp về mail
        Mail.sendEmailOTP(sReceiverEmail);
        HomeActivity.DocDL();
        ExpenseActivity.docDLDMGD(LoginActivity.currenEmailUser);
        DocDLDM();

        //btn Xác nhận
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkOTP(edtOtp.getText().toString(), Mail.otp)){
                    ExpenseActivity.docDLDMGD(LoginActivity.currenEmailUser);
                    Intent intentOTP = new Intent(otpActivity.this, HomeActivity.class);
                    startActivity(intentOTP);
                }
            }
        });

        //btn Gửi lại mã otp
        btnGuiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Log.d("otp2", Mail.otp);
                Mail.sendEmailOTP(sReceiverEmail);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setControl() {

        progressBar = findViewById(R.id.progressBar);
        btnGui = findViewById(R.id.btnOtpGui);
        btnGuiLai = findViewById(R.id.btnReOtp);
        edtOtp = findViewById(R.id.edtOtp);
    }

    private boolean checkOTP(String sOTP, String sReOTP){
        if (sOTP.equals(sReOTP)){
            return true;
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(otpActivity.this);
            builder.setTitle("Xác thực OTP");
            builder.setMessage("Mã OTP không đúng!!!");
            builder.show();
        }
        return false;
    }
}