package com.example.projectver3.adapter;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

public class RemindHolder extends RecyclerView.ViewHolder {
    TextView tvTenLoiNhac;
    Switch swOnOff;
    public RemindHolder(@NonNull View itemView) {
        super(itemView);
        tvTenLoiNhac = itemView.findViewById(R.id.tvTenLoiNhac);
        swOnOff = itemView.findViewById(R.id.swOnOff);
    }
}
