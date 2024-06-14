package com.example.bestdiet.database;

import android.content.Context;

import androidx.room.*;


@Database(entities = {user.class, meal.class, clients.class, records.class, client_message.class,user_message.class,dishinfomeal.class, productinfoodmeal.class, product.class, dish.class, product_dish.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "bestdiet";

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract recordsDao recordsDao();

    public abstract Client_messageDao clientMessageDao();

    public abstract user_messageDao userMessageDao();

    public abstract MealDao mealDao();

    public abstract DishDao dishDao();
    public abstract ProductDao productDao();

    public abstract product_info_meal_Dao product_info_meal_Dao();



    public abstract ClientDao clientDao();
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


