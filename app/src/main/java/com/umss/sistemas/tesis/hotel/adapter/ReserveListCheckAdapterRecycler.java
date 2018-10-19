package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveResultActivity;
import com.umss.sistemas.tesis.hotel.activity.ReserveSearchActivity;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceHelper;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

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
    public void onBindViewHolder(CheckReserveViewHolder holder, final int position) {
        final CheckModel checkModel = checkModels.get(position);
        ServiceHelper serviceHelper =new ServiceHelper(activity);
        ArrayList<ConsumeServiceModel> consumeServiceModels=checkModel.getConsumeServiceModelArrayList();

        if (!consumeServiceModels.isEmpty()) {
            ArrayList<ServiceModel> serviceModelArrayList = serviceHelper.getServiceModel(consumeServiceModels.get(0).getIdKeyService());
            ServiceModel serviceModel = serviceModelArrayList.get(0);

            Picasso.with(activity).load(Conexion.urlServer + serviceModel.getImage()).into(holder.imageReserveList);
        }else{
            System.out.println("Error en la BD");
        }
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
                if (checkModel.getId()>0) {
                    Intent intent = new Intent(activity, ReserveResultActivity.class);
                    intent.putExtra("checkModel", checkModel);
                    intent.putExtra("isMember",false);
                    intent.putExtra("nAdult", checkModel.getConsumeServiceModelArrayList().get(0).getMemberModelArrayList().size());
                    intent.putExtra("nBoy", 0);
                    intent.putExtra("dateIn", checkModel.getDateIn());
                    intent.putExtra("timeIn", checkModel.getTimeIn());
                    intent.putExtra("dateOut", checkModel.getDateEnd());
                    intent.putExtra("timeOut", checkModel.getTimeEnd());

                    activity.startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, ReserveSearchActivity.class);
                    intent.putExtra("checkModel", checkModel);
                    activity.startActivity(intent);
                }
            }
        });

        holder.btnCancelReserveCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
                alertBuilder.setMessage("¿Cancelar reserva?");
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton(
                        "Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("android", "android");
                                params.put("idCheck", checkModel.getId());

                                client.post(Conexion.RESERVE_CANCEL, params, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        if (statusCode == 200) {
                                            try {
                                                JSONObject obj = new JSONObject(new String(responseBody));
                                                int i=0;
                                                for (CheckModel check: checkModels){
                                                    if (check.getId()==checkModel.getId()){
                                                        checkModels.remove(i);
                                                        notifyItemRemoved(i);
                                                        break;
                                                    }
                                                    i++;
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Toast.makeText(activity.getApplicationContext(), "Reserva eliminada", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(activity.getApplicationContext(), "Error en la respuesta del servidor", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(activity.getApplicationContext(), "No se detecta conexion de red", Toast.LENGTH_LONG).show();
                                    }
                                });
                                dialog.cancel();
                            }
                        });

                alertBuilder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertSave2 = alertBuilder.create();
                alertSave2.show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        ReserveListConsumeAdapterRecycler listConsumeAdapterRecycler = new ReserveListConsumeAdapterRecycler(checkModel.getConsumeServiceModelArrayList(), R.layout.cardview_reserve_list_consume, activity);
        holder.recyclerView.setAdapter(listConsumeAdapterRecycler);

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
        LinearLayout btnPlusReserveCardView;
        LinearLayout btnCancelReserveCardView;
        ImageView imageReserveList;

        private CheckReserveViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.consumeReserveRecyclerView);
            imageReserveList= itemView.findViewById(R.id.imageReserveList);
            checkReserveInTextView = itemView.findViewById(R.id.checkReserveInTextView);
            checkReserveOutTextView = itemView.findViewById(R.id.checkReserveOutTextView);
            checkReserveCostTotal = itemView.findViewById(R.id.checkReserveCostTotal);
            checkReserveDeposit = itemView.findViewById(R.id.checkReserveDeposit);
            checkReserveVerifyTarget = itemView.findViewById(R.id.checkReserveVerifyTarget);
            checkReserveState = itemView.findViewById(R.id.checkReserveState);
            btnPlusReserveCardView = itemView.findViewById(R.id.btnPlusReserveCardView);
            btnCancelReserveCardView = itemView.findViewById(R.id.btnCancelReserveCardView);
        }
    }
}
