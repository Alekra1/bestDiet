package com.example.bestdiet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.records;
import com.example.bestdiet.database.user_message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class client_card_activity extends AppCompatActivity {

//    private static final String token = "7144334653:AAEAStoToGf7_P78Ep7FWLAzHWQDYW4sOOc";
//    private static final String CHAT_ID = "751112696";
//    private TelegramBotClient telegramBotClient;

    AppDatabase database;

    ClientDao clientDao;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private RecyclerView client_cardRecyclerView;
    private meal_of_plan_Adapter adapter;
    private MessagesAdapter adapter2;
    String clientId;


    List<TelegramMessage> messages = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_card);
        database = AppDatabase.getAppDatabase(getApplicationContext());
        clientDao = database.clientDao();

        List<Item> items = new ArrayList<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                clients client = null;
                Intent intent = getIntent();
                clientId = intent.getStringExtra("CLIENT_ID");
                if(clientId != null)
                {
                    client = clientDao.getclientbyid(Integer.parseInt(clientId));
                }
                if(client != null) {
                    header_client_card header = new header_client_card(client.getphoto_url(), client.firstName, client.middleName, client.lastName);
                    items.add(new Item(0,header));
                    body_client_card body = new body_client_card(client);
//                    SharedPreferences sharedPreferences = getSharedPreferences("client_prefs", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("client_full_name", client.getFirstName() + " " + client.getMiddleName() + " " + client.getLastName());
//                    editor.apply();
                    items.add(new Item(1,body));
                    end_client_card end = new end_client_card("Це рекомендація");
                    items.add(new Item(2,end));
                }

            }
        });

//        header_plan_menu header = new header_plan_menu("Сніданок",7);
//        items.add(new Item(0,header));
//        body_plan_menu body = new body_plan_menu("Полуниця",100);
//        items.add(new Item(1,body));
//        header_plan_menu header2 = new header_plan_menu("Вечеря",19);
//        items.add(new Item(0,header2));
//        body_plan_menu body2 = new body_plan_menu("Полуниця",100);
//        items.add(new Item(1,body2));


        client_cardRecyclerView = findViewById(R.id.client_cardRecyclerView);
        client_cardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        client_cardRecyclerView.setAdapter(new client_card_Adapter(items,clientId));
    }
}
