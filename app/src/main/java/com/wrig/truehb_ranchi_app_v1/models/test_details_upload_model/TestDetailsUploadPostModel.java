package com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TestDetailsUploadPostModel implements Serializable {
    @SerializedName("testDetails")
    private List<TestDetailsUploadModel> testDetailsUploadModels;

    public TestDetailsUploadPostModel() {
    }

    public TestDetailsUploadPostModel(List<TestDetailsUploadModel> testDetailsUploadModels) {
        this.testDetailsUploadModels = testDetailsUploadModels;
    }

    public List<TestDetailsUploadModel> getTestDetailsUploadModels() {
        return testDetailsUploadModels;
    }

    public void setTestDetailsUploadModels(List<TestDetailsUploadModel> testDetailsUploadModels) {
        this.testDetailsUploadModels = testDetailsUploadModels;
    }

    @Override
    public String toString() {
        return "TestDetailsUploadPostModel{" +
                "testDetailsUploadModels=" + testDetailsUploadModels +
                '}';
    }
}
