package com.example.projectver3.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Account;
import com.google.android.material.imageview.ShapeableImageView;

public class DialogHolder extends RecyclerView.ViewHolder {
    RadioButton rdoDiaLog;
    ShapeableImageView sivCellDiaLog;
    TextView tvNameAccountDiaLog,tvMoneyAccountDiaLog;

    public DialogHolder(@NonNull View itemView) {
        super(itemView);
        rdoDiaLog = itemView.findViewById(R.id.rdoDiaLog);
        sivCellDiaLog = itemView.findViewById(R.id.sivCellDiaLog);
        tvNameAccountDiaLog = itemView.findViewById(R.id.tvNameAccountDiaLog);
        tvMoneyAccountDiaLog = itemView.findViewById(R.id.tvMoneyAccountDiaLog);
    }

    public void bindData(final Account account) {
        tvNameAccountDiaLog.setText(account.getNameAccount());
        tvMoneyAccountDiaLog.setText(String.valueOf(account.getMoneyAccount()));

        // Đặt hình ảnh cho ShapeableImageView
        if (account.getIconAccount() != null) {
            int resId = itemView.getContext().getResources().getIdentifier(account.getIconAccount(), "drawable", itemView.getContext().getPackageName());
            sivCellDiaLog.setImageResource(resId);
        }
        sivCellDiaLog.setBackgroundColor(Color.parseColor(account.getColorAccount().toString()));

    }
}
