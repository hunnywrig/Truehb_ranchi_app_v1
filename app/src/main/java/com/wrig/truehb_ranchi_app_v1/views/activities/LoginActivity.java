package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.apis.ApiClient;
import com.wrig.truehb_ranchi_app_v1.interfaces.ApiInterface;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginModel;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginPostModel;
import com.wrig.truehb_ranchi_app_v1.models.user_login_model.UserLoginResponseModel;
import com.wrig.truehb_ranchi_app_v1.utils.InternetConnection;
import com.wrig.truehb_ranchi_app_v1.utils.PreferenceKey;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;
import com.wrig.truehb_ranchi_app_v1.utils.Validation;
import com.wrig.truehb_ranchi_app_v1.views.activities.admin.SuperAdminDashBaordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.textinputedittext_mobile_no)
    TextInputEditText textinputedittext_mobile_no;
    @BindView(R.id.textinputedittext_password)
    TextInputEditText textinputedittext_password;
    @BindView(R.id.spinner_userType)
    AppCompatSpinner spinner_userType;
    @BindView(R.id.button_login)
    Button button_login;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(LoginActivity.this);
        getSupportActionBar().setTitle("User Login");
        button_login.setOnClickListener(v -> loginCheck());
    }

    void loginCheck() {
        String mobile_no = textinputedittext_mobile_no.getText().toString().trim();
        String password = textinputedittext_password.getText().toString().trim();
        String userType = spinner_userType.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(mobile_no) || mobile_no.length() < 10) {
            textinputedittext_mobile_no.setError("Please enter your number");
            textinputedittext_mobile_no.requestFocus();
        } else if (password.length() < 8 && !Validation.isValidPassword(password)) {
            textinputedittext_password.setError("Password must contain minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character");
            textinputedittext_password.requestFocus();

        } else if (userType.equals("Select User Type")) {
            ShowToastUtils.showToastMessage(LoginActivity.this, "Please select valid user");
        } else if (InternetConnection.checkConnection(LoginActivity.this)) {
            if (userType.equals("User")) {
                userLogin(new UserLoginPostModel(mobile_no, password));
            } else if (userType.equals("Admin")) {
                superAdminLogin(new UserLoginPostModel(mobile_no, password));
            }
        } else {
            InternetConnection.showConnectionMsgDialog(LoginActivity.this);
        }
    }

    void userLogin(UserLoginPostModel userLoginPostModel) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserLoginResponseModel> call = apiInterface.userLogin(userLoginPostModel);
        call.enqueue(new Callback<UserLoginResponseModel>() {
            @Override
            public void onResponse(Call<UserLoginResponseModel> call, Response<UserLoginResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    UserLoginResponseModel userLoginResponseModel = response.body();
                    if (!userLoginResponseModel.isError()) {
                        Log.d(TAG, "onResponse___" + userLoginResponseModel.toString());
                        ShowToastUtils.showUiMsgDialog(LoginActivity.this, "", userLoginResponseModel.getMessage());
                        UserLoginModel userLoginModel = userLoginResponseModel.getUserLoginModel();
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_ID_KEY, userLoginModel.getU_id());
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_ACTIVE_STATUS_KEY, userLoginModel.getAcive_status());
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_TYPE_KEY, 2);
                        startActivity(new Intent(LoginActivity.this, UserDashBoardActivity.class));
                        finish();

                    } else {
                        ShowToastUtils.showUiMsgDialog(LoginActivity.this, "", userLoginResponseModel.getMessage());
                        //textinputedittext_mobile_no.setText("");
                        textinputedittext_password.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure__" + t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    void superAdminLogin(UserLoginPostModel userLoginPostModel) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserLoginResponseModel> call = apiInterface.superAdminLogin(userLoginPostModel);
        call.enqueue(new Callback<UserLoginResponseModel>() {
            @Override
            public void onResponse(Call<UserLoginResponseModel> call, Response<UserLoginResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    UserLoginResponseModel userLoginResponseModel = response.body();
                    if (!userLoginResponseModel.isError()) {
                        Log.d(TAG, "onResponse___" + userLoginResponseModel.toString());
                        ShowToastUtils.showUiMsgDialog(LoginActivity.this, "", userLoginResponseModel.getMessage());
                        UserLoginModel userLoginModel = userLoginResponseModel.getUserLoginModel();
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_ID_KEY, userLoginModel.getU_id());
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_ACTIVE_STATUS_KEY, userLoginModel.getAcive_status());
                        sharedPref.setIntegerData(PreferenceKey.PREF_USER_TYPE_KEY, 1);
                        startActivity(new Intent(LoginActivity.this, SuperAdminDashBaordActivity.class));
                        finish();

                    } else {
                        ShowToastUtils.showUiMsgDialog(LoginActivity.this, "", userLoginResponseModel.getMessage());
                        //textinputedittext_mobile_no.setText("");
                        textinputedittext_password.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure__" + t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userType = sharedPref.getIntegerData(PreferenceKey.PREF_USER_TYPE_KEY, 0);
        switch (userType) {
            case 1: {
                startActivity(new Intent(LoginActivity.this, SuperAdminDashBaordActivity.class));
                finish();
                break;
            }
            case 2: {
                startActivity(new Intent(LoginActivity.this, UserDashBoardActivity.class));
                finish();
                break;
            }
        }
    }
}