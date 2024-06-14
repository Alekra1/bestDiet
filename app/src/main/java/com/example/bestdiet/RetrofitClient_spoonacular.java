package com.example.bestdiet;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient_spoonacular {
    private static final String BASE_URL = "https://api.spoonacular.com/";
    private static Retrofit retrofit = null;

    public static SpoonacularService getService(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(provideClient("101fd9f2db624f8ca0d0c9561a343685"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SpoonacularService.class);
    }

    public static OkHttpClient provideClient(final String apiKey) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("x-api-key", apiKey)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        httpClient.addInterceptor(new AuthInterceptor());
        return httpClient.build();
    }

    public static class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            // Проверяем успешность авторизации (статус код 200)
            if (response.code() == 200) {
                // Успешная авторизация
                Log.d("Auth", "Authorization successful");
            } else {
                // Ошибка авторизации
                Log.e("Auth", "Authorization failed");
            }

            return response;
        }
    }
}
