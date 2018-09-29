package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceConsumeModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceCheck extends ServiceParent {
    private ServiceConsume serviceConsume;
    private ServiceConsumeFood serviceConsumeFood;
    private ServiceCard serviceCard;

    public ServiceCheck(SQLiteDatabase db) {
        super(db);
        serviceConsume=new ServiceConsume(db);
        serviceConsumeFood=new ServiceConsumeFood(db);
        serviceCard=new ServiceCard(db);
    }

    /**
     * si idCheck=0?obtener la lista de todos los registros de estadias: obtener la lista del registro con id=idCheck
     *
     * @param idCheck: id de registro de un huesped
     * @return ArrayList<CheckModel>: lista de registros de estadia de un huesped
     */
    public ArrayList<CheckModel> getCheckModel(int idCheck, int idStateCheck, int idTypeCheck) {
        ArrayList<CheckModel> listCheckModel = new ArrayList<>();
        Cursor cursor;
        if (idCheck > 0) {//para un check especifico
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_CHECK
                    + " where " + DBSQLite.KEY_CHECK_ID + "=" + idCheck
                    +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
        } else {
            if (idStateCheck > 0) {//para los check con estado idState
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            + " AND " + DBSQLite.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                }
            } else {//para el historial
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select * from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select * " +
                            "from " + DBSQLite.TABLE_CHECK
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                }
            }
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CheckModel checkModel = obtainCheckModelCursor(cursor);
                checkModel.setCardTargetArrayList(serviceCard.getCardModel(checkModel.getId()));
                checkModel.setConsumeServiceModelArrayList(serviceConsume.getConsumeModel(checkModel.getId()));
                checkModel.setConsumeFoodModelArrayList(serviceConsumeFood.getConsumeFoodModel(checkModel.getId()));
                listCheckModel.add(checkModel);

                cursor.moveToNext();
            }
        }
        return listCheckModel;
    }

    /**
     * sincronizar base de datos SQLite desde webserver a checkModel
     *
     * @param obj:objeto JSON checkModel
     */
    public void syncUpCheck(JSONObject obj) {
        ArrayList<CheckModel> checkModels = getCheckModelJSON(obj);
        insertCheckSQLite(checkModels);
    }

    private CheckModel obtainCheckModelCursor(Cursor cursor) {
        CheckModel checkModel = new CheckModel();

        checkModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID)));
        checkModel.setIdState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID_STATE)));
        checkModel.setValueState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_VALUE_STATE)));
        checkModel.setNameState(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_NAME_STATE)));
        checkModel.setIdType(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID_KEY_TYPE)));
        checkModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TYPE)));
        checkModel.setDateIn(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_DATE_IN)));
        checkModel.setTimeIn(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TIME_IN)));
        checkModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_DATE_END)));
        checkModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TIME_END)));

        return checkModel;
    }

    private ArrayList<CheckModel> getCheckModelJSON(JSONObject obj) {
        ArrayList<CheckModel> checkArray = new ArrayList<>();

        try {
            JSONArray checkJSONArray = obj.getJSONArray("checks");

            for (int j = 0; j < checkJSONArray.length(); j++) {
                CheckModel checkModel = new CheckModel();

                JSONObject checkObject = checkJSONArray.getJSONObject(j);

                checkModel.setId(checkObject.getInt("ID_CHECK"));
                checkModel.setIdState(checkObject.getInt("ID_STATE_CHECK"));
                checkModel.setValueState(checkObject.getInt("VALUE_STATE_CHECK"));
                checkModel.setNameState(checkObject.getString("NAME_STATE_CHECK"));
                checkModel.setIdType(checkObject.getInt("ID_TYPE_CHECK"));
                checkModel.setType(checkObject.getString("NAME_TYPE_CHECK"));
                checkModel.setDateIn(checkObject.getString("DATE_START_CHECK"));
                checkModel.setTimeIn(checkObject.getString("TIME_START_CHECK"));
                checkModel.setDateEnd(checkObject.getString("DATE_END_CHECK"));
                checkModel.setTimeEnd(checkObject.getString("TIME_END_CHECK"));

                checkModel.setCardTargetArrayList(serviceCard.getCardModelJSON(checkObject));
                checkModel.setConsumeServiceModelArrayList(serviceConsume.getConsumeModelJSON(checkObject));
                checkModel.setConsumeFoodModelArrayList(serviceConsumeFood.getConsumeFoodModelJSON(checkObject));

                checkArray.add(checkModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return checkArray;
    }

    /**
     * guardar registro de ingresos al hotel en la base de datos SQLIte
     *
     * @param checkModels:lista de registros formato JAVa
     */
    private void insertCheckSQLite(ArrayList<CheckModel> checkModels) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CHECK);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CONSUM);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CARD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ARTICLE);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_MEMBER);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CONSUME_FOOD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_OCCUPATION);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_RESERVE);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ARTICLE);

        for (CheckModel checkModel : checkModels) {
            ContentValues checkContent = new ContentValues();

            checkContent.put(DBSQLite.KEY_CHECK_ID, checkModel.getId());
            checkContent.put(DBSQLite.KEY_CHECK_ID_STATE, checkModel.getIdState());
            checkContent.put(DBSQLite.KEY_CHECK_VALUE_STATE, checkModel.getValueState());
            checkContent.put(DBSQLite.KEY_CHECK_NAME_STATE, checkModel.getNameState());
            checkContent.put(DBSQLite.KEY_CHECK_ID_KEY_TYPE,checkModel.getIdType());
            checkContent.put(DBSQLite.KEY_CHECK_TYPE, checkModel.getType());
            checkContent.put(DBSQLite.KEY_CHECK_DATE_IN, checkModel.getDateIn());
            checkContent.put(DBSQLite.KEY_CHECK_TIME_IN, checkModel.getTimeIn());
            checkContent.put(DBSQLite.KEY_CHECK_DATE_END, checkModel.getDateEnd());
            checkContent.put(DBSQLite.KEY_CHECK_TIME_END, checkModel.getTimeEnd());

            if (db.insert(DBSQLite.TABLE_CHECK, null, checkContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            serviceCard.insertTargetSQLite(checkModel.getCardTargetArrayList());
            serviceConsume.insertConsumeSQLite(checkModel.getConsumeServiceModelArrayList());
            serviceConsumeFood.insertConsumeFoodSQLite(checkModel.getConsumeFoodModelArrayList());
        }
    }

    /**
     * obtener la lista de habitaciones libres del hotel
     *
     * @param obj: objeto habitaciones disponibles en formato JSON
     * @return ArrayList<ReserveSearchModel>: lista de tipos de habitaciones disponibles del hotel
     */
    public ArrayList<ReserveSearchModel> getRoomAvailableModel(JSONObject obj) {
        ArrayList<ReserveSearchModel> reserveSearchModels = new ArrayList<>();
        try {
            JSONArray roomAvailableJSONArray = obj.getJSONArray("roomAvailable");

            for (int i = 0; i < roomAvailableJSONArray.length(); i++) {
                JSONObject roomAvailableObject = roomAvailableJSONArray.getJSONObject(i);

                ReserveSearchModel reserveSearchModel = new ReserveSearchModel();

                reserveSearchModel.setIdTypeRoom(roomAvailableObject.getInt("ID_ROOM_MODEL"));
                reserveSearchModel.setUnitAdult(roomAvailableObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                reserveSearchModel.setUnitBoy(roomAvailableObject.getInt("UNIT_BOY_ROOM_MODEL"));
                reserveSearchModel.setUnitPet(roomAvailableObject.getInt("UNIT_PET_ROOM_MODEL"));
                reserveSearchModel.setUnitRoom(roomAvailableObject.getInt("TOTAL_ROOM") - roomAvailableObject.getInt("n_reserved"));
                reserveSearchModel.setIdService(roomAvailableObject.getInt("ID_SERVICE"));
                reserveSearchModel.setNameService(roomAvailableObject.getString("NAME_SERVICE"));
                reserveSearchModel.setImageTypeRoom(roomAvailableObject.getString("IMAGE_ROOM_MODEL"));
                reserveSearchModel.setNameTypeRoom(roomAvailableObject.getString("NAME_ROOM_MODEL"));
                reserveSearchModel.setDescriptionTypeRoom(roomAvailableObject.getString("DESCRIPTION_ROOM_MODEL"));
                reserveSearchModel.setServicePriceConsumeModels(getPriceRoomModel(roomAvailableObject));

                reserveSearchModels.add(reserveSearchModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return reserveSearchModels;
    }

    private ArrayList<ServicePriceConsumeModel> getPriceRoomModel(JSONObject obj) {
        ArrayList<ServicePriceConsumeModel> servicePriceConsumeModels = new ArrayList<>();
        try {
            JSONArray priceRoomJSONArray = obj.getJSONArray("list_cost");

            for (int i = 0; i < priceRoomJSONArray.length(); i++) {
                JSONObject roomAvailableObject = priceRoomJSONArray.getJSONObject(i);

                ServicePriceConsumeModel priceRoomModel = new ServicePriceConsumeModel();

                priceRoomModel.setUnitHour(roomAvailableObject.getInt("UNIT_HOUR_COST_SERVICE"));
                priceRoomModel.setUnitDay(roomAvailableObject.getInt("UNIT_DAY_COST_SERVICE"));
                priceRoomModel.setPointObtain(roomAvailableObject.getInt("POINT_OBTAIN_COST_SERVICE"));
                priceRoomModel.setPointRequired(roomAvailableObject.getInt("POINT_REQUIRED_COST_SERVICE"));
                priceRoomModel.setIdKeyCost(roomAvailableObject.getInt("ID_COST_SERVICE"));
                priceRoomModel.setNameTypeMoney(roomAvailableObject.getString("NAME_TYPE_MONEY"));
                priceRoomModel.setPriceService(roomAvailableObject.getInt("PRICE_COST_SERVICE"));
                priceRoomModel.setUnitService(roomAvailableObject.getInt("UNIT_COST_SERVICE"));

                servicePriceConsumeModels.add(priceRoomModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servicePriceConsumeModels;
    }
}
