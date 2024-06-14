package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

import androidx.room.Query;

@Dao
public interface DishDao {

    @Insert
    void insertDish(dish dish);

    @Query("SELECT * FROM dish")
    List<dish> getAllDishes();
}