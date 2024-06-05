package com.example.projectver3.giaodich;

import static android.content.Context.MODE_PRIVATE;

import static com.example.projectver3.HomeActivity.data_giaoDich;
import static com.example.projectver3.giaodich.ExpenseActivity.*;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectver3.HomeActivity;
import com.example.projectver3.R;
import com.example.projectver3.adapter.DanhMucAdapter;
import com.example.projectver3.adapter.DialogAdapter;
import com.example.projectver3.model.Account;
import com.example.projectver3.model.DanhMuc;
import com.example.projectver3.model.GiaoDich;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class IncomeFragment extends Fragment {
    EditText edtTien, edtGhiChu;
    TextView tvNgayGD;
    TextView tvTaiKhoan, tvThongBao;
    Button btnThem;
    RecyclerView rcvDanhMuc;


    //Danh muc được chọn
    private DanhMuc danhMucselected = new DanhMuc("temp",0, "null", "null", "null", "null", true);
    String ngayGD;
    LocalDate localDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        setControl(view);
        setEvent();
        return view;
    }

    private void setEvent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
            ngayGD = localDate.getDayOfMonth() + "/" + localDate.getMonth().getValue()+"/"+localDate.getYear();
        }
        tvNgayGD.setText("Hôm nay\n"+ngayGD);

        //set up rcv
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        rcvDanhMuc.setLayoutManager(layoutManager);
        int spanCount = 5; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        rcvDanhMuc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


        adapterDanhMuc = new DanhMucGDAdapter(list_DanhMucThu, getContext(), true);
        rcvDanhMuc.setAdapter(adapterDanhMuc);

        //Chọn tài khoản
        tvTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseActivity.openFeedbackDialog(Gravity.CENTER, getContext(), tvTaiKhoan);
            }
        });

        //chon danh muc
        adapterDanhMuc.setOnItemClickListener(new DanhMucGDAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DanhMuc danhMuc) {
                danhMucselected = danhMuc;
                tvThongBao.setText("     ");
                Toast.makeText(getContext(),
                        danhMuc.getTenDanhMuc().toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("danhmuc selected", danhMucselected.toString());
            }
        });

        tvNgayGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext()
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        //tvNgayGD.setText(i2 + "/" + i1 + "/" + i);
                        tvNgayGD.setText(i2 + "/" + i1);
                        ngayGD = i2 + "/" + i1 + "/" + i;
                    }
                }, 2023, 10, 27);
                datePicker.show();
            }
        });

        //start btnThem
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkDataInput(edtTien) && checkDataInput(edtGhiChu) && checkDanhMuc(danhMucselected, tvThongBao) && checkAcountSelected(tvTaiKhoan)) {
                    //tạo giao dịch
                    GiaoDich giaoDich = new GiaoDich();
                    giaoDich.setMaGiaoDich(data_giaoDich.push().getKey());
                    giaoDich.setSoTien(edtTien.getText().toString());
                    giaoDich.setTaiKhoan(tvTaiKhoan.getText().toString());
                    giaoDich.setNgayGiaoDich(ngayGD);
                    giaoDich.setDanhMuc(danhMucselected);
                    //cap nhat so tien tai khoan
                    int iTienCong = Integer.parseInt(edtTien.getText().toString());
                    int iTienUpdate = iMoneyOld + iTienCong;
                    upDateMoneyInAccout(iID, iTienUpdate);
                    //Them giao dich moi
                    data_GD.child(giaoDich.getMaGiaoDich())
                            .setValue(giaoDich);
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setControl(View view) {

        edtTien = view.findViewById(R.id.edtTienThu);
        edtGhiChu = view.findViewById(R.id.edtGhiChuThu);
        tvTaiKhoan = view.findViewById(R.id.tvTaiKhoan);
        rcvDanhMuc = view.findViewById(R.id.rcvGDThu);
        tvNgayGD = view.findViewById(R.id.tvNgayGDThu);
        btnThem = view.findViewById(R.id.btnThemThuNhap);
        tvThongBao = view.findViewById(R.id.tvThongBao);
    }


    //dialog hiển thị danh sách tài khoản
    private void openFeedbackDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
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
        revDanhSachDiaLog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dialogAdapter = new DialogAdapter(getContext(), data_Account, new DialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                tvTaiKhoan.setText(account.getNameAccount().toString());
                Log.d("tk", account.getNameAccount().toString());
                dialog.dismiss();
            }
        });

        revDanhSachDiaLog.setAdapter(dialogAdapter);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
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