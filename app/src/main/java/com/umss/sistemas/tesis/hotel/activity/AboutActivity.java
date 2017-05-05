package com.umss.sistemas.tesis.hotel.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.HelperSQLite;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

public class AboutActivity extends ActivityParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        helperSQLite=new HelperSQLite(this);
        showToolBar(getResources().getString(R.string.toolbar_tittle_about), true);
        showContentAbout(helperSQLite.getAboutModel());
    }

    private void showContentAbout(AboutModel about) {
        TextView phone=((TextView)findViewById(R.id.aboutHotelPhone));
        phone.setText(String.valueOf(about.getPhoneHotel()));

        TextView name=((TextView)findViewById(R.id.aboutHotelName));
        name.setText(about.getNameHotel());

        TextView mision=((TextView)findViewById(R.id.aboutHotelMision));
        mision.setText(about.getMision());

        TextView vision=((TextView)findViewById(R.id.aboutHotelVision));
        vision.setText(about.getVision());

        TextView address=((TextView)findViewById(R.id.aboutHotelAddress));
        address.setText(about.getAddress());

        TextView scope=((TextView)findViewById(R.id.aboutHotelScope));
        scope.setText(about.getScope());

        TextView history=((TextView)findViewById(R.id.aboutHotelHistory));
        history.setText(about.getHistory());

        TextView dateFoundation=((TextView)findViewById(R.id.aboutHotelDateFoundation));
        dateFoundation.setText(about.getFundation());

        TextView watchword=((TextView)findViewById(R.id.aboutHotelWatchword));
        watchword.setText(about.getWatchWord());

        TextView objetives=((TextView)findViewById(R.id.aboutHotelObjetives));
        objetives.setText(about.getObjetive());

        TextView email=((TextView)findViewById(R.id.aboutHotelEmail));
        email.setText(about.getEmail());

        TextView description=((TextView)findViewById(R.id.aboutHotelDescription));
        description.setText(about.getDescription());

        TextView type=((TextView)findViewById(R.id.aboutHotelType));
        type.setText(about.getType());

        TextView siteWeb=((TextView)findViewById(R.id.aboutHotelSiteWeb));
        siteWeb.setText(about.getSiteWeb());
    }
}
