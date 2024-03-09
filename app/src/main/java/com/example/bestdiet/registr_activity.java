package com.example.bestdiet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class registr_activity extends AppCompatActivity {

    private user user = new user();
    private AppDatabase appDatabase;
    private UserDao userDao;
    private ClientDao clientDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_activity);

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
                TextInputLayout textInputLayout = findViewById(R.id.PIBregistr);
                EditText textInputEditText = textInputLayout.getEditText();
                String text = textInputEditText.getText().toString();
                String[] pibmas = text.split(" ");
                String PIB = text;
                if(pibmas.length == 3) {
                    user.setFirstName(pibmas[0]);
                    user.setMiddleName(pibmas[1]);
                    user.setLastName(pibmas[2]);
                }
                else
                {
                    Log.d("MyTag", "Некоректно уведено ПІБ");
                    return;
                }

                // добавление в БД - email
                textInputLayout = findViewById(R.id.emailregistr);
                textInputEditText = textInputLayout.getEditText();
                text = textInputEditText.getText().toString();
                if (isValidEmail(text)) {
                    user.setEmail(text);
                    PIB = PIB + text;
                } else {
                    Log.d("MyTag","Введите корректный адрес электронной почты");
                    return;
                }

                // добавление в БД - пароль
                textInputLayout = findViewById(R.id.passwordregistr);
                textInputEditText = textInputLayout.getEditText();
                text = textInputEditText.getText().toString();
                user.setPassword(text);
                PIB = PIB + text;
                String finalPIB = PIB;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.insert(user);
                        Log.d("MyTag", "Додано запис:" + finalPIB.toString());
//                        List<user> users = userDao.getAllUsers();
                        user.setPhone("phonestring");
                        userDao.updateUser(user);

                    }
                });
                Intent intent = new Intent(registr_activity.this, correct_register.class);
                startActivity(intent);
            }
        });
    }
}
