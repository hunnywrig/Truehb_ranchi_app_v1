package com.wrig.truehb_ranchi_app_v1.models.health_center_data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "HealthCenterTable")
public class HeathCenterDataModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String district,block,center;

    public HeathCenterDataModel() {
    }

    public HeathCenterDataModel(String district, String block, String center) {
        this.district = district;
        this.block = block;
        this.center = center;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "HeathCenterDataModel{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", block='" + block + '\'' +
                ", center='" + center + '\'' +
                '}';
    }
}
