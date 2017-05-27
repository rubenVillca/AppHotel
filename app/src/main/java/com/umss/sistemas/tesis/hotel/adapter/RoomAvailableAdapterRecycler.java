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
import com.umss.sistemas.tesis.hotel.model.RoomAvailableModel;

import java.util.ArrayList;

/**
 * Created by ruben on 23/05/2017
 */

public class RoomAvailableAdapterRecycler extends RecyclerView.Adapter<RoomAvailableAdapterRecycler.AvailableRoomViewHolder> {

    private ArrayList<RoomAvailableModel> availableModelArray;
    private int resource;
    private Activity activity;

    public RoomAvailableAdapterRecycler(ArrayList<RoomAvailableModel> roomAvailableModels, int resource, Activity activity) {
        this.availableModelArray = roomAvailableModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public RoomAvailableAdapterRecycler.AvailableRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new RoomAvailableAdapterRecycler.AvailableRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomAvailableAdapterRecycler.AvailableRoomViewHolder holder, int position) {
        RoomAvailableModel roomAvailableModel = availableModelArray.get(position);

        holder.nameTypeRoomCardView.setText(roomAvailableModel.getNameService());
        holder.priceCardView.setText(String.valueOf("Habitaciones Disponibles: "+roomAvailableModel.getUnitRoom()));

        String urlImage = Conexion.urlServer + roomAvailableModel.getImageTypeRoom();
        if (!urlImage.isEmpty())
            Picasso.with(activity).load(urlImage).into(holder.imageCardView);
    }

    @Override
    public int getItemCount() {
        return availableModelArray.size();
    }

    class AvailableRoomViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTypeRoomCardView;
        private TextView priceCardView;
        private ImageView imageCardView;

        private AvailableRoomViewHolder(View itemView) {
            super(itemView);

            nameTypeRoomCardView = (TextView) itemView.findViewById(R.id.nameTypeRoomCardView);
            priceCardView = (TextView) itemView.findViewById(R.id.nRoomFreeCardViewText);
            imageCardView = (ImageView) itemView.findViewById(R.id.imageAvailableRoomCardView);
        }
    }
}
