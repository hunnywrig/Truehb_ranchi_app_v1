package com.wrig.truehb_ranchi_app_v1.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ShowToastUtils {

    public static void showToastMessage(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showUiToast(Activity context, String msg) {
        //for toast msg
        new Thread() {
            public void run() {
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                    }
                });
            }
        }.start();
    }


}
