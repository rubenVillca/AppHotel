package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.model.ServicePriceConsumeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceCheck {
    private SQLiteDatabase db;

    public ServiceCheck(SQLiteDatabase db) {
        this.db = db;
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
                    + " from " + DBSQLite.TABLE_CHECK
                    + " where " + DBSQLite.KEY_CHECK_ID + "=" + idCheck
                    +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
        } else {
            if (idStateCheck > 0) {//para los check con estado idState
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            + " AND " + DBSQLite.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select *"
                            + " from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_STATE + "=" + idStateCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                }
            } else {//para el historial
                if (idTypeCheck > 0) {
                    cursor = db.rawQuery("select * from " + DBSQLite.TABLE_CHECK
                            + " where " + DBSQLite.KEY_CHECK_ID_KEY_TYPE + "=" + idTypeCheck
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
                } else {
                    cursor = db.rawQuery("select * " +
                            "from " + DBSQLite.TABLE_CHECK
                            +" ORDER BY "+DBSQLite.KEY_CHECK_ID+" DESC", null);
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
     * sincronizar base de datos SQLite desde webserver a checkModel
     *
     * @param obj:objeto JSON checkModel
     */
    public void syncUpCheck(JSONObject obj) {
        ArrayList<CheckModel> checkModels = getCheckModelJSON(obj);
        insertCheckSQLite(checkModels);
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
                + " from " + DBSQLite.TABLE_CONSUME_FOOD
                + " where " + DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLite.KEY_CONSUME_FOOD_ID+" DESC", null);

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
                + " from " + DBSQLite.TABLE_CONSUM
                + " where " + DBSQLite.KEY_CONSUM_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLite.KEY_CONSUM_ID+" DESC", null);

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
                + " from " + DBSQLite.TABLE_RESERVE
                + " where " + DBSQLite.KEY_RESERVE_ID_CONSUME + "=" + idConsum, null);

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
                + " from " + DBSQLite.TABLE_OCCUPATION
                + " where " + DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE + "=" + idConsum, null);

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
                + " from " + DBSQLite.TABLE_CARD
                + " where " + DBSQLite.KEY_CARD_ID_KEY_CHECK + "=" + idCheck, null);

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
                + " from " + DBSQLite.TABLE_MEMBER
                + " where " + DBSQLite.KEY_MEMBER_ID_KEY_CONSUM + "=" + idConsume, null);

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
                + " from " + DBSQLite.TABLE_ARTICLE
                + " where " + DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM + "=" + idConsum, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ArticleModel articleModel = obtainArticleModelCursor(cursor);

                listArticleModel.add(articleModel);

                cursor.moveToNext();
            }
        }
        return listArticleModel;
    }

    private ArticleModel obtainArticleModelCursor(Cursor cursor) {
        ArticleModel articleModel = new ArticleModel();

        articleModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_ID)));
        articleModel.setIdKeyConsum(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM)));
        articleModel.setName(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_NAME)));
        articleModel.setDescription(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_DESCRIPTION)));
        articleModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_ARTICLE_IS_ACTIVE)) > 0);

        return articleModel;
    }

    private MemberModel obtainMemberModelCursor(Cursor cursorPerson) {
        MemberModel memberModel = new MemberModel();

        memberModel.setIdPerson(cursorPerson.getInt(cursorPerson.getColumnIndex(DBSQLite.KEY_MEMBER_ID_KEY_PERSON)));
        memberModel.setIdKeyConsum(cursorPerson.getInt(cursorPerson.getColumnIndex(DBSQLite.KEY_MEMBER_ID_KEY_CONSUM)));

        Cursor cursorMember = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_PERSON
                + " where " + DBSQLite.KEY_PERSON_ID + "=" + memberModel.getIdPerson(), null);
        if (cursorMember.moveToFirst()) {
            while (!cursorMember.isAfterLast()) {
                memberModel.setNamePerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_NAME)));
                memberModel.setNameLastPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_NAME_LAST)));
                memberModel.setCityPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_CITY)));
                memberModel.setAddressPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_ADDRESS)));
                memberModel.setDateBornPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_DATE_BORN)));
                memberModel.setDateRegisterPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_DATE_REGISTER)));
                memberModel.setEmailPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_EMAIL)));
                memberModel.setPointPerson(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_POINT)));
                memberModel.setCountryPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_COUNTRY)));
                memberModel.setSexPerson((byte) cursorMember.getInt(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_SEX)));
                memberModel.setImgPerson(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_IMG_PERSON)));
                memberModel.setTypeDocument(cursorMember.getString(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_TYPE_DOCUMENT)));
                memberModel.setNumberDocument(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_NUMBER_DOCUMENT)));
                memberModel.setNumberPhone(cursorMember.getInt(cursorMember.getColumnIndex(DBSQLite.KEY_PERSON_NUMBER_PHONE)));

                cursorMember.moveToNext();
            }
        }
        cursorMember.close();
        return memberModel;
    }

    private ConsumeServiceModel obtainConsumeModelCursor(Cursor cursor) {
        ConsumeServiceModel consumeServiceModel = new ConsumeServiceModel();

        consumeServiceModel.setIdConsum(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_ID)));
        consumeServiceModel.setDateInConsum(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_DATE_START)));
        consumeServiceModel.setTimeInConsum(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_TIME_START)));
        consumeServiceModel.setDateOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_DATE_END)));
        consumeServiceModel.setTimeOutConsum(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_TIME_END)));
        consumeServiceModel.setNameService(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_NAME_SERVICE)));
        consumeServiceModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_NAME_MONEY)));
        consumeServiceModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_PRICE)));
        consumeServiceModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_PAY)));
        consumeServiceModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_STATE)) > 0);
        consumeServiceModel.setIdKeyService(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_ID_KEY_SERVICE)));
        consumeServiceModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_ID_KEY_CHECK)));
        consumeServiceModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_POINT_OBTAIN)));
        consumeServiceModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_POINT_REQUIRED)));
        consumeServiceModel.setnDay(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_N_DAY)));
        consumeServiceModel.setnHour(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_N_HOUR)));
        consumeServiceModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUM_N_UNIT)));

        return consumeServiceModel;
    }

    private CardModel obtainCardModelCursor(Cursor cursor) {
        CardModel cardModel = new CardModel();

        cardModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_ID)));
        cardModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_ID_KEY_CHECK)));
        cardModel.setNameType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CARD_NAME_TYPE)));
        cardModel.setNumber(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_NUMBER)));
        cardModel.setMonth(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_MONTH)));
        cardModel.setYear(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_YEAR)));
        cardModel.setCcv(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_CCV)));
        cardModel.setValid(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CARD_IS_VALID)) > 0);
        cardModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CARD_TYPE_MONEY)));
        cardModel.setDeposit(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CARD_DEPOSIT)));
        return cardModel;
    }

    private CheckModel obtainCheckModelCursor(Cursor cursor) {
        CheckModel checkModel = new CheckModel();

        checkModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID)));
        checkModel.setIdState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID_STATE)));
        checkModel.setValueState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_VALUE_STATE)));
        checkModel.setNameState(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_NAME_STATE)));
        checkModel.setIdType(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CHECK_ID_KEY_TYPE)));
        checkModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TYPE)));
        checkModel.setDateIn(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_DATE_IN)));
        checkModel.setTimeIn(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TIME_IN)));
        checkModel.setDateEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_DATE_END)));
        checkModel.setTimeEnd(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CHECK_TIME_END)));

        return checkModel;
    }

    private ConsumeFoodModel obtainConsumeFoodModelCursor(Cursor cursor) {
        ConsumeFoodModel consumeFoodModel = new ConsumeFoodModel();

        consumeFoodModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_ID)));
        consumeFoodModel.setIdKeyCheck(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK)));
        consumeFoodModel.setPrice(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_PRICE)));
        consumeFoodModel.setPay(cursor.getDouble(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_PAY)));
        consumeFoodModel.setTypeMoney(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_TYPE_MONEY)));
        consumeFoodModel.setNameFood(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_NAME_FOOD)));
        consumeFoodModel.setDescriptionFood(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_DESCRIPTION_FOOD)));
        consumeFoodModel.setPointObtain(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_POINT_OBTAIN)));
        consumeFoodModel.setPointRequired(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_POINT_REQUIRED)));
        consumeFoodModel.setUnitFood(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_UNIT_FOOD)));
        consumeFoodModel.setDateConsume(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_DATE)));
        consumeFoodModel.setTimeConsume(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_TIME)));
        consumeFoodModel.setState(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_STATE)));
        consumeFoodModel.setSite(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_CONSUME_FOOD_SITE)));

        return consumeFoodModel;
    }

    private OccupationModel obtainOccupationModelCursor(Cursor cursor) {
        OccupationModel occupationModel = new OccupationModel();

        occupationModel.setIdRoom(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_ID_ROOM)));
        occupationModel.setIdConsumeService(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE)));
        occupationModel.setNameRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_NAME_ROOM)));
        occupationModel.setImageRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_IMAGE_ROOM)));
        occupationModel.setStateRoom(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_STATE_ROOM)) > 0);
        occupationModel.setTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_TYPE_ROOM)));
        occupationModel.setDescriptionTypeRoom(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM)));
        occupationModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_N_ADULT)));
        occupationModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_OCCUPATION_N_BOY)));

        return occupationModel;
    }

    private ReserveModel obtainReserveModelCursor(Cursor cursor) {
        ReserveModel reserveModel = new ReserveModel();

        reserveModel.setIdConsume(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_ID_CONSUME)));
        reserveModel.setNameRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_NAME_ROOM_MODEL)));
        reserveModel.setDescriptionRoomModel(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_DESCRIPTION_ROOM_MODEL)));
        reserveModel.setnAdult(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_N_ADULT)));
        reserveModel.setnBoy(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_N_BOY)));
        reserveModel.setUnit(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_RESERVE_UNIT)));

        return reserveModel;
    }

    private ArrayList<CheckModel> getCheckModelJSON(JSONObject obj) {
        ArrayList<CheckModel> checkArray = new ArrayList<>();

        try {
            JSONArray checkJSONArray = obj.getJSONArray("checks");

            for (int j = 0; j < checkJSONArray.length(); j++) {
                CheckModel checkModel = new CheckModel();

                JSONObject checkObject = checkJSONArray.getJSONObject(j);

                checkModel.setId(checkObject.getInt("ID_CHECK"));
                checkModel.setIdState(checkObject.getInt("ID_STATE_CHECK"));
                checkModel.setValueState(checkObject.getInt("VALUE_STATE_CHECK"));
                checkModel.setNameState(checkObject.getString("NAME_STATE_CHECK"));
                checkModel.setIdType(checkObject.getInt("ID_TYPE_CHECK"));
                checkModel.setType(checkObject.getString("NAME_TYPE_CHECK"));
                checkModel.setDateIn(checkObject.getString("DATE_START_CHECK"));
                checkModel.setTimeIn(checkObject.getString("TIME_START_CHECK"));
                checkModel.setDateEnd(checkObject.getString("DATE_END_CHECK"));
                checkModel.setTimeEnd(checkObject.getString("TIME_END_CHECK"));

                checkModel.setCardTargetArrayList(getCardModelJSON(checkObject));
                checkModel.setConsumeServiceModelArrayList(getConsumeModelJSON(checkObject));
                checkModel.setConsumeFoodModelArrayList(getConsumeFoodModelJSON(checkObject));

                checkArray.add(checkModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return checkArray;
    }

    private ArrayList<ConsumeFoodModel> getConsumeFoodModelJSON(JSONObject obj) {
        ArrayList<ConsumeFoodModel> consumeFoodArray = new ArrayList<>();
        try {
            JSONArray consumeFoodJSONArray = obj.getJSONArray("consumeFood");

            for (int j = 0; j < consumeFoodJSONArray.length(); j++) {
                ConsumeFoodModel consumeFoodModel = new ConsumeFoodModel();

                JSONObject consumeFoodObject = consumeFoodJSONArray.getJSONObject(j);

                consumeFoodModel.setIdConsume(consumeFoodObject.getInt("ID_CONSUME_FOOD"));
                consumeFoodModel.setIdKeyCheck(consumeFoodObject.getInt("ID_CHECK"));
                consumeFoodModel.setPrice(consumeFoodObject.getDouble("PRICE_CONSUME_FOOD"));
                consumeFoodModel.setPay(consumeFoodObject.getDouble("PAY_CONSUME_FOOD"));
                consumeFoodModel.setTypeMoney(consumeFoodObject.getString("NAME_TYPE_MONEY"));
                consumeFoodModel.setNameFood(consumeFoodObject.getString("NAME_FOOD"));
                consumeFoodModel.setDescriptionFood(consumeFoodObject.getString("DESCRIPTION_FOOD"));
                consumeFoodModel.setPointObtain(consumeFoodObject.getInt("POINT_OBTAIN_COST_FOOD"));
                consumeFoodModel.setPointRequired(consumeFoodObject.getInt("POINT_REQUIRED_COST_FOOD"));
                consumeFoodModel.setUnitFood(consumeFoodObject.getInt("UNIT_CONSUME_FOOD"));
                consumeFoodModel.setDateConsume(consumeFoodObject.getString("DATE_CONSUME_FOOD"));
                consumeFoodModel.setTimeConsume(consumeFoodObject.getString("TIME_CONSUME_FOOD"));
                consumeFoodModel.setState(consumeFoodObject.getInt("STATE_CONSUME_FOOD"));
                consumeFoodModel.setSite(consumeFoodObject.getString("SITE_CONSUME_FOOD"));

                consumeFoodArray.add(consumeFoodModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumeFoodArray;
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

    private ArrayList<ConsumeServiceModel> getConsumeModelJSON(JSONObject obj) {
        ArrayList<ConsumeServiceModel> consumArray = new ArrayList<>();

        try {
            JSONArray consumJSONArray = obj.getJSONArray("consum");

            for (int j = 0; j < consumJSONArray.length(); j++) {
                ConsumeServiceModel consumeServiceModel = new ConsumeServiceModel();

                JSONObject consumObject = consumJSONArray.getJSONObject(j);

                consumeServiceModel.setIdConsum(consumObject.getInt("ID_CONSUME_SERVICE"));
                consumeServiceModel.setIdKeyCheck(consumObject.getInt("ID_CHECK"));
                consumeServiceModel.setIdKeyService(consumObject.getInt("ID_SERVICE"));
                consumeServiceModel.setPrice(consumObject.getDouble("PRICE_CONSUME_SERVICE"));
                consumeServiceModel.setPay(consumObject.getDouble("PAY_CONSUME_SERVICE"));
                consumeServiceModel.setDateInConsum(consumObject.getString("DATE_START_CONSUME_SERVICE"));
                consumeServiceModel.setTimeInConsum(consumObject.getString("TIME_START_CONSUME_SERVICE"));
                consumeServiceModel.setDateOutConsum(consumObject.getString("DATE_END_CONSUME_SERVICE"));
                consumeServiceModel.setTimeOutConsum(consumObject.getString("TIME_END_CONSUME_SERVICE"));
                consumeServiceModel.setState(consumObject.getInt("ACTIVE_CONSUME_SERVICE") > 0);
                consumeServiceModel.setNameService(consumObject.getString("NAME_SERVICE"));
                consumeServiceModel.setTypeMoney(consumObject.getString("NAME_TYPE_MONEY"));
                consumeServiceModel.setPointObtain(consumObject.getInt("POINT_OBTAIN_COST_SERVICE"));
                consumeServiceModel.setPointRequired(consumObject.getInt("POINT_REQUIRED_COST_SERVICE"));
                consumeServiceModel.setnDay(consumObject.getInt("UNIT_DAY_COST_SERVICE"));
                consumeServiceModel.setnHour(consumObject.getInt("UNIT_HOUR_COST_SERVICE"));
                consumeServiceModel.setUnit(consumObject.getInt("UNIT_CONSUME_SERVICE"));

                consumeServiceModel.setMemberModelArrayList(getMemberModelJSON(consumObject));
                consumeServiceModel.setArticleModel(getArticleModelJSON(consumObject));
                consumeServiceModel.setOccupationModelArrayList(getOccupationModelJSON(consumObject));
                consumeServiceModel.setReserveModelArrayList(getReserveModelJSON(consumObject));

                consumArray.add(consumeServiceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumArray;
    }

    /**
     * guardar registro de ingresos al hotel en la base de datos SQLIte
     *
     * @param checkModels:lista de registros formato JAVa
     */
    private void insertCheckSQLite(ArrayList<CheckModel> checkModels) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CHECK);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CONSUM);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CARD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ARTICLE);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_MEMBER);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_CONSUME_FOOD);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_OCCUPATION);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_RESERVE);
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_ARTICLE);

        for (CheckModel checkModel : checkModels) {
            ContentValues checkContent = new ContentValues();

            checkContent.put(DBSQLite.KEY_CHECK_ID, checkModel.getId());
            checkContent.put(DBSQLite.KEY_CHECK_ID_STATE, checkModel.getIdState());
            checkContent.put(DBSQLite.KEY_CHECK_VALUE_STATE, checkModel.getValueState());
            checkContent.put(DBSQLite.KEY_CHECK_NAME_STATE, checkModel.getNameState());
            checkContent.put(DBSQLite.KEY_CHECK_ID_KEY_TYPE,checkModel.getIdType());
            checkContent.put(DBSQLite.KEY_CHECK_TYPE, checkModel.getType());
            checkContent.put(DBSQLite.KEY_CHECK_DATE_IN, checkModel.getDateIn());
            checkContent.put(DBSQLite.KEY_CHECK_TIME_IN, checkModel.getTimeIn());
            checkContent.put(DBSQLite.KEY_CHECK_DATE_END, checkModel.getDateEnd());
            checkContent.put(DBSQLite.KEY_CHECK_TIME_END, checkModel.getTimeEnd());

            if (db.insert(DBSQLite.TABLE_CHECK, null, checkContent) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            insertTargetSQLite(checkModel.getCardTargetArrayList());
            insertConsumeSQLite(checkModel.getConsumeServiceModelArrayList());
            insertConsumeFoodSQLite(checkModel.getConsumeFoodModelArrayList());
        }
    }

    private ArrayList<ReserveModel> getReserveModelJSON(JSONObject obj) {
        ArrayList<ReserveModel> reserveModelArrayList = new ArrayList<>();

        try {
            JSONArray fequentlyJSONArray = obj.getJSONArray("reserve");

            for (int j = 0; j < fequentlyJSONArray.length(); j++) {
                ReserveModel reserveModel = new ReserveModel();

                JSONObject reserveObject = fequentlyJSONArray.getJSONObject(j);

                reserveModel.setIdConsume(reserveObject.getInt("ID_CONSUME_SERVICE"));
                reserveModel.setNameRoomModel(reserveObject.getString("NAME_ROOM_MODEL"));
                reserveModel.setDescriptionRoomModel(reserveObject.getString("DESCRIPTION_ROOM_MODEL"));
                reserveModel.setnAdult(reserveObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                reserveModel.setnBoy(reserveObject.getInt("UNIT_BOY_ROOM_MODEL"));
                reserveModel.setUnit(reserveObject.getInt("UNIT_RESERVED"));

                reserveModelArrayList.add(reserveModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reserveModelArrayList;
    }

    private ArrayList<OccupationModel> getOccupationModelJSON(JSONObject obj) {
        ArrayList<OccupationModel> occupationModelArrayList = new ArrayList<>();

        try {
            JSONArray occupationJSONArray = obj.getJSONArray("occupation");

            for (int j = 0; j < occupationJSONArray.length(); j++) {
                OccupationModel occupationModel = new OccupationModel();

                JSONObject occupationObject = occupationJSONArray.getJSONObject(j);

                occupationModel.setIdRoom(occupationObject.getInt("ID_ROOM"));
                occupationModel.setIdConsumeService(occupationObject.getInt("ID_CONSUME_SERVICE"));
                occupationModel.setNameRoom(occupationObject.getString("NAME_ROOM"));
                occupationModel.setImageRoom(occupationObject.getString("IMAGE_ROOM").isEmpty() ? occupationObject.getString("IMAGE_ROOM_MODEL") : occupationObject.getString("IMAGE_ROOM"));
                occupationModel.setStateRoom(occupationObject.getInt("VALUE_TYPE_ROOM_MODEL") > 0);
                occupationModel.setTypeRoom(occupationObject.getString("NAME_ROOM_MODEL"));
                occupationModel.setDescriptionTypeRoom(occupationObject.getString("DESCRIPTION_ROOM_MODEL"));
                occupationModel.setnAdult(occupationObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                occupationModel.setnBoy(occupationObject.getInt("UNIT_BOY_ROOM_MODEL"));

                occupationModelArrayList.add(occupationModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return occupationModelArrayList;
    }

    private ArrayList<ArticleModel> getArticleModelJSON(JSONObject obj) {
        ArrayList<ArticleModel> articleArray = new ArrayList<>();

        try {
            JSONArray articleJSONArray = obj.getJSONArray("articles");

            for (int j = 0; j < articleJSONArray.length(); j++) {
                ArticleModel articleModel = new ArticleModel();

                JSONObject articleObject = articleJSONArray.getJSONObject(j);

                articleModel.setId(articleObject.getInt("ID_ARTICLE"));
                articleModel.setIdKeyConsum(articleObject.getInt("ID_CONSUME_SERVICE"));
                articleModel.setName(articleObject.getString("NAME_ARTICLE"));
                articleModel.setDescription(articleObject.getString("DESCRIPTION_ARTICLE"));
                articleModel.setActive(articleObject.getInt("VALUE_STATE_ARTICLE") > 0);

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
            JSONArray memberJSONArray = obj.getJSONArray("members");

            for (int j = 0; j < memberJSONArray.length(); j++) {
                MemberModel memberModel = new MemberModel();

                JSONObject memberObject = memberJSONArray.getJSONObject(j);

                memberModel.setIdKeyConsum(memberObject.getInt("ID_CONSUME_SERVICE"));
                memberModel.setIdPerson(memberObject.getInt("ID_PERSON"));
                memberModel.setEmailPerson(Conexion.decode(memberObject.getString("EMAIL_PERSON")));
                memberModel.setNamePerson(memberObject.getString("NAME_PERSON"));
                memberModel.setNameLastPerson(memberObject.getString("LAST_NAME_PERSON"));
                memberModel.setSexPerson((byte) memberObject.getInt("SEX_PERSON"));
                memberModel.setPointPerson(memberObject.getInt("POINT_PERSON"));
                memberModel.setCityPerson(memberObject.getString("CITY_PERSON"));
                memberModel.setCountryPerson(memberObject.getString("COUNTRY_PERSON"));
                memberModel.setDateBornPerson(memberObject.getString("DATE_BORN_PERSON"));
                memberModel.setDateRegisterPerson(memberObject.getString("DATE_REGISTER_PERSON"));
                memberModel.setAddressPerson(memberObject.getString("ADDRESS_PERSON"));
                memberModel.setImgPerson(memberObject.getString("IMAGE_PROFILE"));
                memberModel.setNumberPhone(memberObject.getInt("NUMBER_PHONE"));
                memberModel.setNumberDocument(memberObject.getInt("NUMBER_DOCUMENT"));
                memberModel.setTypeDocument(memberObject.getString("NAME_TYPE_DOCUMENT"));

                memberArray.add(memberModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return memberArray;
    }

    /**
     * guardar targetas de credito  en la base de datos SQLIte
     *
     * @param cardTargetArrayList:lista de targetas de credito
     */
    private void insertTargetSQLite(ArrayList<CardModel> cardTargetArrayList) {
        for (CardModel cardModel : cardTargetArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_CARD_ID, cardModel.getId());
            contentValues.put(DBSQLite.KEY_CARD_ID_KEY_CHECK, cardModel.getIdKeyCheck());
            contentValues.put(DBSQLite.KEY_CARD_NAME_TYPE, cardModel.getNameType());
            contentValues.put(DBSQLite.KEY_CARD_NUMBER, cardModel.getNumber());
            contentValues.put(DBSQLite.KEY_CARD_MONTH, cardModel.getMonth());
            contentValues.put(DBSQLite.KEY_CARD_YEAR, cardModel.getYear());
            contentValues.put(DBSQLite.KEY_CARD_CCV, cardModel.getCcv());
            contentValues.put(DBSQLite.KEY_CARD_IS_VALID, cardModel.isValid());
            contentValues.put(DBSQLite.KEY_CARD_TYPE_MONEY, cardModel.getTypeMoney());
            contentValues.put(DBSQLite.KEY_CARD_DEPOSIT, cardModel.getDeposit());

            if (db.insert(DBSQLite.TABLE_CARD, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * guardar registro consumos del huesped en la base de datos SQLIte
     *
     * @param consumeServiceModelArrayList:lista de consumos hechos por el cliente en la cuenta del check  formato JAVa
     */
    public void insertConsumeSQLite(ArrayList<ConsumeServiceModel> consumeServiceModelArrayList) {
        for (ConsumeServiceModel consumeServiceModel : consumeServiceModelArrayList) {
            ContentValues contentValues = new ContentValues();
            if (consumeServiceModel.getIdConsum()>0)
                contentValues.put(DBSQLite.KEY_CONSUM_ID, consumeServiceModel.getIdConsum());
            contentValues.put(DBSQLite.KEY_CONSUM_DATE_START, consumeServiceModel.getDateInConsum());
            contentValues.put(DBSQLite.KEY_CONSUM_TIME_START, consumeServiceModel.getTimeInConsum());
            contentValues.put(DBSQLite.KEY_CONSUM_DATE_END, consumeServiceModel.getDateOutConsum());
            contentValues.put(DBSQLite.KEY_CONSUM_TIME_END, consumeServiceModel.getTimeOutConsum());
            contentValues.put(DBSQLite.KEY_CONSUM_PRICE, consumeServiceModel.getPrice());
            contentValues.put(DBSQLite.KEY_CONSUM_PAY, consumeServiceModel.getPay());
            contentValues.put(DBSQLite.KEY_CONSUM_STATE, consumeServiceModel.isState());
            contentValues.put(DBSQLite.KEY_CONSUM_ID_KEY_SERVICE, consumeServiceModel.getIdKeyService());
            contentValues.put(DBSQLite.KEY_CONSUM_NAME_SERVICE, consumeServiceModel.getNameService());
            contentValues.put(DBSQLite.KEY_CONSUM_NAME_MONEY, consumeServiceModel.getTypeMoney());
            contentValues.put(DBSQLite.KEY_CONSUM_ID_KEY_CHECK, consumeServiceModel.getIdKeyCheck());
            contentValues.put(DBSQLite.KEY_CONSUM_POINT_OBTAIN, consumeServiceModel.getPointObtain());
            contentValues.put(DBSQLite.KEY_CONSUM_POINT_REQUIRED, consumeServiceModel.getPointRequired());
            contentValues.put(DBSQLite.KEY_CONSUM_N_DAY, consumeServiceModel.getnDay());
            contentValues.put(DBSQLite.KEY_CONSUM_N_HOUR, consumeServiceModel.getnHour());
            contentValues.put(DBSQLite.KEY_CONSUM_N_UNIT, consumeServiceModel.getUnit());

            if (db.insert(DBSQLite.TABLE_CONSUM, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            insertArticleSQLite(consumeServiceModel.getArticleModel());
            insertMemberSQLite(consumeServiceModel.getMemberModelArrayList());
            insertOccupationSQLite(consumeServiceModel.getOccupationModelArrayList());
            insertReserveSQLite(consumeServiceModel.getReserveModelArrayList());
        }
    }

    /**
     * Guardar en la base de datos SQLite la lista de reservas q hizo el usuario
     *
     * @param reserveModelArrayList: lista de reservas realizadas por el huesped
     */
    private void insertReserveSQLite(ArrayList<ReserveModel> reserveModelArrayList) {
        for (ReserveModel reserveModel : reserveModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_RESERVE_ID_CONSUME, reserveModel.getIdConsume());
            contentValues.put(DBSQLite.KEY_RESERVE_NAME_ROOM_MODEL, reserveModel.getNameRoomModel());
            contentValues.put(DBSQLite.KEY_RESERVE_DESCRIPTION_ROOM_MODEL, reserveModel.getDescriptionRoomModel());
            contentValues.put(DBSQLite.KEY_RESERVE_N_ADULT, reserveModel.getnAdult());
            contentValues.put(DBSQLite.KEY_RESERVE_N_BOY, reserveModel.getnBoy());
            contentValues.put(DBSQLite.KEY_RESERVE_UNIT, reserveModel.getUnit());

            if (db.insert(DBSQLite.TABLE_RESERVE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * Guardar lista de habitaciones ocupadas por el cliente
     *
     * @param occupationModelArrayList: lista habitaciones ocupadas por el huesped
     */
    private void insertOccupationSQLite(ArrayList<OccupationModel> occupationModelArrayList) {
        for (OccupationModel occupationModel : occupationModelArrayList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_OCCUPATION_ID_CONSUME_SERVICE, occupationModel.getIdConsumeService());
            contentValues.put(DBSQLite.KEY_OCCUPATION_ID_ROOM, occupationModel.getIdRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_NAME_ROOM, occupationModel.getNameRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_IMAGE_ROOM, occupationModel.getImageRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_STATE_ROOM, occupationModel.isStateRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_TYPE_ROOM, occupationModel.getTypeRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_DESCRIPTION_TYPE_ROOM, occupationModel.getDescriptionTypeRoom());
            contentValues.put(DBSQLite.KEY_OCCUPATION_N_ADULT, occupationModel.getnAdult());
            contentValues.put(DBSQLite.KEY_OCCUPATION_N_BOY, occupationModel.getnBoy());

            if (db.insert(DBSQLite.TABLE_OCCUPATION, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
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

            contentValues.put(DBSQLite.KEY_ARTICLE_ID, articleModel.getId());
            contentValues.put(DBSQLite.KEY_ARTICLE_ID_KEY_CONSUM, articleModel.getIdKeyConsum());
            contentValues.put(DBSQLite.KEY_ARTICLE_NAME, articleModel.getName());
            contentValues.put(DBSQLite.KEY_ARTICLE_DESCRIPTION, articleModel.getDescription());
            contentValues.put(DBSQLite.KEY_ARTICLE_IS_ACTIVE, articleModel.isActive()?1:0);

            if (db.insert(DBSQLite.TABLE_ARTICLE, null, contentValues) == -1)
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

            contentValues.put(DBSQLite.KEY_MEMBER_ID_KEY_CONSUM, memberModel.getIdKeyConsum());
            contentValues.put(DBSQLite.KEY_MEMBER_ID_KEY_PERSON, memberModel.getIdPerson());

            if (db.insert(DBSQLite.TABLE_MEMBER, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");

            ServicePerson servicePerson=new ServicePerson(db);
            servicePerson.insertPersonSQLite(memberModel);
        }
    }

    /**
     * guardar lista de alimentos consumidos en base de datos SQLite
     *
     * @param consumeFoodModelArrayList:lista de alimentos consumidos
     */
    public void insertConsumeFoodSQLite(ArrayList<ConsumeFoodModel> consumeFoodModelArrayList) {
        for (ConsumeFoodModel consumeFoodModel : consumeFoodModelArrayList) {
            ContentValues contentValues = new ContentValues();
            if (consumeFoodModel.getIdConsume()>0)
                contentValues.put(DBSQLite.KEY_CONSUME_FOOD_ID, consumeFoodModel.getIdConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_ID_KEY_CHECK, consumeFoodModel.getIdKeyCheck());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_PRICE, consumeFoodModel.getPrice());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_PAY, consumeFoodModel.getPay());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_TYPE_MONEY, consumeFoodModel.getTypeMoney());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_NAME_FOOD, consumeFoodModel.getNameFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_DESCRIPTION_FOOD, consumeFoodModel.getDescriptionFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_POINT_OBTAIN, consumeFoodModel.getPointObtain());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_POINT_REQUIRED, consumeFoodModel.getPointRequired());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_UNIT_FOOD, consumeFoodModel.getUnitFood());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_DATE, consumeFoodModel.getDateConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_TIME, consumeFoodModel.getTimeConsume());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_STATE, consumeFoodModel.getState());
            contentValues.put(DBSQLite.KEY_CONSUME_FOOD_SITE, consumeFoodModel.getSite());

            if (db.insert(DBSQLite.TABLE_CONSUME_FOOD, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }
    }

    /**
     * obtener la lista de habitaciones libres del hotel
     *
     * @param obj: objeto habitaciones disponibles en formato JSON
     * @return ArrayList<ReserveSearchModel>: lista de tipos de habitaciones disponibles del hotel
     */
    public ArrayList<ReserveSearchModel> getRoomAvailableModel(JSONObject obj) {
        ArrayList<ReserveSearchModel> reserveSearchModels = new ArrayList<>();
        try {
            JSONArray roomAvailableJSONArray = obj.getJSONArray("roomAvailable");

            for (int i = 0; i < roomAvailableJSONArray.length(); i++) {
                JSONObject roomAvailableObject = roomAvailableJSONArray.getJSONObject(i);

                ReserveSearchModel reserveSearchModel = new ReserveSearchModel();

                reserveSearchModel.setIdTypeRoom(roomAvailableObject.getInt("ID_ROOM_MODEL"));
                reserveSearchModel.setUnitAdult(roomAvailableObject.getInt("UNIT_ADULT_ROOM_MODEL"));
                reserveSearchModel.setUnitBoy(roomAvailableObject.getInt("UNIT_BOY_ROOM_MODEL"));
                reserveSearchModel.setUnitPet(roomAvailableObject.getInt("UNIT_PET_ROOM_MODEL"));
                reserveSearchModel.setUnitRoom(roomAvailableObject.getInt("TOTAL_ROOM") - roomAvailableObject.getInt("n_reserved"));
                reserveSearchModel.setIdService(roomAvailableObject.getInt("ID_SERVICE"));
                reserveSearchModel.setNameService(roomAvailableObject.getString("NAME_SERVICE"));
                reserveSearchModel.setImageTypeRoom(roomAvailableObject.getString("IMAGE_ROOM_MODEL"));
                reserveSearchModel.setNameTypeRoom(roomAvailableObject.getString("NAME_ROOM_MODEL"));
                reserveSearchModel.setDescriptionTypeRoom(roomAvailableObject.getString("DESCRIPTION_ROOM_MODEL"));
                reserveSearchModel.setServicePriceConsumeModels(getPriceRoomModel(roomAvailableObject));

                reserveSearchModels.add(reserveSearchModel);
            }
        } catch (JSONException e) {
            System.out.println("Datos no legibles");
            e.printStackTrace();
        }
        return reserveSearchModels;
    }

    private ArrayList<ServicePriceConsumeModel> getPriceRoomModel(JSONObject obj) {
        ArrayList<ServicePriceConsumeModel> servicePriceConsumeModels = new ArrayList<>();
        try {
            JSONArray priceRoomJSONArray = obj.getJSONArray("list_cost");

            for (int i = 0; i < priceRoomJSONArray.length(); i++) {
                JSONObject roomAvailableObject = priceRoomJSONArray.getJSONObject(i);

                ServicePriceConsumeModel priceRoomModel = new ServicePriceConsumeModel();

                priceRoomModel.setUnitHour(roomAvailableObject.getInt("UNIT_HOUR_COST_SERVICE"));
                priceRoomModel.setUnitDay(roomAvailableObject.getInt("UNIT_DAY_COST_SERVICE"));
                priceRoomModel.setPointObtain(roomAvailableObject.getInt("POINT_OBTAIN_COST_SERVICE"));
                priceRoomModel.setPointRequired(roomAvailableObject.getInt("POINT_REQUIRED_COST_SERVICE"));
                priceRoomModel.setIdKeyCost(roomAvailableObject.getInt("ID_COST_SERVICE"));
                priceRoomModel.setNameTypeMoney(roomAvailableObject.getString("NAME_TYPE_MONEY"));
                priceRoomModel.setPriceService(roomAvailableObject.getInt("PRICE_COST_SERVICE"));
                priceRoomModel.setUnitService(roomAvailableObject.getInt("UNIT_COST_SERVICE"));

                servicePriceConsumeModels.add(priceRoomModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servicePriceConsumeModels;
    }
}
