package com.example.projectver3.giaodich;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.DanhMuc;
import com.google.android.material.imageview.ShapeableImageView;

public class DanhMucViewHolder extends RecyclerView.ViewHolder {
    TextView tvCellName;
    ShapeableImageView sivCellDanhMuc;

    public DanhMucViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCellName = itemView.findViewById(R.id.tvCellName);
        sivCellDanhMuc = itemView.findViewById(R.id.sivCellDanhMuc);
    }

    public void bindData(DanhMuc danhMuc) {
        tvCellName.setText(danhMuc.getTenDanhMuc());

        // Đặt hình ảnh cho ShapeableImageView
        if (danhMuc.getHinh() != null) {
            int resId = itemView.getContext().getResources().getIdentifier(danhMuc.getHinh(), "drawable", itemView.getContext().getPackageName());
            sivCellDanhMuc.setImageResource(resId);
        }
        sivCellDanhMuc.setBackgroundColor(Color.parseColor(danhMuc.getMau().toString()));
    }


}