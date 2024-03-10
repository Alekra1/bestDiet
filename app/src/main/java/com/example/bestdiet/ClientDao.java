package com.example.bestdiet;

import androidx.room.*;

import java.util.List;
@Dao
public interface ClientDao {
    @Insert
    void insert(clients client_data);

    @Query("SELECT * FROM clients")
    List<clients> getAllclient();

    @Query("SELECT * FROM clients WHERE clientid = :uidpar")
    clients getclientbyid(int uidpar);

    @Update
    void updateUser(clients client_data);

    @Query("DELETE FROM clients")
    void deleteAllClients();
}
