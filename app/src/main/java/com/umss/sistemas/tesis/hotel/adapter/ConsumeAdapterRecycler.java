package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;

import java.util.ArrayList;

public class ConsumeAdapterRecycler extends RecyclerView.Adapter<ConsumeAdapterRecycler.ConsumeViewHolder> {

    private ArrayList<ConsumeModel> consumeModelArray;
    private int resource;
    private Activity activity;

    public ConsumeAdapterRecycler(ArrayList<ConsumeModel> consumeModel, int resource, Activity activity) {
        this.consumeModelArray = consumeModel;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ConsumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ConsumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumeViewHolder holder, int position) {
        ConsumeModel consumeModel = consumeModelArray.get(position);

        holder.priceCardView.setText(String.valueOf(consumeModel.getPrice()));
        holder.dateConsumeCardView.setText(consumeModel.getDateInConsum());
        holder.timeConsumeCardView.setText(consumeModel.getTimeInConsum());
        //holder.serviceConsumeCardView.setText(consumeModel.getTimeStart());

    }

    @Override
    public int getItemCount() {
        return consumeModelArray.size();
    }

    class ConsumeViewHolder extends RecyclerView.ViewHolder {

        private TextView priceCardView;
        private TextView dateConsumeCardView;
        private TextView timeConsumeCardView;
        private TextView serviceConsumeCardView;

        private ConsumeViewHolder(View itemView) {
            super(itemView);

            priceCardView = (TextView) itemView.findViewById(R.id.priceConsumeCardView);
            dateConsumeCardView = (TextView) itemView.findViewById(R.id.dateConsumeCardView);
            timeConsumeCardView = (TextView) itemView.findViewById(R.id.timeConsumeCardView);
            serviceConsumeCardView = (TextView) itemView.findViewById(R.id.serviceConsumeCardView);
        }
    }
}
