package com.wrig.truehb_ranchi_app_v1.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.textfield.TextInputEditText;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.interfaces.BottomSheetListener;
import com.wrig.truehb_ranchi_app_v1.models.data_filter_model.DataFilterModel;
import com.wrig.truehb_ranchi_app_v1.utils.DisplayDateUtils;
import com.wrig.truehb_ranchi_app_v1.utils.Validation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterDataBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterDataBottomSheetFragment extends BottomSheetDialogFragment {

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

    private BottomSheetListener mListener;


    private String TAG = FilterDataBottomSheetFragment.class.getSimpleName();

    public FilterDataBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_data_bottom_sheet, container, false);

        ButterKnife.bind(this,view);

        textinputedittext_from_date.setText("2020-07-01");
        textinputedittext_to_date.setText(DisplayDateUtils.getCurrentDate());

        textinputedittext_from_date.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textinputedittext_from_date.setText(sdf.format(myCalendar.getTime()));
                    Validation.closeKeybord(getActivity());
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    textinputedittext_to_date.setText(sdf.format(myCalendar.getTime()));
                    Validation.closeKeybord(getActivity());
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        return view;
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
            /*Intent i =new Intent(getContext(), DataDisplayActivity.class);
            i.putExtra("dataFilter",dataFilterModel);
            startActivity(i);*/

            mListener.onButtonClicked(dataFilterModel);

            dismiss();

        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }


}