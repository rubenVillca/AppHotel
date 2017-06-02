package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
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
        helperSQLiteObtain = new HelperSQLiteObtain(this);
        ArrayList<CheckModel> checkModels = helperSQLiteObtain.getCheckModel(0, 1, 1);

        if (!checkModels.isEmpty()) {
            goReserveListActivity();
        }else{
            goReserveActivity();
        }
    }

    private void goReserveListActivity() {
        Intent intent=new Intent(this,ReserveCheckActivity.class);
        startActivity(intent);
        ReserveVerifyActivity.this.finish();
    }

    private void goReserveActivity() {
        Intent intent=new Intent(this,ReserveSearchActivity.class);
        startActivity(intent);
        ReserveVerifyActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
