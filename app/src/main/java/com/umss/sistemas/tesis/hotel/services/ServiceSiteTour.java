package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * sincronizar base de datos SQLite desde webserver a siteTourModel y siteTourImage
     *
     * @param obj:objeto JSON siteTour y siteTourImage
     */
    public void syncUpSiteTour(JSONObject obj) {
        ArrayList<SiteTourModel> siteTourModel = getSiteTourModelJSON(obj);
        insertSiteTourSQLite(siteTourModel);
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

    /**
     * convertir el object en un array
     *
     * @param sitesObject:lista     de imagenes del sitio turistico
     * @param idSiteTourModel:idKey del sitio turistico
     * @return sitesTourImageArray: lista de imagenes pertenecientes al sitio turistico con idSiteTour
     */
    private ArrayList<SiteTourImageModel> getSiteTourImageModelJSON(JSONObject sitesObject, int idSiteTourModel) {
        ArrayList<SiteTourImageModel> sitesTourImageArray = new ArrayList<>();

        try {
            JSONArray imagesSitesTour = sitesObject.getJSONArray("images");

            for (int j = 0; j < imagesSitesTour.length(); j++) {
                SiteTourImageModel siteTourImageModel = new SiteTourImageModel();

                JSONObject imgObject = imagesSitesTour.getJSONObject(j);

                siteTourImageModel.setIdSiteTourImage(imgObject.getInt("ID_IMAGE_SITE"));
                siteTourImageModel.setNameSiteTourImage(Conexion.decode(imgObject.getString("NAME_IMAGE_SITE")));
                siteTourImageModel.setDescriptionSiteTourImage(Conexion.decode(imgObject.getString("DESCRIPTION_IMAGE_SITE")));
                siteTourImageModel.setAddressSiteTour(imgObject.getString("IMAGE_SITE"));
                siteTourImageModel.setStateSiteTourImage(imgObject.getInt("STATE_IMAGE_SITE"));
                siteTourImageModel.setIdSiteTour(idSiteTourModel);

                sitesTourImageArray.add(siteTourImageModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sitesTourImageArray;
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un array
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<SiteTourModel>: JSON convertido en un array
     */
    private ArrayList<SiteTourModel> getSiteTourModelJSON(JSONObject obj) {
        ArrayList<SiteTourModel> sitesTourModel = new ArrayList<>();

        try {
            JSONArray sitesTourArray = obj.getJSONArray("sitesTour");

            for (int i = 0; i < sitesTourArray.length(); i++) {
                JSONObject sitesObject = sitesTourArray.getJSONObject(i);

                SiteTourModel siteTourModel = new SiteTourModel();

                siteTourModel.setIdSite(sitesObject.getInt("id"));
                siteTourModel.setStateSite(sitesObject.getInt("state"));
                siteTourModel.setNameSite(Conexion.decode(sitesObject.getString("name")));
                siteTourModel.setDescriptionSite(Conexion.decode(sitesObject.getString("description")));
                siteTourModel.setAddressSite(Conexion.decode(sitesObject.getString("address")));
                siteTourModel.setGpsLatitudeSite(Float.parseFloat(sitesObject.getString("gpsX")));
                siteTourModel.setGpsLongitudeSite(Float.parseFloat(sitesObject.getString("gpsY")));

                siteTourModel.setImagesSite(getSiteTourImageModelJSON(sitesObject, siteTourModel.getIdSite()));

                sitesTourModel.add(siteTourModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());

            e.printStackTrace();
        }

        return sitesTourModel;
    }

    /**
     * guardar lista de images de los sitios turisticos en sqlite
     *
     * @param siteTourModel:lista de sitios tiristicos
     */
    private void insertSiteTourImageSQLite(SiteTourModel siteTourModel) {
        for (SiteTourImageModel siteImage : siteTourModel.getImagesSite()) {
            ContentValues siteTourImageContent = new ContentValues();

            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_ID, siteImage.getIdSiteTourImage());
            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_STATE, siteImage.getStateSiteTourImage());
            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_NAME, siteImage.getNameSiteTourImage());
            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_DESCRIPTION, siteImage.getDescriptionSiteTourImage());
            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_ADDRESS, siteImage.getAddressSiteTour());

            siteTourImageContent.put(DBSQLite.KEY_SITE_TOUR_IMAGE_ID_KEY, siteTourModel.getIdSite());

            if (db.insert(DBSQLite.TABLE_SITE_TOUR_IMAGE, null, siteTourImageContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en SiteTourImageModel");
        }
    }

    /**
     * ingresar la lista de sitios turisticos en la base da datos SQLite
     *
     * @param sitesTourModel: lista de lugares turisticos
     */
    private void insertSiteTourSQLite(ArrayList<SiteTourModel> sitesTourModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_SITE_TOUR);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_SITE_TOUR_IMAGE);

        for (SiteTourModel siteTourModel : sitesTourModel) {
            ContentValues siteTourContent = new ContentValues();

            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_ID, siteTourModel.getIdSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_NAME, siteTourModel.getNameSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_DESCRIPTION, siteTourModel.getDescriptionSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_STATE, siteTourModel.getStateSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_ADDRESS, siteTourModel.getAddressSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_GPS_X, siteTourModel.getGpsLatitudeSite());
            siteTourContent.put(DBSQLite.KEY_SITE_TOUR_GPS_Y, siteTourModel.getGpsLongitudeSite());

            insertSiteTourImageSQLite(siteTourModel);

            if (db.insert(DBSQLite.TABLE_SITE_TOUR, null, siteTourContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en SiteTourModel");
        }
    }

}
