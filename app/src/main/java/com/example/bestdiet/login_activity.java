package com.example.bestdiet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class login_activity extends AppCompatActivity {
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
                registr_activity a = null;
                if(a.isValidEmail(email)) {

                }
                else
                {
                    Log.d("MyTag", "Некоректно уведено ПІБ");
                    return;
                }

            }
        });
    }

}
