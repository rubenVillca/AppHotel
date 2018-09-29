package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveSearchActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.Services;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;

import java.util.ArrayList;

public class ReserveListCheckAdapterRecycler extends RecyclerView.Adapter<ReserveListCheckAdapterRecycler.CheckReserveViewHolder> {
    private final static int FADE_DURATION = 1000; // in milliseconds
    private ArrayList<CheckModel> checkModels;
    private int resource;
    private Activity activity;

    public ReserveListCheckAdapterRecycler(ArrayList<CheckModel> checkModels, int resource, Activity activity) {
        this.checkModels = checkModels;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public CheckReserveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new CheckReserveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckReserveViewHolder holder, int position) {
        final CheckModel checkModel = checkModels.get(position);
        Services services =new Services(activity);
        ArrayList<ServiceModel> serviceModelArrayList= services.getServiceModel(checkModel.getConsumeServiceModelArrayList().get(0).getIdKeyService());
        ServiceModel serviceModel = serviceModelArrayList.get(0);

        Picasso.with(activity).load(Conexion.urlServer+serviceModel.getImage()).into(holder.imageReserveList);

        holder.checkReserveInTextView.setText(checkModel.getDateIn() + " " + checkModel.getTimeIn());
        holder.checkReserveOutTextView.setText(checkModel.getDateEnd() + " " + checkModel.getTimeEnd());
        boolean isVerify=false;
        for (CardModel cardModel: checkModel.getCardTargetArrayList()) {
            isVerify=cardModel.isValid();
        }

        holder.checkReserveVerifyTarget.setText(isVerify?"Verificado":"Pendiente");
        holder.checkReserveState.setText(checkModel.getNameState());
        double priceTotal = 0;
        double depositTotal = 0;
        String typeMoney = "$";
        for (ConsumeServiceModel consumeServiceModel : checkModel.getConsumeServiceModelArrayList()) {
            priceTotal += consumeServiceModel.getPrice();
            depositTotal += consumeServiceModel.getPay();
            typeMoney = consumeServiceModel.getTypeMoney();
        }

        holder.checkReserveCostTotal.setText(String.valueOf(typeMoney + " " + priceTotal));
        holder.checkReserveDeposit.setText(String.valueOf(typeMoney + " " + depositTotal));
        holder.btnPlusReserveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReserveSearchActivity.class);
                intent.putExtra("checkModel", checkModel);
                activity.startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListConsumeAdapterRecycler siteTourAdapter = new ReserveListConsumeAdapterRecycler(checkModel.getConsumeServiceModelArrayList(), R.layout.cardview_reserve_list_consume, activity);
        holder.recyclerView.setAdapter(siteTourAdapter);

        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return checkModels.size();
    }

    class CheckReserveViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView checkReserveInTextView;
        TextView checkReserveOutTextView;
        TextView checkReserveCostTotal;
        TextView checkReserveDeposit;
        TextView checkReserveVerifyTarget;
        TextView checkReserveState;
        ImageView btnPlusReserveCardView;
        ImageView imageReserveList;

        private CheckReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.consumeReserveRecyclerView);
            imageReserveList=(ImageView)itemView.findViewById(R.id.imageReserveList);
            checkReserveInTextView = (TextView) itemView.findViewById(R.id.checkReserveInTextView);
            checkReserveOutTextView = (TextView) itemView.findViewById(R.id.checkReserveOutTextView);
            checkReserveCostTotal = (TextView) itemView.findViewById(R.id.checkReserveCostTotal);
            checkReserveDeposit = (TextView) itemView.findViewById(R.id.checkReserveDeposit);
            checkReserveVerifyTarget = (TextView) itemView.findViewById(R.id.checkReserveVerifyTarget);
            checkReserveState = (TextView)itemView.findViewById(R.id.checkReserveState);
            btnPlusReserveCardView = (ImageView) itemView.findViewById(R.id.btnPlusReserveCardView);
        }
    }
}
