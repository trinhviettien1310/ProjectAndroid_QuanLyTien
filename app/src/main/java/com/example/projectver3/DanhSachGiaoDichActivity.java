package com.example.projectver3;

import static com.example.projectver3.HomeActivity.listGD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectver3.adapter.DanhSachAdapter;
import com.example.projectver3.model.GiaoDich;
import com.example.projectver3.model.ThongKe;

import java.util.ArrayList;


public class DanhSachGiaoDichActivity extends AppCompatActivity {
    private static int MY_REQUEST_CODE = 10;
    public ArrayList<GiaoDich> list_gd;

    public static String tenTK;
    TextView tvTenDM, tvTongTien;

    Button btnBack;

    RecyclerView rcvDS;

    DanhSachAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_giao_dich);
        setControll();
        setEvent();
    }

    private void setEvent() {
        ThongKe thongKe = (ThongKe) getIntent().getExtras().get("list_gd");
        tvTenDM.setText(thongKe.getTenGD());
        tvTongTien.setText(thongKe.getTongTien().toString());
        //Log.d("chuyen man hinh", thongKe.toString());
        //lay ds giao dich
        list_gd = new ArrayList<>();
        for (GiaoDich giaoDich:listGD
             ) {
            if (thongKe.getTenGD().equals(giaoDich.getDanhMuc().getTenDanhMuc())){
                list_gd.add(giaoDich);

            }
        }
        //Log.d("show list", list_gd.toString());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //rcv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getLayoutInflater().getContext());
        rcvDS.setLayoutManager(linearLayoutManager);
        adapter = new DanhSachAdapter(list_gd, DanhSachGiaoDichActivity.this, new DanhSachAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GiaoDich giaoDich) {
                Intent intent = new Intent(DanhSachGiaoDichActivity.this, EditGiaoDichActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("chi_tiet", giaoDich);
                tenTK = giaoDich.getTaiKhoan();
                Log.d("test list", tenTK);
                intent.putExtras(bundle);
                startActivityForResult(intent, DanhSachGiaoDichActivity.MY_REQUEST_CODE);
            }
        });
        rcvDS.setAdapter(adapter);

    }

    private void setControll() {
        tvTenDM = findViewById(R.id.tvTenDanhMuc);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnBack = findViewById(R.id.btnBackHome);
        rcvDS = findViewById(R.id.rcvDS);
    }

}