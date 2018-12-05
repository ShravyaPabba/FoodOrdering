package com.example.pabbashravya.foodordering.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FoodItemDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<FoodItemEntity> newFoodItemsFromNetwork);

    @Query("SELECT * FROM fooditems")
    LiveData<List<FoodItemEntity>> getFoodItemsList();

    @Query("SELECT * FROM fooditems ORDER BY pricePerItem")
    LiveData<List<FoodItemEntity>> getFoodItemsSortedByPrice();

    @Query("SELECT * FROM fooditems ORDER BY rating DESC")
    LiveData<List<FoodItemEntity>> getFoodItemsSortedByRating();

    @Query("SELECT * FROM fooditems WHERE id = :id")
    LiveData<FoodItemEntity> getFoodItemById(int id);

    @Query("SELECT COUNT(*) FROM fooditems")
    int getTotalCount();

    @Query("SELECT * FROM fooditems WHERE quantity>0")
    LiveData<List<FoodItemEntity>> getCartItems();

    @Query("UPDATE fooditems SET quantity = :qty WHERE id = :id")
    void updateQuantityInDb(int id, int qty);
}
