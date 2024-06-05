package com.example.projectver3.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectver3.R;
import com.example.projectver3.model.Remind;

import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RemindHolder> {
    Context context;
    List<Remind> data;
    OnItemClickListener onItemClickListener;

    public RemindAdapter(Context context, List<Remind> data,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RemindHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RemindHolder((LayoutInflater.from(context).inflate(R.layout.layout_item_remind,parent,false)));
    }

    @Override
    public void onBindViewHolder(@NonNull RemindHolder holder, int position) {
        Remind remind = data.get(position);
        holder.tvTenLoiNhac.setText(remind.getNameRemind());
        holder.swOnOff.setChecked(remind.getFlag());
        holder.swOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bật
                if (holder.swOnOff.isChecked()){
                    Toast.makeText(context, "On", Toast.LENGTH_SHORT).show();
                }
                //tắt
                else {
//                    Intent intent = new Intent(context, Remind_AlarmReceiver.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                    if (alarmManager != null){
//                        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                    }
//                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //click recyclerView
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(remind);
        });
    }
    public interface OnItemClickListener {
        void onItemClick(Remind remind);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
