package com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestDetailsDownloadResponseModel implements Serializable {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("testDetails")
    private List<TestDetailsDownloadModel> testDetailsDownloadModelList;

    public TestDetailsDownloadResponseModel() {
    }

    public TestDetailsDownloadResponseModel(boolean error, String message, List<TestDetailsDownloadModel> testDetailsDownloadModelList) {
        this.error = error;
        this.message = message;
        this.testDetailsDownloadModelList = testDetailsDownloadModelList;
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

    public List<TestDetailsDownloadModel> getTestDetailsDownloadModelList() {
        return testDetailsDownloadModelList;
    }

    public void setTestDetailsDownloadModelList(List<TestDetailsDownloadModel> testDetailsDownloadModelList) {
        this.testDetailsDownloadModelList = testDetailsDownloadModelList;
    }

    @Override
    public String toString() {
        return "TestDetailsDownloadResponseModel{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", testDetailsDownloadModelList=" + testDetailsDownloadModelList +
                '}';
    }
}
