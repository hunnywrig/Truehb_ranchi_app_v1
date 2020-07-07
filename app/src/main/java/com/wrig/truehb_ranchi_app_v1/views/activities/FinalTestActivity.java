package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wrig.truehb_ranchi_app_v1.views.fragments.BottomSheetFragment;
import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.databases.AppRepository;
import com.wrig.truehb_ranchi_app_v1.interfaces.BottomSheetFragmentButtonClickListener;
import com.wrig.truehb_ranchi_app_v1.interfaces.MyUsbBroadcastListener;
import com.wrig.truehb_ranchi_app_v1.models.ClientDetailsModel;
import com.wrig.truehb_ranchi_app_v1.models.test_details_database_model.TestDetailsDatabaseModel;
import com.wrig.truehb_ranchi_app_v1.services.MyUsbReceiver;
import com.wrig.truehb_ranchi_app_v1.services.UsbService;
import com.wrig.truehb_ranchi_app_v1.utils.Constants;
import com.wrig.truehb_ranchi_app_v1.utils.GenrateTestIdUtils;
import com.wrig.truehb_ranchi_app_v1.utils.MysqlDateUtils;
import com.wrig.truehb_ranchi_app_v1.utils.PreferenceKey;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;

import java.lang.ref.WeakReference;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinalTestActivity extends AppCompatActivity implements MyUsbBroadcastListener, BottomSheetFragmentButtonClickListener {
    private AppRepository appRepository;
    SharedPref sharedPref;
    ClientDetailsModel clientDetailsModel;

    @BindView(R.id.txtdisplay)
    TextView txtdisplay;
    @BindView(R.id.txt_value_batch_code)
    TextView txt_value_batch_code;

    private MyHandler mHandler, readHandler;
    private UsbService usbService;

    private String TAG = FinalTestActivity.class.getSimpleName();

    ProgressDialog progressDialog;
    BottomSheetFragment bottomSheetFragment;
    /*
     * Notifications from UsbService will be received here.
     */
    MyUsbReceiver myUsbReceiver = new MyUsbReceiver();
    String hb_value = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Device Test Screen");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(FinalTestActivity.this);
        clientDetailsModel = (ClientDetailsModel) getIntent().getSerializableExtra("clientDetails");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//Screen always on

        appRepository = AppRepository.getInstance(getApplicationContext());

        progressDialog = new ProgressDialog(FinalTestActivity.this);
        progressDialog.setMessage("Fetching Hb value. Please wait...");
        progressDialog.setCancelable(false);

        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setCancelable(false);

       /* progressDialog.setButton(ProgressDialog.BUTTON_POSITIVE, "Abort Test", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write("U371".getBytes());//Start new test command

                } else {
                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                }
            }
        });*/

        mHandler = new MyHandler(this);
        readHandler = new MyHandler(this);
    }

    @OnClick(R.id.button_set_batch_code)
    void setBatchCode(View view) {
       MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(FinalTestActivity.this);
        builder.setCancelable(false);
        //builder.setTitle("Set Batch Code");

        final EditText input = new EditText(FinalTestActivity.this);
        input.setHint("Enter Batch Code");
        input.setTextColor(getColor(R.color.content_text_black_color));
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Set", (dialogInterface, i) -> {
            String code = input.getText().toString();
            if (code.length() < 3) {
                ShowToastUtils.showUiToast(FinalTestActivity.this, "Please enter valid the batch code");
            } else {
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write("U371".getBytes());//Start new test command

                } else {
                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (usbService != null) { // if UsbService was correctly binded, Send data
                            String batchCodeCommand = "U403" + code;
                            usbService.write(batchCodeCommand.getBytes());//start device Command
                        } else {
                            ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                        }
                    }
                }, 2000);


            }

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


    @OnClick(R.id.button_start_new_test)
    void startNewTest(View view) {


        if (usbService != null) { // if UsbService was correctly binded, Send data
            usbService.write("U371".getBytes());//Start new test command

        } else {
            ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write("U401".getBytes());//Start new test command
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                } else {
                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                }
            }
        }, 2000);

    }

    @Override
    public void setTextView(String result) {
        txtdisplay.setText(result);
    }

    @Override
    public void repeatTest() {
        if (usbService != null) { // if UsbService was correctly binded, Send data
            usbService.write("U371".getBytes());//Start new test command

        } else {
            ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write("U401".getBytes());//Start new test command
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                } else {
                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                }
            }
        }, 2000);
    }

    @Override
    public void saveTest() {
        try {
            TestDetailsDatabaseModel testDetailsDatabaseModel = new TestDetailsDatabaseModel();
            testDetailsDatabaseModel.setTest_id(GenrateTestIdUtils.getgenrateTestId());
            testDetailsDatabaseModel.setU_id(sharedPref.getIntegerData(PreferenceKey.PREF_USER_ID_KEY, 0));
            testDetailsDatabaseModel.setClient_name(clientDetailsModel.getClientName());
            testDetailsDatabaseModel.setClient_age(Integer.parseInt(clientDetailsModel.getClientAge()));
            testDetailsDatabaseModel.setClient_gender(clientDetailsModel.getClientGender());
            testDetailsDatabaseModel.setClient_pregnant_status(clientDetailsModel.getClientPregnant());
            testDetailsDatabaseModel.setClient_hb_value(hb_value);
            testDetailsDatabaseModel.setDistrict(clientDetailsModel.getDistrict());
            testDetailsDatabaseModel.setBlock(clientDetailsModel.getBlock());
            testDetailsDatabaseModel.setPhc_uhc_sc(clientDetailsModel.getCenter());
            testDetailsDatabaseModel.setTest_time_stamp(MysqlDateUtils.getMySqlTimeStamp());
            testDetailsDatabaseModel.setServer_status(Constants.SERVER_STATUS_NO);
            long res = appRepository.insertTestData(testDetailsDatabaseModel, FinalTestActivity.this);
            if (res == -1) {
                ShowToastUtils.showToastMessage(FinalTestActivity.this, "Data Already Exist !");
            } else if (res > 0) {
                ShowToastUtils.showToastMessage(FinalTestActivity.this, "Data Saved...");
                Intent intent = new Intent(FinalTestActivity.this, UserDashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception__" + e);
        }
    }

    @Override
   public void backToHome()
    {
        Intent intent = new Intent(FinalTestActivity.this, UserDashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    /*
     * This handler will be passed to UsbService. Data received from serial port is displayed through this handler
     */
    private class MyHandler extends Handler {
        private final WeakReference<FinalTestActivity> mActivity;

        public MyHandler(FinalTestActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT: {
                    String data = (String) msg.obj;
                    mActivity.get().txtdisplay.setText(data);
                    if (data.contains("B:") && !data.contains("Hb:")) {
                        data = data.replace("B:", "");
                        mActivity.get().txt_value_batch_code.setText(data);
                    }
                    if (data.contains("BATCH SAVED:")) {
                        data = data.replaceAll("BATCH SAVED: ", "");
                        data = data.replaceAll("[\\n\\t ]", "");
                        mActivity.get().txt_value_batch_code.setText(data);
                        /*Log.d(TAG,"batch__"+data);*/
                    }

                    if (data.contains("Hb:")) {
                        mActivity.get().txtdisplay.setText(data);
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                        hb_value = data.substring(6);
                        hb_value = hb_value.replaceAll("[^0-9.]", "");
                        Bundle bundle = new Bundle();
                        bundle.putString("hbvalue_key", hb_value);
                        bottomSheetFragment.setArguments(bundle);
                        bottomSheetFragment.show(getSupportFragmentManager(), "bottomSheetFragment");
                        mActivity.get().txtdisplay.setText("HB :" + hb_value);
                        //Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    }

                    break;
                }
                case UsbService.CTS_CHANGE: {
                    Toast.makeText(mActivity.get(), "CTS_CHANGE", Toast.LENGTH_LONG).show();
                    break;
                }
                case UsbService.DSR_CHANGE: {
                    Toast.makeText(mActivity.get(), "DSR_CHANGE", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }

    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };


    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(FinalTestActivity.this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(FinalTestActivity.this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(myUsbReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setFilters();  // Start listening notifications from UsbService
        startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it

        readHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (usbService != null) { // if UsbService was correctly binded, Send data
                    usbService.write("U402".getBytes());// Read Batch Code Command

                } else {
                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                }
            }
        }, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(myUsbReceiver);
        unbindService(usbConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_test_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_device_off: {

                new MaterialAlertDialogBuilder(FinalTestActivity.this)
                        .setTitle("Device Off")
                        .setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (usbService != null) { // if UsbService was correctly binded, Send data
                                    usbService.write("U370".getBytes());// Device off Command
                                    Intent intent = new Intent(FinalTestActivity.this, UserDashBoardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("EXIT", true);
                                    startActivity(intent);

                                } else {
                                    ShowToastUtils.showUiToast(FinalTestActivity.this, "Check OTG connection");
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                break;
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
