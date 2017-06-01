package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.io.Serializable;

/**
 * Created by ruben on 20/05/2017
 */

public class ReserveModel extends ModelParent {
    private int idConsume;
    private String nameRoomModel;
    private String descriptionRoomModel;
    private int nAdult;
    private int nBoy;
    private int unit;

    public ReserveModel() {
        this.idConsume = 0;
        this.nameRoomModel = "";
        this.descriptionRoomModel = "";
        this.nAdult = 0;
        this.nBoy = 0;
        this.unit = 0;
    }

    public int getIdConsume() {
        return idConsume;
    }

    public void setIdConsume(int idConsume) {
        this.idConsume = idConsume;
    }

    public String getNameRoomModel() {
        return nameRoomModel;
    }

    public void setNameRoomModel(String nameRoomModel) {
        this.nameRoomModel = nameRoomModel;
    }

    public String getDescriptionRoomModel() {
        return descriptionRoomModel;
    }

    public void setDescriptionRoomModel(String descriptionRoomModel) {
        this.descriptionRoomModel = descriptionRoomModel;
    }

    public int getnAdult() {
        return nAdult;
    }

    public void setnAdult(int nAdult) {
        this.nAdult = nAdult;
    }

    public int getnBoy() {
        return nBoy;
    }

    public void setnBoy(int nBoy) {
        this.nBoy = nBoy;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
