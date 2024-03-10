package com.example.bestdiet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class login_activity extends AppCompatActivity {

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Button myButton = findViewById(R.id.login_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, registr_activity.class);
//                startActivity(intent);
                TextInputLayout textInputLayout = findViewById(R.id.email_login);
                EditText textInputEditText = textInputLayout.getEditText();
                String email = textInputEditText.getText().toString();

                if (isValidEmail(email)) {
                    AppDatabase database = AppDatabase.getAppDatabase(getApplicationContext());

                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            return database.userDao().checkuserbyemail(email);
                        }
                        @Override
                        protected void onPostExecute(Boolean userExists) {
                            if (userExists) {
                                Log.d("MyTag", "Користувач існує");
                            } else {
                                Log.d("MyTag", "Користувача не існує");
                            }
                        }
                    }.execute();
                } else {
                    Log.d("MyTag", "Некоректно уведено email");
                    return;
                }


            }
        });
    }

}
