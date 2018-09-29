package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.FoodAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class MenuFoodActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_menu_food),true);

        setRecyclerView();
    }

    private void setRecyclerView() {
        services =new Services(this);
        boolean isActiveCheck= !services.getCheckModel(0, 1, 2).isEmpty();

        RecyclerView pictureRecycler= findViewById(R.id.foodMenuRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ArrayList<FoodMenuModel> listMenu=buildMenu();
        FoodAdapterRecycler foodAdapter=new FoodAdapterRecycler(listMenu,R.layout.cardview_food,this,isActiveCheck);
        pictureRecycler.setAdapter(foodAdapter);
    }

    public ArrayList<FoodMenuModel> buildMenu(){
        services =new Services(this);
        return services.getFoodMenuModel(0);
    }
}
