package com.example.bestdiet;

import android.app.Activity;
import android.content.Context;

import androidx.room.*;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {user.class, clients.class, records.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "bestdiet";

    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract ClientDao clientDao();
    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
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


