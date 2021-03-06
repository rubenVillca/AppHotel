package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ActivityAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.CalendarModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class CalendarActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        showToolBar(getResources().getString(R.string.toolbar_tittle_calendar), true);

        adapterRecyclerView();
    }

    private void adapterRecyclerView() {
        RecyclerView pictureRecycler = findViewById(R.id.calendarRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pictureRecycler.setLayoutManager(linearLayoutManager);

        ActivityAdapterRecycler activityAdapter = new ActivityAdapterRecycler(buildCalendar(), R.layout.cardview_activity, this);
        pictureRecycler.setAdapter(activityAdapter);
    }

    public ArrayList<CalendarModel> buildCalendar() {
        serviceHelper = new ServiceHelper(this);
        return serviceHelper.getActivityModel(0);
    }
}
