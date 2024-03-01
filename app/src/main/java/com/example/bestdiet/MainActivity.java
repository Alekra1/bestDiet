package com.example.bestdiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.*;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "bestDiet")
                .build();

        Button myButton = findViewById(R.id.starting_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user user = null;
                user.setFirstName("Malyobanyi");
                user.setMiddleName("Maksim");
                user.setLastName("Yaroslavovich");
                user.setEmail("reerre");
                user.setPhone("phonestring");
                user.setPassword("passwordstring");

                appDatabase.userDao().insert(user);

                Intent intent = new Intent(MainActivity.this, login_activity.class);
                startActivity(intent);
            }
        });
    }
}
