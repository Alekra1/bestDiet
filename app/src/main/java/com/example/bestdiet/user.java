package com.example.bestdiet;

import androidx.room.*;

@Entity(tableName = "users")
public class user {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName= "";

    @ColumnInfo(name = "middleName")
    public String middleName= "";

    @ColumnInfo(name = "last_name")
    public String lastName= "";

    @ColumnInfo(name = "email")
    public String email= "";

    @ColumnInfo(name = "phone")
    public String phone = "";

    @ColumnInfo(name = "password")
    public String password = "";

    public int getUid() {
        return uid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String firstName, String middleName, String lastName, String email, String phone, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
