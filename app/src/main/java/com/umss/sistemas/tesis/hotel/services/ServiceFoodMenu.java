package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceFoodMenu extends ServiceParent {
    private ServiceFood serviceFood;

    public ServiceFoodMenu(SQLiteDatabase db) {
        super(db);
        serviceFood=new ServiceFood(db);
    }

    /**
     * obtener de la base datos SQLite una lista de menus ofrecidos en el hotel
     *
     * @param idFoodMenu: id de menu
     * @return ArrayList<FoodMenuModel>: lista de alimentos ofrecidos en un menu
     */
    public ArrayList<FoodMenuModel> getFoodMenuModel(int idFoodMenu) {
        ArrayList<FoodMenuModel> listFoodMenu = new ArrayList<>();
        Cursor cursor;
        if (idFoodMenu > 0)
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_FOOD_MENU
                    + " where " + DBSQLite.KEY_FOOD_MENU_ID + "=" + idFoodMenu, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_FOOD_MENU, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodMenuModel foodMenuModel = obtainFoodMenuModelCursor(cursor);
                foodMenuModel.setFoodModelArrayList(serviceFood.getFoodModel(foodMenuModel.getId()));

                listFoodMenu.add(foodMenuModel);
                cursor.moveToNext();
            }
        }
        return listFoodMenu;
    }

    /**
     * sincronizar base de datos SQLite desde webserver a offerModel
     *
     * @param obj:objeto JSON offer
     */
    public void syncUpFoodMenu(JSONObject obj) {
        ArrayList<FoodMenuModel> foodMenuModels = getFoodMenuModelJSON(obj);
        insertFoodMenuSQLite(foodMenuModels);
    }

    /**
     * leer los datos desde SQLite y convertirlos en objetos
     *
     * @param cursor:lista de menus de comida en formato RAW
     * @return foodMenuModel: objeto de menu leido desde la base de datos SQLite
     */
    private FoodMenuModel obtainFoodMenuModelCursor(Cursor cursor) {
        FoodMenuModel foodMenuModel = new FoodMenuModel();

        foodMenuModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_ID)));
        foodMenuModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_NAME)));
        foodMenuModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_DATE_START)));
        foodMenuModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_DATE_END)));

        return foodMenuModel;
    }

    /**
     * guardar lista de menus de comida en la base de datos SQLIte
     *
     * @param foodMenuModels:lista menus de comida
     */
    private void insertFoodMenuSQLite(ArrayList<FoodMenuModel> foodMenuModels) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD_MENU);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD_PRICE);

        for (FoodMenuModel foodMenuModel : foodMenuModels) {
            serviceFood.insertFoodSQLite(foodMenuModel.getFoodModelArrayList());

            ContentValues foodMenuContent = new ContentValues();

            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_ID, foodMenuModel.getId());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_NAME, foodMenuModel.getName());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_DATE_START, foodMenuModel.getDateStart());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_DATE_END, foodMenuModel.getDateEnd());

            if (db.insert(DBSQLite.TABLE_FOOD_MENU, null, foodMenuContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en FoodMenuModel");

        }
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un un objeto
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<FoodMenuModel>: JSON convertido en un array
     */
    private ArrayList<FoodMenuModel> getFoodMenuModelJSON(JSONObject obj) {
        ArrayList<FoodMenuModel> foodMenuListModel = new ArrayList<>();

        try {
            JSONArray foodsArray = obj.getJSONArray("foodMenu");

            for (int i = 0; i < foodsArray.length(); i++) {
                JSONObject foodJSONObject = foodsArray.getJSONObject(i);

                FoodMenuModel foodMenuModel = new FoodMenuModel();

                foodMenuModel.setId(foodJSONObject.getInt("ID_MENU"));
                foodMenuModel.setName(foodJSONObject.getString("NAME_MENU"));
                foodMenuModel.setDateStart(foodJSONObject.getString("DATE_START_MENU"));
                foodMenuModel.setDateEnd(foodJSONObject.getString("DATE_END_MENU"));

                ArrayList<FoodModel> foods = serviceFood.getFoodModelJSON(foodJSONObject, foodMenuModel.getId());
                foodMenuModel.setFoodModelArrayList(foods);

                foodMenuListModel.add(foodMenuModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }

        return foodMenuListModel;
    }
}
