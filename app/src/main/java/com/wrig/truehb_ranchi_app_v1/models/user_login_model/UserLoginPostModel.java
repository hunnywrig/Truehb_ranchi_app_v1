package com.wrig.truehb_ranchi_app_v1.models.user_login_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginPostModel implements Serializable {

    @SerializedName("phNo")
    private String phNo;

    @SerializedName("password")
    private String password;

    public UserLoginPostModel() {
    }

    public UserLoginPostModel(String phNo, String password) {
        this.phNo = phNo;
        this.password = password;
    }

    public String getPhNo() {
        return phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginPostModel{" +
                "phNo='" + phNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
