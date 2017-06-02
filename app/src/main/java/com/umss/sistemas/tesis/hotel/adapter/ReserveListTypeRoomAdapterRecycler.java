package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;

import java.util.ArrayList;

public class ReserveListTypeRoomAdapterRecycler extends RecyclerView.Adapter<ReserveListTypeRoomAdapterRecycler.TypeRoomReserveViewHolder>{

    private ArrayList<ReserveModel> reserveModels;
    private int resource;
    private Activity activity;

    public ReserveListTypeRoomAdapterRecycler(ArrayList<ReserveModel> reserveModels, int resource, Activity activity) {
        this.reserveModels = reserveModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public TypeRoomReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new TypeRoomReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TypeRoomReserveViewHolder holder, int position) {
        ReserveModel reserveModel=reserveModels.get(position);
        holder.consumeReserveRoom.setText(reserveModel.getNameRoomModel());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListRoomAdapterRecycler adapterRecycler = new ReserveListRoomAdapterRecycler(reserveModel, R.layout.cardview_reserve_list_room, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return reserveModels.size();
    }

    class TypeRoomReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView consumeReserveRoom;
        private TypeRoomReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.roomReserveRecyclerView);
            consumeReserveRoom=(TextView)itemView.findViewById(R.id.nameReserveTypeRoom);
        }
    }
}
