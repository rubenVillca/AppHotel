package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonSqliteHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Hotel";

    public static final String TABLE_PERSON = "Person";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "namePerson";
    public static final String KEY_NAME_LAST = "nameLastPerson";
    public static final String KEY_EMAIL = "emailPerson";
    public static final String KEY_POINT = "pointPerson";
    public static final String KEY_ADDRESS = "addressPerson";
    public static final String KEY_CITY = "cityPerson";
    public static final String KEY_COUNTRY = "countryPerson";
    public static final String KEY_SEX = "sexPerson";
    public static final String KEY_DATE_BORN = "dateBornPerson";
    public static final String KEY_DATE_REGISTER = "dateRegisterBorn";
    public static final String KEY_STATE = "stateAccountPerson";
    public static final String KEY_IMG_PERSON = "imgPerson";

    public PersonSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String table= "CREATE TABLE " + TABLE_PERSON + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_NAME_LAST + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_POINT + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_CITY + " TEXT,"
                + KEY_COUNTRY + " TEXT,"
                + KEY_SEX + " INTEGER,"
                + KEY_DATE_BORN + " TEXT,"
                + KEY_DATE_REGISTER + " TEXT,"
                + KEY_STATE + " TEXT,"
                + KEY_IMG_PERSON + " TEXT"
                + ")";
        db.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + PersonSqliteHelper.TABLE_PERSON);
        //se crea la nueva version de la tabla
        onCreate(db);
    }
}
