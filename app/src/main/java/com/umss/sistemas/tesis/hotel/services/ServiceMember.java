package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.conexion.Conexion;
import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.MemberModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceMember extends ServiceParent {

    public ServiceMember(SQLiteDatabase db) {
        super(db);
    }

    /**
     * guardar lista de miembros del check que ingresaron al hotel en la base de datos SQLIte
     *
     * @param memberModelArrayList:lista de clientes registrados con un check check  formato JAVa
     */
    public void insertMemberSQLite(ArrayList<MemberModel> memberModelArrayList) {
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
     * obtener de la base de datos la lista de miembros del consumo
     *
     * @param idConsume: id de consumo
     * @return ArrayList<MemberModel>: lista de miembros
     */
    public ArrayList<MemberModel> getMemberModel(int idConsume) {
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

    public ArrayList<MemberModel> getMemberModelJSON(JSONObject obj) {
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
}
