package com.example.pabbashravya.foodordering;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.example.pabbashravya.foodordering.database.FoodItemDao;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;
import com.example.pabbashravya.foodordering.network.NetworkUtils;

import java.util.List;

public class FoodRepository {

    private static final Object LOCK=new Object();
    private static FoodRepository sInstance;
    private final FoodItemDao mFoodDao;
    private final NetworkUtils mNetworkInstance;
    private final AppExecutors mExecutors;
    private boolean mInitialized=false;

    private FoodRepository(FoodItemDao foodItemDao,NetworkUtils networkUtils,AppExecutors executors){
        mFoodDao=foodItemDao;
        mNetworkInstance=networkUtils;
        mExecutors=executors;

        LiveData<List<FoodItemEntity>> networkData=mNetworkInstance.getDownloadedFoodItems();
        networkData.observeForever(new Observer<List<FoodItemEntity>>() {
            @Override
            public void onChanged(@Nullable final List<FoodItemEntity> newFoodItemsFromNetwork) {
                mExecutors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mFoodDao.bulkInsert(newFoodItemsFromNetwork);
                    }
                });

                }
        });
    }

    public synchronized static FoodRepository getInstance(FoodItemDao foodItemDao,NetworkUtils networkUtils,AppExecutors executors){
        if(sInstance==null){
            synchronized (LOCK){
                sInstance=new FoodRepository(foodItemDao,networkUtils,executors);
            }
        }
        return sInstance;
    }

    private synchronized void initializeData(){
        if(mInitialized)
            return;
        mInitialized=true;
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(isFetchNeeded())
                    mNetworkInstance.startFetchFoodService();
            }
        });

    }

    public LiveData<List<FoodItemEntity>> getFoodItemsList(){
        initializeData();
        return mFoodDao.getFoodItemsList();
    }

    public LiveData<List<FoodItemEntity>> getFoodItemsSortedByPrice(){
       initializeData();
        return mFoodDao.getFoodItemsSortedByPrice();

    }

    public LiveData<List<FoodItemEntity>> getFoodItemsSortedByRating(){
        return mFoodDao.getFoodItemsSortedByRating();
    }

    public LiveData<FoodItemEntity> getFoodItemById(int id){
        initializeData();
        return mFoodDao.getFoodItemById(id);
    }

    public LiveData<List<FoodItemEntity>> getCartItems(){
        return mFoodDao.getCartItems();
    }

    private boolean isFetchNeeded(){
        int count=mFoodDao.getTotalCount();
        return (count==0);
    }

    public void updateQuantityInDb(final int id, final int qty) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFoodDao.updateQuantityInDb(id,qty);
            }
        });
    }
}
