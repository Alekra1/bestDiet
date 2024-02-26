package com.example.bestdiet;

import androidx.room.*;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(user user);

    @Query("SELECT * FROM users")
    List<user> getAllUsers();
}
