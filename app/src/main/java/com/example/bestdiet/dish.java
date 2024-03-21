package com.example.bestdiet;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "dish")

public class dish {

        @PrimaryKey
        private String dishname;

        @ColumnInfo(name = "kkal")
        private float kkal;
        @ColumnInfo(name = "fats")
        private float fats;

        @ColumnInfo(name = "uglivod")
        private float uglivod;

        @ColumnInfo(name = "protein")
        private float protein;

        public String getDishname() {
                return dishname;
        }

        public void setDishname(String dishname) {
                this.dishname = dishname;
        }

        public float getKkal() {
                return kkal;
        }

        public void setKkal(float kkal) {
                this.kkal = kkal;
        }

        public float getFats() {
                return fats;
        }

        public void setFats(float fats) {
                this.fats = fats;
        }

        public float getUglivod() {
                return uglivod;
        }

        public void setUglivod(float uglivod) {
                this.uglivod = uglivod;
        }

        public float getProtein() {
                return protein;
        }

        public void setProtein(float protein) {
                this.protein = protein;
        }

}
