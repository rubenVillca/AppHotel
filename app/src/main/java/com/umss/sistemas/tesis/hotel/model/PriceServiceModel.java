package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.io.Serializable;

/**
 * Created by ruben on 25/05/2017
 */

public class PriceServiceModel extends ModelParent {
    private int pointObtain;
    private int pointRequired;
    private int idKeyCost;
    private String nameTypeMoney;
    private double priceService;
    private int unitService;
    private int unitHour;
    private int unitDay;

    public PriceServiceModel() {
        this.idKeyCost = 0;
        this.nameTypeMoney = "";
        this.priceService = 0;
        this.pointObtain = 0;
        this.pointRequired = 0;
        this.unitService =0;
        this.unitHour = 0;
        this.unitDay = 0;
    }

    public int getUnitHour() {
        return unitHour;
    }

    public void setUnitHour(int unitHour) {
        this.unitHour = unitHour;
    }

    public int getUnitDay() {
        return unitDay;
    }

    public void setUnitDay(int unitDay) {
        this.unitDay = unitDay;
    }

    public int getPointObtain() {
        return pointObtain;
    }

    public void setPointObtain(int pointObtain) {
        this.pointObtain = pointObtain;
    }

    public int getPointRequired() {
        return pointRequired;
    }

    public void setPointRequired(int pointRequired) {
        this.pointRequired = pointRequired;
    }

    public int getIdKeyCost() {
        return idKeyCost;
    }

    public void setIdKeyCost(int idKeyCost) {
        this.idKeyCost = idKeyCost;
    }

    public String getNameTypeMoney() {
        return nameTypeMoney;
    }

    public void setNameTypeMoney(String nameTypeMoney) {
        this.nameTypeMoney = nameTypeMoney;
    }

    public double getPriceService() {
        return priceService;
    }

    public void setPriceService(double priceService) {
        this.priceService = priceService;
    }

    public int getUnitService() {
        return unitService;
    }

    public void setUnitService(int unitService) {
        this.unitService = unitService;
    }
}
