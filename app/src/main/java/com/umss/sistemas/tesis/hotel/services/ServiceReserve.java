package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceReserve extends ServiceParent {

    public ServiceReserve(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtener de la base de datos la lista de reservas hechas por el cliente
     *
     * @param idConsum: identificador de consumo
     * @return listReserveModel: lista de reservas hechas por el usuario
     */
    public ArrayList<ReserveModel> getReserveModel(int idConsum) {
        ArrayList<ReserveModel> listReserveModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_RESERVE
                + " where " + DBSQLite.KEY_RESERVE_ID_CONSUME + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ReserveModel reserveModel = obtainReserveModelCursor(cursor);
                listReserveModel.add(reserveModel);

                cursor.moveToNext();
            }
        }
        return listReserveModel;
    }

    private ReserveModel obtainReserveModelCursor(Cursor cursor) {
        ReserveModel reserveModel = new ReserveModel();

        reserveModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_ID_CONSUME)));
        reserveModel.setNameRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_NAME_ROOM_MODEL)));
        reserveModel.setDescriptionRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_DESCRIPTION_ROOM_MODEL)));
        reserveModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_N_ADULT)));
        reserveModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_N_BOY)));
        reserveModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_UNIT)));

        return reserveModel;
    }

    /**
     * Guardar en la base de datos SQLite la lista de reservas q hizo el usuario
     *
     * @param reserveModelArrayList: lista de reservas realizadas por el huesped
     */
    public void insertReserveSQLite(ArrayList<ReserveModel> reserveModelArrayList) {
        for (ReserveModel reserveModel : reserveModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_RESERVE_ID_CONSUME, reserveModel.getIdConsume());
            contentValues.put(DBSQLite.KEY_RESERVE_NAME_ROOM_MODEL, reserveModel.getNameRoomModel());
            contentValues.put(DBSQLite.KEY_RESERVE_DESCRIPTION_ROOM_MODEL, reserveModel.getDescriptionRoomModel());
            contentValues.put(DBSQLite.KEY_RESERVE_N_ADULT, reserveModel.getnAdult());
            contentValues.put(DBSQLite.KEY_RESERVE_N_BOY, reserveModel.getnBoy());
            contentValues.put(DBSQLite.KEY_RESERVE_UNIT, reserveModel.getUnit());

            if (db.insert(DBSQLite.TABLE_RESERVE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    public ArrayList<ReserveModel> getReserveModelJSON(JSONObject obj) {
        ArrayList<ReserveModel> reserveModelArrayList = new ArrayList<>();

        try {
            JSONArray fequentlyJSONArray = obj.getJSONArray("reserve");

            for (int j = 0; j < fequentlyJSONArray.length(); j++) {
                ReserveModel reserveModel = new ReserveModel();

                JSONObject reserveObject = fequentlyJSONArray.getJSONObject(j);

                reserveModel.setIdConsume(reserveObject.getInt("ID_CONSUME_SERVICE"));
                reserveModel.setNameRoomModel(reserveObject.getString("NAME_ROOM_MODEL"));
                reserveModel.setDescriptionRoomModel(reserveObject.getString("DESCRIPTION_ROOM_MODEL"));
                reserveModel.setnAdult(reserveObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                reserveModel.setnBoy(reserveObject.getInt("UNIT_BOY_ROOM_MODEL"));
                reserveModel.setUnit(reserveObject.getInt("UNIT_RESERVED"));

                reserveModelArrayList.add(reserveModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reserveModelArrayList;
    }
}
