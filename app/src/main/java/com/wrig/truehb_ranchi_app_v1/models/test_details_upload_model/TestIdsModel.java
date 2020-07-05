package com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model;

import java.io.Serializable;

public class TestIdsModel implements Serializable {
    private String test_id;
    private String status;

    public TestIdsModel() {
    }

    public TestIdsModel(String test_id, String status) {
        this.test_id = test_id;
        this.status = status;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestIdsModel{" +
                "test_id='" + test_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
