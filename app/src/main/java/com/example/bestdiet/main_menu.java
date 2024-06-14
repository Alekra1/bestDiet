package com.example.bestdiet;

import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class main_menu extends AppCompatActivity {
    CalendarView calendarView;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        TextView gradient_name = findViewById(R.id.name_user);
        // Получение ширины текста
        TextPaint paint = gradient_name.getPaint();
        float textWidth = paint.measureText(gradient_name.getText().toString());


        // Задаем градиент
        LinearGradient linearGradient = new LinearGradient(
                0, 0, textWidth, 0,
                getResources().getColor(R.color.start_color),
                getResources().getColor(R.color.end_color),
                Shader.TileMode.CLAMP);

        gradient_name.getPaint().setShader(linearGradient);

        ImageButton task_complete = findViewById(R.id.task_buttom);
        task_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_complete.setImageResource(R.drawable.task_ready);
            }
        });

        ImageButton Buttom_plans_clients = findViewById(R.id.clients_imageButton);
        Buttom_plans_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buttom_plans_clients.setImageResource(R.drawable.users_font_select);
                Intent intent = new Intent(main_menu.this, List_plans_activity.class);
                startActivity(intent);
            }
        });
        ImageButton Buttom_chats_clients = findViewById(R.id.chatclients_imageButton);
        Buttom_chats_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buttom_chats_clients.setImageResource(R.drawable.chats_icon_select);
                Intent intent = new Intent(main_menu.this, List_chats_activity.class);
                startActivity(intent);
            }
        });

        ImageButton Buttom_user_profile = findViewById(R.id.profile_imageButton);
        Buttom_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buttom_user_profile.setImageResource(R.drawable.user_icon_select);
                Intent intent = new Intent(main_menu.this, user_profile_activity.class);
                startActivity(intent);
            }
        });

        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        setDate(Calendar.DAY_OF_MONTH, Calendar.MONTH, Calendar.YEAR);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(main_menu.this, dayOfMonth + "/" + month + 1 + "/" + year, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void getDate() {
        long date = calendarView.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        calendar.setTimeInMillis(date);
        String selected_date = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(this, selected_date, Toast.LENGTH_SHORT).show();
    }

    public void setDate(int day, int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);
    }
}
