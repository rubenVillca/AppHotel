package com.umss.sistemas.tesis.hotel.model;

import com.umss.sistemas.tesis.hotel.parent.ActivityParent;

/**
 * Created by ruben on 15/05/2017
 */

public class ActivityModel extends ActivityParent {
    private int id;
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
    private String name;
    private String description;
    private String image;

    public ActivityModel() {
        this.id = 0;
        this.dateStart = "";
        this.dateEnd = "";
        this.timeStart = "";
        this.timeEnd = "";
        this.name = "";
        this.description = "";
        this.image = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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
        this.description = android.text.Html.fromHtml(description).toString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
