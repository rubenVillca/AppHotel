package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class ConsumModel extends ModelParent {
    private int idConsum;
    private String dateInConsum;
    private String timeInConsum;
    private String dateOutConsum;
    private String timeOutConsum;
    private double price;
    private double pay;
    private String typeRoom;
    private String nameRoom;
    private boolean state;
    private int idKeyService;
    private int idKeyCheck;
    private ArrayList<ArticleModel> articleModel;
    private ArrayList<MemberModel> memberModelArrayList;

    public ConsumModel() {
        idConsum=0;
        dateInConsum="";
        timeInConsum="";
        dateOutConsum="";
        timeOutConsum="";
        price=0;
        pay=0;
        typeRoom="";
        nameRoom="";
        state=false;
        idKeyService=0;
        idKeyCheck=0;
        articleModel=new ArrayList<>();
        memberModelArrayList =new ArrayList<>();
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public ArrayList<MemberModel> getMemberModelArrayList() {
        return memberModelArrayList;
    }

    public void setMemberModelArrayList(ArrayList<MemberModel> memberModelArrayList) {
        this.memberModelArrayList = memberModelArrayList;
    }

    public int getIdKeyCheck() {
        return idKeyCheck;
    }

    public void setIdKeyCheck(int idKeyCheck) {
        this.idKeyCheck = idKeyCheck;
    }

    public int getIdKeyService() {
        return idKeyService;
    }

    public void setIdKeyService(int idKeyService) {
        this.idKeyService = idKeyService;
    }

    public int getIdConsum() {
        return idConsum;
    }

    public void setIdConsum(int idConsum) {
        this.idConsum = idConsum;
    }

    public String getDateInConsum() {
        return dateInConsum;
    }

    public void setDateInConsum(String dateInConsum) {
        this.dateInConsum = dateInConsum;
    }

    public String getTimeInConsum() {
        return timeInConsum;
    }

    public void setTimeInConsum(String timeInConsum) {
        this.timeInConsum = timeInConsum;
    }

    public String getDateOutConsum() {
        return dateOutConsum;
    }

    public void setDateOutConsum(String dateOutConsum) {
        this.dateOutConsum = dateOutConsum;
    }

    public String getTimeOutConsum() {
        return timeOutConsum;
    }

    public void setTimeOutConsum(String timeOutConsum) {
        this.timeOutConsum = timeOutConsum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getIdService() {
        return idKeyService;
    }

    public void setIdService(int idKeyService) {
        this.idKeyService = idKeyService;
    }

    public ArrayList<ArticleModel> getArticleModel() {
        return articleModel;
    }

    public void setArticleModel(ArrayList<ArticleModel> articleModel) {
        this.articleModel = articleModel;
    }
}
