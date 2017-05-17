package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 41;
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

    //check
    static final String TABLE_CHECK = "checkHotel";
    static final String KEY_CHECK_ID = "idCheck";
    static final String KEY_CHECK_STATE = "stateCheck";
    static final String KEY_CHECK_TYPE = "typeCheck";
    static final String KEY_CHECK_DATE_IN = "dateInCheck";
    static final String KEY_CHECK_TIME_IN = "timeInCheck";
    static final String KEY_CHECK_DATE_END = "dateEndCheck";
    static final String KEY_CHECK_TIME_END = "timeEndCheck";

    //consum
    static final String TABLE_CONSUM = "consumHotel";
    static final String KEY_CONSUM_ID = "id";
    static final String KEY_CONSUM_DATE_START = "dateStart";
    static final String KEY_CONSUM_TIME_START = "timeStart";
    static final String KEY_CONSUM_DATE_END = "dateEnd";
    static final String KEY_CONSUM_TIME_END = "timeEnd";
    static final String KEY_CONSUM_PRICE = "price";
    static final String KEY_CONSUM_PAY = "pay";
    static final String KEY_CONSUM_TYPE_ROOM = "typeRoom";
    static final String KEY_CONSUM_NAME_ROOM = "nameRoom";
    static final String KEY_CONSUM_STATE = "state";
    static final String KEY_CONSUM_ID_KEY_SERVICE = "idKeyService";
    static final String KEY_CONSUM_ID_KEY_CHECK = "idKeyCheck";

    //article
    static final String TABLE_ARTICLE = "article";
    static final String KEY_ARTICLE_ID = "idArticle";
    static final String KEY_ARTICLE_ID_KEY_CONSUM = "idkeyconsumArticle";
    static final String KEY_ARTICLE_NAME = "nameArticle";
    static final String KEY_ARTICLE_DESCRIPTION = "descriptionArticle";
    static final String KEY_ARTICLE_IS_ACTIVE = "isactiveArticle";

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
    static final String KEY_MEMBER_ID = "idMember";
    static final String KEY_MEMBER_TYPE_MEMBER = "typeMemberMember";
    static final String KEY_MEMBER_ID_KEY_CONSUM = "idKeyConsumMember";
    static final String KEY_MEMBER_ID_KEY_CHECK = "idKeyCheckMember";
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

    static final String TABLE_ACTIVITY="calendar";
    static final String KEY_ACTIVITY_ID = "idActivity";
    static final String KEY_ACTIVITY_DATE_START="dateStartActivity";
    static final String KEY_ACTIVITY_DATE_END="dateEndActivity";
    static final String KEY_ACTIVITY_TIME_START="timeStartActivity";
    static final String KEY_ACTIVITY_TIME_END="timeEndActivity";
    static final String KEY_ACTIVITY_NAME="nameActivity";
    static final String KEY_ACTIVITY_DESCRIPTION="descriptionActivity";
    static final String KEY_ACTIVITY_IMAGE="imageActivity";

    public DBSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
        String tableCheck = getCheck();
        String tableConsum = getConsum();
        String tableArticle = getArticle();
        String tableCard = getCard();
        String tableMember = getMember();
        String tableMessages = getMessages();
        String tableActivity = getActivity();

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
        db.execSQL(tableCheck);
        db.execSQL(tableConsum);
        db.execSQL(tableArticle);
        db.execSQL(tableCard);
        db.execSQL(tableMember);
        db.execSQL(tableMessages);
        db.execSQL(tableActivity);
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
                + KEY_SERVICE_TYPE + " TEXT,"
                + KEY_SERVICE_IMAGE + " TEXT,"
                + KEY_SERVICE_RESERVED + " INTEGER"
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
     * crear tabal FoodMenu
     *
     * @return String: tablaSQL de alimentos que ofrec el menu del hotel
     */
    private String getFoodMenu() {
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

    /**
     * crear tabla Check
     *
     * @return String: tabla SQL de registros dechekIn en el hotel
     */
    private String getCheck() {
        return "CREATE TABLE " + TABLE_CHECK + " ( "
                + KEY_CHECK_ID + " INTEGER PRIMARY KEY,"
                + KEY_CHECK_STATE + " INTEGER,"
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
    private String getConsum() {
        return "CREATE TABLE " + TABLE_CONSUM + " ( "
                + KEY_CONSUM_ID + " INTEGER PRIMARY KEY,"
                + KEY_CONSUM_DATE_START + " TEXT,"
                + KEY_CONSUM_TIME_START + " TEXT,"
                + KEY_CONSUM_DATE_END + " TEXT,"
                + KEY_CONSUM_TIME_END + " TEXT,"
                + KEY_CONSUM_PRICE + " REAL,"
                + KEY_CONSUM_PAY + " REAL,"
                + KEY_CONSUM_TYPE_ROOM + " TEXT,"
                + KEY_CONSUM_NAME_ROOM + " TEXT,"
                + KEY_CONSUM_STATE + " INTEGER,"
                + KEY_CONSUM_ID_KEY_SERVICE + " INTEGER,"
                + KEY_CONSUM_ID_KEY_CHECK + " INTEGER"
                + ")";
    }

    /**
     * crear tabla Article
     *
     * @return String: tabla SQL de lista de articulos
     */
    private String getArticle() {
        return "CREATE TABLE " + TABLE_ARTICLE + " ( "
                + KEY_ARTICLE_ID + " INTEGER PRIMARY KEY,"
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
    private String getCard() {
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
    private String getMember() {
        return "CREATE TABLE " + TABLE_MEMBER + " ( "
                + KEY_MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MEMBER_TYPE_MEMBER + " TEXT,"
                + KEY_MEMBER_ID_KEY_CONSUM + " INTEGER,"
                + KEY_MEMBER_ID_KEY_CHECK + " INTEGER,"
                + KEY_MEMBER_ID_KEY_PERSON + " INTEGER"
                + ")";
    }

    /**
     * crear tabla messages
     *
     * @return String: tabla SQL de lista de mensages recividos del usuario
     */
    private String getMessages() {
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
    private String getActivity() {
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
}
