package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.umss.sistemas.tesis.hotel.helper.ServiceGet;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class MainActivity extends ActivityParent {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceGet =new ServiceGet(this);
        if (serviceGet.isAccountActive()) {
            Intent intent = new Intent(this, ContainerActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
