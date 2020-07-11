package com.wrig.truehb_ranchi_app_v1.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {


   public static String getJsonFromAssets(Context context, String fileName) {
         String TAG = JsonUtils.class.getSimpleName();
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (Exception e) {
            Log.d(TAG,"Exception___"+e);
            return null;
        }

        return jsonString;
    }
}
