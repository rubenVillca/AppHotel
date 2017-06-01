package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.io.Serializable;

/**
 * Created by ruben on 08/05/2017
 */
public class CardModel extends ModelParent {
    private int id;
    private int idKeyCheck;
    private String nameType;
    private long number;
    private int month;
    private int year;
    private int ccv;
    private boolean isValid;
    private String typeMoney;
    private double deposit;

    public CardModel() {
        id = 0;
        idKeyCheck = 0;
        nameType = "";
        number = 0;
        month = 0;
        year = 0;
        ccv = 0;
        isValid = false;
        typeMoney = "";
        deposit = 0;
    }

    public String getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(String typeMoney) {
        this.typeMoney = typeMoney;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKeyCheck() {
        return idKeyCheck;
    }

    public void setIdKeyCheck(int idKeyCheck) {
        this.idKeyCheck = idKeyCheck;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
