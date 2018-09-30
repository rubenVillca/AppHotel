package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceServices extends ServiceParent {
    private ServiceServicePrice serviceServicePrice;

    public ServiceServices(SQLiteDatabase db) {
        super(db);
        serviceServicePrice=new ServiceServicePrice(db);
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
                serviceModel.setServicePrice(serviceServicePrice.getServicePriceModel(serviceModel.getId()));

                listService.add(serviceModel);
                cursor.moveToNext();
            }
        }
        return listService;
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

                serviceModel.setServicePrice(serviceServicePrice.getServicePriceModelJSON(service, serviceModel.getId(), false));
                servicesModel.add(serviceModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }
        return servicesModel;
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

            serviceServicePrice.insertServicePriceSQLite(serviceModel.getServicePrice());
        }
    }
}
