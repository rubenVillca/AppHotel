package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;

public class ReserveListRoomAdapterRecycler extends RecyclerView.Adapter<ReserveListRoomAdapterRecycler.RoomReserveViewHolder>{

    private ConsumeServiceModel consumeServiceModel;
    private int resource;
    private Activity activity;

    public ReserveListRoomAdapterRecycler(ConsumeServiceModel consumeServiceModel, int resource, Activity activity) {
        this.consumeServiceModel = consumeServiceModel;
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
        holder.consumeReserveRoom.setText(consumeServiceModel.getNameService());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListMemberAdapterRecycler adapterRecycler = new ReserveListMemberAdapterRecycler(consumeServiceModel, R.layout.cardview_reserve_list_member, activity);
        holder.recyclerView.setAdapter(adapterRecycler);
    }

    @Override
    public int getItemCount() {
        return consumeServiceModel.getReserveModelArrayList().size();
    }

    class RoomReserveViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView consumeReserveRoom;
        private RoomReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView= itemView.findViewById(R.id.memberReserveRecyclerView);
            consumeReserveRoom= itemView.findViewById(R.id.nameReserveTypeRoom);
        }
    }
}
