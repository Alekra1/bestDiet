package com.example.bestdiet;

import androidx.room.*;

@Database(entities = {user.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}


