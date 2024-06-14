package com.example.bestdiet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.user;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class registr_activity extends AppCompatActivity {

    private user user = new user();
    private AppDatabase appDatabase;
    private UserDao userDao;
    private ClientDao clientDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    TextInputEditText userdata;


    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_activity);

        Button myButton = findViewById(R.id.register_button);

        AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());

        userDao = database.userDao();

        clientDao = database.clientDao();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // добавление в БД - ПІБ
                TextInputLayout textInputLayout = findViewById(R.id.firstname_redact);
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
                    showPiberorDialog("Некоректно уведено ПІБ","Уведено некоректне ПІБ. Будь ласка, перевірте правильність введених даних.");
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
                    showPiberorDialog("Некоректно уведено адресу електроної пошти","Уведено некоректне email. Будь ласка, перевірте правильність формату введення пошти.");
                    return;
                }

                // добавление в БД - пароль
                textInputLayout = findViewById(R.id.passwordregistr);
                textInputEditText = textInputLayout.getEditText();
                text = textInputEditText.getText().toString();
                user.setPassword(text);
                PIB = PIB + text;
                String finalPIB = PIB;

                SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                userdata = findViewById(R.id.Pib_input);
                editor.putString("Pib", String.valueOf(userdata.getText()));
                userdata = findViewById(R.id.email_input_reg);
                editor.putString("email", String.valueOf(userdata.getText()));
                userdata = findViewById(R.id.password_input);
                editor.putString("password", String.valueOf(userdata.getText()));
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        userDao.insert(user);
                        user = userDao.getLastAddedUser();
                        editor.putString("cur_userid", String.valueOf(user.getUid()));
                        editor.putString("cur_userid", String.valueOf(user.getUid()));
                        editor.apply();
                        Log.d("MyTag", "Додано запис:" + finalPIB.toString());
                        Intent intent = new Intent(registr_activity.this, correct_register.class);
                        startActivity(intent);
//                        for(user users : userDao.getAllUsers())
//                        {
//                            Log.d("Users", "User:" + users.firstName);
//                        }
                        user.setPhone("phonestring");
                        userDao.updateUser(user);

                    }
                });
            }
        });
    }

    private void showPiberorDialog(String title,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

        // Метод для получения текущего экземпляра appDatabase
    public AppDatabase getAppDatabase() {
            return appDatabase;
    }
}
