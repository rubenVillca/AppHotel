package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.CheckModel;

import java.util.ArrayList;

public class CheckReserveAdapterRecycler extends RecyclerView.Adapter<CheckReserveAdapterRecycler.CheckReserveViewHolder> {

    private ArrayList<CheckModel> checkModels;
    private int resource;
    private Activity activity;

    public CheckReserveAdapterRecycler(ArrayList<CheckModel> checkModels, int resource, Activity activity) {
        this.checkModels = checkModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public CheckReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CheckReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckReserveViewHolder holder, int position) {
        final CheckModel checkModel = checkModels.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setLayoutManager(linearLayoutManager);

        ConsumeReserveAdapterRecycler siteTourAdapter = new ConsumeReserveAdapterRecycler(checkModel.getConsumeModelArrayList(), R.layout.cardview_consume_reserve, activity);
        holder.recyclerView.setAdapter(siteTourAdapter);
    }

    @Override
    public int getItemCount() {
        return checkModels.size();
    }

    class CheckReserveViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        RecyclerView recyclerView;
        private CheckReserveViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.checkReserveLayout);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.consumeReserveRecyclerView);
        }
    }
}
