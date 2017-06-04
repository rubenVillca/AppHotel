package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

public class PersonModel extends ModelParent {
    protected int idPerson;
    protected byte sexPerson;
    protected int pointPerson;
    protected int numberDocument;
    protected int numberPhone;
    protected String emailPerson;
    protected String namePerson;
    protected String nameLastPerson;
    protected String cityPerson;
    protected String countryPerson;
    protected String addressPerson;
    protected String imgPerson;
    protected String dateBornPerson;
    protected String dateRegisterPerson;
    protected String typeDocument;

    public PersonModel() {
        this.idPerson = 0;
        this.emailPerson = "";
        this.namePerson = "";
        this.nameLastPerson = "";
        this.cityPerson = "";
        this.countryPerson = "";
        this.pointPerson = 0;
        this.sexPerson = -1;
        this.addressPerson = "";
        this.imgPerson = "";
        this.dateBornPerson = "";
        this.dateRegisterPerson = "";
        this.typeDocument = "";
        this.numberDocument = 0;
        this.numberPhone = 0;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public int getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(int numberDocument) {
        this.numberDocument = numberDocument;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(int numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmailPerson() {
        return emailPerson;
    }

    public void setEmailPerson(String emailPerson) {
        this.emailPerson = emailPerson;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getNameLastPerson() {
        return nameLastPerson;
    }

    public void setNameLastPerson(String nameLastPerson) {
        this.nameLastPerson = nameLastPerson;
    }

    public String getCityPerson() {
        return cityPerson;
    }

    public void setCityPerson(String cityPerson) {
        this.cityPerson = cityPerson;
    }

    public String getCountryPerson() {
        return countryPerson;
    }

    public void setCountryPerson(String countryPerson) {
        this.countryPerson = countryPerson;
    }

    public int getPointPerson() {
        return pointPerson;
    }

    public void setPointPerson(int pointPerson) {
        this.pointPerson = pointPerson;
    }

    public byte getSexPerson() {
        return sexPerson;
    }

    public void setSexPerson(byte sexPerson) {
        this.sexPerson = sexPerson;
    }

    public String getAddressPerson() {
        return addressPerson;
    }

    public void setAddressPerson(String addressPerson) {
        this.addressPerson = addressPerson;
    }

    public String getImgPerson() {
        return imgPerson;
    }

    public void setImgPerson(String imgPerson) {
        this.imgPerson = imgPerson;
    }

    public String getDateBornPerson() {
        return dateBornPerson;
    }

    public void setDateBornPerson(String dateBornPerson) {
        this.dateBornPerson = dateBornPerson;
    }

    public String getDateRegisterPerson() {
        return dateRegisterPerson;
    }

    public void setDateRegisterPerson(String dateRegisterPerson) {
        this.dateRegisterPerson = dateRegisterPerson;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }
}

