package com.example.projectver3;



import static com.example.projectver3.fragment.CateFragment.DocDLDM;
import static com.example.projectver3.fragment.CateFragment.danhMucList;
import static com.example.projectver3.fragment.CateFragment.data_DM;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.adapter.IconDanhMucAdapter;
import com.example.projectver3.giaodich.GridSpacingItemDecoration;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class AddUpdateDanhMucActivity extends AppCompatActivity {

    private static final int YOUR_REQUEST_CODE = 1;
    Switch swLoai;
    EditText edtGhichu, edtTenDanhMuc;
    TextView tvLoaiDanhMuc, tvTitleDanhmuc;
    RecyclerView revDanhmucIcons;
    Button btnChonMau, btnBack, btnLuuDanhmuc, btnXoaDanhmuc;
    public static ImageView imgColor;
    IconDanhMucAdapter iconAdapter;

    public static DanhMuc temp;
    public static DanhMuc danhMucToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_danhmuc);
        setControl();
        setEvent();

        boolean isEditMode = getIntent().getBooleanExtra("isEditMode", false);
        if (isEditMode) {
            temp = danhMucToEdit;
            Log.d("tempAA", temp.toString());
            edtTenDanhMuc.setText(temp.getTenDanhMuc());
            edtGhichu.setText(temp.getGhiChu());
            swLoai.setChecked(temp.isLoai());
            tvTitleDanhmuc.setText("Sửa danh mục");
            imgColor.setBackgroundColor(Color.parseColor(temp.getMau()));
        }
    }

    private void setEvent() {
        khoiTaoIcon();

        boolean isEditMode = getIntent().getBooleanExtra("isEditMode", false);

        if (isEditMode) {
            int position = getIntent().getIntExtra("position", -1);
            if (position >= 0) {
                //Toast.makeText(AddUpdateDanhMucActivity.this, "a" + position, Toast.LENGTH_SHORT).show();
                Toast.makeText(AddUpdateDanhMucActivity.this, danhMucToEdit.getMaDanhMuc()+ " - " + danhMucToEdit.getTenDanhMuc(), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(AddUpdateDanhMucActivity.this, "Không tìm thấy danh mục để cập nhật", Toast.LENGTH_SHORT).show();
            }

//            btnLuuDanhmuc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (kiemTraTen()) {
//                        temp.setLoai(swLoai.isChecked());
//                        temp.setTenDanhMuc(edtTenDanhMuc.getText().toString());
//                        danhMucList.set(danhMucList.indexOf(danhMucToEdit), temp);

//                        Intent intent = new Intent(getApplicationContext(), MainDanhMucActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            });
            btnLuuDanhmuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (kiemTraTen()) {
                        temp.setLoai(swLoai.isChecked());
                        temp.setTenDanhMuc(edtTenDanhMuc.getText().toString());
                        temp.setGhiChu(edtGhichu.getText().toString());
//                        danhMucList.set(danhMucList.indexOf(danhMucToEdit), temp);

//                        data_user.child(temp.maDanhMuc).child("maDanhMuc").setValue(temp.maDanhMuc);
                        data_DM.child(String.valueOf(temp.getMaDanhMuc())).child("tenDanhMuc").setValue(temp.getTenDanhMuc());
                        data_DM.child(String.valueOf(temp.getMaDanhMuc())).child("hinh").setValue(temp.getHinh());
                        data_DM.child(String.valueOf(temp.getMaDanhMuc())).child("mau").setValue(temp.getMau());
                        data_DM.child(String.valueOf(temp.getMaDanhMuc())).child("loai").setValue(temp.isLoai());
                        data_DM.child(String.valueOf(temp.getMaDanhMuc())).child("ghiChu").setValue(temp.getGhiChu());

//                        Intent intent = new Intent(getApplicationContext(), CateFragment.class);
//                        startActivity(intent);
                        Toast.makeText(AddUpdateDanhMucActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        DocDLDM();

//                        onBackPressed();
                        finish();
                    }
                }
            });

            btnXoaDanhmuc.setEnabled(true);
//            btnXoaDanhmuc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    danhMucList.remove(danhMucList.indexOf(danhMucToEdit));
//
//                    Intent intent = new Intent(getApplicationContext(), MainDanhMucActivity.class);
//                    startActivity(intent);
//                }
//            });
            btnXoaDanhmuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data_DM.child(String.valueOf(temp.getMaDanhMuc())).removeValue();
                    danhMucList.remove(danhMucList.indexOf(danhMucToEdit));
//                    Intent intent = new Intent(getApplicationContext(), CateFragment.class);
//                    startActivity(intent);
                    Toast.makeText(AddUpdateDanhMucActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    onBackPressed();
                    DocDLDM();
                    finish();
                    //XoaDanhMucAPI(temp.getMaDanhMuc());
                }
            });
        } else {
            // Đây là trường hợp thêm mới danh mục
            // Giao diện sẽ ở chế độ thêm mới
            createNewDanhMuc();
//            btnLuuDanhmuc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (kiemTraTen()) {
//                        temp.setLoai(swLoai.isChecked());
//                        temp.setTenDanhMuc(edtTenDanhMuc.getText().toString());
//                        //temp.setTienDanhMuc(Double.parseDouble(edtTienDanhMuc.getText().toString()));
//                        danhMucList.add(temp);
//                        Intent resultIntent = new Intent();
//                        Intent intent = new Intent(getApplicationContext(), MainDanhMucActivity.class);
//                        startActivity(intent);
//                    }
//                }
//            });
            btnLuuDanhmuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (kiemTraTen()) {

                        if (!danhMucList.isEmpty()) {
                            DanhMuc addDM = danhMucList.get(danhMucList.size() - 1);
                            int lastIndexList = addDM.getMaDanhMuc() + 1;
                            Log.d("lastIndexList", String.valueOf(lastIndexList));
                            temp.setMail(LoginActivity.currenEmailUser);
                            temp.setMaDanhMuc(lastIndexList);
                            temp.setLoai(swLoai.isChecked());
                            temp.setTenDanhMuc(edtTenDanhMuc.getText().toString());
                            temp.setGhiChu(edtGhichu.getText().toString());
                            danhMucList.add(temp);

                            data_DM.child(String.valueOf(temp.getMaDanhMuc())).setValue(temp);
//                            finish();
//                        Intent intent = new Intent(getApplicationContext(), CateFragment.class);
//                        startActivity(intent);
//                            onBackPressed();
                            finish();
                        } else {
                            temp.setMaDanhMuc(0);
                            temp.setLoai(swLoai.isChecked());
                            temp.setTenDanhMuc(edtTenDanhMuc.getText().toString());
                            temp.setGhiChu(edtGhichu.getText().toString());
                            temp.setMail(LoginActivity.currenEmailUser);
                            danhMucList.add(temp);

                            data_DM.child(String.valueOf(temp.getMaDanhMuc())).setValue(temp);
//                        Intent intent = new Intent(getApplicationContext(), CateFragment.class);
//                        startActivity(intent);
//                            finish();
//                            onBackPressed();
                            finish();
                        }
                        Toast.makeText(AddUpdateDanhMucActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        DocDLDM();
                    }
                }
            });
        }


//        imgColor.setBackgroundColor(Color.parseColor(temp.getMau()));

        swLoai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    temp.setLoai(true);
                    tvLoaiDanhMuc.setText("Thu nhập");
                } else {
                    temp.setLoai(false);
                    tvLoaiDanhMuc.setText("Chi phí");
                }
            }
        });

        iconAdapter.setItemClickListener(new IconDanhMucAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String iconName) {
                temp.setHinh(iconName);
                Toast.makeText(AddUpdateDanhMucActivity.this, iconName, Toast.LENGTH_SHORT).show();
            }
        });


        btnChonMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến hoạt động chọn màu (ColorActivity)
                Intent intent = new Intent(getApplicationContext(), ColorDMActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void createNewDanhMuc() {
        temp = new DanhMuc("abc@gmail.com",1, "Luong", "#FFEB3B", "img_2", "vuong", true);
    }

    private boolean kiemTraTen() {
        if (edtTenDanhMuc.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void khoiTaoIcon() {
        List<Integer> iconList = new ArrayList<>();
        for (int i = 1; i <= 63; i++) {
            String iconName = "img_" + i;
            int iconResId = getResources().getIdentifier(iconName, "drawable", getPackageName());
            iconList.add(iconResId);
        }
        iconAdapter = new IconDanhMucAdapter(iconList, this);
        revDanhmucIcons.setLayoutManager(new GridLayoutManager(this, 5));
        revDanhmucIcons.setAdapter(iconAdapter);
        int spanCount = 5; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        revDanhmucIcons.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == YOUR_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedColor = data.getStringExtra("selectedColor");
            if (selectedColor != null) {
                temp.setMau(selectedColor.toUpperCase());
                imgColor.setBackgroundColor(Color.parseColor(selectedColor.toUpperCase()));
            }
        }
    }

    private void setControl() {
        swLoai = findViewById(R.id.swLoai);
        edtGhichu = findViewById(R.id.edtGhichu);
        edtTenDanhMuc = findViewById(R.id.edtTenDanhMuc);
        revDanhmucIcons = findViewById(R.id.revDanhmucIcons);
        btnChonMau = findViewById(R.id.btnChonMau);
        btnBack = findViewById(R.id.btnBack);
        imgColor = findViewById(R.id.imgColor);
        btnLuuDanhmuc = findViewById(R.id.btnLuuDanhmuc);
        btnXoaDanhmuc = findViewById(R.id.btnXoaDanhmuc);
        tvLoaiDanhMuc = findViewById(R.id.tvLoaiDanhMuc);
        tvTitleDanhmuc = findViewById(R.id.tvTitleDanhmuc);
    }
}
