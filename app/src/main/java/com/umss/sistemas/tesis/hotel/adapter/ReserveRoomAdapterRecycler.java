package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;

public class ReserveRoomAdapterRecycler extends RecyclerView.Adapter<ReserveRoomAdapterRecycler.RoomReserveViewHolder>{

    private ReserveModel reserveModel;
    private int resource;
    private Activity activity;

    public ReserveRoomAdapterRecycler(ReserveModel reserveModel, int resource, Activity activity) {
        this.reserveModel = reserveModel;
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
        //holder.consumeReservePriceRoom.setText(String.valueOf(consumeModel.getPrice()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveMemberAdapterRecycler adapterRecycler = new ReserveMemberAdapterRecycler(reserveModel, R.layout.cardview_reserve_member, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return reserveModel.getUnit();
    }

    class RoomReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        //TextView consumeReservePriceRoom;
        private RoomReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.memberReserveRecyclerView);
            //consumeReservePriceRoom=(TextView)itemView.findViewById(R.id.consumeReservePriceRoom);

        }
    }
}
