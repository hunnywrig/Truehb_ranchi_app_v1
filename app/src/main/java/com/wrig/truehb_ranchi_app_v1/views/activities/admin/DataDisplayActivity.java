package com.wrig.truehb_ranchi_app_v1.views.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.adapters.DisplayReportAdapter;
import com.wrig.truehb_ranchi_app_v1.apis.ApiClient;
import com.wrig.truehb_ranchi_app_v1.interfaces.ApiInterface;
import com.wrig.truehb_ranchi_app_v1.models.data_filter_model.DataFilterModel;
import com.wrig.truehb_ranchi_app_v1.models.test_dispaly_model.TestDetailsDisplayModel;
import com.wrig.truehb_ranchi_app_v1.models.test_dispaly_model.TestDetailsDisplayResponseModel;
import com.wrig.truehb_ranchi_app_v1.utils.CSVWriter;
import com.wrig.truehb_ranchi_app_v1.utils.DisplayDateUtils;
import com.wrig.truehb_ranchi_app_v1.utils.InternetConnection;
import com.wrig.truehb_ranchi_app_v1.utils.MysqlDateUtils;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;
import com.wrig.truehb_ranchi_app_v1.views.activities.TestResportActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataDisplayActivity extends AppCompatActivity {
    @BindView(R.id.txt_value_total_test_count)
    TextView txt_value_total_test_count;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    DataFilterModel dataFilterModel;

    private String TAG = DataDisplayActivity.class.getSimpleName();
    DisplayReportAdapter displayReportAdapter;
    List<TestDetailsDisplayModel> testDetailsDisplayModelList = null;
    public final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;
    Uri URI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Details");
        ButterKnife.bind(this);
        dataFilterModel = (DataFilterModel) getIntent().getSerializableExtra("dataFilter");

        if (InternetConnection.checkConnection(DataDisplayActivity.this)) {
            showRecylerView();
        } else {
            InternetConnection.showConnectionMsgDialog(DataDisplayActivity.this);
        }
    }

    void showRecylerView() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<TestDetailsDisplayResponseModel> call = apiInterface.displayHbTestDetails(dataFilterModel);
            call.enqueue(new Callback<TestDetailsDisplayResponseModel>() {
                @Override
                public void onResponse(Call<TestDetailsDisplayResponseModel> call, Response<TestDetailsDisplayResponseModel> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        TestDetailsDisplayResponseModel testDetailsDisplayResponseModel = response.body();
                        if (!testDetailsDisplayResponseModel.isError()) {
                            testDetailsDisplayModelList = testDetailsDisplayResponseModel.getTestDetailsDisplayModelList();
                            txt_value_total_test_count.setText(testDetailsDisplayModelList.size() + "");

                            displayReportAdapter = new DisplayReportAdapter(DataDisplayActivity.this, testDetailsDisplayModelList);
                            recyclerView.setAdapter(displayReportAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(DataDisplayActivity.this));

                            // Log.d(TAG,response.toString());

                        } else {
                            ShowToastUtils.showUiToast(DataDisplayActivity.this, testDetailsDisplayResponseModel.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<TestDetailsDisplayResponseModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onFailure" + t);
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Exception__" + e);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.super_admin_display_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sendData: {
                if (testDetailsDisplayModelList != null) {
                    if (testDetailsDisplayModelList.size() > 0) {
                        exportCsv();
                    } else {
                        ShowToastUtils.showUiMsgDialog(DataDisplayActivity.this, "", "Data Not Found");
                    }
                } else {
                    ShowToastUtils.showUiMsgDialog(DataDisplayActivity.this, "", "Data Not Found");
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void exportCsv() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(DataDisplayActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(DataDisplayActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(DataDisplayActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            new ExportDatabaseCSVTask().execute();
        }
    }


    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(DataDisplayActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            try {
                File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "True Hb", "");
                if (!exportDir.exists()) {
                    exportDir.mkdirs();
                }

                File file = new File(exportDir, DisplayDateUtils.getCurrentDateAndTimeFileName() + ".csv");

                file.createNewFile();
                //File file = File.createTempFile(/*DisplayDateUtils.getCurrentDateAndTimeFileName()*/  "Report.csv", null, DataDisplayActivity.this.getCacheDir());
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                String[] column = {"Test Id", "Name", "Age", "Gender", "Pregnant Status", "HB Value", "District", "Block", "Center", "Test Time"};
                csvWrite.writeNext(column);

                for (TestDetailsDisplayModel dataList : testDetailsDisplayModelList) {

                        String[] mySecondStringArray = {dataList.getTest_id(), dataList.getClient_name()
                                , "" + dataList.getClient_age(),getGender(dataList.getClient_gender())
                        ,getPregnant(dataList.getClient_pregnant_status()),dataList.getClient_hb_value()
                                ,dataList.getDistrict(),dataList.getBlock(),dataList.getPhc_uhc_sc(),dataList.getTest_time_stamp()};
                    csvWrite.writeNext(mySecondStringArray);
                }

                csvWrite.close();
                if (Build.VERSION.SDK_INT < 24) {
                    URI = Uri.fromFile(file);
                } else {
                    URI = Uri.parse(file.getPath()); // My work-around for new SDKs, doesn't work in Android 10.
                }

                return true;
            } catch (Exception e) {
                Log.d(TAG, "csv_Exception" + e);
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                // emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
                  emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Report");

                if (URI != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                // emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
                DataDisplayActivity.this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
                Toast.makeText(DataDisplayActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(DataDisplayActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    String getGender(int gender) {

        if (gender == 1) {
            return "Male";
        } else if (gender == 2) {
            return "Female";
        } else {
            return "Transgender";
        }

    }

    String getPregnant(int preg) {

        if (preg == 1) {
            return "Yes";
        }
        return "No";
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}