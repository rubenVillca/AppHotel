package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ReserveSearchAdapterRecycler;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ReserveResultActivity extends ActivityParent {

    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private  ArrayList<ReserveSearchModel> reserveSearchModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_result);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_room_available), true);
        buildBundle();
        adapterRecyclerView();
    }

    private void buildBundle() {
        Bundle bundle=getIntent().getExtras();
        nAdult = bundle.getInt("nAdult");
        nBoy = bundle.getInt("nBoy");
        dateIn = bundle.getString("dateIn");
        dateOut = bundle.getString("dateOut");
        timeIn = bundle.getString("timeIn");
        timeOut = bundle.getString("timeOut");

        reserveSearchModels = new ArrayList<>();
        int size = getIntent().getExtras().getInt("roomAvailableSize");
        for (int i = 0; i < size; i++) {
            ReserveSearchModel reserveSearchModel = (ReserveSearchModel) getIntent().getExtras().getSerializable("room-" + i);
            reserveSearchModels.add(reserveSearchModel);
        }
    }

    private void adapterRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.roomAvailableRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        ReserveSearchAdapterRecycler reserveSearchAdapterRecycler = new ReserveSearchAdapterRecycler(reserveSearchModels, R.layout.cardview_reserve_result, this, nAdult, nBoy, dateIn, timeIn, dateOut, timeOut);
        recyclerView.setAdapter(reserveSearchAdapterRecycler);
    }
}
