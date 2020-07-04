package com.wrig.truehb_ranchi_app_v1.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPref {

    private static SharedPref sharedPref;
    private SharedPreferences sharedPreferences;

    public static SharedPref getInstance(Context context) {
        if (sharedPref == null) {
            sharedPref = new SharedPref(context);
        }
        return sharedPref;
    }


    private SharedPref(Context context) {
        //sharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
    }


    public void setStringData(String key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getStringData(String key, String defValue) {

        return sharedPreferences.getString(key, defValue);

    }

    public void setbooleanData(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getbooleanData(String key, boolean defValue) {

        return sharedPreferences.getBoolean(key, defValue);

    }



}
