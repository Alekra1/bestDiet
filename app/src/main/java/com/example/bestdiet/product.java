package com.example.bestdiet;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class product {
    @PrimaryKey
    private String productname;

    @ColumnInfo(name = "kkal")
    private float kkal;

    @ColumnInfo(name = "protein")
    private float protein;

    @ColumnInfo(name = "fats")
    private float fats;

    @ColumnInfo(name = "uglivod")
    private float uglivod;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public float getKkal() {
        return kkal;
    }

    public void setKkal(float kkal) {
        this.kkal = kkal;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
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

}

