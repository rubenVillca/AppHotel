package com.umss.sistemas.tesis.hotel.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
            showDataProfile(aboutModel);
        }
    }

    private void showDataProfile(AboutModel about) {
        ((TextView)findViewById(R.id.aboutHotelPhone)).setText(about.getPhoneHotel());
        ((TextView)findViewById(R.id.aboutHotelName)).setText(about.getNameHotel());
        ((TextView)findViewById(R.id.aboutHotelMision)).setText(about.getMision());
        ((TextView)findViewById(R.id.aboutHotelVision)).setText(about.getVision());
        ((TextView)findViewById(R.id.aboutHotelAddress)).setText(about.getAddress());
        ((TextView)findViewById(R.id.aboutHotelScope)).setText(about.getScope());
        ((TextView)findViewById(R.id.aboutHotelHistory)).setText(about.getHistory());
        ((TextView)findViewById(R.id.aboutHotelDateFoundation)).setText(about.getFundation());
        ((TextView)findViewById(R.id.aboutHotelWatchword)).setText(about.getWatchWord());
        ((TextView)findViewById(R.id.aboutHotelObjetives)).setText(about.getObjetive());
        ((TextView)findViewById(R.id.aboutHotelEmail)).setText(about.getEmail());
        ((TextView)findViewById(R.id.aboutHotelDescription)).setText(about.getDescription());
        ((TextView)findViewById(R.id.aboutHotelType)).setText(about.getType());
        ((TextView)findViewById(R.id.aboutHotelSiteWeb)).setText(about.getSiteWeb());

    }
    /**
     * Lee de la base de datos de sqlite los datos del hotel
     *
     * @return List<PersonModel>: datos del hotel
     */
    protected List<AboutModel> getContentAbout() {
        List<AboutModel> listAbout = new ArrayList<>();
        sync = new DataBaseSQLiteHelper(this, DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_ABOUT, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                AboutModel aboutModel = new AboutModel();
                aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL)));
                aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL)));
                aboutModel.setMision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_MISION)));
                aboutModel.setVision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_VISION)));
                aboutModel.setAddress(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS)));
                aboutModel.setScope(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE)));
                aboutModel.setHistory(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY)));
                aboutModel.setFundation(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION)));
                aboutModel.setWatchWord(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD)));
                aboutModel.setObjetive(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE)));
                aboutModel.setEmail(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL)));
                aboutModel.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION)));
                aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL)));
                aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX)));
                aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY)));
                aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE)));
                aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL)));
                listAbout.add(aboutModel);

                cursor.moveToNext();
            }
        }
        return listAbout;
    }
}
