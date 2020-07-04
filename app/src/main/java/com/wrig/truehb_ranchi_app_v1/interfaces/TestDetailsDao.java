package com.wrig.truehb_ranchi_app_v1.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.wrig.truehb_ranchi_app_v1.models.TestDetailsModels;

@Dao
public interface TestDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTestDetails(TestDetailsModels testDataModel);
}
