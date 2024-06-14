package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface user_messageDao {
    @Insert
    void insert(user_message message);

    @Query("SELECT * FROM user_message")
    List<user_message> getAllclientmessages();

    @Query("SELECT * FROM user_message WHERE message_id = :mes_id")
    user_message getAllclientmessagesByid(int mes_id);

    @Update
    void updateUser(user_message message);

    @Query("DELETE FROM user_message")
    void deleteAllclient_message();
}

