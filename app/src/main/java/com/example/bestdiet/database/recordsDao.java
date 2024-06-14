package com.example.bestdiet.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface recordsDao {
        @Insert
        void insert(records records);

        @Query("SELECT * FROM records WHERE userid = :useridpar")
        records getAllrecordsdoctor(int useridpar);

        @Query("SELECT r.* " +
                "FROM records r " +
                "JOIN users u ON r.userid = u.uid " +
                "JOIN clients c ON r.cid = c.clientid " +
                "WHERE u.email = :email AND c.first_name = :firstname LIMIT 1")
        records getRecordsByUserEmailAndClientSurname(String email, String firstname);


//        @Update
//        void updateUser(client_data client_data);

        @Query("DELETE FROM records")
        void deleteAllClients();
}