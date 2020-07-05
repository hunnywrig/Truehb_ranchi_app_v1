package com.wrig.truehb_ranchi_app_v1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class ShowToastUtils {

    public static void showToastMessage(Context context, String msg) {
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

    public static void showUiMsgDialog(Activity context, String title, String msg) {
        new Thread() {
            public void run() {
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        new MaterialAlertDialogBuilder(context)
                                .setTitle(title)
                                .setMessage(msg)

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                //.setNegativeButton(android.R.string.no, null)
                                //.setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }
                });
            }
        }.start();
    }

    public static void showUiSnackBar(Activity context, View view, String msg) {
        new Thread() {
            public void run() {
                context.runOnUiThread(new Runnable() {
                    public void run() {
                        Snackbar.make(view,msg, BaseTransientBottomBar.LENGTH_LONG).show();

                    }
                });
            }
        }.start();
    }


}
