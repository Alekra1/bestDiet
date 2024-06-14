package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity(tableName = "user_message",
        foreignKeys = {
                @ForeignKey(entity = records.class,
                        parentColumns = "record_id",
                        childColumns = "record_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class user_message {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int message_id;
    @NonNull
    @ColumnInfo(name = "record_id")
    public int record_id;
    @NonNull
    @ColumnInfo(name = "date")
    public String date;

    @NonNull
    @ColumnInfo(name = "text")
    public String text;

    public user_message(int record_id,String date,String text)
    {
        this.record_id = record_id;
        this.date = date;
        this.text = text;
    }
    public int getrecord_id()
    {
        return record_id;
    }

    public String getdate() {
        return date;
    }
}