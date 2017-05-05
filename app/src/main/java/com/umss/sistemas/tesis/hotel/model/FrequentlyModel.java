package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

/**
 * Created by ruben on 04/05/2017.
 */

public class FrequentlyModel extends ModelParent {
    private int id;
    private int idInquest;
    private String type;
    private String question;
    private String response;
    private boolean isFrequently;
    private boolean isActive;

    public FrequentlyModel() {
        this.id = 0;
        this.idInquest = 0;
        this.type = "";
        this.question = "";
        this.response = "";
        this.isFrequently = false;
        this.isActive = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInquest() {
        return idInquest;
    }

    public void setIdInquest(int idInquest) {
        this.idInquest = idInquest;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isFrequently() {
        return isFrequently;
    }

    public void setFrequently(boolean frequently) {
        isFrequently = frequently;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
