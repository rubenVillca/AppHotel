package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ConsumeFoodAdapterRecycler;
import com.umss.sistemas.tesis.hotel.adapter.ConsumeServiceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
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
        helperSQLiteObtain = new HelperSQLiteObtain(this);
        ArrayList<CheckModel> checkModels = helperSQLiteObtain.getCheckModel(0, 1, 2);
        if (!checkModels.isEmpty()) {
            ArrayList<ConsumeModel> consumeModels = buildService(checkModels);
            ArrayList<ConsumeFoodModel> foodModels = buildFood(checkModels);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerView consumeFoodRecycler = (RecyclerView) findViewById(R.id.consumeFoodRecyclerView);
            consumeFoodRecycler.setLayoutManager(linearLayoutManager);
            ConsumeFoodAdapterRecycler conumeFoodAdapter = new ConsumeFoodAdapterRecycler(foodModels, R.layout.cardview_consume_food);
            consumeFoodRecycler.setAdapter(conumeFoodAdapter);

            RecyclerView consumeServiceRecycler = (RecyclerView) findViewById(R.id.consumeServiceRecyclerView);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
            consumeServiceRecycler.setLayoutManager(linearLayoutManager2);
            ConsumeServiceAdapterRecycler conumeAdapter = new ConsumeServiceAdapterRecycler(consumeModels, R.layout.cardview_consume_service, this);
            consumeServiceRecycler.setAdapter(conumeAdapter);

            TextView total = (TextView) findViewById(R.id.totalConsume);
            double totalCheck = 0;
            for (CheckModel checkModel : checkModels) {
                totalCheck += checkModel.getTotal();
            }
            total.setText(String.valueOf(checkModels.get(0).getConsumeModelArrayList().get(0).getTypeMoney() + " " + totalCheck));
        }
    }

    public ArrayList<ConsumeModel> buildService(ArrayList<CheckModel> checkModels) {
        ArrayList<ConsumeModel> consumeModels = new ArrayList<>();
        for (CheckModel checkModel : checkModels) {
            consumeModels.addAll(checkModel.getConsumeModelArrayList());
        }

        return consumeModels;
    }

    public ArrayList<ConsumeFoodModel> buildFood(ArrayList<CheckModel> checkModels) {
        ArrayList<ConsumeFoodModel> consumeModels = new ArrayList<>();
        for (CheckModel checkModel : checkModels) {
            consumeModels.addAll(checkModel.getConsumeFoodModelArrayList());
        }

        return consumeModels;
    }
}
