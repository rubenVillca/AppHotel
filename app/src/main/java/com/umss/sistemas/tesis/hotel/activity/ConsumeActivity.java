package com.umss.sistemas.tesis.hotel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class ConsumeActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume);

        showToolBar(getResources().getString(R.string.toolbar_tittle_consume), true);
    }
}
