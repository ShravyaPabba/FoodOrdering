package com.example.pabbashravya.foodordering.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.pabbashravya.foodordering.AppExecutors;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtils {

    private static final Object LOCK=new Object();
    private static NetworkUtils sInstance;
    private final Context mContext;
    private final AppExecutors mExecutors;
    private final List<FoodItemEntity> mFoodEntities=new ArrayList<>();
    private final MutableLiveData<List<FoodItemEntity>> mDownloadedFoodItems;

    private NetworkUtils(Context context,AppExecutors executors){
        mContext=context;
        mExecutors=executors;
        mDownloadedFoodItems=new MutableLiveData<>();
    }

    public static NetworkUtils getInstance(Context context,AppExecutors executors){
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkUtils(context.getApplicationContext(), executors);
            }
        }
        return sInstance;

    }

    public  void fetchFoodItemsList(){
        APIInterface apiService = APIClient.getClient().create(APIInterface.class);
        Call<List<FoodItemDetail>> foodCall=apiService.getFoodItems();
        foodCall.enqueue(new Callback<List<FoodItemDetail>>() {
            @Override
            public void onResponse(Call<List<FoodItemDetail>> call, Response<List<FoodItemDetail>> response) {
                if (response.body() == null) return;

                for(FoodItemDetail foodItem:response.body()){
                    if(foodItem!=null&&foodItem.getItemName()!=null){
                        mFoodEntities.add(new FoodItemEntity(foodItem.getItemName(),foodItem.getItemPrice(),0,
                                foodItem.getAverageRating(),foodItem.getImageUrl()));
                    }
                }

                mDownloadedFoodItems.postValue(mFoodEntities);


            }

            @Override
            public void onFailure(Call<List<FoodItemDetail>> call, Throwable t) {
                call.cancel();

            }
        });

    }

    public LiveData<List<FoodItemEntity>> getDownloadedFoodItems(){
        return mDownloadedFoodItems;
    }

    public void startFetchFoodService() {
        Intent intent=new Intent(mContext,FoodIntentService.class);
        mContext.startService(intent);
    }
}
