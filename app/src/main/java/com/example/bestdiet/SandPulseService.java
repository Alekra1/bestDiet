package com.example.bestdiet;

import android.os.Message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SandPulseService {
    @FormUrlEncoded
    @POST("oauth/access_token")
    Call<AccessTokenResponse> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @GET("/telegram/contacts/getByVariable")
    Call<ResponseData> getContact(@Query("variable_name") String contact_name,@Query("bot_id") String bot_id,@Query("variable_value") String variable_value);

    @POST("/telegram/contacts/setVariable")
    Call<ResponseData> setVariable(@Query("contact_id") String client_id,@Query("variable_name") String variable_name, @Query("variable_value") String variable_value);
}

