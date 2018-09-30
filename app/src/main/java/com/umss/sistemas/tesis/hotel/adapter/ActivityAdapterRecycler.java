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
import com.umss.sistemas.tesis.hotel.model.CalendarModel;

import java.util.ArrayList;

public class ActivityAdapterRecycler extends RecyclerView.Adapter<ActivityAdapterRecycler.ActivityViewHolder> {

    private ArrayList<CalendarModel> calendarModelArray;
    private int resource;
    private Activity activity;

    public ActivityAdapterRecycler(ArrayList<CalendarModel> activitiesImage, int resource, Activity activity) {
        this.calendarModelArray = activitiesImage;
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
        CalendarModel calendarModel = calendarModelArray.get(position);

        holder.nameCardView.setText(calendarModel.getName());
        holder.dateStartCardView.setText(calendarModel.getDateStart());
        holder.timeStartCardView.setText(calendarModel.getTimeStart());
        holder.dateEndCardView.setText(calendarModel.getDateEnd());
        holder.timeEndCardView.setText(calendarModel.getTimeEnd());
        holder.descriptionCardView.setText(calendarModel.getDescription());

        String urlImage = Conexion.urlServer + calendarModel.getImage();
        if (!urlImage.isEmpty())
            Picasso.with(activity).load(urlImage).into(holder.imageCardView);
    }

    @Override
    public int getItemCount() {
        return calendarModelArray.size();
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
