package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

class DataBaseSQLiteHelper extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 33;
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

    //precio de los servicios
    static final String TABLE_PRICE_SERVICE = "price_service";
    static final String KEY_PRICE_SERVICE_ID = "idPriceService";
    static final String KEY_PRICE_SERVICE_KEY = "idKeyPriceService";
    static final String KEY_PRICE_SERVICE_NAME_MONEY = "nameMoneyPriceService";
    static final String KEY_PRICE_SERVICE_UNIT = "unitPriceService";
    static final String KEY_PRICE_SERVICE_DAY = "unitDayPriceService";
    static final String KEY_PRICE_SERVICE_HOUR = "unitHourPriceService";
    static final String KEY_PRICE_SERVICE_PRICE = "pricePriceService";
    static final String KEY_PRICE_SERVICE_POINT_OBTAIN = "pointObtainPriceService";
    static final String KEY_PRICE_SERVICE_POINT_REQUIRED = "pointRequiredPriceService";
    static final String KEY_PRICE_SERVICE_IS_OFFER = "isOfferPriceService";

    //siteTour
    static final String TABLE_SITE_TOUR = "siteTour";
    static final String KEY_SITE_TOUR_ID = "idSiteTour";
    static final String KEY_SITE_TOUR_NAME = "nameSiteTour";
    static final String KEY_SITE_TOUR_DESCRIPTION = "descriptionSiteTour";
    static final String KEY_SITE_TOUR_ADDRESS = "addressSiteTour";
    static final String KEY_SITE_TOUR_GPS_X = "gpsXSiteTour";
    static final String KEY_SITE_TOUR_GPS_Y = "gpsYSiteTour";
    static final String KEY_SITE_TOUR_STATE = "stateSiteTour";

    //siteTourImage
    static final String TABLE_SITE_TOUR_IMAGE = "siteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_ID = "idSiteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_NAME = "nameSiteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_DESCRIPTION = "descriptionSiteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_ADDRESS = "addressSiteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_ID_KEY = "idKeySiteTourImage";
    static final String KEY_SITE_TOUR_IMAGE_STATE = "stateSiteTourImage";

    //offer
    static final String TABLE_OFFER = "offer";
    static final String KEY_OFFER_ID = "idOffer";
    static final String KEY_OFFER_NAME = "nameOffer";
    static final String KEY_OFFER_DESCRIPTION = "descriptionOffer";
    static final String KEY_OFFER_DATE_INI = "dateIniOffer";
    static final String KEY_OFFER_TIME_INI = "timeIniOffer";
    static final String KEY_OFFER_DATE_FIN = "dateFinOffer";
    static final String KEY_OFFER_TIME_FIN = "timeFinOffer";
    static final String KEY_OFFER_ID_KEY_SERVICE = "idServiceOffer";
    static final String KEY_OFFER_STATE = "stateOffer";
    static final String KEY_OFFER_IMAGE = "imageOffer";
    static final String KEY_OFFER_NAME_TYPE = "nameTypeOffer";
    static final String KEY_OFFER_DESCRIPTION_TYPE = "nameDescriptionOffer";

    //food
    static final String TABLE_FOOD = "food";
    static final String KEY_FOOD_ID = "idFood";
    static final String KEY_FOOD_IDKEYMENU = "idKeyMenuFood";
    static final String KEY_FOOD_NAME = "nameFood";
    static final String KEY_FOOD_STATE = "stateFood";
    static final String KEY_FOOD_TYPE = "typeFood";
    static final String KEY_FOOD_DESCRIPTION = "descriptionFood";
    static final String KEY_FOOD_IMAGE = "imageFood";

    //menu_food_Menu
    static final String TABLE_FOOD_MENU = "foodMenu";
    private static final String KEY_FOOD_MENU_ID_AUTOINCREMENT = "idAutoincrement";
    static final String KEY_FOOD_MENU_ID = "idFoodMenu";
    static final String KEY_FOOD_MENU_NAME = "nameFoodMenu";
    static final String KEY_FOOD_MENU_DATE_START = "dateStartFoodMenu";
    static final String KEY_FOOD_MENU_DATE_END = "dateEndFoodMenu";

    //price_food
    static final String TABLE_FOOD_PRICE = "foodPrice";
    static final String KEY_FOOD_PRICE_ID = "idFoodPrice";
    static final String KEY_FOOD_PRICE_IDKEYFOOD = "idKeyFoodFoodPrice";
    static final String KEY_FOOD_PRICE_TYPEMONEY = "typeMoneyFoodPrice";
    static final String KEY_FOOD_PRICE_PRICE = "priceFoodPrice";
    static final String KEY_FOOD_PRICE_POINTOBTAIN = "pointObtainFoodPrice";
    static final String KEY_FOOD_PRICE_POINTREQUIRED = "pointRequiredFoodPrice";

    DataBaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableLogin = getTableLogin();
        String tablePerson = getTablePerson();
        String tableAbout = getTableAbout();
        String tableService = getTableService();
        String tableServicePrice = getTableServicePrice();
        String tableSiteTour = getTableSiteTour();
        String tableSiteTourImage = getTableSiteTourImage();
        String tableOffer = getTableOffer();
        String tableFood = getFood();
        String tableFoodMenu = getFoodMenu();
        String tableFoodPrice = getFoodPrice();

        db.execSQL(tableLogin);
        db.execSQL(tablePerson);
        db.execSQL(tableAbout);
        db.execSQL(tableServicePrice);
        db.execSQL(tableService);
        db.execSQL(tableSiteTour);
        db.execSQL(tableSiteTourImage);
        db.execSQL(tableOffer);
        db.execSQL(tableFood);
        db.execSQL(tableFoodMenu);
        db.execSQL(tableFoodPrice);
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

    @NonNull
    private String getTableServicePrice() {
        return "CREATE TABLE " + TABLE_PRICE_SERVICE + " ( "
                + KEY_PRICE_SERVICE_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRICE_SERVICE_KEY + " INTEGER,"
                + KEY_PRICE_SERVICE_NAME_MONEY + " TEXT,"
                + KEY_PRICE_SERVICE_UNIT + " INTEGER,"
                + KEY_PRICE_SERVICE_DAY + " INTEGER,"
                + KEY_PRICE_SERVICE_HOUR + " INTEGER,"
                + KEY_PRICE_SERVICE_PRICE + " TEXT,"
                + KEY_PRICE_SERVICE_POINT_OBTAIN + " INTEGER,"
                + KEY_PRICE_SERVICE_POINT_REQUIRED + " INTEGER,"
                + KEY_PRICE_SERVICE_IS_OFFER + " INTEGER"
                + ")";
    }

    @NonNull
    private String getTableSiteTour() {
        return "CREATE TABLE " + TABLE_SITE_TOUR + " ( "
                + KEY_SITE_TOUR_ID + " INTEGER PRIMARY KEY,"
                + KEY_SITE_TOUR_NAME + " TEXT,"
                + KEY_SITE_TOUR_DESCRIPTION + " TEXT,"
                + KEY_SITE_TOUR_ADDRESS + " TEXT,"
                + KEY_SITE_TOUR_GPS_X + " TEXT,"
                + KEY_SITE_TOUR_GPS_Y + " TEXT,"
                + KEY_SITE_TOUR_STATE + " INTEGER"
                + ")";
    }

    @NonNull
    private String getTableSiteTourImage() {
        return "CREATE TABLE " + TABLE_SITE_TOUR_IMAGE + " ( "
                + KEY_SITE_TOUR_IMAGE_ID + " INTEGER PRIMARY KEY,"
                + KEY_SITE_TOUR_IMAGE_NAME + " TEXT,"
                + KEY_SITE_TOUR_IMAGE_DESCRIPTION + " TEXT,"
                + KEY_SITE_TOUR_IMAGE_STATE + " TEXT,"
                + KEY_SITE_TOUR_IMAGE_ADDRESS + " TEXT,"
                + KEY_SITE_TOUR_IMAGE_ID_KEY + " INTEGER"
                + ")";
    }

    @NonNull
    private String getTableOffer() {
        return "CREATE TABLE " + TABLE_OFFER + " ( "
                + KEY_OFFER_ID + " INTEGER PRIMARY KEY,"
                + KEY_OFFER_NAME + " TEXT,"
                + KEY_OFFER_DESCRIPTION + " TEXT,"
                + KEY_OFFER_STATE + " INTEGER,"
                + KEY_OFFER_DATE_INI + " TEXT,"
                + KEY_OFFER_TIME_INI + " TEXT,"
                + KEY_OFFER_DATE_FIN + " TEXT,"
                + KEY_OFFER_TIME_FIN + " TEXT,"
                + KEY_OFFER_IMAGE + " TEXT,"
                + KEY_OFFER_NAME_TYPE + " TEXT,"
                + KEY_OFFER_DESCRIPTION_TYPE + " TEXT,"
                + KEY_OFFER_ID_KEY_SERVICE + " INTEGER"
                + ")";
    }

    /**
     * @return String: tabl SQL de food
     */
    private String getFood() {
        return "CREATE TABLE " + TABLE_FOOD + " ( "
                + KEY_FOOD_MENU_ID_AUTOINCREMENT + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_ID + " INTEGER,"
                + KEY_FOOD_IDKEYMENU + " INTEGER,"
                + KEY_FOOD_STATE + " INTEGER,"
                + KEY_FOOD_TYPE + " TEXT,"
                + KEY_FOOD_NAME + " TEXT,"
                + KEY_FOOD_DESCRIPTION + " TEXT,"
                + KEY_FOOD_IMAGE + " TEXT"
                + ")";
    }

    /**
     * @return String: tablaSQL de foodMenu
     */
    private String getFoodMenu() {
        return "CREATE TABLE " + TABLE_FOOD_MENU + " ( "
                + KEY_FOOD_MENU_ID + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_MENU_NAME + " TEXT,"
                + KEY_FOOD_MENU_DATE_START + " TEXT,"
                + KEY_FOOD_MENU_DATE_END + " TEXT"
                + ")";
    }

    private String getFoodPrice() {
        return "CREATE TABLE " + TABLE_FOOD_PRICE + " ( "
                + KEY_FOOD_PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FOOD_PRICE_IDKEYFOOD + " INTEGER,"
                + KEY_FOOD_PRICE_TYPEMONEY + " TEXT,"
                + KEY_FOOD_PRICE_PRICE + " REAL,"
                + KEY_FOOD_PRICE_POINTOBTAIN + " INTEGER,"
                + KEY_FOOD_PRICE_POINTREQUIRED + " INTEGER"
                + ")";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_ABOUT);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_PRICE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_SITE_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_SITE_TOUR_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_OFFER);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_FOOD_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseSQLiteHelper.TABLE_FOOD_PRICE);
        //se crea la nueva version de la tabla
        onCreate(db);
    }
}


