package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;

import java.util.ArrayList;

public class ServiceFrequently {
    private SQLiteDatabase db;

    public ServiceFrequently(SQLiteDatabase db) {
        this.db = db;
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
}
