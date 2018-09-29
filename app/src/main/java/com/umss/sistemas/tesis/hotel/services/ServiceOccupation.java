package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceOccupation extends ServiceParent {

    public ServiceOccupation(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtener de la base de datos la lista de habitacioes ocupadas por el cliente
     *
     * @param idConsum: identificador de consumo
     * @return listOccuparionModel: lista de habitaciones occupadas
     */
    public ArrayList<OccupationModel> getOccupationModel(int idConsum) {
        ArrayList<OccupationModel> listOccupationModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_OCCUPATION
                + " where " + DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                OccupationModel occupationModel = obtainOccupationModelCursor(cursor);
                listOccupationModel.add(occupationModel);

                cursor.moveToNext();
            }
        }
        return listOccupationModel;
    }

    private OccupationModel obtainOccupationModelCursor(Cursor cursor) {
        OccupationModel occupationModel = new OccupationModel();

        occupationModel.setIdRoom(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_ID_ROOM)));
        occupationModel.setIdConsumeService(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE)));
        occupationModel.setNameRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_NAME_ROOM)));
        occupationModel.setImageRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_IMAGE_ROOM)));
        occupationModel.setStateRoom(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_STATE_ROOM)) > 0);
        occupationModel.setTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_TYPE_ROOM)));
        occupationModel.setDescriptionTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM)));
        occupationModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_N_ADULT)));
        occupationModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_N_BOY)));

        return occupationModel;
    }

    public ArrayList<OccupationModel> getOccupationModelJSON(JSONObject obj) {
        ArrayList<OccupationModel> occupationModelArrayList = new ArrayList<>();

        try {
            JSONArray occupationJSONArray = obj.getJSONArray("occupation");

            for (int j = 0; j < occupationJSONArray.length(); j++) {
                OccupationModel occupationModel = new OccupationModel();

                JSONObject occupationObject = occupationJSONArray.getJSONObject(j);

                occupationModel.setIdRoom(occupationObject.getInt("ID_ROOM"));
                occupationModel.setIdConsumeService(occupationObject.getInt("ID_CONSUME_SERVICE"));
                occupationModel.setNameRoom(occupationObject.getString("NAME_ROOM"));
                occupationModel.setImageRoom(occupationObject.getString("IMAGE_ROOM").isEmpty() ? occupationObject.getString("IMAGE_ROOM_MODEL") : occupationObject.getString("IMAGE_ROOM"));
                occupationModel.setStateRoom(occupationObject.getInt("VALUE_TYPE_ROOM_MODEL") > 0);
                occupationModel.setTypeRoom(occupationObject.getString("NAME_ROOM_MODEL"));
                occupationModel.setDescriptionTypeRoom(occupationObject.getString("DESCRIPTION_ROOM_MODEL"));
                occupationModel.setnAdult(occupationObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                occupationModel.setnBoy(occupationObject.getInt("UNIT_BOY_ROOM_MODEL"));

                occupationModelArrayList.add(occupationModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return occupationModelArrayList;
    }

    /**
     * Guardar lista de habitaciones ocupadas por el cliente
     *
     * @param occupationModelArrayList: lista habitaciones ocupadas por el huesped
     */
    public void insertOccupationSQLite(ArrayList<OccupationModel> occupationModelArrayList) {
        for (OccupationModel occupationModel : occupationModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE, occupationModel.getIdConsumeService());
            contentValues.put(DBSQLite.KEY_OCCUPATION_ID_ROOM, occupationModel.getIdRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_NAME_ROOM, occupationModel.getNameRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_IMAGE_ROOM, occupationModel.getImageRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_STATE_ROOM, occupationModel.isStateRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_TYPE_ROOM, occupationModel.getTypeRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM, occupationModel.getDescriptionTypeRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_N_ADULT, occupationModel.getnAdult());
            contentValues.put(DBSQLite.KEY_OCCUPATION_N_BOY, occupationModel.getnBoy());

            if (db.insert(DBSQLite.TABLE_OCCUPATION, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }
}
