package com.wrig.truehb_ranchi_app_v1.views.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.models.data_filter_model.DataFilterModel;
import com.wrig.truehb_ranchi_app_v1.utils.DisplayDateUtils;
import com.wrig.truehb_ranchi_app_v1.utils.Validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataFilterActivity extends AppCompatActivity {
    @BindView(R.id.textinputedittext_from_date)
    TextInputEditText textinputedittext_from_date;
    @BindView(R.id.textinputedittext_to_date)
    TextInputEditText textinputedittext_to_date;
    @BindView(R.id.range_slider_age)
    RangeSlider range_slider_age;
    @BindView(R.id.spinner_user_gender)
    AppCompatSpinner spinner_user_gender;
    @BindView(R.id.spinner_block)
    AppCompatSpinner spinner_block;
    @BindView(R.id.spinner_anemic)
    AppCompatSpinner spinner_anemic;


    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    private String TAG = DataFilterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Data Filter");
        ButterKnife.bind(this);

        textinputedittext_from_date.setText("2020-07-01");
        textinputedittext_to_date.setText(DisplayDateUtils.getCurrentDate());

        textinputedittext_from_date.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DataFilterActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textinputedittext_from_date.setText(sdf.format(myCalendar.getTime()));
                    Validation.closeKeybord(DataFilterActivity.this);
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        textinputedittext_to_date.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(DataFilterActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textinputedittext_to_date.setText(sdf.format(myCalendar.getTime()));
                    Validation.closeKeybord(DataFilterActivity.this);
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


    }

    @OnClick(R.id.button_save)
    void saveFilter(View view) {
        String fromDate = textinputedittext_from_date.getText().toString().trim();
        String toDate = textinputedittext_to_date.getText().toString().trim();
        int fromAge = Math.round(range_slider_age.getValues().get(0));
        int toAge = Math.round(range_slider_age.getValues().get(1));
        String client_gender = spinner_user_gender.getSelectedItem().toString().trim();
        String block = spinner_block.getSelectedItem().toString().trim();
        String client_anemic = spinner_anemic.getSelectedItem().toString().trim();
        int gender = 0;
        int anemicStatus = 0;


        if (block.equals("Select Block")) {
            block = "NA";
        }

        if (client_gender.equals("Male")) {
            gender = 1;
        } else if (client_gender.equals("Female")) {
            gender = 2;
        } else if (client_gender.equals("Transgender")) {
            gender = 3;
        }

        if (client_anemic.equals("Anemic")) {
            anemicStatus = 1;
        } else if (client_anemic.equals("Not Anemic")) {
            anemicStatus = 2;
        }

        if (TextUtils.isEmpty(fromDate)) {
            textinputedittext_from_date.setError("Please fill the date");
        } else if (TextUtils.isEmpty(toDate)) {
            textinputedittext_to_date.setError("Please fill the date");
        } else {
            DataFilterModel dataFilterModel = new DataFilterModel(fromDate, toDate, fromAge, toAge, gender, anemicStatus, block);
           // Log.d(TAG, "dataFilterModel__" + dataFilterModel.toString());
            Intent i =new Intent(DataFilterActivity.this,DataDisplayActivity.class);
            i.putExtra("dataFilter",dataFilterModel);
            startActivity(i);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}