package com.umss.sistemas.tesis.hotel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.util.Activities;

public class SitesTourActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_tour);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_sites_tour),true);
    }
}
