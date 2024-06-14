package com.example.bestdiet;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.UserDao;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.records;
import com.example.bestdiet.database.recordsDao;
import com.example.bestdiet.database.user;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reloadData {
    private final UserDao userDao;
    private final ClientDao clientDao;
    private final recordsDao recordsdao;
    private final Context context;
    private final AppDatabase database;
    private final SandPulseService sandPulseService;
    private final List<ClientUser> clientUserList = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<String> already_clear = new ArrayList<>();

    private static final String BASE_URL = "http://api.sandpulse.com";

    public reloadData(Context context) {
        this.context = context;
        this.database = AppDatabase.getAppDatabase(context);
        this.userDao = database.userDao();
        this.clientDao = database.clientDao();
        this.recordsdao = database.recordsDao();
        this.sandPulseService = RetrofitClient_sendpulse.getService(context);

        if (authenticateSendPulse(context)) {
            schedulePeriodicTask();
        }
    }

    private boolean authenticateSendPulse(Context context) {
        new authenticateSendPulse(context);
        return authenticateSendPulse.getAccessToken(context) != null;
    }

    private void schedulePeriodicTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable periodicTask = this::runPeriodicTask;
        scheduler.scheduleAtFixedRate(periodicTask, 0, 10, TimeUnit.SECONDS);
    }

    private void runPeriodicTask() {
        searchUserInDB("Status", "660bc3894dcd6ca38a0eb90a", "Wait");
        if(!clientUserList.isEmpty()) {
            fetchContactInfo("Status", "660bc3894dcd6ca38a0eb90a", "Знайдено");
        }
        else Log.e("Клієнта нет", "Клієнта нет");

    }

    private void setVariableValue(String clientId, String variableName, String variableValue) {
        sandPulseService.setVariable(clientId, variableName, variableValue).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("Values", response.message());
                } else {
                    Log.e("API Error", "Failed to set variable: " + response.message());
                }
            }


            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e("API Error", "Failed to set variable: ", t);
            }
        });
    }

    private void searchUserInDB(String variableName, String botId, String variableValue) {
        sandPulseService.getContact(variableName, botId, variableValue).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processSearchResponse(response.body(), variableValue);
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e("API Error", "Failed to fetch contact info: ", t);
            }
        });
    }

    private void processSearchResponse(ResponseData responseData, String variableValue) {
        for (Bot bot : responseData.getData()) {
            if (bot.getstatus().equals(variableValue) && bot.getPib() != null && !bot.getPib().equals("0")) {
                String[] pibSplit = bot.getPib().split(" ");
                if (pibSplit.length == 3) {
                    addClientUser(bot, pibSplit);
                } else {
                    Log.e("Split Error", "PIB does not contain expected values");
                }
            } else {
                Log.e("PІБ", "Pib is null or status does not match");
            }
        }
        if(!clientUserList.isEmpty())
            executor.execute(() -> updateUsers(variableValue));
    }

    private void addClientUser(Bot bot, String[] pibSplit) {
        boolean isClient = clientUserList.stream().anyMatch(clientUser ->
                clientUser.doctorPib.equals(String.join(" ", pibSplit)) && clientUser.idClient.equals(bot.getclient_id()));
        if (!isClient) {
            ClientUser clientUser = new ClientUser(String.join(" ", pibSplit), bot.getclient_id(), null);
            clientUserList.add(clientUser);
            Log.e("ClientUserList", "Added record to list: " + clientUserList.size());
        }
    }

    private void updateUsers(String variableValue) {
        List<ClientUser> clientUserList_work = new ArrayList<>(clientUserList);
        for (ClientUser clientUser : clientUserList_work) {
            String varbiable[] = {"PIB","Date","Gender","height","weight","need_weight_result","activity_of_week","limitation","count_of_meal"};
            String[] pibSplit = clientUser.doctorPib.split(" ");
            if (pibSplit.length == 3) {
                user user = userDao.getusersbyPib(pibSplit[0], pibSplit[1], pibSplit[2]);
                if (user != null) {
                    setVariableValue(clientUser.idClient, "Status", "Знайдено");
                    if(already_clear.indexOf(clientUser.idClient) == -1)
                    {
                        for(String var : varbiable) {
                            setVariableValue(clientUser.idClient, var, "0");
                        }
                        already_clear.add(clientUser.idClient);
                    }
                    Log.e("Status", clientUser.idClient + " Status changed to Знайдено");
                }
                else {
                    clientUserList.remove(clientUserList.indexOf(clientUser));
                }
            } else {
                Log.e("PIB Split Error", "PIB does not contain expected values");
            }
        }
    }

    private void fetchContactInfo(String variableName, String botId, String variableValue) {
        sandPulseService.getContact(variableName, botId, variableValue).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processContactResponse(response.body(), variableValue);
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.e("API Error", "Failed to fetch contact info: ", t);
            }
        });
    }

    private void processContactResponse(ResponseData responseData, String variableValue) {
        for (Bot bot : responseData.getData()) {
            if (bot.getstatus().equals(variableValue)) {
                processBotData(bot);
            } else {
                Log.e("Status", "Status does not match");
            }
        }

        executor.execute(this::updateClientsAndRecords);
    }

    private void processBotData(Bot bot) {
        if (bot.getPib() == null || bot.getPib().equals("0")) {
            Log.e("PIB", "Pib is null or invalid");
            return;
        }

        String[] pibSplit = bot.getPib().split(" ");
        if (pibSplit.length < 3) {
            Log.e("Split Error", "PIB does not contain expected values");
            return;
        }
        boolean client_not_empty = false;
        clients client = new clients();


        // Check and set first name
        if (pibSplit[0] != null && !pibSplit[0].isEmpty()) {
            client.setFirstName(pibSplit[0]);
            client_not_empty = true;
            Log.e("setFirstName", pibSplit[0]);
        }
        else client_not_empty = false;

// Check and set middle name
        if (pibSplit[1] != null && !pibSplit[1].isEmpty()) {
            client.setMiddleName(pibSplit[1]);
            client_not_empty = true;
        }
        else client_not_empty = false;


// Check and set last name
        if (pibSplit[2] != null && !pibSplit[2].isEmpty()) {
            client.setLastName(pibSplit[2]);
            client_not_empty = true;
        }
        else client_not_empty = false;


// Check and set chat ID
        String chatId = String.valueOf(bot.getchat_id_tg());
        if (chatId != null && !chatId.isEmpty() || !chatId.equals("0")) {
            client.setChat_id(chatId);
            client_not_empty = true;
        }
        else client_not_empty = false;


// Check and set height
        String heightStr = bot.getheight();
        if (heightStr != null && !heightStr.isEmpty() || !heightStr.equals("0")) {
            float height = parseFloat(heightStr, "Height");
            if (height > 0) {
                client.setHeight(height);
                client_not_empty = true;
            }
        }
        else client_not_empty = false;

// Check and set year
        String dateStr = bot.getDate();
        if (dateStr != null && !dateStr.isEmpty()) {
            int year = parseYear(dateStr);
            if (year > 1900 && year < 2019) {
                client.setYear(year);
                client_not_empty = true;
            }
            else client_not_empty = false;
        }
        else client_not_empty = false;


// Check and set gender
        String gender = bot.getGender();
        if (gender != null && !gender.isEmpty() || !gender.equals("0")) {
            client.setGender(gender);
            client_not_empty = true;
        }
        else client_not_empty = false;


// Check and set weight
        String weightStr = bot.getweight();
        if (weightStr == null && weightStr.isEmpty() || !weightStr.equals("0")) {
            float weight = parseFloat(weightStr, "Weight");
            if (weight > 0) {
                client.setWeight(weight);
                client_not_empty = true;
            }
            else client_not_empty = false;
        }
        else client_not_empty = false;

// Check and set result weight
        String resultWeightStr = bot.getneedweight();
        if (resultWeightStr != null && !resultWeightStr.isEmpty() || !resultWeightStr.equals("0")) {
            float resultWeight = parseFloat(resultWeightStr, "Need Weight");
            if (resultWeight > 0) {
                client.setResultWeight(resultWeight);
                client_not_empty = true;
            }
        }
        else client_not_empty = false;

// Check and set activity days
        String activityDaysStr = bot.getactivity_days();
        if (activityDaysStr != null && !activityDaysStr.isEmpty() || !activityDaysStr.equals("0")) {
            int activityDays = parseActivityDays(activityDaysStr);
            if (activityDays > 0) {
                client.setActivityDays(activityDays);
                client_not_empty = true;
            }
        }
        else client_not_empty = false;


// Check and set limitation
        String limitation = bot.getlimitation();
        if (limitation != null && !limitation.isEmpty() || !limitation.equals("0")) {
            client.setLimitation(limitation);
            client_not_empty = true;
        }
        else client_not_empty = false;

        String photo_url = bot.getphoto_url();
        if (photo_url != null && !photo_url.isEmpty() && !photo_url.equals("0")) {
                client.setphoto_url(photo_url);
                client_not_empty = true;
        }
        else client_not_empty = false;


// Check and set meal count
        String mealCountStr = bot.getcountofmeal();
        if (mealCountStr != null && !mealCountStr.isEmpty() || !mealCountStr.equals("0")) {
            int mealCount = parseInt(mealCountStr, "Count Of Meal");
            if (mealCount > 0) {
                client.setMealCount(mealCount);
                client_not_empty = true;
            }
            else client_not_empty = false;
        }
        else client_not_empty = false;

        int clientIndex = getClientIndex(bot.getclient_id());
        if (clientIndex != -1 && client_not_empty) {
            ClientUser clientUser = clientUserList.get(clientIndex);
            clientUser.setClientData(client);
            clientUserList.set(clientIndex, clientUser);
        } else {
            Log.e("Client List", "Client list is empty or ");
        }
    }

    private float parseFloat(String value, String field) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            Log.e(field, field + " is null or invalid");
            return 0;
        }
    }

    private int parseYear(String date) {
        if (date != null) {
            String[] parts = date.split("-");
            if (parts.length > 0) {
                return Integer.parseInt(parts[0]);
            } else {
                Log.e("Date Split", "Date format is incorrect");
            }
        }
        Log.e("Date", "Date is null");
        return 0;
    }

    private int parseActivityDays(String activityDays) {
        if (activityDays != null) {
            return Integer.parseInt(String.valueOf(activityDays.charAt(0)));
        }
        Log.e("Activity Days", "Activity days is null");
        return 0;
    }

    private int parseInt(String value, String field) {
        try {
            return (int)Float.parseFloat(value);
        } catch (NumberFormatException e) {
            Log.e(field, field + " is null or invalid");
            return 0;
        }
    }

    private int getClientIndex(String clientId) {
        for (int i = 0; i < clientUserList.size(); i++) {
            if (clientUserList.get(i).idClient.equals(clientId)) {
                return i;
            }
        }
        return -1;
    }

    private void updateClientsAndRecords() {
        List<ClientUser> clientUserList_work = new ArrayList<>(clientUserList);
        for (ClientUser clientUser : clientUserList_work) {
            if (clientUser != null) {
                String[] pib = clientUser.doctorPib.split(" ");
                if (pib.length == 3) {
                    user user = userDao.getusersbyPib(pib[0], pib[1], pib[2]);
                    if (user != null) {
                        if(clientUser.getClientData() != null) {
                            clientDao.insert(clientUser.getClientData());
                            Log.e("New Client", "New client added: " + clientUser.getClientData().getFirstName());

                            if (clientUser.getClientData() != null) {
                                records record = new records(user.getUid(), clientDao.getclientbypib(
                                        clientUser.getClientData().getFirstName(),
                                        clientUser.getClientData().getMiddleName(),
                                        clientUser.getClientData().getLastName()).getUid());
                                recordsdao.insert(record);
                                Log.e("New Record", "New record added: " + record.getrecord_id());
                            } else {
                                Log.e("New Record", "Client data is null, record not added");
                            }

                            setVariableValue(clientUser.getIdClient(), "Status", "Успішна реєстрація");
                            Log.e("Status", clientUser.getClientData().getChat_id() + clientUser.getIdClient() + " Status changed to Успішна реєстрація");
                            clientUserList.remove(clientUserList_work.indexOf(clientUser));
                        }
                        else Log.e("Клієнт є", "Клієнт є але він не заповнений");

                    } else {
                        Log.e("New Client", "User is null, client not added");
                    }
                } else {
                    Log.e("PIB Split Error", "PIB does not contain expected values");
                }
            } else {
                Log.e("Client User", "Client user is null");
            }
        }
    }

    private void handleErrorResponse(Response<ResponseData> response) {
        try {
            String errorBody = response.errorBody().string();
            Log.e("API Error", "Error Response: " + errorBody);
            JSONObject jsonObject = new JSONObject(errorBody);
            if (jsonObject.has("message") && jsonObject.has("errors")) {
                String message = jsonObject.getString("message");
                JSONObject errors = jsonObject.getJSONObject("errors");
                Log.e("API Validation Error", message + ": " + errors.toString());
            }
        } catch (IOException | JSONException e) {
            Log.e("API Error", "Failed to parse error response", e);
        }
    }
    private static class ClientUser {
        String doctorPib;
        String idClient;
        clients clientData;

        ClientUser(String doctorPib, String idClient, clients clientData) {
            this.doctorPib = doctorPib;
            this.idClient = idClient;
            this.clientData = clientData;
        }

        public String getDoctorPib() {
            return doctorPib;
        }

        public String getIdClient() {
            return idClient;
        }

        public clients getClientData() {
            return clientData;
        }

        public void setClientData(clients clientData) {
            this.clientData = clientData;
        }
    }
}

