package com.umss.sistemas.tesis.hotel.util;


import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.umss.sistemas.tesis.hotel.R;

public class Activities extends AppCompatActivity {

    protected void showToolBar(String tittle, boolean upButton){
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
    }
}
