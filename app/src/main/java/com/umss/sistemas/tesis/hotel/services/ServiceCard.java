package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceCard extends ServiceParent {
    public ServiceCard(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtenerr de SQLite la lista de targetas de credito afilidas a un registro
     *
     * @param idCheck: id de registro de estadia
     * @return ArrayList<CardModel>: lista de tarjetas de credito de un registro
     */
    public ArrayList<CardModel> getCardModel(int idCheck) {
        ArrayList<CardModel> listCardModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_CARD
                + " where " + DBSQLite.KEY_CARD_ID_KEY_CHECK + "=" + idCheck, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CardModel cardModel = obtainCardModelCursor(cursor);
                listCardModel.add(cardModel);

                cursor.moveToNext();
            }
        }
        return listCardModel;
    }

    private CardModel obtainCardModelCursor(Cursor cursor) {
        CardModel cardModel = new CardModel();

        cardModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_ID)));
        cardModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_ID_KEY_CHECK)));
        cardModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CARD_NAME_TYPE)));
        cardModel.setNumber(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_NUMBER)));
        cardModel.setMonth(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_MONTH)));
        cardModel.setYear(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_YEAR)));
        cardModel.setCcv(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_CCV)));
        cardModel.setValid(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_IS_VALID)) > 0);
        cardModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CARD_TYPE_MONEY)));
        cardModel.setDeposit(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CARD_DEPOSIT)));
        return cardModel;
    }

    public ArrayList<CardModel> getCardModelJSON(JSONObject obj) {
        ArrayList<CardModel> cardArray = new ArrayList<>();

        try {
            JSONArray cardJSONArray = obj.getJSONArray("cards");

            for (int j = 0; j < cardJSONArray.length(); j++) {
                CardModel cardModel = new CardModel();

                JSONObject cardObject = cardJSONArray.getJSONObject(j);

                cardModel.setId(cardObject.getInt("ID_CARD"));
                cardModel.setTypeMoney(cardObject.getString("TYPE_MONEY_CARD"));
                cardModel.setDeposit(cardObject.getInt("DEPOSIT_CARD"));
                cardModel.setIdKeyCheck(cardObject.getInt("ID_CHECK"));
                cardModel.setNameType(cardObject.getString("NAME_TYPE_CARD"));
                cardModel.setNumber(cardObject.getInt("NUMBER_CARD"));
                cardModel.setMonth(cardObject.getInt("MONTH_CARD"));
                cardModel.setYear(cardObject.getInt("YEAR_CARD"));
                cardModel.setCcv(cardObject.getInt("CCV_CARD"));
                cardModel.setValid(cardObject.getInt("VALID_CARD") > 0);

                cardArray.add(cardModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cardArray;
    }

    /**
     * guardar targetas de credito  en la base de datos SQLIte
     *
     * @param cardTargetArrayList:lista de targetas de credito
     */
    public void insertTargetSQLite(ArrayList<CardModel> cardTargetArrayList) {
        for (CardModel cardModel : cardTargetArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_CARD_ID, cardModel.getId());
            contentValues.put(DBSQLite.KEY_CARD_ID_KEY_CHECK, cardModel.getIdKeyCheck());
            contentValues.put(DBSQLite.KEY_CARD_NAME_TYPE, cardModel.getNameType());
            contentValues.put(DBSQLite.KEY_CARD_NUMBER, cardModel.getNumber());
            contentValues.put(DBSQLite.KEY_CARD_MONTH, cardModel.getMonth());
            contentValues.put(DBSQLite.KEY_CARD_YEAR, cardModel.getYear());
            contentValues.put(DBSQLite.KEY_CARD_CCV, cardModel.getCcv());
            contentValues.put(DBSQLite.KEY_CARD_IS_VALID, cardModel.isValid());
            contentValues.put(DBSQLite.KEY_CARD_TYPE_MONEY, cardModel.getTypeMoney());
            contentValues.put(DBSQLite.KEY_CARD_DEPOSIT, cardModel.getDeposit());

            if (db.insert(DBSQLite.TABLE_CARD, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }
}
