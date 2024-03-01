package com.example.bestdiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class login_activity extends AppCompatActivity {
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button myButton = findViewById(R.id.register_button);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "bestDiet")
                .build();
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_activity.this, login_activitytwo.class);
                startActivity(intent);
                /*EditText pib = findViewById(R.id.editTextText2);
                String pibString = pib.toString();
                String[] splitpib = pibString.split(" ");
                EditText email = findViewById(R.id.editTextTextEmailAddress);
                String emailstring = email.toString();
                EditText phone = findViewById(R.id.editTextPhone);
                String phonestring = phone.toString();
                EditText password = findViewById(R.id.editTextTextPassword);
                String passwordstring = password.toString();
                user user = null;
                user.setUser(splitpib[0],splitpib[1],splitpib[2],emailstring,phonestring,passwordstring);
                appDatabase.userDao().insert(user);*/
            }
        });
    }
}
