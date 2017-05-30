package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.RoomAvailableAdapterRecycler;
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class RoomAvailableActivity extends ActivityParent {

    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private  ArrayList<RoomAvailableModel> roomAvailableModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_available);

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

        roomAvailableModels = new ArrayList<>();
        int size = getIntent().getExtras().getInt("roomAvailableSize");
        for (int i = 0; i < size; i++) {
            RoomAvailableModel roomAvailableModel = (RoomAvailableModel) getIntent().getExtras().getSerializable("room-" + i);
            roomAvailableModels.add(roomAvailableModel);
        }
    }

    private void adapterRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.roomAvailableRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        RoomAvailableAdapterRecycler roomAvailableAdapterRecycler = new RoomAvailableAdapterRecycler(roomAvailableModels, R.layout.cardview_available_room, this, nAdult, nBoy, dateIn, timeIn, dateOut, timeOut);
        recyclerView.setAdapter(roomAvailableAdapterRecycler);
    }
}
