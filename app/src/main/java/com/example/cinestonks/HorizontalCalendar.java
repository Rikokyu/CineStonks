package com.example.cinestonks;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HorizontalCalendar extends AppCompatActivity {
    RecyclerView rvCalendar;
    CalendarAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_calendar);
        rvCalendar = findViewById(R.id.rv_calendar); // Nhớ khai báo RecyclerView trong activity_main.xml


        // 1. Tạo dữ liệu cho 14 ngày tới
        List<String> dayNames = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", new Locale("vi", "VN"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 14; i++) {
            dayNames.add(i == 0 ? "Hôm nay" : dayFormat.format(calendar.getTime()));
            dates.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // 2. Thiết lập RecyclerView cuộn ngang
        adapter = new CalendarAdapter(dayNames, dates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCalendar.setLayoutManager(layoutManager);
        rvCalendar.setAdapter(adapter);
          }

}
