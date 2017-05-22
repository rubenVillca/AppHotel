package com.umss.sistemas.tesis.hotel.model;

/**
 * Created by ruben on 20/05/2017
 */

public class OccupationModel {
    private int idConsumeService;
    private int idRoom;
    private String nameRoom;
    private String imageRoom;
    private boolean stateRoom;
    private String typeRoom;
    private String descriptionTypeRoom;
    private int nAdult;
    private int nBoy;

    public OccupationModel() {
        this.idConsumeService = 0;
        idRoom=0;
        this.nameRoom = "";
        this.imageRoom = "";
        this.stateRoom = false;
        this.typeRoom = "";
        this.descriptionTypeRoom = "";
        this.nAdult = 0;
        this.nBoy = 0;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getIdConsumeService() {
        return idConsumeService;
    }

    public void setIdConsumeService(int idConsumeService) {
        this.idConsumeService = idConsumeService;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getImageRoom() {
        return imageRoom;
    }

    public void setImageRoom(String imageRoom) {
        this.imageRoom = imageRoom;
    }

    public boolean isStateRoom() {
        return stateRoom;
    }

    public void setStateRoom(boolean stateRoom) {
        this.stateRoom = stateRoom;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public String getDescriptionTypeRoom() {
        return descriptionTypeRoom;
    }

    public void setDescriptionTypeRoom(String descriptionTypeRoom) {
        this.descriptionTypeRoom = descriptionTypeRoom;
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
}
