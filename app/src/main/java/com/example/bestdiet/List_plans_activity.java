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
import com.example.bestdiet.database.client_in_client_list;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.records;
import com.example.bestdiet.database.recordsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class List_plans_activity extends AppCompatActivity {

    AppDatabase database;

    private reloadData reload;

    ClientDao clientDao;

    UserDao userDao;

    recordsDao RecordsDao;

    private static final String token = "7144334653:AAEAStoToGf7_P78Ep7FWLAzHWQDYW4sOOc";
    private static final String CHAT_ID = "751112696";

    List<clients> listclietns = null;
    private TelegramBotClient telegramBotClient;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private RecyclerView recyclerviewlistchat;
    private List_plansAdapter adapter;
    List<client_in_client_list> clientslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_clients);
        database = AppDatabase.getAppDatabase(getApplicationContext());
        clientDao = database.clientDao();
        userDao = database.userDao();
        RecordsDao = database.recordsDao();

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String user_email = sharedPreferences.getString("email",null);

        executor.execute(new Runnable() {
            client_in_client_list client = null;
                @Override
                   public void run() {
                       listclietns = clientDao.getClientOfDoctor(user_email);
                       int i=0;
                       for (clients clients : listclietns) {
                           if(i>=10)
                               break;
                           if (clients != null) {
                               client = new client_in_client_list(clients);
                           } else Log.e("List Firstname", "null");
                           clientslist.add(client);
                           i++;
                       }
                   }
            });;

        recyclerviewlistchat = findViewById(R.id.recyclerviewlistclients);
        recyclerviewlistchat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new List_plansAdapter(clientslist);
        recyclerviewlistchat.setAdapter(adapter);
    }
}
