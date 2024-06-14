package com.example.bestdiet;

import static com.example.bestdiet.authenticateSendPulse.getAccessToken;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient_sendpulse {
    private static final String BASE_URL = "https://api.sendpulse.com/";
    private static Retrofit retrofit = null;
    public static SandPulseService getService(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideClient(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SandPulseService.class);
    }

    private static OkHttpClient provideClient(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null when creating OkHttpClient");
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + getAccessToken(context))
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();
        return client;
    }
}

