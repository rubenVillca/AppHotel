package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.adapter.ConsumeFoodAdapterRecycler;
import com.umss.sistemas.tesis.hotel.adapter.ConsumeServiceAdapterRecycler;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
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
        services = new Services(this);
        ArrayList<CheckModel> checkModels = services.getCheckModel(0, 1, 2);
        if (!checkModels.isEmpty()) {
            ArrayList<ConsumeServiceModel> consumeServiceModels = buildService(checkModels);
            ArrayList<ConsumeFoodModel> foodModels = buildFood(checkModels);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerView consumeFoodRecycler = findViewById(R.id.consumeFoodRecyclerView);
            consumeFoodRecycler.setLayoutManager(linearLayoutManager);
            ConsumeFoodAdapterRecycler conumeFoodAdapter = new ConsumeFoodAdapterRecycler(foodModels, R.layout.cardview_consume_food);
            consumeFoodRecycler.setAdapter(conumeFoodAdapter);

            RecyclerView consumeServiceRecycler = findViewById(R.id.consumeServiceRecyclerView);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
            linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
            consumeServiceRecycler.setLayoutManager(linearLayoutManager2);
            ConsumeServiceAdapterRecycler conumeAdapter = new ConsumeServiceAdapterRecycler(consumeServiceModels, R.layout.cardview_consume_service, this);
            consumeServiceRecycler.setAdapter(conumeAdapter);

            TextView total = findViewById(R.id.totalConsume);
            double totalCheck = 0;
            for (CheckModel checkModel : checkModels) {
                totalCheck += checkModel.getTotal();
            }
            total.setText(String.valueOf(checkModels.get(0).getConsumeServiceModelArrayList().get(0).getTypeMoney() + " " + totalCheck));
        }
    }

    public ArrayList<ConsumeServiceModel> buildService(ArrayList<CheckModel> checkModels) {
        ArrayList<ConsumeServiceModel> consumeServiceModels = new ArrayList<>();
        for (CheckModel checkModel : checkModels) {
            consumeServiceModels.addAll(checkModel.getConsumeServiceModelArrayList());
        }

        return consumeServiceModels;
    }

    public ArrayList<ConsumeFoodModel> buildFood(ArrayList<CheckModel> checkModels) {
        ArrayList<ConsumeFoodModel> consumeModels = new ArrayList<>();
        for (CheckModel checkModel : checkModels) {
            consumeModels.addAll(checkModel.getConsumeFoodModelArrayList());
        }

        return consumeModels;
    }
}
