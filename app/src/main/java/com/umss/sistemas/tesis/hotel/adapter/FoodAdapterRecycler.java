package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ConsumeFoodActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;

import java.util.ArrayList;

public class FoodAdapterRecycler extends RecyclerView.Adapter<FoodAdapterRecycler.FoodViewHolder>{

    private ArrayList<FoodModel> foodModels;
    private int resource;
    private Activity activity;
    private boolean isActiveCheck;

    public FoodAdapterRecycler(ArrayList<FoodMenuModel> foodMenuModel, int resource, Activity activity,boolean isActiveCheck) {
        this.resource = resource;
        this.activity = activity;
        this.isActiveCheck=isActiveCheck;
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
        final FoodModel foodModel=foodModels.get(position);
        ArrayList<FoodPriceModel> foodPrices=foodModel.getListFoodPriceModel();
        holder.foodNameCardView.setText(foodModel.getName());
        holder.foodTypeFoodCardView.setText(foodModel.getType());
        if (!foodPrices.isEmpty()) {
            holder.foodTypeMoneyCardView.setText(foodPrices.get(0).getTypeMoney());
            holder.foodPriceCardView.setText(String.valueOf(foodPrices.get(0).getPrice()));
        }

        String urlImage = Conexion.urlServer + foodModel.getImage();
        Picasso.with(activity).load(urlImage).into(holder.foodPictureCardView);
        if (isActiveCheck){
            holder.foodPictureCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(activity, ConsumeFoodActivity.class);
                    intent.putExtra("foodModel",foodModel);
                    activity.startActivity(intent);
                }
            });
        }
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

            foodPictureCardView= itemView.findViewById(R.id.imageFoodCardView);
            foodNameCardView= itemView.findViewById(R.id.nameFoodCardView);
            foodTypeFoodCardView= itemView.findViewById(R.id.typeFoodCardView);

            foodTypeMoneyCardView= itemView.findViewById(R.id.moneyFoodCardView);
            foodPriceCardView= itemView.findViewById(R.id.priceFoodCardView);
        }
    }
}
