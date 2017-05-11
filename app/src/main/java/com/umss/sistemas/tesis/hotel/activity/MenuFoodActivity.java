package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.FoodAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
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
        RecyclerView pictureRecycler=(RecyclerView)findViewById(R.id.foodMenuRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ArrayList<FoodMenuModel> listMenu=buildMenu();
        FoodAdapterRecycler foodAdapter=new FoodAdapterRecycler(listMenu,R.layout.cardview_food,this);
        pictureRecycler.setAdapter(foodAdapter);
    }

    public ArrayList<FoodMenuModel> buildMenu(){
        helperSQLiteObtain =new HelperSQLiteObtain(this);

        return helperSQLiteObtain.getFoodMenuModel(0);
    }
}
