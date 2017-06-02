package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;

import java.util.ArrayList;

public class ReserveListConsumeAdapterRecycler extends RecyclerView.Adapter<ReserveListConsumeAdapterRecycler.ConsumeReserveViewHolder>{

    private ArrayList<ConsumeModel> consumeModels;
    private int resource;
    private Activity activity;

    public ReserveListConsumeAdapterRecycler(ArrayList<ConsumeModel> consumeModels, int resource, Activity activity) {
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

        holder.nameServiceReserveConsume.setText(String.valueOf(consumeModel.getNameService()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListTypeRoomAdapterRecycler adapterRecycler = new ReserveListTypeRoomAdapterRecycler(consumeModel.getReserveModelArrayList(), R.layout.cardview_reserve_list_type_room, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return consumeModels.size();
    }

    class ConsumeReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView nameServiceReserveConsume;
        private ConsumeReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.typeRoomReserveRecyclerView);
            nameServiceReserveConsume=(TextView)itemView.findViewById(R.id.nameServiceReserveConsume);

        }
    }
}
