package com.example.bestdiet.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.stream.Stream;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "client_message",
        foreignKeys = {
                @ForeignKey(entity = records.class,
                        parentColumns = "record_id",
                        childColumns = "record_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class client_message implements Parcelable {

    @NonNull
    @PrimaryKey
    public int message_id;
    @NonNull
    @ColumnInfo(name = "record_id")
    public int record_id;
    @NonNull
    @ColumnInfo(name = "time")
    public String date;
    @NonNull
    @ColumnInfo(name = "text")
    public String text;

    public client_message(int message_id, int record_id, String date, String text) {
        this.message_id = message_id;
        this.record_id = record_id;
        this.date = date;
        this.text = text;
    }

    protected client_message(Parcel in) {
        message_id = in.readInt();
        record_id = in.readInt();
        date = in.readString();
        text = in.readString();
    }

    public static final Creator<client_message> CREATOR = new Creator<client_message>() {
        @Override
        public client_message createFromParcel(Parcel in) {
            return new client_message(in);
        }

        @Override
        public client_message[] newArray(int size) {
            return new client_message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(message_id);
        dest.writeInt(record_id);
        dest.writeString(date);
        dest.writeString(text);
    }

    public int getRecord_id() {
        return record_id;
    }

    public String getdate() {
        return date;
    }

}

