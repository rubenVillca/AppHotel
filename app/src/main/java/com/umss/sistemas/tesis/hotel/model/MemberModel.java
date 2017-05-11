package com.umss.sistemas.tesis.hotel.model;

/**
 * Created by ruben on 08/05/2017
 */

public class MemberModel extends PersonModel {

    private String typeMember;
    private int idKeyConsum;
    private int idKeyCheck;

    public MemberModel() {
        super();
        typeMember = "";
        idKeyCheck = 0;
        idKeyConsum = 0;
    }

    public int getIdKeyConsum() {
        return idKeyConsum;
    }

    public void setIdKeyConsum(int idKeyConsum) {
        this.idKeyConsum = idKeyConsum;
    }

    public int getIdKeyCheck() {
        return idKeyCheck;
    }

    public void setIdKeyCheck(int idKeyCheck) {
        this.idKeyCheck = idKeyCheck;
    }

    public String getTypeMember() {
        return typeMember;
    }

    public void setTypeMember(String typeMember) {
        this.typeMember = typeMember;
    }
}
