package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class DataBaseSQLiteHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 14;
    static final String DATABASE_NAME = "Hotel";

    //table login
    static final String TABLE_LOGIN = "Login";
    static final String KEY_LOGIN_ID_PERSON = "idPerson";
    static final String KEY_LOGIN_STATE = "state";
    static final String KEY_LOGIN_PASSWORD = "password";

    //table person
    static final String TABLE_PERSON = "Person";
    static final String KEY_PERSON_ID = "id";
    static final String KEY_PERSON_NAME = "namePerson";
    static final String KEY_PERSON_NAME_LAST = "nameLastPerson";
    static final String KEY_PERSON_EMAIL = "emailPerson";
    static final String KEY_PERSON_POINT = "pointPerson";
    static final String KEY_PERSON_ADDRESS = "addressPerson";
    static final String KEY_PERSON_CITY = "cityPerson";
    static final String KEY_PERSON_COUNTRY = "countryPerson";
    static final String KEY_PERSON_SEX = "sexPerson";
    static final String KEY_PERSON_DATE_BORN = "dateBornPerson";
    static final String KEY_PERSON_DATE_REGISTER = "dateRegisterBorn";
    static final String KEY_PERSON_IMG_PERSON = "imgPerson";
    static final String KEY_PERSON_TYPE_DOCUMENT = "typeDocument";
    static final String KEY_PERSON_NUMBER_DOCUMENT = "numberDocument";
    static final String KEY_PERSON_NUMBER_PHONE = "numberPhone";

    //table about
    static final String TABLE_ABOUT = "About";
    static final String KEY_ABOUT_ID = "id";
    static final String KEY_ABOUT_LOGOHOTEL = "logoHotel";
    static final String KEY_ABOUT_ADDRESSGPSX = "addressGPSX";
    static final String KEY_ABOUT_ADDRESSGPSY = "addressGPSY";
    static final String KEY_ABOUT_ADDRESSIMAGE = "addressImage";
    static final String KEY_ABOUT_NAMEHOTEL = "nameHotel";
    static final String KEY_ABOUT_PHONEHOTEL = "phoneHotel";
    static final String KEY_ABOUT_EMAIL = "email";
    static final String KEY_ABOUT_ADDRESS = "address";
    static final String KEY_ABOUT_MISION = "mision";
    static final String KEY_ABOUT_VISION = "vision";
    static final String KEY_ABOUT_FUNDATION = "fundation";
    static final String KEY_ABOUT_SCOPE = "scope";
    static final String KEY_ABOUT_HISTORY = "history";
    static final String KEY_ABOUT_WATCHWORD = "watchWord";
    static final String KEY_ABOUT_OBJETIVE = "objetive";
    static final String KEY_ABOUT_DESCRIPTION = "description";
    static final String KEY_ABOUT_TYPEHOTEL = "typeHotel";
    static final String KEY_ABOUT_SITEWEBHOTEL = "siteWebHotel";

    //services
    static final String TABLE_SERVICE = "service";
    static final String KEY_SERVICE_ID = "idService";
    static final String KEY_SERVICE_NAME = "nameService";
    static final String KEY_SERVICE_DESCRIPTION = "descriptionService";
    static final String KEY_SERVICE_TYPE = "typeService";
    static final String KEY_SERVICE_IMAGE = "imageService";
    static final String KEY_SERVICE_RESERVED = "reservedService";


    DataBaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableLogin = getTableLogin();
        String tablePerson = getTablePerson();
        String tableAbout = getTableAbout();
        String tableService = getTableService();

        db.execSQL(tableLogin);
        db.execSQL(tablePerson);
        db.execSQL(tableAbout);
        db.execSQL(tableService);
    }

    @NonNull
    private String getTableLogin() {
        return "CREATE TABLE " + TABLE_LOGIN + " ( "
                + KEY_LOGIN_ID_PERSON + " INTEGER PRIMARY KEY,"
                + KEY_LOGIN_STATE + " INTEGER,"
                + KEY_LOGIN_PASSWORD + " TEXT"
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
                + KEY_PERSON_IMG_PERSON + " TEXT,"
                + KEY_PERSON_TYPE_DOCUMENT + " TEXT,"
                + KEY_PERSON_NUMBER_DOCUMENT + " TEXT,"
                + KEY_PERSON_NUMBER_PHONE + " TEXT"
                + ")";
    }
    @NonNull
    private String getTableAbout() {
        return "CREATE TABLE " + TABLE_ABOUT + " ( "
                + KEY_ABOUT_ID + " INTEGER PRIMARY KEY,"
                + KEY_ABOUT_LOGOHOTEL + " TEXT,"
                + KEY_ABOUT_ADDRESSGPSX + " TEXT,"
                + KEY_ABOUT_ADDRESSGPSY + " TEXT,"
                + KEY_ABOUT_ADDRESSIMAGE + " TEXT,"
                + KEY_ABOUT_NAMEHOTEL + " TEXT,"
                + KEY_ABOUT_PHONEHOTEL + " TEXT,"
                + KEY_ABOUT_EMAIL + " TEXT,"
                + KEY_ABOUT_ADDRESS + " TEXT,"
                + KEY_ABOUT_MISION + " INTEGER,"
                + KEY_ABOUT_VISION + " TEXT,"
                + KEY_ABOUT_FUNDATION + " TEXT,"
                + KEY_ABOUT_SCOPE + " TEXT,"
                + KEY_ABOUT_HISTORY + " TEXT,"
                + KEY_ABOUT_WATCHWORD + " TEXT,"
                + KEY_ABOUT_OBJETIVE + " TEXT,"
                + KEY_ABOUT_DESCRIPTION + " TEXT,"
                + KEY_ABOUT_TYPEHOTEL + " TEXT,"
                + KEY_ABOUT_SITEWEBHOTEL + " TEXT"
                + ")";
    }
    @NonNull
    private String getTableService() {
        return "CREATE TABLE " + TABLE_SERVICE + " ( "
                + KEY_SERVICE_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVICE_NAME + " TEXT,"
                + KEY_SERVICE_DESCRIPTION + " TEXT,"
                + KEY_SERVICE_TYPE + " TEXT,"
                + KEY_SERVICE_IMAGE + " TEXT,"
                + KEY_SERVICE_RESERVED + " INTEGER"
                + ")";
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_ABOUT);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_SERVICE);
        //se crea la nueva version de la tabla
        onCreate(db);
    }
}
