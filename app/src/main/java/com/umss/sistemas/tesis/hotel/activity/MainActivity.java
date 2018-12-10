package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.ads.MobileAds;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class MainActivity extends ActivityParent {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        serviceHelper =new ServiceHelper(this);
        if (serviceHelper.isAccountActive()) {
            Intent intent = new Intent(this, ContainerActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
