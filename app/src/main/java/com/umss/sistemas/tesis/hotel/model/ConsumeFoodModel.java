package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

/**
 * Created by ruben on 20/05/2017
 */

public class ConsumeFoodModel extends ModelParent{
    private int idConsume;
    private int idKeyCheck;
    private double price;
    private double pay;
    private String typeMoney;
    private String nameFood;
    private String descriptionFood;
    private int pointObtain;
    private int pointRequired;
    private int unitFood;

    public ConsumeFoodModel() {
        this.idConsume = 0;
        idKeyCheck=0;
        this.price = 0;
        this.pay = 0;
        this.typeMoney = "$";
        this.nameFood = "";
        this.descriptionFood = "";
        this.pointObtain = 0;
        this.pointRequired=0;
        this.unitFood = 0;
    }

    public int getIdKeyCheck() {
        return idKeyCheck;
    }

    public void setIdKeyCheck(int idKeyCheck) {
        this.idKeyCheck = idKeyCheck;
    }

    public int getIdConsume() {
        return idConsume;
    }

    public void setIdConsume(int idConsume) {
        this.idConsume = idConsume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(String typeMoney) {
        this.typeMoney = typeMoney;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
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

    public int getUnitFood() {
        return unitFood;
    }

    public void setUnitFood(int unitFood) {
        this.unitFood = unitFood;
    }
}
