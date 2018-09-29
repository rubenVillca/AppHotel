package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.ServiceGet;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class AboutActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        serviceGet =new ServiceGet(this);
        showToolBar(getResources().getString(R.string.toolbar_tittle_about), true);
        showContentAbout(serviceGet.getAboutModel());
    }

    private void showContentAbout(AboutModel about) {

        TextView phone= findViewById(R.id.aboutHotelPhone);
        phone.setText(String.valueOf(about.getPhoneHotel()));

        TextView name= findViewById(R.id.aboutHotelName);
        name.setText(about.getNameHotel());

        TextView mision= findViewById(R.id.aboutHotelMision);
        mision.setText(about.getMision());

        TextView vision= findViewById(R.id.aboutHotelVision);
        vision.setText(about.getVision());

        TextView address= findViewById(R.id.aboutHotelAddress);
        address.setText(about.getAddress());

        TextView scope= findViewById(R.id.aboutHotelScope);
        scope.setText(about.getScope());

        TextView history= findViewById(R.id.aboutHotelHistory);
        history.setText(about.getHistory());

        TextView dateFoundation= findViewById(R.id.aboutHotelDateFoundation);
        dateFoundation.setText(about.getFundation());

        TextView watchword= findViewById(R.id.aboutHotelWatchword);
        watchword.setText(about.getWatchWord());

        TextView objetives= findViewById(R.id.aboutHotelObjetives);
        objetives.setText(about.getObjetive());

        TextView email= findViewById(R.id.aboutHotelEmail);
        email.setText(about.getEmail());

        TextView description= findViewById(R.id.aboutHotelDescription);
        description.setText(about.getDescription());

        TextView type= findViewById(R.id.aboutHotelType);
        type.setText(about.getType());

        TextView siteWeb= findViewById(R.id.aboutHotelSiteWeb);
        siteWeb.setText(about.getSiteWeb());
    }
}
