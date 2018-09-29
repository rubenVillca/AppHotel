package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.FrequentlyAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class FrequentlyActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently);

        services =new Services(this);
        showToolBar(getResources().getString(R.string.toolbar_tittle_frequently), true);
        adapterRecyclerView();

    }

    /**
     * cargar lista de preguntas frecuentes a la vista de la app
     */
    private void adapterRecyclerView() {
        RecyclerView frequenltyRecycler = findViewById(R.id.frequentlyRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        frequenltyRecycler.setLayoutManager(linearLayoutManager);

        FrequentlyAdapterRecycler activityAdapter = new FrequentlyAdapterRecycler(buildFrequently(), R.layout.cardview_frequently, this);
        frequenltyRecycler.setAdapter(activityAdapter);
    }

    public ArrayList<FrequentlyModel> buildFrequently(){
        return services.getFrequentlyModel(0);
    }

}
