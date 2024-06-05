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
import com.example.projectver3.model.GiaoDich;

import java.util.List;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.DanhSachViewHolder>{
    private List<GiaoDich> list;
    private Context context;

    public DanhSachAdapter(List<GiaoDich> list, Context context, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DanhSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ds, parent, false);

        return new DanhSachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachViewHolder holder, int position) {
        final GiaoDich giaoDich = list.get(position);
        if (giaoDich == null){
            return;
        }
        else {
            holder.bindData(giaoDich);
        }
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(giaoDich);
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

    public interface OnItemClickListener {
        void onItemClick(GiaoDich giaoDich);
    }

    public class DanhSachViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivIcon;
        private TextView tvTen, tvSoTien, tvNgay;

        private RelativeLayout layout_item;
        public DanhSachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvtenGD);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
            tvNgay = itemView.findViewById(R.id.tvNgayGD);
            ivIcon = itemView.findViewById(R.id.iconGD);
            layout_item = itemView.findViewById(R.id.item_ds);
        }
        public void bindData(final GiaoDich giaoDich) {
            tvTen.setText(giaoDich.getDanhMuc().getTenDanhMuc());
            tvSoTien.setText(giaoDich.getSoTien());
            tvNgay.setText(giaoDich.getNgayGiaoDich());
            // Đặt hình ảnh cho ShapeableImageView
            if (giaoDich.getDanhMuc().getHinh() != null) {
                int resId = itemView.getContext().getResources().getIdentifier(giaoDich.getDanhMuc().getHinh(), "drawable", itemView.getContext().getPackageName());
                ivIcon.setImageResource(resId);
            }
            ivIcon.setBackgroundColor(Color.parseColor(giaoDich.getDanhMuc().getMau().toString()));
            // Xử lý khi cell được nhấp
            layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(giaoDich);
                    }
                }
            });
        }
    }
}
