package com.example.pabbashravya.foodordering.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.pabbashravya.foodordering.AppExecutors;

public class FoodIntentService extends IntentService {

    public FoodIntentService() {
        super("FoodintentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppExecutors executors = AppExecutors.getInstance();
        NetworkUtils networkUtils = NetworkUtils.getInstance(this, executors);
        networkUtils.fetchFoodItemsList();

    }
}
