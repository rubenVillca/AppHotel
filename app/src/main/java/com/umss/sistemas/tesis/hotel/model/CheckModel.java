package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

/**
 * Created by ruben on 09/05/2017
 */
public class CheckModel extends ModelParent {
    private int id;
    private int idState;
    private int idType;
    private int valueState;
    private String nameState;
    private String type;
    private String dateIn;
    private String timeIn;
    private String dateEnd;
    private String timeEnd;
    private ArrayList<CardModel> cardTargetArrayList;
    private ArrayList<ConsumeServiceModel> consumeServiceModelArrayList;
    private ArrayList<ConsumeFoodModel> consumeFoodModelArrayList;

    public CheckModel() {
        id=0;
        idType=0;
        idState =0;
        valueState=0;
        nameState="";
        type="";
        dateIn="";
        timeIn="";
        dateEnd="";
        timeEnd="";
        cardTargetArrayList=new ArrayList<>();
        consumeServiceModelArrayList = new ArrayList<>();
        consumeFoodModelArrayList=new ArrayList<>();
    }

    public int getValueState() {
        return valueState;
    }

    public void setValueState(int valueState) {
        this.valueState = valueState;
    }

    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public ArrayList<ConsumeFoodModel> getConsumeFoodModelArrayList() {
        return consumeFoodModelArrayList;
    }

    public void setConsumeFoodModelArrayList(ArrayList<ConsumeFoodModel> consumeFoodModelArrayList) {
        this.consumeFoodModelArrayList = consumeFoodModelArrayList;
    }

    public ArrayList<ConsumeServiceModel> getConsumeServiceModelArrayList() {
        return consumeServiceModelArrayList;
    }

    public void setConsumeServiceModelArrayList(ArrayList<ConsumeServiceModel> consumeServiceModelArrayList) {
        this.consumeServiceModelArrayList = consumeServiceModelArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public ArrayList<CardModel> getCardTargetArrayList() {
        return cardTargetArrayList;
    }

    public void setCardTargetArrayList(ArrayList<CardModel> cardTargetArrayList) {
        this.cardTargetArrayList = cardTargetArrayList;
    }

    public double getTotal() {
        double total=0;
        for (ConsumeFoodModel consumeFood : consumeFoodModelArrayList) {
            total+=consumeFood.getPay()-consumeFood.getPrice();
        }
        for (ConsumeServiceModel consumeService : consumeServiceModelArrayList) {
            total+=consumeService.getPay()-consumeService.getPrice();
        }

        return total;
    }
}
