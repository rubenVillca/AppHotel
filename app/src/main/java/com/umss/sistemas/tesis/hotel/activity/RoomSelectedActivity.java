package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.RoomPriceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class RoomSelectedActivity extends ActivityParent {

    private RoomAvailableModel roomAvailableModel;
    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selected);

        super.showToolBar("", true);

        buildBundle();
        init();
        setRecyclerView();
    }
    private void buildBundle() {
        nAdult = getIntent().getExtras().getInt("nAdult");
        nBoy = getIntent().getExtras().getInt("nBoy");
        dateIn = getIntent().getExtras().getString("dateIn");
        dateOut = getIntent().getExtras().getString("dateOut");
        timeIn = getIntent().getExtras().getString("timeIn");
        timeOut = getIntent().getExtras().getString("timeOut");
        roomAvailableModel = (RoomAvailableModel) getIntent().getExtras().getSerializable("roomAvailableModel");
    }

    public void init() {
        TextView nAdultTextView = (TextView) findViewById(R.id.nPersonAdultReserve);
        nAdultTextView.setText(String.valueOf(nAdult + " Adultos"));

        TextView nBoytTextView = (TextView) findViewById(R.id.nPersonBoyReserve);
        nBoytTextView.setText(String.valueOf(nBoy + " Niños"));

        TextView dateInTextView = (TextView) findViewById(R.id.dateTimeInReserve);
        dateInTextView.setText(String.valueOf(dateIn + "\n" + timeIn + " Horas"));

        TextView dateOutTextView = (TextView) findViewById(R.id.dateTimeOutReserve);
        dateOutTextView.setText(String.valueOf(dateOut + "\n" + timeOut + " Horas"));

        ImageView imageView = (ImageView) findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer+roomAvailableModel.getImageTypeRoom()).into(imageView);
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.priceRoomRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        RoomPriceAdapterRecycler homeAdapter = new RoomPriceAdapterRecycler(
                roomAvailableModel.getPriceServiceModels(),
                R.layout.cardview_price_service, this,
                nAdult, nBoy, dateIn, timeIn, dateOut, timeOut,roomAvailableModel.getUnitRoom());
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
