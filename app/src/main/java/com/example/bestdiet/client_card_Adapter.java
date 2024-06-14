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
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.clients;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class client_card_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> items;
    private String clientId;

    private static TelegramBotClient telegramBotClient;

    public client_card_Adapter(List<Item> items,String clientId) {
        this.items = items;
        this.clientId = clientId;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 0){
            return new client_card_Adapter.headerViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.header_client_card,
                    parent,
                    false)
            );
        }
        else if(viewType == 1){
            return new client_card_Adapter.bodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.redact_data_client,
                    parent,
                    false)
            );
        }
        else if(viewType == 2){
            return new client_card_Adapter.endViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.end_client_card,
                    parent,
                    false)
            );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == 0)
        {
            header_client_card header = (header_client_card) items.get(position).geObject();
            ((client_card_Adapter.headerViewHolder) holder).setheaderdate(header);
        }
        else if(getItemViewType(position) == 1)
        {
            body_client_card body = (body_client_card) items.get(position).geObject();
            ((client_card_Adapter.bodyViewHolder) holder).setbodydate(body);
        }
        else if(getItemViewType(position) == 2)
        {
            end_client_card end = (end_client_card) items.get(position).geObject();
            ((client_card_Adapter.endViewHolder) holder).setenddate(end);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    static class headerViewHolder extends RecyclerView.ViewHolder {
        private ImageView client_photo;
        private TextView firstname, name,lastname;

        public headerViewHolder(@NonNull View itemView) {
            super(itemView);
            client_photo = itemView.findViewById(R.id.client_photo);
            firstname = itemView.findViewById(R.id.textView_firstname);
            name = itemView.findViewById(R.id.textView_name);
            lastname = itemView.findViewById(R.id.textView_lastname);

        }

        void setheaderdate(header_client_card header)
        {

            if (firstname != null) {
                firstname.setText(header.getfirstname());
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if (name != null) {
                name.setText(String.valueOf(header.getname()));
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if (lastname != null) {
                lastname.setText(String.valueOf(header.getlastname()));
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            Glide.with(client_photo.getContext())
                    .load(header.getImagepath())
                    .into(client_photo);
        }
    }

    static class bodyViewHolder extends RecyclerView.ViewHolder {

        private static final String CHAT_ID = "751112696";

        AppDatabase database;

        ClientDao clientDao;

        clients client = new clients();

        private ExecutorService executor = Executors.newSingleThreadExecutor();

        //private ImageView photo;
        private TextInputEditText firstname, name,lastname,year,gender,weight,resweight,mealcount,activitydays,limitation,recomendation;
        private ImageButton redact_plan_bottom,redact_data_bottom,send_plan;

        public bodyViewHolder(@NonNull View itemView) {
            super(itemView);
            database = AppDatabase.getAppDatabase(itemView.getContext());
            clientDao = database.clientDao();
            redact_plan_bottom = itemView.findViewById(R.id.redact_plan_button);
            redact_data_bottom = itemView.findViewById(R.id.redact_info_client);
            firstname = itemView.findViewById(R.id.firstname_input);
            name = itemView.findViewById(R.id.name_input);
            lastname = itemView.findViewById(R.id.lastname_input);
            year = itemView.findViewById(R.id.year_input);
            gender = itemView.findViewById(R.id.gender_input);
            weight = itemView.findViewById(R.id.weight_input);
            resweight = itemView.findViewById(R.id.resultweight_input);
            mealcount = itemView.findViewById(R.id.count_of_meal_input);
            activitydays = itemView.findViewById(R.id.activity_days_input);
            limitation = itemView.findViewById(R.id.recomendation_input);

            redact_plan_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Редактирование прийома", "Редактирование прийома");
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            clients clients = clientDao.getclientbypib(String.valueOf(firstname.getText()),String.valueOf(name.getText()),String.valueOf(lastname.getText()));
                            if(clients != null) {
                                Intent intent = new Intent(v.getContext(), redact_of_plan_activity.class);
                                intent.putExtra("CLIENT_ID", String.valueOf(clients.getUid()));
                                Log.e("getUid", String.valueOf(clients.getUid()));
                                v.getContext().startActivity(intent);
                            }
                            else Log.e("Редактирование прийома", "client is null");
                        }
                    });
                }
            });

            redact_data_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Редактирование даних увімкнено", "Редактирование даних увімкнено");
                    firstname.setEnabled(true);
                    name.setEnabled(true);
                    lastname.setEnabled(true);
                    year.setEnabled(true);
                    gender.setEnabled(true);
                    weight.setEnabled(true);
                    resweight.setEnabled(true);
                    mealcount.setEnabled(true);
                    activitydays.setEnabled(true);
                }
            });

//            send_plan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    telegramBotClient = new TelegramBotClient();
//                    //telegramBotClient.getUpdates();
//                    telegramBotClient.sendMessage(CHAT_ID,"Ваш план харчування:\n Сніданок: \n" +
//                            "Назва: Apple Pie Калорійність: 250.0g,Білки: 10.0g,Жири: 30.0g, Вугливоди: 2.0g \n"+
//                            "Назва: Caesar Salad, Калорійність: 180.0g, Білки: 15.0g,Жири: 10.g,Вугливоди: 5.0g\n" +
//                            "Назва: Grilled Chicken,Калорійність: 300.0 kkal, Білки: 20.0g, Жири: 0.0f,Вугливоди: 30.0g\n");
//                }
//            });

        }

        void setbodydate(body_client_card body) {
            TextInputEditText[] textedits = {firstname, name, lastname, year, gender, weight, resweight, mealcount, activitydays};
            int[] ids = {R.id.firstname_input, R.id.name_input, R.id.lastname_input, R.id.year_input, R.id.gender_input, R.id.weight_input, R.id.email_input_reg, R.id.weight_input, R.id.resultweight_input, R.id.activity_days_input, R.id.recomendation_input};

            for (int i = 0; i < textedits.length; i++) {
                if (textedits[i] != null) {
                    textedits[i].setText(getTextValue(body, i));
                    textedits[i].setEnabled(false);
                } else {
                    Log.e("meal_of_plan_Adapter", "TextView is not initialized");
                }
            }
        }

        private String getTextValue(body_client_card body, int index) {
            switch (index) {
                case 0:
                    return body.client.getFirstName();
                case 1:
                    return body.client.getMiddleName();
                case 2:
                    return body.client.getLastName();
                case 3:
                    return String.valueOf(body.client.getYear());
                case 4:
                    if(body.client.getGender().equals("ч"))
                        return "Чоловік";
                    else return "Жінка";
                case 5:
                    return String.valueOf(body.client.getWeight()) + " кг";
                case 6:
                    return String.valueOf(body.client.getResultWeight()) + " кг";
                case 7:
                    return String.valueOf(body.client.getMealCount());
                case 8:
                    return String.valueOf(body.client.getActivityDays());
                case 9:
                    return String.valueOf(body.client.getLimitation());
                default:
                    return "";
            }
        }
    }
    static class endViewHolder extends RecyclerView.ViewHolder {
        private TextInputEditText recomendation;

        public endViewHolder(@NonNull View itemView) {
            super(itemView);
            recomendation = itemView.findViewById(R.id.recomendation_input);
        }

        void setenddate(end_client_card end)
        {
            if (recomendation != null) {
                recomendation.setText(end.recomendation);
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
        }
    }
}
