package com.wrig.truehb_ranchi_app_v1.interfaces;

import com.wrig.truehb_ranchi_app_v1.models.data_filter_model.DataFilterModel;
import com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel.TestDetailsDownloadPostModel;
import com.wrig.truehb_ranchi_app_v1.models.test_deatails_download_moel.TestDetailsDownloadResponseModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestDetailsUploadPostModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_upload_model.TestDetailsUploadResponseModel;
import com.wrig.truehb_ranchi_app_v1.models.test_dispaly_model.TestDetailsDisplayResponseModel;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginPostModel;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("userLogin")
    Call<UserLoginResponseModel> userLogin (@Body UserLoginPostModel model);

    @POST("superAdminLogin")
    Call<UserLoginResponseModel> superAdminLogin (@Body UserLoginPostModel model);

    @POST("userDownloadHbTestDetails")
    Call<TestDetailsDownloadResponseModel> userDownloadHbTestDetails(@Body TestDetailsDownloadPostModel testDetailsDownloadPostModel);

    @POST("syncTestDetails")
    Call<TestDetailsUploadResponseModel> syncTestDetails(@Body TestDetailsUploadPostModel testDetailsUploadPostModel);

    @POST("displayHbTestDetails")
    Call<TestDetailsDisplayResponseModel> displayHbTestDetails(@Body DataFilterModel dataFilterModel);


}
