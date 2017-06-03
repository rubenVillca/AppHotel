package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;

public class ReserveListTypeRoomAdapterRecycler extends RecyclerView.Adapter<ReserveListTypeRoomAdapterRecycler.TypeRoomReserveViewHolder>{

    private ConsumeModel consumeModels;
    private int resource;
    private Activity activity;

    public ReserveListTypeRoomAdapterRecycler(ConsumeModel consumeModels, int resource, Activity activity) {
        this.consumeModels = consumeModels;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListRoomAdapterRecycler adapterRecycler = new ReserveListRoomAdapterRecycler(consumeModels, R.layout.cardview_reserve_list_room, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return consumeModels.getReserveModelArrayList().size();
    }

    class TypeRoomReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        private TypeRoomReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.roomReserveRecyclerView);
        }
    }
}
