package com.example.projectver3.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Account;
import com.google.android.material.imageview.ShapeableImageView;

public class AccountHolder extends RecyclerView.ViewHolder {
    public LinearLayout layoutForeGroundAccount;
    private ShapeableImageView sivCellAccount;
    private TextView tvNameAccount,tvMoneyAccount;
    public AccountHolder(@NonNull View itemView) {
        super(itemView);
        sivCellAccount = itemView.findViewById(R.id.sivCellAccount);
        tvNameAccount = itemView.findViewById(R.id.tvNameAccount);
        tvMoneyAccount = itemView.findViewById(R.id.tvMoneyAccount);
        layoutForeGroundAccount = itemView.findViewById(R.id.layout_foregroundAccount);
    }
    public void bindData(final Account account) {
        tvNameAccount.setText(account.getNameAccount());
        tvMoneyAccount.setText(String.valueOf(account.getMoneyAccount()) + " đ");

        // Đặt hình ảnh cho ShapeableImageView
        if (account.getIconAccount() != null) {
            int resId = itemView.getContext().getResources().getIdentifier(account.getIconAccount(), "drawable", itemView.getContext().getPackageName());
            sivCellAccount.setImageResource(resId);
        }
        sivCellAccount.setBackgroundColor(Color.parseColor(account.getColorAccount().toString()));

    }
}
