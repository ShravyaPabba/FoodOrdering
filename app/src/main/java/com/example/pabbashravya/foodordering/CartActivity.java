package com.example.pabbashravya.foodordering;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pabbashravya.foodordering.database.FoodDatabase;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;
import com.example.pabbashravya.foodordering.network.NetworkUtils;
import com.example.pabbashravya.foodordering.ui.CartActivityViewModel;
import com.example.pabbashravya.foodordering.ui.CartActivityViewModelFactory;
import com.example.pabbashravya.foodordering.ui.CartAdapter;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView grandTotalView;
    private EditText couponView;
    private Button apply;
    private TextView amtPayable;

    double grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new CartAdapter(this);
        recyclerView.setAdapter(adapter);

        FoodDatabase database=FoodDatabase.getInstance(this);
        AppExecutors executors=AppExecutors.getInstance();
        NetworkUtils networkUtils=NetworkUtils.getInstance(this,executors);
        FoodRepository repository=FoodRepository.getInstance(database.foodDao(),networkUtils,executors);
        CartActivityViewModelFactory factory=new CartActivityViewModelFactory(repository);
        CartActivityViewModel viewModel=ViewModelProviders.of(this,factory).get(CartActivityViewModel.class);
        viewModel.getCartItems().observe(this, new Observer<List<FoodItemEntity>>() {
            @Override
            public void onChanged(@Nullable List<FoodItemEntity> foodItemEntities) {
                adapter.swapItems(foodItemEntities);
                setUpOtherViews();
            }
        });

    }

    private void setUpOtherViews() {
        grandTotalView=findViewById(R.id.grand_total);
        couponView=findViewById(R.id.coupon);
        apply=findViewById(R.id.apply);
        amtPayable=findViewById(R.id.amt_payable);
        grandTotal=adapter.getGrandTotal();

        grandTotalView.setText("Grand Total : "+grandTotal);
        amtPayable.setText("Amount Payable : "+String.valueOf(grandTotal+30));
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String coupon=couponView.getText().toString();
                if(coupon.isEmpty())
                    Toast.makeText(CartActivity.this,"Enter a coupon to apply",Toast.LENGTH_SHORT).show();
                else
                    applyCoupon(coupon);
            }
        });
    }

    private void applyCoupon(String coupon) {
        if(coupon.equalsIgnoreCase("F22LABS")&&grandTotal>400){
            grandTotal-=(20*grandTotal)/100;
            grandTotalView.setText("Grand Total : "+grandTotal);
            amtPayable.setText("Amount Payable : "+String.valueOf(grandTotal+30));
        }else if(coupon.equalsIgnoreCase("FREEDEL")&&grandTotal>100){
            amtPayable.setText("Amount Payable : "+grandTotal);
        }else {
            Toast.makeText(this,"Invalid Coupon",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"Coupon Applied",Toast.LENGTH_SHORT).show();
    }
}
