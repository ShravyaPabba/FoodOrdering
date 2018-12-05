package com.example.pabbashravya.foodordering.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.pabbashravya.foodordering.FoodRepository;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

import java.util.List;

public class CartActivityViewModel extends ViewModel{

    private final FoodRepository foodRepository;
    private final LiveData<List<FoodItemEntity>> foodItemsList;

    public CartActivityViewModel(FoodRepository foodRepository) {
        this.foodRepository=foodRepository;

        foodItemsList=foodRepository.getCartItems();
    }

    public LiveData<List<FoodItemEntity>> getCartItems(){
        return foodItemsList;
    }
}
