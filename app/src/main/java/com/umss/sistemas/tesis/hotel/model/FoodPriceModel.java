package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

public class FoodPriceModel extends ModelParent {
    private int id;
    private int idKeyFood;
    private String typeMoney;
    private double price;
    private int pointObtain;
    private int pointRequired;

    public FoodPriceModel() {
        this.id = 0;
        this.idKeyFood = 0;
        this.typeMoney = "";
        this.price = 0;
        this.pointObtain = 0;
        this.pointRequired = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKeyFood() {
        return idKeyFood;
    }

    public void setIdKeyFood(int idKeyFood) {
        this.idKeyFood = idKeyFood;
    }

    public String getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(String typeMoney) {
        this.typeMoney = typeMoney;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
