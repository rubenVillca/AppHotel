package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class OfferModel  extends ModelParent {

    private int id;
    private String name;
    private String description;
    private String dateIni;
    private String timeIni;
    private String dateFin;
    private String timeFin;
    private int idKeyService;
    private int state;
    private String image;
    private String nameType;
    private String descriptionType;
    private ArrayList<ServicePriceDetailModel> servicePriceDetailModel;

    public OfferModel() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.dateIni = "";
        this.timeIni = "";
        this.dateFin = "";
        this.timeFin = "";
        this.idKeyService = 0;
        this.state = 0;
        this.image="";
        this.nameType="";
        this.descriptionType="";
        this.servicePriceDetailModel =new ArrayList<>();
    }

    public ArrayList<ServicePriceDetailModel> getServicePriceDetailModel() {
        return servicePriceDetailModel;
    }

    public void setServicePriceDetailModel(ArrayList<ServicePriceDetailModel> servicePriceDetailModel) {
        this.servicePriceDetailModel = servicePriceDetailModel;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameTypeService) {
        this.nameType = nameTypeService;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateIni() {
        return dateIni;
    }

    public void setDateIni(String dateIni) {
        this.dateIni = dateIni;
    }

    public String getTimeIni() {
        return timeIni;
    }

    public void setTimeIni(String timeIni) {
        this.timeIni = timeIni;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getTimeFin() {
        return timeFin;
    }

    public void setTimeFin(String timeFin) {
        this.timeFin = timeFin;
    }

    public int getIdKeyService() {
        return idKeyService;
    }

    public void setIdKeyService(int idKeyService) {
        this.idKeyService = idKeyService;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
