package com.example.projectver3.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Account;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<DialogHolder> {
    Context context;
    List<Account> dataAccount;
    OnItemClickListener onItemClickListener;
    private int selectedPosition = -1;


    public DialogAdapter(Context context, List<Account> dataAccount, OnItemClickListener onItemClickListenner) {
        this.context = context;
        this.dataAccount = dataAccount;
        this.onItemClickListener = onItemClickListenner;
    }

    @NonNull
    @Override
    public DialogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DialogHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_dialog,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DialogHolder holder, @SuppressLint("RecyclerView") int position) {
        Account account = dataAccount.get(position);
        holder.bindData(account);
        // Xử lý sự kiện khi RadioButton được nhấn
        holder.rdoDiaLog.setChecked(position == selectedPosition);
        holder.rdoDiaLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != selectedPosition) {
                    selectedPosition = position; // Cập nhật RadioButton đã chọn
                    notifyDataSetChanged(); // Cập nhật toàn bộ danh sách để thay đổi trạng thái của RadioButton
                    // Lưu giá trị RadioButton đã chọn vào SharedPreferences
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("selectedPosition", selectedPosition);
                    editor.apply();
                }
            }
        });

        //click recyclerView
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(account);
            if (position != selectedPosition) {
                selectedPosition = position; // Cập nhật RadioButton đã chọn
                notifyDataSetChanged(); // Cập nhật toàn bộ danh sách để thay đổi trạng thái của RadioButton
                // Lưu giá trị RadioButton đã chọn vào SharedPreferences
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selectedPosition", selectedPosition);
                editor.apply();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataAccount.size();
    }
    public interface OnItemClickListener{
        void onItemClick(Account account);
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged(); // Cập nhật RecyclerView để thay đổi trạng thái của RadioButton
    }


}
