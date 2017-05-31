package com.umss.sistemas.tesis.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.activity.ReserveTargetActivity;
import com.umss.sistemas.tesis.hotel.model.PriceServiceModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReserveSelectPriceAdapterRecycler extends RecyclerView.Adapter<ReserveSelectPriceAdapterRecycler.PriceServiceViewHolder> {

    private ArrayList<PriceServiceModel> priceServiceModels;
    private int resource;
    private Activity activity;
    private int nAdult;
    private int nBoy;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private int unitRoomFree;

    public ReserveSelectPriceAdapterRecycler(ArrayList<PriceServiceModel> priceService, int resource, Activity activity, int nAdult, int nBoy, String dateIn, String timeIn, String dateOut, String timeOut, int unitRoom) {
        this.priceServiceModels = priceService;
        this.resource = resource;
        this.activity = activity;
        this.nAdult = nAdult;
        this.nBoy = nBoy;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.unitRoomFree = unitRoom;
    }

    @Override
    public PriceServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PriceServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PriceServiceViewHolder holder, int position) {
        int timeSelected = getTimeSelected();

        final PriceServiceModel priceServiceModel = priceServiceModels.get(position);

        holder.priceNameCardView.setText(String.valueOf(priceServiceModel.getPriceService() + " " + priceServiceModel.getNameTypeMoney()));

        int timeUnit = priceServiceModel.getUnitHour() + priceServiceModel.getUnitDay() * 24;
        holder.timeUnitCardView.setText(String.valueOf(timeUnit + " Horas"));

        holder.timeSelectedCardView.setText(String.valueOf(timeSelected + " Horas"));
        double priceSelect=timeSelected * priceServiceModel.getPriceService() / timeUnit;
        holder.priceEstimatedCardView.setText(String.valueOf(priceSelect+" "+priceServiceModel.getNameTypeMoney()));

        String valores[] = new String[unitRoomFree];
        for (int i = 0; i < unitRoomFree; i++) {
            valores[i] = String.valueOf(i + 1);
        }
        holder.spinnerCardView.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores));
        holder.spinnerCardView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holder.priceEstimatedCardView.setText(String.valueOf((position+1)*priceServiceModel.getPriceService()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.buttonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ReserveTargetActivity.class);

                intent.putExtra("priceServiceModel", priceServiceModel);
                intent.putExtra("nRoom",String.valueOf(holder.spinnerCardView.getSelectedItemPosition()+1));
                intent.putExtra("nAdult", nAdult);
                intent.putExtra("nBoy", nBoy);
                intent.putExtra("dateIn", dateIn);
                intent.putExtra("timeIn", timeIn);
                intent.putExtra("dateOut", dateOut);
                intent.putExtra("timeOut", timeOut);

                activity.startActivity(intent);
            }
        });
    }

    /**
     * obnter el tiempo de la reserva convertido en horas
     * @return int:tiempo de estadia en horas
     */
    private int getTimeSelected() {
        int timeSelect = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm");
            Date dateInFormat = formatter.parse(dateIn + " " + timeIn);
            Date dateOutFormat = formatter.parse(dateOut + " " + timeOut);

            long timeS = dateOutFormat.getTime() - dateInFormat.getTime();
            timeSelect = (int) (timeS / (1000 * 60 * 60));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return timeSelect;
    }

    @Override
    public int getItemCount() {
        return priceServiceModels.size();
    }

    class PriceServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView priceNameCardView;
        private TextView priceEstimatedCardView;
        private TextView timeUnitCardView;
        private TextView timeSelectedCardView;
        private Spinner spinnerCardView;
        private Button buttonCardView;

        private PriceServiceViewHolder(View itemView) {
            super(itemView);

            priceNameCardView = (TextView) itemView.findViewById(R.id.priceUnitPriceServiceCardView);
            priceEstimatedCardView = (TextView) itemView.findViewById(R.id.priceEstimadedPriceServiceCardView);
            timeUnitCardView = (TextView) itemView.findViewById(R.id.timeUnitPriceServiceCardView);
            timeSelectedCardView = (TextView) itemView.findViewById(R.id.timeSelectedPriceServiceCardView);
            spinnerCardView = (Spinner) itemView.findViewById(R.id.nRoomPriceServiceCardView);
            buttonCardView=(Button)itemView.findViewById(R.id.btnPriceServiceCardView);
        }
    }
}
