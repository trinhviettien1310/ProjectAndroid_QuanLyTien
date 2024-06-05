package com.example.projectver3.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class IconDanhMucHolder extends RecyclerView.ViewHolder {
    CircleImageView ivCellDanhMuc;

    public IconDanhMucHolder(@NonNull View itemView) {
        super(itemView);
        ivCellDanhMuc = itemView.findViewById(R.id.sivCellDanhMuc);
    }

    public void bindData(int iconResId) {

        ivCellDanhMuc.setImageResource(iconResId);
        ivCellDanhMuc.setColorFilter(Color.parseColor("#96A1A0"), PorterDuff.Mode.DST_ATOP);
    }

    public interface ItemClickListener {
        void onItemClick(String iconName);
    }

}