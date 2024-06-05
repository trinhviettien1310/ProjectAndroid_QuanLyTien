package com.example.projectver3.giaodich;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.projectver3.R;
import com.example.projectver3.adapter.DialogAdapter;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.Account;
import com.example.projectver3.model.DanhMuc;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Button btnBack;

    //tài khoản
    public static ArrayList<Account> data_Account = new ArrayList<>();
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference data_taikhoan = database.getReference("Account");
    public static int iID = 0, iMoneyOld = 0;

    //Danh muc
    //public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference data_DanhMuc = database.getReference("DanhMuc");
    public static ArrayList<DanhMuc> list_DanhMucChi = new ArrayList<>();
    public static ArrayList<DanhMuc> list_DanhMucThu = new ArrayList<>();
    public static DanhMucGDAdapter adapterDanhMuc;

    //Giao dich
    //public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference data_GD = database.getReference("GiaoDich");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        setControl();
        setEvent();
    }


    private void setEvent() {
        ExpenseActivity.docDLDMGD(LoginActivity.currenEmailUser);

        //khoiTao();
        ViewPageGDAdapter viewPageAdapter = new ViewPageGDAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        data_taikhoan.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                docDLTK();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                docDLTK();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                docDLTK();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data_DanhMuc.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                docDLDMGD(LoginActivity.currenEmailUser);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                docDLDMGD(LoginActivity.currenEmailUser);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                docDLDMGD(LoginActivity.currenEmailUser);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setControl() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pagerDM);
        btnBack = findViewById(R.id.btnBackAddTransaction);
    }


    //Đọc danh sách tài khoản
    public static void docDLTK() {
        data_taikhoan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data_Account.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    Account account = item.getValue(Account.class);
                    if (account.geteMail().equals(LoginActivity.currenEmailUser)){
                        data_Account.add(account);
                        Log.d("taikhoan",data_Account.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    //Bat Dau doc dl Danh Muc
    public static void docDLDMGD(String email) {
        data_DanhMuc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_DanhMucChi.clear();
                list_DanhMucThu.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    DanhMuc danhMuc = item.getValue(DanhMuc.class);
                    if (danhMuc.getMail().equals(email)){
                        if (!danhMuc.isLoai()) {
                            list_DanhMucChi.add(danhMuc);
                            Log.d("danhmucchi", list_DanhMucChi.toString());
                        }else {
                            list_DanhMucThu.add(danhMuc);
                            Log.d("danhmucthu", list_DanhMucThu.toString());
                        }
                    }

                }
//                adapterDanhMuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //Kiem tra chon tai khoan
    public static boolean checkAcountSelected(TextView textView){
        if (textView.getText().toString().equals("Chưa chọn")){
            textView.setError("Bạn phải chọn 1 tài khoản!!!");
            return false;
        }
        return true;
    }

    //Cap nhat so tien tai khoan
    public static void upDateMoneyInAccout(int ID, int soTien ){
        data_taikhoan.child(String.valueOf(ID)).child("moneyAccount").setValue(Integer.valueOf(soTien));
    }
    //kiem tra du lieu nhap
    public static boolean checkDataInput(EditText editText) {
        if (editText.getText().length() < 1) {
            Log.d("erro", "erro");
            editText.setError("Không được để trống!!!");
            return false;
        }
        return true;
    }

    //Kiem tra đã chọn danh muc hay chua
    public static boolean checkDanhMuc(DanhMuc danhMuc, TextView textView){
        if (danhMuc.getTenDanhMuc().equals("null")){
            textView.setText("Bạn phải chọn một danh mục !!!");
            return false;
        }
        return true;
    }

    //dialog hiển thị danh sách tài khoản
    public static void openFeedbackDialog(int gravity, Context context, TextView textView) {
        final Dialog dialog = new Dialog(context);
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

        DialogAdapter dialogAdapter;
        RecyclerView revDanhSachDiaLog = dialog.findViewById(R.id.revDanhSachDiaLog);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDialog);

        revDanhSachDiaLog.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        dialogAdapter = new DialogAdapter(context, data_Account, new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                Log.d("account", String.valueOf(account.getMoneyAccount()));
                iID = account.getIdAccount();
                iMoneyOld = account.getMoneyAccount();
                Log.d("id+tien", iID+"+"+iMoneyOld);

                textView.setText(account.getNameAccount().toString());
                Log.d("tk", account.getNameAccount().toString());
                dialog.dismiss();
            }
        });

        revDanhSachDiaLog.setAdapter(dialogAdapter);
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
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
    //kết thúc dialog

//    private void khoiTao() {
//        data_Account.add(new Account("1", 3000, 2000, "Huy", "img_3", "#5B9371"));
//    }
}