package com.example.projectver3.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

public class IconHolder extends RecyclerView.ViewHolder {
    ImageView ivIconAccount;
    public IconHolder(@NonNull View itemView) {
        super(itemView);
        ivIconAccount = itemView.findViewById(R.id.sivIconAccount);
    }
    public void bindData(int iconResId) {
        ivIconAccount.setImageResource(iconResId);
    }
}
