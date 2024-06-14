package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
@Entity(tableName = "product_dish",
        primaryKeys = {"productname", "dishname"},
        foreignKeys = {
        @ForeignKey(entity = product.class,
                parentColumns = "productname",
                childColumns = "productname",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = dish.class,
                parentColumns = "dishname",
                childColumns = "dishname",
                onDelete = ForeignKey.CASCADE)
        })
public class product_dish {
    @NonNull

    String productname;
    @NonNull

    String dishname;

    float amount;

}
