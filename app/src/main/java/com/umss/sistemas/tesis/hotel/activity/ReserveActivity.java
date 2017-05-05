package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class ReserveActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_reserve),true);
    }
}
