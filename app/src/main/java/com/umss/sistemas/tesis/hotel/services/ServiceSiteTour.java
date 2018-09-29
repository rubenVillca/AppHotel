package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;

import java.util.ArrayList;

public class ServiceSiteTour {
    private SQLiteDatabase db;

    public ServiceSiteTour(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Lee de la base de datos en sqlite los sitios turisticos
     *
     * @return List<SiteTourModel>: lista de sitios turisticos
     */
    public ArrayList<SiteTourModel> getSiteTourModel(int idSiteTour) {
        ArrayList<SiteTourModel> listSiteTour = new ArrayList<>();

        Cursor cursor;
        if (idSiteTour > 0)
            cursor = db.rawQuery("select * "
                    + "from " + DBSQLite.TABLE_SITE_TOUR
                    + " where " + DBSQLite.KEY_SITE_TOUR_ID + "=" + idSiteTour, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_SITE_TOUR, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SiteTourModel siteTourModel = obtainSiteTourModelCursor(cursor);

                siteTourModel.setImagesSite(getSiteTourImageModel(siteTourModel.getIdSite()));
                listSiteTour.add(siteTourModel);
                cursor.moveToNext();
            }
        }
        return listSiteTour;
    }

    /**
     * Lee de la base de datos en sqlite los sitios turisticos
     *
     * @param idSiteTour:clave foranes del idSiteTour
     * @return List<SiteTourImageModel>: lista de sitios turisticos
     */
    public ArrayList<SiteTourImageModel> getSiteTourImageModel(int idSiteTour) {
        Cursor cursorImages = db.rawQuery("select * " + "from " + DBSQLite.TABLE_SITE_TOUR_IMAGE + " where " + DBSQLite.KEY_SITE_TOUR_IMAGE_ID_KEY + "=" + idSiteTour, null);

        ArrayList<SiteTourImageModel> listSiteTourImages = new ArrayList<>();
        if (cursorImages.moveToFirst()) {
            while (!cursorImages.isAfterLast()) {
                SiteTourImageModel siteTourImageModel = obtainSiteTourImagesModelCursor(cursorImages);
                listSiteTourImages.add(siteTourImageModel);
                cursorImages.moveToNext();
            }

        }
        return listSiteTourImages;
    }

    private SiteTourModel obtainSiteTourModelCursor(Cursor cursor) {
        SiteTourModel siteTourModel = new SiteTourModel();

        siteTourModel.setIdSite(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_ID)));
        siteTourModel.setStateSite(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_STATE)));
        siteTourModel.setNameSite(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_NAME)));
        siteTourModel.setDescriptionSite(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_DESCRIPTION)));
        siteTourModel.setAddressSite(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_ADDRESS)));
        siteTourModel.setGpsLatitudeSite(Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_GPS_X))));
        siteTourModel.setGpsLongitudeSite(Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_GPS_Y))));

        return siteTourModel;
    }

    private SiteTourImageModel obtainSiteTourImagesModelCursor(Cursor cursor) {
        SiteTourImageModel siteTourImageModel = new SiteTourImageModel();

        siteTourImageModel.setIdSiteTourImage(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_ID)));
        siteTourImageModel.setNameSiteTourImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_NAME)));
        siteTourImageModel.setDescriptionSiteTourImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_DESCRIPTION)));
        siteTourImageModel.setStateSiteTourImage(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_STATE)));
        siteTourImageModel.setAddressSiteTour(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_ADDRESS)));

        siteTourImageModel.setIdSiteTour(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SITE_TOUR_IMAGE_ID_KEY)));

        return siteTourImageModel;
    }
}
