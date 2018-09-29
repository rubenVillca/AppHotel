package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.AboutModel;

public class ServiceInfo {
    private SQLiteDatabase db;

    public ServiceInfo(SQLiteDatabase db) {
        this.db = db;
    }
    /**
     * Lee de la base de datos de sqlite los datos del hotel
     *
     * @return List<PersonModel>: datos del hotel
     */
    public AboutModel getAboutModel() {
        AboutModel aboutModel = new AboutModel();
        Cursor cursor = db.rawQuery("select * from " + DBSQLite.TABLE_ABOUT, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                aboutModel = obtainAboutModelCursor(cursor);
                cursor.moveToNext();
            }
        }
        return aboutModel;
    }

    private AboutModel obtainAboutModelCursor(Cursor cursor) {
        AboutModel aboutModel = new AboutModel();

        aboutModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_ID)));
        aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_LOGO_HOTEL)));
        aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_ADDRESS_GPS_X)));
        aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_ADDRESS_GPS_Y)));
        aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_ADDRESS_IMAGE)));
        aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_NAME_HOTEL)));
        aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_PHONE_HOTEL)));
        aboutModel.setEmail(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_EMAIL)));
        aboutModel.setAddress(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_ADDRESS)));
        aboutModel.setMision(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_MISION)));
        aboutModel.setVision(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_VISION)));
        aboutModel.setFundation(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_FUNDATION)));
        aboutModel.setScope(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_SCOPE)));
        aboutModel.setHistory(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_HISTORY)));
        aboutModel.setWatchWord(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_WATCHWORD)));
        aboutModel.setObjetive(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_OBJETIVE)));
        aboutModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_DESCRIPTION)));
        aboutModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_TYPE_HOTEL)));
        aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ABOUT_SITE_WEB_HOTEL)));

        return aboutModel;
    }


}
