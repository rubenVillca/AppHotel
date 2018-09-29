package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceFood {
    private SQLiteDatabase db;

    public ServiceFood(SQLiteDatabase db) {
        this.db = db;
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
                foodMenuModel.setFoodModelArrayList(getFoodModel(foodMenuModel.getIdSQL()));

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
     * obtener de la base de datos SQLite la lista de alimentos ofrecidos en e menu
     *
     * @param idMenuFood: id del menu a obtener la lista de alimentos
     * @return ArrayList<FoodModel>: lista de alimentos del menu solicitado
     */
    private ArrayList<FoodModel> getFoodModel(int idMenuFood) {
        ArrayList<FoodModel> listFood = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_FOOD
                + " where " + DBSQLite.KEY_FOOD_ID_KEY_MENU + "=" + idMenuFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodModel foodModel = obtainFoodModelCursor(cursor);
                foodModel.setListFoodPriceModel(getFoodPriceModel(foodModel.getId()));
                listFood.add(foodModel);

                cursor.moveToNext();
            }
        }
        return listFood;
    }

    /**
     * obtener de SQLite la lista de precios de un alimento
     *
     * @param idFood: id de comida
     * @return ArrayList<FoodPriceModel>: lista de precios q tiene un alimento
     */
    private ArrayList<FoodPriceModel> getFoodPriceModel(int idFood) {
        ArrayList<FoodPriceModel> listPriceFood = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_FOOD_PRICE
                + " where " + DBSQLite.KEY_FOOD_PRICE_ID_KEY_FOOD + "=" + idFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodPriceModel foodPriceModel = obtainFoodPriceModelCursor(cursor);
                listPriceFood.add(foodPriceModel);

                cursor.moveToNext();
            }
        }
        return listPriceFood;
    }

    /**
     * leer los datos desde SQLite y convertirlos en objetos
     *
     * @param cursor:lista de menus de comida en formato RAW
     * @return foodMenuModel: objeto de menu leido desde la base de datos SQLite
     */
    private FoodMenuModel obtainFoodMenuModelCursor(Cursor cursor) {
        FoodMenuModel foodMenuModel = new FoodMenuModel();

        foodMenuModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_ID_AUTOINCREMENT)));
        foodMenuModel.setIdSQL(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_ID)));
        foodMenuModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_NAME)));
        foodMenuModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_DATE_START)));
        foodMenuModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FOOD_MENU_DATE_END)));

        return foodMenuModel;
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

                foodMenuModel.setIdSQL(foodJSONObject.getInt("ID_MENU"));
                foodMenuModel.setName(foodJSONObject.getString("NAME_MENU"));
                foodMenuModel.setDateStart(foodJSONObject.getString("DATE_START_MENU"));
                foodMenuModel.setDateEnd(foodJSONObject.getString("DATE_END_MENU"));

                ArrayList<FoodModel> foods = getFoodModelJSON(foodJSONObject, foodMenuModel.getIdSQL());
                foodMenuModel.setFoodModelArrayList(foods);

                foodMenuListModel.add(foodMenuModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }

        return foodMenuListModel;
    }

    /**
     * convertir el objetoJson en objeto JAVA
     *
     * @param object:          lista de comidas en formato JSON recibido del webserver
     * @param idFoodMenuModel: clave primaria para conecta con MenuFood
     * @return ArrayList<OfferModel>: JSON convertido en un array
     */
    private ArrayList<FoodModel> getFoodModelJSON(JSONObject object, int idFoodMenuModel) {
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

                ArrayList<FoodPriceModel> foodPrices = getFoodPriceModelJSON(foodObject);
                foodModel.setListFoodPriceModel(foodPrices);

                foodArray.add(foodModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodArray;
    }

    private ArrayList<FoodPriceModel> getFoodPriceModelJSON(JSONObject object) {
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
     * guardar lista de menus de comida en la base de datos SQLIte
     *
     * @param foodMenuModels:lista menus de comida
     */
    private void insertFoodMenuSQLite(ArrayList<FoodMenuModel> foodMenuModels) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD_MENU);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FOOD_PRICE);

        for (FoodMenuModel foodMenuModel : foodMenuModels) {
            insertFoodSQLite(foodMenuModel.getFoodModelArrayList());

            ContentValues foodMenuContent = new ContentValues();

            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_ID, foodMenuModel.getIdSQL());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_NAME, foodMenuModel.getName());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_DATE_START, foodMenuModel.getDateStart());
            foodMenuContent.put(DBSQLite.KEY_FOOD_MENU_DATE_END, foodMenuModel.getDateEnd());

            if (db.insert(DBSQLite.TABLE_FOOD_MENU, null, foodMenuContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta en FoodMenuModel");

        }
    }

    /**
     * guardar lista de comidas en la base de datos SQLIte
     *
     * @param foodModelArrayList:lista comids en formato JAVa
     */
    private void insertFoodSQLite(ArrayList<FoodModel> foodModelArrayList) {
        for (FoodModel foodModel : foodModelArrayList) {
            insertFoodPriceSQLite(foodModel.getListFoodPriceModel());

            ContentValues foodContent = new ContentValues();

            foodContent.put(DBSQLite.KEY_FOOD_ID_AUTOINCREMENT, foodModel.getId());
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

    /**
     * guardar lista de precios de comidas en la base de datos SQLIte
     *
     * @param foodPriceModelArrayList:lista de precios de las comidas en formato JAVa
     */
    private void insertFoodPriceSQLite(ArrayList<FoodPriceModel> foodPriceModelArrayList) {
        for (FoodPriceModel foodPriceModel : foodPriceModelArrayList) {
            ContentValues foodPriceContent = new ContentValues();

            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_ID_KEY_FOOD, foodPriceModel.getIdKeyFood());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_TYPE_MONEY, foodPriceModel.getTypeMoney());
            foodPriceContent.put(DBSQLite.KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY, foodPriceModel.getIdKeyTypeMoneyFood());
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
