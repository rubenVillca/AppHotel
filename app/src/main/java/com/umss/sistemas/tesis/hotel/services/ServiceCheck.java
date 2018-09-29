package com.umss.sistemas.tesis.hotel.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ArticleModel;
import com.umss.sistemas.tesis.hotel.model.CardModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.model.OccupationModel;
import com.umss.sistemas.tesis.hotel.model.ReserveModel;

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
}
