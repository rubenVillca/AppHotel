package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class ReserveMemberActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_member);

        super.showToolBar("Miembros", true);
    }

    public void goReserveVerifyActivity(View view) {
        Intent intent = new Intent(this,ReserveVerifyActivity.class);
        startActivity(intent);
        finish();
    }
}
