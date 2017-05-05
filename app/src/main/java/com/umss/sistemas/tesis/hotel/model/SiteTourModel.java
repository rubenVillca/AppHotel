package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class SiteTourModel extends ModelParent {
    private int idSite;
    private String addressSite;
    private Float gpsLatitudeSite;
    private Float gpsLongitudeSite;
    private String nameSite;
    private String descriptionSite;
    private int stateSite;
    private ArrayList<SiteTourImageModel> imagesSite;

    public SiteTourModel() {
        this.idSite = 0;
        this.addressSite = "";
        this.gpsLatitudeSite = 0f;
        this.gpsLongitudeSite = 0f;
        this.nameSite = "";
        this.descriptionSite = "";
        this.stateSite = 0;
        this.imagesSite = new ArrayList<>();
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public String getAddressSite() {
        return addressSite;
    }

    public void setAddressSite(String addressSite) {
        this.addressSite = addressSite;
    }

    public Float getGpsLatitudeSite() {
        return gpsLatitudeSite;
    }

    public void setGpsLatitudeSite(Float gpsLatitudeSite) {
        this.gpsLatitudeSite = gpsLatitudeSite;
    }

    public Float getGpsLongitudeSite() {
        return gpsLongitudeSite;
    }

    public void setGpsLongitudeSite(Float gpsLongitudeSite) {
        this.gpsLongitudeSite = gpsLongitudeSite;
    }

    public String getNameSite() {
        return nameSite;
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public String getDescriptionSite() {
        return descriptionSite;
    }

    public void setDescriptionSite(String descriptionSite) {
        this.descriptionSite = descriptionSite;
    }

    public int getStateSite() {
        return stateSite;
    }

    public void setStateSite(int stateSite) {
        this.stateSite = stateSite;
    }

    public ArrayList<SiteTourImageModel> getImagesSite() {
        return imagesSite;
    }

    public void setImagesSite(ArrayList<SiteTourImageModel> imagesSite) {
        this.imagesSite = imagesSite;
    }
}
