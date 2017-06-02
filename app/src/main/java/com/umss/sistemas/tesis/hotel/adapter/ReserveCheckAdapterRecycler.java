package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveSearchActivity;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;

import java.util.ArrayList;

public class ReserveCheckAdapterRecycler extends RecyclerView.Adapter<ReserveCheckAdapterRecycler.CheckReserveViewHolder> {

    private ArrayList<CheckModel> checkModels;
    private int resource;
    private Activity activity;

    public ReserveCheckAdapterRecycler(ArrayList<CheckModel> checkModels, int resource, Activity activity) {
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

        holder.checkReserveInTextView.setText(checkModel.getDateIn()+" "+checkModel.getTimeIn());
        holder.checkReserveOutTextView.setText(checkModel.getDateEnd()+" "+checkModel.getTimeEnd());
        double priceTotal=0;
        double depositTotal=0;
        String typeMoney="$";
        for (ConsumeModel consumeModel:checkModel.getConsumeModelArrayList()){
            priceTotal+=consumeModel.getPrice();
            depositTotal+=consumeModel.getPay();
            typeMoney=consumeModel.getTypeMoney();
        }

        holder.checkReserveCostTotal.setText(String.valueOf(typeMoney+" "+priceTotal));
        holder.checkReserveDeposit.setText(String.valueOf(typeMoney+""+depositTotal));
        holder.btnPlusReserveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ReserveSearchActivity.class);
                intent.putExtra("idCheck",checkModel.getId());
                activity.startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveConsumeAdapterRecycler siteTourAdapter = new ReserveConsumeAdapterRecycler(checkModel.getConsumeModelArrayList(), R.layout.cardview_reserve_consume, activity);
        holder.recyclerView.setAdapter(siteTourAdapter);
    }

    @Override
    public int getItemCount() {
        return checkModels.size();
    }

    class CheckReserveViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView checkReserveInTextView;
        TextView checkReserveOutTextView;
        TextView checkReserveCostTotal;
        TextView checkReserveDeposit;
        ImageView btnPlusReserveCardView;
        private CheckReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.consumeReserveRecyclerView);
            checkReserveInTextView=(TextView)itemView.findViewById(R.id.checkReserveInTextView);
            checkReserveOutTextView=(TextView)itemView.findViewById(R.id.checkReserveOutTextView);
            checkReserveCostTotal=(TextView)itemView.findViewById(R.id.checkReserveCostTotal);
            checkReserveDeposit=(TextView)itemView.findViewById(R.id.checkReserveDeposit);
            btnPlusReserveCardView=(ImageView)itemView.findViewById(R.id.btnPlusReserveCardView);
        }
    }
}
