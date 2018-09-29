package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * sincronizar base de datos SQLite desde webserver a serviceModel
     *
     * @param obj:objeto JSON service
     */
    public void syncUpService(JSONObject obj) {
        ArrayList<ServiceModel> serviceModel = getServiceModelJSON(obj);
        insertServiceSQLite(serviceModel);
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
     * @return ArrayList<ServiceModel>: JSON convertido en un array
     */
    private ArrayList<ServiceModel> getServiceModelJSON(JSONObject obj) {
        ArrayList<ServiceModel> servicesModel = new ArrayList<>();

        try {
            JSONArray servicesArray = obj.getJSONArray("services");
            int limit = servicesArray.length();

            for (int i = 0; i < limit; i++) {
                JSONObject service = servicesArray.getJSONObject(i);

                ServiceModel serviceModel = new ServiceModel();

                serviceModel.setId(service.getInt("ID_SERVICE"));
                serviceModel.setName(Conexion.decode(service.getString("NAME_SERVICE")));
                serviceModel.setReserved(service.getInt("RESERVED_SERVICE"));
                serviceModel.setDescription(Conexion.decode(service.getString("DESCRIPTION_SERVICE")));
                serviceModel.setImage(service.getString("IMAGE_SERVICE"));
                serviceModel.setIdType(service.getInt("ID_TYPE_SERVICE"));
                serviceModel.setNameType(Conexion.decode(service.getString("NAME_TYPE_SERVICE")));
                serviceModel.setValueType(service.getInt("VALUE_TYPE_SERVICE"));

                serviceModel.setServicePrice(getServicePriceModelJSON(service, serviceModel.getId(), false));
                servicesModel.add(serviceModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }
        return servicesModel;
    }

    /**
     * convertir el object en un array y huardarlo en SQLite
     *
     * @param priceServiceObject:lista de precios de un servicio
     * @param idService:idKeyService   del servicioPrice
     * @return ArrayList<SiteTourImageModel>:lista de precios del idService
     */
    private ArrayList<ServicePriceDetailModel> getServicePriceModelJSON(JSONObject priceServiceObject, int idService, boolean isOffer) {
        ArrayList<ServicePriceDetailModel> priceServiceArray = new ArrayList<>();

        try {
            JSONArray priceServices = priceServiceObject.getJSONArray("prices");

            for (int j = 0; j < priceServices.length(); j++) {
                ServicePriceDetailModel servicePriceDetailModel = new ServicePriceDetailModel();

                JSONObject priceObject = priceServices.getJSONObject(j);

                servicePriceDetailModel.setServicePriceId(priceObject.getInt("ID_COST_SERVICE"));
                servicePriceDetailModel.setServicePriceKey(idService);
                servicePriceDetailModel.setServicePriceNameMoney(priceObject.getString("NAME_TYPE_MONEY"));
                servicePriceDetailModel.setServicePriceUnit(priceObject.getInt("UNIT_COST_SERVICE"));
                servicePriceDetailModel.setServicePriceDay(priceObject.getInt("UNIT_DAY_COST_SERVICE"));
                servicePriceDetailModel.setServicePriceHour(priceObject.getInt("UNIT_HOUR_COST_SERVICE"));
                servicePriceDetailModel.setServicePricePrice(Double.parseDouble(priceObject.getString("PRICE_COST_SERVICE")));
                servicePriceDetailModel.setServicePricePointObtain(priceObject.getInt("POINT_OBTAIN_COST_SERVICE"));
                servicePriceDetailModel.setServicePricePointRequired(priceObject.getInt("POINT_REQUIRED_COST_SERVICE"));
                servicePriceDetailModel.setServicePriceIsOffer(isOffer);

                priceServiceArray.add(servicePriceDetailModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return priceServiceArray;
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

                ArrayList<ServicePriceDetailModel> prices = getServicePriceModelJSON(result, offerModel.getIdKeyService(), true);
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

    /**
     * ingresar la lista de servicios en la base da datos SQLite
     *
     * @param servicesModel: lista de servicios
     */
    private void insertServiceSQLite(ArrayList<ServiceModel> servicesModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_PRICE_SERVICE
                + " WHERE " + DBSQLite.KEY_PRICE_SERVICE_IS_OFFER + "=0");
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_SERVICE);

        for (ServiceModel serviceModel : servicesModel) {
            ContentValues serviceContent = new ContentValues();

            serviceContent.put(DBSQLite.KEY_SERVICE_ID, serviceModel.getId());
            serviceContent.put(DBSQLite.KEY_SERVICE_NAME, serviceModel.getName());
            serviceContent.put(DBSQLite.KEY_SERVICE_DESCRIPTION, serviceModel.getDescription());
            serviceContent.put(DBSQLite.KEY_SERVICE_IMAGE, serviceModel.getImage());
            serviceContent.put(DBSQLite.KEY_SERVICE_RESERVED, serviceModel.getReserved());
            serviceContent.put(DBSQLite.KEY_SERVICE_ID_TYPE, serviceModel.getIdType());
            serviceContent.put(DBSQLite.KEY_SERVICE_NAME_TYPE, serviceModel.getNameType());
            serviceContent.put(DBSQLite.KEY_SERVICE_VALUE_TYPE, serviceModel.getValueType());

            if (db.insert(DBSQLite.TABLE_SERVICE, null, serviceContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en ServiceModel");

            insertServicePriceSQLite(serviceModel.getServicePrice());
        }
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

            insertServicePriceSQLite(offer.getServicePriceDetailModel());
        }
    }

    /**
     * ingresar la lista de serviciosPrice en la base da datos SQLite
     *
     * @param servicesPriceModel: lista de precios de servicios
     */
    private void insertServicePriceSQLite(ArrayList<ServicePriceDetailModel> servicesPriceModel) {
        for (ServicePriceDetailModel servicePriceDetailModel : servicesPriceModel) {
            ContentValues serviceContent = new ContentValues();

            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_ID, servicePriceDetailModel.getServicePriceId());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_KEY, servicePriceDetailModel.getServicePriceKey());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_DAY, servicePriceDetailModel.getServicePriceDay());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_HOUR, servicePriceDetailModel.getServicePriceHour());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_NAME_MONEY, servicePriceDetailModel.getServicePriceNameMoney());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_PRICE, servicePriceDetailModel.getServicePricePrice());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_UNIT, servicePriceDetailModel.getServicePriceUnit());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_POINT_OBTAIN, servicePriceDetailModel.getServicePricePointObtain());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_POINT_REQUIRED, servicePriceDetailModel.getServicePricePointRequired());
            serviceContent.put(DBSQLite.KEY_PRICE_SERVICE_IS_OFFER, String.valueOf(servicePriceDetailModel.isServicePriceIsOffer() ? 1 : 0));
            if (db.insert(DBSQLite.TABLE_PRICE_SERVICE, null, serviceContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en ServiceModel");
        }
    }

}
