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
import com.umss.sistemas.tesis.hotel.model.Picture;

import java.util.ArrayList;

public class HomeAdapterRecycler extends RecyclerView.Adapter<HomeAdapterRecycler.PictureViewHolder>{

    private ArrayList<Picture> pictures;
    private int resource;
    private Activity activity;

    public HomeAdapterRecycler(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Picture picture=pictures.get(position);

        holder.userNameCard.setText(picture.getUserName());
        holder.timeCard.setText(picture.getLikeNumber());
        holder.likeNumberCard.setText(picture.getLikeNumber());

        Picasso.with(activity).load(picture.getPicture()).into(holder.pictureCard);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder{
        private ImageView pictureCard;
        private TextView userNameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(View itemView) {
            super(itemView);

            pictureCard=(ImageView)itemView.findViewById(R.id.imageService);
            userNameCard=(TextView)itemView.findViewById(R.id.nameServiceCard);
            timeCard=(TextView)itemView.findViewById(R.id.timeCardviewService);
            likeNumberCard=(TextView)itemView.findViewById(R.id.likeCheckCard);
        }
    }
}
