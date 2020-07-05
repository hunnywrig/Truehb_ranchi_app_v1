package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.apis.ApiClient;
import com.wrig.truehb_ranchi_app_v1.databases.AppRepository;
import com.wrig.truehb_ranchi_app_v1.interfaces.ApiInterface;
import com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel.TestDetailsDownloadModel;
import com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel.TestDetailsDownloadPostModel;
import com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel.TestDetailsDownloadResponseModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestDetailsUploadModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestDetailsUploadPostModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestDetailsUploadResponseModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestIdsModel;
import com.wrig.truehb_ranchi_app_v1.utils.Constants;
import com.wrig.truehb_ranchi_app_v1.utils.InternetConnection;
import com.wrig.truehb_ranchi_app_v1.utils.PreferenceKey;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashBoardActivity extends AppCompatActivity {
    SharedPref sharedPref;
    private AppRepository appRepository;
    ProgressDialog progressDialog;
    private String TAG = UserDashBoardActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Dashboard");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(UserDashBoardActivity.this);

        appRepository = AppRepository.getInstance(getApplicationContext());

        progressDialog = new ProgressDialog(UserDashBoardActivity.this);
        progressDialog.setMessage("Syncing Data with Server. Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        if (InternetConnection.checkConnection(UserDashBoardActivity.this)) {
            downloadTestDetails(1);
        } else {
            InternetConnection.showConnectionMsgDialog(UserDashBoardActivity.this);
        }

    }

    @OnClick(R.id.button_start_new_test)
    void buttonStartNewTest() {
        startActivity(new Intent(UserDashBoardActivity.this, ClientDetailsInputActivity.class));
    }

    void downloadTestDetails(final int pageNo) {
        if (!progressDialog.isShowing())
            progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TestDetailsDownloadResponseModel> call = apiInterface.userDownloadHbTestDetails(new TestDetailsDownloadPostModel(sharedPref.getIntegerData(PreferenceKey.PREF_USER_ID_KEY, 0), pageNo));
        call.enqueue(new Callback<TestDetailsDownloadResponseModel>() {
            @Override
            public void onResponse(Call<TestDetailsDownloadResponseModel> call, Response<TestDetailsDownloadResponseModel> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (response.isSuccessful()) {
                    TestDetailsDownloadResponseModel testDetailsDownloadResponseModel = response.body();
                    if (!testDetailsDownloadResponseModel.isError()) {
                        List<TestDetailsDownloadModel> testDetailsDownloadModels = testDetailsDownloadResponseModel.getTestDetailsDownloadModelList();

                        for (TestDetailsDownloadModel downloadModel : testDetailsDownloadModels) {
                            TestDetailsDatabaseModel testDetailsDatabaseModel = new TestDetailsDatabaseModel();

                            testDetailsDatabaseModel.setTest_id(downloadModel.getTest_id());
                            testDetailsDatabaseModel.setU_id(downloadModel.getU_id());
                            testDetailsDatabaseModel.setClient_name(downloadModel.getClient_name());
                            testDetailsDatabaseModel.setClient_age(downloadModel.getClient_age());
                            testDetailsDatabaseModel.setClient_gender(downloadModel.getClient_gender());
                            testDetailsDatabaseModel.setClient_pregnant_status(downloadModel.getClient_pregnant_status());
                            testDetailsDatabaseModel.setClient_hb_value(downloadModel.getClient_hb_value());
                            testDetailsDatabaseModel.setDistrict(downloadModel.getDistrict());
                            testDetailsDatabaseModel.setBlock(downloadModel.getBlock());
                            testDetailsDatabaseModel.setPhc_uhc_sc(downloadModel.getPhc_uhc_sc());
                            testDetailsDatabaseModel.setServer_status(downloadModel.getServer_status());
                            testDetailsDatabaseModel.setTest_time_stamp(downloadModel.getTest_time_stamp());

                            appRepository.insertTestData(testDetailsDatabaseModel, UserDashBoardActivity.this);
                        }
                        int val = pageNo;
                        val++;
                        downloadTestDetails(val);

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<TestDetailsDownloadResponseModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                Log.d(TAG, "onFailure__" + t.toString());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.user_logout: {
                if (localDataForSyncExist()) {
                    ShowToastUtils.showUiMsgDialog(UserDashBoardActivity.this, "Sync", "Please Sync Data Before Logout!");
                } else {
                    new MaterialAlertDialogBuilder(UserDashBoardActivity.this)
                            .setTitle("Logout")
                            .setMessage("Are you sure ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    appRepository.deleteAllTestData();
                                    sharedPref.clearPrefence();
                                    startActivity(new Intent(UserDashBoardActivity.this, LoginActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                break;
            }
            case R.id.refresh: {
                try {
                    if (localDataForSyncExist()) {
                        if (InternetConnection.checkConnection(UserDashBoardActivity.this)) {
                            syncHbTestTable();
                        } else {
                            InternetConnection.showConnectionMsgDialog(UserDashBoardActivity.this);
                        }
                    } else {
                        ShowToastUtils.showUiToast(UserDashBoardActivity.this, "Data Already Synced!");
                    }
                } catch (Exception e) {
                    Log.d(TAG, "Exception___" + e);
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void syncHbTestTable() {
        List<TestDetailsDatabaseModel> testDetailsDatabaseModels = appRepository.getAllTestDataByUserIdAndServerStatus(sharedPref.getIntegerData(PreferenceKey.PREF_USER_ID_KEY, 0), Constants.SERVER_STATUS_NO);
        List<TestDetailsUploadModel> testDetailsUploadModels = new ArrayList<>();
        for (TestDetailsDatabaseModel model : testDetailsDatabaseModels) {
            testDetailsUploadModels.add(new TestDetailsUploadModel(model.getTest_id(), model.getU_id(), model.getClient_name(), model.getClient_age(), model.getClient_gender(), model.getClient_pregnant_status(), model.getClient_hb_value(), model.getDistrict(), model.getBlock(), model.getPhc_uhc_sc(), model.getTest_time_stamp(), model.getServer_status()));
        }

        if (!progressDialog.isShowing())
            progressDialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TestDetailsUploadResponseModel> call = apiInterface.syncTestDetails(new TestDetailsUploadPostModel(testDetailsUploadModels));
        call.enqueue(new Callback<TestDetailsUploadResponseModel>() {
            @Override
            public void onResponse(Call<TestDetailsUploadResponseModel> call, Response<TestDetailsUploadResponseModel> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                if (response.isSuccessful()) {
                    TestDetailsUploadResponseModel testDetailsUploadResponseModel = response.body();

                    if (!testDetailsUploadResponseModel.isError()) {
                        List<TestIdsModel> testIdsModels = testDetailsUploadResponseModel.getTestIdsModels();
                        for (TestIdsModel idsModel : testIdsModels) {
                            if (idsModel.getStatus().equals("yes")) {
                                appRepository.updateTestDataServerStatusByTestId(idsModel.getTest_id(), Constants.SERVER_STATUS_YES);
                            }
                        }
                        ShowToastUtils.showUiToast(UserDashBoardActivity.this, "Sync completed!");

                    }
                }
            }

            @Override
            public void onFailure(Call<TestDetailsUploadResponseModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Log.d(TAG, "onFailure__" + t);
            }
        });
    }

    boolean localDataForSyncExist() {
        List<TestDetailsDatabaseModel> testDetailsDatabaseModels = appRepository.getAllTestDataByUserIdAndServerStatus(sharedPref.getIntegerData(PreferenceKey.PREF_USER_ID_KEY, 0), Constants.SERVER_STATUS_NO);

        if (testDetailsDatabaseModels.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}