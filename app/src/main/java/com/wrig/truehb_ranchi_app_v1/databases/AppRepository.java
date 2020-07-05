package com.wrig.truehb_ranchi_app_v1.databases;

import android.content.Context;
import android.util.Log;

import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    AppDatabase mAppDatabase;
    long hb_test_return;

    private static AppRepository ourInstance;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {

        return ourInstance = new AppRepository(context);
    }

    private AppRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
        hb_test_return = 0;
    }

    public long insertTestData(final TestDetailsDatabaseModel testDetailsDatabaseModel, final Context context) {
        List<TestDetailsDatabaseModel> modelList = mAppDatabase.TestDetailsDao().getTestDataByTestId(testDetailsDatabaseModel.getTest_id());

        if (modelList.size() > 0) {
            //data already exists
            Log.d("insertTestData_Apprep", "I am the UI thread_Data Already Exist");
            hb_test_return = -1;
        } else {
            //data does not exists
            hb_test_return = mAppDatabase.TestDetailsDao().insertTestDetails(testDetailsDatabaseModel);
            Log.d("insertTestData_Apprep", "run: " + "data inserted");
        }
        return hb_test_return;
    }

    public List<TestDetailsDatabaseModel> getAllTestDataByUserIdAndServerStatus(int user_id, int server_status) {
        return mAppDatabase.TestDetailsDao().getAllTestDataByUserIdAndServerStatus(user_id, server_status);
    }

    public void deleteAllTestData() {
        int isDeleted = mAppDatabase.TestDetailsDao().deleteAllTestData();
        Log.d("AppRepository", "DeleteAllTestData___: " + isDeleted);
    }

    public void updateTestDataServerStatusByTestId(String test_id, int server_status) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

                int isUpdate= mAppDatabase.TestDetailsDao().updateTestDataServerStatusByTestId(test_id,  server_status);
                Log.d("AppRepository", "UpdateTestDataServer: " + isUpdate);

            }
        });
    }
}
