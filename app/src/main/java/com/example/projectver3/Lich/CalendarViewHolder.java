package com.example.projectver3.Lich;



import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.GiaoDich;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;
    private final ArrayList<GiaoDich> dsData;
    private final LocalDate selectedDate;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<GiaoDich> dsData, LocalDate selectedDate) {
        super(itemView);
        this.selectedDate = selectedDate;
        this.dsData = dsData;
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String clickedDay = dayOfMonth.getText().toString();
        LocalDate clickedDate = selectedDate.withDayOfMonth(Integer.parseInt(clickedDay));

        int matchedCount = 0; // Số lượng ngày trùng khớp

        ArrayList<GiaoDich> data = new ArrayList<>();

        // Kiểm tra clickedDate với các mục trong dsData
        for (GiaoDich tienTe : dsData) {
            LocalDate tienTeDate = getFormattedDate(tienTe);
            if (tienTeDate != null && tienTeDate.isEqual(clickedDate)) {
                matchedCount++;
                data.add(tienTe);
            }
        }

        if (matchedCount > 0) {
            // Có ngày trùng khớp với dsData
            // Hiển thị số lượng ngày trùng khớp
            String message = "Số lượng ngày trùng khớp: " + matchedCount;
            Toast.makeText(itemView.getContext(), message, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(itemView.getContext(), MainChiTietLich.class); // SecondActivity là tên Activity bạn muốn mở
            intent.putExtra("data", data); // Đặt dữ liệu vào Intent
            itemView.getContext().startActivity(intent); // Khởi chạy SecondActivity và gửi dữ liệu cùng với Intent


        } else {
            // Không có ngày trùng khớp với dsData
            onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
        }
    }
    public LocalDate getFormattedDate(GiaoDich gd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(gd.getNgayGiaoDich(), formatter);
        return date; // Trả về ngày không định dạng nếu có lỗi
    }
}
