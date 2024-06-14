package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal",
        foreignKeys = {
                @ForeignKey(entity = clients.class,
                        parentColumns = "clientid",
                        childColumns = "clientid",
                        onDelete = ForeignKey.CASCADE),
        })
public class meal {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int meal_id;
    @NonNull
    @ColumnInfo(name = "meal_name")
    public String meal_name;
    @NonNull
    @ColumnInfo(name = "clientid")
    public int clientid;
    // Пустой конструктор
    public meal() {
    }

    // Конструктор с параметрами, совпадающими с именами полей
    public meal(String meal_name, int clientid) {
        this.meal_name = meal_name;
        this.clientid = clientid;
    }

}
