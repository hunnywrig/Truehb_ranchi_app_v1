package com.wrig.truehb_ranchi_app_v1.utils;

import java.util.Date;
import java.util.UUID;

public class GenrateTestIdUtils {

    public  static  String getgenrateTestId() {
       /* TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return String.valueOf(new Date().getTime());
        } else {
            String imei_number = UUID.randomUUID().toString().substring(0,15);
            return imei_number + "_" + new Date().getTime();
        }*/
        String imei_number = UUID.randomUUID().toString().substring(0,15);
        return imei_number + "_" + new Date().getTime();
    }
}
