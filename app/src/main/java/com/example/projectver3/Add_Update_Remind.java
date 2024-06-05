package com.example.projectver3;


import static com.example.projectver3.fragment.RemindFragment.DocDL;
import static com.example.projectver3.fragment.RemindFragment.dataRemind;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectver3.databinding.ActivityCreateUpdateRemindBinding;
import com.example.projectver3.fragment.RemindFragment;
import com.example.projectver3.login.LoginActivity;
import com.example.projectver3.model.Remind;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Add_Update_Remind extends AppCompatActivity {

    private ActivityCreateUpdateRemindBinding binding;
    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;
    Button btnBack,btnCreateRemind,btnDeleteRemind;
    TextView tvToolBar,tvDate,tvTime;
    EditText edtNameRemind,edtNoteRemind,edtIDRemind;
    Spinner spNhacNho;
    ArrayList<String> data_SP = new ArrayList<>();
    ArrayAdapter adapter_SP;
    Calendar calendar;
    public static Remind remind = new Remind();;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateUpdateRemindBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        CreateNotification();
        setControl();
        setEvent();


    }
    private void setControl() {
        tvToolBar = findViewById(R.id.tvToolbar);
        tvTime = findViewById(R.id.tvTime);
        tvDate = findViewById(R.id.tvDate);
        spNhacNho = findViewById(R.id.spNhacNho);
        edtIDRemind = findViewById(R.id.edtIDRemind);
        edtNameRemind = findViewById(R.id.edtNameRemind);
        edtNoteRemind = findViewById(R.id.edtNoteRemind);
        btnCreateRemind = findViewById(R.id.btnCreateRemind);
        btnBack = findViewById(R.id.btnBack);
        btnDeleteRemind = findViewById(R.id.btnDeleteRemind);

    }
    private void setEvent() {
        Boolean sflag = getIntent().getBooleanExtra("isUpdate",false);
        //Update
        if (sflag != false){
            //Spinner
            spinner();
            adapter_SP = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, data_SP);
            spNhacNho.setAdapter(adapter_SP);
            //
            binding.btnCreateRemind.setText("Lưu");
            binding.tvToolbar.setText("Chỉnh sửa nhắc nhở");
            if (getIntent().getExtras() != null){
                Remind r = (Remind) getIntent().getExtras().get("remindUpdate");
                binding.edtIDRemind.setText(r.getIdRemind());
                binding.edtNameRemind.setText(r.getNameRemind());
                binding.spNhacNho.getSelectedItem().toString();
                binding.tvTime.setText(r.getTimeRemind());
                binding.tvDate.setText(r.getDateRemind());
                binding.edtNoteRemind.setText(r.getNoteRemind());
            }
            binding.tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time();
                }
            });
            binding.tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date();
                }
            });
            //update
            binding.btnCreateRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataRemind.child(edtIDRemind.getText().toString()).child("nameRemind").setValue(edtNameRemind.getText().toString());
                    dataRemind.child(edtIDRemind.getText().toString()).child("spRemind").setValue(spNhacNho.getSelectedItem().toString());
                    dataRemind.child(edtIDRemind.getText().toString()).child("timeRemind").setValue(tvTime.getText().toString());
                    dataRemind.child(edtIDRemind.getText().toString()).child("dateRemind").setValue(tvDate.getText().toString());
                    dataRemind.child(edtIDRemind.getText().toString()).child("noteRemind").setValue(edtNoteRemind.getText().toString());
                    finish();
                }
            });
            binding.btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            //delete
            btnDeleteRemind.setText("Xóa");
            btnDeleteRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataRemind.child(edtIDRemind.getText().toString()).removeValue();
//                    Intent intent = new Intent(Add_Update_Remind.this, RemindFragment.class);
//                    startActivity(intent);
                    DocDL();
                    finish();
                }
            });
        }
        //Create
        else {
            //Spinner
            spinner();
            adapter_SP = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, data_SP);
            spNhacNho.setAdapter(adapter_SP);
            //
            binding.btnCreateRemind.setText("Tạo");
            binding.tvToolbar.setText("Tạo nhắc nhở");
            binding.tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time();

                }
            });
            binding.tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date();
                }
            });
            binding.btnCreateRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check() == true){
                        setAlarm();
                        remind.setIdRemind(dataRemind.push().getKey());
                        remind.setMailRemind(LoginActivity.currenEmailUser);
                        remind.setNameRemind(edtNameRemind.getText().toString());
                        remind.setSpRemind(spNhacNho.getSelectedItem().toString());
                        remind.setTimeRemind(tvTime.getText().toString());
                        remind.setDateRemind(tvDate.getText().toString());
                        remind.setNoteRemind(edtNoteRemind.getText().toString());
                        remind.setFlag(true);
                        //CreateRemind(remind);
                        dataRemind.child(remind.getIdRemind()).setValue(remind);
                        //chuyển trang
//                        Intent intent = new Intent(Create_Update_Remind.this, RemindFragment.class);
//                        startActivity(intent);
                        finish();
                    }
                }
            });
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void spinner() {
        data_SP.add("Một lần");
        data_SP.add("Hằng ngày");
        data_SP.add("Mỗi tuần một lần");
    }
    //check
    private boolean check(){
        if (edtNameRemind.getText().toString().isEmpty()){
            edtNameRemind.setError("Vui lòng nhập tên");
            return false;
        }
        if (tvTime.getText().toString().equals("Chọn giờ")){
            Toast.makeText(this, "Vui lòng nhập giờ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (tvDate.getText().toString().equals("Chọn ngày")){
            Toast.makeText(this, "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Chọn ngày
    private void date() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (calendar == null){
            calendar = Calendar.getInstance();
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Update_Remind.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                calendar.set(Calendar.YEAR,yy);
                calendar.set(Calendar.MONTH,mm);
                calendar.set(Calendar.DAY_OF_MONTH,dd);
                // Sử dụng SimpleDateFormat để định dạng ngày tháng
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(calendar.getTime());
                binding.tvDate.setText(formattedDate);
            }
        },year,month,day);
        datePickerDialog.show();
    }
    //Chọn thời gian
    private void time() {
        calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Add_Update_Remind.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                calendar.set(Calendar.HOUR_OF_DAY,h);
                calendar.set(Calendar.MINUTE,m);
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                // Sử dụng SimpleDateFormat để định dạng thời gian
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String formattedTime = sdf.format(calendar.getTime());
                binding.tvTime.setText(formattedTime);
            }
        },hh,mm,false);
        timePickerDialog.show();
    }


    //Tạo thông báo
    private void CreateNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "channel";
            String desc = "Channle for alarm";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Remind_AlarmReceiver.CHANNEL_ID,desc,imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void setAlarm() {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, Remind_AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_MUTABLE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            Toast.makeText(this, "Alarm", Toast.LENGTH_SHORT).show();
    }
}