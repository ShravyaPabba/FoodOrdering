package com.example.pabbashravya.foodordering;

import android.app.IntentService;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.pabbashravya.foodordering.database.FoodDatabase;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;
import com.example.pabbashravya.foodordering.network.NetworkUtils;
import com.example.pabbashravya.foodordering.ui.MainActivityViewModel;
import com.example.pabbashravya.foodordering.ui.MainActivityViewModelFactory;
import com.example.pabbashravya.foodordering.ui.MainAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, MainAdapter.OnQuantityChangedListener {

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private Button filter;

    private MainActivityViewModel viewModel;
    private AppExecutors executors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter=findViewById(R.id.filter);
        recyclerView=findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainAdapter=new MainAdapter(this,this);
        recyclerView.setAdapter(mainAdapter);

        FoodDatabase database=FoodDatabase.getInstance(this);
        executors=AppExecutors.getInstance();
        NetworkUtils networkUtils=NetworkUtils.getInstance(this,executors);
        FoodRepository repository=FoodRepository.getInstance(database.foodDao(),networkUtils,executors);
        MainActivityViewModelFactory factory=new MainActivityViewModelFactory(repository);
        viewModel= ViewModelProviders.of(this,factory).get(MainActivityViewModel.class);
        viewModel.getFoodItemsList().observe(this, new Observer<List<FoodItemEntity>>() {
            @Override
            public void onChanged(@Nullable List<FoodItemEntity> foodItemEntities) {
                mainAdapter.swapItems(foodItemEntities);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup=new PopupMenu(MainActivity.this,view);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.getMenuInflater().inflate(R.menu.menu_actions,popup.getMenu());
                popup.show();
            }
        });


    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.price_sort:
                viewModel.getFoodItemsSortedByPrice().observe(this, new Observer<List<FoodItemEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<FoodItemEntity> foodItemEntities) {
                        mainAdapter.swapItems(foodItemEntities);
                    }
                });
                return true;
            case R.id.rating_sort:
                viewModel.getFoodItemsSortedByRating().observe(this, new Observer<List<FoodItemEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<FoodItemEntity> foodItemEntities) {
                        mainAdapter.swapItems(foodItemEntities);
                    }
                });
                return true;
            default:
                return false;

        }
    }

    @Override
    public void onPlusOrMinusClicked(int id, int qty) {
        viewModel.updateQuantityInDb(id,qty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=new Intent(this,CartActivity.class);
        startActivity(intent);
        return true;
    }
}
