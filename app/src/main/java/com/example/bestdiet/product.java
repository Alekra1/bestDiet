package com.example.bestdiet;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class product {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "productname")
    private String productname;

    @ColumnInfo(name = "kkal")
    private float kkal;

    @ColumnInfo(name = "protein")
    private float protein;

    @ColumnInfo(name = "fats")
    private float fats;

    @ColumnInfo(name = "uglivod")
    private float uglivod;

}

