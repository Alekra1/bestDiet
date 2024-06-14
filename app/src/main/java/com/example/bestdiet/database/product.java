package com.example.bestdiet.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "product")
public class product {
    @PrimaryKey
    @NotNull
    private String productname;

    @ColumnInfo(name = "kkal")
    private float kkal;

    @ColumnInfo(name = "protein")
    private float protein;

    @ColumnInfo(name = "fats")
    private float fats;

    @ColumnInfo(name = "uglivod")
    private float uglivod;

    @ColumnInfo(name = "photo_product")
    private String photo_product;

    public product()
    {

    }
    public product(String productname, float kkal, float fats, float uglivod, float protein,String photo_product) {
        this.productname = productname;
        this.kkal = kkal;
        this.fats = fats;
        this.uglivod = uglivod;
        this.protein = protein;
        this.photo_product = photo_product;
    }

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

    public String getPhoto_product() {
        return this.photo_product;
    }

    public void setPhoto_product(String photo_product) {
        this.photo_product = photo_product;
    }

}

