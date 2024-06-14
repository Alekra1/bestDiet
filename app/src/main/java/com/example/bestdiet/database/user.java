package com.example.bestdiet.database;

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

    public void setUid(int uid) {
        this.uid = uid;
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
    public user() {
        this.firstName = " ";
        this.middleName = " ";
        this.lastName = " ";
        this.email = " ";
        this.phone = " ";
        this.password = " ";
    }

    private user(user.Builder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
    }
    public static class Builder {
        private int uid;
        private String firstName;
        private String middleName;
        private String lastName;
        private String phone;
        private String password;

        public String email;

        public Builder() {
            this.firstName = " ";
            this.middleName = " ";
            this.lastName = " ";
            this.phone = " ";
            this.password = " ";
            this.email = "";
        }

        public user.Builder uid(int uid) {
            this.uid = uid;
            return this;
        }

        public user.Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public user.Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public user.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public user.Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public user.Builder email(String email) {
            this.email = email;
            return this;
        }

        public user.Builder password(String password) {
            this.password = password;
            return this;
        }
        public user build() {
            return new user(this);
        }
    }
}
