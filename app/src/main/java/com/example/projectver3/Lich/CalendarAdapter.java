package com.example.projectver3.Lich;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.GiaoDich;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final ArrayList<GiaoDich> dsData;
    private final LocalDate selectedDate;


    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, ArrayList<GiaoDich> dsData, LocalDate selectedDate) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.dsData = dsData;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.1);


        return new CalendarViewHolder(view, onItemListener, dsData, selectedDate);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));

        String currentDay = daysOfMonth.get(position);

        // Kiểm tra xem currentDay có giá trị hợp lệ không
        if (currentDay != null && !currentDay.isEmpty()) {
            holder.dayOfMonth.setText(currentDay);

            // Chuyển đổi currentDay thành số nguyên
            int dayValue = Integer.parseInt(currentDay);

            LocalDate clickedDate = selectedDate.withDayOfMonth(dayValue);
            int matchedCount = 0; // Số lượng ngày trùng khớp
            int thuChi = 0;

            // Kiểm tra clickedDate với các mục trong dsData
            for (GiaoDich tienTe : dsData) {
                LocalDate tienTeDate = getFormattedDate(tienTe);
                if (tienTeDate != null && tienTeDate.equals(clickedDate)) {
                    matchedCount++;
                }
            }
            Log.d("Thuchi", String.valueOf(thuChi));

            if (matchedCount > 0) {
                holder.dayOfMonth.setTextColor(Color.GREEN);
            }
        } else {
            holder.dayOfMonth.setText(""); // Nếu currentDay không hợp lệ, hiển thị chuỗi trống hoặc xử lý theo ý bạn.
        }


    }


    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }
    public LocalDate getFormattedDate(GiaoDich gd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(gd.getNgayGiaoDich(), formatter);
        return date; // Trả về ngày không định dạng nếu có lỗi
    }
}
