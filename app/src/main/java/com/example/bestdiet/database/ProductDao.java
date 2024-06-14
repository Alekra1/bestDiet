package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.bestdiet.ProductWithAmount;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insertproduct(product product);

    @Query("SELECT * FROM product")
    List<product> getAllproducts();

    @Query("SELECT * FROM product Where productname = :prod_name")
    product getproductbyname(String prod_name);

    @Query("SELECT p.productname, p.fats, p.kkal, p.uglivod, p.protein, p.photo_product, pim.amount " +
            "FROM product p " +
            "JOIN productinfoodmeal pim ON p.productname = pim.productname " +
            "JOIN meal m ON pim.meal_id = m.meal_id " +
            "WHERE m.clientid = :client_id AND m.meal_name = :meal_namepar")
    List<ProductWithAmount> getProductByClientId(String client_id, String meal_namepar);



}
