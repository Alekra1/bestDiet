package com.example.bestdiet;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "dish")

public class dish {

        @PrimaryKey(autoGenerate = true)
        private int id;
        @ColumnInfo(name = "dishname")
        private String dishname;


        @ColumnInfo(name = "fats")
        private float fats;

        @ColumnInfo(name = "uglivod")
        private float uglivod;

}
