package com.wrig.truehb_ranchi_app_v1.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wrig.truehb_ranchi_app_v1.models.health_center_data.HeathCenterDataModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;

import java.util.List;


@Dao
public interface HealthCenterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHealthCenter(HeathCenterDataModel heathCenterDataModel);

    @Query("DELETE FROM HealthCenterTable")
    int deleteAllHealthCenter();

    @Query("SELECT district FROM HealthCenterTable")
    List<String> getAllDistrict();

    @Query("SELECT block FROM HealthCenterTable where district = :district")
    List<String> getAllBlockByDistrict(String district);

    @Query("SELECT center FROM HealthCenterTable where block = :block")
    List<String> getAllCenterByDistrict(String block);
}
