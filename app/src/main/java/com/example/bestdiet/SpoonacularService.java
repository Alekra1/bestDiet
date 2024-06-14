package com.example.bestdiet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularService {
    @GET("food/ingredients/{id}/information?amount=1")
    Call<ResponseData_Spoonacular> getIngredientInfo(@Path("id") String id);

    @GET("recipes/complexSearch")
    Call<ResponseData_Spoonacular> searchRecipes(@Query("query") String query,
                                       @Query("apiKey") String apiKey);
}

