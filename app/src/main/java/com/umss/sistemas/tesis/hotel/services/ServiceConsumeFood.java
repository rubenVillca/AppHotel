package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceConsumeFood extends ServiceParent {
    public ServiceConsumeFood(SQLiteDatabase db) {
        super(db);
    }

    /**
     * guardar lista de alimentos consumidos en base de datos SQLite
     *
     * @param consumeFoodModelArrayList:lista de alimentos consumidos
     */
    public void insertConsumeFoodSQLite(ArrayList<ConsumeFoodModel> consumeFoodModelArrayList) {
        for (ConsumeFoodModel consumeFoodModel : consumeFoodModelArrayList) {
            ContentValues contentValues = new ContentValues();
            if (consumeFoodModel.getIdConsume()>0)
                contentValues.put(DBSQLite.KEY_CONSUME_FOOD_ID, consumeFoodModel.getIdConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK, consumeFoodModel.getIdKeyCheck());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_PRICE, consumeFoodModel.getPrice());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_PAY, consumeFoodModel.getPay());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_TYPE_MONEY, consumeFoodModel.getTypeMoney());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_NAME_FOOD, consumeFoodModel.getNameFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_DESCRIPTION_FOOD, consumeFoodModel.getDescriptionFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_POINT_OBTAIN, consumeFoodModel.getPointObtain());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_POINT_REQUIRED, consumeFoodModel.getPointRequired());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_UNIT_FOOD, consumeFoodModel.getUnitFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_DATE, consumeFoodModel.getDateConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_TIME, consumeFoodModel.getTimeConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_STATE, consumeFoodModel.getState());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_SITE, consumeFoodModel.getSite());

            if (db.insert(DBSQLite.TABLE_CONSUME_FOOD, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * lista de todos los alimentos consumidos por el huesped
     *
     * @param idCheck: id de registro de un huesped
     * @return ArrayList<ConsumeFoodModel>: lista de alimentos consumidos
     */
    public ArrayList<ConsumeFoodModel> getConsumeFoodModel(int idCheck) {
        ArrayList<ConsumeFoodModel> listConsumeFoodModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_CONSUME_FOOD
                + " where " + DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLite.KEY_CONSUME_FOOD_ID+" DESC", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ConsumeFoodModel consumeFoodModel = obtainConsumeFoodModelCursor(cursor);
                listConsumeFoodModel.add(consumeFoodModel);

                cursor.moveToNext();
            }
        }
        return listConsumeFoodModel;
    }

    private ConsumeFoodModel obtainConsumeFoodModelCursor(Cursor cursor) {
        ConsumeFoodModel consumeFoodModel = new ConsumeFoodModel();

        consumeFoodModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_ID)));
        consumeFoodModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK)));
        consumeFoodModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_PRICE)));
        consumeFoodModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_PAY)));
        consumeFoodModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_TYPE_MONEY)));
        consumeFoodModel.setNameFood(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_NAME_FOOD)));
        consumeFoodModel.setDescriptionFood(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_DESCRIPTION_FOOD)));
        consumeFoodModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_POINT_OBTAIN)));
        consumeFoodModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_POINT_REQUIRED)));
        consumeFoodModel.setUnitFood(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_UNIT_FOOD)));
        consumeFoodModel.setDateConsume(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_DATE)));
        consumeFoodModel.setTimeConsume(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_TIME)));
        consumeFoodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_STATE)));
        consumeFoodModel.setSite(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_SITE)));

        return consumeFoodModel;
    }

    public ArrayList<ConsumeFoodModel> getConsumeFoodModelJSON(JSONObject obj) {
        ArrayList<ConsumeFoodModel> consumeFoodArray = new ArrayList<>();
        try {
            JSONArray consumeFoodJSONArray = obj.getJSONArray("consumeFood");

            for (int j = 0; j < consumeFoodJSONArray.length(); j++) {
                ConsumeFoodModel consumeFoodModel = new ConsumeFoodModel();

                JSONObject consumeFoodObject = consumeFoodJSONArray.getJSONObject(j);

                consumeFoodModel.setIdConsume(consumeFoodObject.getInt("ID_CONSUME_FOOD"));
                consumeFoodModel.setIdKeyCheck(consumeFoodObject.getInt("ID_CHECK"));
                consumeFoodModel.setPrice(consumeFoodObject.getDouble("PRICE_CONSUME_FOOD"));
                consumeFoodModel.setPay(consumeFoodObject.getDouble("PAY_CONSUME_FOOD"));
                consumeFoodModel.setTypeMoney(consumeFoodObject.getString("NAME_TYPE_MONEY"));
                consumeFoodModel.setNameFood(consumeFoodObject.getString("NAME_FOOD"));
                consumeFoodModel.setDescriptionFood(consumeFoodObject.getString("DESCRIPTION_FOOD"));
                consumeFoodModel.setPointObtain(consumeFoodObject.getInt("POINT_OBTAIN_COST_FOOD"));
                consumeFoodModel.setPointRequired(consumeFoodObject.getInt("POINT_REQUIRED_COST_FOOD"));
                consumeFoodModel.setUnitFood(consumeFoodObject.getInt("UNIT_CONSUME_FOOD"));
                consumeFoodModel.setDateConsume(consumeFoodObject.getString("DATE_CONSUME_FOOD"));
                consumeFoodModel.setTimeConsume(consumeFoodObject.getString("TIME_CONSUME_FOOD"));
                consumeFoodModel.setState(consumeFoodObject.getInt("STATE_CONSUME_FOOD"));
                consumeFoodModel.setSite(consumeFoodObject.getString("SITE_CONSUME_FOOD"));

                consumeFoodArray.add(consumeFoodModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumeFoodArray;
    }

}
