package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.PersonModel;

public class ServicePerson {
    private SQLiteDatabase db;

    public ServicePerson(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * Lee de la base de datos de sqlite los datos de los perfiles
     *
     * @return List<PersonModel>: lista de perfiles
     */
    public PersonModel getPersonModel(int idPerson) {
        PersonModel personModel = new PersonModel();
        Cursor cursor;
        if (idPerson == 0) {
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_PERSON, null);
        } else {
            cursor = db.rawQuery("select * from " + DBSQLite.TABLE_PERSON + " where " + DBSQLite.KEY_PERSON_ID + "=" + idPerson, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                personModel = obtainPersonModelCursor(cursor);
                cursor.moveToNext();
            }
        }

        return personModel;
    }

    private PersonModel obtainPersonModelCursor(Cursor cursor) {
        PersonModel personModel = new PersonModel();

        personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PERSON_ID)));
        personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_NAME)));
        personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_NAME_LAST)));
        personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_CITY)));
        personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_ADDRESS)));
        personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_DATE_BORN)));
        personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_DATE_REGISTER)));
        personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_EMAIL)));
        personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PERSON_POINT)));
        personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_COUNTRY)));
        personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PERSON_SEX)));
        personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_IMG_PERSON)));
        personModel.setTypeDocument(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_PERSON_TYPE_DOCUMENT)));
        personModel.setNumberDocument(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PERSON_NUMBER_DOCUMENT)));
        personModel.setNumberPhone(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_PERSON_NUMBER_PHONE)));
        return personModel;
    }
}
