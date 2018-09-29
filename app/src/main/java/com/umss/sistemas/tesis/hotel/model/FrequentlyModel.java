package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

/**
 * Created by ruben on 04/05/2017
 */

public class FrequentlyModel extends ModelParent {
    private int id;
    private int idInquest;
    private String nameInquest;
    private String question;
    private String response;
    private int typeInquest;
    private boolean isActive;

    public FrequentlyModel() {
        this.id = 0;
        this.idInquest = 0;
        this.nameInquest = "";
        this.question = "";
        this.response = "";
        this.typeInquest = 0;
        this.isActive = false;
    }

    public String getNameInquest() {
        return nameInquest;
    }

    public void setNameInquest(String nameInquest) {
        this.nameInquest = nameInquest;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question=question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response=response;
    }

    public int getTypeInquest() {
        return typeInquest;
    }

    public void setTypeInquest(int typeInquest) {
        this.typeInquest = typeInquest;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
