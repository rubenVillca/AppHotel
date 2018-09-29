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
import com.umss.sistemas.tesis.hotel.model.ActivityModel;

import java.util.ArrayList;

public class ActivityAdapterRecycler extends RecyclerView.Adapter<ActivityAdapterRecycler.ActivityViewHolder> {

    private ArrayList<ActivityModel> activityModelArray;
    private int resource;
    private Activity activity;

    public ActivityAdapterRecycler(ArrayList<ActivityModel> activitiesImage, int resource, Activity activity) {
        this.activityModelArray = activitiesImage;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityModel activityModel = activityModelArray.get(position);

        holder.nameCardView.setText(activityModel.getName());
        holder.dateStartCardView.setText(activityModel.getDateStart());
        holder.timeStartCardView.setText(activityModel.getTimeStart());
        holder.dateEndCardView.setText(activityModel.getDateEnd());
        holder.timeEndCardView.setText(activityModel.getTimeEnd());
        holder.descriptionCardView.setText(activityModel.getDescription());

        String urlImage = Conexion.urlServer + activityModel.getImage();
        if (!urlImage.isEmpty())
            Picasso.with(activity).load(urlImage).into(holder.imageCardView);
    }

    @Override
    public int getItemCount() {
        return activityModelArray.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {

        private TextView nameCardView;
        private TextView dateStartCardView;
        private TextView timeStartCardView;
        private TextView dateEndCardView;
        private TextView timeEndCardView;
        private TextView descriptionCardView;
        private ImageView imageCardView;

        private ActivityViewHolder(View itemView) {
            super(itemView);

            nameCardView = itemView.findViewById(R.id.nameActivityCardView);
            dateStartCardView = itemView.findViewById(R.id.dateStartActivityCardViewText);
            timeStartCardView = itemView.findViewById(R.id.timeStartActivityCardViewText);
            dateEndCardView = itemView.findViewById(R.id.dateEndActivityCardViewText);
            timeEndCardView = itemView.findViewById(R.id.timeEndActivityCardViewText);
            descriptionCardView = itemView.findViewById(R.id.descriptionActivityCardViewText);
            imageCardView = itemView.findViewById(R.id.imageActivityCardView);
        }
    }
}
