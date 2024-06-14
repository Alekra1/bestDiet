package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface product_info_meal_Dao {

    @Insert
    long insertproduct(productinfoodmeal productinfoodmeal);

    @Query("SELECT * FROM productinfoodmeal")
    List<productinfoodmeal> getAllproductinfoodmeal();

    @Query("SELECT * FROM productinfoodmeal pim " +
            "JOIN meal m ON m.meal_id = pim.meal_id " +
            "WHERE m.clientid = :client_id AND pim.meal_id = :meal_id")
    List<productinfoodmeal> getproductbymeal_idandclient(String meal_id, String client_id);

    @Query("SELECT * FROM productinfoodmeal pim " +
            "JOIN meal m ON m.meal_name = :meal_name AND m.clientid = :client_id " +
            "WHERE m.meal_id = pim.meal_id")
    List<productinfoodmeal> getproductbymeal_nameandclient(String meal_name, String client_id);



}
