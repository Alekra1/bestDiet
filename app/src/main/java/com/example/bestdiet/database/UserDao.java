package com.example.bestdiet.database;

import androidx.room.*;

import com.example.bestdiet.database.user;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(user user);

    @Query("SELECT * FROM users")
    List<user> getAllUsers();

    @Query("SELECT * FROM users WHERE uid = :uidpar")
    user getuserbyid(int uidpar);

    @Query("SELECT * FROM users ORDER BY uid DESC LIMIT 1")
    user getLastAddedUser();

    @Query("SELECT * FROM users WHERE email = :emailpar")
    user getuserbyemail(String emailpar);

    @Query("SELECT * FROM users WHERE email = :emailpar")
    boolean checkuserbyemail(String emailpar);

    @Query("SELECT * FROM users WHERE first_name = :first_namepar and middleName = :middleName and last_name = :lastnamepar")
    user getusersbyPib(String first_namepar,String middleName,String lastnamepar);

    @Update
    void updateUser(user user);

    @Query("UPDATE users SET email = :email WHERE uid = :userId")
    void updateUserById(int userId, String email);

    @Query("DELETE FROM users")
    void deleteAllUsers();

}
