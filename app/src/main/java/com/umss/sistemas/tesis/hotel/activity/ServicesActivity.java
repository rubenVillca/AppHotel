package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ServiceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.parent.Activities;

import java.util.ArrayList;

public class ServicesActivity extends Activities {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_services),true);

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView pictureRecycler=(RecyclerView)findViewById(R.id.serviceRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ServiceAdapterRecycler homeAdapter=new ServiceAdapterRecycler(buildService(),R.layout.cardview_service,this);
        pictureRecycler.setAdapter(homeAdapter);
    }

    public ArrayList<ServiceModel> buildService(){
        helperSQLite=new HelperSQLite(this);

        return helperSQLite.getServiceModel(0);
    }
}
