package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;

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
}
