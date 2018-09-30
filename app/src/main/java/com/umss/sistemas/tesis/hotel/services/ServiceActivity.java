package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.CalendarModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceActivity extends ServiceParent implements ServiceInterface<CalendarModel>{
    public ServiceActivity(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public CalendarModel getModelCursor(Cursor cursor) {
        CalendarModel calendarModel = new CalendarModel();

        calendarModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_ID)));
        calendarModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DATE_START)));
        calendarModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DATE_END)));
        calendarModel.setTimeStart(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_TIME_START)));
        calendarModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_TIME_END)));
        calendarModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_NAME)));
        calendarModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_DESCRIPTION)));
        calendarModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ACTIVITY_IMAGE)));

        return calendarModel;
    }

    /**
     * obtener de la base de datos la lista de actividades a realizarse en el hotel
     *
     * @param idActivity: id de actividad
     * @return ArrayList<CalendarModel>:idActivity>0?lista de todas las actividades: actividad seleccionada
     */
    @Override
    public ArrayList<CalendarModel> getModels(int idActivity) {
        ArrayList<CalendarModel> listCalendarModel = new ArrayList<>();
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
                CalendarModel calendarModel = getModelCursor(cursor);

                listCalendarModel.add(calendarModel);

                cursor.moveToNext();
            }
        }
        return listCalendarModel;
    }

    @Override
    public ArrayList<CalendarModel> getModelJSON(JSONObject obj) {
        ArrayList<CalendarModel> calendarModelArrayList = new ArrayList<>();

        try {
            JSONArray activityJSONArray = obj.getJSONArray("calendar");

            for (int i = 0; i < activityJSONArray.length(); i++) {
                JSONObject activityObject = activityJSONArray.getJSONObject(i);

                CalendarModel calendarModel = new CalendarModel();

                calendarModel.setId(activityObject.getInt("ID_ACTIVITY"));
                calendarModel.setDateStart(activityObject.getString("DATE_START_ACTIVITY"));
                calendarModel.setDateEnd(activityObject.getString("DATE_END_ACTIVITY"));
                calendarModel.setTimeStart(activityObject.getString("TIME_START_ACTIVITY"));
                calendarModel.setTimeEnd(activityObject.getString("TIME_END_ACTIVITY"));
                calendarModel.setName(Conexion.decode(activityObject.getString("NAME_ACTIVITY")));
                calendarModel.setDescription(activityObject.getString("DESCRIPTION_ACTIVITY"));
                calendarModel.setImage(activityObject.getString("IMAGE_ACTIVITY"));

                calendarModelArrayList.add(calendarModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return calendarModelArrayList;
    }

    /**
     * sincornizar webserve con SQLite la lista de actividades
     *
     * @param obj: objeto JSON activity
     */
    @Override
    public void syncUp(JSONObject obj) {
        ArrayList<CalendarModel> calendarModelArrayList = getModelJSON(obj);
        insertSQLite(calendarModelArrayList);
    }

    /**
     * guardar en SQLite la lista de actividades del hotel
     *
     * @param calendarModelArrayList: lista de actividades
     */
    @Override
    public void insertSQLite(ArrayList<CalendarModel> calendarModelArrayList) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ACTIVITY);

        for (CalendarModel calendarModel : calendarModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_ACTIVITY_ID, calendarModel.getId());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DATE_START, calendarModel.getDateStart());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DATE_END, calendarModel.getDateEnd());
            contentValues.put(DBSQLite.KEY_ACTIVITY_TIME_START, calendarModel.getTimeStart());
            contentValues.put(DBSQLite.KEY_ACTIVITY_TIME_END, calendarModel.getTimeEnd());
            contentValues.put(DBSQLite.KEY_ACTIVITY_NAME, calendarModel.getName());
            contentValues.put(DBSQLite.KEY_ACTIVITY_DESCRIPTION, calendarModel.getDescription());
            contentValues.put(DBSQLite.KEY_ACTIVITY_IMAGE, calendarModel.getImage());

            if (db.insert(DBSQLite.TABLE_ACTIVITY, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta calendarModel");
        }
    }
}
