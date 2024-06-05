package com.example.projectver3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.ThongKe;

import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder>{

    private List<ThongKe> list;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ThongKe thongKe);
    }

    public ThongKeAdapter(List<ThongKe> list, Context context, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ThongKeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv, parent, false);

        return new ThongKeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeViewHolder holder, int position) {
        final ThongKe thongKe = list.get(position);
        if (thongKe == null){
            return;
        }
        else {
            holder.bindData(thongKe);
        }
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(thongKe);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    //
    public class ThongKeViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivIcon;
        private TextView ten, soTien;

        private RelativeLayout layout_item;

        public ThongKeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iconChiPhi);
            ten = itemView.findViewById(R.id.tvtenChiPhi);
            soTien = itemView.findViewById(R.id.tvChiPhi);
            layout_item = itemView.findViewById(R.id.layout_item);
        }

        public void bindData(final ThongKe thongKe) {
            ten.setText(thongKe.getTenGD());
            soTien.setText(thongKe.getTongTien().toString());
            // Đặt hình ảnh cho ShapeableImageView
            if (thongKe.getHinh() != null) {
                int resId = itemView.getContext().getResources().getIdentifier(thongKe.getHinh(), "drawable", itemView.getContext().getPackageName());
                ivIcon.setImageResource(resId);
            }
            ivIcon.setBackgroundColor(Color.parseColor(thongKe.getMau().toString()));
            // Xử lý khi cell được nhấp
            layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(thongKe);
                    }
                }
            });
        }
    }
}

