package com.wrig.truehb_ranchi_app_v1.models.health_center_data;

import java.io.Serializable;

public class HealthCenterJsonModel implements Serializable {
    private  String Select_District;
    private  String Select_Block;
    private  String Select_Center;

    public HealthCenterJsonModel() {
    }

    public HealthCenterJsonModel(String select_District, String select_Block, String select_Center) {
        Select_District = select_District;
        Select_Block = select_Block;
        Select_Center = select_Center;
    }

    public String getSelect_District() {
        return Select_District;
    }

    public void setSelect_District(String select_District) {
        Select_District = select_District;
    }

    public String getSelect_Block() {
        return Select_Block;
    }

    public void setSelect_Block(String select_Block) {
        Select_Block = select_Block;
    }

    public String getSelect_Center() {
        return Select_Center;
    }

    public void setSelect_Center(String select_Center) {
        Select_Center = select_Center;
    }

    @Override
    public String toString() {
        return "HealthCenterJsonModel{" +
                "Select_District='" + Select_District + '\'' +
                ", Select_Block='" + Select_Block + '\'' +
                ", Select_Center='" + Select_Center + '\'' +
                '}';
    }
}
