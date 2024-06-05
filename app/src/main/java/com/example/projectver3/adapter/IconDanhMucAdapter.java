package com.example.projectver3.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

import java.util.List;

public class IconDanhMucAdapter extends RecyclerView.Adapter<IconDanhMucHolder> {
    private List<Integer> iconList;
    private Context context;

    private ItemClickListener itemClickListener;

    public IconDanhMucAdapter(List<Integer> iconList, Context context) {
        this.iconList = iconList;
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public IconDanhMucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.danhmuc_icon_cell, parent, false);
        return new IconDanhMucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconDanhMucHolder holder, int position) {
        int iconResId = iconList.get(position);
        holder.bindData(iconResId);

        int num = position+1;
        final String iconName = "img_" + num;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(iconName);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public interface ItemClickListener {
        void onItemClick(String iconName);
    }
}
