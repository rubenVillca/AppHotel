package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceConsume extends ServiceParent {
    private ServiceReserve serviceReserve;
    private ServiceArticle serviceArticle;
    private ServiceMember serviceMember;
    private ServiceOccupation serviceOccupation;

    public ServiceConsume(SQLiteDatabase db) {
        super(db);
        serviceReserve=new ServiceReserve(db);
        serviceArticle=new ServiceArticle(db);
        serviceMember=new ServiceMember(db);
        serviceOccupation=new ServiceOccupation(db);
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

    /**
     * obtener de la base de datos SQLite la lista de consumos de in check
     *
     * @param idCheck: id de registro
     * @return ArrayList<ConsumeServiceModel>: lista de consumos de un check
     */
    public ArrayList<ConsumeServiceModel> getConsumeModel(int idCheck) {
        ArrayList<ConsumeServiceModel> listConsumeServiceModel = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select *"
                + " from " + DBSQLite.TABLE_CONSUM
                + " where " + DBSQLite.KEY_CONSUM_ID_KEY_CHECK + "=" + idCheck
                +" ORDER BY "+DBSQLite.KEY_CONSUM_ID+" DESC", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ConsumeServiceModel consumeServiceModel = obtainConsumeModelCursor(cursor);
                consumeServiceModel.setArticleModel(serviceArticle.getArticleModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setMemberModelArrayList(serviceMember.getMemberModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setOccupationModelArrayList(serviceOccupation.getOccupationModel(consumeServiceModel.getIdConsum()));
                consumeServiceModel.setReserveModelArrayList(serviceReserve.getReserveModel(consumeServiceModel.getIdConsum()));

                listConsumeServiceModel.add(consumeServiceModel);

                cursor.moveToNext();
            }
        }
        return listConsumeServiceModel;
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

            serviceArticle.insertArticleSQLite(consumeServiceModel.getArticleModel());
            serviceMember.insertMemberSQLite(consumeServiceModel.getMemberModelArrayList());
            serviceOccupation.insertOccupationSQLite(consumeServiceModel.getOccupationModelArrayList());
            serviceReserve.insertReserveSQLite(consumeServiceModel.getReserveModelArrayList());
        }
    }

    public ArrayList<ConsumeServiceModel> getConsumeModelJSON(JSONObject obj) {
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

                consumeServiceModel.setMemberModelArrayList(serviceMember.getMemberModelJSON(consumObject));
                consumeServiceModel.setArticleModel(serviceArticle.getArticleModelJSON(consumObject));
                consumeServiceModel.setOccupationModelArrayList(serviceOccupation.getOccupationModelJSON(consumObject));
                consumeServiceModel.setReserveModelArrayList(serviceReserve.getReserveModelJSON(consumObject));

                consumArray.add(consumeServiceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return consumArray;
    }
}
