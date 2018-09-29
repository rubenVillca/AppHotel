package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ModelParent;

public class ArticleModel extends ModelParent {
    private int id;
    private int idKeyConsum;
    private String name;
    private String description;
    private boolean isActive;

    public ArticleModel() {
        id=0;
        idKeyConsum=0;
        name="";
        description="";
        isActive=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKeyConsum() {
        return idKeyConsum;
    }

    public void setIdKeyConsum(int idKeyConsum) {
        this.idKeyConsum = idKeyConsum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
