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
import com.umss.sistemas.tesis.hotel.activity.OfferActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.OfferModel;

import java.util.ArrayList;

public class OfferAdapterRecycler extends RecyclerView.Adapter<OfferAdapterRecycler.OfferViewHolder>{

    private ArrayList<OfferModel> offers;
    private int resource;
    private Activity activity;

    public OfferAdapterRecycler(ArrayList<OfferModel> offers, int resource, Activity activity) {
        this.offers = offers;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {
        OfferModel offer=offers.get(position);
        final int idOffer= offer.getId();
        holder.offerNameCardView.setText(offer.getName());
        holder.offernameServiceCardView.setText(offer.getNameType());

        String urlImage = Conexion.urlServer+offers.get(position).getImage();
        Picasso.with(activity).load(urlImage).into(holder.offerPictureCardView);

        holder.offerPictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, OfferActivity.class);
                intent.putExtra("idOffer",idOffer);

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    Explode explode=new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity,v,activity.getString(R.string.transicionname_picture)).toBundle());
                }else{
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    class OfferViewHolder extends RecyclerView.ViewHolder{
        private ImageView offerPictureCardView;
        private TextView offerNameCardView;
        private TextView offernameServiceCardView;

        private OfferViewHolder(View itemView) {
            super(itemView);

            offerPictureCardView=(ImageView)itemView.findViewById(R.id.imageOfferCardView);
            offerNameCardView=(TextView)itemView.findViewById(R.id.nameOfferCardView);
            offernameServiceCardView=(TextView)itemView.findViewById(R.id.nameTypeOfferCardView);
        }
    }
}
