package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ServiceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ServicesActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_services),true);

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView pictureRecycler= findViewById(R.id.serviceRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ServiceAdapterRecycler adapterRecycler=new ServiceAdapterRecycler(buildService(),R.layout.cardview_service,this);
        pictureRecycler.setAdapter(adapterRecycler);
    }

    public ArrayList<ServiceModel> buildService(){
        serviceHelper =new ServiceHelper(this);

        return serviceHelper.getServiceModel(0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ContainerActivity.class);
        startActivity(intent);
        finish();
    }
}
