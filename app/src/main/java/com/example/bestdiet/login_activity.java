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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button myButton = findViewById(R.id.start_registr);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditText pib = findViewById(R.id.editTextText2);
//                String pibString = pib.toString();
//                String[] splitpib = pibString.split(" ");
//                EditText email = findViewById(R.id.editTextTextEmailAddress);
//                String emailstring = email.toString();
//                EditText phone = findViewById(R.id.editTextPhone);
//                String phonestring = phone.toString();
//                EditText password = findViewById(R.id.editTextTextPassword);
//                String passwordstring = password.toString();
//                user user = null;
//                user.setUser("Malyobanyi","Maksim","Yaroslavovich","emailstring","phonestring","passwordstring");
//
//                MainActivity.getAppDatabase().userDao().insert(user);
                Intent intent = new Intent(login_activity.this, login_activitytwo.class);
                startActivity(intent);
            }
        });
    }
}
