package com.example.pabbashravya.foodordering.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.pabbashravya.foodordering.FoodRepository;


public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final int id;
    private final FoodRepository repository;

    public DetailActivityViewModelFactory(int id, FoodRepository repository) {
        this.id = id;
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(id,repository);
    }
}
