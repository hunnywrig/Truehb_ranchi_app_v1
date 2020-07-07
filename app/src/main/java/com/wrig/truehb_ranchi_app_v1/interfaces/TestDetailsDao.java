package com.wrig.truehb_ranchi_app_v1.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;

import java.util.List;

@Dao
public interface TestDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTestDetails(TestDetailsDatabaseModel testDataModel);

    @Query("SELECT * FROM TestDetailsTable WHERE test_id = :test_id")
    List<TestDetailsDatabaseModel> getTestDataByTestId(String test_id);

    @Query("SELECT * FROM TestDetailsTable WHERE u_id = :user_id AND server_status =:server_status")
    List<TestDetailsDatabaseModel> getAllTestDataByUserIdAndServerStatus(int user_id,int server_status);

    @Query("DELETE FROM TestDetailsTable")
    int deleteAllTestData();

    @Query("UPDATE TestDetailsTable SET server_status =:server_status WHERE test_id = :test_id")
    int updateTestDataServerStatusByTestId(String test_id, int server_status);

    @Query("SELECT * FROM TestDetailsTable WHERE u_id = :user_id")
    List<TestDetailsDatabaseModel> getAllTestDataByUserId(int user_id);
}
