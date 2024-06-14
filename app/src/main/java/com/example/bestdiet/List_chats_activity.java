package com.example.bestdiet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.records;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class List_chats_activity extends AppCompatActivity {

        AppDatabase database;

        ClientDao clientDao;

        UserDao userDao;

        private static final String token = "7144334653:AAEAStoToGf7_P78Ep7FWLAzHWQDYW4sOOc";
        private static final String CHAT_ID = "751112696";

        List<clients> listclietns;
    private TelegramBotClient telegramBotClient;

         private ExecutorService executor = Executors.newSingleThreadExecutor();

    private RecyclerView recyclerviewlistchat;
        private ListAdapter adapter;
        List<client_in_client_chat> clientslist = new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_clients);
            database = AppDatabase.getAppDatabase(getApplicationContext());
            clientDao = database.clientDao();
            userDao = database.userDao();

            telegramBotClient = new TelegramBotClient();
            //telegramBotClient.getUpdates();
            telegramBotClient.sendMessage(CHAT_ID,"pRIVET");

            client_in_client_chat client = new client_in_client_chat("Мальований","Максим","Ярославович");
            clientslist.add(client);
            client = new client_in_client_chat("Макаров","Дмитро","Васильович");
            clientslist.add(client);
            client = new client_in_client_chat("Сергієнко","Макар","Сергійович");
            clientslist.add(client);
            client = new client_in_client_chat("Макаров","Макар","Макарович");
            clientslist.add(client);
            client = new client_in_client_chat("Дуб","Макар","Максимович");
            clientslist.add(client);
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userid = sharedPreferences.getString("cur_userid",null);
            records record = new records(Integer.valueOf(userid),1);


//            executor.execute(new Runnable() {
//                client_in_client_chat client = null;
//                @Override
//                   public void run() {
//                       listclietns = clientDao.getClientOfDoctor("ymalyovanyj@gmail.com");
//                       for (clients clients : listclietns) {
//                           if (clients != null) {
//                               client = new client_in_client_chat(clients.getFirstName(),clients.getMiddleName(), clients.getLastName());
//                           } else Log.e("List Firstname", "null");
//                           clientslist.add(client);
//                       }
//                   }
//            });;

            recyclerviewlistchat = findViewById(R.id.recyclerviewlistclients);
            recyclerviewlistchat.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ListAdapter(clientslist);
            Log.e("Test",String.valueOf(adapter.getItemCount()));
            recyclerviewlistchat.setAdapter(adapter);
        }
    }
