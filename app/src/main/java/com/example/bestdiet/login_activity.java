package com.example.bestdiet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class login_activity extends AppCompatActivity {

    private user user = new user();
    private AppDatabase appDatabase;
    private UserDao userDao;
    private ClientDao clientDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Button myButton = findViewById(R.id.register_button);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "bestDiet")
                .build();

        userDao = appDatabase.userDao();

        clientDao = appDatabase.clientDao();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // добавление в БД - ПІБ
                TextInputLayout textInputLayout = findViewById(R.id.PIB);
                EditText textInputEditText = textInputLayout.getEditText();
                String text = textInputEditText.getText().toString();
                String[] pibmas = text.split(" ");
                String PIB = text;
                if(pibmas.length == 3) {
                    user.setFirstName(pibmas[0]);
                    user.setMiddleName(pibmas[1]);
                    user.setLastName(pibmas[2]);
                }
                else Log.d("MyTag", "Некоректно уведено ПІБ");

                // добавление в БД - email
                textInputLayout = findViewById(R.id.email);
                textInputEditText = textInputLayout.getEditText();
                text = textInputEditText.getText().toString();
                user.setEmail(text);
                PIB = PIB + text;
                // добавление в БД - пароль
                textInputLayout = findViewById(R.id.password);
                textInputEditText = textInputLayout.getEditText();
                text = textInputEditText.getText().toString();
                user.setPassword(text);
                PIB = PIB + text;

//                user.setPhone("phonestring");
                String finalPIB = PIB;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.insert(user);
                        Log.d("MyTag", "Додано запис:" + finalPIB.toString());
                        List<user> users = userDao.getAllUsers();
                        user.setPhone("phonestring");
                        userDao.updateUser(user);

                    }
                });
            }
        });
    }
}
