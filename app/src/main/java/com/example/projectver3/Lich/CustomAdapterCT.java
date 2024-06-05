package com.example.projectver3.Lich;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectver3.R;
import com.example.projectver3.model.GiaoDich;

import java.util.ArrayList;

public class CustomAdapterCT extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<GiaoDich> data;

    public CustomAdapterCT(@NonNull Context context, int resource, @NonNull ArrayList<GiaoDich> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        ImageView ivItem = convertView.findViewById(R.id.ivItem);

        TextView tvLoai = convertView.findViewById(R.id.tvLoai);
        TextView tvSoTien = convertView.findViewById(R.id.tvSoTien);

        GiaoDich temp = data.get(position);
        tvSoTien.setText(String.valueOf(temp.getSoTien()));
        tvLoai.setText(checkLoai(temp.getDanhMuc().isLoai()));

        if (temp.getDanhMuc().isLoai()){
            tvLoai.setTextColor(Color.RED);
        }else {
            tvLoai.setTextColor(Color.GREEN);
        }

//        if (temp.getDanhMuc().getMau().equalsIgnoreCase("purple")){
//            ivItem.setImageResource(R.drawable.baseline_catching_pokemon_24);
//            ivItem.setBackgroundColor(ContextCompat.getColor(context, R.color.purple));
//
//        }
//        if (temp.getDanhMuc().getMau().equalsIgnoreCase("green")){
//            ivItem.setImageResource(R.drawable.baseline_cruelty_free_24);
//            ivItem.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
//        }
//        if (temp.getDanhMuc().getMau().equalsIgnoreCase("gray")){
//            ivItem.setImageResource(R.drawable.baseline_local_fire_department_24);
//            ivItem.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
//        }
        int resId = convertView.getContext().getResources().getIdentifier(temp.getDanhMuc().getHinh(), "drawable", convertView.getContext().getPackageName());

        ivItem.setImageResource(resId);
        ivItem.setBackgroundColor(Color.parseColor(temp.getDanhMuc().getMau()));

        return convertView;
    }

    public String checkLoai(boolean a){
        if (a == true){
            return "Chi phi";
        }
        else {
            return "Thu nhap";
        }
    }

}
