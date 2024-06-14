package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insert(meal meal);

    @Query("SELECT * From meal where clientid = :client_id")
    List<meal> getclientmealfromclient(int client_id);

    @Query("SELECT * From meal where clientid = :client_id and meal_name = :meal_name")
    meal getidmeal(String client_id,String meal_name);

    @Update
    void updateUser(meal meal);

    @Query("DELETE FROM meal")
    void deleteclientfoodplan();
}
