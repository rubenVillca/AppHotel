package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;


public class MessageModel extends ModelParent {
    private int id;
    private String tittle;
    private String content;
    private String dateRecived;
    private String timeRecived;
    private boolean isRead;
    private String emailRemitente;
    private String nameRemitente;
    private boolean isActive;

    public MessageModel() {
        this.id = 0;
        this.tittle = "";
        this.content = "";
        this.dateRecived = "";
        this.timeRecived = "";
        this.isRead = false;
        this.emailRemitente = "";
        this.nameRemitente = "";
        this.isActive = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateRecived() {
        return dateRecived;
    }

    public void setDateRecived(String dateRecived) {
        this.dateRecived = dateRecived;
    }

    public String getTimeRecived() {
        return timeRecived;
    }

    public void setTimeRecived(String timeRecived) {
        this.timeRecived = timeRecived;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getEmailRemitente() {
        return emailRemitente;
    }

    public void setEmailRemitente(String emailRemitente) {
        this.emailRemitente = emailRemitente;
    }

    public String getNameRemitente() {
        return nameRemitente;
    }

    public void setNameRemitente(String nameRemitente) {
        this.nameRemitente = nameRemitente;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
