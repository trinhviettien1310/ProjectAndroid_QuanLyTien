package com.example.projectver3.Lich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.R;
import com.example.projectver3.model.GiaoDich;

import java.util.ArrayList;

public class MainChiTietLich extends AppCompatActivity {

   Button btnBack;


    ListView lvDanhSach;
    ArrayList<GiaoDich> data = new ArrayList<>();
    CustomAdapterCT adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_lich);
        setControl();
        setEvent();
    }


    private void setEvent() {
        KhoiTao();

        adapter = new CustomAdapterCT(this, R.layout.layout_item_lich, data);
        lvDanhSach.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }



    void KhoiTao() {
        // Trong Activity muốn nhận dữ liệu:
        Intent intent = getIntent();
        data = intent.getParcelableArrayListExtra("data"); // Lấy dữ liệu từ Intent
    }



    private void setControl() {
        btnBack = findViewById(R.id.btnBack);

        lvDanhSach = findViewById(R.id.lvDanhSach);

    }
}