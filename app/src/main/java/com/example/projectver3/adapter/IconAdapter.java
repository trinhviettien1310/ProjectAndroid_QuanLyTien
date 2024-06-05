package com.example.projectver3.adapter;

import static com.example.projectver3.Add_Update_AccountActivity.tvCheckIcon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;

import java.util.List;

public class IconAdapter extends RecyclerView.Adapter<IconHolder> {
    private List<Integer> iconList;
    private Context context;

    private ItemClickListener itemClickListener;

    public IconAdapter(List<Integer> iconList, Context context) {
        this.iconList = iconList;
        this.context = context;
    }

    @NonNull
    @Override
    public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IconHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconHolder holder, int position) {
        int iconResId = iconList.get(position);
        holder.bindData(iconResId);

        int num = position+1;
        final String iconName = "account_" + num;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    tvCheckIcon.setText("  ");
                    itemClickListener.onItemClick(iconName);
                }
            }
        });
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(String iconName);
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }
}
