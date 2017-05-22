package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

import java.util.ArrayList;

public class ConsumeModel extends ModelParent {
    private int idConsum;
    private String dateInConsum;
    private String timeInConsum;
    private String dateOutConsum;
    private String timeOutConsum;
    private double price;
    private double pay;
    private String typeMoney;
    private boolean state;
    private int idKeyService;
    private String nameService;
    private int idKeyCheck;
    private int pointObtain;
    private int pointRequired;
    private int nDay;
    private int nHour;
    private int unit;

    private ArrayList<ArticleModel> articleModel;
    private ArrayList<MemberModel> memberModelArrayList;
    private ArrayList<ReserveModel> reserveModelArrayList;
    private ArrayList<OccupationModel> occupationModelArrayList;

    public ConsumeModel() {
        idConsum=0;
        dateInConsum="";
        timeInConsum="";
        dateOutConsum="";
        timeOutConsum="";
        price=0;
        pay=0;
        typeMoney="$";
        state=false;
        idKeyService=0;
        nameService="";
        idKeyCheck=0;
        pointObtain=0;
        pointRequired=0;
        nDay=0;
        nHour=0;
        unit=0;

        articleModel=new ArrayList<>();
        memberModelArrayList =new ArrayList<>();
        reserveModelArrayList=new ArrayList<>();
        occupationModelArrayList=new ArrayList<>();
    }

    public ArrayList<ReserveModel> getReserveModelArrayList() {
        return reserveModelArrayList;
    }

    public void setReserveModelArrayList(ArrayList<ReserveModel> reserveModelArrayList) {
        this.reserveModelArrayList = reserveModelArrayList;
    }

    public ArrayList<OccupationModel> getOccupationModelArrayList() {
        return occupationModelArrayList;
    }

    public void setOccupationModelArrayList(ArrayList<OccupationModel> occupationModelArrayList) {
        this.occupationModelArrayList = occupationModelArrayList;
    }

    public String getTypeMoney() {
        return typeMoney;
    }

    public void setTypeMoney(String typeMoney) {
        this.typeMoney = typeMoney;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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

    public ArrayList<ArticleModel> getArticleModel() {
        return articleModel;
    }

    public void setArticleModel(ArrayList<ArticleModel> articleModel) {
        this.articleModel = articleModel;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getPointObtain() {
        return pointObtain;
    }

    public void setPointObtain(int pointObtain) {
        this.pointObtain = pointObtain;
    }

    public int getPointRequired() {
        return pointRequired;
    }

    public void setPointRequired(int pointRequired) {
        this.pointRequired = pointRequired;
    }

    public int getnDay() {
        return nDay;
    }

    public void setnDay(int nDay) {
        this.nDay = nDay;
    }

    public int getnHour() {
        return nHour;
    }

    public void setnHour(int nHour) {
        this.nHour = nHour;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
