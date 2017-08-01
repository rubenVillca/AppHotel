package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ServiceModel  extends ModelParent {
    private int id;
    private int reserved;
    private String name;
    private String image;
    private String description;
    private int idType;
    private String nameType;
    private int valueType;
    private ArrayList<ServicePriceDetailModel> servicePrice;

    public ServiceModel() {
        id = 0;
        name="";
        reserved = 0;
        this.image = "";
        this.nameType = "";
        description = "";
        idType =0;
        valueType =0;
        servicePrice=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ServicePriceDetailModel> getServicePrice() {
        return servicePrice;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public void setServicePrice(ArrayList<ServicePriceDetailModel> servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        try {
            this.description = new String(description.getBytes("ISO-8859-15"), "UTF-8");
            // unreadable -> Ã¤Ã¶Ã¼ÃÃÃÃÃ¡Ã©Ã­Ã³ÃºÃÃÃÃÃÃ Ã¨Ã¬Ã²Ã¹ÃÃÃÃÃÃ±Ã
        } catch (UnsupportedEncodingException e) {
            // handle error
        }
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
