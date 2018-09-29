package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceSiteTourImage extends ServiceParent {

    public ServiceSiteTourImage(SQLiteDatabase db) {
        super(db);
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
     * convertir el object en un array
     *
     * @param sitesObject:lista     de imagenes del sitio turistico
     * @param idSiteTourModel:idKey del sitio turistico
     * @return sitesTourImageArray: lista de imagenes pertenecientes al sitio turistico con idSiteTour
     */
    public ArrayList<SiteTourImageModel> getSiteTourImageModelJSON(JSONObject sitesObject, int idSiteTourModel) {
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
     * guardar lista de images de los sitios turisticos en sqlite
     *
     * @param siteTourModel:lista de sitios tiristicos
     */
    public void insertSiteTourImageSQLite(SiteTourModel siteTourModel) {
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
