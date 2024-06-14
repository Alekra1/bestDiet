package com.example.bestdiet;

public class ProductWithAmount {
    // Поля из таблицы product
    private String productname;
    private float fats;
    private float kkal;
    private float uglivod;
    private float protein;
    private String photo_product;

    // Поле из таблицы productinfoodmeal
    private float amount;

    // Геттеры и сеттеры для всех полей

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getKkal() {
        return kkal;
    }

    public void setKkal(float kkal) {
        this.kkal = kkal;
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

    public String getPhoto_product() {
        return photo_product;
    }

    public void setPhoto_product(String photo_product) {
        this.photo_product = photo_product;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    // ... (добавьте конструкторы, если необходимо)
}


