package com.wrig.truehb_ranchi_app_v1.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DisplayDateUtils {


    public static String getDiplayDate(long time) {
        String myFormat = "dd MMMM yyyy - h:m a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date d = c.getTime();
        return sdf.format(d);
    }

    public static String getCurrentDate()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(timestamp);
    }

}
