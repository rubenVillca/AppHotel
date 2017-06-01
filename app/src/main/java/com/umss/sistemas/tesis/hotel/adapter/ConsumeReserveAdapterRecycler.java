package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;

import java.util.ArrayList;

public class ConsumeReserveAdapterRecycler extends RecyclerView.Adapter<ConsumeReserveAdapterRecycler.ConsumeReserveViewHolder>{

    private ArrayList<ConsumeModel> consumeModels;
    private int resource;
    private Activity activity;

    public ConsumeReserveAdapterRecycler(ArrayList<ConsumeModel> consumeModels, int resource, Activity activity) {
        this.consumeModels = consumeModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ConsumeReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ConsumeReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumeReserveViewHolder holder, int position) {
        ConsumeModel consumeModel = consumeModels.get(position);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        holder.recyclerView.setLayoutManager(linearLayoutManager);

        RoomReserveAdapterRecycler adapterRecycler = new RoomReserveAdapterRecycler(consumeModel.getReserveModelArrayList(), R.layout.cardview_room_reserve, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return consumeModels.size();
    }

    class ConsumeReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        private ConsumeReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.roomReserveRecyclerView);
        }
    }
}
