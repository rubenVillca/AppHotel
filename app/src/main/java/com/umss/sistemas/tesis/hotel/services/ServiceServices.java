package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;

import java.util.ArrayList;

public class ServiceServices {
    private SQLiteDatabase db;

    public ServiceServices(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Lee de la base de datos de sqlite los servicios disponibles
     *
     * @param idService: si el idservice==0 entonces devolver la lista de todos los servicios,
     *                   y si es mayor a 0 devolver el servicio con el id seleccionado
     * @return List<ServiceModel>: lista de servicios
     */
    public ArrayList<ServiceModel> getServiceModel(int idService) {
        ArrayList<ServiceModel> listService = new ArrayList<>();
        Cursor cursor;
        if (idService > 0)
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_SERVICE
                    + " where " + DBSQLite.KEY_SERVICE_ID + "=" + idService, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_SERVICE, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServiceModel serviceModel = obtainServiceModelCursor(cursor);
                serviceModel.setServicePrice(getServicePriceModel(serviceModel.getId()));

                listService.add(serviceModel);
                cursor.moveToNext();
            }
        }
        return listService;
    }

    /**
     * Obtener de la base de datos SQLite la lista de precios de un servicio
     *
     * @return List<ServicePriceDetailModel>: lista de precios de un servicio
     */
    private ArrayList<ServicePriceDetailModel> getServicePriceModel(int idService) {
        Cursor cursor = db.rawQuery("select * "
                + "from " + DBSQLite.TABLE_PRICE_SERVICE
                + " where " + DBSQLite.KEY_PRICE_SERVICE_KEY + "=" + idService, null);

        ArrayList<ServicePriceDetailModel> listPrice = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServicePriceDetailModel servicePriceDetailModel = obtainServicePriceModelCursor(cursor);
                listPrice.add(servicePriceDetailModel);
                cursor.moveToNext();
            }

        }
        return listPrice;
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
                offerModel.setServicePriceDetailModel(getServicePriceModel(offerModel.getIdKeyService()));

                listOfferModel.add(offerModel);
                cursor.moveToNext();
            }

        }
        return listOfferModel;
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

    private ServiceModel obtainServiceModelCursor(Cursor cursor) {
        ServiceModel serviceModel = new ServiceModel();

        serviceModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_ID)));
        serviceModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_NAME)));
        serviceModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_DESCRIPTION)));
        serviceModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_IMAGE)));
        serviceModel.setReserved(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_RESERVED)));
        serviceModel.setIdType(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_ID_TYPE)));
        serviceModel.setValueType(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_VALUE_TYPE)));
        serviceModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_SERVICE_NAME_TYPE)));

        return serviceModel;
    }

    /**
     * @param cursor:base de datos SQLITE servicePrice
     * @return servicePriceModel: lista de precios de un servicio
     */
    private ServicePriceDetailModel obtainServicePriceModelCursor(Cursor cursor) {
        ServicePriceDetailModel servicePriceDetailModel = new ServicePriceDetailModel();

        servicePriceDetailModel.setServicePriceId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_ID)));
        servicePriceDetailModel.setServicePriceKey(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_KEY)));
        servicePriceDetailModel.setServicePriceNameMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_NAME_MONEY)));
        servicePriceDetailModel.setServicePriceUnit(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_UNIT)));
        servicePriceDetailModel.setServicePriceDay(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_DAY)));
        servicePriceDetailModel.setServicePriceHour(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_HOUR)));
        servicePriceDetailModel.setServicePricePrice(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_PRICE)));
        servicePriceDetailModel.setServicePricePointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_POINT_OBTAIN)));
        servicePriceDetailModel.setServicePricePointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_POINT_REQUIRED)));
        servicePriceDetailModel.setServicePriceIsOffer(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PRICE_SERVICE_IS_OFFER)) == 1);

        return servicePriceDetailModel;
    }
}
