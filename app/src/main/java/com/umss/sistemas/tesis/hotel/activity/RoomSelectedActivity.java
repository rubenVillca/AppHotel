package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class RoomSelectedActivity extends ActivityParent {

    private RoomAvailableModel roomAvailableModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selected);

        super.showToolBar("", true);

        roomAvailableModel=(RoomAvailableModel)getIntent().getExtras().getSerializable("roomAvailableModel");
    }

}
