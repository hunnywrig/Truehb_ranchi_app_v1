package com.wrig.truehb_ranchi_app_v1.models;

import java.io.Serializable;

public class ClientDetailsModel implements Serializable {
    private String clientName;
    private String clientAge;
    private int clientGender;
    private int clientPregnant;
    private String district;
    private String block;
    private String center;
    public ClientDetailsModel() {
    }

    public ClientDetailsModel(String clientName, String clientAge, int clientGender, int clientPregnant, String district, String block, String center) {
        this.clientName = clientName;
        this.clientAge = clientAge;
        this.clientGender = clientGender;
        this.clientPregnant = clientPregnant;
        this.district = district;
        this.block = block;
        this.center = center;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAge() {
        return clientAge;
    }

    public void setClientAge(String clientAge) {
        this.clientAge = clientAge;
    }

    public int getClientGender() {
        return clientGender;
    }

    public void setClientGender(int clientGender) {
        this.clientGender = clientGender;
    }

    public int getClientPregnant() {
        return clientPregnant;
    }

    public void setClientPregnant(int clientPregnant) {
        this.clientPregnant = clientPregnant;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "ClientDetailsModel{" +
                "clientName='" + clientName + '\'' +
                ", clientAge='" + clientAge + '\'' +
                ", clientGender=" + clientGender +
                ", clientPregnant=" + clientPregnant +
                ", district='" + district + '\'' +
                ", block='" + block + '\'' +
                ", center='" + center + '\'' +
                '}';
    }
}
