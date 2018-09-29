package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ReserveVerifyActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verify();
    }

    private void verify() {
        services = new Services(this);
        ArrayList<CheckModel> checkModelActive = services.getCheckModel(0, 1, 1);
        ArrayList<CheckModel> checkModelPending = services.getCheckModel(0, 3, 1);

        ArrayList<CheckModel> checkModels=new ArrayList<>();
        checkModels.addAll(checkModelActive);
        checkModels.addAll(checkModelPending);

        if (!checkModels.isEmpty()) {
            goReserveCheckActivity();
        }else{
            goReserveActivity();
        }
    }

    private void goReserveCheckActivity() {
        Intent intent=new Intent(this,ReserveCheckActivity.class);
        startActivity(intent);
        ReserveVerifyActivity.this.finish();
    }

    private void goReserveActivity() {
        Intent intent=new Intent(this,ReserveSearchActivity.class);
        intent.putExtra("checkModel",new CheckModel());
        startActivity(intent);
        ReserveVerifyActivity.this.finish();
    }
}
