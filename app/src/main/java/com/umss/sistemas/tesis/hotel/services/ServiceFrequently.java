package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceFrequently extends ServiceParent {
    public ServiceFrequently(SQLiteDatabase db) {
        super(db);
    }

    /**
     * obtener de SQLite la lista de preguntas ferquentes, si idFrequently=0 entonces
     * lista de todas las pregutnas,
     * si idFrequently>0 entonces devolver la pregunta con el id indicado
     *
     * @param idFrequently:id de pregunta
     * @return Array: lista de preguntas frequentes
     */
    public ArrayList<FrequentlyModel> getFrequentlyModel(int idFrequently) {
        ArrayList<FrequentlyModel> listFrequentlyModel = new ArrayList<>();
        Cursor cursor;
        if (idFrequently > 0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_FREQUENTLY
                    + " where " + DBSQLite.KEY_FREQUENTLY_ID + "=" + idFrequently, null);
        } else {
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_FREQUENTLY, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FrequentlyModel frequentlyModel = obtainFrequentlyModelCursor(cursor);
                listFrequentlyModel.add(frequentlyModel);

                cursor.moveToNext();
            }
        }
        return listFrequentlyModel;

    }

    /**
     * sincornizar webserver con SQLite la lista de preguntas fecuentes
     *
     * @param obj: objeto JSON con los datos del modelo FrequenltyModel
     */
    public void syncUpFrequently(JSONObject obj) {
        ArrayList<FrequentlyModel> frequentlyModelArrayList = getFrequentlyModelJSON(obj);
        insertFrequentlySQLite(frequentlyModelArrayList);
    }

    private FrequentlyModel obtainFrequentlyModelCursor(Cursor cursor) {
        FrequentlyModel frequentlyModel = new FrequentlyModel();

        frequentlyModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_ID)));
        frequentlyModel.setIdInquest(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_ID_INQUEST)));
        frequentlyModel.setNameInquest(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_NAME_INQUEST)));
        frequentlyModel.setQuestion(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_QUESTION)));
        frequentlyModel.setResponse(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_RESPONSE)));
        frequentlyModel.setTypeInquest(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_TYPE_INQUEST)));
        frequentlyModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_FREQUENTLY_IS_ACTIVE)) > 0);

        return frequentlyModel;
    }

    /**
     * almacenar en SQLite la lista de preguntas
     *
     * @param frequentlyModelArrayList: lista de preguntas
     */
    private void insertFrequentlySQLite(ArrayList<FrequentlyModel> frequentlyModelArrayList) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_FREQUENTLY);

        for (FrequentlyModel frequentlyModel : frequentlyModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_FREQUENTLY_ID, frequentlyModel.getId());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_ID_INQUEST, frequentlyModel.getIdInquest());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_NAME_INQUEST, frequentlyModel.getNameInquest());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_QUESTION, frequentlyModel.getQuestion());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_RESPONSE, frequentlyModel.getResponse());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_TYPE_INQUEST, frequentlyModel.getTypeInquest());
            contentValues.put(DBSQLite.KEY_FREQUENTLY_IS_ACTIVE, frequentlyModel.isActive());

            if (db.insert(DBSQLite.TABLE_FREQUENTLY, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta frequenltyModel");
        }
    }

    private ArrayList<FrequentlyModel> getFrequentlyModelJSON(JSONObject obj) {
        ArrayList<FrequentlyModel> frequentlyModelArrayList = new ArrayList<>();

        try {
            JSONArray fequentlyJSONArray = obj.getJSONArray("frequently");

            for (int j = 0; j < fequentlyJSONArray.length(); j++) {
                FrequentlyModel frequentlyModel = new FrequentlyModel();

                JSONObject frequentlyObject = fequentlyJSONArray.getJSONObject(j);

                frequentlyModel.setId(frequentlyObject.getInt("ID_QUESTION"));
                frequentlyModel.setIdInquest(frequentlyObject.getInt("ID_INQUEST"));
                frequentlyModel.setNameInquest(Conexion.decode(frequentlyObject.getString("NAME_INQUEST")));
                frequentlyModel.setQuestion(Conexion.decode(frequentlyObject.getString("DESCRIPTION_QUESTION")));
                frequentlyModel.setResponse(Conexion.decode(frequentlyObject.getString("DESCRIPTION_RESPONSE")));
                frequentlyModel.setTypeInquest(frequentlyObject.getInt("VALUE_STATE_INQUEST"));
                frequentlyModel.setActive(frequentlyObject.getInt("ACTIVE_QUESTION") > 0);

                frequentlyModelArrayList.add(frequentlyModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return frequentlyModelArrayList;
    }

}
