package com.example.projectver3;




import static com.example.projectver3.HomeActivity.data_giaoDich;
import static com.example.projectver3.fragment.AcountFragment.data_account;
import static com.example.projectver3.giaodich.ExpenseActivity.data_Account;
import static com.example.projectver3.giaodich.ExpenseActivity.iID;
import static com.example.projectver3.giaodich.ExpenseActivity.upDateMoneyInAccout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectver3.model.Account;
import com.example.projectver3.model.GiaoDich;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditGiaoDichActivity extends AppCompatActivity {

    Button btnEdit, btnDel, btnBack;

    TextView tvDanhMuc, tvNgay, tvTenTK;

    EditText edtSoTien, edtGhiChu;
    CircleImageView civDanhMuc;
    private int idTK, soTienTK, soTienCuoi;

    private int tienSua, soDu, edtEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_giao_dich);
        setControl();
        setEvent();
    }

    private void setEvent() {
        GiaoDich giaoDich = (GiaoDich) getIntent().getExtras().get("chi_tiet");
        edtSoTien.setText(giaoDich.getSoTien());
        int tienHT = Integer.parseInt(giaoDich.getSoTien());
        edtSoTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    tienSua = Integer.parseInt(edtSoTien.getText().toString());
                    soDu = tienHT - tienSua;
                }
            }
        });
        tvDanhMuc.setText(giaoDich.getDanhMuc().getTenDanhMuc());
        tvNgay.setText(giaoDich.getNgayGiaoDich());
        edtGhiChu.setText(giaoDich.getDanhMuc().getGhiChu());
        tvTenTK.setText(DanhSachGiaoDichActivity.tenTK);
        giaoDich.setTaiKhoan(DanhSachGiaoDichActivity.tenTK);

        for (Account account : data_Account) {
            if (account.getNameAccount().equals(DanhSachGiaoDichActivity.tenTK)){
                idTK = account.getIdAccount();
                soTienTK = account.getMoneyAccount();

            }
        }
        Log.d("IDTK S", idTK + "+" + soTienTK);
        // Đặt hình ảnh cho ShapeableImageView
        if (giaoDich.getDanhMuc().getHinh() != null) {
            int resId = this.getResources().getIdentifier(giaoDich.getDanhMuc().getHinh(), "drawable", this.getPackageName());
            civDanhMuc.setImageResource(resId);
        }
        civDanhMuc.setCircleBackgroundColor(Color.parseColor(giaoDich.getDanhMuc().getMau().toString()));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (!giaoDich.getDanhMuc().isLoai()){
                    soTienCuoi = soTienTK + soDu;
                }
                else {
                    soTienCuoi = soTienTK - soDu;
                }
                Log.d("test update",  "+" + soTienCuoi);
                giaoDich.setSoTien(edtSoTien.getText().toString());
                giaoDich.getDanhMuc().setGhiChu(edtGhiChu.getText().toString());
                giaoDich.setTaiKhoan(DanhSachGiaoDichActivity.tenTK);
                data_giaoDich.child(String.valueOf(giaoDich.getMaGiaoDich())).setValue(giaoDich);
                upDateMoneyInAccout(idTK, soTienCuoi);
                Intent intent = new Intent(EditGiaoDichActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        //xóa
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                data_giaoDich.child(giaoDich.getMaGiaoDich()).removeValue();
                if (!giaoDich.getDanhMuc().isLoai()){
                    soTienCuoi = soTienTK + tienHT;
                } else {
                    soTienCuoi = soTienTK - tienHT;
                }
                upDateMoneyInAccout(idTK, soTienCuoi);
                HomeActivity.DocDL();
                Intent intent = new Intent(EditGiaoDichActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        edtSoTien = findViewById(R.id.edtSoTien);
        tvDanhMuc = findViewById(R.id.tvTenDanhMuc);
        tvNgay = findViewById(R.id.tvNgayGD);
        edtGhiChu = findViewById(R.id.edtNote);
        btnDel = findViewById(R.id.btnDelGD);
        btnEdit = findViewById(R.id.btnEditGD);
        btnBack = findViewById(R.id.btnBackList);
        civDanhMuc = findViewById(R.id.civChiTiet);
        tvTenTK = findViewById(R.id.tvTenTK);
    }

}