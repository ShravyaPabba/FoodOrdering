package com.example.pabbashravya.foodordering.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pabbashravya.foodordering.FoodRepository;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

public class DetailActivityViewModel extends ViewModel {

    private final LiveData<FoodItemEntity> foodDetail;

    private final int id;
    private final FoodRepository repository;

    public DetailActivityViewModel(int id, FoodRepository repository) {
        this.id = id;
        this.repository = repository;

        foodDetail=repository.getFoodItemById(id);
    }

    public LiveData<FoodItemEntity> getFoodDetail() {
        return foodDetail;
    }

    public void updateQuantityInDb(int qty) {
        repository.updateQuantityInDb(id,qty);
    }
}
