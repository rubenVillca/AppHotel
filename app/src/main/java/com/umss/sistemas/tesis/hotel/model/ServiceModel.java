package com.umss.sistemas.tesis.hotel.model;

import java.util.ArrayList;

public class ServiceModel {
    private int serviceId;
    private int serviceReserved;
    private String serviceImage;
    private String serviceName;
    private String serviceType;
    private String serviceDescription;
    private ArrayList<ServicePriceModel> servicePrice;

    public ServiceModel() {
        serviceId = 0;
        serviceReserved = 0;
        this.serviceImage = "";
        this.serviceName = "";
        this.serviceType = "";
        serviceDescription = "";
        servicePrice=new ArrayList<>();
    }

    public ArrayList<ServicePriceModel> getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(ArrayList<ServicePriceModel> servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getServiceReserved() {
        return serviceReserved;
    }

    public void setServiceReserved(int serviceReserved) {
        this.serviceReserved = serviceReserved;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
