package com.example.bestdiet;

import android.os.Message;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TelegramService {
    @POST("sendMessage")
    Call<ResponseBody> sendMessage(@Header("Authorization") String botToken, @Body SendMessageBody body);

    @Multipart
    @POST("sendDocument")
    Call<ResponseBody> sendDocument(
            @Header("Authorization") String botToken,
            @Part("chat_id") RequestBody chatId,
            @Part MultipartBody.Part document
    );

    @GET("/messages")
    Call<List<Message>> getMessages(@Header("Authorization") String botToken, @Body SendMessageBody body);
    @GET("getUpdates")
    Call<TelegramResponse> getUpdates(@Query("offset") Integer offset);
}



