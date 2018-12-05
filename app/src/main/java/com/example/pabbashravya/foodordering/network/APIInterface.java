package com.example.pabbashravya.foodordering.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("data.json")
    Call<List<FoodItemDetail>> getFoodItems();
}
