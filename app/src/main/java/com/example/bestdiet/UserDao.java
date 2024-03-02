package com.example.bestdiet;

import androidx.room.*;
import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(user user);

    @Query("SELECT * FROM users")
    List<user> getAllUsers();

    @Query("SELECT * FROM users WHERE uid = :uidpar")
    user getuserbyid(int uidpar);

    @Update
    void updateUser(user user);

    @Query("DELETE FROM users")
    void deleteAllUsers();

}
