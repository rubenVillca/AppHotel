package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;

import java.util.ArrayList;

public class FoodAdapterRecycler extends RecyclerView.Adapter<FoodAdapterRecycler.FoodViewHolder>{

    private ArrayList<FoodModel> foodModels;
    private int resource;
    private Activity activity;

    public FoodAdapterRecycler(ArrayList<FoodMenuModel> foodMenuModel, int resource, Activity activity) {
        this.resource = resource;
        this.activity = activity;

        this.foodModels=new ArrayList<>();
        for (FoodMenuModel menu:foodMenuModel) {
            foodModels.addAll(menu.getFoodModelArrayList());
        }
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new FoodAdapterRecycler.FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        final FoodModel food=foodModels.get(position);
        ArrayList<FoodPriceModel> foodPrices=food.getListFoodPriceModel();
        holder.foodNameCardView.setText(food.getName());
        holder.foodTypeFoodCardView.setText(food.getType());
        if (!foodPrices.isEmpty()) {
            holder.foodTypeMoneyCardView.setText(foodPrices.get(0).getTypeMoney());
            holder.foodPriceCardView.setText(String.valueOf(foodPrices.get(0).getPrice()));
        }

        String urlImage = Conexion.urlServer + food.getImage();
        Picasso.with(activity).load(urlImage).into(holder.foodPictureCardView);
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{
        private ImageView foodPictureCardView;
        private TextView foodNameCardView;
        private TextView foodTypeFoodCardView;
        private TextView foodTypeMoneyCardView;
        private TextView foodPriceCardView;

        private FoodViewHolder(View itemView) {
            super(itemView);

            foodPictureCardView=(ImageView)itemView.findViewById(R.id.imageFoodCardView);
            foodNameCardView=(TextView)itemView.findViewById(R.id.nameFoodCardView);
            foodTypeFoodCardView=(TextView)itemView.findViewById(R.id.typeFoodCardView);

            foodTypeMoneyCardView=(TextView)itemView.findViewById(R.id.moneyFoodCardView);
            foodPriceCardView=(TextView)itemView.findViewById(R.id.priceFoodCardView);
        }
    }
}
