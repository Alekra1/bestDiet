package com.example.bestdiet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface recordsDao {
        @Insert
        void insert(records records);

        @Query("SELECT * FROM records WHERE userid = :useridpar")
        clients getAllrecordsdoctor(int useridpar);

//        @Update
//        void updateUser(client_data client_data);

        @Query("DELETE FROM records")
        void deleteAllClients();
}