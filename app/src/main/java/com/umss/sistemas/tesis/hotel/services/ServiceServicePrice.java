package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceServicePrice extends ServiceParent {

    public ServiceServicePrice(SQLiteDatabase db) {
        super(db);
    }

    /**
     * Obtener de la base de datos SQLite la lista de precios de un servicio
     *
     * @return List<ServicePriceDetailModel>: lista de precios de un servicio
     */
    public ArrayList<ServicePriceDetailModel> getServicePriceModel(int idService) {
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
     * convertir el object en un array y huardarlo en SQLite
     *
     * @param priceServiceObject:lista de precios de un servicio
     * @param idService:idKeyService   del servicioPrice
     * @return ArrayList<SiteTourImageModel>:lista de precios del idService
     */
    public ArrayList<ServicePriceDetailModel> getServicePriceModelJSON(JSONObject priceServiceObject, int idService, boolean isOffer) {
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
     * ingresar la lista de serviciosPrice en la base da datos SQLite
     *
     * @param servicesPriceModel: lista de precios de servicios
     */
    public void insertServicePriceSQLite(ArrayList<ServicePriceDetailModel> servicesPriceModel) {
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
