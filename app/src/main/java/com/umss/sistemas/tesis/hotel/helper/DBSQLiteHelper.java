package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 57;
    public static final String DATABASE_NAME = "Hotel";

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
    static final String KEY_ABOUT_LOGO_HOTEL = "logoHotel";
    static final String KEY_ABOUT_ADDRESS_GPS_X = "addressGPSX";
    static final String KEY_ABOUT_ADDRESS_GPS_Y = "addressGPSY";
    static final String KEY_ABOUT_ADDRESS_IMAGE = "addressImage";
    static final String KEY_ABOUT_NAME_HOTEL = "nameHotel";
    static final String KEY_ABOUT_PHONE_HOTEL = "phoneHotel";
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
    static final String KEY_ABOUT_TYPE_HOTEL = "typeHotel";
    static final String KEY_ABOUT_SITE_WEB_HOTEL = "siteWebHotel";

    //services
    static final String TABLE_SERVICE = "service";
    static final String KEY_SERVICE_ID = "idService";
    static final String KEY_SERVICE_NAME = "nameService";
    static final String KEY_SERVICE_DESCRIPTION = "descriptionService";
    static final String KEY_SERVICE_IMAGE = "imageService";
    static final String KEY_SERVICE_RESERVED = "reservedService";
    static final String KEY_SERVICE_ID_TYPE = "idTypeService";
    static final String KEY_SERVICE_NAME_TYPE = "typeService";
    static final String KEY_SERVICE_VALUE_TYPE = "valueTypeService";

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
    static final String KEY_FOOD_ID_KEY_MENU = "idKeyMenuFood";
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
    static final String KEY_FOOD_PRICE_ID_KEY_FOOD = "idKeyFoodPrice";
    static final String KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY = "idKeyTypeMoneyFoodPrice";
    static final String KEY_FOOD_PRICE_TYPE_MONEY = "typeMoneyFoodPrice";
    static final String KEY_FOOD_PRICE_PRICE = "priceFoodPrice";
    static final String KEY_FOOD_PRICE_POINT_OBTAIN = "pointObtainFoodPrice";
    static final String KEY_FOOD_PRICE_POINT_REQUIRED = "pointRequiredFoodPrice";
    static final String KEY_FOOD_PRICE_UNIT = "unitFoodPrice";

    //check
    static final String TABLE_CHECK = "checkHotel";
    static final String KEY_CHECK_ID = "idCheck";
    static final String KEY_CHECK_ID_STATE = "stateCheck";
    static final String KEY_CHECK_NAME_STATE = "nameStateCheck";
    static final String KEY_CHECK_VALUE_STATE = "valueStateCheck";
    static final String KEY_CHECK_ID_KEY_TYPE = "idTypeCheck";
    static final String KEY_CHECK_TYPE = "typeCheck";
    static final String KEY_CHECK_DATE_IN = "dateInCheck";
    static final String KEY_CHECK_TIME_IN = "timeInCheck";
    static final String KEY_CHECK_DATE_END = "dateEndCheck";
    static final String KEY_CHECK_TIME_END = "timeEndCheck";

    //consume
    static final String TABLE_CONSUM = "consumHotel";
    static final String KEY_CONSUM_ID = "idConsume";
    static final String KEY_CONSUM_DATE_START = "dateStartConsume";
    static final String KEY_CONSUM_TIME_START = "timeStartConsume";
    static final String KEY_CONSUM_DATE_END = "dateEndConsume";
    static final String KEY_CONSUM_TIME_END = "timeEndConsume";
    static final String KEY_CONSUM_PRICE = "priceConsume";
    static final String KEY_CONSUM_PAY = "payConsume";
    static final String KEY_CONSUM_STATE = "stateConsume";
    static final String KEY_CONSUM_ID_KEY_SERVICE = "idKeyServiceConsume";
    static final String KEY_CONSUM_NAME_SERVICE = "nameServiceConsume";
    static final String KEY_CONSUM_NAME_MONEY = "nameTypeMoneyConsume";
    static final String KEY_CONSUM_ID_KEY_CHECK = "idKeyCheckConsume";
    static final String KEY_CONSUM_POINT_OBTAIN = "pointObtainConsume";
    static final String KEY_CONSUM_POINT_REQUIRED = "pointRequiredConsume";
    static final String KEY_CONSUM_N_HOUR = "nHourConsume";
    static final String KEY_CONSUM_N_DAY = "nDayConsume";
    static final String KEY_CONSUM_N_UNIT = "nUnitConsume";

    //article
    static final String TABLE_ARTICLE = "article";
    private static final String KEY_ARTICLE_ID_PRIMARY = "idPrimaryKeyArticle";
    static final String KEY_ARTICLE_ID = "idArticle";
    static final String KEY_ARTICLE_ID_KEY_CONSUM = "idKeyConsumeArticle";
    static final String KEY_ARTICLE_NAME = "nameArticle";
    static final String KEY_ARTICLE_DESCRIPTION = "descriptionArticle";
    static final String KEY_ARTICLE_IS_ACTIVE = "isActiveArticle";

    //cardTarget
    static final String TABLE_CARD = "cardTarget";
    static final String KEY_CARD_ID = "idCardCard";
    static final String KEY_CARD_ID_KEY_CHECK = "idKeyCheckCard";
    static final String KEY_CARD_NAME_TYPE = "nameTypeCard";
    static final String KEY_CARD_NUMBER = "numberCard";
    static final String KEY_CARD_MONTH = "monthCard";
    static final String KEY_CARD_YEAR = "yearCard";
    static final String KEY_CARD_CCV = "ccvCard";
    static final String KEY_CARD_IS_VALID = "isValidCard";
    static final String KEY_CARD_TYPE_MONEY = "typeMoneyCard";
    static final String KEY_CARD_DEPOSIT = "depositCard";

    //client
    static final String TABLE_MEMBER = "clientHotel";
    private static final String KEY_MEMBER_ID = "idMember";
    static final String KEY_MEMBER_ID_KEY_CONSUM = "idKeyConsumMember";
    static final String KEY_MEMBER_ID_KEY_PERSON = "idKeyPersonMember";

    //message
    static final String TABLE_MESSAGE = "messages";
    static final String KEY_MESSAGE_ID = "idMessage";
    static final String KEY_MESSAGE_TITTLE = "tittleMessage";
    static final String KEY_MESSAGE_CONTENT = "contentMessage";
    static final String KEY_MESSAGE_DATE_RECIVED = "dateRecivedMessage";
    static final String KEY_MESSAGE_TIME_RECIVED = "timeRecivedMessage";
    static final String KEY_MESSAGE_ISREAD = "isReadMessage";
    static final String KEY_MESSAGE_EMAIL_SENDER = "emailRemitenteMessage";
    static final String KEY_MESSAGE_NAME_SENDER = "nameRemitenteMessage";
    static final String KEY_MESSAGE_ISACTIVE = "isActiveMessage";
    static final String KEY_MESSAGE_TYPE = "typeMessage";

    //activity
    static final String TABLE_ACTIVITY = "calendar";
    static final String KEY_ACTIVITY_ID = "idActivity";
    static final String KEY_ACTIVITY_DATE_START = "dateStartActivity";
    static final String KEY_ACTIVITY_DATE_END = "dateEndActivity";
    static final String KEY_ACTIVITY_TIME_START = "timeStartActivity";
    static final String KEY_ACTIVITY_TIME_END = "timeEndActivity";
    static final String KEY_ACTIVITY_NAME = "nameActivity";
    static final String KEY_ACTIVITY_DESCRIPTION = "descriptionActivity";
    static final String KEY_ACTIVITY_IMAGE = "imageActivity";

    //frequently
    static final String TABLE_FREQUENTLY = "fequently";
    static final String KEY_FREQUENTLY_ID = "idFrequently";
    static final String KEY_FREQUENTLY_ID_INQUEST = "idInquestFrequently";
    static final String KEY_FREQUENTLY_NAME_INQUEST = "nameInquestFrequently";
    static final String KEY_FREQUENTLY_QUESTION = "questionFrequently";
    static final String KEY_FREQUENTLY_RESPONSE = "responseFrequently";
    static final String KEY_FREQUENTLY_TYPE_INQUEST = "isFrequentlyFrequently";
    static final String KEY_FREQUENTLY_IS_ACTIVE = "isActiveFrequently";

    //consume_food
    static final String TABLE_CONSUME_FOOD = "consumeFood";
    static final String KEY_CONSUME_FOOD_ID = "idConsumeFood";
    static final String KEY_CONSUME_FOOD_ID_KEY_CHECK = "idKeyCheckFood";
    static final String KEY_CONSUME_FOOD_PRICE = "priceConsumeFood";
    static final String KEY_CONSUME_FOOD_PAY = "payConsumeFood";
    static final String KEY_CONSUME_FOOD_TYPE_MONEY = "typeMoneyConsumeFood";
    static final String KEY_CONSUME_FOOD_NAME_FOOD = "nameFoodConsumeFood";
    static final String KEY_CONSUME_FOOD_DESCRIPTION_FOOD = "descriptionFoodConsumeFood";
    static final String KEY_CONSUME_FOOD_POINT_OBTAIN = "pointObtainConsumeFood";
    static final String KEY_CONSUME_FOOD_POINT_REQUIRED = "pointRequiredConsumeFood";
    static final String KEY_CONSUME_FOOD_UNIT_FOOD = "unitFoodConsumeFood";
    static final String KEY_CONSUME_FOOD_DATE = "dateConsumeFood";
    static final String KEY_CONSUME_FOOD_TIME = "timeConsumeFood";
    static final String KEY_CONSUME_FOOD_STATE = "stateConsumeFood";
    static final String KEY_CONSUME_FOOD_SITE = "siteConsumeFood";

    //occupation
    static final String TABLE_OCCUPATION = "occupation";
    private static final String KEY_OCCUPATION_ID = "idOccupation";
    static final String KEY_OCCUPATION_ID_CONSUME_SERVICE = "idConsumeServiceOccupartion";
    static final String KEY_OCCUPATION_ID_ROOM = "idRoomOccupartion";
    static final String KEY_OCCUPATION_NAME_ROOM = "nameRoomOccupartion";
    static final String KEY_OCCUPATION_IMAGE_ROOM = "imageRoomOccupartion";
    static final String KEY_OCCUPATION_STATE_ROOM = "stateRoomOccupartion";
    static final String KEY_OCCUPATION_TYPE_ROOM = "typeRoomOccupartion";
    static final String KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM = "descriptionTypeRoomOccupartion";
    static final String KEY_OCCUPATION_N_ADULT = "nAdultOccupartion";
    static final String KEY_OCCUPATION_N_BOY = "nBoyOccupartion";

    //reserve
    static final String TABLE_RESERVE = "reserve";
    private static final String KEY_RESERVE_ID = "idReserve";
    static final String KEY_RESERVE_ID_CONSUME = "idConsumeReserve";
    static final String KEY_RESERVE_NAME_ROOM_MODEL = "nameRoomModelReserve";
    static final String KEY_RESERVE_DESCRIPTION_ROOM_MODEL = "descriptionRoomModelReserve";
    static final String KEY_RESERVE_N_ADULT = "nAdultReserve";
    static final String KEY_RESERVE_N_BOY = "nBoyReserve";
    static final String KEY_RESERVE_UNIT = "unitReserve";

    public DBSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_ABOUT);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_PRICE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_SERVICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_SITE_TOUR);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_SITE_TOUR_IMAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_OFFER);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_FOOD_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_FOOD_PRICE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_CHECK);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_CONSUM);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_ARTICLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_FREQUENTLY);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_CONSUME_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_OCCUPATION);
        db.execSQL("DROP TABLE IF EXISTS " + DBSQLiteHelper.TABLE_RESERVE);
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
                + KEY_FOOD_MENU_ID_AUTOINCREMENT + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_ID + " INTEGER,"
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
                + KEY_FOOD_PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
                + KEY_CONSUM_ID + " INTEGER PRIMARY KEY,"
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
                + KEY_CONSUME_FOOD_ID + " INTEGER PRIMARY KEY,"
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
