package com.example.bestdiet;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "records",
        primaryKeys = {"userid", "clientid"},
        foreignKeys = {
                @ForeignKey(entity = user.class,
                        parentColumns = "uid",
                        childColumns = "userid",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = clients.class,
                        parentColumns = "clientid",
                        childColumns = "clientid",
                        onDelete = ForeignKey.CASCADE)
        })
public class records {

    public int userid;
    public int clientid;
}



