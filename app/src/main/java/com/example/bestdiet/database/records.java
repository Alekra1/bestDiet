package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.user;

@Entity(tableName = "records",
        foreignKeys = {
                @ForeignKey(entity = user.class,
                        parentColumns = "uid",
                        childColumns = "userid",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = clients.class,
                        parentColumns = "clientid",
                        childColumns = "cid",
                        onDelete = ForeignKey.CASCADE)
        })
public class records {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int record_id;
    @NonNull
    @ColumnInfo(name = "userid")
    public int userid;
    @NonNull
    @ColumnInfo(name = "cid")
    public int cid;

    public records(int userid,int cid)
    {
        this.userid = userid;
        this.cid = cid;
    }
    public int getrecord_id()
    {
        return record_id;
    }

}



