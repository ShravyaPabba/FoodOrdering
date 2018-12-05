package com.example.pabbashravya.foodordering.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.pabbashravya.foodordering.FoodRepository;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final FoodRepository foodRepository;

    public MainActivityViewModelFactory(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(foodRepository);
    }
}