package com.example.pabbashravya.foodordering.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pabbashravya.foodordering.FoodRepository;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private final FoodRepository foodRepository;
    private final LiveData<List<FoodItemEntity>> foodItemsList;

    public MainActivityViewModel(FoodRepository foodRepository){
        this.foodRepository=foodRepository;

        foodItemsList=foodRepository.getFoodItemsList();
    }

    public LiveData<List<FoodItemEntity>> getFoodItemsList() {
        return foodItemsList;
    }

    public LiveData<List<FoodItemEntity>> getFoodItemsSortedByPrice(){
        return foodRepository.getFoodItemsSortedByPrice();
    }

    public LiveData<List<FoodItemEntity>> getFoodItemsSortedByRating(){
        return foodRepository.getFoodItemsSortedByRating();
    }

    public void updateQuantityInDb(int id, int qty) {
        foodRepository.updateQuantityInDb(id,qty);
    }
}
