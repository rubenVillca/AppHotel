package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceFood extends ServiceParent {
    private ServiceFoodPrice serviceFoodPrice;

    public ServiceFood(SQLiteDatabase db) {
        super(db);
        serviceFoodPrice=new ServiceFoodPrice(db);
    }

    /**
     * obtener de la base de datos SQLite la lista de alimentos ofrecidos en e menu
     *
     * @param idMenuFood: id del menu a obtener la lista de alimentos
     * @return ArrayList<FoodModel>: lista de alimentos del menu solicitado
     */
    public ArrayList<FoodModel> getFoodModel(int idMenuFood) {
        ArrayList<FoodModel> listFood = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_FOOD
                + " where " + DBSQLite.KEY_FOOD_ID_KEY_MENU + "=" + idMenuFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodModel foodModel = obtainFoodModelCursor(cursor);
                foodModel.setListFoodPriceModel(serviceFoodPrice.getFoodPriceModel(foodModel.getId()));
                listFood.add(foodModel);

                cursor.moveToNext();
            }
        }
        return listFood;
    }

    private FoodModel obtainFoodModelCursor(Cursor cursor) {
        FoodModel foodModel = new FoodModel();

        foodModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_ID_AUTOINCREMENT)));
        foodModel.setIdSQL(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_ID_SQL)));
        foodModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_NAME)));
        foodModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_DESCRIPTION)));
        foodModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_IMAGE)));
        foodModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_TYPE)));
        foodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_STATE)) > 0);
        foodModel.setIdKeyMenu(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_ID_KEY_MENU)));

        return foodModel;
    }

    /**
     * convertir el objetoJson en objeto JAVA
     *
     * @param object:          lista de comidas en formato JSON recibido del webserver
     * @param idFoodMenuModel: clave primaria para conecta con MenuFood
     * @return ArrayList<OfferModel>: JSON convertido en un array
     */
    public ArrayList<FoodModel> getFoodModelJSON(JSONObject object, int idFoodMenuModel) {
        ArrayList<FoodModel> foodArray = new ArrayList<>();

        try {
            JSONArray foodJSONArray = object.getJSONArray("foods");

            for (int j = 0; j < foodJSONArray.length(); j++) {
                FoodModel foodModel = new FoodModel();

                JSONObject foodObject = foodJSONArray.getJSONObject(j);

                foodModel.setId(foodObject.getInt("ID_FOOD"));
                foodModel.setIdKeyMenu(idFoodMenuModel);
                foodModel.setName(foodObject.getString("NAME_FOOD"));
                foodModel.setType(foodObject.getString("NAME_TYPE_FOOD"));
                foodModel.setDescription(foodObject.getString("DESCRIPTION_FOOD"));
                foodModel.setImage(foodObject.getString("IMAGE_FOOD"));
                foodModel.setState(foodObject.getInt("VALUE_STATE_FOOD") > 0);

                ArrayList<FoodPriceModel> foodPrices = serviceFoodPrice.getFoodPriceModelJSON(foodObject);
                foodModel.setListFoodPriceModel(foodPrices);

                foodArray.add(foodModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodArray;
    }

    /**
     * guardar lista de comidas en la base de datos SQLIte
     *
     * @param foodModelArrayList:lista comids en formato JAVa
     */
    public void insertFoodSQLite(ArrayList<FoodModel> foodModelArrayList) {
        for (FoodModel foodModel : foodModelArrayList) {
            serviceFoodPrice.insertFoodPriceSQLite(foodModel.getListFoodPriceModel());

            ContentValues foodContent = new ContentValues();

            //foodContent.put(DBSQLite.KEY_FOOD_ID_AUTOINCREMENT, foodModel.getId());
            foodContent.put(DBSQLite.KEY_FOOD_ID_SQL, foodModel.getIdSQL());
            foodContent.put(DBSQLite.KEY_FOOD_ID_KEY_MENU, foodModel.getIdKeyMenu());
            foodContent.put(DBSQLite.KEY_FOOD_STATE, foodModel.isState() ? 1 : 0);
            foodContent.put(DBSQLite.KEY_FOOD_TYPE, foodModel.getType());
            foodContent.put(DBSQLite.KEY_FOOD_NAME, foodModel.getName());
            foodContent.put(DBSQLite.KEY_FOOD_DESCRIPTION, foodModel.getDescription());
            foodContent.put(DBSQLite.KEY_FOOD_IMAGE, foodModel.getImage());

            long succes = db.insert(DBSQLite.TABLE_FOOD, null, foodContent);
            if (succes == -1)
                System.out.println("Ocurrio un error al insertar la consulta FoodModel");
        }
    }
}
