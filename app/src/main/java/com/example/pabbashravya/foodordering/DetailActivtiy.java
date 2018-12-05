package com.example.pabbashravya.foodordering;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pabbashravya.foodordering.database.FoodDatabase;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;
import com.example.pabbashravya.foodordering.network.NetworkUtils;
import com.example.pabbashravya.foodordering.ui.DetailActivityViewModel;
import com.example.pabbashravya.foodordering.ui.DetailActivityViewModelFactory;

public class DetailActivtiy extends AppCompatActivity implements View.OnClickListener{

    int foodItemId;

    ImageView image;
    TextView name;
    TextView price;
    TextView rating;
    TextView quantity;
    ImageButton plus;
    ImageButton minus;

    int qty;
    DetailActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activtiy);

        foodItemId=getIntent().getIntExtra("ITEM_ID",0);

        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        rating=findViewById(R.id.rating);
        quantity=findViewById(R.id.quantity);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);

        plus.setOnClickListener(this);
        minus.setOnClickListener(this);

        FoodDatabase database=FoodDatabase.getInstance(this);
        AppExecutors executors=AppExecutors.getInstance();
        NetworkUtils networkUtils=NetworkUtils.getInstance(this,executors);
        FoodRepository repository=FoodRepository.getInstance(database.foodDao(),networkUtils,executors);
        DetailActivityViewModelFactory factory=new DetailActivityViewModelFactory(foodItemId,repository);
        viewModel= ViewModelProviders.of(this,factory).get(DetailActivityViewModel.class);

        viewModel.getFoodDetail().observe(this, new Observer<FoodItemEntity>() {
            @Override
            public void onChanged(@Nullable FoodItemEntity foodItemEntity) {
                if(foodItemEntity!=null)
                    bindDetails(foodItemEntity);
            }
        });
    }

    private void bindDetails(FoodItemEntity foodItem) {
        qty=foodItem.getQuantity();

        Glide.with(this.getApplicationContext()).load(foodItem.getImageUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);

        name.setText(foodItem.getName());
        price.setText("Price : "+String.valueOf(foodItem.getPricePerItem()));
        rating.setText("Rating : "+String.valueOf(foodItem.getRating()));
        quantity.setText("Quantity : "+qty);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.plus){
            qty++;
        }else if(view.getId()==R.id.minus){
            if(qty>0)
                qty--;
            else
            {
                Toast.makeText(this,"Negative Quantity",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        quantity.setText("Quantity : "+qty);
        viewModel.updateQuantityInDb(qty);
    }
}
