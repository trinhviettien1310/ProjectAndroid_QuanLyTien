package com.example.projectver3;

import static com.example.projectver3.AddUpdateDanhMucActivity.imgColor;
import static com.example.projectver3.AddUpdateDanhMucActivity.temp;
import static com.example.projectver3.Add_Update_AccountActivity.account;
import static com.example.projectver3.Add_Update_AccountActivity.viewColor;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ColorActivity extends AppCompatActivity {
    ImageView ivColor;
    TextView tvHex;
    View viewColorLayout;
    Button btnLuuMau;
    Bitmap bitmap;

    String hexColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        setControl();
        setEvent();
    }
    private void setEvent() {
        ivColor.setDrawingCacheEnabled(true);
        ivColor.buildDrawingCache(true);

        ivColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = ivColor.getDrawingCache();

                    int pixel = bitmap.getPixel((int) motionEvent.getX(), (int)motionEvent.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    String hex  = "#" + Integer.toHexString(pixel);
                    hexColor = "#" + Integer.toHexString(pixel);
                    viewColorLayout.setBackgroundColor(Color.rgb(r,g,b));

                    tvHex.setText("RGB: " + r +", " + g + ", " + b + "\nHex: " + hex);
                    Toast.makeText(ColorActivity.this, hexColor, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

        btnLuuMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị Hex màu từ text view tvHex

                if (!hexColor.isEmpty()){
                    account.setColorAccount(hexColor);
                    viewColor.setBackgroundColor(Color.parseColor(hexColor));
                    finish();
                }else {
                    Toast.makeText(ColorActivity.this, "Hãy ấn vào màu muốn chọn trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setControl() {
        ivColor = findViewById(R.id.ivColor);
        tvHex = findViewById(R.id.tvHex);
        viewColorLayout = findViewById(R.id.viewColorLayout);
        btnLuuMau = findViewById(R.id.btnLuuMau);
    }
}