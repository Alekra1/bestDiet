package com.example.bestdiet;

import android.app.Activity;

import androidx.room.*;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {user.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            if (user != null) {
//                // Получите значения из объекта User
//                int uid = user.uid + 1;
//                String firstName = user.getFirstName();
//                String middleName = user.getMiddleName();
//                String lastName = user.getLastName();
//                String email = user.getEmail();
//                String phone = user.getPhone();
//                String password = user.getPassword();
//
//                // Добавьте ваш запрос SQL для вставки данных в базу данных
//                database.execSQL("INSERT INTO users (uid,first_name, middleName, last_name, email, phone, password) " +
//                                "VALUES (?, ?, ?, ?, ?, ?,?)",
//                        new Object[]{uid,firstName, middleName, lastName, email, phone, password});
//            }
//        }
//    };
//
//    private static user user;
//
//    // Метод для установки значения user
//    public static void setUser(user userpar) {
//        user = userpar;
//    }
//
//    // Метод для передачи данных миграции
//    public void migrateUser(user userpar) {
//        user = userpar;
//        MIGRATION_1_2.migrate(getOpenHelper().getWritableDatabase());
//    }
}


