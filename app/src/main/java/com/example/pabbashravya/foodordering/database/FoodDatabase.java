package com.example.pabbashravya.foodordering.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FoodItemEntity.class},version = 1,exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static FoodDatabase sInstance;

    public static FoodDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FoodDatabase.class,"food").build();
            }
        }
        return sInstance;
    }


    public abstract FoodItemDao foodDao();
}