package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.RoomAvailableAdapterRecycler;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class RoomAvailableActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_available);

        super.showToolBar(getResources().getString(R.string.toolbar_tittle_room_available), true);

        adapterRecyclerView();
    }

    private void adapterRecyclerView() {
        RecyclerView pictureRecycler = (RecyclerView) findViewById(R.id.roomAvailableRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        RoomAvailableAdapterRecycler homeAdapter = new RoomAvailableAdapterRecycler(buildService(), R.layout.cardview_available_room, this);
        pictureRecycler.setAdapter(homeAdapter);
    }

    public ArrayList<RoomAvailableModel> buildService() {
        ArrayList<RoomAvailableModel> roomAvailableModels=new ArrayList<>();

        int size=getIntent().getExtras().getInt("roomAvailableSize");
        for (int i=0;i<size;i++){
            RoomAvailableModel roomAvailableModel=(RoomAvailableModel) getIntent().getExtras().getSerializable("room-"+i);
            roomAvailableModels.add(roomAvailableModel);
        }

        return roomAvailableModels;
    }

}
