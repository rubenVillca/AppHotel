package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;

public class ReserveListTypeRoomAdapterRecycler extends RecyclerView.Adapter<ReserveListTypeRoomAdapterRecycler.TypeRoomReserveViewHolder>{

    private ConsumeServiceModel consumeServiceModels;
    private int resource;
    private Activity activity;

    public ReserveListTypeRoomAdapterRecycler(ConsumeServiceModel consumeServiceModels, int resource, Activity activity) {
        this.consumeServiceModels = consumeServiceModels;
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
        ReserveListRoomAdapterRecycler adapterRecycler = new ReserveListRoomAdapterRecycler(consumeServiceModels, R.layout.cardview_reserve_list_room, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return consumeServiceModels.getReserveModelArrayList().size();
    }

    class TypeRoomReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        private TypeRoomReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.roomReserveRecyclerView);
        }
    }
}
