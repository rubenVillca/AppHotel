package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.Cursor;

import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.ActivityModel;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumModel;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.HelperParent;

import java.util.ArrayList;

public class HelperSQLiteObtain extends HelperParent {
    public HelperSQLiteObtain(Context context) {
        super(context);
    }

    /**
     * verificar q la cuenta del usuario este activa en la base de datos SQLite
     *
     * @return true: si la cuenta del usuario esta activa
     */
    public boolean isAccountActive() {
        LoginModel loginModel = getLoginModel();
        return loginModel.getState() > 0;
    }

    /**
     * Lee de la base de datos de sqlite los datos del login
     *
     * @return LoginModel: estado de la cuenta
     */
    public LoginModel getLoginModel() {
        LoginModel loginModel = new LoginModel();
        Cursor cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_LOGIN, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                loginModel = getLoginModelCursor(cursor);
                cursor.moveToNext();
            }
        }

        return loginModel;
    }

    /**
     * Lee de la base de datos de sqlite los datos de los perfiles
     *
     * @return List<PersonModel>: lista de perfiles
     */
    public PersonModel getPersonModel() {
        PersonModel personModel = new PersonModel();
        Cursor cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_PERSON, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                personModel = getPersonModelCursor(cursor);
                cursor.moveToNext();
            }
        }

        return personModel;
    }

    /**
     * Lee de la base de datos de sqlite los datos del hotel
     *
     * @return List<PersonModel>: datos del hotel
     */
    public AboutModel getAboutModel() {
        AboutModel aboutModel = new AboutModel();
        Cursor cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_ABOUT, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                aboutModel = getAboutModelCursor(cursor);
                cursor.moveToNext();
            }
        }
        return aboutModel;
    }

    /**
     * Lee de la base de datos de sqlite los servicios disponibles
     *
     * @param idService: si el idservice==0 entonces devolver la lista de todos los servicios,
     *                   y si es mayor a 0 devolver el servicio con el id seleccionado
     * @return List<ServiceModel>: lista de servicios
     */
    public ArrayList<ServiceModel> getServiceModel(int idService) {
        ArrayList<ServiceModel> listService = new ArrayList<>();
        Cursor cursor;
        if (idService > 0)
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_SERVICE
                    + " where " + DBSQLiteHelper.KEY_SERVICE_ID + "=" + idService, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_SERVICE, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServiceModel serviceModel = getServiceModelCursor(cursor);
                serviceModel.setServicePrice(getServicePriceModel(serviceModel.getServiceId()));

                listService.add(serviceModel);
                cursor.moveToNext();
            }
        }
        return listService;
    }

    /**
     * Obtener de la base de datos SQLite la lista de precios de un servicio
     *
     * @return List<ServicePriceModel>: lista de precios de un servicio
     */
    private ArrayList<ServicePriceModel> getServicePriceModel(int idService) {
        Cursor cursor = db.rawQuery("select * "
                + "from " + DBSQLiteHelper.TABLE_PRICE_SERVICE
                + " where " + DBSQLiteHelper.KEY_PRICE_SERVICE_KEY + "=" + idService, null);

        ArrayList<ServicePriceModel> listPrice = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServicePriceModel servicePriceModel = getServicePriceModelCursor(cursor);
                listPrice.add(servicePriceModel);
                cursor.moveToNext();
            }

        }
        return listPrice;
    }

    /**
     * Lee de la base de datos en sqlite los sitios turisticos
     *
     * @return List<SiteTourModel>: lista de sitios turisticos
     */
    public ArrayList<SiteTourModel> getSiteTourModel(int idSiteTour) {
        ArrayList<SiteTourModel> listSiteTour = new ArrayList<>();

        Cursor cursor;
        if (idSiteTour > 0)
            cursor = db.rawQuery("select * "
                    + "from " + DBSQLiteHelper.TABLE_SITE_TOUR
                    + " where " + DBSQLiteHelper.KEY_SITE_TOUR_ID + "=" + idSiteTour, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_SITE_TOUR, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                SiteTourModel siteTourModel = getSiteTourModelCursor(cursor);

                siteTourModel.setImagesSite(getSiteTourImageModel(siteTourModel.getIdSite()));
                listSiteTour.add(siteTourModel);
                cursor.moveToNext();
            }
        }
        return listSiteTour;
    }

    /**
     * Lee de la base de datos en sqlite los sitios turisticos
     *
     * @param idSiteTour:clave foranes del idSiteTour
     * @return List<SiteTourImageModel>: lista de sitios turisticos
     */
    public ArrayList<SiteTourImageModel> getSiteTourImageModel(int idSiteTour) {
        Cursor cursorImages = db.rawQuery("select * " + "from " + DBSQLiteHelper.TABLE_SITE_TOUR_IMAGE + " where " + DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ID_KEY + "=" + idSiteTour, null);

        ArrayList<SiteTourImageModel> listSiteTourImages = new ArrayList<>();
        if (cursorImages.moveToFirst()) {
            while (!cursorImages.isAfterLast()) {
                SiteTourImageModel siteTourImageModel = getSiteTourImagesModelCursor(cursorImages);
                listSiteTourImages.add(siteTourImageModel);
                cursorImages.moveToNext();
            }

        }
        return listSiteTourImages;
    }

    /**
     * selecciones las offertas de la base de datos SQLite y los convierte en un array
     *
     * @return offerModelArrayList: lista de ofertas disponibles
     */
    public ArrayList<OfferModel> getOfferModel(int idOffer) {
        Cursor cursor;
        if (idOffer == 0)
            cursor = db.rawQuery("select * " + "from " + DBSQLiteHelper.TABLE_OFFER, null);
        else
            cursor = db.rawQuery("select * " + "from " + DBSQLiteHelper.TABLE_OFFER + " where " + DBSQLiteHelper.KEY_OFFER_ID + "=" + idOffer, null);

        ArrayList<OfferModel> listOfferModel = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                OfferModel offerModel = getOfferModelCursor(cursor);
                offerModel.setServicePriceModel(getServicePriceModel(offerModel.getIdKeyService()));

                listOfferModel.add(offerModel);
                cursor.moveToNext();
            }

        }
        return listOfferModel;
    }

    /**
     * obtener de la base datos SQLite una lista de menus ofrecidos en el hotel
     *
     * @param idFoodMenu: id de menu
     * @return ArrayList<FoodMenuModel>: lista de alimentos ofrecidos en un menu
     */
    public ArrayList<FoodMenuModel> getFoodMenuModel(int idFoodMenu) {
        ArrayList<FoodMenuModel> listFoodMenu = new ArrayList<>();
        Cursor cursor;
        if (idFoodMenu > 0)
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_FOOD_MENU
                    + " where " + DBSQLiteHelper.KEY_FOOD_MENU_ID + "=" + idFoodMenu, null);
        else
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_FOOD_MENU, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodMenuModel foodMenuModel = getFoodMenuModelCursor(cursor);
                foodMenuModel.setFoodModelArrayList(getFoodModel(foodMenuModel.getId()));

                listFoodMenu.add(foodMenuModel);
                cursor.moveToNext();
            }
        }
        return listFoodMenu;
    }

    /**
     * obtener de la base de datos SQLite la lista de alimentos ofrecidos en e menu
     *
     * @param idMenuFood: id del menu a obtener la lista de alimentos
     * @return ArrayList<FoodModel>: lista de alimentos del menu solicitado
     */
    private ArrayList<FoodModel> getFoodModel(int idMenuFood) {
        ArrayList<FoodModel> listFood = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_FOOD
                + " where " + DBSQLiteHelper.KEY_FOOD_IDKEYMENU + "=" + idMenuFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodModel foodModel = getFoodModelCursor(cursor);
                foodModel.setListFoodPriceModel(getFoodPriceModel(foodModel.getId()));
                listFood.add(foodModel);

                cursor.moveToNext();
            }
        }
        return listFood;
    }

    /**
     * obtener de SQLite la lista de precios de un alimento
     *
     * @param idFood: id de comida
     * @return ArrayList<FoodPriceModel>: lista de precios q tiene un alimento
     */
    private ArrayList<FoodPriceModel> getFoodPriceModel(int idFood) {
        ArrayList<FoodPriceModel> listPriceFood = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_FOOD_PRICE
                + " where " + DBSQLiteHelper.KEY_FOOD_PRICE_IDKEYFOOD + "=" + idFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodPriceModel foodPriceModel = getFoodPriceModelCursor(cursor);
                listPriceFood.add(foodPriceModel);

                cursor.moveToNext();
            }
        }
        return listPriceFood;
    }

    /**
     * si idCheck=0?obtener la lista de todos los registros de estadias: obtener la lista del registro con id=idCheck
     *
     * @param idCheck: id de registro de un huesped
     * @return ArrayList<CheckModel>: lista de registros de estadia de un huesped
     */
    public ArrayList<CheckModel> getCheckModel(int idCheck) {
        ArrayList<CheckModel> listCheckModel = new ArrayList<>();
        Cursor cursor;
        if (idCheck > 0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_CHECK
                    + " where " + DBSQLiteHelper.KEY_CHECK_ID + "=" + idCheck, null);
        } else {
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_CHECK, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CheckModel checkModel = getCheckModelCursor(cursor);
                checkModel.setCardTargetArrayList(getCardModel(checkModel.getId()));
                checkModel.setConsumModelArrayList(getConsumModel(checkModel.getId()));
                listCheckModel.add(checkModel);

                cursor.moveToNext();
            }
        }
        return listCheckModel;
    }

    /**
     * obtener de la base de datos SQLite la lista de consumos de in check
     *
     * @param idCheck: id de registro
     * @return ArrayList<ConsumModel>: lista de consumos de un check
     */
    private ArrayList<ConsumModel> getConsumModel(int idCheck) {
        ArrayList<ConsumModel> listConsumModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_CONSUM
                + " where " + DBSQLiteHelper.KEY_CONSUM_ID_KEY_CHECK + "=" + idCheck, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ConsumModel consumModel = getConsumModelCursor(cursor);
                consumModel.setArticleModel(getArticleModel(consumModel.getIdConsum()));
                consumModel.setMemberModelArrayList(getMemberModel(consumModel.getIdConsum()));
                listConsumModel.add(consumModel);

                cursor.moveToNext();
            }
        }
        return listConsumModel;
    }

    /**
     * obtenerr de SQLite la lista de targetas de credito afilidas a un registro
     *
     * @param idCheck: id de registro de estadia
     * @return ArrayList<CardModel>: lista de tarjetas de credito de un registro
     */
    private ArrayList<CardModel> getCardModel(int idCheck) {
        ArrayList<CardModel> listCardModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_CARD
                + " where " + DBSQLiteHelper.KEY_CARD_ID_KEY_CHECK + "=" + idCheck, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CardModel cardModel = getCardModelCursor(cursor);
                listCardModel.add(cardModel);

                cursor.moveToNext();
            }
        }
        return listCardModel;
    }

    /**
     * obtener de la base de datos la lista de miembros del consumo
     *
     * @param idConsum: id de consumo
     * @return ArrayList<MemberModel>: lista de miembros
     */
    private ArrayList<MemberModel> getMemberModel(int idConsum) {
        ArrayList<MemberModel> listMemberModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_MEMBER
                + " where " + DBSQLiteHelper.KEY_MEMBER_ID_KEY_CONSUM + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                MemberModel memberModel = getMemberModelCursor(cursor);

                listMemberModel.add(memberModel);

                cursor.moveToNext();
            }
        }
        return listMemberModel;
    }

    /**
     * obtener de la base datos SQLite la lista de articulos entregados al ingresar
     *
     * @param idConsum: id de consumo
     * @return ArrayList<ArticleModel>: lista de articulos entregados al momento del registro
     */
    private ArrayList<ArticleModel> getArticleModel(int idConsum) {
        ArrayList<ArticleModel> listArticleModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_ARTICLE
                + " where " + DBSQLiteHelper.KEY_ARTICLE_ID_KEY_CONSUM + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArticleModel articleModel = getArticleModelCursor(cursor);

                listArticleModel.add(articleModel);

                cursor.moveToNext();
            }
        }
        return listArticleModel;
    }

    /**
     * obtener de la base deatos SQLite la lista de mensajes de un usuario
     *
     * @param idMessage: id de mensaje
     * @return ArrayList<MessageModel>: idMensaje==0?lista de todos los mensjes: lista del mensaje con id requerido
     */
    public ArrayList<MessageModel> getMessageModel(int idMessage) {
        ArrayList<MessageModel> listMessageModel = new ArrayList<>();
        Cursor cursor;
        if (idMessage == 0) {
            cursor = db.rawQuery("select *" + " from " + DBSQLiteHelper.TABLE_MESSAGE, null);
        } else {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_MESSAGE
                    + " where " + DBSQLiteHelper.KEY_MESSAGE_ID + "=" + idMessage, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                MessageModel messageModel = getMessageModelCursor(cursor);

                listMessageModel.add(messageModel);

                cursor.moveToNext();
            }
        }
        return listMessageModel;
    }

    /**
     * obtener de la base de datos la lista de actividades a realizarse en el hotel
     *
     * @param idActivity: id de actividad
     * @return ArrayList<ActivityModel>:idActivity>0?lista de todas las actividades: actividad seleccionada
     */
    public ArrayList<ActivityModel> getActivityModel(int idActivity) {
        ArrayList<ActivityModel> listActivityModel = new ArrayList<>();
        Cursor cursor;

        if (idActivity > 0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_ACTIVITY
                    + " where " + DBSQLiteHelper.KEY_ACTIVITY_ID + "=" + idActivity, null);
        } else {
            cursor = db.rawQuery("select *" + " from " + DBSQLiteHelper.TABLE_ACTIVITY, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ActivityModel activityModel = getActivityModelCursor(cursor);

                listActivityModel.add(activityModel);

                cursor.moveToNext();
            }
        }
        return listActivityModel;
    }

    //*****************************************GET_MODEL_CURSOR*************************************
    private ActivityModel getActivityModelCursor(Cursor cursor) {
        ActivityModel activityModel = new ActivityModel();

        activityModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_ID)));
        activityModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_DATE_START)));
        activityModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_DATE_END)));
        activityModel.setTimeStart(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_TIME_START)));
        activityModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_TIME_END)));
        activityModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_NAME)));
        activityModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_DESCRIPTION)));
        activityModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ACTIVITY_IMAGE)));

        return activityModel;
    }

    private MessageModel getMessageModelCursor(Cursor cursor) {
        MessageModel messageModel = new MessageModel();

        messageModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_ID)));
        messageModel.setTittle(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_TITTLE)));
        messageModel.setContent(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_CONTENT)));
        messageModel.setDateRecived(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_DATE_RECIVED)));
        messageModel.setTimeRecived(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_TIME_RECIVED)));
        messageModel.setRead(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_ISREAD)) > 0);
        messageModel.setEmailSender(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_EMAIL_SENDER)));
        messageModel.setNameSender(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_NAME_SENDER)));
        messageModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_ISACTIVE)) > 0);
        messageModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MESSAGE_TYPE)));

        return messageModel;
    }

    private ArticleModel getArticleModelCursor(Cursor cursor) {
        ArticleModel articleModel = new ArticleModel();

        articleModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_ID)));
        articleModel.setIdKeyConsum(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_ID_KEY_CONSUM)));
        articleModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_NAME)));
        articleModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_DESCRIPTION)));
        articleModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_IS_ACTIVE)) > 0);

        return articleModel;
    }

    private MemberModel getMemberModelCursor(Cursor cursor) {
        MemberModel memberModel = (MemberModel) getPersonModelCursor(cursor);

        memberModel.setTypeMember(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_TYPE_MEMBER)));
        memberModel.setIdKeyConsum(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_ID_KEY_CONSUM)));
        memberModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_ID_KEY_CHECK)));
        memberModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_ID_KEY_PERSON)));

        return memberModel;
    }

    private ConsumModel getConsumModelCursor(Cursor cursor) {
        ConsumModel consumModel = new ConsumModel();

        consumModel.setIdConsum(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID)));
        consumModel.setDateInConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_DATE_START)));
        consumModel.setTimeInConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_TIME_START)));
        consumModel.setDateOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_DATE_END)));
        consumModel.setTimeOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_TIME_END)));
        consumModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_PRICE)));
        consumModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_PAY)));
        consumModel.setTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_TYPE_ROOM)));
        consumModel.setNameRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_NAME_ROOM)));
        consumModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_STATE)) > 0);
        consumModel.setIdKeyService(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID_KEY_SERVICE)));
        consumModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID_KEY_CHECK)));

        return consumModel;
    }

    private CardModel getCardModelCursor(Cursor cursor) {
        CardModel cardModel = new CardModel();

        cardModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_ID)));
        cardModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_ID_KEY_CHECK)));
        cardModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_NAME_TYPE)));
        cardModel.setNumber(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_NUMBER)));
        cardModel.setMonth(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_MONTH)));
        cardModel.setYear(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_YEAR)));
        cardModel.setCcv(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_CCV)));
        cardModel.setValid(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_IS_VALID)) > 0);
        cardModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_TYPE_MONEY)));
        cardModel.setDeposit(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CARD_DEPOSIT)));
        return cardModel;
    }

    private PersonModel getPersonModelCursor(Cursor cursor) {
        PersonModel personModel = new PersonModel();
        personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_ID)));
        personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NAME)));
        personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NAME_LAST)));
        personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_CITY)));
        personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_ADDRESS)));
        personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_DATE_BORN)));
        personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_DATE_REGISTER)));
        personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_EMAIL)));
        personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_POINT)));
        personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_COUNTRY)));
        personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_SEX)));
        personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_IMG_PERSON)));
        return personModel;
    }

    private AboutModel getAboutModelCursor(Cursor cursor) {
        AboutModel aboutModel = new AboutModel();

        aboutModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ID)));
        aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_LOGOHOTEL)));
        aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESSGPSX)));
        aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESSGPSY)));
        aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE)));
        aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_NAMEHOTEL)));
        aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_PHONEHOTEL)));
        aboutModel.setEmail(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_EMAIL)));
        aboutModel.setAddress(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESS)));
        aboutModel.setMision(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_MISION)));
        aboutModel.setVision(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_VISION)));
        aboutModel.setFundation(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_FUNDATION)));
        aboutModel.setScope(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_SCOPE)));
        aboutModel.setHistory(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_HISTORY)));
        aboutModel.setWatchWord(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_WATCHWORD)));
        aboutModel.setObjetive(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_OBJETIVE)));
        aboutModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_DESCRIPTION)));
        aboutModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_TYPEHOTEL)));
        aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL)));

        return aboutModel;
    }

    private LoginModel getLoginModelCursor(Cursor cursor) {
        LoginModel loginModel = new LoginModel();

        loginModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_ID_PERSON)));
        loginModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_STATE)));
        loginModel.setPassword(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_PASSWORD)));

        return loginModel;
    }

    private ServiceModel getServiceModelCursor(Cursor cursor) {
        ServiceModel serviceModel = new ServiceModel();

        serviceModel.setServiceId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_ID)));
        serviceModel.setServiceReserved(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_RESERVED)));
        serviceModel.setServiceName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_NAME)));
        serviceModel.setServiceDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_DESCRIPTION)));
        serviceModel.setServiceImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_IMAGE)));
        serviceModel.setServiceType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_TYPE)));

        return serviceModel;
    }

    /**
     * @param cursor:base de datos SQLITE servicePrice
     * @return servicePriceModel: lista de precios de un servicio
     */
    private ServicePriceModel getServicePriceModelCursor(Cursor cursor) {
        ServicePriceModel servicePriceModel = new ServicePriceModel();

        servicePriceModel.setServicePriceId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_ID)));
        servicePriceModel.setServicePriceKey(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_KEY)));
        servicePriceModel.setServicePriceNameMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_NAME_MONEY)));
        servicePriceModel.setServicePriceUnit(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_UNIT)));
        servicePriceModel.setServicePriceDay(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_DAY)));
        servicePriceModel.setServicePriceHour(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_HOUR)));
        servicePriceModel.setServicePricePrice(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_PRICE)));
        servicePriceModel.setServicePricePointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_OBTAIN)));
        servicePriceModel.setServicePricePointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_REQUIRED)));
        servicePriceModel.setServicePriceIsOffer(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_IS_OFFER)) == 1);

        return servicePriceModel;
    }

    private SiteTourModel getSiteTourModelCursor(Cursor cursor) {
        SiteTourModel siteTourModel = new SiteTourModel();

        siteTourModel.setIdSite(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_ID)));
        siteTourModel.setStateSite(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_STATE)));
        siteTourModel.setNameSite(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_NAME)));
        siteTourModel.setDescriptionSite(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_DESCRIPTION)));
        siteTourModel.setAddressSite(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_ADDRESS)));
        siteTourModel.setGpsLatitudeSite(Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_GPS_X))));
        siteTourModel.setGpsLongitudeSite(Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_GPS_Y))));

        return siteTourModel;
    }

    private SiteTourImageModel getSiteTourImagesModelCursor(Cursor cursor) {
        SiteTourImageModel siteTourImageModel = new SiteTourImageModel();

        siteTourImageModel.setIdSiteTourImage(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ID)));
        siteTourImageModel.setNameSiteTourImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_NAME)));
        siteTourImageModel.setDescriptionSiteTourImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_DESCRIPTION)));
        siteTourImageModel.setStateSiteTourImage(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_STATE)));
        siteTourImageModel.setAddressSiteTour(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ADDRESS)));

        siteTourImageModel.setIdSiteTour(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ID_KEY)));

        return siteTourImageModel;
    }

    /**
     * leer los datos desde SQLite y convertirlos en objetos
     *
     * @param cursor:lista de offertas en formato RAW
     * @return offerModel: objeto de ofertas leido desde la base de datos SQLite
     */
    private OfferModel getOfferModelCursor(Cursor cursor) {
        OfferModel offerModel = new OfferModel();

        offerModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_ID)));
        offerModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_NAME)));
        offerModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_DESCRIPTION)));
        offerModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_STATE)));
        offerModel.setDateIni(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_DATE_INI)));
        offerModel.setTimeIni(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_TIME_INI)));
        offerModel.setDateFin(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_DATE_FIN)));
        offerModel.setTimeFin(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_TIME_FIN)));
        offerModel.setIdKeyService(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_ID_KEY_SERVICE)));
        offerModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_IMAGE)));
        offerModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_NAME_TYPE)));
        offerModel.setDescriptionType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OFFER_DESCRIPTION_TYPE)));
        return offerModel;
    }

    /**
     * leer los datos desde SQLite y convertirlos en objetos
     *
     * @param cursor:lista de menus de comida en formato RAW
     * @return foodMenuModel: objeto de menu leido desde la base de datos SQLite
     */
    private FoodMenuModel getFoodMenuModelCursor(Cursor cursor) {
        FoodMenuModel foodMenuModel = new FoodMenuModel();

        foodMenuModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_ID)));
        foodMenuModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_NAME)));
        foodMenuModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_DATE_START)));
        foodMenuModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_DATE_END)));

        return foodMenuModel;
    }

    private FoodModel getFoodModelCursor(Cursor cursor) {
        FoodModel foodModel = new FoodModel();

        foodModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_ID)));
        foodModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_NAME)));
        foodModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_DESCRIPTION)));
        foodModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_IMAGE)));
        foodModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_TYPE)));
        foodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_STATE)) > 0);
        foodModel.setIdKeyMenu(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_IDKEYMENU)));

        return foodModel;
    }

    private FoodPriceModel getFoodPriceModelCursor(Cursor cursor) {
        FoodPriceModel foodPriceModel = new FoodPriceModel();

        foodPriceModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_ID)));
        foodPriceModel.setIdKeyFood(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_IDKEYFOOD)));
        foodPriceModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_TYPEMONEY)));
        foodPriceModel.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_PRICE))));
        foodPriceModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_POINTOBTAIN)));
        foodPriceModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_POINTREQUIRED)));

        return foodPriceModel;
    }

    private CheckModel getCheckModelCursor(Cursor cursor) {
        CheckModel checkModel = new CheckModel();

        checkModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_ID)));
        checkModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_STATE)));
        checkModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TYPE)));
        checkModel.setDateIn(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_DATE_IN)));
        checkModel.setTimeIn(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TIME_IN)));
        checkModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_DATE_END)));
        checkModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TIME_END)));

        return checkModel;
    }
}
