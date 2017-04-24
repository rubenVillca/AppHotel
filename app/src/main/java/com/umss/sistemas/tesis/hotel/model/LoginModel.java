package com.umss.sistemas.tesis.hotel.model;

public class LoginModel {
    private int idPerson;
    private String password;
    private int state;

    public LoginModel() {
        this.idPerson = 0;
        this.password = "";
        this.state = 0;
    }

    public LoginModel(int idPerson, String password, int state) {
        this.idPerson = idPerson;
        this.password = password;
        this.state = state;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
