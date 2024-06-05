package com.example.projectver3.giaodich;

import static com.example.projectver3.HomeActivity.*;
import static com.example.projectver3.giaodich.ExpenseActivity.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.Add_Update_AccountActivity;
import com.example.projectver3.HomeActivity;
import com.example.projectver3.R;
import com.example.projectver3.adapter.DanhMucAdapter;
import com.example.projectver3.model.DanhMuc;
import com.example.projectver3.model.GiaoDich;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ExpenseFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        setControl(view);
        setEvent();
        return view;
    }


    private void setEvent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
            ngayGD = localDate.getDayOfMonth() + "/" + localDate.getMonth().getValue()+"/"+localDate.getYear();
        }
        tvNgayGD.setText("Hôm nay");

        //list_DanhMucChi.add(new DanhMuc("5", "Luong", "#FFEB3B", "img_2", "abc", false));
//        list_DanhMucChi.add(new DanhMuc("3", "Luong", "#FFEB3B", "img_2", "abc", false));
//        list_DanhMucChi.add(new DanhMuc("4", "Luong", "#FFEB3B", "img_2", "abc", false));
//        list_DanhMucChi.add(new DanhMuc("5", "Luong", "#FFEB3B", "img_2", "abc", false));



        //set up rcv
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 5);
        rcvDanhMuc.setLayoutManager(layoutManager);
        int spanCount = 5; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        rcvDanhMuc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


        adapterDanhMuc = new DanhMucGDAdapter(list_DanhMucChi, getContext(), false);
        rcvDanhMuc.setAdapter(adapterDanhMuc);

        //Chọn tài khoản
        tvTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFeedbackDialog(Gravity.CENTER, getContext(), tvTaiKhoan);
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
        Calendar calendar = Calendar.getInstance();
        tvNgayGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext()
                        , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        //tvNgayGD.setText(i2 + "/" + i1 + "/" + i);
                        tvNgayGD.setText(i2 + "/" + i1+"/"+i);
                        if (i2 >10){
                            ngayGD = "0"+String.valueOf(i2+"/"+i1+"/"+i);
                        }
                        ngayGD = String.valueOf(i2+"/"+i1+"/"+i);
                        // Sử dụng SimpleDateFormat để định dạng thời gian
//                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                        String formattedDate = sdf.format(calendar.getTime());
//                        tvNgayGD.setText(formattedDate);
//                        ngayGD = formattedDate;
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
                    giaoDich.setNgayGiaoDich(ngayGD);
                    giaoDich.setDanhMuc(danhMucselected);
                    giaoDich.setTaiKhoan(tvTaiKhoan.getText().toString());
                    //cap nhat so tien tai khoan
                    int iTienTru = Integer.parseInt(edtTien.getText().toString());
                    int iTienUpdate = iMoneyOld - iTienTru;
                    upDateMoneyInAccout(iID, iTienUpdate);
                    //Them giao dich moi
                    data_GD.child(giaoDich.getMaGiaoDich())
                            .setValue(giaoDich);
                    //chuyển màn hình khi thêm giao dịch thành công
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
    //end

    //ánh xạ
    private void setControl(View view) {
        edtTien = view.findViewById(R.id.edtTienChi);
        edtGhiChu = view.findViewById(R.id.edtGhiChuChi);
        tvTaiKhoan = view.findViewById(R.id.tvTaiKhoan);
        rcvDanhMuc = view.findViewById(R.id.rcvGDChi);
        tvNgayGD = view.findViewById(R.id.tvNgayGDChi);
        btnThem = view.findViewById(R.id.btnThemChiPhi);
        tvThongBao = view.findViewById(R.id.tvThongBao);
    }



}