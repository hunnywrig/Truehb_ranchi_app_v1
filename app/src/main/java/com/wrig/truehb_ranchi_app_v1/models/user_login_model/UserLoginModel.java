package com.wrig.truehb_ranchi_app_v1.models.user_login_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLoginModel implements Serializable {

    @SerializedName("u_id")
    private int u_id;

    @SerializedName("acive_status")
    private int acive_status;

    public UserLoginModel() {
    }

    public UserLoginModel(int u_id, int acive_status) {
        this.u_id = u_id;
        this.acive_status = acive_status;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getAcive_status() {
        return acive_status;
    }

    public void setAcive_status(int acive_status) {
        this.acive_status = acive_status;
    }

    @Override
    public String toString() {
        return "UserLoginModel{" +
                "u_id=" + u_id +
                ", acive_status=" + acive_status +
                '}';
    }
}
