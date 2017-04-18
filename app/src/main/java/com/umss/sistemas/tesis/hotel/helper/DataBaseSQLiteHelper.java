package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Hotel";

    public static final String TABLE_PERSON = "Person";

    public static final String KEY_PERSON_ID = "id";
    public static final String KEY_PERSON_NAME = "namePerson";
    public static final String KEY_PERSON_NAME_LAST = "nameLastPerson";
    public static final String KEY_PERSON_EMAIL = "emailPerson";
    public static final String KEY_PERSON_POINT = "pointPerson";
    public static final String KEY_PERSON_ADDRESS = "addressPerson";
    public static final String KEY_PERSON_CITY = "cityPerson";
    public static final String KEY_PERSON_COUNTRY = "countryPerson";
    public static final String KEY_PERSON_SEX = "sexPerson";
    public static final String KEY_PERSON_DATE_BORN = "dateBornPerson";
    public static final String KEY_PERSON_DATE_REGISTER = "dateRegisterBorn";
    public static final String KEY_PERSON_STATE = "stateAccountPerson";
    public static final String KEY_PERSON_IMG_PERSON = "imgPerson";
    public static final String KEY_PERSON_TYPE_DOCUMENT="typeDocument";
    public static final String KEY_PERSON_NUMBER_DOCUMENT="numberDocument";
    public static final String KEY_PERSON_NUMBER_PHONE="numberPhone";

    public DataBaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String table= "CREATE TABLE " + TABLE_PERSON + " ( "
                + KEY_PERSON_ID + " INTEGER PRIMARY KEY,"
                + KEY_PERSON_NAME + " TEXT,"
                + KEY_PERSON_NAME_LAST + " TEXT,"
                + KEY_PERSON_EMAIL + " TEXT,"
                + KEY_PERSON_POINT + " TEXT,"
                + KEY_PERSON_ADDRESS + " TEXT,"
                + KEY_PERSON_CITY + " TEXT,"
                + KEY_PERSON_COUNTRY + " TEXT,"
                + KEY_PERSON_SEX + " INTEGER,"
                + KEY_PERSON_DATE_BORN + " TEXT,"
                + KEY_PERSON_DATE_REGISTER + " TEXT,"
                + KEY_PERSON_STATE + " TEXT,"
                + KEY_PERSON_IMG_PERSON + " TEXT,"
                + KEY_PERSON_TYPE_DOCUMENT + " TEXT,"
                + KEY_PERSON_NUMBER_DOCUMENT + " TEXT,"
                + KEY_PERSON_NUMBER_PHONE + " TEXT"
                + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_PERSON);
        //se crea la nueva version de la tabla
        onCreate(db);
    }
}
