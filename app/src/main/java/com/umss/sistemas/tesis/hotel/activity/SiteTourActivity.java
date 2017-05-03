package com.umss.sistemas.tesis.hotel.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.Activities;
import com.umss.sistemas.tesis.hotel.util.CustomSwip;

import java.util.ArrayList;

public class SiteTourActivity extends Activities {

    private ViewPager viewPager;
    private CustomSwip customSwip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_tour);

        super.showToolBar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }

        int idSiteTour = getIdSiteTour();
        chargeContent(idSiteTour);
    }

    private int getIdSiteTour() {
        Bundle bundle = getIntent().getExtras();
        return bundle.getInt("idSiteTour");
    }

    /**
     * @param idSite:identificador del sitio turistico
     */
    private void chargeContent(int idSite) {
        helperSQLite = new HelperSQLite(this);

        SiteTourModel siteTourModel = helperSQLite.getSiteTourModel(idSite).get(0);
        ArrayList<SiteTourImageModel> siteTourImageModel=helperSQLite.getSiteTourImageModel(idSite);

        viewPager=(ViewPager)findViewById(R.id.imageSiteHeaderCollapsing);

        customSwip=new CustomSwip(siteTourImageModel,this);
        viewPager.setAdapter(customSwip);

        TextView nameSiteTour = (TextView) findViewById(R.id.nameSiteTourTextView);
        nameSiteTour.setText(siteTourModel.getNameSite());

        TextView addressSiteTour = (TextView) findViewById(R.id.addressSiteTourlTextView);
        addressSiteTour.setText(siteTourModel.getAddressSite());

        TextView descriptionSiteTour = (TextView) findViewById(R.id.descriptionSiteTourTextView);
        descriptionSiteTour.setText(siteTourModel.getDescriptionSite());

    }
}
