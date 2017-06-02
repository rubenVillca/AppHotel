package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ReserveCheckAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ReserveCheckActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_check);

        super.showToolBar("Lista de reservas",true);

        adapterRecyclerView();
    }

    private void adapterRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.checkReserveRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        ReserveCheckAdapterRecycler adapterRecycler = new ReserveCheckAdapterRecycler(buildCheckReserve(), R.layout.cardview_reserve_check, this);
        recyclerView.setAdapter(adapterRecycler);
    }

    public ArrayList<CheckModel> buildCheckReserve() {
        helperSQLiteObtain = new HelperSQLiteObtain(this);
        return helperSQLiteObtain.getCheckModel(0,1,1);
    }

    public void goReserveSearchActivity(View view) {
        Intent intent=new Intent(this,ReserveSearchActivity.class);
        intent.putExtra("checkModel",new CheckModel());
        startActivity(intent);
    }
    /**
     * @param keyCode: boton de atras del teclado del celular
     * @param event:evento al presionar el boron
     * @return boolean:si presiono el boton return true
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent=new Intent(this,ContainerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param item: munu superior
     * @return boolean:resultado de presionar el boton de atras del menu superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent=new Intent(this,ContainerActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
