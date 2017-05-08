package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;
import com.umss.sistemas.tesis.hotel.util.CustomSwip;

import java.util.ArrayList;

public class SiteTourActivity extends ActivityParent implements View.OnClickListener{

    private ViewPager viewPager;
    private CustomSwip customSwip;
    private int idSiteTour=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_tour);

        super.showToolBar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Fade());
        }

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);

        idSiteTour = getIdSiteTour();
        chargeContent(idSiteTour);
    }

    private int getIdSiteTour() {
        Bundle bundle = getIntent().getExtras();
        int res=0;
        try {
            res=bundle.getInt("idSiteTour");
        }catch (Exception e){
            Intent intent=new Intent(this,SitesTourActivity.class);
            startActivity(intent);
        }

        return res;
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SiteTourLocationActivity.class);
        intent.putExtra("idSiteTour",idSiteTour);
        startActivity(intent);
    }

}
