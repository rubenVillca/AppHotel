package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class FoodModel extends ModelParent {
    private int id;
    private int idKeyMenu;
    private String name;
    private boolean state;
    private String type;
    private String description;
    private String image;
    private ArrayList<FoodPriceModel> listFoodPriceModel;

    public FoodModel() {
        this.id = 0;
        this.idKeyMenu = 0;
        this.name = "";
        this.state = false;
        this.description = "";
        this.image = "";
        this.type="";
        this.listFoodPriceModel=new ArrayList<>();
    }

    public ArrayList<FoodPriceModel> getListFoodPriceModel() {
        return listFoodPriceModel;
    }

    public void setListFoodPriceModel(ArrayList<FoodPriceModel> listFoodPriceModel) {
        this.listFoodPriceModel = listFoodPriceModel;
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

    public int getIdKeyMenu() {
        return idKeyMenu;
    }

    public void setIdKeyMenu(int idKeyMenu) {
        this.idKeyMenu = idKeyMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
