package com.umss.sistemas.tesis.hotel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;

import java.util.ArrayList;

public class ConsumeFoodAdapterRecycler extends RecyclerView.Adapter<ConsumeFoodAdapterRecycler.ConsumeFoodViewHolder> {

    private ArrayList<ConsumeFoodModel> consumeFoodModelArray;
    private int resource;

    public ConsumeFoodAdapterRecycler(ArrayList<ConsumeFoodModel> consumeFoodModel, int resource) {
        this.consumeFoodModelArray = consumeFoodModel;
        this.resource = resource;
    }

    @Override
    public ConsumeFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ConsumeFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumeFoodViewHolder holder, int position) {
        ConsumeFoodModel consumeModel = consumeFoodModelArray.get(position);

        holder.priceConsumeFoodCardView.setText(String.valueOf(consumeModel.getTypeMoney() + " " + consumeModel.getPrice()));
        holder.dateConsumeFoodCardView.setText(consumeModel.getDateConsume());
        holder.timeConsumeFoodCardView.setText(consumeModel.getTimeConsume());
        holder.nameConsumeFoodCardView.setText(consumeModel.getNameFood());
        holder.unitConsumeFoodCardView.setText(String.valueOf(consumeModel.getUnitFood()));
        holder.stateConsumeFoodCardView.setText(consumeModel.getState() == 1 ? "Entregado" : (consumeModel.getState() == 0 ? "Pedido" : "Cancelado"));
    }

    @Override
    public int getItemCount() {
        return consumeFoodModelArray.size();
    }

    class ConsumeFoodViewHolder extends RecyclerView.ViewHolder {

        private TextView priceConsumeFoodCardView;
        private TextView dateConsumeFoodCardView;
        private TextView timeConsumeFoodCardView;
        private TextView nameConsumeFoodCardView;
        private TextView stateConsumeFoodCardView;
        private TextView unitConsumeFoodCardView;

        private ConsumeFoodViewHolder(View itemView) {
            super(itemView);

            priceConsumeFoodCardView = (TextView) itemView.findViewById(R.id.priceConsumeFoodCardView);
            dateConsumeFoodCardView = (TextView) itemView.findViewById(R.id.dateConsumeFoodCardView);
            timeConsumeFoodCardView = (TextView) itemView.findViewById(R.id.timeConsumeFoodCardView);
            nameConsumeFoodCardView = (TextView) itemView.findViewById(R.id.nameConsumeFoodCardView);
            stateConsumeFoodCardView = (TextView) itemView.findViewById(R.id.stateConsumeFoodCardView);
            unitConsumeFoodCardView = (TextView) itemView.findViewById(R.id.unitConsumeFoodCardView);
        }
    }
}
