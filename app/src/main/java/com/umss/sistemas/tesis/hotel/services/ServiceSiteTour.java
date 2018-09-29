package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceSiteTour extends ServiceParent {
    private ServiceSiteTourImage serviceSiteTourImage;

    public ServiceSiteTour(SQLiteDatabase db) {
        super(db);
        serviceSiteTourImage=new ServiceSiteTourImage(db);
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

                siteTourModel.setImagesSite(serviceSiteTourImage.getSiteTourImageModel(siteTourModel.getIdSite()));
                listSiteTour.add(siteTourModel);
                cursor.moveToNext();
            }
        }
        return listSiteTour;
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

                siteTourModel.setImagesSite(serviceSiteTourImage.getSiteTourImageModelJSON(sitesObject, siteTourModel.getIdSite()));

                sitesTourModel.add(siteTourModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());

            e.printStackTrace();
        }

        return sitesTourModel;
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

            serviceSiteTourImage.insertSiteTourImageSQLite(siteTourModel);

            if (db.insert(DBSQLite.TABLE_SITE_TOUR, null, siteTourContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en SiteTourModel");
        }
    }

}
