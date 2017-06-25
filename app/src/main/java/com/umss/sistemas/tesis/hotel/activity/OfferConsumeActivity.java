package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.view.MenuItem;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class OfferConsumeActivity extends ActivityParent {
    private OfferModel offerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_consume);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_offer_consume),true);
        initValues();
    }

    private void initValues() {
        Bundle bundle=getIntent().getExtras();
        offerModel=(OfferModel) bundle.getSerializable("offerModel");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
