package com.umss.sistemas.tesis.hotel.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.util.Activities;

public class CreateAccountActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_create_account),true);
    }
}
