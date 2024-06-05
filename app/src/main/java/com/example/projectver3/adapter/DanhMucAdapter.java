package com.example.projectver3.adapter;

import static com.example.projectver3.AddUpdateDanhMucActivity.danhMucToEdit;
import static com.example.projectver3.fragment.CateFragment.data_DM;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.AddUpdateDanhMucActivity;
import com.example.projectver3.R;
import com.example.projectver3.model.DanhMuc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHolder> implements SwipeAndDragHelper.ItemTouchHelperAdapter {
    private List<DanhMuc> dsDanhMuc;
    private boolean isIncome;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ItemTouchHelper touchHelper;
    public static ArrayList<DanhMuc> dsEx = new ArrayList<>();
    public static ArrayList<DanhMuc> dsIn = new ArrayList<>();

    public DanhMucAdapter(List<DanhMuc> danhMucList, Context context, boolean isIncome) {
        this.dsDanhMuc = danhMucList;
        this.context = context;
        this.isIncome = isIncome;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        Collections.swap(dsDanhMuc, oldPosition, newPosition);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {

        if (!isIncome) {
            data_DM.child(String.valueOf(dsEx.get(position).getMaDanhMuc())).removeValue();
            dsEx.remove(position);
            notifyItemRemoved(position);
        } else {
            data_DM.child(String.valueOf(dsIn.get(position).getMaDanhMuc())).removeValue();
            dsIn.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

    public interface OnItemClickListener {
        void onItemClick(DanhMuc danhMuc);
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gd_danhmuc_item_cell, parent, false);

        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = dsDanhMuc.get(position);


        if (isIncome == danhMuc.isLoai()) {
            holder.bindData(danhMuc);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tạo Intent để mở AddUpdateTransactionActivity với trạng thái chỉnh sửa
                    Intent intent = new Intent(context, AddUpdateDanhMucActivity.class);
                    intent.putExtra("isEditMode", true); // Đây là chế độ chỉnh sửa
                    intent.putExtra("danhMucToEdit", (Serializable) danhMuc); // Truyền dữ liệu của danh mục cụ thể
                    danhMucToEdit = danhMuc;
                    intent.putExtra("position", holder.getAdapterPosition()); // Truyền dữ liệu của danh mục cụ thể
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (DanhMuc danhMuc : dsDanhMuc) {
            if (isIncome == danhMuc.isLoai()) {
                count++;
            }
        }
        return count;
    }

    public void updateData() {
        if (!isIncome) {
            dsDanhMuc.clear();
            dsDanhMuc = dsEx;
        } else {
            dsDanhMuc.clear();
            dsDanhMuc = dsIn;
        }
        notifyDataSetChanged();
    }
    public void updateData(ArrayList<DanhMuc> ex, ArrayList<DanhMuc> in) {
        if (!isIncome) {
            dsDanhMuc.clear();
            dsDanhMuc = ex;
            dsEx = ex;
        } else {
            dsDanhMuc.clear();
            dsDanhMuc = in;
            dsIn = in;
        }
        notifyDataSetChanged();
    }


    public class DanhMucViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCellName;
        private CircleImageView sivCellDanhMuc;

        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCellName = itemView.findViewById(R.id.tvCellName);
            sivCellDanhMuc = itemView.findViewById(R.id.sivCellDanhMuc);
        }

        public void bindData(final DanhMuc danhMuc) {
            tvCellName.setText(danhMuc.getTenDanhMuc());

            // Đặt hình ảnh cho ShapeableImageView
            if (danhMuc.getHinh() != null) {
                int resId = itemView.getContext().getResources().getIdentifier(danhMuc.getHinh(), "drawable", itemView.getContext().getPackageName());
                sivCellDanhMuc.setImageResource(resId);
            }
            sivCellDanhMuc.setColorFilter(Color.parseColor(danhMuc.getMau().toString()), PorterDuff.Mode.DST_ATOP);

            // Xử lý khi cell được nhấp
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(danhMuc);
                    }
                }
            });
        }
    }
}
