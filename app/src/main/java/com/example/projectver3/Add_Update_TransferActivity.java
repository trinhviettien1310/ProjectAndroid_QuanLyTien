package com.example.projectver3;


import static com.example.projectver3.TransferHistoryActivity.data_transfer;
import static com.example.projectver3.fragment.AcountFragment.data_Account;
import static com.example.projectver3.fragment.AcountFragment.data_account;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.adapter.DialogAdapter;
import com.example.projectver3.model.Account;
import com.example.projectver3.model.Transfer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add_Update_TransferActivity extends AppCompatActivity {
    Button btnBackTransfer;
    TextView tvFromAccount, tvToAccount,tvDateAccount, tvToolTransfer;
    EditText edtNoteAccount, edtMoneyAccount, edtIDTransfer;
    Button btnAddTransfer;
    Boolean sflag;
    public static FirebaseDatabase databaseTransfer;
    public static DatabaseReference data_userTransfer;
    public static Transfer transfer = new Transfer();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_transfer);
        //setup firebase
        databaseTransfer = FirebaseDatabase.getInstance();
        data_userTransfer = databaseTransfer.getReference("Transfer");
        setControl();
        setEvent();
    }

    private void setControl() {
        btnBackTransfer = findViewById(R.id.btnBackTransfer);
        tvFromAccount = findViewById(R.id.tvFromAccount);
        tvToAccount = findViewById(R.id.tvToAccount);
        tvDateAccount = findViewById(R.id.tvDateAccount);
        edtNoteAccount = findViewById(R.id.edtNoteAccount);
        edtMoneyAccount = findViewById(R.id.edtMoneyAccount);
        btnAddTransfer = findViewById(R.id.btnAddTransfer);
        edtIDTransfer = findViewById(R.id.edtIDTransfer);
        tvToolTransfer = findViewById(R.id.tvToolTransfer);
    }

    private void setEvent() {
        //Boolean flag = getIntent().getBooleanExtra("isEditTransfer", false);
        btnBackTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //add
//        String formatdate = "E, d-M-yyyy k:m:sa";
//        tvDateAccount.setFormat12Hour(formatdate);
        tvToolTransfer.setText(R.string.createTransfer);
        btnAddTransfer.setText(R.string.add);
        tvFromAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sflag = true;
                openFeedbackDialog(Gravity.CENTER);
            }
        });
        tvToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sflag = false;
                openFeedbackDialog(Gravity.CENTER);
            }
        });
        //date
        updateCurrentDate();
        btnAddTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    String fromAccountName = tvFromAccount.getText().toString();
                    String toAccountName = tvToAccount.getText().toString();
                    int moneyTransfer = Integer.parseInt(edtMoneyAccount.getText().toString());

                    // Tìm tài khoản gốc và đích trong danh sách tài khoản
                    Account fromAccount = findAccountByName(fromAccountName);
                    Account toAccount = findAccountByName(toAccountName);
                    if (fromAccount != null && toAccount != null) {
                        //kiểm tra tài khoản giống nhau không
                        if (tvFromAccount.getText().toString() != tvToAccount.getText().toString()) {

                            // Kiểm tra xem tài khoản gốc có đủ tiền để chuyển không
                            if (fromAccount.getMoneyAccount() >= moneyTransfer) {
                                // Cập nhật số tiền trong tài khoản gốc và đích
                                fromAccount.setMoneyAccount(fromAccount.getMoneyAccount() - moneyTransfer);
                                toAccount.setMoneyAccount(toAccount.getMoneyAccount() + moneyTransfer);
                                // Cập nhật số tiền trong tài khoản gốc và đích trên Firebase
                                data_account.child(String.valueOf(fromAccount.getIdAccount())).child("moneyAccount").setValue(fromAccount.getMoneyAccount());
                                data_account.child(String.valueOf(toAccount.getIdAccount())).child("moneyAccount").setValue(toAccount.getMoneyAccount());
                                //add
                                if (!data_transfer.isEmpty()) {
                                    Transfer t1 = data_transfer.get(data_transfer.size() - 1);
                                    int lastIndexList = t1.getIdTransfer() + 1;
                                    transfer.setIdTransfer(lastIndexList);
                                    transfer.setNameFromAccount(fromAccountName);
                                    transfer.setNameToAccount(toAccountName);
                                    transfer.setMoneyTransfer(moneyTransfer);
                                    transfer.setDateTransfer(tvDateAccount.getText().toString());
                                    transfer.setNoteTransfer(edtNoteAccount.getText().toString());
                                    //data_transfer.add(transfer);
                                    data_userTransfer.child(String.valueOf(transfer.getIdTransfer())).setValue(transfer);
                                    finish();
                                } else {
                                    transfer.setIdTransfer(0);
                                    transfer.setNameFromAccount(fromAccountName);
                                    transfer.setNameToAccount(toAccountName);
                                    transfer.setMoneyTransfer(moneyTransfer);
                                    transfer.setDateTransfer(tvDateAccount.getText().toString());
                                    transfer.setNoteTransfer(edtNoteAccount.getText().toString());
                                    //data_transfer.add(transfer);
                                    data_userTransfer.child(String.valueOf(transfer.getIdTransfer())).setValue(transfer);
                                    finish();
                                }

                            } else {
                                Toast.makeText(Add_Update_TransferActivity.this, "Số tiền không đủ trong tài khoản gốc", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Add_Update_TransferActivity.this, "Tài khoản giống nhau vui lòng chọn lại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Add_Update_TransferActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private Account findAccountByName(String name) {
        for (Account account : data_Account) {
            if (account.getNameAccount().equals(name)) {
                return account;
            }
        }
        return null; // Trả về null nếu không tìm thấy tài khoản
    }

    //Hiển thị ngày tháng năm hiện tại
    private void updateCurrentDate() {
        // Lấy ngày tháng năm hiện tại
        Date currentDate = new Date();

        // Định dạng ngày tháng năm theo định dạng mong muốn
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(currentDate);

        // Hiển thị ngày tháng năm lên TextView
        tvDateAccount.setText(formattedDate);
    }

    private boolean check() {
        if (edtMoneyAccount.getText().toString().isEmpty()) {
            edtMoneyAccount.setError("Chưa nhập tiền");
            return false;
        }
        return true;
    }

    //dialog
    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_dialog_feedback);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        //setControl();
        DialogAdapter dialogAdapter;
        RecyclerView revDanhSachDiaLog = dialog.findViewById(R.id.revDanhSachDiaLog);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);
        //setEvent();
        revDanhSachDiaLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dialogAdapter = new DialogAdapter(this, data_Account, new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                if (sflag == true) {
                    tvFromAccount.setText(account.getNameAccount().toString());
                    dialog.dismiss();
                } else {
                    tvToAccount.setText(account.getNameAccount().toString());
                    dialog.dismiss();
                }
            }
        });

        revDanhSachDiaLog.setAdapter(dialogAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int selectedPosition = sharedPreferences.getInt("selectedPosition", -1); // -1 là giá trị mặc định nếu không có giá trị trong SharedPreferences

        if (selectedPosition != -1) {
            // Cập nhật trạng thái của RadioButton dựa trên selectedPosition
            dialogAdapter.setSelectedPosition(selectedPosition);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}