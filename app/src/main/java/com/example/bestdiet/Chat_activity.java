package com.example.bestdiet;

import static com.example.bestdiet.ListAdapter.MessageViewHolder.formatTimestamp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.Client_messageDao;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.client_message;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.records;
import com.example.bestdiet.database.recordsDao;
import com.example.bestdiet.database.user;
import com.example.bestdiet.database.user_message;
import com.example.bestdiet.database.user_messageDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Chat_activity extends AppCompatActivity {

    AppDatabase database;
    clients clients;

    UserDao User;

    ClientDao clientDao;

    Client_messageDao messageDao_client;
    user_messageDao messageDao_user;

    recordsDao record;
    boolean isWriting = false;

    private RecyclerView chat_recyclerview;
    private MessagesAdapter adapter;
    List<TelegramMessage> messages_client = new ArrayList<>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ExecutorService executor2 = Executors.newSingleThreadExecutor();


    ArrayList<client_message> listMessage;
    List<client_message> list_messages_DB = null;
    List<user_message> list_messages_user_DB = null;

    private TelegramBotClient telegramBotClient;
    private static final String CHAT_ID = "751112696";


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_whis_client);
        telegramBotClient = new TelegramBotClient();
        //telegramBotClient.getUpdates();
        // Initialize database and DAOs
        database = AppDatabase.getAppDatabase(getApplicationContext());
        messageDao_client = database.clientMessageDao();
        messageDao_user = database.userMessageDao();
        record = database.recordsDao();
        User = database.userDao();
        String clientName = getIntent().getStringExtra("CLIENT_NAME");
        listMessage = getIntent().getParcelableArrayListExtra("List_new_message");

        // Initialize RecyclerView and adapter
        chat_recyclerview = findViewById(R.id.chat_recyclerview);
        chat_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessagesAdapter(messages_client);
        chat_recyclerview.setAdapter(adapter);

//        user user = new user.Builder().firstName("Мальований").email("ymalyovanyj@gmail.com").build();
//        User.insert(user);
//        clients client = new clients.Builder().firstName("Мальований").email("ymalyovanyj@gmail.com").build();
//        clientDao.insert(client);
//
//        records record_new = new records(1,1);
//        record.insert(record_new);
        // Load messages from the database
        loadMessagesFromDb();

        checkForNewMessages();

        ImageButton send_message_user = findViewById(R.id.button_chatbox_send);
        send_message_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user_message_text = findViewById(R.id.edittext_user_message);
                String messageText = user_message_text.getText().toString();

                if (messageText.isEmpty()) {
                    // Optionally, you can show a toast message or error if the text is empty
                    return;
                }

                executor2.execute(new Runnable() {

                    @Override
                    public void run() {
                        user_message message;
                        records test = record.getRecordsByUserEmailAndClientSurname("ymalyovanyj@gmail.com", "Мальований");
                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        if (test != null)
                            message = new user_message(test.record_id, currentTime, messageText);
                        else
                            message = new user_message(1, "sosiska", messageText);

                        if (message != null) {
                            messageDao_user.insert(message);
                            Log.d("Message add", "Message added: " + messageText);

                            // Create a new TelegramMessage
                            TelegramMessage mes = new TelegramMessage(message.text, "Maxik", false);

                            // Update the messages list and notify the adapter
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messages_client.add(mes);
                                    adapter.notifyItemInserted(messages_client.size() - 1);
                                    chat_recyclerview.scrollToPosition(messages_client.size() - 1);
                                }
                            });
                            telegramBotClient.sendMessage(CHAT_ID,message.text);
                        } else {
                            Log.d("Message null", "Message is null");
                        }
                    }
                });

                // Clear the input field after sending the message
                user_message_text.setText("");
            }
        });
        // Add initial messages
        //addInitialMessages();
    }

    private void loadMessagesFromDb() {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                // Adding client_message to the database
                for (client_message message : listMessage) {
                    if (message == null) {
                        Log.d("null", "null");
                    } else {
                        // Check for the /write_doctor command
                        if (message.text.equals("/write_doctor")) {
                            isWriting = true; // Set the flag to start writing
                            continue; // Skip the command itself to not write it
                        }

                        // Stop writing when another command is detected
                        if (message.text.startsWith("/")) {
                            isWriting = false; // Reset the flag when another command is detected
                        }

                        // If the write flag is set, add the message to the database
                        if (isWriting) {
                            if (messageDao_client.getAllclientmessagesByid(message.message_id) == null) {
                                messageDao_client.insert(message);
                                Log.d("insert", "inserted: " + message.text);
                            } else {
                                Log.d("Not insert", "Message already exists: " + message.text);
                            }
                        }
                    }
                }
                // Load messages from the database
                list_messages_DB = messageDao_client.getAllclientmessages();
                list_messages_user_DB = messageDao_user.getAllclientmessages();

                int start_index = 0;

                // Добавляем сообщения из списка list_messages_DB
                if (list_messages_DB != null && list_messages_user_DB != null) {
                    if(list_messages_DB.size() > list_messages_user_DB.size())
                    {
                        messages_client.clear();
                        for (client_message i : list_messages_DB) {
                            for (int index = start_index; index < list_messages_user_DB.size(); index++) {

                                if (compare(i, list_messages_user_DB.get(index)) == 1) {
                                    TelegramMessage mes = new TelegramMessage(list_messages_user_DB.get(index).text, "Maxik", false);
                                    messages_client.add(mes);
                                    start_index++;
                                }
                            }
                            TelegramMessage mes = new TelegramMessage(i.text, "Maxik", true);
                            messages_client.add(mes);
                        }
                    }
                    else
                    {
                        messages_client.clear();
                        for (user_message i : list_messages_user_DB) {
                            for (int index = start_index; index < list_messages_DB.size(); index++) {

                                if (compare(list_messages_DB.get(index), i) == -1) {
                                    TelegramMessage mes = new TelegramMessage(list_messages_DB.get(index).text, "Maxik", true);
                                    messages_client.add(mes);
                                    Log.d("сообщение ", "вивожу дату client: " + list_messages_DB.get(index).getdate());
                                    start_index++;
                                }
                            }
                            TelegramMessage mes = new TelegramMessage(i.text, "Maxik", false);
                            Log.d("сообщение ", "вивожу дату user: " + i.getdate());
                            messages_client.add(mes);
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public int compare(client_message message1, user_message message2) {
        try {
            Date date1 = dateFormat.parse(message1.getdate());
            Date date2 = dateFormat.parse(message2.getdate());
            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            // Если не удается разобрать дату, возвращаем 0 (равенство)
            return 0;
        }
    }
        private void checkForNewMessages() {

        TelegramBotClient telegramBotClient = new TelegramBotClient();

        ArrayList<client_message> listMessage = new ArrayList<>();

        messageDao_client = database.clientMessageDao();

        int offset = 0;
        executor.execute(new Runnable() {
            client_message clientMessage = null;
            @Override
            public void run() {
                while (true) { // Infinite loop to continuously check for new messages
                    telegramBotClient.getUpdatesByUserId(new UpdatesCallback() {
                        @Override
                        public void onUpdatesReceived(List<Message> messages) {
                            if (!messages.isEmpty()) {
                                for (Message message : messages) {
                                    String date = formatTimestamp(message.getDate()); // Форматируем временную метку
                                    client_message clientMessage = new client_message(message.getMessageId(), 1, date, message.getText()); // Создаем объект client_message
                                    listMessage.add(clientMessage); // Добавляем объект в список

                                    // Обновление UI
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            TelegramMessage newMessage = new TelegramMessage(clientMessage.text, "Maxik", true);
                                            messages_client.add(newMessage);
                                            adapter.notifyItemInserted(messages_client.size() - 1);
                                            chat_recyclerview.scrollToPosition(messages_client.size() - 1);
                                        }
                                    });
                                }
                            } else {
                                Log.d("TelegramBotClient", "No new messages");
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Обработка ошибки
                            Log.e("TelegramBotClient", "Error fetching updates", t);
                        }
                    }, 751112696);

                    for (client_message message : listMessage) {
                        if (message == null) {
                            Log.d("null", "null");
                        } else {
                            // Check for the /write_doctor command
                            isWriting = true; // Set the flag to start writing

                            // Stop writing when another command is detected
                            if (message.text.startsWith("/")) {
                                isWriting = false; // Reset the flag when another command is detected
                            }

                            // If the write flag is set, add the message to the database
                            if (isWriting) {
                                if (messageDao_client.getAllclientmessagesByid(message.message_id) == null) {
                                    messageDao_client.insert(message);
                                    Log.d("insert", "inserted: " + message.text);
                                } else {
                                    Log.d("Not insert", "Message already exists: " + message.text);
                                }
                            }
                        }
                    }

                    try {
                        Thread.sleep(5000); // Wait for 5 seconds before checking for new messages again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//    private void addInitialMessages() {
//        // Adding initial messages
//        // This is just an example of how you could add initial messages to the list
//        messages_client.add(new TelegramMessage("Hello!", "Maxik", true));
//        adapter.notifyItemInserted(messages_client.size() - 1);
//    }
    }
}
