package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class MenuFoodActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_food);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_menu_food),true);
    }
}
