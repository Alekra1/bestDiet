package com.example.bestdiet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class authenticateSendPulse {

    private Context context;

    static public void saveAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCESS_TOKEN", accessToken);
        editor.apply();
    }

    static public String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ACCESS_TOKEN", null);
    }
    public authenticateSendPulse(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        this.context = context; // Ensure this context is not null before assignment
        initializeServiceAndMakeRequest();
    }

    private void initializeServiceAndMakeRequest() {
        SandPulseService service = RetrofitClient_sendpulse.getService(context); // Pass context here if necessary
        // Ensure service is not null and then proceed to make the call
        if (service != null) {
            makeRequest(service);
        }
    }

    private void makeRequest(SandPulseService service) {
        Call<AccessTokenResponse> call = service.getAccessToken("client_credentials", "171c854a291b81c0217b4693923483fb", "8abf9e2fa89efa3bd9ab36a5fd1126df");
        call.enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String accessToken = response.body().getAccessToken();
                    saveAccessToken(context, accessToken);
                    Log.e("successful", "Успішна реєстрація");
                } else {
                    Log.e("error", "Error or null body received");
                }
            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                Log.e("error", "Failure: " + t.getMessage());
            }
        });
    }
}