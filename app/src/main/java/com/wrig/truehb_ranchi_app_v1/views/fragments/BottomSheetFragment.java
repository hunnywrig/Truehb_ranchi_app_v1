package com.wrig.truehb_ranchi_app_v1.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.interfaces.BottomSheetFragmentButtonClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {

    @BindView(R.id.btnSaveTest)
    Button btnSaveTest;

    @BindView(R.id.btnRepeatTest)
    Button btnRepeatTest;

    @BindView(R.id.btnBackToHome)
    Button btnBackToHome;

    @BindView(R.id.iv_close)
    ImageView iv_close;

    @BindView(R.id.tv_hbValue)
    TextView tv_hbValue;
    private BottomSheetFragmentButtonClickListener bottomSheetFragmentButtonClickListener;
    private String TAG = BottomSheetFragment.class.getSimpleName();

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        ButterKnife.bind(this, view);
        btnSaveTest.setOnClickListener(v -> {
            bottomSheetFragmentButtonClickListener.saveTest();
        });

        iv_close.setOnClickListener(v -> {
            //todo close bottom sheet
            dismiss();
        });
        btnRepeatTest.setOnClickListener(v -> {
            //todo close bottom sheet and repeat test
            dismiss();
            bottomSheetFragmentButtonClickListener.repeatTest();
        });
        btnBackToHome.setOnClickListener(v -> {
            dismiss();
            bottomSheetFragmentButtonClickListener.backToHome();
        });
        tv_hbValue.setText(this.getArguments().getString("hbvalue_key")+" g/dl");
        //Log.d(TAG, "onCreateView: " + this.getArguments().getString("hbvalue_key"));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BottomSheetFragmentButtonClickListener) {
            bottomSheetFragmentButtonClickListener = (BottomSheetFragmentButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bottomSheetFragmentButtonClickListener = null;
    }
}