package com.wrig.truehb_ranchi_app_v1.models.user_login_model;

import com.google.gson.annotations.SerializedName;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginModel;

import java.io.Serializable;

public class UserLoginResponseModel implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("userData")
   private UserLoginModel userLoginModel;

    public UserLoginResponseModel() {
    }

    public UserLoginResponseModel(boolean error, String message, UserLoginModel userLoginModel) {
        this.error = error;
        this.message = message;
        this.userLoginModel = userLoginModel;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserLoginModel getUserLoginModel() {
        return userLoginModel;
    }

    public void setUserLoginModel(UserLoginModel userLoginModel) {
        this.userLoginModel = userLoginModel;
    }

    @Override
    public String toString() {
        return "UserLoginResponseModel{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", userLoginModel=" + userLoginModel +
                '}';
    }
}
