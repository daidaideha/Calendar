package com.lyl.calendar;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.lyl.calendar.widget.calendar.CalendarCellDecorator;
import com.lyl.calendar.widget.calendar.CalendarCellView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lyl on 2016/8/6.
 */

public class SampleDecorator implements CalendarCellDecorator {

    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
            String dateString = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            SpannableString string = new SpannableString(dateString + "\n今天");
            string.setSpan(new RelativeSizeSpan(0.8f), 0, dateString.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            string.setSpan(new RelativeSizeSpan(0.7f), dateString.length(), dateString.length() + 3,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            cellView.getDayOfMonthTextView().setText(string);
        }
    }
}
