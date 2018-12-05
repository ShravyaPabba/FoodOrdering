package com.example.pabbashravya.foodordering.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "fooditems")
public class FoodItemEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double pricePerItem;
    private int quantity;
    private double rating;
    private String imageUrl;

    public FoodItemEntity(int id, String name, double pricePerItem, int quantity,double rating,String imageUrl) {
        this.id = id;
        this.name = name;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        this.rating=rating;
        this.imageUrl=imageUrl;
    }

    @Ignore
    public FoodItemEntity(String name, double pricePerItem, int quantity, double rating, String imageUrl) {
        this.name = name;
        this.pricePerItem = pricePerItem;
        this.quantity = quantity;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
