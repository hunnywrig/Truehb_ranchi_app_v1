package com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestDetailsDownloadModel implements Serializable {

    @SerializedName("test_id")
    private String test_id;
    @SerializedName("u_id")
    private int u_id;
    @SerializedName("client_name")
    private String client_name;
    @SerializedName("client_age")
    private int client_age;
    @SerializedName("client_gender")
    private int client_gender;
    @SerializedName("client_pregnant_status")
    private int client_pregnant_status;
    @SerializedName("client_hb_value")
    private String client_hb_value;
    @SerializedName("district")
    private String district;
    @SerializedName("block")
    private String block;
    @SerializedName("phc_uhc_sc")
    private String phc_uhc_sc;
    @SerializedName("test_time_stamp")
    private String test_time_stamp;
    @SerializedName("server_status")
    private int server_status;

    public TestDetailsDownloadModel() {
    }

    public TestDetailsDownloadModel(String test_id, int u_id, String client_name, int client_age, int client_gender, int client_pregnant_status, String client_hb_value, String district, String block, String phc_uhc_sc, String test_time_stamp, int server_status) {
        this.test_id = test_id;
        this.u_id = u_id;
        this.client_name = client_name;
        this.client_age = client_age;
        this.client_gender = client_gender;
        this.client_pregnant_status = client_pregnant_status;
        this.client_hb_value = client_hb_value;
        this.district = district;
        this.block = block;
        this.phc_uhc_sc = phc_uhc_sc;
        this.test_time_stamp = test_time_stamp;
        this.server_status = server_status;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getClient_age() {
        return client_age;
    }

    public void setClient_age(int client_age) {
        this.client_age = client_age;
    }

    public int getClient_gender() {
        return client_gender;
    }

    public void setClient_gender(int client_gender) {
        this.client_gender = client_gender;
    }

    public int getClient_pregnant_status() {
        return client_pregnant_status;
    }

    public void setClient_pregnant_status(int client_pregnant_status) {
        this.client_pregnant_status = client_pregnant_status;
    }

    public String getClient_hb_value() {
        return client_hb_value;
    }

    public void setClient_hb_value(String client_hb_value) {
        this.client_hb_value = client_hb_value;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPhc_uhc_sc() {
        return phc_uhc_sc;
    }

    public void setPhc_uhc_sc(String phc_uhc_sc) {
        this.phc_uhc_sc = phc_uhc_sc;
    }

    public String getTest_time_stamp() {
        return test_time_stamp;
    }

    public void setTest_time_stamp(String test_time_stamp) {
        this.test_time_stamp = test_time_stamp;
    }

    public int getServer_status() {
        return server_status;
    }

    public void setServer_status(int server_status) {
        this.server_status = server_status;
    }

    @Override
    public String toString() {
        return "TestDetailsModels{" +
                "test_id='" + test_id + '\'' +
                ", u_id=" + u_id +
                ", client_name='" + client_name + '\'' +
                ", client_age=" + client_age +
                ", client_gender=" + client_gender +
                ", client_pregnant_status=" + client_pregnant_status +
                ", client_hb_value='" + client_hb_value + '\'' +
                ", district='" + district + '\'' +
                ", block='" + block + '\'' +
                ", phc_uhc_sc='" + phc_uhc_sc + '\'' +
                ", test_time_stamp='" + test_time_stamp + '\'' +
                ", server_status=" + server_status +
                '}';
    }
}
