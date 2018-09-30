package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class FoodMenuModel extends ModelParent {
    private int id;
    private String name;
    private String dateStart;
    private String dateEnd;
    private ArrayList<FoodModel> foodModelArrayList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FoodMenuModel() {
        this.name = "";
        this.dateStart = "";
        this.dateEnd = "";
        foodModelArrayList=new ArrayList<>();
    }

    public ArrayList<FoodModel> getFoodModelArrayList() {
        return foodModelArrayList;
    }

    public void setFoodModelArrayList(ArrayList<FoodModel> foodModelArrayList) {
        this.foodModelArrayList = foodModelArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
