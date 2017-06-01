package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;

public class ReserveMemberAdapterRecycler extends RecyclerView.Adapter<ReserveMemberAdapterRecycler.MemberReserveViewHolder>{

    private ReserveModel reserveModel;
    private int resource;
    private Activity activity;

    public ReserveMemberAdapterRecycler(ReserveModel reserveModel, int resource, Activity activity) {
        this.reserveModel = reserveModel;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public MemberReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new MemberReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberReserveViewHolder holder, int position) {
        holder.memberReserveTextView.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return reserveModel.getnAdult()+reserveModel.getnBoy();
    }

    class MemberReserveViewHolder extends RecyclerView.ViewHolder{
        TextView memberReserveTextView;
        private MemberReserveViewHolder(View itemView) {
            super(itemView);
            memberReserveTextView=(TextView)itemView.findViewById(R.id.memberReserveTextView);
        }
    }
}
