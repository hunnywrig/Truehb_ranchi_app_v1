package com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestDetailsDownloadPostModel implements Serializable {

    @SerializedName("u_id")
    private int u_id;
    @SerializedName("page_no")
    private  int page_no;

    public TestDetailsDownloadPostModel() {
    }

    public TestDetailsDownloadPostModel(int u_id, int page_no) {
        this.u_id = u_id;
        this.page_no = page_no;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    @Override
    public String toString() {
        return "TestDetailsDownloadPostModel{" +
                "u_id=" + u_id +
                ", page_no=" + page_no +
                '}';
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }
}
