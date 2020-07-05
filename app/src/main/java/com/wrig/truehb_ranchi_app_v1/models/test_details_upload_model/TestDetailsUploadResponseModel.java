package com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestDetailsUploadResponseModel implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("testIds")
    private List<TestIdsModel> testIdsModels;

    public TestDetailsUploadResponseModel() {
    }

    public TestDetailsUploadResponseModel(boolean error, String message, List<TestIdsModel> testIdsModels) {
        this.error = error;
        this.message = message;
        this.testIdsModels = testIdsModels;
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

    public List<TestIdsModel> getTestIdsModels() {
        return testIdsModels;
    }

    public void setTestIdsModels(List<TestIdsModel> testIdsModels) {
        this.testIdsModels = testIdsModels;
    }

    @Override
    public String toString() {
        return "TestDetailsUploadResponseModel{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", testIdsModels=" + testIdsModels +
                '}';
    }
}
