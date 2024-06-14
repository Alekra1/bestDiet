package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "productinfoodmeal",
        primaryKeys = {"meal_id", "productname"},
        foreignKeys = {
                @ForeignKey(entity = meal.class,
                        parentColumns = "meal_id",
                        childColumns = "meal_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = product.class,
                        parentColumns = "productname",
                        childColumns = "productname",
                        onDelete = ForeignKey.CASCADE),
        })
public class productinfoodmeal {
    @NonNull
    int meal_id;
    @NonNull

    String productname;
    @NonNull
    @ColumnInfo(name = "amount")
    float amount;

    public productinfoodmeal(int meal_id,String productname,float amount)
    {
        this.meal_id = meal_id;
        this.productname = productname;
        this.amount = amount;

    }

    public String getProductname()
    {
        return this.productname;
    }

    public float getAmount()
    {
        return this.amount;
    }
}
