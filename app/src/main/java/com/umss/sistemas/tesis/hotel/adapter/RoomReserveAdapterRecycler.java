package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.model.ReserveModel;

import java.util.ArrayList;

public class RoomReserveAdapterRecycler extends RecyclerView.Adapter<RoomReserveAdapterRecycler.RoomReserveViewHolder>{

    private ArrayList<ReserveModel> reserveModels;
    private int resource;
    private Activity activity;

    public RoomReserveAdapterRecycler(ArrayList<ReserveModel> reserveModels, int resource, Activity activity) {
        this.reserveModels = reserveModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public RoomReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new RoomReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomReserveViewHolder holder, int position) {
        ReserveModel reserveModel = reserveModels.get(position);


    }

    @Override
    public int getItemCount() {
        return reserveModels.size();
    }

    class RoomReserveViewHolder extends RecyclerView.ViewHolder{

        private RoomReserveViewHolder(View itemView) {
            super(itemView);

        }
    }
}
