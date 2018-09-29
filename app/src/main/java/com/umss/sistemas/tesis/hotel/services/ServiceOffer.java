package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceOffer extends ServiceParent {
    private ServiceServicePrice serviceServicePrice;

    public ServiceOffer(SQLiteDatabase db) {
        super(db);
        serviceServicePrice =new ServiceServicePrice(db);
    }

    /**
     * selecciones las offertas de la base de datos SQLite y los convierte en un array
     *
     * @return offerModelArrayList: lista de ofertas disponibles
     */
    public ArrayList<OfferModel> getOfferModel(int idOffer) {
        Cursor cursor;
        if (idOffer == 0)
            cursor = db.rawQuery("select * " + "from " + DBSQLite.TABLE_OFFER, null);
        else
            cursor = db.rawQuery("select * " + "from " + DBSQLite.TABLE_OFFER + " where " + DBSQLite.KEY_OFFER_ID + "=" + idOffer, null);

        ArrayList<OfferModel> listOfferModel = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                OfferModel offerModel = obtainOfferModelCursor(cursor);
                offerModel.setServicePriceDetailModel(serviceServicePrice.getServicePriceModel(offerModel.getIdKeyService()));

                listOfferModel.add(offerModel);
                cursor.moveToNext();
            }

        }
        return listOfferModel;
    }

    /**
     * sincronizar base de datos SQLite desde webserver a offerModel
     *
     * @param obj:objeto JSON offer
     */
    public void syncUpOffer(JSONObject obj) {
        ArrayList<OfferModel> offerModel = getOfferModelJSON(obj);
        insertOfferSQLite(offerModel);
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un array
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<OfferModel>: JSON convertido en un array
     */
    private ArrayList<OfferModel> getOfferModelJSON(JSONObject obj) {
        ArrayList<OfferModel> offerListModel = new ArrayList<>();

        try {
            JSONArray offersArray = obj.getJSONArray("offers");

            for (int i = 0; i < offersArray.length(); i++) {
                JSONObject result = offersArray.getJSONObject(i);

                OfferModel offerModel = new OfferModel();

                offerModel.setId(result.getInt("ID_OFFER"));
                offerModel.setName(result.getString("NAME_SERVICE"));
                offerModel.setDescription(result.getString("DESCRIPTION_SERVICE"));
                offerModel.setDateIni(result.getString("DATE_INI_OFFER"));
                offerModel.setTimeIni(result.getString("TIME_INI_OFFER"));
                offerModel.setDateFin(result.getString("DATE_FIN_OFFER"));
                offerModel.setTimeFin(result.getString("TIME_FIN_OFFER"));
                offerModel.setImage(result.getString("IMAGE_SERVICE"));
                offerModel.setState(result.getInt("ACTIVE_OFFER"));
                offerModel.setIdKeyService(result.getInt("ID_SERVICE"));
                offerModel.setNameType(result.getString("NAME_TYPE_SERVICE"));
                offerModel.setDescriptionType(result.getString("DESCRIPTION_TYPE_SERVICE"));

                ArrayList<ServicePriceDetailModel> prices = serviceServicePrice.getServicePriceModelJSON(result, offerModel.getIdKeyService(), true);
                offerModel.setServicePriceDetailModel(prices);

                offerListModel.add(offerModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }

        return offerListModel;

    }

    /**
     * leer los datos desde SQLite y convertirlos en objetos
     *
     * @param cursor:lista de offertas en formato RAW
     * @return offerModel: objeto de ofertas leido desde la base de datos SQLite
     */
    private OfferModel obtainOfferModelCursor(Cursor cursor) {
        OfferModel offerModel = new OfferModel();

        offerModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OFFER_ID)));
        offerModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_NAME)));
        offerModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_DESCRIPTION)));
        offerModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OFFER_STATE)));
        offerModel.setDateIni(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_DATE_INI)));
        offerModel.setTimeIni(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_TIME_INI)));
        offerModel.setDateFin(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_DATE_FIN)));
        offerModel.setTimeFin(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_TIME_FIN)));
        offerModel.setIdKeyService(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OFFER_ID_KEY_SERVICE)));
        offerModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_IMAGE)));
        offerModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_NAME_TYPE)));
        offerModel.setDescriptionType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OFFER_DESCRIPTION_TYPE)));
        return offerModel;
    }

    /**
     * guardar lista de images de ofertas en sqlite
     *
     * @param offerModel:lista offertas
     */
    private void insertOfferSQLite(ArrayList<OfferModel> offerModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_PRICE_SERVICE + " WHERE " + DBSQLite.KEY_PRICE_SERVICE_IS_OFFER + "=1");
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_OFFER);
        for (OfferModel offer : offerModel) {
            ContentValues offerContent = new ContentValues();

            offerContent.put(DBSQLite.KEY_OFFER_ID, offer.getId());
            offerContent.put(DBSQLite.KEY_OFFER_STATE, offer.getState());
            offerContent.put(DBSQLite.KEY_OFFER_NAME, offer.getName());
            offerContent.put(DBSQLite.KEY_OFFER_DESCRIPTION, offer.getDescription());
            offerContent.put(DBSQLite.KEY_OFFER_DATE_INI, offer.getDateIni());
            offerContent.put(DBSQLite.KEY_OFFER_TIME_INI, offer.getTimeIni());
            offerContent.put(DBSQLite.KEY_OFFER_DATE_FIN, offer.getDateFin());
            offerContent.put(DBSQLite.KEY_OFFER_TIME_FIN, offer.getTimeFin());
            offerContent.put(DBSQLite.KEY_OFFER_ID_KEY_SERVICE, offer.getIdKeyService());
            offerContent.put(DBSQLite.KEY_OFFER_IMAGE, offer.getImage());
            offerContent.put(DBSQLite.KEY_OFFER_NAME_TYPE, offer.getNameType());
            offerContent.put(DBSQLite.KEY_OFFER_DESCRIPTION_TYPE, offer.getDescriptionType());

            if (db.insert(DBSQLite.TABLE_OFFER, null, offerContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en OfferModel");

            serviceServicePrice.insertServicePriceSQLite(offer.getServicePriceDetailModel());
        }
    }
}
