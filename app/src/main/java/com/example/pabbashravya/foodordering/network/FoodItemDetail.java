package com.example.pabbashravya.foodordering.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodItemDetail {

    @SerializedName("average_rating")
    @Expose
    private double averageRating;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_price")
    @Expose
    private double itemPrice;

    /**
     * No args constructor for use in serialization
     *
     */
    public FoodItemDetail() {
    }

    /**
     *
     * @param itemName
     * @param averageRating
     * @param imageUrl
     * @param itemPrice
     */
    public FoodItemDetail(double averageRating, String imageUrl, String itemName, double itemPrice) {
        super();
        this.averageRating = averageRating;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

}
