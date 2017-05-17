package com.umss.sistemas.tesis.hotel.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.ActivityModel;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.ConsumModel;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FoodModel;
import com.umss.sistemas.tesis.hotel.model.FoodPriceModel;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.parent.HelperParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelperSQLiteInsert extends HelperParent {
    public HelperSQLiteInsert(Context context) {
        super(context);
    }

    /**
     * sincornizar sqlite en la tabla loginModel para poder iniciar session
     *
     * @param idPerson: identificador de la cuenta
     * @param passText: contrasena de la cuenta
     * @param state:    estado de la cuenta
     */
    public void syncUpLogin(int idPerson, String passText, int state) {
        LoginModel loginModel = new LoginModel(idPerson, passText, state);
        insertLoginSQLite(loginModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a personModel
     *
     * @param obj:objeto JSON person
     */
    public void syncUpPerson(JSONObject obj) {
        PersonModel personModel = getPersonModelJSON(obj);
        insertPersonSQLite(personModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a aboutModel
     *
     * @param obj:objeto JSON about
     */
    public void syncUpAbout(JSONObject obj) {
        AboutModel aboutModel = getAboutModelJSON(obj);
        insertAboutSQLite(aboutModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a serviceModel
     *
     * @param obj:objeto JSON service
     */
    public void syncUpService(JSONObject obj) {
        ArrayList<ServiceModel> serviceModel = getServiceModelJSON(obj);
        insertServiceSQLite(serviceModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a siteTourModel y siteTourImage
     *
     * @param obj:objeto JSON siteTour y siteTourImage
     */
    public void syncUpSiteTour(JSONObject obj) {
        ArrayList<SiteTourModel> siteTourModel = getSiteTourModelJSON(obj);
        insertSiteTourSQLite(siteTourModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a offerModel
     *
     * @param obj:objeto JSON offer
     */
    public void syncUpOffer(JSONObject obj) {
        ArrayList<OfferModel> offerModel = getOfferModelJSON(obj);
        insertOfferSQLite(offerModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a offerModel
     *
     * @param obj:objeto JSON offer
     */
    public void syncUpFoodMenu(JSONObject obj) {
        ArrayList<FoodMenuModel> foodMenuModels = getFoodMenuModelJSON(obj);
        insertFoodMenuSQLite(foodMenuModels);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a checkModel
     *
     * @param obj:objeto JSON checkModel
     */
    public void syncUpCheck(JSONObject obj) {
        ArrayList<CheckModel> checkModels = getCheckModelJSON(obj);
        insertCheckSQLite(checkModels);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a messageModel
     *
     * @param obj:objeto JSON messaggeModel
     */
    public void syncUpMessages(JSONObject obj) {
        ArrayList<MessageModel> messageModels = getMessageModelJSON(obj);
        insertMessageSQLite(messageModels);
    }

    /**
     * sincornizar webserve con SQLite la lista de actividades
     *
     * @param obj: objeto JSON activity
     */
    public void syncUpCalendar(JSONObject obj) {
        ArrayList<ActivityModel> activityModelArrayList = getActivityModelJSON(obj);
        insertActivitySQLite(activityModelArrayList);
    }

    //**************************************MODEL_JSON**********************************************

    /**
     * obtener la lista de actividades del hotel
     *
     * @param obj: objeto activity en formato JSON
     * @return ArrayList<ActivityModel>: lista de actividades del hotel
     */
    private ArrayList<ActivityModel> getActivityModelJSON(JSONObject obj) {
        ArrayList<ActivityModel> activityModelArrayList = new ArrayList<>();

        try {
            JSONArray activityJSONArray = obj.getJSONArray("calendar");

            for (int i = 0; i < activityJSONArray.length(); i++) {
                JSONObject activityObject = activityJSONArray.getJSONObject(i);

                ActivityModel activityModel = new ActivityModel();

                activityModel.setId(activityObject.getInt("ID_ACTIVITY"));
                activityModel.setDateStart(activityObject.getString("DATE_START_ACTIVITY"));
                activityModel.setDateEnd(activityObject.getString("DATE_END_ACTIVITY"));
                activityModel.setTimeStart(activityObject.getString("TIME_START_ACTIVITY"));
                activityModel.setTimeEnd(activityObject.getString("TIME_END_ACTIVITY"));
                activityModel.setName(activityObject.getString("NAME_ACTIVITY"));
                activityModel.setDescription(activityObject.getString("DESCRIPTION_ACTIVITY"));
                activityModel.setImage(activityObject.getString("IMAGE_ACTIVITY"));

                activityModelArrayList.add(activityModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return activityModelArrayList;
    }

    /**
     * obtener el message model a partir del json recivido del webserver
     *
     * @param obj: datos recividos del web server, objeto messagePerson
     * @return MessageModel: lista de mensajes recividos cnvertido en objeto
     */
    private ArrayList<MessageModel> getMessageModelJSON(JSONObject obj) {
        ArrayList<MessageModel> messagesModel = new ArrayList<>();

        try {
            JSONArray messagesJSONArray = obj.getJSONArray("messages");

            for (int i = 0; i < messagesJSONArray.length(); i++) {
                JSONObject messageObject = messagesJSONArray.getJSONObject(i);

                MessageModel messageModel = new MessageModel();

                messageModel.setId(messageObject.getInt("ID_MESSAGE"));
                messageModel.setTittle(messageObject.getString("TITTLE_MESSAGE"));
                messageModel.setContent(messageObject.getString("CONTAINER_MESSAGE"));
                messageModel.setDateRecived(messageObject.getString("DATE_MESSAGE"));
                messageModel.setTimeRecived(messageObject.getString("TIME_MESSAGE"));
                messageModel.setRead(messageObject.getInt("STATE_MESSAGE") > 0);
                messageModel.setEmailSender(messageObject.getString("EMAIL_PERSON"));
                messageModel.setNameSender(messageObject.getString("NAME_PERSON"));
                messageModel.setType(messageObject.getString("NAME_TYPE_MESSAGE"));
                messageModel.setActive(true);

                messagesModel.add(messageModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return messagesModel;
    }

    /**
     * obtener el person model a partir del json recivido del webserver
     *
     * @param obj: datos recividos del web server, objeto Person
     * @return PersonModel: modelo de la person cnvertido en objeto
     */
    private PersonModel getPersonModelJSON(JSONObject obj) {
        PersonModel personModel = new PersonModel();
        try {
            personModel.setIdPerson(obj.getInt("idPerson"));
            personModel.setEmailPerson(obj.getString("email"));
            personModel.setNamePerson(obj.getString("namePerson"));
            personModel.setNameLastPerson(obj.getString("nameLastPerson"));
            personModel.setSexPerson((byte) obj.getInt("sex"));
            personModel.setPointPerson(obj.getInt("point"));
            personModel.setCityPerson(obj.getString("city"));
            personModel.setCountryPerson(obj.getString("country"));
            personModel.setDateBornPerson(obj.getString("dateBorn"));
            personModel.setDateRegisterPerson(obj.getString("dateRegister"));
            personModel.setAddressPerson(obj.getString("address"));
            personModel.setImgPerson(obj.getString("imageProfile"));
            personModel.setNumberPhone(obj.getInt("numberPhone"));
            personModel.setNumberDocument(obj.getInt("numberDocument"));
            personModel.setTypeDocument(obj.getString("typeDocument"));
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return personModel;
    }

    /**
     * obtener el person model a partir del json recivido del webserver
     *
     * @param obj: datos recividos del web server, objeto Acerca de
     * @return AboutModel: modelo de la about convertido en objeto
     */
    private AboutModel getAboutModelJSON(JSONObject obj) {
        AboutModel aboutModel = new AboutModel();
        try {
            aboutModel.setId(1);
            aboutModel.setPhoneHotel(obj.getInt("phoneHotel"));
            aboutModel.setNameHotel(obj.getString("nameHotel"));
            aboutModel.setMision(obj.getString("mision"));
            aboutModel.setVision(obj.getString("vision"));
            aboutModel.setAddress(obj.getString("address"));
            aboutModel.setScope(obj.getString("scope"));
            aboutModel.setHistory(obj.getString("history"));
            aboutModel.setFundation(obj.getString("fundation"));
            aboutModel.setWatchWord(obj.getString("watchWord"));
            aboutModel.setObjetive(obj.getString("objetive"));
            aboutModel.setEmail(obj.getString("email"));
            aboutModel.setDescription(obj.getString("description"));
            aboutModel.setLogoHotel(obj.getString("logoHotel"));
            aboutModel.setAddressGPSX(obj.getString("addressGPSX"));
            aboutModel.setAddressGPSY(obj.getString("addressGPSY"));
            aboutModel.setAddressImage(obj.getString("addressImage"));
            aboutModel.setType(obj.getString("typeHotel"));
            aboutModel.setSiteWeb(obj.getString("siteWeb"));
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
        }
        return aboutModel;
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un array
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<ServiceModel>: JSON convertido en un array
     */
    private ArrayList<ServiceModel> getServiceModelJSON(JSONObject obj) {
        ArrayList<ServiceModel> servicesModel = new ArrayList<>();

        try {
            JSONArray servicesArray = obj.getJSONArray("services");
            int limit = servicesArray.length();

            for (int i = 0; i < limit; i++) {
                JSONObject service = servicesArray.getJSONObject(i);

                ServiceModel serviceModel = new ServiceModel();

                serviceModel.setServiceId(service.getInt("id"));
                serviceModel.setServiceReserved(service.getInt("reservable"));
                serviceModel.setServiceName(service.getString("name"));
                serviceModel.setServiceDescription(service.getString("description"));
                serviceModel.setServiceType(service.getString("type"));
                serviceModel.setServiceImage(service.getString("image"));

                serviceModel.setServicePrice(getServicePriceModelJSON(service, serviceModel.getServiceId(), false));
                servicesModel.add(serviceModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());

            e.printStackTrace();
        }

        return servicesModel;
    }

    /**
     * convertir el object en un array y huardarlo en SQLite
     *
     * @param priceServiceObject:lista de precios de un servicio
     * @param idService:idKeyService   del servicioPrice
     * @return ArrayList<SiteTourImageModel>:lista de precios del idService
     */
    private ArrayList<ServicePriceModel> getServicePriceModelJSON(JSONObject priceServiceObject, int idService, boolean isOffer) {
        ArrayList<ServicePriceModel> priceServiceArray = new ArrayList<>();

        try {
            JSONArray priceServices = priceServiceObject.getJSONArray("prices");

            for (int j = 0; j < priceServices.length(); j++) {
                ServicePriceModel servicePriceModel = new ServicePriceModel();

                JSONObject priceObject = priceServices.getJSONObject(j);

                servicePriceModel.setServicePriceId(priceObject.getInt("ID_COST_SERVICE"));
                servicePriceModel.setServicePriceKey(idService);
                servicePriceModel.setServicePriceNameMoney(priceObject.getString("NAME_TYPE_MONEY"));
                servicePriceModel.setServicePriceUnit(priceObject.getInt("UNIT_COST_SERVICE"));
                servicePriceModel.setServicePriceDay(priceObject.getInt("UNIT_DAY_COST_SERVICE"));
                servicePriceModel.setServicePriceHour(priceObject.getInt("UNIT_HOUR_COST_SERVICE"));
                servicePriceModel.setServicePricePrice(Double.parseDouble(priceObject.getString("PRICE_COST_SERVICE")));
                servicePriceModel.setServicePricePointObtain(priceObject.getInt("POINT_OBTAIN_COST_SERVICE"));
                servicePriceModel.setServicePricePointRequired(priceObject.getInt("POINT_REQUIRED_COST_SERVICE"));
                servicePriceModel.setServicePriceIsOffer(isOffer);

                priceServiceArray.add(servicePriceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return priceServiceArray;
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un array
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<SiteTourModel>: JSON convertido en un array
     */
    private ArrayList<SiteTourModel> getSiteTourModelJSON(JSONObject obj) {
        ArrayList<SiteTourModel> sitesTourModel = new ArrayList<>();

        try {
            JSONArray sitesTourArray = obj.getJSONArray("sitesTour");

            for (int i = 0; i < sitesTourArray.length(); i++) {
                JSONObject sitesObject = sitesTourArray.getJSONObject(i);

                SiteTourModel siteTourModel = new SiteTourModel();

                siteTourModel.setIdSite(sitesObject.getInt("id"));
                siteTourModel.setStateSite(sitesObject.getInt("state"));
                siteTourModel.setNameSite(sitesObject.getString("name"));
                siteTourModel.setDescriptionSite(sitesObject.getString("description"));
                siteTourModel.setAddressSite(sitesObject.getString("address"));
                siteTourModel.setGpsLatitudeSite(Float.parseFloat(sitesObject.getString("gpsX")));
                siteTourModel.setGpsLongitudeSite(Float.parseFloat(sitesObject.getString("gpsY")));

                siteTourModel.setImagesSite(getSiteTourImageModelJSON(sitesObject, siteTourModel.getIdSite()));

                sitesTourModel.add(siteTourModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());

            e.printStackTrace();
        }

        return sitesTourModel;
    }

    /**
     * convertir el object en un array
     *
     * @param sitesObject:lista     de imagenes del sitio turistico
     * @param idSiteTourModel:idKey del sitio turistico
     * @return sitesTourImageArray: lista de imagenes pertenecientes al sitio turistico con idSiteTour
     */
    private ArrayList<SiteTourImageModel> getSiteTourImageModelJSON(JSONObject sitesObject, int idSiteTourModel) {
        ArrayList<SiteTourImageModel> sitesTourImageArray = new ArrayList<>();

        try {
            JSONArray imagesSitesTour = sitesObject.getJSONArray("images");

            for (int j = 0; j < imagesSitesTour.length(); j++) {
                SiteTourImageModel siteTourImageModel = new SiteTourImageModel();

                JSONObject imgObject = imagesSitesTour.getJSONObject(j);

                siteTourImageModel.setIdSiteTourImage(imgObject.getInt("ID_IMAGE_SITE"));
                siteTourImageModel.setNameSiteTourImage(imgObject.getString("NAME_IMAGE_SITE"));
                siteTourImageModel.setDescriptionSiteTourImage(imgObject.getString("DESCRIPTION_IMAGE_SITE"));
                siteTourImageModel.setAddressSiteTour(imgObject.getString("IMAGE_SITE"));
                siteTourImageModel.setStateSiteTourImage(imgObject.getInt("STATE_IMAGE_SITE"));
                siteTourImageModel.setIdSiteTour(idSiteTourModel);

                sitesTourImageArray.add(siteTourImageModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sitesTourImageArray;
    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un array
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<OfferModel>: JSON convertido en un array
     */
    private ArrayList<OfferModel> getOfferModelJSON(JSONObject obj) {
        ArrayList<OfferModel> offerListModel = new ArrayList<>();

        try {
            JSONArray offersArray = obj.getJSONArray("offers");

            for (int i = 0; i < offersArray.length(); i++) {
                JSONObject result = offersArray.getJSONObject(i);

                OfferModel offerModel = new OfferModel();

                offerModel.setId(result.getInt("ID_OFFER"));
                offerModel.setName(result.getString("NAME_SERVICE"));
                offerModel.setDescription(result.getString("DESCRIPTION_SERVICE"));
                offerModel.setDateIni(result.getString("DATE_INI_OFFER"));
                offerModel.setTimeIni(result.getString("TIME_INI_OFFER"));
                offerModel.setDateFin(result.getString("DATE_FIN_OFFER"));
                offerModel.setTimeFin(result.getString("TIME_FIN_OFFER"));
                offerModel.setImage(result.getString("IMAGE_SERVICE"));
                offerModel.setState(result.getInt("ACTIVE_OFFER"));
                offerModel.setIdKeyService(result.getInt("ID_SERVICE"));
                offerModel.setNameType(result.getString("NAME_TYPE_SERVICE"));
                offerModel.setDescriptionType(result.getString("DESCRIPTION_TYPE_SERVICE"));

                ArrayList<ServicePriceModel> prices = getServicePriceModelJSON(result, offerModel.getIdKeyService(), true);
                offerModel.setServicePriceModel(prices);

                offerListModel.add(offerModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }

        return offerListModel;

    }

    /**
     * convertir el JSONOBJECT recibido del webserver en un un objeto
     *
     * @param obj: JSON recibido del webserver
     * @return ArrayList<FoodMenuModel>: JSON convertido en un array
     */
    private ArrayList<FoodMenuModel> getFoodMenuModelJSON(JSONObject obj) {
        ArrayList<FoodMenuModel> foodMenuListModel = new ArrayList<>();

        try {
            JSONArray foodsArray = obj.getJSONArray("foodMenu");

            for (int i = 0; i < foodsArray.length(); i++) {
                JSONObject foodJSONObject = foodsArray.getJSONObject(i);

                FoodMenuModel foodMenuModel = new FoodMenuModel();

                foodMenuModel.setId(foodJSONObject.getInt("ID_MENU"));
                foodMenuModel.setName(foodJSONObject.getString("NAME_MENU"));
                foodMenuModel.setDateStart(foodJSONObject.getString("DATE_START_MENU"));
                foodMenuModel.setDateEnd(foodJSONObject.getString("DATE_END_MENU"));

                ArrayList<FoodModel> foods = getFoodModelJSON(foodJSONObject, foodMenuModel.getId());
                foodMenuModel.setFoodModelArrayList(foods);

                foodMenuListModel.add(foodMenuModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, " + e.toString());
            e.printStackTrace();
        }

        return foodMenuListModel;
    }

    /**
     * convertir el objetoJson en objeto JAVA
     *
     * @param object:          lista de comidas en formato JSON recibido del webserver
     * @param idFoodMenuModel: clave primaria para conecta con MenuFood
     * @return ArrayList<OfferModel>: JSON convertido en un array
     */
    private ArrayList<FoodModel> getFoodModelJSON(JSONObject object, int idFoodMenuModel) {
        ArrayList<FoodModel> foodArray = new ArrayList<>();

        try {
            JSONArray foodJSONArray = object.getJSONArray("foods");

            for (int j = 0; j < foodJSONArray.length(); j++) {
                FoodModel foodModel = new FoodModel();

                JSONObject foodObject = foodJSONArray.getJSONObject(j);

                foodModel.setId(foodObject.getInt("ID_FOOD"));
                foodModel.setIdKeyMenu(idFoodMenuModel);
                foodModel.setName(foodObject.getString("NAME_FOOD"));
                foodModel.setType(foodObject.getString("NAME_TYPE_FOOD"));
                foodModel.setDescription(foodObject.getString("DESCRIPTION_FOOD"));
                foodModel.setImage(foodObject.getString("IMAGE_FOOD"));
                foodModel.setState(foodObject.getInt("VALUE_STATE_FOOD") > 0);

                ArrayList<FoodPriceModel> foodPrices = getFoodPriceModelJSON(foodObject);
                foodModel.setListFoodPriceModel(foodPrices);

                foodArray.add(foodModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodArray;
    }

    private ArrayList<FoodPriceModel> getFoodPriceModelJSON(JSONObject object) {
        ArrayList<FoodPriceModel> foodPriceArray = new ArrayList<>();

        try {
            JSONArray foodJSONArray = object.getJSONArray("prices");

            for (int j = 0; j < foodJSONArray.length(); j++) {
                FoodPriceModel foodPriceModel = new FoodPriceModel();

                JSONObject foodPriceObject = foodJSONArray.getJSONObject(j);

                foodPriceModel.setIdKeyFood(foodPriceObject.getInt("ID_FOOD"));
                foodPriceModel.setTypeMoney(foodPriceObject.getString("NAME_TYPE_MONEY"));
                foodPriceModel.setPrice(foodPriceObject.getDouble("PRICE_COST_FOOD"));
                foodPriceModel.setPointObtain(foodPriceObject.getInt("POINT_OBTAIN_COST_FOOD"));
                foodPriceModel.setPointObtain(foodPriceObject.getInt("POINT_REQUIRED_COST_FOOD"));

                foodPriceArray.add(foodPriceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return foodPriceArray;
    }

    private ArrayList<CheckModel> getCheckModelJSON(JSONObject obj) {
        ArrayList<CheckModel> checkArray = new ArrayList<>();

        try {
            JSONArray checkJSONArray = obj.getJSONArray("checks");

            for (int j = 0; j < checkJSONArray.length(); j++) {
                CheckModel checkModel = new CheckModel();

                JSONObject checkObject = checkJSONArray.getJSONObject(j);

                checkModel.setId(checkObject.getInt("ID_CHECK"));
                checkModel.setState(checkObject.getInt("VALUE_STATE_CHECK"));
                checkModel.setType(checkObject.getString("NAME_TYPE_CHECK"));
                checkModel.setDateIn(checkObject.getString("DATE_START_CHECK"));
                checkModel.setTimeIn(checkObject.getString("TIME_START_CHECK"));
                checkModel.setDateEnd(checkObject.getString("DATE_END_CHECK"));
                checkModel.setTimeEnd(checkObject.getString("TIME_END_CHECK"));
                checkModel.setCardTargetArrayList(getCardModelJSON(checkObject));
                checkModel.setConsumModelArrayList(getConsumModelJSON(checkObject));

                checkArray.add(checkModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return checkArray;
    }

    private ArrayList<CardModel> getCardModelJSON(JSONObject obj) {
        ArrayList<CardModel> cardArray = new ArrayList<>();

        try {
            JSONArray cardJSONArray = obj.getJSONArray("cards");

            for (int j = 0; j < cardJSONArray.length(); j++) {
                CardModel cardModel = new CardModel();

                JSONObject cardObject = cardJSONArray.getJSONObject(j);

                cardModel.setId(cardObject.getInt("ID_CARD"));
                cardModel.setTypeMoney(cardObject.getString("TYPE_MONEY_CARD"));
                cardModel.setDeposit(cardObject.getInt("DEPOSIT_CARD"));
                cardModel.setIdKeyCheck(cardObject.getInt("ID_CHECK"));
                cardModel.setNameType(cardObject.getString("NAME_TYPE_CARD"));
                cardModel.setNumber(cardObject.getInt("NUMBER_CARD"));
                cardModel.setMonth(cardObject.getInt("MONTH_CARD"));
                cardModel.setYear(cardObject.getInt("YEAR_CARD"));
                cardModel.setCcv(cardObject.getInt("CCV_CARD"));
                cardModel.setValid(cardObject.getInt("VALID_CARD") > 0);

                cardArray.add(cardModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cardArray;
    }

    private ArrayList<ConsumModel> getConsumModelJSON(JSONObject obj) {
        ArrayList<ConsumModel> consumArray = new ArrayList<>();

        try {
            JSONArray consumJSONArray = obj.getJSONArray("consum");

            for (int j = 0; j < consumJSONArray.length(); j++) {
                ConsumModel consumModel = new ConsumModel();

                JSONObject consumObject = consumJSONArray.getJSONObject(j);

                consumModel.setIdConsum(consumObject.getInt("ID_CONSUME_SERVICE"));
                consumModel.setIdKeyCheck(consumObject.getInt("ID_CHECK"));
                consumModel.setIdKeyService(consumObject.getInt("ID_SERVICE"));
                consumModel.setPrice(consumObject.getDouble("PRICE_CONSUME_SERVICE"));
                consumModel.setPay(consumObject.getDouble("PAY_CONSUME_SERVICE"));
                consumModel.setDateInConsum(consumObject.getString("DATE_START_CONSUME_SERVICE"));
                consumModel.setTimeInConsum(consumObject.getString("TIME_START_CONSUME_SERVICE"));
                consumModel.setDateOutConsum(consumObject.getString("DATE_END_CONSUME_SERVICE"));
                consumModel.setTimeOutConsum(consumObject.getString("TIME_END_CONSUME_SERVICE"));
                consumModel.setTypeRoom(consumObject.getString("NAME_ROOM_MODEL"));
                consumModel.setNameRoom(consumObject.getString("NAME_ROOM"));
                consumModel.setState(consumObject.getBoolean("ACTIVE_CONSUME_SERVICE"));
                consumModel.setIdService(consumObject.getInt("ID_COST_SERVICE"));
                consumModel.setMemberModelArrayList(getMemberModelJSON(consumObject));
                consumModel.setArticleModel(getArticleModelJSON(consumObject));

                consumArray.add(consumModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumArray;
    }

    private ArrayList<ArticleModel> getArticleModelJSON(JSONObject obj) {
        ArrayList<ArticleModel> articleArray = new ArrayList<>();

        try {
            JSONArray articleJSONArray = obj.getJSONArray("articles");

            for (int j = 0; j < articleJSONArray.length(); j++) {
                ArticleModel articleModel = new ArticleModel();

                JSONObject articleObject = articleJSONArray.getJSONObject(j);

                articleModel.setId(articleObject.getInt(""));
                articleModel.setIdKeyConsum(articleObject.getInt(""));
                articleModel.setName(articleObject.getString(""));
                articleModel.setDescription(articleObject.getString(""));
                articleModel.setActive(articleObject.getInt("") > 0);

                articleArray.add(articleModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return articleArray;
    }

    private ArrayList<MemberModel> getMemberModelJSON(JSONObject obj) {
        ArrayList<MemberModel> memberArray = new ArrayList<>();

        try {
            JSONArray memberJSONArray = obj.getJSONArray("member");

            for (int j = 0; j < memberJSONArray.length(); j++) {
                MemberModel memberModel = new MemberModel();

                JSONObject memberObject = memberJSONArray.getJSONObject(j);

                memberModel.setIdKeyConsum(memberObject.getInt("ID_CONSUME_SERVICE"));
                memberModel.setIdKeyCheck(memberObject.getInt("ID_CHECK"));
                memberModel.setTypeMember(memberObject.getString(""));
                memberModel.setIdPerson(obj.getInt("ID_PERSON"));
                memberModel.setEmailPerson(obj.getString("EMAIL_PERSON"));
                memberModel.setNamePerson(obj.getString("NAME_PERSON"));
                memberModel.setNameLastPerson(obj.getString("NAME_LAST_PERSON"));
                memberModel.setSexPerson((byte) obj.getInt("SEX_PERSON"));
                memberModel.setPointPerson(obj.getInt("POINT_PERSON"));
                memberModel.setCityPerson(obj.getString("CITY_PERSON"));
                memberModel.setCountryPerson(obj.getString("COUNTRY_PERSON"));
                memberModel.setDateBornPerson(obj.getString("DATE_BORN_PERSON"));
                memberModel.setDateRegisterPerson(obj.getString("DATE_REGISTER_PERSON"));
                memberModel.setAddressPerson(obj.getString("ADDRESS_PERSON"));
                memberModel.setImgPerson(obj.getString("IMAGE_PROFILE_PERSON"));
                memberModel.setNumberPhone(obj.getInt("NUMBER_PHONE_PERSON"));
                memberModel.setNumberDocument(obj.getInt("NUMBER_DOCUMENT_PERSON"));
                memberModel.setTypeDocument(obj.getString("TYPE_DOCUMENT_PERSON"));
                memberArray.add(memberModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return memberArray;
    }

    //*****************************************INSERT_SQLITE****************************************

    /**
     * ingresar loginModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param loginModel: objeto a ingresar a la base de datos sqlite
     */
    private void insertLoginSQLite(LoginModel loginModel) {
        ContentValues newRegister = new ContentValues();
        newRegister.put(DBSQLiteHelper.KEY_LOGIN_ID_PERSON, loginModel.getIdPerson());
        newRegister.put(DBSQLiteHelper.KEY_LOGIN_PASSWORD, loginModel.getPassword());
        newRegister.put(DBSQLiteHelper.KEY_LOGIN_STATE, loginModel.getState());

        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_LOGIN);
        db.insert(DBSQLiteHelper.TABLE_LOGIN, null, newRegister);
    }

    /**
     * ingresar personModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param personModel: objeto a ingresar a la base dedd atos sqlite
     */
    private void insertPersonSQLite(PersonModel personModel) {
        ContentValues newRegister = new ContentValues();
        newRegister.put(DBSQLiteHelper.KEY_PERSON_ID, personModel.getIdPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_EMAIL, personModel.getEmailPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_NAME, personModel.getNamePerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_NAME_LAST, personModel.getNameLastPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_CITY, personModel.getCityPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_COUNTRY, personModel.getCountryPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_POINT, personModel.getPointPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_SEX, personModel.getSexPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_ADDRESS, personModel.getAddressPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_IMG_PERSON, personModel.getImgPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_DATE_BORN, personModel.getDateBornPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_DATE_REGISTER, personModel.getDateRegisterPerson());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT, personModel.getTypeDocument());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT, personModel.getNumberDocument());
        newRegister.put(DBSQLiteHelper.KEY_PERSON_NUMBER_PHONE, personModel.getNumberPhone());

        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_PERSON);
        db.insert(DBSQLiteHelper.TABLE_PERSON, null, newRegister);
    }

    /**
     * ingresar aboutModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param aboutModel: objeto a ingresar a la base ded datos sqlite
     */
    private void insertAboutSQLite(AboutModel aboutModel) {
        ContentValues newRegister = new ContentValues();

        newRegister.put(DBSQLiteHelper.KEY_ABOUT_ID, aboutModel.getId());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_PHONEHOTEL, aboutModel.getPhoneHotel());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_NAMEHOTEL, aboutModel.getNameHotel());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_MISION, aboutModel.getMision());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_VISION, aboutModel.getVision());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_ADDRESS, aboutModel.getAddress());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_SCOPE, aboutModel.getScope());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_HISTORY, aboutModel.getHistory());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_FUNDATION, aboutModel.getFundation());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_WATCHWORD, aboutModel.getWatchWord());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_OBJETIVE, aboutModel.getObjetive());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_EMAIL, aboutModel.getEmail());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_DESCRIPTION, aboutModel.getDescription());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_LOGOHOTEL, aboutModel.getLogoHotel());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_ADDRESSGPSX, aboutModel.getAddressGPSX());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_ADDRESSGPSY, aboutModel.getAddressGPSY());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE, aboutModel.getAddressImage());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_TYPEHOTEL, aboutModel.getType());
        newRegister.put(DBSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL, aboutModel.getSiteWeb());

        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_ABOUT);
        db.insert(DBSQLiteHelper.TABLE_ABOUT, null, newRegister);
    }

    /**
     * ingresar la lista de servicios en la base da datos SQLite
     *
     * @param servicesModel: lista de servicios
     */
    private void insertServiceSQLite(ArrayList<ServiceModel> servicesModel) {
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_PRICE_SERVICE
                + " WHERE " + DBSQLiteHelper.KEY_PRICE_SERVICE_IS_OFFER + "=0");
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_SERVICE);

        for (ServiceModel serviceModel : servicesModel) {
            ContentValues serviceContent = new ContentValues();

            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_ID, serviceModel.getServiceId());
            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_NAME, serviceModel.getServiceName());
            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_TYPE, serviceModel.getServiceType());
            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_DESCRIPTION, serviceModel.getServiceDescription());
            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_IMAGE, serviceModel.getServiceImage());
            serviceContent.put(DBSQLiteHelper.KEY_SERVICE_RESERVED, serviceModel.getServiceReserved());

            db.insert(DBSQLiteHelper.TABLE_SERVICE, null, serviceContent);

            insertServicePriceSQLite(serviceModel.getServicePrice());
        }
    }

    /**
     * ingresar la lista de serviciosPrice en la base da datos SQLite
     *
     * @param servicesPriceModel: lista de precios de servicios
     */
    private void insertServicePriceSQLite(ArrayList<ServicePriceModel> servicesPriceModel) {
        for (ServicePriceModel servicePriceModel : servicesPriceModel) {
            ContentValues serviceContent = new ContentValues();

            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_ID, servicePriceModel.getServicePriceId());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_KEY, servicePriceModel.getServicePriceKey());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_DAY, servicePriceModel.getServicePriceDay());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_HOUR, servicePriceModel.getServicePriceHour());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_NAME_MONEY, servicePriceModel.getServicePriceNameMoney());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_PRICE, servicePriceModel.getServicePricePrice());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_UNIT, servicePriceModel.getServicePriceUnit());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_OBTAIN, servicePriceModel.getServicePricePointObtain());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_POINT_REQUIRED, servicePriceModel.getServicePricePointRequired());
            serviceContent.put(DBSQLiteHelper.KEY_PRICE_SERVICE_IS_OFFER, String.valueOf(servicePriceModel.isServicePriceIsOffer() ? 1 : 0));

            db.insert(DBSQLiteHelper.TABLE_PRICE_SERVICE, null, serviceContent);
        }
    }

    /**
     * ingresar la lista de sitios turisticos en la base da datos SQLite
     *
     * @param sitesTourModel: lista de lugares turisticos
     */
    private void insertSiteTourSQLite(ArrayList<SiteTourModel> sitesTourModel) {
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_SITE_TOUR);
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_SITE_TOUR_IMAGE);

        for (SiteTourModel siteTourModel : sitesTourModel) {
            ContentValues siteTourContent = new ContentValues();

            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_ID, siteTourModel.getIdSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_NAME, siteTourModel.getNameSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_DESCRIPTION, siteTourModel.getDescriptionSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_STATE, siteTourModel.getStateSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_ADDRESS, siteTourModel.getAddressSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_GPS_X, siteTourModel.getGpsLatitudeSite());
            siteTourContent.put(DBSQLiteHelper.KEY_SITE_TOUR_GPS_Y, siteTourModel.getGpsLongitudeSite());

            insertSiteTourImageSQLite(siteTourModel);

            db.insert(DBSQLiteHelper.TABLE_SITE_TOUR, null, siteTourContent);
        }
    }

    /**
     * guardar lista de images de los sitios turisticos en sqlite
     *
     * @param siteTourModel:lista de sitios tiristicos
     */
    private void insertSiteTourImageSQLite(SiteTourModel siteTourModel) {
        for (SiteTourImageModel siteImage : siteTourModel.getImagesSite()) {
            ContentValues siteTourImageContent = new ContentValues();

            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ID, siteImage.getIdSiteTourImage());
            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_STATE, siteImage.getStateSiteTourImage());
            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_NAME, siteImage.getNameSiteTourImage());
            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_DESCRIPTION, siteImage.getDescriptionSiteTourImage());
            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ADDRESS, siteImage.getAddressSiteTour());

            siteTourImageContent.put(DBSQLiteHelper.KEY_SITE_TOUR_IMAGE_ID_KEY, siteTourModel.getIdSite());

            db.insert(DBSQLiteHelper.TABLE_SITE_TOUR_IMAGE, null, siteTourImageContent);
        }
    }

    /**
     * guardar lista de images de ofertas en sqlite
     *
     * @param offerModel:lista offertas
     */
    private void insertOfferSQLite(ArrayList<OfferModel> offerModel) {
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_PRICE_SERVICE
                + " WHERE " + DBSQLiteHelper.KEY_PRICE_SERVICE_IS_OFFER + "=1");
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_OFFER);
        for (OfferModel offer : offerModel) {
            ContentValues offerContent = new ContentValues();

            offerContent.put(DBSQLiteHelper.KEY_OFFER_ID, offer.getId());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_STATE, offer.getState());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_NAME, offer.getName());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_DESCRIPTION, offer.getDescription());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_DATE_INI, offer.getDateIni());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_TIME_INI, offer.getTimeIni());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_DATE_FIN, offer.getDateFin());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_TIME_FIN, offer.getTimeFin());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_ID_KEY_SERVICE, offer.getIdKeyService());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_IMAGE, offer.getImage());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_NAME_TYPE, offer.getNameType());
            offerContent.put(DBSQLiteHelper.KEY_OFFER_DESCRIPTION_TYPE, offer.getDescriptionType());

            db.insert(DBSQLiteHelper.TABLE_OFFER, null, offerContent);

            insertServicePriceSQLite(offer.getServicePriceModel());
        }
    }

    /**
     * guardar lista de menus de comida en la base de datos SQLIte
     *
     * @param foodMenuModels:lista menus de comida
     */
    private void insertFoodMenuSQLite(ArrayList<FoodMenuModel> foodMenuModels) {
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_FOOD);
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_FOOD_MENU);
        db.execSQL("DELETE FROM " + DBSQLiteHelper.TABLE_FOOD_PRICE);

        for (FoodMenuModel foodMenuModel : foodMenuModels) {
            insertFoodSQLite(foodMenuModel.getFoodModelArrayList());

            ContentValues foodMenuContent = new ContentValues();

            foodMenuContent.put(DBSQLiteHelper.KEY_FOOD_MENU_ID, foodMenuModel.getId());
            foodMenuContent.put(DBSQLiteHelper.KEY_FOOD_MENU_NAME, foodMenuModel.getName());
            foodMenuContent.put(DBSQLiteHelper.KEY_FOOD_MENU_DATE_START, foodMenuModel.getDateStart());
            foodMenuContent.put(DBSQLiteHelper.KEY_FOOD_MENU_DATE_END, foodMenuModel.getDateEnd());
            long success = db.insert(DBSQLiteHelper.TABLE_FOOD_MENU, null, foodMenuContent);
            if (success == -1)
                System.out.println("Ocurrio un error al inserar la consulta en FoodMenuModel");

        }
    }

    /**
     * guardar lista de comidas en la base de datos SQLIte
     *
     * @param foodModelArrayList:lista comids en formato JAVa
     */
    private void insertFoodSQLite(ArrayList<FoodModel> foodModelArrayList) {
        for (FoodModel foodModel : foodModelArrayList) {
            insertFoodPriceSQLite(foodModel.getListFoodPriceModel());

            ContentValues foodContent = new ContentValues();

            foodContent.put(DBSQLiteHelper.KEY_FOOD_ID, foodModel.getId());
            foodContent.put(DBSQLiteHelper.KEY_FOOD_IDKEYMENU, foodModel.getIdKeyMenu());
            foodContent.put(DBSQLiteHelper.KEY_FOOD_STATE, foodModel.isState() ? 1 : 0);
            foodContent.put(DBSQLiteHelper.KEY_FOOD_TYPE, foodModel.getType());
            foodContent.put(DBSQLiteHelper.KEY_FOOD_NAME, foodModel.getName());
            foodContent.put(DBSQLiteHelper.KEY_FOOD_DESCRIPTION, foodModel.getDescription());
            foodContent.put(DBSQLiteHelper.KEY_FOOD_IMAGE, foodModel.getImage());

            long succes = db.insert(DBSQLiteHelper.TABLE_FOOD, null, foodContent);
            if (succes == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodModel");
        }
    }

    /**
     * guardar lista de precios de comidas en la base de datos SQLIte
     *
     * @param foodPriceModelArrayList:lista de precios de las comidas en formato JAVa
     */
    private void insertFoodPriceSQLite(ArrayList<FoodPriceModel> foodPriceModelArrayList) {
        for (FoodPriceModel foodPriceModel : foodPriceModelArrayList) {
            ContentValues foodPriceContent = new ContentValues();

            foodPriceContent.put(DBSQLiteHelper.KEY_FOOD_PRICE_IDKEYFOOD, foodPriceModel.getIdKeyFood());
            foodPriceContent.put(DBSQLiteHelper.KEY_FOOD_PRICE_TYPEMONEY, foodPriceModel.getTypeMoney());
            foodPriceContent.put(DBSQLiteHelper.KEY_FOOD_PRICE_PRICE, foodPriceModel.getPrice());
            foodPriceContent.put(DBSQLiteHelper.KEY_FOOD_PRICE_POINTOBTAIN, foodPriceModel.getPointObtain());
            foodPriceContent.put(DBSQLiteHelper.KEY_FOOD_PRICE_POINTREQUIRED, foodPriceModel.getPointRequired());

            long succes = db.insert(DBSQLiteHelper.TABLE_FOOD_PRICE, null, foodPriceContent);
            if (succes == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * guardar registro de ingresos al hotel en la base de datos SQLIte
     *
     * @param checkModels:lista de registros formato JAVa
     */
    private void insertCheckSQLite(ArrayList<CheckModel> checkModels) {
        for (CheckModel checkModel : checkModels) {
            ContentValues checkContent = new ContentValues();

            checkContent.put(DBSQLiteHelper.KEY_CHECK_ID, checkModel.getId());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_STATE, checkModel.getState());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_TYPE, checkModel.getType());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_DATE_IN, checkModel.getDateIn());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_TIME_IN, checkModel.getTimeIn());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_DATE_END, checkModel.getDateEnd());
            checkContent.put(DBSQLiteHelper.KEY_CHECK_TIME_END, checkModel.getTimeEnd());

            if (db.insert(DBSQLiteHelper.TABLE_CHECK, null, checkContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            insertTargetSQLite(checkModel.getCardTargetArrayList());
            insertConsumSQLite(checkModel.getConsumModelArrayList());
        }
    }

    /**
     * guardar targetas de credito  en la base de datos SQLIte
     *
     * @param cardTargetArrayList:lista de targetas de credito
     */
    private void insertTargetSQLite(ArrayList<CardModel> cardTargetArrayList) {
        for (CardModel cardModel : cardTargetArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_CARD_ID, cardModel.getId());
            contentValues.put(DBSQLiteHelper.KEY_CARD_ID_KEY_CHECK, cardModel.getIdKeyCheck());
            contentValues.put(DBSQLiteHelper.KEY_CARD_NAME_TYPE, cardModel.getNameType());
            contentValues.put(DBSQLiteHelper.KEY_CARD_NUMBER, cardModel.getNumber());
            contentValues.put(DBSQLiteHelper.KEY_CARD_MONTH, cardModel.getMonth());
            contentValues.put(DBSQLiteHelper.KEY_CARD_YEAR, cardModel.getYear());
            contentValues.put(DBSQLiteHelper.KEY_CARD_CCV, cardModel.getCcv());
            contentValues.put(DBSQLiteHelper.KEY_CARD_IS_VALID, cardModel.isValid());
            contentValues.put(DBSQLiteHelper.KEY_CARD_TYPE_MONEY, cardModel.getTypeMoney());
            contentValues.put(DBSQLiteHelper.KEY_CARD_DEPOSIT, cardModel.getDeposit());

            if (db.insert(DBSQLiteHelper.TABLE_CARD, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * guardar registro consumos del huesped en la base de datos SQLIte
     *
     * @param consumModelArrayList:lista de consumos hechos por el cliente en la cuenta del check  formato JAVa
     */
    private void insertConsumSQLite(ArrayList<ConsumModel> consumModelArrayList) {
        for (ConsumModel consumModel : consumModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_CONSUM_ID, consumModel.getIdConsum());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_DATE_START, consumModel.getDateInConsum());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_TIME_START, consumModel.getTimeInConsum());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_DATE_END, consumModel.getDateOutConsum());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_TIME_END, consumModel.getTimeOutConsum());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_PRICE, consumModel.getPrice());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_PAY, consumModel.getPay());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_TYPE_ROOM, consumModel.getTypeRoom());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_NAME_ROOM, consumModel.getNameRoom());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_STATE, consumModel.isState());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_ID_KEY_SERVICE, consumModel.getIdKeyService());
            contentValues.put(DBSQLiteHelper.KEY_CONSUM_ID_KEY_CHECK, consumModel.getIdKeyCheck());

            if (db.insert(DBSQLiteHelper.TABLE_CONSUM, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            insertArticleSQLite(consumModel.getArticleModel());
            insertMemberSQLite(consumModel.getMemberModelArrayList());
        }
    }

    /**
     * guardar los ariculos q recibio el huesped base de datos SQLIte
     *
     * @param articleModels:lista de articulos q el cliente recibio al ingrear al hotel  formato JAVa
     */
    private void insertArticleSQLite(ArrayList<ArticleModel> articleModels) {
        for (ArticleModel articleModel : articleModels) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_ARTICLE_ID, articleModel.getId());
            contentValues.put(DBSQLiteHelper.KEY_ARTICLE_ID_KEY_CONSUM, articleModel.getIdKeyConsum());
            contentValues.put(DBSQLiteHelper.KEY_ARTICLE_NAME, articleModel.getName());
            contentValues.put(DBSQLiteHelper.KEY_ARTICLE_DESCRIPTION, articleModel.getDescription());
            contentValues.put(DBSQLiteHelper.KEY_ARTICLE_IS_ACTIVE, articleModel.isActive());

            if (db.insert(DBSQLiteHelper.TABLE_ARTICLE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * guardar lista de miembros del check que ingresaron al hotel en la base de datos SQLIte
     *
     * @param memberModelArrayList:lista de clientes registrados con un check check  formato JAVa
     */
    private void insertMemberSQLite(ArrayList<MemberModel> memberModelArrayList) {
        for (MemberModel memberModel : memberModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_MEMBER_TYPE_MEMBER, memberModel.getTypeMember());
            contentValues.put(DBSQLiteHelper.KEY_MEMBER_ID_KEY_CONSUM, memberModel.getIdKeyConsum());
            contentValues.put(DBSQLiteHelper.KEY_MEMBER_ID_KEY_CHECK, memberModel.getIdKeyCheck());
            contentValues.put(DBSQLiteHelper.KEY_MEMBER_ID_KEY_PERSON, memberModel.getIdPerson());

            if (db.insert(DBSQLiteHelper.TABLE_MEMBER, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            insertPersonSQLite(memberModel);
        }
    }

    /**
     * guradar en SQLite la lista de mensajes recividos
     *
     * @param messageModels: lista de mensajes recibidos
     */
    private void insertMessageSQLite(ArrayList<MessageModel> messageModels) {
        for (MessageModel messageModel : messageModels) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_ID, messageModel.getId());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_TITTLE, messageModel.getTittle());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_CONTENT, messageModel.getContent());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_DATE_RECIVED, messageModel.getDateRecived());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_TIME_RECIVED, messageModel.getTimeRecived());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_ISREAD, messageModel.isRead());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_EMAIL_SENDER, messageModel.getEmailSender());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_NAME_SENDER, messageModel.getNameSender());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_TYPE,messageModel.getType());
            contentValues.put(DBSQLiteHelper.KEY_MESSAGE_ISACTIVE, true);

            if (db.insert(DBSQLiteHelper.TABLE_MESSAGE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }

    }

    /**
     * guardar en SQLite la lista de actividades del hotel
     *
     * @param activityModelArrayList: lista de actividades
     */
    private void insertActivitySQLite(ArrayList<ActivityModel> activityModelArrayList) {
        for (ActivityModel activityModel : activityModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_ID, activityModel.getId());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_DATE_START, activityModel.getDateStart());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_DATE_END, activityModel.getDateEnd());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_TIME_START, activityModel.getTimeStart());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_TIME_END, activityModel.getTimeEnd());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_NAME, activityModel.getName());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_DESCRIPTION, activityModel.getDescription());
            contentValues.put(DBSQLiteHelper.KEY_ACTIVITY_IMAGE, activityModel.getImage());

            if (db.insert(DBSQLiteHelper.TABLE_ACTIVITY, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta activityModel");
        }
    }

    //**********************************************************************************************

    /**
     * cerrar session del usuario, cambiar el estado de la tabla logout
     */
    public void logoutAction() {
        db.execSQL("UPDATE " + DBSQLiteHelper.TABLE_LOGIN + " SET " + DBSQLiteHelper.KEY_LOGIN_STATE + "=0");
    }
}
