package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.FoodAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
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
        serviceHelper =new ServiceHelper(this);
        boolean isActiveCheck= !serviceHelper.getCheckModel(0, 1, 2).isEmpty();

        RecyclerView pictureRecycler= findViewById(R.id.foodMenuRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ArrayList<FoodMenuModel> listMenu=buildMenu();
        FoodAdapterRecycler foodAdapter=new FoodAdapterRecycler(listMenu,R.layout.cardview_food,this,isActiveCheck);
        pictureRecycler.setAdapter(foodAdapter);
    }

    public ArrayList<FoodMenuModel> buildMenu(){
        serviceHelper =new ServiceHelper(this);
        return serviceHelper.getFoodMenuModel(0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ContainerActivity.class);
        startActivity(intent);
        finish();
    }
}
