package com.umss.sistemas.tesis.hotel.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.umss.sistemas.tesis.hotel.model.AboutModel;
import com.umss.sistemas.tesis.hotel.model.CalendarModel;
import com.umss.sistemas.tesis.hotel.model.CheckModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeFoodModel;
import com.umss.sistemas.tesis.hotel.model.ConsumeServiceModel;
import com.umss.sistemas.tesis.hotel.model.FoodMenuModel;
import com.umss.sistemas.tesis.hotel.model.FrequentlyModel;
import com.umss.sistemas.tesis.hotel.model.LoginModel;
import com.umss.sistemas.tesis.hotel.model.MessageModel;
import com.umss.sistemas.tesis.hotel.model.OfferModel;
import com.umss.sistemas.tesis.hotel.model.PersonModel;
import com.umss.sistemas.tesis.hotel.model.ReserveSearchModel;
import com.umss.sistemas.tesis.hotel.model.ServiceModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourImageModel;
import com.umss.sistemas.tesis.hotel.model.SiteTourModel;
import com.umss.sistemas.tesis.hotel.services.ServiceActivity;
import com.umss.sistemas.tesis.hotel.services.ServiceCheck;
import com.umss.sistemas.tesis.hotel.services.ServiceConsume;
import com.umss.sistemas.tesis.hotel.services.ServiceConsumeFood;
import com.umss.sistemas.tesis.hotel.services.ServiceFoodMenu;
import com.umss.sistemas.tesis.hotel.services.ServiceFrequently;
import com.umss.sistemas.tesis.hotel.services.ServiceInfo;
import com.umss.sistemas.tesis.hotel.services.ServiceMessage;
import com.umss.sistemas.tesis.hotel.services.ServiceOffer;
import com.umss.sistemas.tesis.hotel.services.ServicePerson;
import com.umss.sistemas.tesis.hotel.services.ServiceProfile;
import com.umss.sistemas.tesis.hotel.services.ServiceServices;
import com.umss.sistemas.tesis.hotel.services.ServiceSiteTour;
import com.umss.sistemas.tesis.hotel.services.ServiceSiteTourImage;

import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceHelper{
    protected SQLiteDatabase db;

    public ServiceHelper(Context context) {
        DBSQLite sync = new DBSQLite(context, DBSQLite.DATABASE_NAME, null, DBSQLite.DATABASE_VERSION);
        db = sync.getWritableDatabase();
    }

    public AboutModel getAboutModel() {
        ServiceInfo serviceInfo = new ServiceInfo(db);
        return serviceInfo.getAboutModel();
    }

    public LoginModel getLoginModel() {
        ServiceProfile serviceProfile = new ServiceProfile(db);
        return serviceProfile.getLoginModel();
    }

    public PersonModel getPersonModel(int idPerson) {
        ServicePerson servicePerson = new ServicePerson(db);
        return servicePerson.getPersonModel(idPerson);
    }

    public ArrayList<CalendarModel> getActivityModel(int idActivity) {
        ServiceActivity serviceActivity = new ServiceActivity(db);
        return serviceActivity.getModels(idActivity);
    }

    public ArrayList<CheckModel> getCheckModel(int idCheck, int idStateCheck, int idTypeCheck) {
        ServiceCheck serviceCheck = new ServiceCheck(db);
        return serviceCheck.getCheckModel(idCheck, idStateCheck, idTypeCheck);
    }

    public ArrayList<FrequentlyModel> getFrequentlyModel(int idFrequently) {
        ServiceFrequently serviceFrequently = new ServiceFrequently(db);
        return serviceFrequently.getFrequentlyModel(idFrequently);
    }

    public ArrayList<FoodMenuModel> getFoodMenuModel(int idFoodMenu) {
        ServiceFoodMenu serviceFoodMenu = new ServiceFoodMenu(db);
        return serviceFoodMenu.getFoodMenuModel(idFoodMenu);
    }

    public ArrayList<MessageModel> getMessageModel(int idMessage) {
        ServiceMessage serviceMessage = new ServiceMessage(db);
        return serviceMessage.getMessageModel(idMessage);
    }

    public ArrayList<OfferModel> getOfferModel(int idOffer) {
        ServiceOffer serviceOffer = new ServiceOffer(db);
        return serviceOffer.getOfferModel(idOffer);
    }

    public ArrayList<ReserveSearchModel> getRoomAvailableModel(JSONObject obj) {
        ServiceCheck serviceCheck = new ServiceCheck(db);
        return serviceCheck.getRoomAvailableModel(obj);
    }

    public ArrayList<ServiceModel> getServiceModel(int idService) {
        ServiceServices serviceServices = new ServiceServices(db);
        return serviceServices.getServiceModel(idService);
    }

    public ArrayList<SiteTourModel> getSiteTourModel(int idSiteTour) {
        ServiceSiteTour serviceSiteTour = new ServiceSiteTour(db);
        return serviceSiteTour.getSiteTourModel(idSiteTour);
    }

    public ArrayList<SiteTourImageModel> getSiteTourImageModel(int idSiteTour) {
        ServiceSiteTourImage serviceSiteTourImage = new ServiceSiteTourImage(db);
        return serviceSiteTourImage.getSiteTourImageModel(idSiteTour);
    }

    public void insertConsumeFoodSQLite(ArrayList<ConsumeFoodModel> foodModelArrayList) {
        ServiceConsumeFood serviceConsumeFood=new ServiceConsumeFood(db);
        serviceConsumeFood.insertConsumeFoodSQLite(foodModelArrayList);
    }

    public void insertConsumeSQLite(ArrayList<ConsumeServiceModel> listConsumeService) {
        ServiceConsume serviceConsume=new ServiceConsume(db);
        serviceConsume.insertConsumeSQLite(listConsumeService);
    }

    public boolean isAccountActive() {
        ServiceProfile serviceProfile = new ServiceProfile(db);
        return serviceProfile.isAccountActive();
    }

    public void logoutAction() {
        ServiceProfile serviceProfile = new ServiceProfile(db);
        serviceProfile.logoutAction();
    }

    public void syncUpFrequently(JSONObject obj) {
        ServiceFrequently serviceFrequently = new ServiceFrequently(db);
        serviceFrequently.syncUpFrequently(obj);
    }

    public void syncUpCheck(JSONObject obj) {
        ServiceCheck serviceCheck = new ServiceCheck(db);
        serviceCheck.syncUpCheck(obj);
    }

    public void syncUpPerson(JSONObject obj) {
        ServicePerson servicePerson = new ServicePerson(db);
        servicePerson.syncUpPerson(obj);
    }

    public void syncUpSiteTour(JSONObject obj) {
        ServiceSiteTour serviceSiteTour = new ServiceSiteTour(db);
        serviceSiteTour.syncUpSiteTour(obj);
    }

    public void syncUpCalendar(JSONObject obj) {
        ServiceActivity serviceActivity = new ServiceActivity(db);
        serviceActivity.syncUp(obj);
    }

    public void syncUpMessages(JSONObject obj) {
        ServiceMessage serviceMessage = new ServiceMessage(db);
        serviceMessage.syncUpMessages(obj);
    }

    public void syncUpFoodMenu(JSONObject obj) {
        ServiceFoodMenu serviceFoodMenu = new ServiceFoodMenu(db);
        serviceFoodMenu.syncUpFoodMenu(obj);
    }

    public void syncUpOffer(JSONObject obj) {
        ServiceOffer serviceOffer = new ServiceOffer(db);
        serviceOffer.syncUpOffer(obj);
    }

    public void syncUpAbout(JSONObject obj) {
        ServiceInfo serviceInfo = new ServiceInfo(db);
        serviceInfo.syncUpAbout(obj);
    }

    public void syncUpService(JSONObject obj) {
        ServiceServices serviceServices = new ServiceServices(db);
        serviceServices.syncUpService(obj);
    }

    public void syncUpLogin(int idPerson, String passText, int state) {
        ServiceProfile serviceProfile = new ServiceProfile(db);
        serviceProfile.syncUpLogin(idPerson, passText, state);
    }

    /**
     * cerrar conexion con  SQLite
     */
    public void destroy() {
        if (db != null)
            db.close();
    }
}
