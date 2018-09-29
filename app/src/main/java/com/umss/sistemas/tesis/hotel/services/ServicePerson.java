package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.PersonModel;

import org.json.JSONException;
import org.json.JSONObject;

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

    /**
     * sincronizar base de datos SQLite desde webserver a personModel
     *
     * @param obj:objeto JSON person
     */
    public void syncUpPerson(JSONObject obj) {
        PersonModel personModel = getPersonModelJSON(obj);
        insertPersonSQLite(personModel);
    }

    /**
     * obtener el person model a partir del json recivido del webserver
     *
     * @param obj: datos recividos del web server, objeto Person
     * @return PersonModel: modelo de la person cnvertido en objeto
     */
    private PersonModel getPersonModelJSON(JSONObject obj) {
        PersonModel personModel = new PersonModel();
        try {
            personModel.setIdPerson(obj.getInt("idPerson"));
            personModel.setEmailPerson(obj.getString("email"));
            personModel.setNamePerson(obj.getString("namePerson"));
            personModel.setNameLastPerson(obj.getString("nameLastPerson"));
            personModel.setSexPerson((byte) obj.getInt("sex"));
            personModel.setPointPerson(obj.getInt("point"));
            personModel.setCityPerson(obj.getString("city"));
            personModel.setCountryPerson(obj.getString("country"));
            personModel.setDateBornPerson(obj.getString("dateBorn"));
            personModel.setDateRegisterPerson(obj.getString("dateRegister"));
            personModel.setAddressPerson(obj.getString("address"));
            personModel.setImgPerson(obj.getString("imageProfile"));
            personModel.setNumberPhone(obj.getInt("numberPhone"));
            personModel.setNumberDocument(obj.getInt("numberDocument"));
            personModel.setTypeDocument(obj.getString("typeDocument"));
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return personModel;
    }

    /**
     * ingresar personModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param personModel: objeto a ingresar a la base dedd atos sqlite
     */
    public void insertPersonSQLite(PersonModel personModel) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_PERSON + " where " + DBSQLite.KEY_PERSON_ID + "=" + personModel.getIdPerson());

        ContentValues newRegister = new ContentValues();

        newRegister.put(DBSQLite.KEY_PERSON_ID, personModel.getIdPerson());
        newRegister.put(DBSQLite.KEY_PERSON_EMAIL, personModel.getEmailPerson());
        newRegister.put(DBSQLite.KEY_PERSON_NAME, personModel.getNamePerson());
        newRegister.put(DBSQLite.KEY_PERSON_NAME_LAST, personModel.getNameLastPerson());
        newRegister.put(DBSQLite.KEY_PERSON_CITY, personModel.getCityPerson());
        newRegister.put(DBSQLite.KEY_PERSON_COUNTRY, personModel.getCountryPerson());
        newRegister.put(DBSQLite.KEY_PERSON_POINT, personModel.getPointPerson());
        newRegister.put(DBSQLite.KEY_PERSON_SEX, personModel.getSexPerson());
        newRegister.put(DBSQLite.KEY_PERSON_ADDRESS, personModel.getAddressPerson());
        newRegister.put(DBSQLite.KEY_PERSON_IMG_PERSON, personModel.getImgPerson());
        newRegister.put(DBSQLite.KEY_PERSON_DATE_BORN, personModel.getDateBornPerson());
        newRegister.put(DBSQLite.KEY_PERSON_DATE_REGISTER, personModel.getDateRegisterPerson());
        newRegister.put(DBSQLite.KEY_PERSON_TYPE_DOCUMENT, personModel.getTypeDocument());
        newRegister.put(DBSQLite.KEY_PERSON_NUMBER_DOCUMENT, personModel.getNumberDocument());
        newRegister.put(DBSQLite.KEY_PERSON_NUMBER_PHONE, personModel.getNumberPhone());

        if (db.insert(DBSQLite.TABLE_PERSON, null, newRegister) == -1)
            System.out.println("Ocurrio un error al inserar la consulta en PersonModel");
    }

}
