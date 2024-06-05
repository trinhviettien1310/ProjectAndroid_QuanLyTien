package com.example.projectver3;



import static com.example.projectver3.fragment.AcountFragment.DocDLAccount;
import static com.example.projectver3.fragment.AcountFragment.data_Account;
import static com.example.projectver3.fragment.AcountFragment.data_account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.adapter.IconAdapter;
import com.example.projectver3.fragment.AcountFragment;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.Account;

import java.util.ArrayList;
import java.util.List;

public class Add_Update_AccountActivity extends AppCompatActivity {
    private static final int YOUR_REQUEST_CODE = 1;
    public static TextView tvToolBar, tvCheckIcon, tvCheckColor;
    EditText edtMoney,edtNameAccount,edtIDAccount;
    Button btnBack, btnSelectColor,btnAddAccount,btnDeleteAccount;
    IconAdapter iconAdapter;
    RecyclerView revCategoryExpense;
    public static Account account = new Account();

    public static View viewColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_account);
        setControl();
        setEvent();
    }
    private void setControl() {
        tvToolBar = findViewById(R.id.tvToolBar);
        btnBack = findViewById(R.id.btnBackAddAccount);
        btnAddAccount = findViewById(R.id.btnAddAccount);
        edtIDAccount = findViewById(R.id.edtIDAccount);
        edtMoney = findViewById(R.id.edtMoney);
        edtNameAccount = findViewById(R.id.edtNameAccount);
        revCategoryExpense = findViewById(R.id.revCategoryExpense);
        btnSelectColor = findViewById(R.id.btnSelectColor);
        viewColor = findViewById(R.id.viewColor);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        tvCheckIcon = findViewById(R.id.tvCheckIcon);
        tvCheckColor = findViewById(R.id.tvCheckColor);

    }

    private void setEvent() {
        khoiTaoIcon();
        Boolean sflag = getIntent().getBooleanExtra("isUpdateAccount",false);
        //update
        if (sflag != false){
            tvToolBar.setText("Chỉnh sửa tài khoản");
            btnAddAccount.setText("Lưu");
            if (getIntent().getExtras() != null){
                Account a = (Account) getIntent().getExtras().get("accountUpdate");
                edtIDAccount.setText(String.valueOf(a.getIdAccount()));
                edtMoney.setText(String.valueOf(a.getMoneyAccount()));
                edtNameAccount.setText(a.getNameAccount());
                viewColor.setBackgroundColor(Color.parseColor(a.getColorAccount()));
            }
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //update
            btnAddAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAccount();

                    finish();
                }
            });
            //delete
            btnDeleteAccount.setText("Xóa");
            btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data_account.child(edtIDAccount.getText().toString()).removeValue();
                    DocDLAccount();
                    finish();
                }
            });
        }
        //Create
        else{
            CreateAccount();
            tvToolBar.setText("Thêm tài khoản");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            btnAddAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkAccount() == true){
                        if (!data_Account.isEmpty()){
                            Account a1 = data_Account.get(data_Account.size() -1);
                            int lastIndexList = a1.getIdAccount() + 1;
                            account.setIdAccount(lastIndexList);
                            account.setMoneyAccount(Integer.valueOf(edtMoney.getText().toString()));
                            account.setNameAccount(edtNameAccount.getText().toString());
                            account.seteMail(LoginActivity.currenEmailUser);
                            //data_Account.add(account);
                            data_account.child(String.valueOf(account.getIdAccount())).setValue(account);
                            finish();
                        }
                        else{
                            account.setIdAccount(0);
                            account.setMoneyAccount(Integer.valueOf(edtMoney.getText().toString()));
                            account.setNameAccount(edtNameAccount.getText().toString());
                            account.seteMail(LoginActivity.currenEmailUser);
                            //data_Account.add(account);
                            data_account.child(String.valueOf(account.getIdAccount())).setValue(account);
                            finish();
                        }

                    }

                }
            });
        }
        btnSelectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCheckColor.setText("  ");
                // Chuyển đến hoạt động chọn màu (ColorActivity)
                Intent intent = new Intent(getApplicationContext(), ColorActivity.class);
                startActivity(intent);
            }
        });
        iconAdapter.setItemClickListener(new IconAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String iconName) {
                account.setIconAccount(iconName);
                Toast.makeText(Add_Update_AccountActivity.this, iconName, Toast.LENGTH_SHORT).show();
            }
        });

//        viewColor.setBackgroundColor(Color.parseColor(account.getColorAccount()));viewColor.setBackgroundColor(Color.parseColor(account.getColorAccount()));
    }

    private void updateAccount() {
        data_account.child(String.valueOf(edtIDAccount.getText())).child("moneyAccount").setValue(Integer.valueOf(edtMoney.getText().toString()));
        data_account.child(String.valueOf(edtIDAccount.getText())).child("nameAccount").setValue(edtNameAccount.getText().toString());
        data_account.child(String.valueOf(edtIDAccount.getText())).child("iconAccount").setValue(account.getIconAccount().toString());
        data_account.child(String.valueOf(edtIDAccount.getText())).child("colorAccount").setValue(account.getColorAccount().toString());
    }

    private void CreateAccount() {
        account = new Account(0,0,0,"123","null","null","test@gmail.com");
    }

    private boolean checkAccount() {
        for (Account a : data_Account) {
            if (a.getNameAccount().toString().equals(edtNameAccount.getText().toString())){
                edtNameAccount.setError("Tên tài khoản đã có!!!");
                return false;
            }
        }
        if (edtMoney.getText().toString().isEmpty()){
            edtMoney.setError("Chưa nhập số tiền");
            return false;
        }
        if (edtNameAccount.getText().toString().isEmpty()){
            edtNameAccount.setError("Chưa nhập tên");
            return false;
        }
        if (account.getIconAccount().equals("null")){
            tvCheckIcon.setText("Bạn phải chọn một biểu tượng!!!");
            return false;
        }
        if (account.getColorAccount().equals("null")){
            tvCheckColor.setText("Bạn chưa chọn màu!!!");
            return false;
        }
        return true;
    }

    private void khoiTaoIcon() {
        List<Integer> iconList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String iconName = "account_" + i;
            int iconResId = getResources().getIdentifier(iconName, "drawable", getPackageName());
            iconList.add(iconResId);
        }
        iconAdapter = new IconAdapter(iconList, this);
        revCategoryExpense.setLayoutManager(new GridLayoutManager(this, 4));
        revCategoryExpense.setAdapter(iconAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedColor = data.getStringExtra("selectedColor");
            if (selectedColor != null) {
                account.setColorAccount(selectedColor.toUpperCase());
                viewColor.setBackgroundColor(Color.parseColor(selectedColor.toUpperCase()));
            }
        }
    }

}