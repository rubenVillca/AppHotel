package com.umss.sistemas.tesis.hotel.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HelperSQLite {
    private SQLiteDatabase db;

    public HelperSQLite(Context context) {
        DataBaseSQLiteHelper sync = new DataBaseSQLiteHelper(context, DataBaseSQLiteHelper.DATABASE_NAME, null, DataBaseSQLiteHelper.DATABASE_VERSION);
        db = sync.getWritableDatabase();
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
        setLoginSQLite(loginModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a personModel
     *
     * @param obj:objeto JSON person
     */
    public void syncUpPerson(JSONObject obj) {
        PersonModel personModel = getPersonModelJSON(obj);
        setPersonSQLite(personModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a aboutModel
     *
     * @param obj:objeto JSON about
     */
    public void syncUpAbout(JSONObject obj) {
        AboutModel aboutModel = getAboutModelJSON(obj);
        setAboutSQLite(aboutModel);
    }

    /**
     * sincronizar base de datos SQLite desde webserver a serviceModel
     *
     * @param obj:objeto JSON person
     */
    public void syncUpService(JSONObject obj) {
        ArrayList<ServiceModel> serviceModel = getServiceModelJSON(obj);
        setServiceSQLite(serviceModel);
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

    private ArrayList<ServiceModel> getServiceModelJSON(JSONObject obj) {
        ArrayList<ServiceModel> servicesModel=new ArrayList<>();

        try {
            JSONArray servicesArray=obj.getJSONArray("services");
            int limit=servicesArray.length();

            for (int i=0;i<limit;i++){
                JSONObject result=servicesArray.getJSONObject(i);

                ServiceModel serviceModel=new ServiceModel();

                serviceModel.setServiceId(result.getInt("id"));
                serviceModel.setServiceReserved(result.getInt("reservable"));
                serviceModel.setServiceName(result.getString("name"));
                serviceModel.setServiceDescription(result.getString("description"));
                serviceModel.setServiceType(result.getString("type"));
                serviceModel.setServiceImage(result.getString("image"));

                servicesModel.add(serviceModel);
            }

        } catch (JSONException e) {
            System.out.println("Error: Objeto no convertible, "+e.toString());

            e.printStackTrace();
        }

        return servicesModel;
    }
    /**
     * Lee de la base de datos de sqlite los datos del login
     *
     * @return LoginModel: estado de la cuenta
     */
    public LoginModel getLoginModel() {
        LoginModel loginModel = new LoginModel();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_LOGIN, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                loginModel = getLoginModelSQLite(cursor);
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
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_PERSON, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                personModel = getPersonModelSQLite(cursor);
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
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_ABOUT, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                aboutModel = getAboutModelSQLite(cursor);
                cursor.moveToNext();
            }
        }
        return aboutModel;
    }

    /**
     * Lee de la base de datos de sqlite los datos del hotel
     *
     * @return List<PersonModel>: datos del hotel
     */
    public ArrayList<ServiceModel> getServiceModel() {
        ArrayList<ServiceModel> listService = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + DataBaseSQLiteHelper.TABLE_SERVICE, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ServiceModel serviceModel=getServiceModelSQLite(cursor);
                listService.add(serviceModel);
                cursor.moveToNext();
            }
        }
        return listService;
    }

    @NonNull
    private PersonModel getPersonModelSQLite(Cursor cursor) {
        PersonModel personModel = new PersonModel();
        personModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ID)));
        personModel.setNamePerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME)));
        personModel.setNameLastPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST)));
        personModel.setCityPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_CITY)));
        personModel.setAddressPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS)));
        personModel.setDateBornPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN)));
        personModel.setDateRegisterPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER)));
        personModel.setEmailPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_EMAIL)));
        personModel.setPointPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_POINT)));
        personModel.setCountryPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY)));
        personModel.setSexPerson((byte) cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_SEX)));
        personModel.setImgPerson(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON)));
        return personModel;
    }

    @NonNull
    private AboutModel getAboutModelSQLite(Cursor cursor) {
        AboutModel aboutModel = new AboutModel();

        aboutModel.setId(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ID)));
        aboutModel.setLogoHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL)));
        aboutModel.setAddressGPSX(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX)));
        aboutModel.setAddressGPSY(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY)));
        aboutModel.setAddressImage(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE)));
        aboutModel.setNameHotel(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL)));
        aboutModel.setPhoneHotel(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL)));
        aboutModel.setEmail(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL)));
        aboutModel.setAddress(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS)));
        aboutModel.setMision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_MISION)));
        aboutModel.setVision(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_VISION)));
        aboutModel.setFundation(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION)));
        aboutModel.setScope(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE)));
        aboutModel.setHistory(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY)));
        aboutModel.setWatchWord(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD)));
        aboutModel.setObjetive(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE)));
        aboutModel.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION)));
        aboutModel.setType(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_TYPEHOTEL)));
        aboutModel.setSiteWeb(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL)));

        return aboutModel;
    }

    @NonNull
    private LoginModel getLoginModelSQLite(Cursor cursor) {
        LoginModel loginModel = new LoginModel();

        loginModel.setIdPerson(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_LOGIN_ID_PERSON)));
        loginModel.setState(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_LOGIN_STATE)));
        loginModel.setPassword(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_LOGIN_PASSWORD)));

        return loginModel;
    }
    @NonNull
    private ServiceModel getServiceModelSQLite(Cursor cursor) {
        ServiceModel serviceModel = new ServiceModel();

        serviceModel.setServiceId(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_ID)));
        serviceModel.setServiceReserved(cursor.getInt(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_RESERVED)));
        serviceModel.setServiceName(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_NAME)));
        serviceModel.setServiceDescription(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_DESCRIPTION)));
        serviceModel.setServiceImage(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_IMAGE)));
        serviceModel.setServiceType(cursor.getString(cursor.getColumnIndex(DataBaseSQLiteHelper.KEY_SERVICE_TYPE)));

        return serviceModel;
    }

    /**
     * ingresar loginModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param loginModel: objeto a ingresar a la base de datos sqlite
     */
    private void setLoginSQLite(LoginModel loginModel) {
        ContentValues newRegister = new ContentValues();
        newRegister.put(DataBaseSQLiteHelper.KEY_LOGIN_ID_PERSON, loginModel.getIdPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_LOGIN_PASSWORD, loginModel.getPassword());
        newRegister.put(DataBaseSQLiteHelper.KEY_LOGIN_STATE, loginModel.getState());

        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_LOGIN);
        db.insert(DataBaseSQLiteHelper.TABLE_LOGIN, null, newRegister);
    }

    /**
     * ingresar personModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param personModel: objeto a ingresar a la base dedd atos sqlite
     */
    private void setPersonSQLite(PersonModel personModel) {
        ContentValues newRegister = new ContentValues();
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ID, personModel.getIdPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_EMAIL, personModel.getEmailPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME, personModel.getNamePerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NAME_LAST, personModel.getNameLastPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_CITY, personModel.getCityPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_COUNTRY, personModel.getCountryPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_POINT, personModel.getPointPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_SEX, personModel.getSexPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_ADDRESS, personModel.getAddressPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_IMG_PERSON, personModel.getImgPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_BORN, personModel.getDateBornPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_DATE_REGISTER, personModel.getDateRegisterPerson());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_TYPE_DOCUMENT, personModel.getTypeDocument());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_DOCUMENT, personModel.getNumberDocument());
        newRegister.put(DataBaseSQLiteHelper.KEY_PERSON_NUMBER_PHONE, personModel.getNumberPhone());

        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_PERSON);
        db.insert(DataBaseSQLiteHelper.TABLE_PERSON, null, newRegister);
    }

    /**
     * ingresar aboutModel a la base de datos SQLite, si hay reemplazarlos
     *
     * @param aboutModel: objeto a ingresar a la base ded datos sqlite
     */
    private void setAboutSQLite(AboutModel aboutModel) {
        ContentValues newRegister = new ContentValues();

        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ID, aboutModel.getId());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_PHONEHOTEL, aboutModel.getPhoneHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_NAMEHOTEL, aboutModel.getNameHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_MISION, aboutModel.getMision());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_VISION, aboutModel.getVision());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESS, aboutModel.getAddress());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_SCOPE, aboutModel.getScope());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_HISTORY, aboutModel.getHistory());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_FUNDATION, aboutModel.getFundation());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_WATCHWORD, aboutModel.getWatchWord());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_OBJETIVE, aboutModel.getObjetive());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_EMAIL, aboutModel.getEmail());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_DESCRIPTION, aboutModel.getDescription());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_LOGOHOTEL, aboutModel.getLogoHotel());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSX, aboutModel.getAddressGPSX());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSGPSY, aboutModel.getAddressGPSY());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_ADDRESSIMAGE, aboutModel.getAddressImage());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_TYPEHOTEL, aboutModel.getType());
        newRegister.put(DataBaseSQLiteHelper.KEY_ABOUT_SITEWEBHOTEL, aboutModel.getSiteWeb());

        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_ABOUT);
        db.insert(DataBaseSQLiteHelper.TABLE_ABOUT, null, newRegister);
    }

    /**
     * ingresar la lista de servicios en la base da datos SQLite
     * @param servicesModel: lista de servicios
     */
    private void setServiceSQLite(ArrayList<ServiceModel> servicesModel) {
        db.execSQL("DELETE FROM " + DataBaseSQLiteHelper.TABLE_SERVICE);

        for (ServiceModel serviceModel: servicesModel) {
            ContentValues serviceContent = new ContentValues();

            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_ID, serviceModel.getServiceId());
            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_NAME, serviceModel.getServiceName());
            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_TYPE, serviceModel.getServiceType());
            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_DESCRIPTION, serviceModel.getServiceDescription());
            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_IMAGE, serviceModel.getServiceImage());
            serviceContent.put(DataBaseSQLiteHelper.KEY_SERVICE_RESERVED, serviceModel.getServiceReserved());

            db.insert(DataBaseSQLiteHelper.TABLE_SERVICE, null, serviceContent);
        }
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
     * cerrar conexion con  SQLite
     */
    public void destroy() {
        if (db != null)
            db.close();
    }
}
