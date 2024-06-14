package com.example.bestdiet;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.OnChatButtonClickListener;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.client_message;
import com.example.bestdiet.database.recordsDao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MessageViewHolder> {
        private List<client_in_client_chat> clients;

        private OnChatButtonClickListener listener;

        static AppDatabase database;
        static recordsDao records;
        static ClientDao client;
        static UserDao user;



    public ListAdapter(List<client_in_client_chat> clients) {
            this.clients = clients;

            this.listener = listener;

        }

        @NonNull
        @Override
        public ListAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_element_in_chat_list, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            client_in_client_chat client = clients.get(position);
            holder.firstname.setText(client.getFirstname());
            holder.name.setText(client.getname());
            holder.lastname.setText(client.getlastname());
            //holder.senderName.setText(message.getSenderName());
        }

        @Override
        public int getItemCount() {
            return clients.size();
        }

        static class MessageViewHolder extends RecyclerView.ViewHolder {
            TextView firstname,name,lastname;


            ImageButton select_chat;

            List<client_message> list_message = new ArrayList<>();
            client_message client_message;

            private TelegramBotClient telegramBotClient;

            TextView senderName;

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                firstname = itemView.findViewById(R.id.firstname);
                name = itemView.findViewById(R.id.name);
                lastname = itemView.findViewById(R.id.lastname);
                select_chat = itemView.findViewById(R.id.imageButton7);
                //senderName = itemView.findViewById(R.id.senderName);

                select_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Выбраный чат", firstname.getText().toString());
                        Intent intent = new Intent(v.getContext(), Chat_activity.class);
                        intent.putExtra("CLIENT_firstNAME", firstname.getText().toString());
                        telegramBotClient = new TelegramBotClient();
                        database = AppDatabase.getAppDatabase(v.getContext());
                        records = database.recordsDao();
                        client = database.clientDao();
                        user = database.userDao();

                        telegramBotClient.getUpdatesByUserId(new UpdatesCallback() {
                            @Override
                            public void onUpdatesReceived(List<Message> messages) {
                                // Обработка полученных сообщений
                                for (Message message : messages) {
//                                    client.getClientOfDoctor();
//                                    user.getusersbyfirstname(firstname.getText().toString());
//                                    records.getrecordId();
                                    String date = formatTimestamp(message.getDate()); // Форматируем временную метку
                                    client_message clientMessage = new client_message(message.getMessageId(), 1, date, message.getText()); // Создаем объект client_message
                                    list_message.add(clientMessage); // Добавляем объект в список
                                }
                                if(list_message == null)  Log.e("list_message", "null");
                                else {
                                    for (client_message test : list_message) {
                                        Log.d("list_message", String.valueOf(test.message_id));
                                    }
                                }
                                intent.putParcelableArrayListExtra("List_new_message", new ArrayList<>(list_message));
                                v.getContext().startActivity(intent);
                            }

                            @Override
                            public void onError(Throwable t) {
                                // Обработка ошибки
                                Log.e("TelegramBotClient", "Error fetching updates", t);
                            }
                        },751112696);

                    }
                });
            }
            public static String formatTimestamp(long timestamp) {
                // Создаем объект Date из UNIX timestamp
                Date date = new Date(timestamp * 1000); // Конвертируем секунды в миллисекунды
                // Создаем форматтер для вывода даты
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // Устанавливаем временную зону
                sdf.setTimeZone(TimeZone.getDefault());
                // Возвращаем отформатированную дату
                return sdf.format(date);
            }

        }
    }