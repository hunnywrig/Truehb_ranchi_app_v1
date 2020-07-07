package com.wrig.truehb_ranchi_app_v1.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DisplayDateUtils {


    public static String getDiplayDateLong(long time) {
        String myFormat = "dd MMMM yyyy - h:m a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        Date d = c.getTime();
        return sdf.format(d);
    }

    public static String getDiplayDateString(String simpledate) {
        String myFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat   displaySdf = new SimpleDateFormat("dd MMMM yyyy - h:m a", Locale.US);
        /// Calendar c = Calendar.getInstance();
        // c.setTimeInMillis(Timestamp.valueOf(simpledate).getTime());
        Date d = null;
        try {
            d = sdf.parse(simpledate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displaySdf.format(d);
    }

    public static String getCurrentDate()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(timestamp);
    }

}
