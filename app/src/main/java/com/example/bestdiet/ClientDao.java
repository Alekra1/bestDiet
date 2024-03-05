package com.example.bestdiet;

import androidx.room.*;

import java.util.List;
@Dao
public interface ClientDao {
    @Insert
    void insert(client_data client_data);

    @Query("SELECT * FROM clients")
    List<client_data> getAllclient();

    @Query("SELECT * FROM clients WHERE uid = :uidpar")
    client_data getclientbyid(int uidpar);

    @Update
    void updateUser(client_data client_data);

    @Query("DELETE FROM clients")
    void deleteAllClients();
}
