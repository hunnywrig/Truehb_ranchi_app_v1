package com.wrig.truehb_ranchi_app_v1.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wrig.truehb_ranchi_app_v1.interfaces.MyUsbBroadcastListener;

public class MyUsbReceiver extends BroadcastReceiver {
    MyUsbBroadcastListener myUsbBroadcastListener;
    @Override
    public void onReceive(Context context, Intent intent) {
        myUsbBroadcastListener=(MyUsbBroadcastListener)context;
        switch (intent.getAction()) {
            case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                myUsbBroadcastListener.setTextView("USB Ready");
                break;
            case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                myUsbBroadcastListener.setTextView("USB Permission not granted");
                break;
            case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                myUsbBroadcastListener.setTextView("No USB connected");
                break;
            case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                myUsbBroadcastListener.setTextView("USB disconnected");
                break;
            case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                myUsbBroadcastListener.setTextView("USB device not supported");
                break;
        }
    }
}
