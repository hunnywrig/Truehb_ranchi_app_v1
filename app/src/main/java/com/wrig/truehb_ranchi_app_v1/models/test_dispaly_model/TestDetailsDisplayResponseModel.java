package com.wrig.truehb_ranchi_app_v1.models.test_dispaly_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestDetailsDisplayResponseModel implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("testDetails")
    private List<TestDetailsDisplayModel> testDetailsDisplayModelList;

    public TestDetailsDisplayResponseModel() {
    }

    public TestDetailsDisplayResponseModel(boolean error, String message, List<TestDetailsDisplayModel> testDetailsDisplayModelList) {
        this.error = error;
        this.message = message;
        this.testDetailsDisplayModelList = testDetailsDisplayModelList;
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

    public List<TestDetailsDisplayModel> getTestDetailsDisplayModelList() {
        return testDetailsDisplayModelList;
    }

    public void setTestDetailsDisplayModelList(List<TestDetailsDisplayModel> testDetailsDisplayModelList) {
        this.testDetailsDisplayModelList = testDetailsDisplayModelList;
    }

    @Override
    public String toString() {
        return "TestDetailsDisplayResponseModel{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", testDetailsDisplayModelList=" + testDetailsDisplayModelList +
                '}';
    }
}
