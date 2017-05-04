package com.umss.sistemas.tesis.hotel.model;


public class ServicePriceModel {
    private int servicePriceId;
    private String servicePriceNameMoney;
    private int servicePriceUnit;
    private int servicePriceDay;
    private int servicePriceHour;
    private double servicePricePrice;
    private int servicePricePointObtain;
    private int servicePricePointRequired;
    private int servicePriceKey;

    public ServicePriceModel() {
        this.servicePriceId = 0;
        this.servicePriceNameMoney = "";
        this.servicePriceUnit = 0;
        this.servicePriceDay = 0;
        this.servicePriceHour = 0;
        this.servicePricePrice = 0;
        this.servicePricePointObtain = 0;
        this.servicePricePointRequired = 0;
        this.servicePriceKey=0;
    }

    public int getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(int servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public String getServicePriceNameMoney() {
        return servicePriceNameMoney;
    }

    public void setServicePriceNameMoney(String servicePriceNameMoney) {
        this.servicePriceNameMoney = servicePriceNameMoney;
    }

    public int getServicePriceUnit() {
        return servicePriceUnit;
    }

    public void setServicePriceUnit(int servicePriceUnit) {
        this.servicePriceUnit = servicePriceUnit;
    }

    public int getServicePriceDay() {
        return servicePriceDay;
    }

    public void setServicePriceDay(int servicePriceDay) {
        this.servicePriceDay = servicePriceDay;
    }

    public int getServicePriceHour() {
        return servicePriceHour;
    }

    public void setServicePriceHour(int servicePriceHour) {
        this.servicePriceHour = servicePriceHour;
    }

    public double getServicePricePrice() {
        return servicePricePrice;
    }

    public void setServicePricePrice(double servicePricePrice) {
        this.servicePricePrice = servicePricePrice;
    }

    public int getServicePricePointObtain() {
        return servicePricePointObtain;
    }

    public void setServicePricePointObtain(int servicePricePointObtain) {
        this.servicePricePointObtain = servicePricePointObtain;
    }

    public int getServicePricePointRequired() {
        return servicePricePointRequired;
    }

    public void setServicePricePointRequired(int servicePricePointRequired) {
        this.servicePricePointRequired = servicePricePointRequired;
    }

    public int getServicePriceKey() {
        return servicePriceKey;
    }

    public void setServicePriceKey(int servicePriceKey) {
        this.servicePriceKey = servicePriceKey;
    }
}
