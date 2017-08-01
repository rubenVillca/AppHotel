package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

/**
 * Created by ruben on 23/05/2017
 */

public class ReserveSearchModel extends ModelParent {
    private int idTypeRoom;
    private int unitAdult;
    private int unitBoy;
    private int unitPet;
    private int unitRoom;
    private int idService;
    private String nameTypeRoom;
    private String descriptionTypeRoom;
    private String nameService;
    private String imageTypeRoom;
    private ArrayList<ServicePriceConsumeModel> servicePriceConsumeModels;

    public ReserveSearchModel() {
        this.idTypeRoom = 0;
        this.nameTypeRoom = "";
        this.descriptionTypeRoom = "";
        this.unitAdult = 0;
        this.unitBoy = 0;
        this.unitPet = 0;
        this.unitRoom = 0;
        this.idService = 0;
        this.nameService = "";
        this.imageTypeRoom = "";
        this.servicePriceConsumeModels =new ArrayList<>();
    }

    public ArrayList<ServicePriceConsumeModel> getServicePriceConsumeModels() {
        return servicePriceConsumeModels;
    }

    public void setServicePriceConsumeModels(ArrayList<ServicePriceConsumeModel> servicePriceConsumeModels) {
        this.servicePriceConsumeModels = servicePriceConsumeModels;
    }

    public int getIdTypeRoom() {
        return idTypeRoom;
    }

    public void setIdTypeRoom(int idTypeRoom) {
        this.idTypeRoom = idTypeRoom;
    }

    public int getUnitAdult() {
        return unitAdult;
    }

    public void setUnitAdult(int unitAdult) {
        this.unitAdult = unitAdult;
    }

    public int getUnitBoy() {
        return unitBoy;
    }

    public void setUnitBoy(int unitBoy) {
        this.unitBoy = unitBoy;
    }

    public int getUnitPet() {
        return unitPet;
    }

    public void setUnitPet(int unitPet) {
        this.unitPet = unitPet;
    }

    public int getUnitRoom() {
        return unitRoom;
    }

    public void setUnitRoom(int unitRoom) {
        this.unitRoom = unitRoom;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameTypeRoom() {
        return nameTypeRoom;
    }

    public void setNameTypeRoom(String nameTypeRoom) {
        this.nameTypeRoom = nameTypeRoom;
    }

    public String getDescriptionTypeRoom() {
        return descriptionTypeRoom;
    }

    public void setDescriptionTypeRoom(String descriptionTypeRoom) {
        this.descriptionTypeRoom = descriptionTypeRoom;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getImageTypeRoom() {
        return imageTypeRoom;
    }

    public void setImageTypeRoom(String imageTypeRoom) {
        this.imageTypeRoom = imageTypeRoom;
    }
}
