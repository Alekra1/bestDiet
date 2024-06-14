package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Client_messageDao {
    @Insert
    void insert(client_message message);

    @Query("SELECT * FROM client_message")
    List<client_message> getAllclientmessages();

    @Query("SELECT * FROM client_message WHERE message_id = :mes_id")
    client_message getAllclientmessagesByid(int mes_id);

    @Update
    void updateUser(client_message message);

    @Query("DELETE FROM client_message")
    void deleteAllclient_message();
}

