package com.umss.sistemas.tesis.hotel.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umss.sistemas.tesis.hotel.R;
import com.umss.sistemas.tesis.hotel.helper.DataBaseSQLiteHelper;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.util.Activities;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends Activities {
    protected DataBaseSQLiteHelper sync;
    protected SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.showToolBar(getResources().getString(R.string.toolbar_tittle_about), true);
        showContentAbout();
    }

    private void showContentAbout() {
        List<AboutModel> listAbout = getContentAbout();
        if (!listAbout.isEmpty()) {
            AboutModel aboutModel = listAbout.get(0);
            showContentAbout(aboutModel);
        }
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
    /**
     * Lee de la base de datos de sqlite los datos del hotel
     *
     * @return List<PersonModel>: datos del hotel
     */
    protected List<AboutModel> getContentAbout() {
        sync = new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
        List<AboutModel> listAbout = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_ABOUT, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listAbout.add(getAboutModel(cursor));
                cursor.moveToNext();
            }
        }
        return listAbout;
    }

    @NonNull
    private AboutModel getAboutModel(Cursor cursor) {
        AboutModel aboutModel = new AboutModel();

        aboutModel.setId(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ID)));
        aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL)));
        aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX)));
        aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY)));
        aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE)));
        aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL)));
        aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL)));
        aboutModel.setEmail(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL)));
        aboutModel.setAddress(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS)));
        aboutModel.setMision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_MISION)));
        aboutModel.setVision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_VISION)));
        aboutModel.setFundation(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION)));
        aboutModel.setScope(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE)));
        aboutModel.setHistory(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY)));
        aboutModel.setWatchWord(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD)));
        aboutModel.setObjetive(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE)));
        aboutModel.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION)));
        aboutModel.setType(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_TYPEHOTEL)));
        aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL)));

        return aboutModel;
    }
}
