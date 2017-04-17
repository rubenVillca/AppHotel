package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.HomeAdapterRecycler;
import com.umss.sistemas.tesis.hotel.model.Picture;
import com.umss.sistemas.tesis.hotel.util.Activities;

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
        RecyclerView pictureRecycler=(RecyclerView)findViewById(R.id.homeRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        HomeAdapterRecycler homeAdapter=new HomeAdapterRecycler(buidPicture(),R.layout.cardview_service,this);
        pictureRecycler.setAdapter(homeAdapter);
    }

    public ArrayList<Picture> buidPicture(){
        ArrayList<Picture> pictures= new ArrayList<>();

        pictures.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Mario","4 dias","3  me gusta"));
        pictures.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","4"));
        pictures.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","5 me gusta"));
        pictures.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","6  me gusta"));
        pictures.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Sasso_lungo_da_passo_pordoi.jpg/270px-Sasso_lungo_da_passo_pordoi.jpg","Marioas","7 dias","7  me gusta"));

        return pictures;
    }
}
