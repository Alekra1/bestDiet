package com.example.bestdiet;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Get_SpoonacularAPI {

    private UserDao userDao;
    private ClientDao clientDao;

    Context context;

    AppDatabase database;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    List<String> listidcallsearch = new ArrayList<>();

    private static final String BASE_URL = "https://api.spoonacular.com/";

    SpoonacularService SpoonacularService;

    public Get_SpoonacularAPI(Context context){
        database = AppDatabase.getAppDatabase(context);
            getIngredientInfo("pasta","1");
    }

    private void getIngredientInfo(String name,String amount) {
        SpoonacularService = RetrofitClient_spoonacular.getService(context);
        Call<ResponseData_Spoonacular> call = SpoonacularService.searchRecipes(name, "317193d0d4eb44f39afb3546ab11e8d5");
        call.enqueue(new Callback<ResponseData_Spoonacular>() {
            @Override
            public void onResponse(Call<ResponseData_Spoonacular> call, Response<ResponseData_Spoonacular> response) {
                if (response.isSuccessful() || response.body() != null) {
                    List<Recipe> recipes = response.body().getRecipeDetails();
                    Log.e("Список рецептов пуст", response.body().toString());

                    if (recipes != null && !recipes.isEmpty()) {
                        for (Recipe recipe : recipes) {
                            Log.d(TAG, "Recipe: " + recipe.getTitle() + ", Image: " + recipe.getImage());
                        }
                    } else {
                        Log.e(TAG, "Список рецептов пуст");
                    }
                } else {
                    Log.e(TAG, "Ответ неуспешный или пустое тело: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseData_Spoonacular> call, Throwable t) {
                Log.e(TAG, "Запрос не удался: " + t.getMessage());
            }
        });
//        SpoonacularService.searchRecipes("pasta","101fd9f2db624f8ca0d0c9561a343685").enqueue(new Callback<ResponseData_Spoonacular>() {
//            @Override
//            public void onResponse(Call<ResponseData_Spoonacular> call, Response<ResponseData_Spoonacular> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    // Выводим весь ответ в лог для отладки
//                    Log.e("SpoonacularAPI answer", response.toString());
//                    Log.e("SpoonacularAPI raw body", response.raw().toString());
//
//                    if (response.body().getRecipeDetails() != null) {
//                        for (Recipe recipe : response.body().getRecipeDetails()) {
//                            if (recipe != null) {
//                                Log.e("название ингредиента", recipe.getTitle());
//                            } else {
//                                Log.e("Ошибка", "В списке ингредиентов есть нулевой объект");
//                            }
//                        }
//                    } else {
//                        Log.e("Ошибка", "Список ингредиентов пуст");
//                    }
//                } else {
//                    // Выводим сообщение об ошибке и саму ошибку
//                    Log.e("Ошибка", "Не удалось получить данные: " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData_Spoonacular> call, Throwable t) {
//                // Ошибка при выполнении запроса
//                Log.e("API Error", "Failed to fetch contact info: ", t);
//            }
//        });

    }


    private void processResponseData(ResponseData_Spoonacular data) {
        // Ваша логика обработки данных здесь
        // Например, сохранение данных в базу данных или их отображение в пользовательском интерфейсе
    }
}

