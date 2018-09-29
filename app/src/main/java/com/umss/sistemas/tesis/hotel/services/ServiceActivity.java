package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ActivityModel;

import java.util.ArrayList;

public class ServiceActivity {
    private SQLiteDatabase db;

    public ServiceActivity(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * obtener de la base de datos la lista de actividades a realizarse en el hotel
     *
     * @param idActivity: id de actividad
     * @return ArrayList<ActivityModel>:idActivity>0?lista de todas las actividades: actividad seleccionada
     */
    public ArrayList<ActivityModel> getActivityModel(int idActivity) {
        ArrayList<ActivityModel> listActivityModel = new ArrayList<>();
        Cursor cursor;

        if (idActivity > 0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_ACTIVITY
                    + " where " + DBSQLite.KEY_ACTIVITY_ID + "=" + idActivity, null);
        } else {
            cursor = db.rawQuery("select *" + " from " + DBSQLite.TABLE_ACTIVITY, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ActivityModel activityModel = obtainActivityModelCursor(cursor);

                listActivityModel.add(activityModel);

                cursor.moveToNext();
            }
        }
        return listActivityModel;
    }

    private ActivityModel obtainActivityModelCursor(Cursor cursor) {
        ActivityModel activityModel = new ActivityModel();

        activityModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_ID)));
        activityModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DATE_START)));
        activityModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DATE_END)));
        activityModel.setTimeStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_TIME_START)));
        activityModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_TIME_END)));
        activityModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_NAME)));
        activityModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DESCRIPTION)));
        activityModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_IMAGE)));

        return activityModel;
    }
}
