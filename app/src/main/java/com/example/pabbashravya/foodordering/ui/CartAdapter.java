package com.example.pabbashravya.foodordering.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pabbashravya.foodordering.R;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context mContext;
    private List<FoodItemEntity> foodItems;

    public CartAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int position) {
        FoodItemEntity foodItem=foodItems.get(position);
        cartViewHolder.name.setText("Name : "+foodItem.getName());
        cartViewHolder.price.setText("Price per Item : "+String.valueOf(foodItem.getPricePerItem()));
        cartViewHolder.quantity.setText("Quantity : "+foodItem.getQuantity());
        cartViewHolder.total.setText("Total : "+String.valueOf(foodItem.getPricePerItem()*foodItem.getQuantity()));

    }

    @Override
    public int getItemCount() {
        if(foodItems==null)
            return 0;
        else
            return foodItems.size();
    }

    public void swapItems(List<FoodItemEntity> newItems){
        foodItems=newItems;
        notifyDataSetChanged();

    }

    public double getGrandTotal(){
        double sum=0;
        if(foodItems!=null) {
            for (FoodItemEntity foodItem : foodItems)
                sum += foodItem.getPricePerItem() * foodItem.getQuantity();
        }
        return sum;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView price;
        TextView quantity;
        TextView total;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            total=itemView.findViewById(R.id.total);
        }

    }
}
