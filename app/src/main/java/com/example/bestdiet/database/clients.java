package com.example.bestdiet.database;

import androidx.room.*;

@Entity(tableName = "clients")
public class clients {
    @PrimaryKey(autoGenerate = true)
    public int clientid;

    @ColumnInfo(name = "chat_id_tg")
    public String chat_id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "middleName")
    public String middleName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "height")
    public float height;

    @ColumnInfo(name = "weight")
    public float weight;

    @ColumnInfo(name = "result_weight")
    public float result_weight;

    @ColumnInfo(name = "meal_count")
    public int meal_count;

    @ColumnInfo(name = "limitation")
    public String limitation;

    @ColumnInfo(name = "recomendation")
    public String recomendation;

    @ColumnInfo(name = "activity_days")
    public int activity_days;

    @ColumnInfo(name = "photo_url")
    public String photo_url;


    public clients() {
        this.firstName = " ";
        this.middleName = " ";
        this.lastName = " ";
        this.year = 2024;
        this.gender = "";
    }
    public clients(String firstName, String middleName, String lastName, int year, String gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.year = year;
        this.gender = gender;
    }

    private clients(Builder builder) {
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.year = builder.year;
        this.gender = builder.gender;
        this.weight = builder.weight;
        this.result_weight = builder.result_weight;
        this.meal_count = builder.meal_count;
        this.activity_days = builder.activity_days;
        this.photo_url = builder.photo_url;
    }

    // Геттеры и сеттеры для каждого поля
    public int getUid() {
        return clientid;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    // Getters and setters for weight
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    public float getHeight() {
        return height;
    }


    // Getters and setters for result_weight
    public float getResultWeight() {
        return result_weight;
    }

    public void setResultWeight(float result_weight) {
        this.result_weight = result_weight;
    }

    // Getters and setters for meal_count
    public int getMealCount() {
        return meal_count;
    }

    public void setMealCount(int meal_count) {
        this.meal_count = meal_count;
    }

    // Getters and setters for activity_days
    public int getActivityDays() {
        return activity_days;
    }

    public void setActivityDays(int activity_days) {
        this.activity_days = activity_days;
    }

    public String getLimitation() {
        return limitation;
    }


    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public String getphoto_url() {
        return this.photo_url;
    }

    public void setphoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


    // Статический класс Builder класса clients
    public static class Builder {
        private String firstName;
        private String middleName;
        private String lastName;
        private int year;
        private String gender;

        public float weight;

        public float result_weight;

        public int meal_count;
        public int activity_days;

        public String photo_url;


        public Builder() {
            this.firstName = " ";
            this.middleName = " ";
            this.lastName = " ";
            this.year = -1;
            this.gender = "";
            this.weight = -1;
            this.result_weight = -1;
            this.meal_count = -1;
            this.activity_days = -1;
            this.photo_url = "";
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder weight(float weight) {
            this.weight = weight;
            return this;
        }

        public Builder result_weight(float result_weight) {
            this.result_weight = result_weight;
            return this;
        }

        public Builder meal_count(int meal_count) {
            this.meal_count = meal_count;
            return this;
        }

        public Builder activity_days(int activity_days) {
            this.activity_days = activity_days;
            return this;
        }

        public Builder photo_url(String photo_url) {
            this.photo_url = photo_url;
            return this;
        }

        public clients build() {
            return new clients(this);
        }
    }
}

