package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

public class SiteTourImageModel  extends ModelParent {
    private int idSiteTourImage;
    private String nameSiteTourImage;
    private String descriptionSiteTourImage;
    private String addressSiteTour;
    private int stateSiteTourImage;
    private int idSiteTour;

    public SiteTourImageModel() {
        this.idSiteTourImage = 0;
        this.nameSiteTourImage = "";
        this.descriptionSiteTourImage = "";
        this.addressSiteTour="";
        this.stateSiteTourImage = 0;
        this.idSiteTour = 0;
    }

    public String getDescriptionSiteTourImage() {
        return descriptionSiteTourImage;
    }

    public String getAddressSiteTour() {
        return addressSiteTour;
    }

    public void setAddressSiteTour(String addressSiteTour) {
        this.addressSiteTour = addressSiteTour;
    }

    public int getIdSiteTourImage() {
        return idSiteTourImage;
    }

    public void setIdSiteTourImage(int idSiteTourImage) {
        this.idSiteTourImage = idSiteTourImage;
    }

    public String getNameSiteTourImage() {
        return nameSiteTourImage;
    }

    public void setNameSiteTourImage(String nameSiteTourImage) {
        this.nameSiteTourImage = nameSiteTourImage;
    }

    public String getDescripcionSiteTourImage() {
        return descriptionSiteTourImage;
    }

    public void setDescriptionSiteTourImage(String descriptionSiteTourImage) {
        this.descriptionSiteTourImage = descriptionSiteTourImage;
    }

    public int getStateSiteTourImage() {
        return stateSiteTourImage;
    }

    public void setStateSiteTourImage(int stateSiteTourImage) {
        this.stateSiteTourImage = stateSiteTourImage;
    }

    public int getIdSiteTour() {
        return idSiteTour;
    }

    public void setIdSiteTour(int idSiteTour) {
        this.idSiteTour = idSiteTour;
    }
}
