package com.wrig.truehb_ranchi_app_v1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MysqlDateUtils {

    public static String getMysqlDate(String simpledate)
    {
        String myFormat = "dd MMMM yyyy - h:m a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat   mysqlsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        /// Calendar c = Calendar.getInstance();
        // c.setTimeInMillis(Timestamp.valueOf(simpledate).getTime());
        Date d = null;
        try {
            d = sdf.parse(simpledate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mysqlsdf.format(d);
    }
}
