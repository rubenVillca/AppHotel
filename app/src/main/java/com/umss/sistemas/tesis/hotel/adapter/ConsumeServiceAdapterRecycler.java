package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.model.ConsumeModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ConsumeServiceAdapterRecycler extends RecyclerView.Adapter<ConsumeServiceAdapterRecycler.ConsumeServiceViewHolder> {

    private ArrayList<ConsumeModel> consumeModelArray;
    private int resource;
    private Activity activity;

    public ConsumeServiceAdapterRecycler(ArrayList<ConsumeModel> consumeModel, int resource, Activity activity) {
        this.consumeModelArray = consumeModel;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ConsumeServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ConsumeServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConsumeServiceViewHolder holder, int position) {
        ConsumeModel consumeModel = consumeModelArray.get(position);

        holder.priceConsumeServiceCardView.setText(String.valueOf(consumeModel.getTypeMoney() + " " + consumeModel.getPrice()));
        holder.dateConsumeCardView.setText(consumeModel.getDateInConsum());
        holder.timeConsumeCardView.setText(consumeModel.getTimeInConsum());
        holder.serviceConsumeCardView.setText(consumeModel.getNameService());

        long dateIn = 0;
        long dateOut = 0;
        long dateToday = 0;
        try {
            SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date dateInParse = parseador.parse(consumeModel.getDateInConsum() + " " + consumeModel.getTimeInConsum());
            Date dateOutParse = parseador.parse(consumeModel.getDateOutConsum() + " " + consumeModel.getTimeOutConsum());
            Date date = new Date();
            dateIn = dateInParse.getTime();
            dateOut = dateOutParse.getTime();
            dateToday = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String state = "";
        int image = R.drawable.img_service_1;
        if (dateIn <= dateToday && dateToday <= dateOut) {
            state = "En proceso";
            image = R.drawable.ic_progress;
        }
        if (dateOut < dateToday) {
            state = "Consumido";
            image = R.drawable.ic_success;
        }
        if (dateIn > dateToday) {
            state = "Reserva";
            image = R.drawable.ic_reserve;
        }
        if (!consumeModel.isState()) {
            state = "Cancelado";
            image = R.drawable.ic_cancel;
        }
        holder.stateConsumeCardView.setText(String.valueOf(state));
        holder.imageConsumeCardView.setBackgroundResource(image);
    }

    @Override
    public int getItemCount() {
        return consumeModelArray.size();
    }

    class ConsumeServiceViewHolder extends RecyclerView.ViewHolder {

        private TextView priceConsumeServiceCardView;
        private TextView dateConsumeCardView;
        private TextView timeConsumeCardView;
        private TextView serviceConsumeCardView;
        private TextView stateConsumeCardView;
        private ImageView imageConsumeCardView;

        private ConsumeServiceViewHolder(View itemView) {
            super(itemView);

            priceConsumeServiceCardView = (TextView) itemView.findViewById(R.id.priceConsumeServiceCardView);
            dateConsumeCardView = (TextView) itemView.findViewById(R.id.dateConsumeServiceCardView);
            timeConsumeCardView = (TextView) itemView.findViewById(R.id.timeConsumeServiceCardView);
            serviceConsumeCardView = (TextView) itemView.findViewById(R.id.serviceConsumeServiceCardView);
            stateConsumeCardView = (TextView) itemView.findViewById(R.id.stateConsumeServiceCardView);
            imageConsumeCardView = (ImageView) itemView.findViewById(R.id.imageConsumeServiceCardView);
        }
    }
}
