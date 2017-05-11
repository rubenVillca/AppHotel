package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteInsert;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLiteObtain;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class OfferActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        super.showToolBar("",true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }

        int idOffer = getIdOffer();

        chargeContent(idOffer);
    }
    private int getIdOffer() {
        Bundle bundle=getIntent().getExtras();
        return bundle.getInt("idOffer");
    }

    private void chargeContent(int idOffer) {
        helperSQLiteObtain =new HelperSQLiteObtain(this);
        OfferModel offerModel= helperSQLiteObtain.getOfferModel(idOffer).get(0);
        ServicePriceModel listPrice =offerModel.getServicePriceModel().get(0);

        ImageView imageOffer=(ImageView)findViewById(R.id.imageHeaderCollapsing);
        Picasso.with(this).load(Conexion.urlServer + offerModel.getImage()).into(imageOffer);

        TextView nameOffer=(TextView)findViewById(R.id.nameOfferTextView);
        nameOffer.setText(offerModel.getName());

        TextView typeOffer=(TextView)findViewById(R.id.nameTypeOfferTextView);
        typeOffer.setText(offerModel.getNameType());

        TextView descriptionOffer=(TextView)findViewById(R.id.descriptionOfferTextView);
        descriptionOffer.setText(offerModel.getDescription());

        TextView typeMoney=(TextView)findViewById(R.id.typeMoneyOfferTextView);
        typeMoney.setText(listPrice.getServicePriceNameMoney());

        TextView priceOffer=(TextView)findViewById(R.id.priceOfferTextView);
        priceOffer.setText(String.valueOf(listPrice.getServicePricePrice()));

        TextView timeOffer=(TextView)findViewById(R.id.timeOfferTextView);
        String hourTotal=String.valueOf(listPrice.getServicePriceHour()+listPrice.getServicePriceDay()*24)+" Horas";
        timeOffer.setText(hourTotal);

    }
}
