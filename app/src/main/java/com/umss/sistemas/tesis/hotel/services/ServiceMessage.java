package com.umss.sistemas.tesis.hotel.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.helper.DBSQLite;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.parent.ServiceParent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceMessage extends ServiceParent {

    public ServiceMessage(SQLiteDatabase db) {
        super(db);
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
     * obtener de la base deatos SQLite la lista de mensajes de un usuario
     *
     * @param idMessage: id de mensaje
     * @return ArrayList<MessageModel>: idMensaje==0?lista de todos los mensjes: lista del mensaje con id requerido
     */
    public ArrayList<MessageModel> getMessageModel(int idMessage) {
        ArrayList<MessageModel> listMessageModel = new ArrayList<>();
        Cursor cursor;
        if (idMessage == 0) {
            cursor = db.rawQuery("select *" + " from " + DBSQLite.TABLE_MESSAGE, null);
        } else {
            cursor = db.rawQuery("select *"
                    + " from " + DBSQLite.TABLE_MESSAGE
                    + " where " + DBSQLite.KEY_MESSAGE_ID + "=" + idMessage, null);
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

    private MessageModel obtainMessageModelCursor(Cursor cursor) {
        MessageModel messageModel = new MessageModel();

        messageModel.setId(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ID)));
        messageModel.setTittle(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TITTLE)));
        messageModel.setContent(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_CONTENT)));
        messageModel.setDateRecived(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_DATE_RECIVED)));
        messageModel.setTimeRecived(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TIME_RECIVED)));
        messageModel.setRead(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ISREAD)) > 0);
        messageModel.setEmailSender(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_EMAIL_SENDER)));
        messageModel.setNameSender(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_NAME_SENDER)));
        messageModel.setActive(cursor.getInt(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_ISACTIVE)) > 0);
        messageModel.setType(cursor.getString(cursor.getColumnIndex(DBSQLite.KEY_MESSAGE_TYPE)));

        return messageModel;
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
     * guradar en SQLite la lista de mensajes recividos
     *
     * @param messageModels: lista de mensajes recibidos
     */
    private void insertMessageSQLite(ArrayList<MessageModel> messageModels) {
        db.execSQL("DELETE FROM " + DBSQLite.TABLE_MESSAGE);

        for (MessageModel messageModel : messageModels) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(DBSQLite.KEY_MESSAGE_ID, messageModel.getId());
            contentValues.put(DBSQLite.KEY_MESSAGE_TITTLE, messageModel.getTittle());
            contentValues.put(DBSQLite.KEY_MESSAGE_CONTENT, messageModel.getContent());
            contentValues.put(DBSQLite.KEY_MESSAGE_DATE_RECIVED, messageModel.getDateRecived());
            contentValues.put(DBSQLite.KEY_MESSAGE_TIME_RECIVED, messageModel.getTimeRecived());
            contentValues.put(DBSQLite.KEY_MESSAGE_ISREAD, messageModel.isRead());
            contentValues.put(DBSQLite.KEY_MESSAGE_EMAIL_SENDER, messageModel.getEmailSender());
            contentValues.put(DBSQLite.KEY_MESSAGE_NAME_SENDER, messageModel.getNameSender());
            contentValues.put(DBSQLite.KEY_MESSAGE_TYPE, messageModel.getType());
            contentValues.put(DBSQLite.KEY_MESSAGE_ISACTIVE, true);

            if (db.insert(DBSQLite.TABLE_MESSAGE, null, contentValues) == -1)
                System.out.println("Ocurrio un error al inserar la consulta FoodPriceModel");
        }

    }
}
