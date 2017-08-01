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
import com.umss.sistemas.tesis.hotel.model.ServicePriceConsumeModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReserveSelectPriceAdapterRecycler extends RecyclerView.Adapter<ReserveSelectPriceAdapterRecycler.PriceServiceViewHolder> {

    private ArrayList<ServicePriceConsumeModel> servicePriceConsumeModels;
    private int resource;
    private Activity activity;
    private int idCheck;
    private int nAdult;
    private int nBoy;
    private boolean isMember;
    private String dateIn;
    private String dateOut;
    private String timeIn;
    private String timeOut;
    private int unitRoomFree;
    private int idTypeRoom;

    public ReserveSelectPriceAdapterRecycler(ArrayList<ServicePriceConsumeModel> priceService, int resource, Activity activity, int nAdult, int nBoy, String dateIn, String timeIn, String dateOut, String timeOut, int unitRoom, int idTypeRoom, boolean isMember, int idCheck) {
        this.servicePriceConsumeModels = priceService;
        this.resource = resource;
        this.activity = activity;
        this.nAdult = nAdult;
        this.nBoy = nBoy;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.unitRoomFree = unitRoom;
        this.idTypeRoom = idTypeRoom;
        this.isMember = isMember;
        this.idCheck = idCheck;
    }

    @Override
    public PriceServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PriceServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PriceServiceViewHolder holder, int position) {
        int timeSelected = getTimeSelected();

        final ServicePriceConsumeModel servicePriceConsumeModel = servicePriceConsumeModels.get(position);

        holder.priceNameCardView.setText(String.valueOf(servicePriceConsumeModel.getPriceService() + " " + servicePriceConsumeModel.getNameTypeMoney()));

        int timeUnit = servicePriceConsumeModel.getUnitHour() + servicePriceConsumeModel.getUnitDay() * 24;
        holder.timeUnitCardView.setText(String.valueOf(timeUnit + " Horas"));

        String resTimeDayText=timeSelected/24>0?timeSelected/24 +" Días":"";
        String resTimeHour=timeSelected%24>0?timeSelected%24+" Horas":"";
        holder.timeSelectedCardView.setText(String.valueOf(resTimeDayText + (resTimeHour.isEmpty()?"":" ,"+resTimeHour)));
        final double priceSelect = timeSelected * servicePriceConsumeModel.getPriceService() / timeUnit;
        holder.priceEstimatedCardView.setText(String.valueOf(priceSelect + " " + servicePriceConsumeModel.getNameTypeMoney()));

        String valores[] = new String[unitRoomFree];
        for (int i = 0; i < unitRoomFree; i++) {
            valores[i] = String.valueOf(i + 1);
        }
        holder.spinnerCardView.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores));
        holder.spinnerCardView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionSpin, long id) {
                holder.priceEstimatedCardView.setText(String.valueOf((positionSpin + 1) * servicePriceConsumeModel.getPriceService()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (idCheck>0)
            holder.buttonCardView.setText(String.valueOf("Agregar"));
        holder.buttonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReserveTargetActivity.class);

                intent.putExtra("idCheck", idCheck);
                intent.putExtra("isMember", isMember);
                intent.putExtra("servicePriceConsumeModel", servicePriceConsumeModel);
                intent.putExtra("idTypeRoom", String.valueOf(idTypeRoom));
                intent.putExtra("nRoom", String.valueOf(holder.spinnerCardView.getSelectedItem().toString()));
                intent.putExtra("nAdult", String.valueOf(nAdult));
                intent.putExtra("nBoy", String.valueOf(nBoy));
                intent.putExtra("priceEstimated", String.valueOf(priceSelect));
                intent.putExtra("timeIn", String.valueOf(timeIn));
                intent.putExtra("timeOut", String.valueOf(timeOut));

                try {
                    SimpleDateFormat parseador = new SimpleDateFormat("MMM dd, yyyy");
                    SimpleDateFormat formateador = new SimpleDateFormat("yy/MM/dd");

                    Date dateInParse = parseador.parse(dateIn);
                    intent.putExtra("dateIn", formateador.format(dateInParse));

                    Date dateOutParse = parseador.parse(dateOut);
                    intent.putExtra("dateOut", formateador.format(dateOutParse));
                } catch (ParseException e) {
                    intent.putExtra("dateIn", dateIn);
                    intent.putExtra("dateOut", dateOut);
                }
                activity.startActivity(intent);
            }
        });
    }

    /**
     * obnter el tiempo de la reserva convertido en horas
     *
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
        return servicePriceConsumeModels.size();
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
            buttonCardView = (Button) itemView.findViewById(R.id.btnPriceServiceCardView);
        }
    }
}
