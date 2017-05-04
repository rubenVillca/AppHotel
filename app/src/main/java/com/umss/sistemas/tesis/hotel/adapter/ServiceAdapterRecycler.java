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
import com.umss.sistemas.tesis.hotel.activity.ServiceDetailActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;

import java.util.ArrayList;

public class ServiceAdapterRecycler extends RecyclerView.Adapter<ServiceAdapterRecycler.ServiceViewHolder>{

    private ArrayList<ServiceModel> servicesImage;
    private int resource;
    private Activity activity;

    public ServiceAdapterRecycler(ArrayList<ServiceModel> servicesImage, int resource, Activity activity) {
        this.servicesImage = servicesImage;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        ServiceModel service=servicesImage.get(position);
        final int idService=service.getServiceId();
        holder.serviceNameCardView.setText(service.getServiceName());
        holder.serviceTypeCardView.setText(service.getServiceType());

        String urlImage = Conexion.urlServer+servicesImage.get(position).getServiceImage();
        Picasso.with(activity).load(urlImage).into(holder.servicePictureCardView);

        holder.servicePictureCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ServiceDetailActivity.class);
                intent.putExtra("idService",idService);

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
        return servicesImage.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{
        private ImageView servicePictureCardView;
        private TextView serviceNameCardView;
        private TextView serviceTypeCardView;

        private ServiceViewHolder(View itemView) {
            super(itemView);

            servicePictureCardView=(ImageView)itemView.findViewById(R.id.imageServiceCardView);
            serviceNameCardView=(TextView)itemView.findViewById(R.id.nameServiceCardView);
            serviceTypeCardView=(TextView)itemView.findViewById(R.id.typeServiceCardView);
        }
    }
}
