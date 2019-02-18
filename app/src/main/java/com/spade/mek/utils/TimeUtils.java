package com.spade.mek.utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Ayman Abouzeid on 8/28/17.
 */

public class TimeUtils {

    public static String getDate(long timeStamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        DateFormat dateFormatter = DateFormat.getDateInstance();
        dateFormatter.setTimeZone(timeZone);

        Calendar calendar =
                Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(timeStamp * 1000);
        String result = dateFormatter.format(calendar.getTime());
        calendar.clear();
        return result;
    }
}
