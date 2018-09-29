package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.AboutModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.text.Html.fromHtml;

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

    /**
     * sincronizar base de datos SQLite desde webserver a aboutModel
     *
     * @param obj:objeto JSON about
     */
    public void syncUpAbout(JSONObject obj) {
        AboutModel aboutModel = getAboutModelJSON(obj);
        insertAboutSQLite(aboutModel);
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

    /**
     * obtener el person model a partir del json recivido del webserver
     *
     * @param obj: datos recividos del web server, objeto Acerca de
     * @return AboutModel: modelo de la about convertido en objeto
     */
    @SuppressWarnings("deprecation")
    private AboutModel getAboutModelJSON(JSONObject obj) {
        AboutModel aboutModel = new AboutModel();
        try {
            JSONObject objectAbout = obj.getJSONObject("hotel");
            aboutModel.setId(objectAbout.getInt("ID_HOTEL"));
            aboutModel.setPhoneHotel(objectAbout.getInt("PHONE_HOTEL"));
            aboutModel.setNameHotel(objectAbout.getString("NAME_HOTEL"));
            aboutModel.setMision(String.valueOf(fromHtml(objectAbout.getString("MISSION_HOTEL"))));
            aboutModel.setVision(String.valueOf(fromHtml(objectAbout.getString("VISION_HOTEL"))));
            aboutModel.setAddress(String.valueOf(fromHtml(objectAbout.getString("ADDRESS_HOTEL"))));
            aboutModel.setScope(String.valueOf(fromHtml(objectAbout.getString("SCOPE_HOTEL"))));
            aboutModel.setHistory(String.valueOf(fromHtml(objectAbout.getString("HISTORY_HOTEL"))));
            aboutModel.setFundation(objectAbout.getString("DATE_FOUNDATION_HOTEL"));
            aboutModel.setWatchWord(String.valueOf(fromHtml(objectAbout.getString("WATCHWORD_HOTEL"))));
            aboutModel.setObjetive(String.valueOf(fromHtml(objectAbout.getString("OBJECTIVE_HOTEL"))));
            aboutModel.setEmail(objectAbout.getString("EMAIL_HOTEL"));
            aboutModel.setDescription(String.valueOf(fromHtml(objectAbout.getString("DESCRIPTION_HOTEL"))));
            aboutModel.setLogoHotel(objectAbout.getString("LOGO_HOTEL"));
            aboutModel.setAddressGPSX(objectAbout.getString("ADDRESS_GPS_X_HOTEL"));
            aboutModel.setAddressGPSY(objectAbout.getString("ADDRESS_GPS_Y_HOTEL"));
            aboutModel.setAddressImage(objectAbout.getString("ADDRESS_IMAGE_HOTEL"));
            aboutModel.setType(objectAbout.getString("NAME_TYPE_HOTEL"));
            aboutModel.setSiteWeb(objectAbout.getString("DOMINIO_HOTEL"));
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
        }
        return aboutModel;
    }

    /**
     * ingresar aboutModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param aboutModel: objeto a ingresar a la base ded datos sqlite
     */
    private void insertAboutSQLite(AboutModel aboutModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ABOUT);

        ContentValues newRegister = new ContentValues();

        newRegister.put(DBSQLite.KEY_ABOUT_ID, aboutModel.getId());
        newRegister.put(DBSQLite.KEY_ABOUT_PHONE_HOTEL, aboutModel.getPhoneHotel());
        newRegister.put(DBSQLite.KEY_ABOUT_NAME_HOTEL, aboutModel.getNameHotel());
        newRegister.put(DBSQLite.KEY_ABOUT_MISION, aboutModel.getMision());
        newRegister.put(DBSQLite.KEY_ABOUT_VISION, aboutModel.getVision());
        newRegister.put(DBSQLite.KEY_ABOUT_ADDRESS, aboutModel.getAddress());
        newRegister.put(DBSQLite.KEY_ABOUT_SCOPE, aboutModel.getScope());
        newRegister.put(DBSQLite.KEY_ABOUT_HISTORY, aboutModel.getHistory());
        newRegister.put(DBSQLite.KEY_ABOUT_FUNDATION, aboutModel.getFundation());
        newRegister.put(DBSQLite.KEY_ABOUT_WATCHWORD, aboutModel.getWatchWord());
        newRegister.put(DBSQLite.KEY_ABOUT_OBJETIVE, aboutModel.getObjetive());
        newRegister.put(DBSQLite.KEY_ABOUT_EMAIL, aboutModel.getEmail());
        newRegister.put(DBSQLite.KEY_ABOUT_DESCRIPTION, aboutModel.getDescription());
        newRegister.put(DBSQLite.KEY_ABOUT_LOGO_HOTEL, aboutModel.getLogoHotel());
        newRegister.put(DBSQLite.KEY_ABOUT_ADDRESS_GPS_X, aboutModel.getAddressGPSX());
        newRegister.put(DBSQLite.KEY_ABOUT_ADDRESS_GPS_Y, aboutModel.getAddressGPSY());
        newRegister.put(DBSQLite.KEY_ABOUT_ADDRESS_IMAGE, aboutModel.getAddressImage());
        newRegister.put(DBSQLite.KEY_ABOUT_TYPE_HOTEL, aboutModel.getType());
        newRegister.put(DBSQLite.KEY_ABOUT_SITE_WEB_HOTEL, aboutModel.getSiteWeb());

        if (db.insert(DBSQLite.TABLE_ABOUT, null, newRegister) == -1)
            System.out.println("Ocurrio un error al inserar la consulta en AboutModel");
    }
}
