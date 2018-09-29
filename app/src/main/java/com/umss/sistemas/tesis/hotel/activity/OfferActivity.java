package com.umss.sistemas.tesis.hotel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.ServiceGet;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OfferActivity extends ActivityParent {
    private OfferModel offerModel;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        super.showToolBar("",true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        int idOffer = getIdOffer();
        initValues(idOffer);
        chargeContent();
    }

    private void initValues(int idOffer) {
        fab = findViewById(R.id.fabOrderService);
        serviceGet =new ServiceGet(this);
        offerModel= serviceGet.getOfferModel(idOffer).get(0);
        boolean isChecked= serviceGet.getCheckModel(0,1,2).size()>0;

        //varificar si la fecha de la oferta es valida para utilizarla
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date dateToday=new Date();
        try {
            Date dateInit=simpleDateFormat.parse(offerModel.getDateIni());
            Date dateFinish=simpleDateFormat.parse(offerModel.getDateFin());

            if (!isChecked||(dateToday.before(dateInit))||dateFinish.before(dateToday))
                fab.setVisibility(View.GONE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private int getIdOffer() {
        Bundle bundle=getIntent().getExtras();
        int res=0;
        if (bundle != null) {
            res= bundle.getInt("idOffer");
        }
        return res;
    }

    private void chargeContent() {
        ServicePriceDetailModel listPrice =offerModel.getServicePriceDetailModel().get(0);

        ImageView imageOffer= findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer + offerModel.getImage()).into(imageOffer);

        TextView nameOffer= findViewById(R.id.nameOfferTextView);
        nameOffer.setText(offerModel.getName());

        TextView typeOffer= findViewById(R.id.nameTypeOfferTextView);
        typeOffer.setText(offerModel.getNameType());

        TextView descriptionOffer= findViewById(R.id.descriptionOfferTextView);
        descriptionOffer.setText(android.text.Html.fromHtml(offerModel.getDescription()).toString());

        TextView typeMoney= findViewById(R.id.typeMoneyOfferTextView);
        typeMoney.setText(listPrice.getServicePriceNameMoney());

        TextView priceOffer= findViewById(R.id.priceOfferTextView);
        priceOffer.setText(String.valueOf(listPrice.getServicePricePrice()));

        TextView timeOffer= findViewById(R.id.timeOfferTextView);
        String hourTotal=String.valueOf(listPrice.getServicePriceHour()+listPrice.getServicePriceDay()*24)+" Horas";
        timeOffer.setText(hourTotal);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferActivity.this,OfferConsumeActivity.class);
                intent.putExtra("offerModel",offerModel);
                startActivity(intent);
            }
        });
    }
}
