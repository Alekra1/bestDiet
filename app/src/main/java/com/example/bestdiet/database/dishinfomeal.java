package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "dishinfomeal",
        primaryKeys = {"meal_id", "dishname"},
        foreignKeys = {
                @ForeignKey(entity = meal.class,
                        parentColumns = "meal_id",
                        childColumns = "meal_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = dish.class,
                        parentColumns = "dishname",
                        childColumns = "dishname",
                        onDelete = ForeignKey.CASCADE)
        })
public class dishinfomeal {
    @NonNull
    int meal_id;
    @NonNull
    String dishname;

    float amount;
}
