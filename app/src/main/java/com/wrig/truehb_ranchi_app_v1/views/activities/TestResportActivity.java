package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.adapters.ReportAdapter;
import com.wrig.truehb_ranchi_app_v1.databases.AppRepository;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;
import com.wrig.truehb_ranchi_app_v1.utils.PreferenceKey;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestResportActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ReportAdapter adapter;
    private static final String TAG = TestResportActivity.class.getSimpleName();
    private AppRepository appRepository;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_resport);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Report");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(TestResportActivity.this);
        appRepository = AppRepository.getInstance(getApplicationContext());

        getLocalData();
    }

    void getLocalData() {

        try {

            List<TestDetailsDatabaseModel> testDataModelList = appRepository.getAllTestDataByUserId(sharedPref.getIntegerData(PreferenceKey.PREF_USER_ID_KEY, 0));
            if (testDataModelList.size() > 0) {


                adapter = new ReportAdapter(TestResportActivity.this, testDataModelList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(TestResportActivity.this));

            } else {

                ShowToastUtils.showToastMessage(TestResportActivity.this, "Test Data Not Found!");
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}