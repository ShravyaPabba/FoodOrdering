package com.example.pabbashravya.foodordering.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pabbashravya.foodordering.DetailActivtiy;
import com.example.pabbashravya.foodordering.R;
import com.example.pabbashravya.foodordering.database.FoodItemEntity;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    final OnQuantityChangedListener listener;
    private final Context mContext;
    private List<FoodItemEntity> foodItems;

    public interface OnQuantityChangedListener{
        void onPlusOrMinusClicked(int id,int qty);
    }

    public MainAdapter(Context context,OnQuantityChangedListener listener) {
        mContext = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.main_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int position) {
        FoodItemEntity foodItem=foodItems.get(position);
        mainViewHolder.bind(foodItem);

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

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView itemCard;
        ImageView image;
        TextView name;
        TextView price;
        TextView rating;
        TextView quantity;
        ImageButton plus;
        ImageButton minus;

        FoodItemEntity foodItem;
        int qty;

        public MainViewHolder(@NonNull View view) {
            super(view);

            itemCard=view.findViewById(R.id.itemCard);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=view.findViewById(R.id.price);
            rating=view.findViewById(R.id.rating);
            quantity=view.findViewById(R.id.quantity);
            plus=view.findViewById(R.id.plus);
            minus=view.findViewById(R.id.minus);
            itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, DetailActivtiy.class);
                    intent.putExtra("ITEM_ID",foodItem.getId());
                    mContext.startActivity(intent);
                }
            });

            plus.setOnClickListener(this);
            minus.setOnClickListener(this);

        }

        public void bind(FoodItemEntity foodItem) {
            this.foodItem=foodItem;
            qty=foodItem.getQuantity();

            Glide.with(mContext.getApplicationContext()).load(foodItem.getImageUrl())
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
                    Toast.makeText(mContext,"Negative Quantity",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            quantity.setText("Quantity : "+qty);
            listener.onPlusOrMinusClicked(foodItem.getId(),qty);
        }
    }
}
