package com.umss.sistemas.tesis.hotel.model;

public class AboutModel {
    private int id;
    private int phoneHotel;
    private String nameHotel;
    private String mision;
    private String vision;
    private String address;
    private String scope;
    private String history;
    private String fundation;
    private String watchWord;
    private String objetive;
    private String email;
    private String description;
    private String logoHotel;
    private String addressGPSX;
    private String addressGPSY;
    private String addressImage;
    private String type;
    private String siteWeb;

    public AboutModel() {
        this.id = 0;
        this.phoneHotel = 0;
        this.nameHotel = "";
        this.mision = "";
        this.vision = "";
        this.address = "";
        this.scope = "";
        this.history = "";
        this.fundation = "";
        this.watchWord = "";
        this.objetive = "";
        this.email = "";
        this.description = "";
        this.logoHotel = "";
        this.addressGPSX = "";
        this.addressGPSY = "";
        this.addressImage = "";
        this.type = "";
        this.siteWeb="";

    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public int getPhoneHotel() {
        return phoneHotel;
    }

    public void setPhoneHotel(int phoneHotel) {
        this.phoneHotel = phoneHotel;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getFundation() {
        return fundation;
    }

    public void setFundation(String fundation) {
        this.fundation = fundation;
    }

    public String getWatchWord() {
        return watchWord;
    }

    public void setWatchWord(String watchWord) {
        this.watchWord = watchWord;
    }

    public String getObjetive() {
        return objetive;
    }

    public void setObjetive(String objetive) {
        this.objetive = objetive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoHotel() {
        return logoHotel;
    }

    public void setLogoHotel(String logoHotel) {
        this.logoHotel = logoHotel;
    }

    public String getAddressGPSX() {
        return addressGPSX;
    }

    public void setAddressGPSX(String addressGPSX) {
        this.addressGPSX = addressGPSX;
    }

    public String getAddressGPSY() {
        return addressGPSY;
    }

    public void setAddressGPSY(String addressGPSY) {
        this.addressGPSY = addressGPSY;
    }

    public String getAddressImage() {
        return addressImage;
    }

    public void setAddressImage(String addressImage) {
        this.addressImage = addressImage;
    }

}
