package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.SiteTourAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class SitesTourActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites_tour);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_sites_tour), true);

        adapterRecyclerView();
    }

    private void adapterRecyclerView() {
        RecyclerView pictureRecycler = findViewById(R.id.siteTourRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        SiteTourAdapterRecycler siteTourAdapter = new SiteTourAdapterRecycler(buildSiteTour(), R.layout.cardview_site_tour, this);
        pictureRecycler.setAdapter(siteTourAdapter);
    }

    public ArrayList<SiteTourModel> buildSiteTour() {
        serviceHelper = new ServiceHelper(this);

        return serviceHelper.getSiteTourModel(0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ContainerActivity.class);
        startActivity(intent);
        finish();
    }
}
