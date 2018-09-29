package com.umss.sistemas.tesis.hotel.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.Services;
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

        FloatingActionButton fab= findViewById(R.id.fab);
        fab.setOnClickListener(this);

        idSiteTour = getIdSiteTour();
        chargeContent(idSiteTour);
    }

    private int getIdSiteTour() {
        Bundle bundle = getIntent().getExtras();
        int res=0;
        try {
            if (bundle != null) {
                res=bundle.getInt("idSiteTour");
            }else{
                Intent intent=new Intent(this,SitesTourActivity.class);
                startActivity(intent);
            }
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
        services = new Services(this);

        SiteTourModel siteTourModel = services.getSiteTourModel(idSite).get(0);
        ArrayList<SiteTourImageModel> siteTourImageModel= services.getSiteTourImageModel(idSite);

        viewPager= findViewById(R.id.imageViewPagerHeaderCollapsing);

        customSwip=new CustomSwip(siteTourImageModel,this);
        viewPager.setAdapter(customSwip);

        TextView nameSiteTour = findViewById(R.id.nameSiteTourTextView);
        nameSiteTour.setText(siteTourModel.getNameSite());

        TextView addressSiteTour = findViewById(R.id.addressSiteTourlTextView);
        addressSiteTour.setText(siteTourModel.getAddressSite());

        TextView descriptionSiteTour = findViewById(R.id.descriptionSiteTourTextView);
        descriptionSiteTour.setText(siteTourModel.getDescriptionSite());

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SiteTourLocationActivity.class);
        intent.putExtra("idSiteTour",idSiteTour);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,SitesTourActivity.class);
        startActivity(intent);
    }
}
