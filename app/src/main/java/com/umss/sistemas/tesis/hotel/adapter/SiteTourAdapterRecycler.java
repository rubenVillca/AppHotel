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
import com.umss.sistemas.tesis.hotel.activity.SiteTourActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;

import java.util.ArrayList;

public class SiteTourAdapterRecycler extends RecyclerView.Adapter<SiteTourAdapterRecycler.SiteTourViewHolder>{

    private ArrayList<SiteTourModel> sitesTourModel;
    private int resource;
    private Activity activity;

    public SiteTourAdapterRecycler(ArrayList<SiteTourModel> sitesTourModel, int resource, Activity activity) {
        this.sitesTourModel = sitesTourModel;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public SiteTourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new SiteTourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteTourViewHolder holder, int position) {
        final SiteTourModel siteTour=sitesTourModel.get(position);

        holder.siteTourNameCardView.setText(siteTour.getNameSite());
        holder.siteTourAddressCardView.setText(siteTour.getAddressSite());

        if (!siteTour.getImagesSite().isEmpty()) {
            String urlImage = Conexion.urlServer + siteTour.getImagesSite().get(0).getAddressSiteTour();
            Picasso.with(activity).load(urlImage).into(holder.siteTourPictureCardView);
        }
        holder.siteTourPictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, SiteTourActivity.class);
                intent.putExtra("idSiteTour",siteTour.getIdSite());

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
        return sitesTourModel.size();
    }

    class SiteTourViewHolder extends RecyclerView.ViewHolder{
        private ImageView siteTourPictureCardView;
        private TextView siteTourNameCardView;
        private TextView siteTourAddressCardView;

        private SiteTourViewHolder(View itemView) {
            super(itemView);

            siteTourPictureCardView= itemView.findViewById(R.id.imageSiteTourCardView);
            siteTourNameCardView= itemView.findViewById(R.id.nameSiteTourCardView);
            siteTourAddressCardView= itemView.findViewById(R.id.addressSiteTourCardView);
        }
    }
}
