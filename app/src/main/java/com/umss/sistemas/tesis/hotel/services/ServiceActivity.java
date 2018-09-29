package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ActivityModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * sincornizar webserve con SQLite la lista de actividades
     *
     * @param obj: objeto JSON activity
     */
    public void syncUpCalendar(JSONObject obj) {
        ArrayList<ActivityModel> activityModelArrayList = getActivityModelJSON(obj);
        insertActivitySQLite(activityModelArrayList);
    }

    /**
     * obtener la lista de actividades del hotel
     *
     * @param obj: objeto activity en formato JSON
     * @return ArrayList<ActivityModel>: lista de actividades del hotel
     */
    private ArrayList<ActivityModel> getActivityModelJSON(JSONObject obj) {
        ArrayList<ActivityModel> activityModelArrayList = new ArrayList<>();

        try {
            JSONArray activityJSONArray = obj.getJSONArray("calendar");

            for (int i = 0; i < activityJSONArray.length(); i++) {
                JSONObject activityObject = activityJSONArray.getJSONObject(i);

                ActivityModel activityModel = new ActivityModel();

                activityModel.setId(activityObject.getInt("ID_ACTIVITY"));
                activityModel.setDateStart(activityObject.getString("DATE_START_ACTIVITY"));
                activityModel.setDateEnd(activityObject.getString("DATE_END_ACTIVITY"));
                activityModel.setTimeStart(activityObject.getString("TIME_START_ACTIVITY"));
                activityModel.setTimeEnd(activityObject.getString("TIME_END_ACTIVITY"));
                activityModel.setName(Conexion.decode(activityObject.getString("NAME_ACTIVITY")));
                activityModel.setDescription(activityObject.getString("DESCRIPTION_ACTIVITY"));
                activityModel.setImage(activityObject.getString("IMAGE_ACTIVITY"));

                activityModelArrayList.add(activityModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return activityModelArrayList;
    }

    /**
     * guardar en SQLite la lista de actividades del hotel
     *
     * @param activityModelArrayList: lista de actividades
     */
    private void insertActivitySQLite(ArrayList<ActivityModel> activityModelArrayList) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ACTIVITY);

        for (ActivityModel activityModel : activityModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_ACTIVITY_ID, activityModel.getId());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DATE_START, activityModel.getDateStart());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DATE_END, activityModel.getDateEnd());
            contentValues.put(DBSQLite.KEY_ACTIVITY_TIME_START, activityModel.getTimeStart());
            contentValues.put(DBSQLite.KEY_ACTIVITY_TIME_END, activityModel.getTimeEnd());
            contentValues.put(DBSQLite.KEY_ACTIVITY_NAME, activityModel.getName());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DESCRIPTION, activityModel.getDescription());
            contentValues.put(DBSQLite.KEY_ACTIVITY_IMAGE, activityModel.getImage());

            if (db.insert(DBSQLite.TABLE_ACTIVITY, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta activityModel");
        }
    }
}
