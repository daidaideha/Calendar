package com.lyl.calendar.dao;

import android.databinding.ObservableField;

/**
 * Created by lyl on 2016/8/6.
 */

public class HistoryDate {
    public ObservableField<String> mStartDate = new ObservableField<>();
    public ObservableField<String> mEndDate = new ObservableField<>();

    public HistoryDate(String startDate, String endDate) {
        this.mStartDate.set(startDate);
        this.mEndDate.set(endDate);
    }
}
