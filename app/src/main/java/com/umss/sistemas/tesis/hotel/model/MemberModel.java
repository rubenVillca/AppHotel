package com.umss.sistemas.tesis.hotel.model;

/**
 * Created by ruben on 08/05/2017
 */

public class MemberModel extends PersonModel {

    private int idKeyConsum;

    public MemberModel() {
        super();
        idKeyConsum = 0;
    }

    public int getIdKeyConsum() {
        return idKeyConsum;
    }

    public void setIdKeyConsum(int idKeyConsum) {
        this.idKeyConsum = idKeyConsum;
    }
}
