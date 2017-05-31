package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveSelectedActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;

import java.util.ArrayList;

/**
 * Created by ruben on 23/05/2017
 */

public class ReserveSearchAdapterRecycler extends RecyclerView.Adapter<ReserveSearchAdapterRecycler.AvailableRoomViewHolder> {

    private ArrayList<ReserveSearchModel> availableModelArray;
    private int resource;
    private Activity activity;
    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;

    public ReserveSearchAdapterRecycler(ArrayList<ReserveSearchModel> reserveSearchModels, int resource, Activity activity, int nAdult, int nBoy, String dateIn, String timeIn, String dateOut, String timeOut) {
        this.availableModelArray = reserveSearchModels;
        this.resource = resource;
        this.activity = activity;
        this.nAdult = nAdult;
        this.nBoy = nBoy;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    @Override
    public ReserveSearchAdapterRecycler.AvailableRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ReserveSearchAdapterRecycler.AvailableRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReserveSearchAdapterRecycler.AvailableRoomViewHolder holder, int position) {
        final ReserveSearchModel reserveSearchModel = availableModelArray.get(position);

        holder.nameTypeRoomCardView.setText(reserveSearchModel.getNameService());
        holder.priceCardView.setText(String.valueOf("Habitaciones Disponibles: " + reserveSearchModel.getUnitRoom()));

        String urlImage = Conexion.urlServer + reserveSearchModel.getImageTypeRoom();
        if (!urlImage.isEmpty())
            Picasso.with(activity).load(urlImage).into(holder.imageCardView);

        holder.imageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReserveSelectedActivity.class);

                intent.putExtra("reserveSearchModel", reserveSearchModel);
                intent.putExtra("nAdult", nAdult);
                intent.putExtra("nBoy", nBoy);
                intent.putExtra("dateIn", dateIn);
                intent.putExtra("timeIn", timeIn);
                intent.putExtra("dateOut", dateOut);
                intent.putExtra("timeOut", timeOut);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, v, activity.getString(R.string.transicionname_picture)).toBundle());
                } else {
                    activity.startActivity(intent);
                }
            }
        });
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
