package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

/**
 * Created by ruben on 09/05/2017
 */
public class CheckModel extends ModelParent {
    private int id;
    private int state;
    private String type;
    private String dateIn;
    private String timeIn;
    private String dateEnd;
    private String timeEnd;
    private ArrayList<CardModel> cardTargetArrayList;
    private ArrayList<ConsumeModel> consumeModelArrayList;
    private ArrayList<ConsumeFoodModel> consumeFoodModelArrayList;

    public CheckModel() {
        id=0;
        state=0;
        type="";
        dateIn="";
        timeIn="";
        dateEnd="";
        timeEnd="";
        cardTargetArrayList=new ArrayList<>();
        consumeModelArrayList = new ArrayList<>();
        consumeFoodModelArrayList=new ArrayList<>();
    }

    public ArrayList<ConsumeFoodModel> getConsumeFoodModelArrayList() {
        return consumeFoodModelArrayList;
    }

    public void setConsumeFoodModelArrayList(ArrayList<ConsumeFoodModel> consumeFoodModelArrayList) {
        this.consumeFoodModelArrayList = consumeFoodModelArrayList;
    }

    public ArrayList<ConsumeModel> getConsumeModelArrayList() {
        return consumeModelArrayList;
    }

    public void setConsumeModelArrayList(ArrayList<ConsumeModel> consumeModelArrayList) {
        this.consumeModelArrayList = consumeModelArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
}
