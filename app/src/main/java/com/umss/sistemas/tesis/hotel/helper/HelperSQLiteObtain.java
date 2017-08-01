package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.Cursor;

import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.ActivityModel;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceDetailModel;
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
                loginModel = obtainLoginModelCursor(cursor);
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
    public PersonModel getPersonModel(int idPerson) {
        PersonModel personModel = new PersonModel();
        Cursor cursor;
        if (idPerson == 0) {
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_PERSON, null);
        } else {
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_PERSON + " where " + DBSQLiteHelper.KEY_PERSON_ID + "=" + idPerson, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                personModel = obtainPersonModelCursor(cursor);
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
                aboutModel = obtainAboutModelCursor(cursor);
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
                ServiceModel serviceModel = obtainServiceModelCursor(cursor);
                serviceModel.setServicePrice(getServicePriceModel(serviceModel.getId()));

                listService.add(serviceModel);
                cursor.moveToNext();
            }
        }
        return listService;
    }

    /**
     * Obtener de la base de datos SQLite la lista de precios de un servicio
     *
     * @return List<ServicePriceDetailModel>: lista de precios de un servicio
     */
    private ArrayList<ServicePriceDetailModel> getServicePriceModel(int idService) {
        Cursor cursor = db.rawQuery("select * "
                + "from " + DBSQLiteHelper.TABLE_PRICE_SERVICE
                + " where " + DBSQLiteHelper.KEY_PRICE_SERVICE_KEY + "=" + idService, null);

        ArrayList<ServicePriceDetailModel> listPrice = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServicePriceDetailModel servicePriceDetailModel = obtainServicePriceModelCursor(cursor);
                listPrice.add(servicePriceDetailModel);
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
                SiteTourModel siteTourModel = obtainSiteTourModelCursor(cursor);

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
                SiteTourImageModel siteTourImageModel = obtainSiteTourImagesModelCursor(cursorImages);
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
                OfferModel offerModel = obtainOfferModelCursor(cursor);
                offerModel.setServicePriceDetailModel(getServicePriceModel(offerModel.getIdKeyService()));

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
                FoodMenuModel foodMenuModel = obtainFoodMenuModelCursor(cursor);
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
                + " where " + DBSQLiteHelper.KEY_FOOD_ID_KEY_MENU + "=" + idMenuFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodModel foodModel = obtainFoodModelCursor(cursor);
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
                + " where " + DBSQLiteHelper.KEY_FOOD_PRICE_ID_KEY_FOOD + "=" + idFood, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FoodPriceModel foodPriceModel = obtainFoodPriceModelCursor(cursor);
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
    public ArrayList<CheckModel> getCheckModel(int idCheck, int idStateCheck, int idTypeCheck) {
        ArrayList<CheckModel> listCheckModel = new ArrayList<>();
        Cursor cursor;
        if (idCheck > 0) {//para un check especifico
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_CHECK
                    + " where " + DBSQLiteHelper.KEY_CHECK_ID + "=" + idCheck
                    +" ORDER BY "+DBSQLiteHelper.KEY_CHECK_ID+" DESC", null);
        } else {
            if (idStateCheck > 0) {//para los check con estado idState
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLiteHelper.TABLE_CHECK
                            + " where " + DBSQLiteHelper.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            + " AND " + DBSQLiteHelper.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLiteHelper.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLiteHelper.TABLE_CHECK
                            + " where " + DBSQLiteHelper.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            +" ORDER BY "+DBSQLiteHelper.KEY_CHECK_ID+" DESC", null);
                }
            } else {//para el historial
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_CHECK
                            + " where " + DBSQLiteHelper.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLiteHelper.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select * " +
                            "from " + DBSQLiteHelper.TABLE_CHECK
                            +" ORDER BY "+DBSQLiteHelper.KEY_CHECK_ID+" DESC", null);
                }
            }
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                CheckModel checkModel = obtainCheckModelCursor(cursor);
                checkModel.setCardTargetArrayList(getCardModel(checkModel.getId()));
                checkModel.setConsumeServiceModelArrayList(getConsumeModel(checkModel.getId()));
                checkModel.setConsumeFoodModelArrayList(getConsumeFoodModel(checkModel.getId()));
                listCheckModel.add(checkModel);

                cursor.moveToNext();
            }
        }
        return listCheckModel;
    }

    /**
     * lista de todos los alimentos consumidos por el huesped
     *
     * @param idCheck: id de registro de un huesped
     * @return ArrayList<ConsumeFoodModel>: lista de alimentos consumidos
     */
    private ArrayList<ConsumeFoodModel> getConsumeFoodModel(int idCheck) {
        ArrayList<ConsumeFoodModel> listConsumeFoodModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_CONSUME_FOOD
                + " where " + DBSQLiteHelper.KEY_CONSUME_FOOD_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLiteHelper.KEY_CONSUME_FOOD_ID+" DESC", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ConsumeFoodModel consumeFoodModel = obtainConsumeFoodModelCursor(cursor);
                listConsumeFoodModel.add(consumeFoodModel);

                cursor.moveToNext();
            }
        }
        return listConsumeFoodModel;
    }

    /**
     * obtener de la base de datos SQLite la lista de consumos de in check
     *
     * @param idCheck: id de registro
     * @return ArrayList<ConsumeServiceModel>: lista de consumos de un check
     */
    private ArrayList<ConsumeServiceModel> getConsumeModel(int idCheck) {
        ArrayList<ConsumeServiceModel> listConsumeServiceModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_CONSUM
                + " where " + DBSQLiteHelper.KEY_CONSUM_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLiteHelper.KEY_CONSUM_ID+" DESC", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ConsumeServiceModel consumeServiceModel = obtainConsumeModelCursor(cursor);
                consumeServiceModel.setArticleModel(getArticleModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setMemberModelArrayList(getMemberModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setOccupationModelArrayList(getOccupationModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setReserveModelArrayList(getReserveModel(consumeServiceModel.getIdConsum()));

                listConsumeServiceModel.add(consumeServiceModel);

                cursor.moveToNext();
            }
        }
        return listConsumeServiceModel;
    }

    /**
     * obtener de la base de datos la lista de reservas hechas por el cliente
     *
     * @param idConsum: identificador de consumo
     * @return listReserveModel: lista de reservas hechas por el usuario
     */
    private ArrayList<ReserveModel> getReserveModel(int idConsum) {
        ArrayList<ReserveModel> listReserveModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_RESERVE
                + " where " + DBSQLiteHelper.KEY_RESERVE_ID_CONSUME + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ReserveModel reserveModel = obtainReserveModelCursor(cursor);
                listReserveModel.add(reserveModel);

                cursor.moveToNext();
            }
        }
        return listReserveModel;
    }

    /**
     * obtener de la base de datos la lista de habitacioes ocupadas por el cliente
     *
     * @param idConsum: identificador de consumo
     * @return listOccuparionModel: lista de habitaciones occupadas
     */
    private ArrayList<OccupationModel> getOccupationModel(int idConsum) {
        ArrayList<OccupationModel> listOccupationModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_OCCUPATION
                + " where " + DBSQLiteHelper.KEY_OCCUPATION_ID_CONSUME_SERVICE + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                OccupationModel occupationModel = obtainOccupationModelCursor(cursor);
                listOccupationModel.add(occupationModel);

                cursor.moveToNext();
            }
        }
        return listOccupationModel;
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
                CardModel cardModel = obtainCardModelCursor(cursor);
                listCardModel.add(cardModel);

                cursor.moveToNext();
            }
        }
        return listCardModel;
    }

    /**
     * obtener de la base de datos la lista de miembros del consumo
     *
     * @param idConsume: id de consumo
     * @return ArrayList<MemberModel>: lista de miembros
     */
    private ArrayList<MemberModel> getMemberModel(int idConsume) {
        ArrayList<MemberModel> listMemberModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_MEMBER
                + " where " + DBSQLiteHelper.KEY_MEMBER_ID_KEY_CONSUM + "=" + idConsume, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                MemberModel memberModel = obtainMemberModelCursor(cursor);

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
                ArticleModel articleModel = obtainArticleModelCursor(cursor);

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
                MessageModel messageModel = obtainMessageModelCursor(cursor);

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
                ActivityModel activityModel = obtainActivityModelCursor(cursor);

                listActivityModel.add(activityModel);

                cursor.moveToNext();
            }
        }
        return listActivityModel;
    }

    /**
     * obtener de SQLite la lista de preguntas ferquentes, si idFrequently=0 entonces
     * lista de todas las pregutnas,
     * si idFrequently>0 entonces devolver la pregunta con el id indicado
     *
     * @param idFrequently:id de pregunta
     * @return Array: lista de preguntas frequentes
     */
    public ArrayList<FrequentlyModel> getFrequentlyModel(int idFrequently) {
        ArrayList<FrequentlyModel> listFrequentlyModel = new ArrayList<>();
        Cursor cursor;
        if (idFrequently > 0) {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLiteHelper.TABLE_FREQUENTLY
                    + " where " + DBSQLiteHelper.KEY_FREQUENTLY_ID + "=" + idFrequently, null);
        } else {
            cursor = db.rawQuery("select * from " + DBSQLiteHelper.TABLE_FREQUENTLY, null);
        }
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                FrequentlyModel frequentlyModel = obtainFrequentlyModelCursor(cursor);
                listFrequentlyModel.add(frequentlyModel);

                cursor.moveToNext();
            }
        }
        return listFrequentlyModel;

    }

    //*****************************************GET_MODEL_CURSOR*************************************
    private ActivityModel obtainActivityModelCursor(Cursor cursor) {
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

    private MessageModel obtainMessageModelCursor(Cursor cursor) {
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

    private ArticleModel obtainArticleModelCursor(Cursor cursor) {
        ArticleModel articleModel = new ArticleModel();

        articleModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_ID)));
        articleModel.setIdKeyConsum(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_ID_KEY_CONSUM)));
        articleModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_NAME)));
        articleModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_DESCRIPTION)));
        articleModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ARTICLE_IS_ACTIVE)) > 0);

        return articleModel;
    }

    private MemberModel obtainMemberModelCursor(Cursor cursorPerson) {
        MemberModel memberModel = new MemberModel();

        memberModel.setIdPerson(cursorPerson.getInt(cursorPerson.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_ID_KEY_PERSON)));
        memberModel.setIdKeyConsum(cursorPerson.getInt(cursorPerson.getColumnIndex(DBSQLiteHelper.KEY_MEMBER_ID_KEY_CONSUM)));

        Cursor cursorMember = db.rawQuery("select *"
                + " from " + DBSQLiteHelper.TABLE_PERSON
                + " where " + DBSQLiteHelper.KEY_PERSON_ID + "=" + memberModel.getIdPerson(), null);

        if (cursorMember.moveToFirst()) {
            while (!cursorMember.isAfterLast()) {
                memberModel.setNamePerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NAME)));
                memberModel.setNameLastPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NAME_LAST)));
                memberModel.setCityPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_CITY)));
                memberModel.setAddressPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_ADDRESS)));
                memberModel.setDateBornPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_DATE_BORN)));
                memberModel.setDateRegisterPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_DATE_REGISTER)));
                memberModel.setEmailPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_EMAIL)));
                memberModel.setPointPerson(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_POINT)));
                memberModel.setCountryPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_COUNTRY)));
                memberModel.setSexPerson((byte) cursorMember.getInt(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_SEX)));
                memberModel.setImgPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_IMG_PERSON)));
                memberModel.setTypeDocument(cursorMember.getString(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT)));
                memberModel.setNumberDocument(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT)));
                memberModel.setNumberPhone(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NUMBER_PHONE)));

                cursorMember.moveToNext();
            }
        }

        return memberModel;
    }

    private ConsumeServiceModel obtainConsumeModelCursor(Cursor cursor) {
        ConsumeServiceModel consumeServiceModel = new ConsumeServiceModel();

        consumeServiceModel.setIdConsum(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID)));
        consumeServiceModel.setDateInConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_DATE_START)));
        consumeServiceModel.setTimeInConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_TIME_START)));
        consumeServiceModel.setDateOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_DATE_END)));
        consumeServiceModel.setTimeOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_TIME_END)));
        consumeServiceModel.setNameService(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_NAME_SERVICE)));
        consumeServiceModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_NAME_MONEY)));
        consumeServiceModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_PRICE)));
        consumeServiceModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_PAY)));
        consumeServiceModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_STATE)) > 0);
        consumeServiceModel.setIdKeyService(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID_KEY_SERVICE)));
        consumeServiceModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_ID_KEY_CHECK)));
        consumeServiceModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_POINT_OBTAIN)));
        consumeServiceModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_POINT_REQUIRED)));
        consumeServiceModel.setnDay(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_N_DAY)));
        consumeServiceModel.setnHour(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_N_HOUR)));
        consumeServiceModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUM_N_UNIT)));

        return consumeServiceModel;
    }

    private CardModel obtainCardModelCursor(Cursor cursor) {
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

    private PersonModel obtainPersonModelCursor(Cursor cursor) {
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
        personModel.setTypeDocument(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT)));
        personModel.setNumberDocument(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT)));
        personModel.setNumberPhone(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PERSON_NUMBER_PHONE)));
        return personModel;
    }

    private AboutModel obtainAboutModelCursor(Cursor cursor) {
        AboutModel aboutModel = new AboutModel();

        aboutModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ID)));
        aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_LOGO_HOTEL)));
        aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESS_GPS_X)));
        aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESS_GPS_Y)));
        aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_ADDRESS_IMAGE)));
        aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_NAME_HOTEL)));
        aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_PHONE_HOTEL)));
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
        aboutModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_TYPE_HOTEL)));
        aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_ABOUT_SITE_WEB_HOTEL)));

        return aboutModel;
    }

    private LoginModel obtainLoginModelCursor(Cursor cursor) {
        LoginModel loginModel = new LoginModel();

        loginModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_ID_PERSON)));
        loginModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_STATE)));
        loginModel.setPassword(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_LOGIN_PASSWORD)));

        return loginModel;
    }

    private ServiceModel obtainServiceModelCursor(Cursor cursor) {
        ServiceModel serviceModel = new ServiceModel();

        serviceModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_ID)));
        serviceModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_NAME)));
        serviceModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_DESCRIPTION)));
        serviceModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_IMAGE)));
        serviceModel.setReserved(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_RESERVED)));
        serviceModel.setIdType(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_ID_TYPE)));
        serviceModel.setValueType(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_VALUE_TYPE)));
        serviceModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_SERVICE_NAME_TYPE)));

        return serviceModel;
    }

    /**
     * @param cursor:base de datos SQLITE servicePrice
     * @return servicePriceModel: lista de precios de un servicio
     */
    private ServicePriceDetailModel obtainServicePriceModelCursor(Cursor cursor) {
        ServicePriceDetailModel servicePriceDetailModel = new ServicePriceDetailModel();

        servicePriceDetailModel.setServicePriceId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_ID)));
        servicePriceDetailModel.setServicePriceKey(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_KEY)));
        servicePriceDetailModel.setServicePriceNameMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_NAME_MONEY)));
        servicePriceDetailModel.setServicePriceUnit(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_UNIT)));
        servicePriceDetailModel.setServicePriceDay(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_DAY)));
        servicePriceDetailModel.setServicePriceHour(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_HOUR)));
        servicePriceDetailModel.setServicePricePrice(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_PRICE)));
        servicePriceDetailModel.setServicePricePointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_OBTAIN)));
        servicePriceDetailModel.setServicePricePointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_REQUIRED)));
        servicePriceDetailModel.setServicePriceIsOffer(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_PRICE_SERVICE_IS_OFFER)) == 1);

        return servicePriceDetailModel;
    }

    private SiteTourModel obtainSiteTourModelCursor(Cursor cursor) {
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

    private SiteTourImageModel obtainSiteTourImagesModelCursor(Cursor cursor) {
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
    private OfferModel obtainOfferModelCursor(Cursor cursor) {
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
    private FoodMenuModel obtainFoodMenuModelCursor(Cursor cursor) {
        FoodMenuModel foodMenuModel = new FoodMenuModel();

        foodMenuModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_ID)));
        foodMenuModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_NAME)));
        foodMenuModel.setDateStart(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_DATE_START)));
        foodMenuModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_MENU_DATE_END)));

        return foodMenuModel;
    }

    private FoodModel obtainFoodModelCursor(Cursor cursor) {
        FoodModel foodModel = new FoodModel();

        foodModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_ID)));
        foodModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_NAME)));
        foodModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_DESCRIPTION)));
        foodModel.setImage(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_IMAGE)));
        foodModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_TYPE)));
        foodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_STATE)) > 0);
        foodModel.setIdKeyMenu(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_ID_KEY_MENU)));

        return foodModel;
    }

    private FoodPriceModel obtainFoodPriceModelCursor(Cursor cursor) {
        FoodPriceModel foodPriceModel = new FoodPriceModel();

        foodPriceModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_ID)));
        foodPriceModel.setIdKeyFood(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_ID_KEY_FOOD)));
        foodPriceModel.setIdKeyTypeMoneyFood(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_ID_KEY_TYPE_MONEY)));
        foodPriceModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_TYPE_MONEY)));
        foodPriceModel.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_PRICE))));
        foodPriceModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_POINT_OBTAIN)));
        foodPriceModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_POINT_REQUIRED)));
        foodPriceModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FOOD_PRICE_UNIT)));

        return foodPriceModel;
    }

    private CheckModel obtainCheckModelCursor(Cursor cursor) {
        CheckModel checkModel = new CheckModel();

        checkModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_ID)));
        checkModel.setIdState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_ID_STATE)));
        checkModel.setValueState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_VALUE_STATE)));
        checkModel.setNameState(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_NAME_STATE)));
        checkModel.setIdType(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_ID_KEY_TYPE)));
        checkModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TYPE)));
        checkModel.setDateIn(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_DATE_IN)));
        checkModel.setTimeIn(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TIME_IN)));
        checkModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_DATE_END)));
        checkModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CHECK_TIME_END)));

        return checkModel;
    }

    private FrequentlyModel obtainFrequentlyModelCursor(Cursor cursor) {
        FrequentlyModel frequentlyModel = new FrequentlyModel();

        frequentlyModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_ID)));
        frequentlyModel.setIdInquest(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_ID_INQUEST)));
        frequentlyModel.setNameInquest(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_NAME_INQUEST)));
        frequentlyModel.setQuestion(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_QUESTION)));
        frequentlyModel.setResponse(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_RESPONSE)));
        frequentlyModel.setTypeInquest(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_TYPE_INQUEST)));
        frequentlyModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_FREQUENTLY_IS_ACTIVE)) > 0);

        return frequentlyModel;
    }

    private ConsumeFoodModel obtainConsumeFoodModelCursor(Cursor cursor) {
        ConsumeFoodModel consumeFoodModel = new ConsumeFoodModel();

        consumeFoodModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_ID)));
        consumeFoodModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_ID_KEY_CHECK)));
        consumeFoodModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_PRICE)));
        consumeFoodModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_PAY)));
        consumeFoodModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_TYPE_MONEY)));
        consumeFoodModel.setNameFood(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_NAME_FOOD)));
        consumeFoodModel.setDescriptionFood(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_DESCRIPTION_FOOD)));
        consumeFoodModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_POINT_OBTAIN)));
        consumeFoodModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_POINT_REQUIRED)));
        consumeFoodModel.setUnitFood(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_UNIT_FOOD)));
        consumeFoodModel.setDateConsume(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_DATE)));
        consumeFoodModel.setTimeConsume(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_TIME)));
        consumeFoodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_STATE)));
        consumeFoodModel.setSite(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_CONSUME_FOOD_SITE)));

        return consumeFoodModel;
    }

    private OccupationModel obtainOccupationModelCursor(Cursor cursor) {
        OccupationModel occupationModel = new OccupationModel();

        occupationModel.setIdRoom(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_ID_ROOM)));
        occupationModel.setIdConsumeService(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_ID_CONSUME_SERVICE)));
        occupationModel.setNameRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_NAME_ROOM)));
        occupationModel.setImageRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_IMAGE_ROOM)));
        occupationModel.setStateRoom(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_STATE_ROOM)) > 0);
        occupationModel.setTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_TYPE_ROOM)));
        occupationModel.setDescriptionTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM)));
        occupationModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_N_ADULT)));
        occupationModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_OCCUPATION_N_BOY)));

        return occupationModel;
    }

    private ReserveModel obtainReserveModelCursor(Cursor cursor) {
        ReserveModel reserveModel = new ReserveModel();

        reserveModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_ID_CONSUME)));
        reserveModel.setNameRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_NAME_ROOM_MODEL)));
        reserveModel.setDescriptionRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_DESCRIPTION_ROOM_MODEL)));
        reserveModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_N_ADULT)));
        reserveModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_N_BOY)));
        reserveModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLiteHelper.KEY_RESERVE_UNIT)));

        return reserveModel;
    }
}
