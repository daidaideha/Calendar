package com.lyl.calendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lyl on 2016/8/3.
 */

public class DateUtils {

    public static String getDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return (formatter.format(date));
    }

    public static String getDateMonth(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        return (formatter.format(date));
    }

    public static Date getHHmm(int hour, int min) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return formatter.parse(add0(hour) + ":" + add0(min));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String add0(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return time + "";
    }
}
