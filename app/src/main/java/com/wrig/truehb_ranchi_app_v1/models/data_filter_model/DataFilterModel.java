package com.wrig.truehb_ranchi_app_v1.models.data_filter_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataFilterModel implements Serializable {
    @SerializedName("fromDate")
    private String fromDate;
    @SerializedName("toDate")
    private String toDate;
    @SerializedName("fromAge")
    private int fromAge;
    @SerializedName("toAge")
    private int toAge;
    @SerializedName("gender")
    private int gender;
    @SerializedName("anemicStatus")
    private int anemicStatus;
    @SerializedName("block")
    private String block;

    public DataFilterModel() {
    }

    public DataFilterModel(String fromDate, String toDate, int fromAge, int toAge, int gender, int anemicStatus, String block) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromAge = fromAge;
        this.toAge = toAge;
        this.gender = gender;
        this.anemicStatus = anemicStatus;
        this.block = block;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAnemicStatus() {
        return anemicStatus;
    }

    public void setAnemicStatus(int anemicStatus) {
        this.anemicStatus = anemicStatus;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "DataFilterModel{" +
                "fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", fromAge=" + fromAge +
                ", toAge=" + toAge +
                ", gender=" + gender +
                ", anemicStatus=" + anemicStatus +
                ", block='" + block + '\'' +
                '}';
    }
}
