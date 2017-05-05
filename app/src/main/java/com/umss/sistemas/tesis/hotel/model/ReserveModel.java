package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class ReserveModel extends ModelParent {
    private int idReserve;
    private String dateReserved;
    private String timeReserved;
    private String dateInReserve;
    private String timeInReserve;
    private String dateOutReserve;
    private String timeOutReserve;
    private double price;
    private double priceIn;
    private double priceOut;
    private String typeRoom;
    private String nameRoom;
    private ArrayList<ArticleModel> articleModel;


}
