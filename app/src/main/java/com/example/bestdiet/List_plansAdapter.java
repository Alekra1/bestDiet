package com.example.bestdiet;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.OnChatButtonClickListener;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.client_in_client_list;
import com.example.bestdiet.database.client_message;
import com.example.bestdiet.database.recordsDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class List_plansAdapter extends RecyclerView.Adapter<List_plansAdapter.MessageViewHolder> {
    private static List<client_in_client_list> clients;

    private OnChatButtonClickListener listener;

    static AppDatabase database;
    static recordsDao records;
    static ClientDao client;
    static UserDao user;

    static String img_url = null;

    static client_in_client_list client_in_list;


    public List_plansAdapter(List<client_in_client_list> clients) {
        this.clients = clients;

        this.listener = listener;

    }

    @NonNull
    @Override
    public List_plansAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_in_plans_list, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        client_in_list = clients.get(position);
        img_url = client_in_list.getPhoto_url();
        holder.firstname.setText(client_in_list.getFirstname());
        holder.name.setText(client_in_list.getname());
        holder.lastname.setText(client_in_list.getlastname());
        holder.year.setText("Рік: " + String.valueOf(client_in_list.getYear()));
        holder.gender.setText("Стать: " + client_in_list.getGender());
        Glide.with(holder.client_photo.getContext())
                .load(client_in_list.getPhoto_url())
                .into(holder.client_photo);

        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void updateData(List<client_in_client_list> newclientsdata) {
        clients = newclientsdata;
        notifyDataSetChanged(); // Уведомляем адаптер о внесенных изменениях
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView firstname,name,lastname,year,gender;

        ImageView client_photo;


        ImageButton select_user;

        int position;


        List<com.example.bestdiet.database.client_message> list_message = new ArrayList<>();
        client_message client_message;

        private TelegramBotClient telegramBotClient;

        TextView senderName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            name = itemView.findViewById(R.id.name);
            lastname = itemView.findViewById(R.id.lastname);
            year = itemView.findViewById(R.id.year);
            gender = itemView.findViewById(R.id.gender);
            select_user = itemView.findViewById(R.id.imageButton7);
            client_photo = itemView.findViewById(R.id.client_photo);
            if(img_url != null) {
                Log.e("Img_url ", img_url);
                Glide.with(itemView.getContext())
                        .load(img_url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0)))
                        .into(client_photo);
            }
            else Log.e("Img_url ", "Img_url null");

            select_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    client_in_client_list client = clients.get(position);
                    int clientId = client.getClient_id();
                    Intent intent = new Intent(v.getContext(), client_card_activity.class);
                    intent.putExtra("CLIENT_ID", String.valueOf(clientId));
                    v.getContext().startActivity(intent);
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
