package com.example.bestdiet.database;

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

    @Query("SELECT * FROM clients WHERE first_name = :first_namepar and middleName = :middleName and last_name = :lastnamepar")
    clients getclientbypib(String first_namepar,String middleName,String lastnamepar);

    @Query("SELECT c.* " +
            "FROM clients c " +
            "JOIN records a ON c.clientid = a.cid " +
            "JOIN users u ON a.userid = u.uid " +
            "WHERE u.email = :email " +
            "ORDER BY c.clientid DESC") // Или другой столбец для сортировки
    List<clients> getClientOfDoctor(String email);

    @Update
    void updateUser(clients client_data);

    @Query("DELETE FROM clients")
    void deleteAllClients();
}
