package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 69;
    public static final String DATABASE_NAME = "Hotel";

    //table login
    public static final String TABLE_LOGIN = "Login";
    public static final String KEY_LOGIN_ID_PERSON = "idPerson";
    public static final String KEY_LOGIN_STATE = "state";
    public static final String KEY_LOGIN_PASSWORD = "password";

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
    public static final String KEY_PERSON_IMG_PERSON = "imgPerson";
    public static final String KEY_PERSON_TYPE_DOCUMENT = "typeDocument";
    public static final String KEY_PERSON_NUMBER_DOCUMENT = "numberDocument";
    public static final String KEY_PERSON_NUMBER_PHONE = "numberPhone";

    //table about
    public static final String TABLE_ABOUT = "About";
    public static final String KEY_ABOUT_ID = "id";
    public static final String KEY_ABOUT_LOGO_HOTEL = "logoHotel";
    public static final String KEY_ABOUT_ADDRESS_GPS_X = "addressGPSX";
    public static final String KEY_ABOUT_ADDRESS_GPS_Y = "addressGPSY";
    public static final String KEY_ABOUT_ADDRESS_IMAGE = "addressImage";
    public static final String KEY_ABOUT_NAME_HOTEL = "nameHotel";
    public static final String KEY_ABOUT_PHONE_HOTEL = "phoneHotel";
    public static final String KEY_ABOUT_EMAIL = "email";
    public static final String KEY_ABOUT_ADDRESS = "address";
    public static final String KEY_ABOUT_MISION = "mision";
    public static final String KEY_ABOUT_VISION = "vision";
    public static final String KEY_ABOUT_FUNDATION = "fundation";
    public static final String KEY_ABOUT_SCOPE = "scope";
    public static final String KEY_ABOUT_HISTORY = "history";
    public static final String KEY_ABOUT_WATCHWORD = "watchWord";
    public static final String KEY_ABOUT_OBJETIVE = "objetive";
    public static final String KEY_ABOUT_DESCRIPTION = "description";
    public static final String KEY_ABOUT_TYPE_HOTEL = "typeHotel";
    public static final String KEY_ABOUT_SITE_WEB_HOTEL = "siteWebHotel";

    //serviceHelper
    public static final String TABLE_SERVICE = "service";
    public static final String KEY_SERVICE_ID = "idService";
    public static final String KEY_SERVICE_NAME = "nameService";
    public static final String KEY_SERVICE_DESCRIPTION = "descriptionService";
    public static final String KEY_SERVICE_IMAGE = "imageService";
    public static final String KEY_SERVICE_RESERVED = "reservedService";
    public static final String KEY_SERVICE_ID_TYPE = "idTypeService";
    public static final String KEY_SERVICE_NAME_TYPE = "typeService";
    public static final String KEY_SERVICE_VALUE_TYPE = "valueTypeService";

    //precio de los servicios
    public static final String TABLE_PRICE_SERVICE = "price_service";
    public static final String KEY_PRICE_SERVICE_ID = "idPriceService";
    public static final String KEY_PRICE_SERVICE_KEY = "idKeyPriceService";
    public static final String KEY_PRICE_SERVICE_NAME_MONEY = "nameMoneyPriceService";
    public static final String KEY_PRICE_SERVICE_UNIT = "unitPriceService";
    public static final String KEY_PRICE_SERVICE_DAY = "unitDayPriceService";
    public static final String KEY_PRICE_SERVICE_HOUR = "unitHourPriceService";
    public static final String KEY_PRICE_SERVICE_PRICE = "pricePriceService";
    public static final String KEY_PRICE_SERVICE_POINT_OBTAIN = "pointObtainPriceService";
    public static final String KEY_PRICE_SERVICE_POINT_REQUIRED = "pointRequiredPriceService";
    public static final String KEY_PRICE_SERVICE_IS_OFFER = "isOfferPriceService";

    //siteTour
    public static final String TABLE_SITE_TOUR = "siteTour";
    public static final String KEY_SITE_TOUR_ID = "idSiteTour";
    public static final String KEY_SITE_TOUR_NAME = "nameSiteTour";
    public static final String KEY_SITE_TOUR_DESCRIPTION = "descriptionSiteTour";
    public static final String KEY_SITE_TOUR_ADDRESS = "addressSiteTour";
    public static final String KEY_SITE_TOUR_GPS_X = "gpsXSiteTour";
    public static final String KEY_SITE_TOUR_GPS_Y = "gpsYSiteTour";
    public static final String KEY_SITE_TOUR_STATE = "stateSiteTour";

    //siteTourImage
    public static final String TABLE_SITE_TOUR_IMAGE = "siteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_ID = "idSiteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_NAME = "nameSiteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_DESCRIPTION = "descriptionSiteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_ADDRESS = "addressSiteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_ID_KEY = "idKeySiteTourImage";
    public static final String KEY_SITE_TOUR_IMAGE_STATE = "stateSiteTourImage";

    //offer
    public static final String TABLE_OFFER = "offer";
    public static final String KEY_OFFER_ID = "idOffer";
    public static final String KEY_OFFER_NAME = "nameOffer";
    public static final String KEY_OFFER_DESCRIPTION = "descriptionOffer";
    public static final String KEY_OFFER_DATE_INI = "dateIniOffer";
    public static final String KEY_OFFER_TIME_INI = "timeIniOffer";
    public static final String KEY_OFFER_DATE_FIN = "dateFinOffer";
    public static final String KEY_OFFER_TIME_FIN = "timeFinOffer";
    public static final String KEY_OFFER_ID_KEY_SERVICE = "idServiceOffer";
    public static final String KEY_OFFER_STATE = "stateOffer";
    public static final String KEY_OFFER_IMAGE = "imageOffer";
    public static final String KEY_OFFER_NAME_TYPE = "nameTypeOffer";
    public static final String KEY_OFFER_DESCRIPTION_TYPE = "nameDescriptionOffer";

    //food
    public static final String TABLE_FOOD = "food";
    public static final String KEY_FOOD_ID_AUTOINCREMENT = "idFood";
    public static final String KEY_FOOD_ID_SQL = "idFoodSQL";
    public static final String KEY_FOOD_ID_KEY_MENU = "idKeyMenuFood";
    public static final String KEY_FOOD_NAME = "nameFood";
    public static final String KEY_FOOD_STATE = "stateFood";
    public static final String KEY_FOOD_TYPE = "typeFood";
    public static final String KEY_FOOD_DESCRIPTION = "descriptionFood";
    public static final String KEY_FOOD_IMAGE = "imageFood";

    //menu_food_Menu
    public static final String TABLE_FOOD_MENU = "foodMenu";
    //public static final String KEY_FOOD_MENU_ID_AUTOINCREMENT = "idAutoincrement";
    public static final String KEY_FOOD_MENU_ID = "idFoodMenu";
    public static final String KEY_FOOD_MENU_NAME = "nameFoodMenu";
    public static final String KEY_FOOD_MENU_DATE_START = "dateStartFoodMenu";
    public static final String KEY_FOOD_MENU_DATE_END = "dateEndFoodMenu";

    //price_food
    public static final String TABLE_FOOD_PRICE = "foodPrice";
    public static final String KEY_FOOD_PRICE_ID_AUTOINCREMENT = "idFoodPrice";
    public static final String KEY_FOOD_PRICE_ID_KEY_FOOD = "idKeyFoodPrice";
    public static final String KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY = "idKeyTypeMoneyFoodPrice";
    public static final String KEY_FOOD_PRICE_TYPE_MONEY = "typeMoneyFoodPrice";
    public static final String KEY_FOOD_PRICE_PRICE = "priceFoodPrice";
    public static final String KEY_FOOD_PRICE_POINT_OBTAIN = "pointObtainFoodPrice";
    public static final String KEY_FOOD_PRICE_POINT_REQUIRED = "pointRequiredFoodPrice";
    public static final String KEY_FOOD_PRICE_UNIT = "unitFoodPrice";

    //check
    public static final String TABLE_CHECK = "checkHotel";
    public static final String KEY_CHECK_ID = "idCheck";
    public static final String KEY_CHECK_ID_STATE = "stateCheck";
    public static final String KEY_CHECK_NAME_STATE = "nameStateCheck";
    public static final String KEY_CHECK_VALUE_STATE = "valueStateCheck";
    public static final String KEY_CHECK_ID_KEY_TYPE = "idTypeCheck";
    public static final String KEY_CHECK_TYPE = "typeCheck";
    public static final String KEY_CHECK_DATE_IN = "dateInCheck";
    public static final String KEY_CHECK_TIME_IN = "timeInCheck";
    public static final String KEY_CHECK_DATE_END = "dateEndCheck";
    public static final String KEY_CHECK_TIME_END = "timeEndCheck";

    //consume
    public static final String TABLE_CONSUM = "consumHotel";
    public static final String KEY_CONSUM_ID = "idConsume";
    public static final String KEY_CONSUM_DATE_START = "dateStartConsume";
    public static final String KEY_CONSUM_TIME_START = "timeStartConsume";
    public static final String KEY_CONSUM_DATE_END = "dateEndConsume";
    public static final String KEY_CONSUM_TIME_END = "timeEndConsume";
    public static final String KEY_CONSUM_PRICE = "priceConsume";
    public static final String KEY_CONSUM_PAY = "payConsume";
    public static final String KEY_CONSUM_STATE = "stateConsume";
    public static final String KEY_CONSUM_ID_KEY_SERVICE = "idKeyServiceConsume";
    public static final String KEY_CONSUM_NAME_SERVICE = "nameServiceConsume";
    public static final String KEY_CONSUM_NAME_MONEY = "nameTypeMoneyConsume";
    public static final String KEY_CONSUM_ID_KEY_CHECK = "idKeyCheckConsume";
    public static final String KEY_CONSUM_POINT_OBTAIN = "pointObtainConsume";
    public static final String KEY_CONSUM_POINT_REQUIRED = "pointRequiredConsume";
    public static final String KEY_CONSUM_N_HOUR = "nHourConsume";
    public static final String KEY_CONSUM_N_DAY = "nDayConsume";
    public static final String KEY_CONSUM_N_UNIT = "nUnitConsume";

    //article
    public static final String TABLE_ARTICLE = "article";
    public static final String KEY_ARTICLE_ID_PRIMARY = "idPrimaryKeyArticle";
    public static final String KEY_ARTICLE_ID = "idArticle";
    public static final String KEY_ARTICLE_ID_KEY_CONSUM = "idKeyConsumeArticle";
    public static final String KEY_ARTICLE_NAME = "nameArticle";
    public static final String KEY_ARTICLE_DESCRIPTION = "descriptionArticle";
    public static final String KEY_ARTICLE_IS_ACTIVE = "isActiveArticle";

    //cardTarget
    public static final String TABLE_CARD = "cardTarget";
    public static final String KEY_CARD_ID = "idCardCard";
    public static final String KEY_CARD_ID_KEY_CHECK = "idKeyCheckCard";
    public static final String KEY_CARD_NAME_TYPE = "nameTypeCard";
    public static final String KEY_CARD_NUMBER = "numberCard";
    public static final String KEY_CARD_MONTH = "monthCard";
    public static final String KEY_CARD_YEAR = "yearCard";
    public static final String KEY_CARD_CCV = "ccvCard";
    public static final String KEY_CARD_IS_VALID = "isValidCard";
    public static final String KEY_CARD_TYPE_MONEY = "typeMoneyCard";
    public static final String KEY_CARD_DEPOSIT = "depositCard";

    //client
    public static final String TABLE_MEMBER = "clientHotel";
    public static final String KEY_MEMBER_ID = "idMember";
    public static final String KEY_MEMBER_ID_KEY_CONSUM = "idKeyConsumMember";
    public static final String KEY_MEMBER_ID_KEY_PERSON = "idKeyPersonMember";

    //message
    public static final String TABLE_MESSAGE = "messages";
    public static final String KEY_MESSAGE_ID = "idMessage";
    public static final String KEY_MESSAGE_TITTLE = "tittleMessage";
    public static final String KEY_MESSAGE_CONTENT = "contentMessage";
    public static final String KEY_MESSAGE_DATE_RECIVED = "dateRecivedMessage";
    public static final String KEY_MESSAGE_TIME_RECIVED = "timeRecivedMessage";
    public static final String KEY_MESSAGE_ISREAD = "isReadMessage";
    public static final String KEY_MESSAGE_EMAIL_SENDER = "emailRemitenteMessage";
    public static final String KEY_MESSAGE_NAME_SENDER = "nameRemitenteMessage";
    public static final String KEY_MESSAGE_ISACTIVE = "isActiveMessage";
    public static final String KEY_MESSAGE_TYPE = "typeMessage";

    //activity
    public static final String TABLE_ACTIVITY = "calendar";
    public static final String KEY_ACTIVITY_ID = "idActivity";
    public static final String KEY_ACTIVITY_DATE_START = "dateStartActivity";
    public static final String KEY_ACTIVITY_DATE_END = "dateEndActivity";
    public static final String KEY_ACTIVITY_TIME_START = "timeStartActivity";
    public static final String KEY_ACTIVITY_TIME_END = "timeEndActivity";
    public static final String KEY_ACTIVITY_NAME = "nameActivity";
    public static final String KEY_ACTIVITY_DESCRIPTION = "descriptionActivity";
    public static final String KEY_ACTIVITY_IMAGE = "imageActivity";

    //frequently
    public static final String TABLE_FREQUENTLY = "fequently";
    public static final String KEY_FREQUENTLY_ID = "idFrequently";
    public static final String KEY_FREQUENTLY_ID_INQUEST = "idInquestFrequently";
    public static final String KEY_FREQUENTLY_NAME_INQUEST = "nameInquestFrequently";
    public static final String KEY_FREQUENTLY_QUESTION = "questionFrequently";
    public static final String KEY_FREQUENTLY_RESPONSE = "responseFrequently";
    public static final String KEY_FREQUENTLY_TYPE_INQUEST = "isFrequentlyFrequently";
    public static final String KEY_FREQUENTLY_IS_ACTIVE = "isActiveFrequently";

    //consume_food
    public static final String TABLE_CONSUME_FOOD = "consumeFood";
    public static final String KEY_CONSUME_FOOD_ID = "idConsumeFood";
    public static final String KEY_CONSUME_FOOD_ID_KEY_CHECK = "idKeyCheckFood";
    public static final String KEY_CONSUME_FOOD_PRICE = "priceConsumeFood";
    public static final String KEY_CONSUME_FOOD_PAY = "payConsumeFood";
    public static final String KEY_CONSUME_FOOD_TYPE_MONEY = "typeMoneyConsumeFood";
    public static final String KEY_CONSUME_FOOD_NAME_FOOD = "nameFoodConsumeFood";
    public static final String KEY_CONSUME_FOOD_DESCRIPTION_FOOD = "descriptionFoodConsumeFood";
    public static final String KEY_CONSUME_FOOD_POINT_OBTAIN = "pointObtainConsumeFood";
    public static final String KEY_CONSUME_FOOD_POINT_REQUIRED = "pointRequiredConsumeFood";
    public static final String KEY_CONSUME_FOOD_UNIT_FOOD = "unitFoodConsumeFood";
    public static final String KEY_CONSUME_FOOD_DATE = "dateConsumeFood";
    public static final String KEY_CONSUME_FOOD_TIME = "timeConsumeFood";
    public static final String KEY_CONSUME_FOOD_STATE = "stateConsumeFood";
    public static final String KEY_CONSUME_FOOD_SITE = "siteConsumeFood";

    //occupation
    public static final String TABLE_OCCUPATION = "occupation";
    public static final String KEY_OCCUPATION_ID = "idOccupation";
    public static final String KEY_OCCUPATION_ID_CONSUME_SERVICE = "idConsumeServiceOccupartion";
    public static final String KEY_OCCUPATION_ID_ROOM = "idRoomOccupartion";
    public static final String KEY_OCCUPATION_NAME_ROOM = "nameRoomOccupartion";
    public static final String KEY_OCCUPATION_IMAGE_ROOM = "imageRoomOccupartion";
    public static final String KEY_OCCUPATION_STATE_ROOM = "stateRoomOccupartion";
    public static final String KEY_OCCUPATION_TYPE_ROOM = "typeRoomOccupartion";
    public static final String KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM = "descriptionTypeRoomOccupartion";
    public static final String KEY_OCCUPATION_N_ADULT = "nAdultOccupartion";
    public static final String KEY_OCCUPATION_N_BOY = "nBoyOccupartion";

    //reserve
    public static final String TABLE_RESERVE = "reserve";
    public static final String KEY_RESERVE_ID = "idReserve";
    public static final String KEY_RESERVE_ID_CONSUME = "idConsumeReserve";
    public static final String KEY_RESERVE_NAME_ROOM_MODEL = "nameRoomModelReserve";
    public static final String KEY_RESERVE_DESCRIPTION_ROOM_MODEL = "descriptionRoomModelReserve";
    public static final String KEY_RESERVE_N_ADULT = "nAdultReserve";
    public static final String KEY_RESERVE_N_BOY = "nBoyReserve";
    public static final String KEY_RESERVE_UNIT = "unitReserve";

    public DBSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getTableLogin());
        db.execSQL(getTablePerson());
        db.execSQL(getTableAbout());
        db.execSQL(getTableServicePrice());
        db.execSQL(getTableService());
        db.execSQL(getTableSiteTour());
        db.execSQL(getTableSiteTourImage());
        db.execSQL(getTableOffer());
        db.execSQL(getTableFood());
        db.execSQL(getTableFoodMenu());
        db.execSQL(getTableFoodPrice());
        db.execSQL(getTableCheck());
        db.execSQL(getTableConsum());
        db.execSQL(getTableArticle());
        db.execSQL(getTableCard());
        db.execSQL(getTableMember());
        db.execSQL(getTableMessages());
        db.execSQL(getTableActivity());
        db.execSQL(getTableFrequently());
        db.execSQL(getTableConsumeFood());
        db.execSQL(getTableOccupation());
        db.execSQL(getTableReserve());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //elimina la version anterior
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_ABOUT);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_PRICE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_SITE_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_SITE_TOUR_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_OFFER);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_FOOD_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_FOOD_PRICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_CHECK);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_CONSUM);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_ARTICLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_FREQUENTLY);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_CONSUME_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_OCCUPATION);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLite.TABLE_RESERVE);
        //se crea la nueva version de la tabla
        onCreate(db);
    }

    /**
     * crear tabla Login
     *
     * @return String: tabla SQL de login
     */
    private String getTableLogin() {
        return "CREATE TABLE " + TABLE_LOGIN + " ( "
                + KEY_LOGIN_ID_PERSON + " INTEGER PRIMARY KEY,"
                + KEY_LOGIN_STATE + " INTEGER,"
                + KEY_LOGIN_PASSWORD + " TEXT"
                + ")";
    }

    /**
     * crear tabla Person
     *
     * @return String: tabla SQL de person
     */
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

    /**
     * crear tabla about
     *
     * @return String: tabla SQL de about - acerca del hotel
     */
    private String getTableAbout() {
        return "CREATE TABLE " + TABLE_ABOUT + " ( "
                + KEY_ABOUT_ID + " INTEGER PRIMARY KEY,"
                + KEY_ABOUT_LOGO_HOTEL + " TEXT,"
                + KEY_ABOUT_ADDRESS_GPS_X + " TEXT,"
                + KEY_ABOUT_ADDRESS_GPS_Y + " TEXT,"
                + KEY_ABOUT_ADDRESS_IMAGE + " TEXT,"
                + KEY_ABOUT_NAME_HOTEL + " TEXT,"
                + KEY_ABOUT_PHONE_HOTEL + " TEXT,"
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
                + KEY_ABOUT_TYPE_HOTEL + " TEXT,"
                + KEY_ABOUT_SITE_WEB_HOTEL + " TEXT"
                + ")";
    }

    /**
     * crear tabla Service
     *
     * @return String: tabla SQL de  service para la lista de servicios del hotel
     */
    private String getTableService() {
        return "CREATE TABLE " + TABLE_SERVICE + " ( "
                + KEY_SERVICE_ID + " INTEGER PRIMARY KEY,"
                + KEY_SERVICE_NAME + " TEXT,"
                + KEY_SERVICE_DESCRIPTION + " TEXT,"
                + KEY_SERVICE_NAME_TYPE + " TEXT,"
                + KEY_SERVICE_IMAGE + " TEXT,"
                + KEY_SERVICE_RESERVED + " INTEGER,"
                + KEY_SERVICE_ID_TYPE + " INTEGER,"
                + KEY_SERVICE_VALUE_TYPE + " INTEGER"
                + ")";
    }

    /**
     * crear tabla servicePrice
     *
     * @return String: tabla SQL de servicePrice lista de servicios del hotel
     */
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

    /**
     * crear tabla SiteTour
     *
     * @return String: tabla SQL de siteTour, lugares turisticos
     */
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

    /**
     * crear tabla siteTourImage
     *
     * @return String: tabla SQL de imagenes del lugar turistico
     */
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

    /**
     * crear tabla Offer
     *
     * @return String: tabla SQL de ofertas q ogrece el hotel
     */
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
     * crear tabla Food
     *
     * @return String: tabla SQL de comidas
     */
    private String getTableFood() {
        return "CREATE TABLE " + TABLE_FOOD + " ( "
                + KEY_FOOD_ID_AUTOINCREMENT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FOOD_ID_SQL + " INTEGER,"
                + KEY_FOOD_ID_KEY_MENU + " INTEGER,"
                + KEY_FOOD_STATE + " INTEGER,"
                + KEY_FOOD_TYPE + " TEXT,"
                + KEY_FOOD_NAME + " TEXT,"
                + KEY_FOOD_DESCRIPTION + " TEXT,"
                + KEY_FOOD_IMAGE + " TEXT"
                + ")";
    }

    /**
     * crear tabal FoodMenu
     *
     * @return String: tablaSQL de alimentos que ofrec el menu del hotel
     */
    private String getTableFoodMenu() {
        return "CREATE TABLE " + TABLE_FOOD_MENU + " ( "
                + KEY_FOOD_MENU_ID + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_MENU_NAME + " TEXT,"
                + KEY_FOOD_MENU_DATE_START + " TEXT,"
                + KEY_FOOD_MENU_DATE_END + " TEXT"
                + ")";
    }

    /**
     * crear tabla FoodPrice
     *
     * @return String: tabla SQL de los precios de las comidas
     */
    private String getTableFoodPrice() {
        return "CREATE TABLE " + TABLE_FOOD_PRICE + " ( "
                + KEY_FOOD_PRICE_ID_AUTOINCREMENT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FOOD_PRICE_ID_KEY_FOOD + " INTEGER,"
                + KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY + " INTEGER,"
                + KEY_FOOD_PRICE_TYPE_MONEY + " TEXT,"
                + KEY_FOOD_PRICE_PRICE + " REAL,"
                + KEY_FOOD_PRICE_POINT_OBTAIN + " INTEGER,"
                + KEY_FOOD_PRICE_POINT_REQUIRED + " INTEGER,"
                + KEY_FOOD_PRICE_UNIT + " INTEGER"
                + ")";
    }

    /**
     * crear tabla Check
     *
     * @return String: tabla SQL de registros dechekIn en el hotel
     */
    private String getTableCheck() {
        return "CREATE TABLE " + TABLE_CHECK + " ( "
                + KEY_CHECK_ID + " INTEGER PRIMARY KEY,"
                + KEY_CHECK_ID_STATE + " INTEGER,"
                + KEY_CHECK_VALUE_STATE + " INTEGER,"
                + KEY_CHECK_NAME_STATE + " TEXT,"
                + KEY_CHECK_ID_KEY_TYPE + " INTEGER,"
                + KEY_CHECK_TYPE + " TEXT,"
                + KEY_CHECK_DATE_IN + " TEXT,"
                + KEY_CHECK_TIME_IN + " TEXT,"
                + KEY_CHECK_DATE_END + " TEXT,"
                + KEY_CHECK_TIME_END + " TEXT"
                + ")";
    }

    /**
     * crear tabla Consum
     *
     * @return String: tabla SQL de consumo del cliente
     */
    private String getTableConsum() {
        return "CREATE TABLE " + TABLE_CONSUM + " ( "
                + KEY_CONSUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CONSUM_DATE_START + " TEXT,"
                + KEY_CONSUM_TIME_START + " TEXT,"
                + KEY_CONSUM_DATE_END + " TEXT,"
                + KEY_CONSUM_TIME_END + " TEXT,"
                + KEY_CONSUM_PRICE + " REAL,"
                + KEY_CONSUM_PAY + " REAL,"
                + KEY_CONSUM_POINT_OBTAIN + " INTEGER,"
                + KEY_CONSUM_POINT_REQUIRED + " INTEGER,"
                + KEY_CONSUM_N_HOUR + " INTEGER,"
                + KEY_CONSUM_N_DAY + " INTEGER,"
                + KEY_CONSUM_N_UNIT + " INTEGER,"
                + KEY_CONSUM_STATE + " INTEGER,"
                + KEY_CONSUM_ID_KEY_SERVICE + " INTEGER,"
                + KEY_CONSUM_NAME_SERVICE + " TEXT,"
                + KEY_CONSUM_NAME_MONEY + " TEXT,"
                + KEY_CONSUM_ID_KEY_CHECK + " INTEGER"
                + ")";
    }

    /**
     * crear tabla Article
     *
     * @return String: tabla SQL de lista de articulos
     */
    private String getTableArticle() {
        return "CREATE TABLE " + TABLE_ARTICLE + " ( "
                + KEY_ARTICLE_ID_PRIMARY + " INTEGER PRIMARY KEY,"
                + KEY_ARTICLE_ID + " INTEGER,"
                + KEY_ARTICLE_ID_KEY_CONSUM + " INTEGER,"
                + KEY_ARTICLE_NAME + " TEXT,"
                + KEY_ARTICLE_DESCRIPTION + " TEXT,"
                + KEY_ARTICLE_IS_ACTIVE + " INTEGER"
                + ")";
    }

    /**
     * crear tabla Card
     *
     * @return String: tabla SQL de targetas de credito
     */
    private String getTableCard() {
        return "CREATE TABLE " + TABLE_CARD + " ( "
                + KEY_CARD_ID + " INTEGER PRIMARY KEY,"
                + KEY_CARD_ID_KEY_CHECK + " INTEGER,"
                + KEY_CARD_NAME_TYPE + " TEXT,"
                + KEY_CARD_NUMBER + " INTEGER,"
                + KEY_CARD_MONTH + " INTEGER,"
                + KEY_CARD_YEAR + " INTEGER,"
                + KEY_CARD_CCV + " INTEGER,"
                + KEY_CARD_IS_VALID + " INTEGER,"
                + KEY_CARD_TYPE_MONEY + " TEXT,"
                + KEY_CARD_DEPOSIT + " REAL"
                + ")";
    }

    /**
     * crear tabla member
     *
     * @return String: tabla SQL de lista de clientes registrado en un check
     */
    private String getTableMember() {
        return "CREATE TABLE " + TABLE_MEMBER + " ( "
                + KEY_MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MEMBER_ID_KEY_CONSUM + " INTEGER,"
                + KEY_MEMBER_ID_KEY_PERSON + " INTEGER"
                + ")";
    }

    /**
     * crear tabla messages
     *
     * @return String: tabla SQL de lista de mensages recividos del usuario
     */
    private String getTableMessages() {
        return "CREATE TABLE " + TABLE_MESSAGE + " ( "
                + KEY_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MESSAGE_TITTLE + " TEXT,"
                + KEY_MESSAGE_CONTENT + " TEXT,"
                + KEY_MESSAGE_DATE_RECIVED + " TEXT,"
                + KEY_MESSAGE_TIME_RECIVED + " TEXT,"
                + KEY_MESSAGE_ISREAD + " INTEGER,"
                + KEY_MESSAGE_EMAIL_SENDER + " TEXT,"
                + KEY_MESSAGE_NAME_SENDER + " TEXT,"
                + KEY_MESSAGE_TYPE + " TEXT,"
                + KEY_MESSAGE_ISACTIVE + " INTEGER"
                + ")";
    }

    /**
     * crear tabla activity
     *
     * @return String: tabla SQL de lista de actividades a realizar en el hotel
     */
    private String getTableActivity() {
        return "CREATE TABLE " + TABLE_ACTIVITY + " ( "
                + KEY_ACTIVITY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ACTIVITY_DATE_START + " TEXT,"
                + KEY_ACTIVITY_DATE_END + " TEXT,"
                + KEY_ACTIVITY_TIME_START + " TEXT,"
                + KEY_ACTIVITY_TIME_END + " TEXT,"
                + KEY_ACTIVITY_NAME + " TEXT,"
                + KEY_ACTIVITY_DESCRIPTION + " TEXT,"
                + KEY_ACTIVITY_IMAGE + " TEXT"
                + ")";
    }

    /**
     * crear tabla activity
     *
     * @return String: tabla SQL de lista de preguntas frequentes sobre el hotel
     */
    private String getTableFrequently() {
        return "CREATE TABLE " + TABLE_FREQUENTLY + " ( "
                + KEY_FREQUENTLY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FREQUENTLY_ID_INQUEST + " INTEGER,"
                + KEY_FREQUENTLY_NAME_INQUEST + " TEXT,"
                + KEY_FREQUENTLY_QUESTION + " TEXT,"
                + KEY_FREQUENTLY_RESPONSE + " TEXT,"
                + KEY_FREQUENTLY_TYPE_INQUEST + " INTEGER,"
                + KEY_FREQUENTLY_IS_ACTIVE + " INTEGER"
                + ")";
    }

    /**
     * crear la tabla consumeFood
     *
     * @return String: tabla SQL de lista de conusumos de comida
     */
    private String getTableConsumeFood() {
        return "CREATE TABLE " + TABLE_CONSUME_FOOD + " ( "
                + KEY_CONSUME_FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CONSUME_FOOD_ID_KEY_CHECK + " INTEGER,"
                + KEY_CONSUME_FOOD_PRICE + " REAL,"
                + KEY_CONSUME_FOOD_PAY + " REAL,"
                + KEY_CONSUME_FOOD_TYPE_MONEY + " TEXT,"
                + KEY_CONSUME_FOOD_NAME_FOOD + " TEXT,"
                + KEY_CONSUME_FOOD_DESCRIPTION_FOOD + " TEXT,"
                + KEY_CONSUME_FOOD_POINT_OBTAIN + " INTEGER,"
                + KEY_CONSUME_FOOD_POINT_REQUIRED + " INTEGER,"
                + KEY_CONSUME_FOOD_DATE + " TEXT,"
                + KEY_CONSUME_FOOD_TIME + " TEXT,"
                + KEY_CONSUME_FOOD_STATE + " INTEGER,"
                + KEY_CONSUME_FOOD_SITE + " TEXT,"
                + KEY_CONSUME_FOOD_UNIT_FOOD + " INTEGER"
                + ")";
    }

    /**
     * crear la tabla occupation para las habitaciones q el cliente ha usado
     *
     * @return String: tabla SQL de lista habitaciones ocupadas por el cliente
     */
    private String getTableOccupation() {
        return "CREATE TABLE " + TABLE_OCCUPATION + " ( "
                + KEY_OCCUPATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_OCCUPATION_ID_CONSUME_SERVICE + " INTEGER,"
                + KEY_OCCUPATION_ID_ROOM + " INTEGER,"
                + KEY_OCCUPATION_NAME_ROOM + " TEXT,"
                + KEY_OCCUPATION_IMAGE_ROOM + " TEXT,"
                + KEY_OCCUPATION_STATE_ROOM + " INTEGER,"
                + KEY_OCCUPATION_TYPE_ROOM + " TEXT,"
                + KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM + " TEXT,"
                + KEY_OCCUPATION_N_ADULT + " INTEGER,"
                + KEY_OCCUPATION_N_BOY + " INTEGER"
                + ")";
    }

    /**
     * crear la tabla reserve para los tipos de habitaciones reservadas por el cliente
     *
     * @return String: tabla SQL de lista habitaciones reservadas por el cliente
     */
    private String getTableReserve() {
        return "CREATE TABLE " + TABLE_RESERVE + " ( "
                + KEY_RESERVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RESERVE_ID_CONSUME + " INTEGER,"
                + KEY_RESERVE_NAME_ROOM_MODEL + " TEXT,"
                + KEY_RESERVE_DESCRIPTION_ROOM_MODEL + " TEXT,"
                + KEY_RESERVE_N_ADULT + " INTEGER,"
                + KEY_RESERVE_N_BOY + " INTEGER,"
                + KEY_RESERVE_UNIT + " INTEGER"
                + ")";
    }
}
