package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceFoodPrice extends ServiceParent {
    public ServiceFoodPrice(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtener de SQLite la lista de precios de un alimento
     *
     * @param idFood: id de comida
     * @return ArrayList<FoodPriceModel>: lista de precios q tiene un alimento
     */
    public ArrayList<FoodPriceModel> getFoodPriceModel(int idFood) {
        ArrayList<FoodPriceModel> listPriceFood = new ArrayList<>();
        Cursor cursor;
        if (idFood>0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_FOOD_PRICE
                    + " where " + DBSQLite.KEY_FOOD_PRICE_ID_KEY_FOOD + "=" + idFood, null);
        }else {
            cursor = db.rawQuery("select *" + " from " + DBSQLite.TABLE_FOOD_PRICE, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodPriceModel foodPriceModel = obtainFoodPriceModelCursor(cursor);
                listPriceFood.add(foodPriceModel);

                cursor.moveToNext();
            }
        }
        return listPriceFood;
    }

    private FoodPriceModel obtainFoodPriceModelCursor(Cursor cursor) {
        FoodPriceModel foodPriceModel = new FoodPriceModel();

        foodPriceModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_ID_AUTOINCREMENT)));
        foodPriceModel.setIdKeyFood(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_ID_KEY_FOOD)));
        foodPriceModel.setIdKeyTypeMoneyFood(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY)));
        foodPriceModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_TYPE_MONEY)));
        foodPriceModel.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_PRICE))));
        foodPriceModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_POINT_OBTAIN)));
        foodPriceModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_POINT_REQUIRED)));
        foodPriceModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_PRICE_UNIT)));

        return foodPriceModel;
    }

    public ArrayList<FoodPriceModel> getFoodPriceModelJSON(JSONObject object) {
        ArrayList<FoodPriceModel> foodPriceArray = new ArrayList<>();

        try {
            JSONArray foodJSONArray = object.getJSONArray("prices");

            for (int j = 0; j < foodJSONArray.length(); j++) {
                FoodPriceModel foodPriceModel = new FoodPriceModel();

                JSONObject foodPriceObject = foodJSONArray.getJSONObject(j);

                foodPriceModel.setIdKeyFood(foodPriceObject.getInt("ID_FOOD"));
                foodPriceModel.setIdKeyTypeMoneyFood(foodPriceObject.getInt("ID_TYPE_MONEY"));
                foodPriceModel.setTypeMoney(foodPriceObject.getString("NAME_TYPE_MONEY"));
                foodPriceModel.setPrice(foodPriceObject.getDouble("PRICE_COST_FOOD"));
                foodPriceModel.setPointObtain(foodPriceObject.getInt("POINT_OBTAIN_COST_FOOD"));
                foodPriceModel.setPointRequired(foodPriceObject.getInt("POINT_REQUIRED_COST_FOOD"));
                foodPriceModel.setUnit(foodPriceObject.getInt("UNIT_COST_FOOD"));

                foodPriceArray.add(foodPriceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodPriceArray;
    }

    /**
     * guardar lista de precios de comidas en la base de datos SQLIte
     *
     * @param foodPriceModelArrayList:lista de precios de las comidas en formato JAVa
     */
    public void insertFoodPriceSQLite(ArrayList<FoodPriceModel> foodPriceModelArrayList) {
        for (FoodPriceModel foodPriceModel : foodPriceModelArrayList) {
            ContentValues foodPriceContent = new ContentValues();

            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_ID_KEY_FOOD, foodPriceModel.getIdKeyFood());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY, foodPriceModel.getIdKeyTypeMoneyFood());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_TYPE_MONEY, foodPriceModel.getTypeMoney());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_PRICE, foodPriceModel.getPrice());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_POINT_OBTAIN, foodPriceModel.getPointObtain());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_POINT_REQUIRED, foodPriceModel.getPointRequired());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_UNIT, foodPriceModel.getUnit());

            long succes = db.insert(DBSQLite.TABLE_FOOD_PRICE, null, foodPriceContent);
            if (succes == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }
}
