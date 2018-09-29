package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.OfferAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class OffersActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_offer),true);
        setRecyclerView();
    }
    private void setRecyclerView() {
        RecyclerView pictureRecycler= findViewById(R.id.offerRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        OfferAdapterRecycler offerAdapter=new OfferAdapterRecycler(buildOffer(),R.layout.cardview_offer,this);
        pictureRecycler.setAdapter(offerAdapter);
    }
    public ArrayList<OfferModel> buildOffer(){
        services =new Services(this);
        return services.getOfferModel(0);
    }
}
