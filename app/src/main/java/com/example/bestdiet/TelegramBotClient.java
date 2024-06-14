package com.example.bestdiet;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TelegramBotClient {

    private static final String token = "7144334653:AAEAStoToGf7_P78Ep7FWLAzHWQDYW4sOOc";

    private static final String BASE_URL = "https://api.telegram.org/bot";
    private TelegramService telegramService;

    List<String> messageList = new ArrayList<>();
    List<String> listidcallsearch = new ArrayList<>();

    private int lastUpdateId = 0;

    public TelegramBotClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + token + "/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        telegramService = retrofit.create(TelegramService.class);
    }

    public void sendPdfDocument(String chatId, File pdfFile) {

        // Создаем объект запроса на отправку документа
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/pdf"), pdfFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("document", pdfFile.getName(), requestBody);
        RequestBody chatIdPart = RequestBody.create(MediaType.parse("text/plain"), chatId);

        // Выполняем запрос на отправку документа
        telegramService.sendDocument(token, chatIdPart, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.i("TelegramAPI", "Document sent successfully: " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TelegramAPI", "Failed to send document: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TelegramAPI", "Error sending document", t);
            }
        });
    }


    public void sendMessage(String chatId, String text) {
        SendMessageBody messageRequest = new SendMessageBody(chatId, text);
        telegramService.sendMessage(token, messageRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.i("TelegramAPI", "Message sent successfully: " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("TelegramAPI", "Failed to send message: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TelegramAPI", "Error sending message", t);
            }
        });
    }

    public List<String> callSearch() {
        telegramService.getUpdates(lastUpdateId).enqueue(new Callback<TelegramResponse>() {
            @Override
            public void onResponse(Call<TelegramResponse> call, Response<TelegramResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Update update : response.body().getResult()) {
                        if (update.getMessage().getText().equals("Пошук")) {
                            Log.e("ID call search", String.valueOf(update.getMessage().getChat().getId()));
                            listidcallsearch.add(String.valueOf(update.getMessage().getChat().getId()));
                            lastUpdateId = update.getUpdateId() + 1;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TelegramResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("Eror", "Eror");
            }
        });
        return messageList;
    }

    public List<String> getUpdates(final UpdatesCallback callback) {
        telegramService.getUpdates(lastUpdateId).enqueue(new Callback<TelegramResponse>() {
            @Override
            public void onResponse(Call<TelegramResponse> call, Response<TelegramResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Message> messageList = new ArrayList<>();
                    for (Update update : response.body().getResult()) {
                        messageList.add(update.getMessage());
                        lastUpdateId = update.getUpdateId() + 1;
                    }
                    callback.onUpdatesReceived(messageList);

                } else {
                    callback.onError(new Exception("Response not successful or body is null"));
                }
            }

            @Override
            public void onFailure(Call<TelegramResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("Eror", "Eror");
            }
        });
        return messageList;
    }

    public List<Message> getUpdatesByUserId(final UpdatesCallback callback, int userId) {
        telegramService.getUpdates(lastUpdateId).enqueue(new Callback<TelegramResponse>() {
            @Override
            public void onResponse(Call<TelegramResponse> call, Response<TelegramResponse> response) {
                List<Message> filteredMessages = new ArrayList<>();
                if (response.isSuccessful() && response.body() != null) {
                    for (Update update : response.body().getResult()) {
                        Message message = update.getMessage();
                        if (message != null && message.getFrom() != null && message.getFrom().getId() == userId) {
                            filteredMessages.add(message);
                        }
                        lastUpdateId = update.getUpdateId() + 1;
                    }
                    callback.onUpdatesReceived(filteredMessages);
                } else {
                    callback.onError(new Exception("Response not successful or body is null"));
                }
            }

            @Override
            public void onFailure(Call<TelegramResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e("Error", "Error occurred: " + t.getMessage());
                callback.onError(t);
            }
        });
        return null;
    }
}

