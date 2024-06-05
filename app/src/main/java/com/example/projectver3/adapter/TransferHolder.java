package com.example.projectver3.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

public class TransferHolder extends RecyclerView.ViewHolder {
    public LinearLayout layout_foregroundTransfer;
    TextView tvFromAccount,tvToAccount,tvMoneyTransfer;
    public TransferHolder(@NonNull View itemView) {
        super(itemView);
        tvFromAccount = itemView.findViewById(R.id.tvFromAccount);
        tvToAccount = itemView.findViewById(R.id.tvToAccount);
        tvMoneyTransfer = itemView.findViewById(R.id.tvMoneyTransfer);
        layout_foregroundTransfer = itemView.findViewById(R.id.layout_foregroundTransfer);
    }
}
