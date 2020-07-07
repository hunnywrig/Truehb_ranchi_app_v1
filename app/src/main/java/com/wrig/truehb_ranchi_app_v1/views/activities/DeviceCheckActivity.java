package com.wrig.truehb_ranchi_app_v1.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wrig.truehb_ranchi_app_v1.R;
import com.wrig.truehb_ranchi_app_v1.interfaces.MyUsbBroadcastListener;
import com.wrig.truehb_ranchi_app_v1.models.ClientDetailsModel;
import com.wrig.truehb_ranchi_app_v1.services.MyUsbReceiver;
import com.wrig.truehb_ranchi_app_v1.services.UsbService;
import com.wrig.truehb_ranchi_app_v1.utils.SharedPref;
import com.wrig.truehb_ranchi_app_v1.utils.ShowToastUtils;

import java.lang.ref.WeakReference;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceCheckActivity extends AppCompatActivity implements MyUsbBroadcastListener {
    SharedPref sharedPref;
    ClientDetailsModel clientDetailsModel;
    @BindView(R.id.txtdisplay)
    TextView txtdisplay;
    private MyHandler mHandler, readHandler;
    private UsbService usbService;

    /*
     * Notifications from UsbService will be received here.
     */
    MyUsbReceiver myUsbReceiver = new MyUsbReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Device Test Screen");
        ButterKnife.bind(this);
        sharedPref = SharedPref.getInstance(DeviceCheckActivity.this);
        clientDetailsModel = (ClientDetailsModel) getIntent().getSerializableExtra("clientDetails");

        mHandler = new MyHandler(this);
        readHandler = new MyHandler(this);

    }

    @OnClick(R.id.button_device_onn)
    void deviceOnn(View view) {
        if (usbService != null) { // if UsbService was correctly binded, Send data
            usbService.write("U371".getBytes());//start device Command
        } else {
            ShowToastUtils.showUiToast(DeviceCheckActivity.this, "Check OTG connection");
        }
    }


    @Override
    public void setTextView(String result) {
        txtdisplay.setText(result);
    }

    /*
     * This handler will be passed to UsbService. Data received from serial port is displayed through this handler
     */
    private class MyHandler extends Handler {
        private final WeakReference<DeviceCheckActivity> mActivity;

        public MyHandler(DeviceCheckActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT: {
                    String data = (String) msg.obj;
                    mActivity.get().txtdisplay.setText(data);
                    if (data.contains("ON") || data.contains("Hi")) {
                        readHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent i = new Intent(DeviceCheckActivity.this, FinalTestActivity.class);
                                i.putExtra("clientDetails", clientDetailsModel);
                                startActivity(i);
                                finish();
                            }
                        }, 3000);


                        // Toast.makeText(DeviceCheckActivity.this, data, Toast.LENGTH_LONG).show();

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
            Intent startService = new Intent(DeviceCheckActivity.this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(DeviceCheckActivity.this, service);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(myUsbReceiver);
        unbindService(usbConnection);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}