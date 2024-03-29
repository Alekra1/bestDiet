package com.example.bestdiet;

import androidx.room.*;
@Entity(tableName = "clients")
public class clients {
    @PrimaryKey(autoGenerate = true)
    public int clientid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "middleName")
    public String middleName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "gender")
    public int gender;

    public clients() {
        this.firstName = " ";
        this.middleName = " ";
        this.lastName = " ";
        this.year = 2024;
        this.gender = 1;
    }
    // Конструктор
    public clients(String firstName, String middleName, String lastName, int year, int gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.year = year;
        this.gender = gender;
    }

    // Геттеры и сеттеры для каждого поля
    public int getUid() {
        return clientid;
    }

    public void setUid(int uid) {
        this.clientid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}

