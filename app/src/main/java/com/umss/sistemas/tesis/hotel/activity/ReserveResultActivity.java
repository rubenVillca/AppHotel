package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ReserveResultAdapterRecycler;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ReserveResultActivity extends ActivityParent {

    private CheckModel checkModel;
    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private ArrayList<ReserveSearchModel> reserveSearchModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_result);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_room_available), true);
        buildBundle();
        adapterRecyclerView();
    }

    private void buildBundle() {
        Bundle bundle = getIntent().getExtras();
        checkModel = (CheckModel) bundle.getSerializable("checkModel");
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

        ReserveResultAdapterRecycler reserveResultAdapterRecycler = new ReserveResultAdapterRecycler(reserveSearchModels, R.layout.cardview_reserve_result, this, nAdult, nBoy, dateIn, timeIn, dateOut, timeOut, checkModel.getId());
        recyclerView.setAdapter(reserveResultAdapterRecycler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(this,ReserveSearchActivity.class);
            intent.putExtra("checkModel",checkModel);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
