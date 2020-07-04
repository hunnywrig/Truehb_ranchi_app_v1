package com.wrig.truehb_ranchi_app_v1.interfaces;

import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginPostModel;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("userlogin")
    Call<UserLoginResponseModel> userLogin (@Body UserLoginPostModel model);
}
