package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "Hotel";

    //table person
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

    //table about
    public static final String TABLE_ABOUT = "About";
    public static final String KEY_ABOUT_ID="id";
    public static final String KEY_ABOUT_LOGOHOTEL="logoHotel";
    public static final String KEY_ABOUT_ADDRESSGPSX="addressGPSX";
    public static final String KEY_ABOUT_ADDRESSGPSY="addressGPSY";
    public static final String KEY_ABOUT_ADDRESSIMAGE="addressImage";
    public static final String KEY_ABOUT_NAMEHOTEL="nameHotel";
    public static final String KEY_ABOUT_PHONEHOTEL="phoneHotel";
    public static final String KEY_ABOUT_EMAIL="email";
    public static final String KEY_ABOUT_ADDRESS="address";
    public static final String KEY_ABOUT_MISION="mision";
    public static final String KEY_ABOUT_VISION="vision";
    public static final String KEY_ABOUT_FUNDATION="fundation";
    public static final String KEY_ABOUT_SCOPE="scope";
    public static final String KEY_ABOUT_HISTORY="history";
    public static final String KEY_ABOUT_WATCHWORD="watchWord";
    public static final String KEY_ABOUT_OBJETIVE="objetive";
    public static final String KEY_ABOUT_DESCRIPTION="description";
    public static final String KEY_ABOUT_TYPEHOTEL="typeHotel";
    public static final String KEY_ABOUT_SITEWEBHOTEL="siteWebHotel";

    public DataBaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablePerson = getTablePerson();
        String tableAbout=getTableAbout();

        db.execSQL(tablePerson);
        db.execSQL(tableAbout);
    }

    private String getTableAbout() {
        return "CREATE TABLE " + TABLE_ABOUT + " ( "
                + KEY_ABOUT_ID+" INTEGER PRIMARY KEY,"
                + KEY_ABOUT_LOGOHOTEL+" TEXT,"
                + KEY_ABOUT_ADDRESSGPSX + " TEXT,"
                + KEY_ABOUT_ADDRESSGPSY + " TEXT,"
                + KEY_ABOUT_ADDRESSIMAGE + " TEXT,"
                + KEY_ABOUT_NAMEHOTEL + " TEXT,"
                + KEY_ABOUT_PHONEHOTEL+ " TEXT,"
                + KEY_ABOUT_EMAIL + " TEXT,"
                + KEY_ABOUT_ADDRESS + " TEXT,"
                + KEY_ABOUT_MISION + " INTEGER,"
                + KEY_ABOUT_VISION + " TEXT,"
                + KEY_ABOUT_FUNDATION + " TEXT,"
                + KEY_ABOUT_SCOPE + " TEXT,"
                + KEY_ABOUT_HISTORY + " TEXT,"
                + KEY_ABOUT_WATCHWORD + " TEXT,"
                + KEY_ABOUT_OBJETIVE +" TEXT,"
                + KEY_ABOUT_DESCRIPTION + " TEXT,"
                + KEY_ABOUT_TYPEHOTEL + " TEXT,"
                + KEY_ABOUT_SITEWEBHOTEL + " TEXT"
                + ")";
    }

    @NonNull
    private String getTablePerson() {
        return "CREATE TABLE " + TABLE_PERSON + " ( "
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_ABOUT);
        //se crea la nueva version de la tabla
        onCreate(db);
    }
}
