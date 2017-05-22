package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ConsumeAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.util.ArrayList;

public class ConsumeActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume);

        showToolBar(getResources().getString(R.string.toolbar_tittle_consume), true);

        setRecyclerView();
    }
    private void setRecyclerView() {
        RecyclerView consumeRecycler =(RecyclerView)findViewById(R.id.consumeRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        consumeRecycler.setLayoutManager(linearLayoutManager);

        ConsumeAdapterRecycler conumeAdapter =new ConsumeAdapterRecycler(buildService(),R.layout.cardview_consume,this);
        consumeRecycler.setAdapter(conumeAdapter);
    }

    public ArrayList<ConsumeModel> buildService(){
        helperSQLiteObtain =new HelperSQLiteObtain(this);

        ArrayList<CheckModel> checkModels=helperSQLiteObtain.getCheckModel(0,1);
        ArrayList<ConsumeModel> consumeModels=new ArrayList<>();

        for (CheckModel checkModel: checkModels) {
            consumeModels.addAll(checkModel.getConsumeModelArrayList());
        }

        return consumeModels;
    }
}
