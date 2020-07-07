package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.models.ClientDetailsModel;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientDetailsInputActivity extends AppCompatActivity {
    @BindView(R.id.textinputedittext_clinet_name)
    TextInputEditText textinputedittext_clinet_name;
    @BindView(R.id.textinputedittext_clinet_age)
    TextInputEditText textinputedittext_clinet_age;
    @BindView(R.id.spinner_user_gender)
    AppCompatSpinner spinner_user_gender;
    @BindView(R.id.spinner_district)
    AppCompatSpinner spinner_district;
    @BindView(R.id.spinner_block)
    AppCompatSpinner spinner_block;
    @BindView(R.id.spinner_center)
    AppCompatSpinner spinner_center;
    @BindView(R.id.checkbox_pregnant)
    MaterialCheckBox checkbox_pregnant;


    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Details");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(ClientDetailsInputActivity.this);

        spinner_user_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner_user_gender.getSelectedItem().equals("Female")) {
                    checkbox_pregnant.setEnabled(true);
                } else {
                    checkbox_pregnant.setEnabled(false);
                    checkbox_pregnant.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.button_save)
    void saveClientDetails(View view) {
        String client_name = textinputedittext_clinet_name.getText().toString().trim();
        String client_age = textinputedittext_clinet_age.getText().toString().trim();
        String client_gender = spinner_user_gender.getSelectedItem().toString().trim();
        int client_pregnant = checkbox_pregnant.isChecked() ? 1 : 0;
        String district = spinner_district.getSelectedItem().toString().trim();
        String block = spinner_block.getSelectedItem().toString().trim();
        String center = spinner_center.getSelectedItem().toString().trim();
        int gender = 0;

        if (TextUtils.isEmpty(client_name) || client_name.length() < 3) {
            textinputedittext_clinet_name.setError("Please enter the name");
            textinputedittext_clinet_name.requestFocus();
        } else if (TextUtils.isEmpty(client_age)) {
            textinputedittext_clinet_name.setError("Please enter the age");
            textinputedittext_clinet_age.requestFocus();
        } else if (client_gender.equals("Select Gender")) {
            ShowToastUtils.showUiSnackBar(ClientDetailsInputActivity.this, view, "Please select the gender");
        } else if (district.equals("Select District")) {
            ShowToastUtils.showUiSnackBar(ClientDetailsInputActivity.this, view, "Please select the district");
        } else if (block.equals("Select Block")) {
            ShowToastUtils.showUiSnackBar(ClientDetailsInputActivity.this, view, "Please select the block");
        } else if (center.equals("Select Center")) {
            ShowToastUtils.showUiSnackBar(ClientDetailsInputActivity.this, view, "Please select the center");
        } else {

            if (client_gender.equals("Male")) {
                gender = 1;
            } else if (client_gender.equals("Female")) {
                gender = 2;
            } else {
                gender = 3;
            }

            ClientDetailsModel clientDetailsModel = new ClientDetailsModel(client_name, client_age, gender, client_pregnant, district, block, center);
            Intent i = new Intent(ClientDetailsInputActivity.this, DeviceCheckActivity.class);
            i.putExtra("clientDetails", clientDetailsModel);
            startActivity(i);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}