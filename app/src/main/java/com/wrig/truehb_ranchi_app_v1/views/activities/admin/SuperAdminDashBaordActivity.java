package com.wrig.truehb_ranchi_app_v1.views.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.views.activities.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperAdminDashBaordActivity extends AppCompatActivity {
    SharedPref sharedPref;
    private static final int REQUEST_STORAGE = 2;
private String TAG =SuperAdminDashBaordActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin_dash_baord);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Admin Dashboard");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(SuperAdminDashBaordActivity.this);
        requestLocationPermission();
    }
    @OnClick(R.id.button_test_details)
    void fetchTestDetials(View view)
    {
        startActivity(new Intent(this,DataFilterActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.super_admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.super_admin_logout: {
                 new MaterialAlertDialogBuilder(SuperAdminDashBaordActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sharedPref.clearPrefence();
                                startActivity(new Intent(SuperAdminDashBaordActivity.this, LoginActivity.class));
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
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}